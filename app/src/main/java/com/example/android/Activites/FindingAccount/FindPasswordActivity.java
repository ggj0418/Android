package com.example.android.Activites.FindingAccount;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.Activites.UserAccount.SignUpActivity;
import com.example.android.DTOS.FindingPasswordDTO;
import com.example.android.DTOS.NewPasswordDTO;
import com.example.android.DTOS.SignupMessageDTO;
import com.example.android.R;
import com.example.android.Retrofit.RetrofitClient;
import com.example.android.Services.Services;
import com.example.android.Utils.Preference.PreferenceManager;
import com.example.android.databinding.ActivityFindPwBinding;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class FindPasswordActivity extends AppCompatActivity {
    private static final int MILLISINFUTURE = 181 * 1000;
    private static final int COUNT_DOWN_INTERVAL = 1000;
    private CountDownTimer countDownTimer;
    String confirm_number2 = null;
    Boolean isSemiPassword = false;
    private ActivityFindPwBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFindPwBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        settings();
        textEvent();
        onBtnEvent();
    }


    private void settings() {
        binding.findPwButton1.setEnabled(false);
        binding.findPwNumber.setVisibility(View.INVISIBLE);
        binding.findPwTextview1.setVisibility(View.INVISIBLE);
    }

    private void textEvent() {
        binding.findPwName.addTextChangedListener(textWatcher01);
        binding.findPwPhonenumber.addTextChangedListener(textWatcher02);
        binding.findPwNumber.addTextChangedListener(textWatcher03);
    }

    private final TextWatcher textWatcher01 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String edit2 = binding.findPwPhonenumber.getText().toString();
            String edit1 = binding.findPwName.getText().toString();
            if (edit2.length() <= 10 || edit1.equals("")) {
                binding.findPwButton1.setEnabled(false);
                binding.findPwButton1.setBackgroundColor(getResources().getColor(R.color.ButtonGray));
                binding.findPwButton1.setTextColor(getResources().getColor(R.color.TextGray));

            } else {
                binding.findPwButton1.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                binding.findPwButton1.setTextColor(getResources().getColor(R.color.colorBlack));
                binding.findPwButton1.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private final TextWatcher textWatcher02 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String edit2 = binding.findPwPhonenumber.getText().toString();
            String edit1 = binding.findPwName.getText().toString();
            if (edit2.length() <= 10 || edit1.equals("")) {
                binding.findPwButton1.setEnabled(false);
                binding.findPwButton1.setBackgroundColor(getResources().getColor(R.color.ButtonGray));
                binding.findPwButton1.setTextColor(getResources().getColor(R.color.TextGray));
            } else {
                binding.findPwButton1.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                binding.findPwButton1.setTextColor(getResources().getColor(R.color.colorBlack));
                binding.findPwButton1.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private final TextWatcher textWatcher03 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String edit3 = binding.findPwNumber.getText().toString();
            if (edit3.equals(confirm_number2)) {
                binding.findPwButton2.setEnabled(true);
                binding.findPwButton2.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                binding.findPwButton2.setTextColor(getResources().getColor(R.color.colorBlack));
            } else {
                binding.findPwButton2.setEnabled(false);
                binding.findPwButton2.setBackgroundColor(getResources().getColor(R.color.ButtonGray));
                binding.findPwButton2.setTextColor(getResources().getColor(R.color.TextGray));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    void onBtnEvent() {
        binding.findPwButton1.setOnClickListener(onClickListener);
        binding.findPwButton2.setOnClickListener(onClickListener);
        binding.findPwImage.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.find_pw_button1:
                    binding.findPwNumber.setVisibility(View.VISIBLE);
                    binding.findPwTextview1.setVisibility(View.VISIBLE);
                    countDownTimer();
                    countDownTimer.start();
                    String phone_no = binding.findPwPhonenumber.getText().toString();
                    Services retrofitAPI2 = RetrofitClient.getRetrofit(null).create(Services.class);
                    FindingPasswordDTO findingPasswordDTO = new FindingPasswordDTO(phone_no);
                    Call<ResponseBody> valid_phone = retrofitAPI2.requestfindpassword(findingPasswordDTO);
                    valid_phone.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                            switch (response.code()) {
                                case 200:
                                    Toast.makeText(FindPasswordActivity.this, "전송된 인증번호 반환", Toast.LENGTH_SHORT).show();
                                    try {
                                        confirm_number2 = response.body().string();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    break;

                                case 400:
                                    Toast.makeText(FindPasswordActivity.this, "유효한 입력값이 아닙니다.", Toast.LENGTH_SHORT).show();
                                    break;

                                case 500:
                                    Toast.makeText(FindPasswordActivity.this, "메세지 전송 실패", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                        }
                    });
                    break;
                case R.id.find_pw_button2:
                    String edit1 = binding.findPwName.getText().toString();
                    String edit2 = binding.findPwPhonenumber.getText().toString();
                    Services retrofitAPI = RetrofitClient.getRetrofit(null).create(Services.class);
                    NewPasswordDTO userInfo = new NewPasswordDTO(edit1, edit2);
                    Call<ResponseBody> loginCall = retrofitAPI.requestnewpassword(userInfo);
                    loginCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                            switch (response.code()) {
                                case 200:
                                    Toast.makeText(FindPasswordActivity.this, "새 비밀번호 발급 후 전송 완료", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(FindPasswordActivity.this, GetPasswrodActivity.class);
                                    isSemiPassword = true;
                                    PreferenceManager.setBoolean(getApplicationContext(), "semipassword", isSemiPassword);
//                                SharedPreferences pref = getSharedPreferences("key", MODE_PRIVATE);
//                                SharedPreferences.Editor edit = pref.edit();
//                                edit.putBoolean("semipassword", isSemiPassword);
//                                edit.apply();
                                    intent.putExtra("name3", edit1);
                                    startActivity(intent);
                                    break;

                                case 400:
                                    Toast.makeText(FindPasswordActivity.this, "유효한 입력값이 아닙니다.", Toast.LENGTH_LONG).show();
                                    break;

                                case 404:
                                    Toast.makeText(FindPasswordActivity.this, "일치하는 회원이 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                                    break;

                                case 500:
                                    Toast.makeText(FindPasswordActivity.this, "메세지 전송 실패", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                            Log.e("##########", "Fail", t);
                        }
                    });

                    break;
                case R.id.find_pw_image:
                    Intent intent = new Intent(FindPasswordActivity.this, SignUpActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    public void countDownTimer() {
        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                long emailAuthCount = millisUntilFinished / 1000;
                if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10) {//초가 10보다 크면 그냥 출력
                    binding.findPwTimer.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                } else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                    binding.findPwTimer.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }
            }

            public void onFinish() {
                binding.findPwTimer.setText("");
                binding.findPwButton2.setEnabled(false);
                binding.findPwButton2.setBackgroundColor(getResources().getColor(R.color.ButtonGray));
                binding.findPwButton2.setTextColor(getResources().getColor(R.color.TextGray));
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            countDownTimer.cancel();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        countDownTimer = null;
    }
}
