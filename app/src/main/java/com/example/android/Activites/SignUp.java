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

public class SignUp extends AppCompatActivity {
    EditText editText1,editText2;
    Button button;
    ImageView imageview;
    TextView textView1,textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editText1 = findViewById(R.id.signup_pw1);
        editText2 = findViewById(R.id.signup_pw2);
        button = findViewById(R.id.signup_button1);
        imageview = findViewById(R.id.sign_image);
        textView1 = findViewById(R.id.signup_text1);
        textView2 = findViewById(R.id.signup_text2);
        button.setEnabled(false);

        Intent intent = getIntent();
        String email = intent.getExtras().getString("email");

        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String Signup_pw = editText1.getText().toString();
                String regex = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{8,15}.$";
                Boolean c = Signup_pw.matches(regex);
                if(c==true){
                    textView1.setText("올바른 형식입니다.");
                    textView1.setTextColor(getResources().getColor(R.color.TextGray));


                    editText2.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String Signup_pw1 = editText1.getText().toString();
                            String Signup_pw2 = editText2.getText().toString();
                            if(Signup_pw2.equals(Signup_pw1)&&Signup_pw2!=null){
                                textView2.setText("비밀번호가 일치합니다");
                                textView2.setTextColor(getResources().getColor(R.color.TextGray));
                                button.setEnabled(true);
                                button.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                                button.setTextColor(getResources().getColor(R.color.colorBlack));
                            }

                            else{
                                textView2.setText("비밀번호가 일치하지 않습니다.");
                                textView2.setTextColor(getResources().getColor(R.color.colormiss));
                                button.setEnabled(false);
                                button.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                button.setTextColor(getResources().getColor(R.color.TextColor1));
                            }
                            if(Signup_pw2.equals("")||Signup_pw2 == null){
                                textView2.setText("");
                                button.setEnabled(false);
                            }
                            if(Signup_pw1.equals("")||Signup_pw1 == null){
                                textView2.setText("");
                                button.setEnabled(false);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else if(c==false){
                    textView1.setText("올바르지 않은 형식입니다.");
                    textView1.setTextColor(getResources().getColor(R.color.colormiss));
                }
                if (Signup_pw.equals("")||Signup_pw == null){
                    textView1.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {



            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    String pw1 = editText2.getText().toString();
                    Intent intent = new Intent(SignUp.this, Confirmation.class);
                    intent.putExtra("email", email);
                    intent.putExtra("pw",pw1);
                    startActivity(intent);
                }
        });

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(SignUp.this, Login.class);
               startActivity(intent);
            }
        });


    }
}