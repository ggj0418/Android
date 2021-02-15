package com.example.android.SocialLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.android.MyWebViewClient;
import com.example.android.R;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SocialLogin extends AppCompatActivity {

    private OkHttpClient okHttpClient = new OkHttpClient.Builder().build(); // TODO: Make this a singleton for the app

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_login);

        WebView webView = (WebView) findViewById(R.id.social_webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());



    }
}


//        webView.setWebViewClient(new WebViewClient() {
//            @SuppressWarnings("deprecation") // From API 21 we should use another overload
//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//                return handleRequest(url);
//            }
//
//            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public WebResourceResponse shouldInterceptRequest(@NonNull WebView view, @NonNull WebResourceRequest request) {
//                return handleRequest(request.getUrl().toString());
//            }
//
//            private WebResourceResponse handleRequest(String url) {
//                try {
//                    final Call call = okHttpClient.newCall(new Request.Builder()
//                            .url(url)
//                            // TODO: Add custom headers
//                            .build()
//                    );
//
//                    final Response response = call.execute();
//
//                    // TODO: Probably need to go back to the UI thread, verify first
//                    // TODO: Get the title from some custom "x-" header instead of using the response code
//                    updateScreenTitle(Integer.toString(response.code()));
//
//                    return new WebResourceResponse(
//                            response.header("Content-Type", "application/json"), // You can set something other as default content-type
//                            response.header("Accept", "application/json"),  // Again, you can set another encoding as default
//                            response.body().byteStream()
//                    );
//
//                } catch (Exception e) {
//                    // TODO: Figure out how to show a custom error screen when we fail
//                    return null;
//                }
//            }
//        });
//
//        webView.loadUrl("http://www.automart.ml/oauth2/authorization/naver");    //url 주소를 가져오기
//    }
//
//
//    private void updateScreenTitle(String title) {
//        setTitle(title);
//    }
//}