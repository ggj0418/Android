package com.example.android.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.R;

public class GetEmail extends AppCompatActivity {
    TextView textView1,textView2,textView3;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_email);

        textView1 = findViewById(R.id.get_email_text1);
        textView2 = findViewById(R.id.get_email_text2);
        textView3 = findViewById(R.id.get_email_text3);
        button = findViewById(R.id.get_email_button);
        button.setBackgroundColor(getResources().getColor(R.color.colorYellow));
        button.setTextColor(getResources().getColor(R.color.colorBlack));

        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        String email = intent.getExtras().getString("find_email");

        textView1.setText(name+"님의 이메일은");
        textView2.setText(email+"입니다.");
        textView3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GetEmail.this,FindPw.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GetEmail.this, Login.class);
                startActivity(intent);
            }
        });
    }
}