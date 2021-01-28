package com.example.android.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.android.Activites.Barcode.QRCodeAcitivty;
import com.example.android.Activites.UserAccount.InputPasswordActivity;
import com.example.android.Activites.UserAccount.LoginActivity;
import com.example.android.Activites.UserAccount.SignUpActivity;
import com.example.android.R;
import com.example.android.Retrofit.RetrofitClient;
import com.example.android.Services.Services;
import com.example.android.Utils.Preference.PreferenceManager;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 1000); // 1초 후에 hd handler 실행  3000ms = 3초
    }

    private class splashhandler implements Runnable{
        public void run() {
            String accessToken = PreferenceManager.getString(getApplicationContext(), "accessToken");
            if(!accessToken.equals("")) {
                verifyToken(accessToken);
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        }
    }

    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }

    private void verifyToken(String accessToken) {
        Services retrofitAPI = RetrofitClient.getRetrofit(accessToken).create(Services.class);
        Call<ResponseBody> verifyTokenCall = retrofitAPI.verifyToken();
        verifyTokenCall.enqueue(new Callback<ResponseBody>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    switch (response.code()) {
                        case 200:
                            if(response.body().string().equals("true")) {
                                startActivity(new Intent(SplashActivity.this, QRCodeAcitivty.class));
                            } else {
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            }
                            break;
                        case 404:
                            Toast.makeText(getApplicationContext(), "잘못된 요청입니다", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "서버 내부 오류입니다", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "통신 에러입니다", Toast.LENGTH_SHORT).show();
            }
        });
    }
}