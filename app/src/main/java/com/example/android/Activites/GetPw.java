package com.example.android.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.R;

public class GetPw extends AppCompatActivity {
    TextView textview1,textview2;
    Button button;
    ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_pw);

        textview1 = findViewById(R.id.get_pw_text1);
        button = findViewById(R.id.get_pw_button);
        imageview = findViewById(R.id.get_pw_back);
        textview2 = findViewById(R.id.get_pw_text2);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("name3");
        textview1.setText(name + "님의 핸드폰으로 임시 비밀번호를 발송했습니다.");

        textview2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GetPw.this, FindEmail.class);
                startActivity(intent);
            } 
        });
        button.setBackgroundColor(getResources().getColor(R.color.colorYellow));
        button.setTextColor(getResources().getColor(R.color.colorBlack));

        imageview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GetPw.this, FindPw.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GetPw.this, Login.class);
                startActivity(intent);
            }
        });
    }
    }

