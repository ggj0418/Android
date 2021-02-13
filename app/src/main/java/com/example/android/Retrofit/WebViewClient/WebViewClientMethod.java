package com.example.android.Retrofit.WebViewClient;

import android.content.Context;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.android.Activites.Barcode.QRCodeActivity;
import com.example.android.Retrofit.RetrofitClient;
import com.example.android.Services.Services;
import com.example.android.Utils.Preference.PreferenceManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class WebViewClientMethod {
    private final Services retrofitAPI2;
    private final Context mContext;

    public WebViewClientMethod(Context mContext) {
        this.mContext = mContext;
        this.retrofitAPI2 = RetrofitClient.getRetrofit(PreferenceManager.getString(mContext, "accessToken")).create(Services.class);
    }

    public void getHashUid(WebView webview, String merchantUid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("merchant_uid",merchantUid);

        Call<ResponseBody> getHashUidCall = retrofitAPI2.getHashUid(map);
        getHashUidCall.enqueue(new Callback<ResponseBody>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                switch (response.code()) {
                    case 200:
                        try {
                            String hashMerchantUid = response.body().string();
                            String impUrl = "javascript:inicis_script.get_inicis_webview('"
                                    + hashMerchantUid + "',"
                                    + "'card',"
                                    + "'꼬북칩',"
                                    + 20 + ","
                                    + "'ggj0418@naver.com',"
                                    + "'이현준',"
                                    + ")";
                            webview.loadUrl(impUrl);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 401:
                        Toast.makeText(mContext, "토큰 만료", Toast.LENGTH_SHORT).show();
                        break;
                    case 403:
                        Toast.makeText(mContext, "유저만 접근 가능", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(mContext, "서버 내부 에러입니다", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "통신 에러입니다", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
