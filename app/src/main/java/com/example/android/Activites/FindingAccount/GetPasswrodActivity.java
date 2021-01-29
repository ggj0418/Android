package com.example.android.Activites.FindingAccount;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.Activites.UserAccount.LoginActivity;
import com.example.android.R;
import com.example.android.databinding.ActivityGetPwBinding;

public class GetPasswrodActivity extends AppCompatActivity {

    private ActivityGetPwBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGetPwBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setting();
        onBtnEvent();
    }

    @SuppressLint("SetTextI18n")
    private void setting(){
        binding.getPwButton.setBackgroundColor(getResources().getColor(R.color.colorYellow));
        binding.getPwButton.setTextColor(getResources().getColor(R.color.colorBlack));
        Intent intent = getIntent();
        String name = intent.getExtras().getString("name3");
        binding.getPwText1.setText(name + "님의 핸드폰으로 임시 비밀번호를 발송했습니다.");
    }

    private void onBtnEvent() {
        binding.getPwButton.setOnClickListener(onClickListener);
        binding.getPwBack.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.get_pw_back:
                    Intent intent = new Intent(GetPasswrodActivity.this, FindPasswordActivity.class);
                    startActivity(intent);
                    break;
                case R.id.get_pw_button:
                    Intent intent2 = new Intent(GetPasswrodActivity.this, LoginActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.get_pw_text2:
                    Intent intent3 = new Intent(GetPasswrodActivity.this, FindEmailActivity.class);
                    startActivity(intent3);
                    break;
            }
        }
    };
}

