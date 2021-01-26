package com.example.android.Activites.UserAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.DTOS.UserInfoDTO;
import com.example.android.DAOS.LoginInfo;
import com.example.android.QR.QRCodeAcitivty;
import com.example.android.R;
import com.example.android.Retrofit.RetrofitClient;
import com.example.android.Services.Services;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputPasswordAcitivty extends AppCompatActivity {
    Button button1;
    EditText editText;
    ImageView imageview;
    TextView textView;

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


        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String textpw = editText.getText().toString();
                Intent intent = getIntent();
                String email = intent.getExtras().getString("email1");
                Services retrofitAPI2 = RetrofitClient.getRetrofit().create(Services.class);
                UserInfoDTO userInfo = new UserInfoDTO(email,textpw);
                Call<LoginInfo> signinCall = retrofitAPI2.requestSignin(userInfo);
                signinCall.enqueue(new Callback<LoginInfo>() {
                    @Override
                    public void onResponse(Call<LoginInfo> call, Response<LoginInfo> response) {
                        switch (response.code()) {
                            case 200:
                                Toast.makeText(InputPasswordAcitivty.this, "정상적으로 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(InputPasswordAcitivty.this, QRCodeAcitivty.class);
                                startActivity(intent);
                                finish();
                                break;

                            case 403:
                                Toast.makeText(InputPasswordAcitivty.this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                                break;


                            case 428:
                                Toast.makeText(InputPasswordAcitivty.this, "비밀번호를 변경해야 합니다.", Toast.LENGTH_SHORT).show();
                                Intent intent2 = new Intent(InputPasswordAcitivty.this, QRCodeAcitivty.class);
                                startActivity(intent2);
                                finish();
                                break;

                        }
                    }

                    @Override
                    public void onFailure(Call<LoginInfo> call, Throwable t) {
                        Log.e("##########","Fail",t);
                    }
                });
            }
        });

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InputPasswordAcitivty.this, LoginActivity.class));
            }
        });

    }
}