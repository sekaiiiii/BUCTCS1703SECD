package com.buct.museumguide.ui.ClassForNews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.buct.museumguide.R;

public class WebViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_web_viewer);
        Intent intent = getIntent();
        String uri=intent.getStringExtra("uri");
        WebView myWebView = new WebView(WebViewer.this);
        setContentView(myWebView);
        myWebView.loadUrl(uri);//在这里输入新闻对应的地址
    }
}
