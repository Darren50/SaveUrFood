package com.sp.saveurfood1.Community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sp.saveurfood1.R;

public class Foodbanks extends AppCompatActivity {

    CardView freefoodforall,foodbanksingapore,foodforall,ronaldmacdonald,societyforaged,willinghearts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodbanks);
        freefoodforall=findViewById(R.id.freefoodforall);//FFFA
        foodbanksingapore=findViewById(R.id.foodbanksingapore);//FBS
        foodforall=findViewById(R.id.foodforall);//FOOD from the heart
        ronaldmacdonald=findViewById(R.id.ronaldmacdonald);
        societyforaged=findViewById(R.id.societyfortheaged);
        willinghearts=findViewById(R.id.willinghearts);
        freefoodforall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Foodbanks.this, FreefoodforallWV.class);
                startActivity(intent);
            }
        });
        foodbanksingapore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Foodbanks.this, Foodbanksingapore.class);
                startActivity(intent);
            }
        });
        foodforall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Foodbanks.this, Foodforall.class);
                startActivity(intent);
            }
        });
        ronaldmacdonald.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Foodbanks.this, RonaldMacdonald.class);
                startActivity(intent);
            }
        });
        societyforaged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Foodbanks.this, Societyforaged.class);
                startActivity(intent);
            }
        });
        willinghearts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Foodbanks.this, Willinghearts.class);
                startActivity(intent);
            }
        });
    }
}