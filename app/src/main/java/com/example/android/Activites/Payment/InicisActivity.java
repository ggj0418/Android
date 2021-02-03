package com.example.android.Activites.Payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.android.R;
import com.example.android.Retrofit.WebViewClient.InicisWebViewClient;

public class InicisActivity extends AppCompatActivity {

    private WebView webView;
    private static final String APP_SCHEME = "iamporttest://";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicis);

        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new InicisWebViewClient(this));

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }

        Intent intent = getIntent();
        Uri intentData = intent.getData();

        if ( intentData == null ) {
            webView.loadUrl("file:///android_asset/inicis_webview.html");
        } else {
            //isp 인증 후 복귀했을 때 결제 후속조치
            String url = intentData.toString();
            if ( url.startsWith(APP_SCHEME) ) {
//                String redirectURL = url.substring(APP_SCHEME.length()+3);
//                webView.loadUrl(redirectURL);
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String url = intent.toString();
        if (url.startsWith(APP_SCHEME)) {
//            String redirectURL = url.substring(APP_SCHEME.length() + 3);
//            webView.loadUrl(redirectURL);
            setResult(RESULT_OK);
            finish();
        }
    }
}