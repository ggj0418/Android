package com.example.android.Activites.Payment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.android.R;
import com.example.android.Retrofit.WebViewClient.InicisWebViewClient;

public class InicisActivity extends AppCompatActivity {

    private WebView webView;
    private static final String APP_SCHEME = "iamporttest://";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicis);

        Intent intent = getIntent();
        Uri intentData = intent.getData();
        int cartNo = intent.getIntExtra("cartNo", -1);

        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new InicisWebViewClient(this, cartNo));

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }

        if ( intentData == null ) {
            webView.loadUrl("file:///android_asset/inicis_webview.html");
        } else {
            //isp 인증 후 복귀했을 때 결제 후속조치
            String url = intentData.toString();
            if ( url.startsWith(APP_SCHEME) ) {
                String redirectURL = url.substring(APP_SCHEME.length()+3);
                webView.loadUrl(redirectURL);
            }
        }
    }

    // 외부 앱으로의 이동이 없는 카카오페이, 신한, 현대, 하나 등은 이 함수를 거치지 않고 바로 redirect_url로 넘어감
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String url = intent.toString();
        if (url.startsWith(APP_SCHEME)) {
            String redirectURL = url.substring(APP_SCHEME.length() + 3);
            webView.loadUrl(redirectURL);
        }
    }
}