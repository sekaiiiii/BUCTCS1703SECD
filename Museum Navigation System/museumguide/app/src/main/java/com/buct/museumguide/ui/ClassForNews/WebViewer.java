package com.buct.museumguide.ui.ClassForNews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.buct.museumguide.R;

public class WebViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_web_viewer);
        WebView myWebView = new WebView(WebViewer.this);
        setContentView(myWebView);
        myWebView.loadUrl("https://www.baidu.com");
    }
}
