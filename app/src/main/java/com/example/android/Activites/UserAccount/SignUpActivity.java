package com.example.android.Activites.UserAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.android.R;
import com.example.android.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    Boolean isCheck = false;
    private ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        onBtnEvent();
        textEvent();
        binding.signupButton1.setEnabled(false);
        binding.signupPw2.setEnabled(false);
    }

    private String firstGetIntent() {
        Intent intent = getIntent();
        String email = intent.getExtras().getString("email1");
        return email;
    }

    void onBtnEvent() {
        binding.signupButton1.setOnClickListener(onClickListener);
        binding.signImage.setOnClickListener(onClickListener);
    }

    void textEvent() {
        binding.signupPw1.addTextChangedListener(textWatcher01);
        binding.signupPw2.addTextChangedListener(textWatcher02);
    }

    private final TextWatcher textWatcher01 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String Signup_pw = binding.signupPw1.getText().toString();
            String regex = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{8,15}.$";
            Boolean c = Signup_pw.matches(regex);
            if (c) {
                binding.signupText1.setText("올바른 형식입니다.");
                binding.signupText1.setTextColor(getResources().getColor(R.color.TextGray));
                binding.signupPw2.setEnabled(true);
                isCheck = true;
            } else {
                binding.signupText1.setText("올바르지 않은 형식입니다.");
                binding.signupText1.setTextColor(getResources().getColor(R.color.colormiss));
                binding.signupPw2.setEnabled(false);
                isCheck = false;
            }
            if (Signup_pw.equals("")) {     
                binding.signupText1.setText("");
                binding.signupPw2.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) { }};

    private final TextWatcher textWatcher02 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (isCheck) {
                String Signup_pw1 = binding.signupPw1.getText().toString();
                String Signup_pw2 = binding.signupPw2.getText().toString();
                if (Signup_pw2.equals(Signup_pw1)) {
                    changeText(true);
                } else {
                    changeText(false);
                }
                if (Signup_pw2.equals("")) {
                    binding.signupText2.setText("");
                    binding.signupButton1.setEnabled(false);
                    isCheck = false;
                }
                if (Signup_pw1.equals("")) {
                    binding.signupText2.setText("");
                    binding.signupButton1.setEnabled(false);
                    isCheck = false;
                }
            }}

        @Override
        public void afterTextChanged(Editable s) {}};

    void changeText(Boolean check) {
        if (check==true){
            binding.signupText2.setText("비밀번호가 일치합니다");
            binding.signupText2.setTextColor(getResources().getColor(R.color.TextGray));
            binding.signupButton1.setEnabled(true);
            binding.signupButton1.setBackgroundColor(getResources().getColor(R.color.colorYellow));
            binding.signupButton1.setTextColor(getResources().getColor(R.color.colorBlack));
        } else if(check==false){
            binding.signupText2.setText("비밀번호가 일치하지 않습니다.");
            binding.signupText2.setTextColor(getResources().getColor(R.color.colormiss));
            binding.signupButton1.setEnabled(false);
            binding.signupButton1.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            binding.signupButton1.setTextColor(getResources().getColor(R.color.TextColor1));
        }
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.signup_button1:
                    String pw1 = binding.signupPw2.getText().toString();
                    Intent intent = new Intent(SignUpActivity.this, PhoneAuthActivity.class);
                    intent.putExtra("email2", firstGetIntent());
                    intent.putExtra("pw", pw1);
                    startActivity(intent);
                    break;

                case R.id.sign_image:
                    Intent intent2 = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent2);
                    break;
            }
        }
    };
}