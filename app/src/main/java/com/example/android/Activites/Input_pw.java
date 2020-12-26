package com.example.android.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.R;

public class Input_pw extends AppCompatActivity {
    Button button1,button2;
    EditText editText;
    ImageView imageview;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pw);

        String id = getIntent().getStringExtra("id1");
        editText = findViewById(R.id.login_pw);
        button1 = findViewById(R.id.login_button2);
        imageview = findViewById(R.id.input_pw_back);
        textView = findViewById(R.id.login_text5);
        button1.setEnabled(false);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String login_pw = editText.getText().toString();
                String regex = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{8,15}.$";
                Boolean c = login_pw.matches(regex);
                if(c==true){
                    textView.setText("올바른 형식입니다.");
                    textView.setTextColor(getResources().getColor(R.color.TextGray));
                    button1.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                    button1.setTextColor(getResources().getColor(R.color.colorBlack));
                    button1.setEnabled(true);
                }
                else if(c==false){
                    button1.setEnabled(false);
                    textView.setText("올바르지 않은 형식입니다.");
                    textView.setTextColor(getResources().getColor(R.color.colormiss));
                    button1.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    button1.setTextColor(getResources().getColor(R.color.TextColor1));
                }
                if(login_pw.equals("")||login_pw == null){
                    button1.setEnabled(false);
                    textView.setText("");
                    button1.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    button1.setTextColor(getResources().getColor(R.color.TextColor1));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });



        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String pw = editText.getText().toString();
                if (pw.equals("toby1122")) {
                    Intent intent = new Intent(Input_pw.this, Login.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Input_pw.this,"비밀번호를 확인하세요",Toast.LENGTH_SHORT).show();
                }
                }
        });

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Input_pw.this,Login.class));
            }
        });

    }
}