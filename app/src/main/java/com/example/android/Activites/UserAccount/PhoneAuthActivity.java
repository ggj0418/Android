package com.example.android.Activites.UserAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import com.example.android.Activites.FindingAccount.FindEmailActivity;
import com.example.android.Activites.FindingAccount.FindPasswordActivity;
import com.example.android.DTOS.SignupDTO;
import com.example.android.DTOS.SignupMessageDTO;
import com.example.android.R;
import com.example.android.Retrofit.RetrofitClient;
import com.example.android.Services.Services;
import com.example.android.databinding.ActivityPhoneauthBinding;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneAuthActivity extends AppCompatActivity {
    private static final int MILLISINFUTURE = 181 * 1000;
    private static final int COUNT_DOWN_INTERVAL = 1000;
    private CountDownTimer countDownTimer;
    private String confirm_number1 = null;
    private ActivityPhoneauthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityPhoneauthBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreate(savedInstanceState);
        setContentView(view);

        binding.confirmButton2.setEnabled(false);
        binding.confirmNumber.setVisibility(View.INVISIBLE);
        binding.confirmTextview1.setVisibility(View.INVISIBLE);
        onBtnEvent();
        textEvent();
    }

    private void textEvent() {
        binding.confirmNumber.addTextChangedListener(textWatcher01);
        binding.confirmPNumber.addTextChangedListener(textWatcher02);
        binding.confirmName.addTextChangedListener(textWatcher03);
    }

    private final TextWatcher textWatcher01 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String edit3 = binding.confirmNumber.getText().toString();
            if (edit3.equals(confirm_number1)) {
                binding.confirmButton2.setEnabled(true);
                binding.confirmButton2.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                binding.confirmButton2.setTextColor(getResources().getColor(R.color.colorBlack));
            } else {
                binding.confirmButton2.setEnabled(false);
                binding.confirmButton2.setBackgroundColor(getResources().getColor(R.color.ButtonGray));
                binding.confirmButton2.setTextColor(getResources().getColor(R.color.TextGray));
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
            String edit2 = binding.confirmPNumber.getText().toString();
            String edit1 = binding.confirmName.getText().toString();
            if (edit2.length() > 10) {
                binding.confirmButton1.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                binding.confirmButton1.setTextColor(getResources().getColor(R.color.colorBlack));
                binding.confirmButton1.setEnabled(true);
            }
            if (edit2.length() <= 10) {
                binding.confirmButton1.setEnabled(false);
                binding.confirmButton1.setBackgroundColor(getResources().getColor(R.color.ButtonGray));
                binding.confirmButton1.setTextColor(getResources().getColor(R.color.TextGray));
            }
            if (edit1.equals("")) {
                binding.confirmButton1.setEnabled(false);
                binding.confirmButton1.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.confirmButton1.setTextColor(getResources().getColor(R.color.TextColor1));
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
            String edit2 = binding.confirmPNumber.getText().toString();
            String edit1 = binding.confirmName.getText().toString();
            if (edit2.length() > 10) {
                binding.confirmButton1.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                binding.confirmButton1.setTextColor(getResources().getColor(R.color.colorBlack));
                binding.confirmButton1.setEnabled(true);
            }
            if (edit2.length() <= 10) {
                binding.confirmButton1.setEnabled(false);
                binding.confirmButton1.setBackgroundColor(getResources().getColor(R.color.ButtonGray));
                binding.confirmButton1.setTextColor(getResources().getColor(R.color.TextGray));
            }
            if (edit1.equals("")) {
                binding.confirmButton1.setEnabled(false);
                binding.confirmButton1.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.confirmButton1.setTextColor(getResources().getColor(R.color.TextColor1));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void onBtnEvent() {
        binding.confirmButton1.setOnClickListener(onClickListener);
        binding.confirmButton2.setOnClickListener(onClickListener);
        binding.confirmImage.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.confirm_button1:
                    binding.confirmNumber.setVisibility(View.VISIBLE);
                    binding.confirmTextview1.setVisibility(View.VISIBLE);
                    countDownTimer();
                    countDownTimer.start();
                    String phone_no = binding.confirmPNumber.getText().toString();
                    Services retrofitAPI2 = RetrofitClient.getRetrofit(null).create(Services.class);
                    SignupMessageDTO signupMessageDTO = new SignupMessageDTO(phone_no);
                    Call<ResponseBody> valid_phone = retrofitAPI2.requestphone(signupMessageDTO);
                    valid_phone.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                            switch (response.code()) {
                                case 200:
                                    Toast.makeText(PhoneAuthActivity.this, "Message sent.", Toast.LENGTH_SHORT).show();
                                    countDownTimer();
                                    countDownTimer.start();
                                    try {
                                        assert response.body() != null;
                                        confirm_number1 = response.body().string();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    break;

                                case 400:
                                    Toast.makeText(PhoneAuthActivity.this, "유효한 입력값이 아닙니다.", Toast.LENGTH_SHORT).show();
                                    break;

                                case 403:
                                    Toast.makeText(PhoneAuthActivity.this, "동일한 휴대폰 번호의 회원이 이미 존재합니다.", Toast.LENGTH_SHORT).show();
                                    break;

                                case 500:
                                    Toast.makeText(PhoneAuthActivity.this, "메세지 전송 실패", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {

                        }
                    });
                    break;

                case R.id.confirm_button2:
                    Intent intent = getIntent();
                    String email = intent.getExtras().getString("email2");
                    String pw = intent.getExtras().getString("pw");
                    String name = binding.confirmName.getText().toString();
                    String p_number = binding.confirmPNumber.getText().toString();
                    Services retrofitAPI3 = RetrofitClient.getRetrofit(null).create(Services.class);
                    SignupDTO signupUserinfo = new SignupDTO(email, name, pw, p_number);
                    Call<ResponseBody> signupCall = retrofitAPI3.requestSignup(signupUserinfo);

                    signupCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                            switch (response.code()) {
                                case 200:
                                    Toast.makeText(PhoneAuthActivity.this, "OK", Toast.LENGTH_SHORT).show();
                                    break;

                                case 201:
                                    Toast.makeText(PhoneAuthActivity.this, "정상적으로 회원가입이 완료되었습니다..", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(PhoneAuthActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    break;

                                case 406:
                                    Toast.makeText(PhoneAuthActivity.this, "이메일 또는 핸드폰번호가 중복되어 회원가입에 실패하였습니다.", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                            Log.e("##########", "Fail", t);
                        }
                    });
                    break;

                case R.id.confirm_image:
                    Intent intent2 = new Intent(PhoneAuthActivity.this, SignUpActivity.class);
                    startActivity(intent2);
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
                    binding.confirmTimer.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                } else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                    binding.confirmTimer.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }
            }

            public void onFinish() {
                binding.confirmTimer.setText(String.valueOf("Finish ."));
                binding.confirmTimer.setText(String.valueOf(""));
                binding.confirmButton2.setEnabled(false);
                binding.confirmButton2.setBackgroundColor(getResources().getColor(R.color.ButtonGray));
                binding.confirmButton2.setTextColor(getResources().getColor(R.color.TextGray));
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            countDownTimer.cancel();
        } catch (Exception ignored) {
        }
        countDownTimer = null;
    }
}
