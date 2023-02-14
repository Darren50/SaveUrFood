package com.sp.saveurfood1.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.sp.saveurfood1.R;

public class InterestingNews extends AppCompatActivity {

    CardView news1;
    CardView news2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interesting_news);
        news2=findViewById(R.id.newscard2);
        news1=findViewById(R.id.newscard1);
        news2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.thepigsite.com/news/2021/07/want-to-learn-how-to-combat-food-waste-at-home");


            }


        });



        news1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.channelnewsasia.com/news/cnainsider/edible-food-waste-ugly-asia-expiry-dates-treedots-14387636");


            }


        });


    }

    private void gotoUrl(String s) {
        Uri uri= Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }



}