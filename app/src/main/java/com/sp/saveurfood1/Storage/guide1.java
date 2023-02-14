package com.sp.saveurfood1.Storage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.sp.saveurfood1.R;

public class guide1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide1);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(guide1.this, InfoDisplay.class);
        startActivity(intent);
    }
}