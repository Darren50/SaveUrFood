package com.sp.saveurfood1.ShoppingList;

import android.content.Intent;

import com.journeyapps.barcodescanner.CaptureActivity;

public class CaptureAct extends CaptureActivity {
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(CaptureAct.this, InfoDisplayForList.class);
        startActivity(intent);
    }
}
