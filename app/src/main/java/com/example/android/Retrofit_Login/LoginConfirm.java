package com.example.android.Retrofit_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.R;

public class LoginConfirm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_confirm2);

        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
        String users = pref.getString("username","");
        TextView hello = findViewById(R.id.hello);
        hello.setText("환영합니다"+users+"님");
    }
}