package com.sp.saveurfood1.Community;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sp.saveurfood1.R;

public class Societyforaged extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_societyforaged);
        webView = findViewById(R.id.Webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://societyagedsick.org.sg/");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}