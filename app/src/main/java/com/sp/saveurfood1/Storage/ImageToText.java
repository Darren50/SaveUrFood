package com.sp.saveurfood1.Storage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.sp.saveurfood1.R;

import java.io.ByteArrayOutputStream;

public class ImageToText extends AppCompatActivity {

    ImageView mImageView;
    TextView detectedText;
    Button sendit;



    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_to_text);
        detectedText=findViewById(R.id.detectedText);
        mImageView=findViewById(R.id.mImageView);
        if (ContextCompat.checkSelfPermission(ImageToText.this,Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ImageToText.this, new String[]{
                    Manifest.permission.CAMERA
            },100);
        }
        sendit=findViewById(R.id.sendit);
        sendit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendit();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        getMenuInflater().inflate(R.menu.menu_onlyimage, menu);
        return true;
    }

    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    private void pickCamera(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,100);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addImage){
            showImageImportDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    private void showImageImportDialog() {
        //items to display in dialog
        String[] items = {" Camera", " Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        //set title
        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if (checkSelfPermission(Manifest.permission.CAMERA)
                                == PackageManager.PERMISSION_DENIED){
                            //permission not granted, request it.
                            String[] permissions = {Manifest.permission.CAMERA};
                            //show popup for runtime permission
                            requestPermissions(permissions, PERMISSION_CODE);
                        }
                        else {
                            //permission already granted
                            pickCamera();
                        }
                    }

                }
                if (which == 1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_DENIED){
                            //permission not granted, request it.
                            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                            //show popup for runtime permission
                            requestPermissions(permissions, PERMISSION_CODE);
                        }
                        else {
                            //permission already granted
                            pickImageFromGallery();
                        }
                    }
                    else {
                        //system os is less then marshmallow
                        pickImageFromGallery();
                    }
                }
            }
        });
        dialog.create().show();     //show dialog
    }

    //handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission was granted
                    pickImageFromGallery();
                } else {
                    //permission was denied
                    Toast.makeText(this, "Permission denied, go to Settings and allow permission for the app!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //handle result of picked image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //set image to image view
            mImageView.setImageURI(data.getData());
            mImageView.setDrawingCacheEnabled(true);
            mImageView.buildDrawingCache();
            Bitmap bm = mImageView.getDrawingCache();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 0, stream);
            detect();


        }
        if(requestCode==100){

            Bitmap bitmap=(Bitmap) data.getExtras().get("data");
            mImageView.setImageBitmap(bitmap);


            mImageView.setDrawingCacheEnabled(true);

            mImageView.buildDrawingCache();

            Bitmap bm = mImageView.getDrawingCache();



            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 0, stream);
            detect();



        }
    }

    public void detect() {
        //perform text detection here
        //This program uses google's vision service

        //TODO 1. define TextRecognizer
        TextRecognizer recognizer = new TextRecognizer.Builder(ImageToText.this).build();

        //TODO 2. Get bitmap from imageview
        Bitmap bitmap = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();

        //TODO 3. get frame from bitmap
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();

        //TODO 4. get data from frame
        SparseArray<TextBlock> sparseArray =  recognizer.detect(frame);

        //TODO 5. set data on textview
        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0;i < sparseArray.size(); i++){
            TextBlock tx = sparseArray.get(i);
            String str = tx.getValue();
            stringBuilder.append(str);
        }
        detectedText.setText(stringBuilder);


    }

    public void sendit(){
        String result=detectedText.getText().toString();
        Intent intent=new Intent(ImageToText.this,DataInput.class);
        Bundle bundle=new Bundle();
        bundle.putString("result",result);
        intent.putExtras(bundle);
        startActivity(intent);
    }






}