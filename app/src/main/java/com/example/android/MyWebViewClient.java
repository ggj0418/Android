package com.example.android;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {

    // 로딩이 시작될 때
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.loadUrl("http://www.automart.ml/oauth2/authorization/naver");
    }
}
