package com.example.android.Activites;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.DAOS.ResponseBody2;
import com.example.android.DTOS.Dto_findemail;
import com.example.android.R;
import com.example.android.Retrofit.RetrofitClient;
import com.example.android.Services.Services;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FindEmail extends AppCompatActivity {
    ImageView imageView;
    EditText editText1,editText2;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_email);

        imageView = findViewById(R.id.find_email_image);
        editText1 = findViewById(R.id.find_email_name);
        editText2 = findViewById(R.id.find_email_phone);
        button = findViewById(R.id.find_email_button1);

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String edit2 = editText2.getText().toString();
                String edit1 = editText1.getText().toString();
                if (edit2.length() > 10) {
                    button.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                    button.setTextColor(getResources().getColor(R.color.colorBlack));
                    button.setEnabled(true);
                }
                if (edit2.length() <= 10) {
                    button.setEnabled(false);
                    button.setBackgroundColor(getResources().getColor(R.color.ButtonGray));
                    button.setTextColor(getResources().getColor(R.color.TextGray));
                }
                if(edit1.equals("")||edit1 == null){
                    button.setEnabled(false);
                    button.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    button.setTextColor(getResources().getColor(R.color.TextColor1));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String edit1 = editText1.getText().toString();
                    String edit2 = editText2.getText().toString();
                    Services retrofitAPI = RetrofitClient.getRetrofit().create(Services.class);
                    Dto_findemail userInfo = new Dto_findemail(edit1,edit2);
                    Call<ResponseBody> loginCall = retrofitAPI.requestfindemail(userInfo);
                    loginCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            switch (response.code()) {
                                case 200:
                                    Toast.makeText(FindEmail.this, "OK", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(FindEmail.this, GetEmail.class);
                                    String email = null;
                                    try {
                                        email = response.body().string();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    intent.putExtra("name", edit1);
                                    intent.putExtra("find_email", email);
                                    startActivity(intent);
                                    break;

                                case 302:
                                    Toast.makeText(FindEmail.this, "일치하는 회원이 존재합니다.", Toast.LENGTH_LONG).show();
                                    break;

                                case 403:
                                    Toast.makeText(FindEmail.this, "일치하는 회원이 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("##########", "Fail", t);
                        }
                    });

                }
            });


        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(FindEmail.this, Login.class);
                startActivity(intent);
            }
        });
    }
}
