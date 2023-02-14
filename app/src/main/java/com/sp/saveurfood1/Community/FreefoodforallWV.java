package com.sp.saveurfood1.Community;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sp.saveurfood1.R;

public class FreefoodforallWV extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freefoodforall_w_v);
        webView = findViewById(R.id.Webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.freefood.org.sg/");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}