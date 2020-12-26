package com.example.android.Okhttp_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.R;

public class LoginActivity extends AppCompatActivity {

    public static EditText id;
    public static EditText pw;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = findViewById(R.id.login_id);
        pw = findViewById(R.id.login_pw);
        button = findViewById(R.id.sign_in);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestActivity.sendData(); // 웹 서버로 데이터 전송
            }
        });
    }
}

