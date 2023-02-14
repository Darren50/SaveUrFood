package com.sp.saveurfood1.Storage;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.sp.saveurfood1.R;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataInput extends AppCompatActivity {
    private EditText contactName;
    private EditText contactAddress;
    private EditText contactTel;
    private EditText contactEmail;
    private RadioGroup contactTypes;
    private Button buttonSave;
    TextView tvResult;
    String btExpiryStr,btTodayStr;
    Button btToday,btExpiry,bTCalculate;
    DatePickerDialog.OnDateSetListener dateSetListener1,dateSetListener2;


    private Helper helper= null;

    private String restaurantID="";
    public byte[] byteArray;

    ImageView mImageView;


    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_input);

        Intent intent=getIntent();
        Bundle k=intent.getExtras();
        contactName = findViewById(R.id.contact_name);
        if (k!=null){
            contactName.setText(k.getString("result"));
        }else{
            contactName.setText(" ");
        }


        contactTel = findViewById(R.id.contact_tel);


        buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(onSave);
        helper = new Helper(this);



        restaurantID = getIntent().getStringExtra("ID");

        mImageView = findViewById(R.id.image_view);

        btToday=findViewById(R.id.bt_today);
        btExpiry=findViewById(R.id.bt_expiry);
        bTCalculate=findViewById(R.id.bt_calculate);
        tvResult=findViewById(R.id.tv_result);



        Calendar calendar = Calendar.getInstance();
        int year =calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        if (ContextCompat.checkSelfPermission(DataInput.this,Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DataInput.this, new String[]{
                    Manifest.permission.CAMERA
            },100);
        }

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        String date=simpleDateFormat.format(Calendar.getInstance().getTime());
        btExpiry.setText(date);
        btToday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        DataInput.this
                        , android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,dateSetListener1,year,month,day

                );
                datePickerDialog.getWindow().setBackgroundDrawable(new
                        ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();


            }
        });

        dateSetListener1 = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                String date= day+"/"+month+"/"+year;
                btToday.setText(date);
                btTodayStr=btToday.getText().toString();
            }
        };

        btExpiry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        DataInput.this
                        , android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,dateSetListener2,year,month,day

                );
                datePickerDialog.getWindow().setBackgroundDrawable(new
                        ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();


            }
        });

        dateSetListener2 = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                String date= day+"/"+month+"/"+year;
                btExpiry.setText(date);
                btExpiryStr=btExpiry.getText().toString();
            }
        };

        bTCalculate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String sDate=btToday.getText().toString();
                String eDate=btExpiry.getText().toString();
                SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("dd/MM/yy");
                try{
                    Date date1=simpleDateFormat1.parse(sDate);
                    Date date2=simpleDateFormat1.parse(eDate);

                    long startDate=date1.getTime();
                    long endDate=date2.getTime();

                    if (startDate<=endDate){
                        Period period=new Period(startDate,endDate, PeriodType.yearMonthDay());
                        int years=period.getYears();
                        int months=period.getMonths();
                        int days=period.getDays();

                        tvResult.setText(years+" Years| "+months+" Months | "+days+" Days");
                    } else {
                        Toast.makeText(getApplicationContext()
                                , "Expiry date should not be earlier than bought Date."
                                ,Toast.LENGTH_SHORT).show();
                    }
                } catch(ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        //handle button click

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(DataInput.this, InfoDisplay.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addImage){
            showImageImportDialog();
        }
        if(id==R.id.ai){
            showImageImportDialogForImage();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showImageImportDialogForImage() {
        //items to display in dialog
        String[] items = {" Yes", " No"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        //set title
        dialog.setTitle("Do you want to scan the food name?");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent intent=new Intent(DataInput.this,ImageToText.class);
                    startActivity(intent);

                }
                if (which == 1) {
                    Intent intent=new Intent(DataInput.this,DataInput.class);
                    startActivity(intent);
                }
            }
        });
        dialog.create().show();     //show dialog
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
                    Toast.makeText(this, "Permission denied, go to Settings and allow permissions for the app!", Toast.LENGTH_SHORT).show();
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
        helper.close();

    }







    View.OnClickListener onSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (byteArray == null) {
                Toast.makeText(v.getContext(), "You need to input an image!", Toast.LENGTH_LONG).show();
                Intent intent;
                intent = new Intent(DataInput.this,DataInput.class);
                startActivity(intent);


            }

            else {
                String nameStr = contactName.getText().toString();
                String telStr = contactTel.getText().toString();

                String btToday=btTodayStr;
                String btExpiry=btExpiryStr;
                String tvResult1=tvResult.getText().toString();







                if (restaurantID == null) {
                    helper.insert(nameStr,telStr, btToday, btExpiry, tvResult1, byteArray);
                } else {
                    helper.update(restaurantID, nameStr, telStr,  btToday, btExpiry, tvResult1, byteArray);
                }

                Intent intent2=new Intent(DataInput.this, InfoDisplay.class);
                startActivity(intent2);


                //To finish the activity
                finish();
            }
        }
    };
}