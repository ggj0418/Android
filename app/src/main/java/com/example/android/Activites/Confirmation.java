package com.example.android.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.R;

public class Confirmation extends AppCompatActivity {
    private static final int MILLISINFUTURE = 181*1000;
    private static final int COUNT_DOWN_INTERVAL = 1000;
    private CountDownTimer countDownTimer;
    EditText editText1, editText2, editText3;
    Button button1, button2;
    TextView text1, text2;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

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


        Intent intent = getIntent();
        String email = intent.getExtras().getString("email");
        String pw = intent.getExtras().getString("pw");

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
                if(edit1.equals("")||edit1 == null){
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
                            String edit3 = editText3.getText().toString();
                                    countDownTimer();
                                    countDownTimer.start();
                            editText3.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }
                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    if(edit3.length()>4) {
                                        button2.setEnabled(true);
                                        button2.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                                        button2.setTextColor(getResources().getColor(R.color.colorBlack));
                                    }
                                    else{
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
                    });
                }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Confirmation.this, SignUp.class);
                    startActivity(intent);
                }
            });

    }
    public void countDownTimer(){
        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            public void onTick(long millisUntilFinished) {
                long emailAuthCount = millisUntilFinished /1000;
                if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10) {//초가 10보다 크면 그냥 출력
                    text1.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }
                else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                    text1.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }
            }
            public void onFinish() {
                text1.setText(String.valueOf(""));
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            countDownTimer.cancel();
        } catch (Exception e) {}
        countDownTimer=null;
    }
}
