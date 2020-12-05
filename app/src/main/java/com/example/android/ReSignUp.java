package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReSignUp extends AppCompatActivity {
    EditText id2,pw2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_sign_up);

        Button login = findViewById(R.id.sign_btn);
        id2 = findViewById(R.id.sign_up_id);
        pw2 = findViewById(R.id.sign_up_pw);
        final String id_tv2 = id2.getText().toString();
        final String pw_tv2 = pw2.getText().toString();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Client client = new Client();
                client.apiService.logup(id_tv2,pw_tv2).enqueue(new Callback<JsonArray>(){
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        if(response.code() == 200){
                            Toast.makeText(ReSignUp.this, "회원가입 성공", Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent(ReSignUp.this, ReLogin.class);
                            startActivity(intent);
                        }
                        else if(response.code()==405){
                            Toast.makeText(ReSignUp.this, "회원가입 실패 : 아이디나 비번이 올바르지 않습니다", Toast.LENGTH_LONG).show();
                        }
                        else if(response.code()==500){
                            Toast.makeText(ReSignUp.this, "회원가입 실패 : 서버오류", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(ReSignUp.this, "회원가입 실패 : 알수없는 오류", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {

                    }
                });
            }
        });
    }
}