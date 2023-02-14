package com.sp.saveurfood1.ShoppingList;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.sp.saveurfood1.R;
import com.sp.saveurfood1.Storage.DataInput;

import java.io.ByteArrayOutputStream;

public class DataInputForList extends AppCompatActivity {
    private EditText listName;
    private EditText listDes;
    private EditText listBarcode;
    private Button buttonSave;

    private Helper2 helper2 = null;

    private String restaurantID = "";
    public byte[] byteArray;

    ImageView mImageView;
    Button mChooseBtn;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_input_for_list);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        listBarcode = findViewById(R.id.list_barcode);

        if (b != null) {
            listBarcode.setText(b.getString("result"));

        } else {
            listBarcode.setText(" ");
        }


        listName = findViewById(R.id.list_name);
        listDes = findViewById(R.id.list_Des);
        if (ContextCompat.checkSelfPermission(DataInputForList.this,Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DataInputForList.this, new String[]{
                    Manifest.permission.CAMERA
            },100);
        }

        buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(onSave);
        helper2 = new Helper2(this);
        restaurantID = getIntent().getStringExtra("ID");
        mImageView = findViewById(R.id.image_view);

    }

    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        getMenuInflater().inflate(R.menu.menu_onlyimage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addImage){
            showImageImportDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void pickCamera(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,100);
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
            byteArray = stream.toByteArray();


        }

        if(requestCode==100){

            Bitmap bitmap=(Bitmap) data.getExtras().get("data");
            mImageView.setImageBitmap(bitmap);


            mImageView.setDrawingCacheEnabled(true);

            mImageView.buildDrawingCache();

            Bitmap bm = mImageView.getDrawingCache();


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 0, stream);
            byteArray = stream.toByteArray();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper2.close();

    }


    View.OnClickListener onSave = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (byteArray == null) {
                Toast.makeText(v.getContext(), "You need to input an image!", Toast.LENGTH_LONG).show();
                Intent intent;
                intent = new Intent(DataInputForList.this, DataInputForList.class);
                startActivity(intent);


            } else {
                String nameStr = listName.getText().toString();
                String barcodeStr = listBarcode.getText().toString();
                String desStr = listDes.getText().toString();


                if (restaurantID == null) {
                    helper2.insert(nameStr, barcodeStr, desStr, byteArray);
                } else {
                    helper2.update(restaurantID, nameStr, barcodeStr, desStr, byteArray);
                }

                Intent intent2 = new Intent(DataInputForList.this, InfoDisplayForList.class);
                startActivity(intent2);


                //To finish the activity
                finish();
            }
        }
    };

}
