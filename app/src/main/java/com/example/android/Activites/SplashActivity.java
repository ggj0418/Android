package com.example.android.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.android.Activites.Barcode.QRCodeAcitivty;
import com.example.android.Activites.UserAccount.LoginActivity;
import com.example.android.R;
import com.example.android.Utils.Preference.PreferenceManager;

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
//            if(!PreferenceManager.getString(getApplicationContext(), "accessToken").equals("")) {
//                startActivity(new Intent(SplashActivity.this, QRCodeAcitivty.class));
//            } else {
//                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//            }
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }
}

