package com.sp.saveurfood1.ShoppingList;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CompareBCtoDB extends AppCompatActivity {
    private Helper2 helper2 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent iii=getIntent();
        Bundle b=iii.getExtras();
        String j;
        if (b!=null){
            j=b.getString("result2");

        }else{
            j=" ";

        }

        helper2 = new Helper2(this);
        helper2.deleteItem2(j);
        Intent intent=new Intent(CompareBCtoDB.this, InfoDisplayForList.class);
        startActivity(intent);
    }

}


