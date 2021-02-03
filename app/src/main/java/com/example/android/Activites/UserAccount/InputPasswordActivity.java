package com.example.android.Activites.UserAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.android.Activites.Barcode.QRCodeActivity;
import com.example.android.DTOS.AccessTokenDTO;
import com.example.android.DTOS.ChangePasswordDTO;
import com.example.android.DTOS.UserInfoDTO;

import com.example.android.Dialog.CustomDialogClickListener;
import com.example.android.Dialog.OptionCodeTypeDialog;
import com.example.android.R;
import com.example.android.Retrofit.RetrofitClient;
import com.example.android.Services.Services;
import com.example.android.Utils.Preference.PreferenceManager;
import com.example.android.databinding.ActivityInputPwBinding;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputPasswordActivity extends AppCompatActivity {
    private ActivityInputPwBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInputPwBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        settings();
        onBtnEvent();

    }
    private void settings(){
        binding.loginButton2.setBackgroundColor(getResources().getColor(R.color.colorYellow));
        binding.loginButton2.setTextColor(getResources().getColor(R.color.colorBlack));
    }

    private void onBtnEvent() {
        binding.loginButton2.setOnClickListener(onClickListener);
        binding.inputPwBack.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_button2:
                    String textpw = binding.loginPw.getText().toString();
                    Intent intent = getIntent();
                    String email = intent.getExtras().getString("email1");
                    Services retrofitAPI2 = RetrofitClient.getRetrofit(null).create(Services.class);
                    UserInfoDTO userInfo = new UserInfoDTO(email, textpw);

                    Call<AccessTokenDTO> signinCall = retrofitAPI2.requestSignin(userInfo);
                    signinCall.enqueue(new Callback<AccessTokenDTO>() {
                        @Override
                        public void onResponse(@NotNull Call<AccessTokenDTO> call, @NotNull Response<AccessTokenDTO> response) {
                            switch (response.code()) {
                                case 200:
                                    assert response.body() != null;
                                    PreferenceManager.setString(getApplicationContext(), "accessToken", response.body().getAccessToken());
                                    Toast.makeText(InputPasswordActivity.this, "정상적으로 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(InputPasswordActivity.this, QRCodeActivity.class);
                                    startActivity(intent);
                                    finish();
                                    break;

                                case 400:
                                    Toast.makeText(InputPasswordActivity.this, "유효한 입력값이 아닙니다..", Toast.LENGTH_LONG).show();
                                    break;

                                case 401:
                                    Toast.makeText(InputPasswordActivity.this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                                    break;

                                case 428:
                                    Toast.makeText(InputPasswordActivity.this, "비밀번호를 변경해야 합니다.", Toast.LENGTH_SHORT).show();

                                    OptionCodeTypeDialog octDialog = new OptionCodeTypeDialog(InputPasswordActivity.this, new CustomDialogClickListener() {
                                        @Override
                                        public void onPositiveClick(String pw1, String pw2) {
                                            Services retrofitAPI2 = RetrofitClient.getRetrofit(null).create(Services.class);
                                            ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO(pw1);
                                            Call<ChangePasswordDTO> newpassCall = retrofitAPI2.requestchange(changePasswordDTO);

                                            newpassCall.enqueue(new Callback<ChangePasswordDTO>() {
                                                @Override
                                                public void onResponse(@NotNull Call<ChangePasswordDTO> call, @NotNull Response<ChangePasswordDTO> response) {
                                                    switch (response.code()) {
                                                        case 200:
                                                            Toast.makeText(InputPasswordActivity.this, "OK", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(InputPasswordActivity.this, LoginActivity.class);
                                                            startActivity(intent);
                                                            finish();
                                                            break;

                                                        case 201:
                                                            Toast.makeText(InputPasswordActivity.this, "정상적으로 비밀번호를 변경, 새로운 토큰을 발급", Toast.LENGTH_LONG).show();
                                                            Intent intent2 = new Intent(InputPasswordActivity.this, LoginActivity.class);
                                                            startActivity(intent2);
                                                            break;

                                                        case 400:
                                                            Toast.makeText(InputPasswordActivity.this, "유효한 입력값이 아닙니다.", Toast.LENGTH_LONG).show();
                                                            break;

                                                        case 401:
                                                            Toast.makeText(InputPasswordActivity.this, "토큰 만료로 인해 비밀번호 변경 불가 -> 새로운 토큰 발급", Toast.LENGTH_LONG).show();
                                                            break;

                                                        case 403:
                                                            Toast.makeText(InputPasswordActivity.this, "유저만 접근 가능", Toast.LENGTH_LONG).show();
                                                            break;
                                                    }
                                                }

                                                @Override
                                                public void onFailure(@NotNull Call<ChangePasswordDTO> call, @NotNull Throwable t) {
                                                    Log.e("##########", "Fail", t);
                                                }
                                            });
                                        }

                                        @Override
                                        public void onNegativeClick() {
                                            Intent intent = new Intent(InputPasswordActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    });

                                    Display display = getWindowManager().getDefaultDisplay();  // in Activity
                                    Point size = new Point();
                                    display.getRealSize(size); // or getSize(size)
                                    int width = (int) (size.x * 0.9444f);
                                    int height = (int) (size.y * 0.46f);
                                    octDialog.setCanceledOnTouchOutside(true);
                                    octDialog.setCancelable(true);

                                    octDialog.show();
                                    Window window = octDialog.getWindow();
                                    window.setLayout(width, height);
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<AccessTokenDTO> call, @NotNull Throwable t) {

                        }
                    });
                    break;
                case R.id.input_pw_back:
                    startActivity(new Intent(InputPasswordActivity.this, LoginActivity.class));
                    break;
            }
        }
    };
}
