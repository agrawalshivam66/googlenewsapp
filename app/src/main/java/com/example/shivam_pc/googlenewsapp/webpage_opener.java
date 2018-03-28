package com.example.shivam_pc.googlenewsapp;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by Shivam-PC on 21-03-2018.
 */

public class webpage_opener extends Fragment {

    WebView webView;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.webpage, container, false);


        //Retrieve the value
        String url = getArguments().getString("YourKey");

        progressBar = (ProgressBar) view.findViewById(R.id.loading_spinner);
        webView = (WebView) view.findViewById(R.id.webpage);

        webView.setWebViewClient(new myWebclient());

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress > 20) {
                    newProgress = newProgress * 3;
                    progressBar.setProgress(newProgress);
                } else if (newProgress <= 20) {
                    newProgress = newProgress * 2;
                    progressBar.setProgress(newProgress);
                }
                if (newProgress >= 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        progressBar.setProgress(0);
        return view;
    }


    public class myWebclient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }


    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
        else onBackPressed();
    }

}
