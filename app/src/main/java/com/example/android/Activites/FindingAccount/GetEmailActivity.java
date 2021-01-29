package com.example.android.Activites.FindingAccount;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.Activites.UserAccount.LoginActivity;
import com.example.android.R;
import com.example.android.databinding.ActivityGetEmailBinding;

public class GetEmailActivity extends AppCompatActivity {

    private ActivityGetEmailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGetEmailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setting();
        onBtnEvent();
    }

    private void  setting(){
        binding.getEmailButton.setBackgroundColor(getResources().getColor(R.color.colorYellow));
        binding.getEmailButton.setTextColor(getResources().getColor(R.color.colorBlack));
        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        String email = intent.getExtras().getString("find_email");
        binding.getEmailText1.setText(name + "님의 이메일은");
        binding.getEmailText2.setText(email + "입니다.");
    }

    private void onBtnEvent() {
        binding.getEmailButton.setOnClickListener(onClickListener);
        binding.getEmailBack.setOnClickListener(onClickListener);
        binding.getEmailText3.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.get_email_button:
                    Intent intent = new Intent(GetEmailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.get_email_text3:
                    Intent intent2 = new Intent(GetEmailActivity.this, FindPasswordActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.get_email_back:
                    Intent intent3 = new Intent(GetEmailActivity.this, FindEmailActivity.class);
                    startActivity(intent3);
                    break;
            }}};
}