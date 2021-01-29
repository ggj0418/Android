package com.example.android.Activites.FindingAccount;


import android.annotation.SuppressLint;
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

import com.example.android.Activites.UserAccount.InputPasswordActivity;
import com.example.android.Activites.UserAccount.LoginActivity;
import com.example.android.Activites.UserAccount.SignUpActivity;
import com.example.android.DTOS.FindingEmailDTO;
import com.example.android.R;
import com.example.android.Retrofit.RetrofitClient;
import com.example.android.Services.Services;
import com.example.android.databinding.ActivityFindEmailBinding;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FindEmailActivity extends AppCompatActivity {
    private ActivityFindEmailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityFindEmailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreate(savedInstanceState);
        setContentView(view);
        textEvent();
        onBtnEvent();
    }

    private void textEvent() {
        binding.findEmailPhone.addTextChangedListener(textWatcher01);
    }

    private final TextWatcher textWatcher01 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String edit2 = binding.findEmailPhone.getText().toString();
            String edit1 = binding.findEmailName.getText().toString();
            if (edit2.length() > 10) {
                binding.findEmailButton1.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                binding.findEmailButton1.setTextColor(getResources().getColor(R.color.colorBlack));
                binding.findEmailButton1.setEnabled(true);
            } else {
                binding.findEmailButton1.setEnabled(false);
                binding.findEmailButton1.setBackgroundColor(getResources().getColor(R.color.ButtonGray));
                binding.findEmailButton1.setTextColor(getResources().getColor(R.color.TextGray));
            }
            if (edit1.equals("")) {
                binding.findEmailButton1.setEnabled(false);
                binding.findEmailButton1.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.findEmailButton1.setTextColor(getResources().getColor(R.color.TextColor1));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void onBtnEvent() {
        binding.findEmailButton1.setOnClickListener(onClickListener);
        binding.findEmailImage.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.find_email_button1: {
                    String edit1 = binding.findEmailName.getText().toString();
                    String edit2 = binding.findEmailPhone.getText().toString();
                    Services retrofitAPI = RetrofitClient.getRetrofit(null).create(Services.class);
                    FindingEmailDTO userInfo = new FindingEmailDTO(edit1, edit2);
                    Call<ResponseBody> loginCall = retrofitAPI.requestfindemail(userInfo);
                    loginCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                            switch (response.code()) {
                                case 200:
                                    Toast.makeText(FindEmailActivity.this, "OK", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(FindEmailActivity.this, GetEmailActivity.class);
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

                                case 400:
                                    Toast.makeText(FindEmailActivity.this, "유효한 입력값이 아닙니다.", Toast.LENGTH_LONG).show();
                                    break;

                                case 404:
                                    Toast.makeText(FindEmailActivity.this, "일치하는 회원이 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                            Log.e("##########", "Fail", t);
                        }
                    });
                }
                break;
                case R.id.find_email_image:
                    Intent intent = new Intent(FindEmailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
    }
