package com.example.android.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.DTOS.UserInfo;
import com.example.android.DAOS.get_Login;
import com.example.android.R;
import com.example.android.Retrofit.RetrofitClient;
import com.example.android.Services.Services;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Input_pw extends AppCompatActivity {
    Button button1;
    EditText editText;
    ImageView imageview;
    TextView textView;
    String Temporarypassword = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pw);

        editText = findViewById(R.id.login_pw);
        button1 = findViewById(R.id.login_button2);
        imageview = findViewById(R.id.input_pw_back);
        textView = findViewById(R.id.login_text5);
        button1.setBackgroundColor(getResources().getColor(R.color.colorYellow));
        button1.setTextColor(getResources().getColor(R.color.colorBlack));


        Intent intent = getIntent();
        if(Temporarypassword == null || Temporarypassword.trim().isEmpty()) {
        }else{
            Temporarypassword = intent.getExtras().getString("password5");
        }

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String textpw = editText.getText().toString();
                Intent intent = getIntent();
                String email = intent.getExtras().getString("email1");
                Services retrofitAPI2 = RetrofitClient.getRetrofit().create(Services.class);
                UserInfo userInfo = new UserInfo(email,textpw);
                Call<get_Login> signinCall = retrofitAPI2.requestSignin(userInfo);
                signinCall.enqueue(new Callback<get_Login>() {
                    @Override
                    public void onResponse(Call<get_Login> call, Response<get_Login> response) {
                        switch (response.code()) {
                            case 200:
                                Toast.makeText(Input_pw.this, "정상적으로 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Input_pw.this, qractivity.class);
                                startActivity(intent);
                                finish();
                                break;

                            case 403:
                                Toast.makeText(Input_pw.this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                                break;


                            case 428:
                                Toast.makeText(Input_pw.this, "비밀번호를 변경해야 합니다.", Toast.LENGTH_SHORT).show();
                                break;

                        }
                    }

                    @Override
                    public void onFailure(Call<get_Login> call, Throwable t) {
                        Log.e("##########","Fail",t);
                    }
                });
            }
        });

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Input_pw.this, Login.class));
            }
        });

    }
}