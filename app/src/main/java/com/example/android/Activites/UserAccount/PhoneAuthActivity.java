package com.example.android.Activites.UserAccount;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.android.DTOS.SignupDTO;
import com.example.android.DAOS.SignupInfo;
import com.example.android.R;
import com.example.android.Retrofit.RetrofitClient;
import com.example.android.Services.Services;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneAuthActivity extends AppCompatActivity {
    private static final int MILLISINFUTURE = 181 * 1000;
    private static final int COUNT_DOWN_INTERVAL = 1000;
    private CountDownTimer countDownTimer;
    EditText editText1, editText2, editText3;
    Button button1, button2;
    TextView text1, text2;
    ImageView imageView;
    String confirm_number1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Intent intent = getIntent();
        String email = intent.getExtras().getString("email2");
        String pw = intent.getExtras().getString("pw");

        editText1 = findViewById(R.id.confirm_name);
        editText2 = findViewById(R.id.confirm_p_number);
        editText3 = findViewById(R.id.confirm_number);
        button1 = findViewById(R.id.confirm_button1);
        button2 = findViewById(R.id.confirm_button2);
        text1 = findViewById(R.id.confirm_timer);
        text2 = findViewById(R.id.confirm_textview1);
        imageView = findViewById(R.id.confirm_image);
        button2.setEnabled(false);
        editText3.setVisibility(View.INVISIBLE);
        text2.setVisibility(View.INVISIBLE);
        String edit1 = editText1.getText().toString();


        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String edit2 = editText2.getText().toString();
                        String edit1 = editText1.getText().toString();
                        if (edit2.length() > 10) {
                            button1.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                            button1.setTextColor(getResources().getColor(R.color.colorBlack));
                            button1.setEnabled(true);
                        }
                        if (edit2.length() <= 10) {
                            button1.setEnabled(false);
                            button1.setBackgroundColor(getResources().getColor(R.color.ButtonGray));
                            button1.setTextColor(getResources().getColor(R.color.TextGray));
                        }
                        if (edit1.equals("") || edit1 == null) {
                            button1.setEnabled(false);
                            button1.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                            button1.setTextColor(getResources().getColor(R.color.TextColor1));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editText3.setVisibility(View.VISIBLE);
                        text2.setVisibility(View.VISIBLE);
                        countDownTimer();
                        countDownTimer.start();
                        String phone_no = editText2.getText().toString();
                        Services retrofitAPI2 = RetrofitClient.getRetrofit().create(Services.class);
                        Call<ResponseBody> valid_phone = retrofitAPI2.requestphone(phone_no);
                        valid_phone.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                switch (response.code()) {
                                    case 200:
                                        Toast.makeText(PhoneAuthActivity.this, "Message sent.", Toast.LENGTH_SHORT).show();
                                        countDownTimer();
                                        countDownTimer.start();
                                        try {
                                            confirm_number1 = response.body().string();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        break;

                                    case 406:
                                        Toast.makeText(PhoneAuthActivity.this, "동일한 휴대폰 번호의 회원이 이미 존재합니다.", Toast.LENGTH_SHORT).show();
                                        break;

                                    case 500:
                                        Toast.makeText(PhoneAuthActivity.this, "메세지 전송 실패", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                });

                editText3.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String edit3 = editText3.getText().toString();
                        if (edit3.equals(confirm_number1)) {
                            button2.setEnabled(true);
                            button2.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                            button2.setTextColor(getResources().getColor(R.color.colorBlack));
                        } else {
                            button2.setEnabled(false);
                            button2.setBackgroundColor(getResources().getColor(R.color.ButtonGray));
                            button2.setTextColor(getResources().getColor(R.color.TextGray));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhoneAuthActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText1.getText().toString();
                String p_number = editText2.getText().toString();
                Services retrofitAPI2 = RetrofitClient.getRetrofit().create(Services.class);
                SignupDTO signupUserinfo = new SignupDTO(email, name, pw, p_number);
                Call<SignupInfo> signupCall = retrofitAPI2.requestSignup(signupUserinfo);

                signupCall.enqueue(new Callback<SignupInfo>() {
                    @Override
                    public void onResponse(Call<SignupInfo> call, Response<SignupInfo> response) {
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
                    public void onFailure(Call<SignupInfo> call, Throwable t) {
                        Log.e("##########", "Fail", t);
                    }
                });
            }
        });
    }

    public void countDownTimer() {
        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            public void onTick(long millisUntilFinished) {
                long emailAuthCount = millisUntilFinished / 1000;
                if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10) {//초가 10보다 크면 그냥 출력
                    text1.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                } else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                    text1.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }
            }

            public void onFinish() {
                text1.setText(String.valueOf("Finish ."));
                text1.setText(String.valueOf(""));
                button2.setEnabled(false);
                button2.setBackgroundColor(getResources().getColor(R.color.ButtonGray));
                button2.setTextColor(getResources().getColor(R.color.TextGray));
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            countDownTimer.cancel();
        } catch (Exception e) {
        }
        countDownTimer = null;
    }

}
