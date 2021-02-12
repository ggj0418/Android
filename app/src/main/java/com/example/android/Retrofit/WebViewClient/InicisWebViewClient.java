package com.example.android.Retrofit.WebViewClient;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.android.Retrofit.RetrofitClient;
import com.example.android.Services.Services;
import com.example.android.Utils.Preference.PreferenceManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http2.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

// TODO 결제 완료 후 리다이렉트 페이지로 네이버로 들어가는데 그 부분을 제어하고 싶은데 어디로 들어가는지 찾아야함
public class InicisWebViewClient extends WebViewClient {
    private final Activity activity;
    private final int cartNo;
    private boolean isPaymentEnd = false;

    public InicisWebViewClient(Activity activity, int cartNo) {
        this.activity = activity;
        this.cartNo = cartNo;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if(url.startsWith("http://52.78.123.79")) {
            view.loadUrl("file:///android_asset/inicis_wait.html");
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if(url.endsWith("html")) {
            WebViewClientMethod method = new WebViewClientMethod(activity);
            method.getHashUid(view, "merchant_" + System.currentTimeMillis());
        }
        Log.d("ggj0418", url);
    }

    // 외부 앱 실행을 위한 Native Code 처리 수행
    // return 값이 true면 새로운 웹 브라우저 앱이 실행
    // return 값이 false면 현재 WebView에서 링크가 이동됨
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        Log.d("ggj0418", url);

        if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {
            Intent intent = null;

            try {
                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME); //IntentURI처리
                Uri uri = Uri.parse(intent.getDataString());

                activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                return true;
            } catch (URISyntaxException ex) {
                return false;
            } catch (ActivityNotFoundException e) {
                if (intent == null) return false;

                if (handleNotFoundPaymentScheme(intent.getScheme())) return true;

                String packageName = intent.getPackage();
                if (packageName != null) {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                    return true;
                }

                return false;
            }
        } else if(url.startsWith("http://52.78.123.79")) {
            Map<String, String> map = new HashMap<>();
            int pos1= url.indexOf("?");

            if (pos1>=0) {
                url = url.substring(pos1+1);
            }

            String[] params = url.split("&");
            for (String param : params) {
                String name = param.split("=")[0];
                String value = param.split("=")[1];
                map.put(name, value);
            }

            boolean impSuccess = Boolean.parseBoolean(map.get("imp_success"));
            String impUid = map.get("imp_uid");

            // TODO 서버에서 merchant_uid를 = 그대로 인식하는지 URL 인코딩해서 인식하는지 알아야 함
            String merchantUid = map.get("merchant_uid");
//            merchantUid = Objects.requireNonNull(merchantUid).replaceAll("%3D", "=");

            Services retrofitAPI2 = RetrofitClient.getRetrofit(PreferenceManager.getString(activity, "accessToken")).create(Services.class);
            Call<ResponseBody> certifyPaymentCall = retrofitAPI2.certifyPayment(cartNo, impSuccess, impUid, merchantUid);
            certifyPaymentCall.enqueue(new Callback<ResponseBody>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    switch (response.code()) {
                        case 200:
                            try {
                                boolean isCerified = Boolean.parseBoolean(response.body().string());
                                if(isCerified) {
                                    activity.setResult(200);
                                } else {
                                    activity.setResult(400);
                                }
                            } catch (IOException e) {
                                activity.setResult(500);
                                e.printStackTrace();
                            }
                            break;
                        case 401:
                            activity.setResult(401);
                            break;
                        case 403:
                            activity.setResult(403);
                            break;
                        default:
                            activity.setResult(500);
                            break;
                    }
                    activity.finish();
                }

                @EverythingIsNonNull
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    activity.setResult(500);
                    activity.finish();
                }
            });

            return false;
        }

        return false;
    }

    /**
     * @param scheme
     * @return 해당 scheme에 대해 처리를 직접 하는지 여부
     * <p>
     * 결제를 위한 3rd-party 앱이 아직 설치되어있지 않아 ActivityNotFoundException이 발생하는 경우 처리합니다.
     * 여기서 handler되지않은 scheme에 대해서는 intent로부터 Package정보 추출이 가능하다면 다음에서 packageName으로 market이동합니다.
     */
    protected boolean handleNotFoundPaymentScheme(String scheme) {
        //PG사에서 호출하는 url에 package정보가 없어 ActivityNotFoundException이 난 후 market 실행이 안되는 경우
        if (PaymentScheme.ISP.equalsIgnoreCase(scheme)) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PaymentScheme.PACKAGE_ISP)));
            return true;
        } else if (PaymentScheme.BANKPAY.equalsIgnoreCase(scheme)) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PaymentScheme.PACKAGE_BANKPAY)));
            return true;
        }

        return false;
    }
}
