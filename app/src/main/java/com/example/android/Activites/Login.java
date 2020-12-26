package com.example.android.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.android.R;

public class Login extends AppCompatActivity {
    EditText editText;
    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        editText = findViewById(R.id.login_edit1);
        button = findViewById(R.id.login_button1);
        textView = findViewById(R.id.login_text2);
        button.setEnabled(false);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String email = editText.getText().toString();
                String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
                Boolean b = email.matches(regex);
                if(b==true){
                    textView.setText("올바른 형식입니다.");
                    textView.setTextColor(getResources().getColor(R.color.TextGray));
                    button.setEnabled(true);
                    button.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                    button.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                else if(b==false){
                    textView.setText("올바르지 않은 형식입니다.");
                    textView.setTextColor(getResources().getColor(R.color.colormiss));
                    button.setEnabled(false);
                    button.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    button.setTextColor(getResources().getColor(R.color.TextColor1));
                }
                  if(email.equals("")||email == null)
                {
                    textView.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                        if (editText.getText().toString().equals("gd0541234@gmail.com")) {
                            Intent intent = new Intent(Login.this, Input_pw.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(Login.this, SignUp.class);
                            intent.putExtra("eamil", editText.getText().toString());
                            startActivity(intent);
                        }
                    }
        });
    }
}
