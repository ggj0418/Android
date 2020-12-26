package com.example.android.Retrofit_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.R;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReLogin extends AppCompatActivity {
    EditText id, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relogin);

        Client retrofitClient = new Client();
        Call<JsonArray> call = retrofitClient.apiService.getretrofitdata();
        SharedPref sharedPref = new SharedPref();

        sharedPref.openSharedPrep(this);
        Button login = findViewById(R.id.res_btn);
        id = findViewById(R.id.res_id);
        pw = findViewById(R.id.res_pw);
        final String id_tv = id.getText().toString();
        final String pw_tv = pw.getText().toString();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Client client = new Client();
                client.apiService.login(id_tv, pw_tv).enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        if (response.code() == 200) {
                            SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("username", id_tv);
                            editor.commit();
                            finish();
                            Intent intent = new Intent(ReLogin.this, LoginConfirm.class);
                            startActivity(intent);
                        } else if (response.code() == 405) {
                            Toast.makeText(ReLogin.this, "로그인 실패 : 아이디나 비번이 올바르지 않습니다", Toast.LENGTH_LONG).show();
                        } else if (response.code() == 500) {
                            Toast.makeText(ReLogin.this, "로그인 실패 : 서버오류", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ReLogin.this, "로그인 실패 : 알수없는 오류", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {
                    }
                });
            }
        });

        TextView sign_up = findViewById(R.id.signup_go);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReLogin.this, ReSignUp.class);
                startActivity(intent);
            }
        });
    }


}