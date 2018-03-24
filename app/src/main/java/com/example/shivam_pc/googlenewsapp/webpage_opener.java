package com.example.shivam_pc.googlenewsapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Shivam-PC on 21-03-2018.
 */

public class webpage_opener extends Activity {

    WebView webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webpage);

        String url = getIntent().getStringExtra("url");

        webview = (WebView) findViewById(R.id.webpage);

        WebSettings websettings = webview.getSettings();
        websettings.setJavaScriptEnabled(true);
        webview.loadUrl(url);
        webview.setWebViewClient(new WebViewClient());

    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
}
