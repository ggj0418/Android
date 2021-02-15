package com.example.android.Activites.UserAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.Activites.FindingAccount.FindEmailActivity;
import com.example.android.Activites.FindingAccount.FindPasswordActivity;
import com.example.android.R;
import com.example.android.Retrofit.RetrofitClient;
import com.example.android.Services.Services;
import com.example.android.SocialLogin.SocialLogin;
import com.example.android.databinding.ActivityLogin2Binding;

import org.jetbrains.annotations.NotNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLogin2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogin2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        textEvent();
        onBtnEvent();
    }

    private void textEvent() {
        binding.loginEdit1.addTextChangedListener(textWatcher01);
    }

    private final TextWatcher textWatcher01 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = binding.loginEdit1.getText().toString();
            String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
            boolean b = email.matches(regex);
            if (b) {
                changeText(b);
            } else {
                changeText(false);
            }
            if (email.equals("")) {
                binding.loginText2.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void changeText(Boolean check) {
        if (check) {
            binding.loginText2.setText("올바른 형식입니다.");
            binding.loginText2.setTextColor(getResources().getColor(R.color.TextGray));
            binding.loginButton1.setEnabled(true);
            binding.loginButton1.setBackgroundColor(getResources().getColor(R.color.colorYellow));
            binding.loginButton1.setTextColor(getResources().getColor(R.color.colorBlack));
        } else {
            binding.loginText2.setText("올바르지 않은 형식입니다.");
            binding.loginText2.setTextColor(getResources().getColor(R.color.colormiss));
            binding.loginButton1.setEnabled(false);
            binding.loginButton1.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            binding.loginButton1.setTextColor(getResources().getColor(R.color.TextColor1));
        }
    }

    private void onBtnEvent() {
        binding.loginButton1.setOnClickListener(onClickListener);
        binding.loginFindPw.setOnClickListener(onClickListener);
        binding.loginFindEmail.setOnClickListener(onClickListener);
        binding.loginNaver.setOnClickListener(onClickListener);
        binding.loginKakao.setOnClickListener(onClickListener);
        binding.loginGoogle.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_button1:
                    String textemail = binding.loginEdit1.getText().toString();
                    Services retrofitAPI = RetrofitClient.getRetrofit(null).create(Services.class);
                    Call<ResponseBody> loginCall = retrofitAPI.requestEmail(textemail);
                    loginCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                            switch (response.code()) {
                                case 200:
                                    Toast.makeText(LoginActivity.this, "이메일이 중복되지 않습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                                    intent.putExtra("email1", textemail);
                                    startActivity(intent);
                                    break;
                                case 400:
                                    Toast.makeText(LoginActivity.this, "유효한 입력값이 아닙니다.", Toast.LENGTH_LONG).show();
                                    break;
                                case 403:
                                    Toast.makeText(LoginActivity.this, "동일한 이메일의 회원이 이미 존재합니다.", Toast.LENGTH_LONG).show();
                                    intent = new Intent(LoginActivity.this, InputPasswordActivity.class);
                                    intent.putExtra("email1", textemail);
                                    startActivity(intent);
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                            Log.e("##########", "Fail", t);
                        }
                    });
                    break;

                case R.id.login_find_email:
                    Intent intent = new Intent(LoginActivity.this, FindEmailActivity.class);
                    startActivity(intent);
                    break;

                case R.id.login_find_pw:
                    Intent intent2 = new Intent(LoginActivity.this, FindPasswordActivity.class);
                    startActivity(intent2);
                    break;

                  case R.id.login_naver:
                    Intent naverintent = new Intent(LoginActivity.this,SocialLogin.class);
                    startActivity(naverintent);
//                    break;

//                case R.id.login_kakao:
//                    Intent kakaointent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.automart.ml/oauth2/authorization/kakao"));
//                    startActivity(kakaointent);
//                    break;
//                case R.id.login_google:
//                    Intent googleintent = new Intent(LoginActivity.this, SocialLogin.class);
//                    startActivity(googleintent);
//                    break;
            }
        }
    };
}
