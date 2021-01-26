package com.example.android.Activites.UserAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    TextView textView,textView2,textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        editText = findViewById(R.id.login_edit1);
        button = findViewById(R.id.login_button1);
        textView = findViewById(R.id.login_text2);
        textView2 = findViewById(R.id.login_find_email);
        textView3 = findViewById(R.id.login_find_pw);
        button.setEnabled(false);

        SharedPreferences pref = getSharedPreferences("key", MODE_PRIVATE);
        Boolean str = pref.getBoolean("semipassword",false);
        Log.d("*********",str.toString());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String email = editText.getText().toString();
                String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
                Boolean b = email.matches(regex);
                if (b == true) {
                    textView.setText("올바른 형식입니다.");
                    textView.setTextColor(getResources().getColor(R.color.TextGray));
                    button.setEnabled(true);
                    button.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                    button.setTextColor(getResources().getColor(R.color.colorBlack));
                } else if (b == false) {
                    textView.setText("올바르지 않은 형식입니다.");
                    textView.setTextColor(getResources().getColor(R.color.colormiss));
                    button.setEnabled(false);
                    button.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    button.setTextColor(getResources().getColor(R.color.TextColor1));
                }
                if (email.equals("") || email == null) {
                    textView.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String textemail = editText.getText().toString();
                    Services retrofitAPI = RetrofitClient.getRetrofit().create(Services.class);
                    Call<ResponseBody> loginCall = retrofitAPI.requestEmail(textemail);
                    loginCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            switch (response.code()) {
                                case 200:
                                    Toast.makeText(LoginActivity.this, "이메일이 중복되지 않습니다.", Toast.LENGTH_SHORT).show();
                                  Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                                  intent.putExtra("email1",textemail);
                                  startActivity(intent);
                                    break;
    
                                case 406:
                                    Toast.makeText(LoginActivity.this, "동일한 이메일의 회원이 이미 존재합니다..", Toast.LENGTH_LONG).show();
                                  intent = new Intent(LoginActivity.this, InputPasswordAcitivty.class);
                                  intent.putExtra("email1",textemail);
                                  startActivity(intent);
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

        textView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FindEmailActivity.class);
                startActivity(intent);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
                startActivity(intent);
            }
        });
        }
}
