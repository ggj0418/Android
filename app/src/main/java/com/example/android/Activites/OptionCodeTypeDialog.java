package com.example.android.Activites;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.android.R;

public class OptionCodeTypeDialog extends Dialog {

    Context context;
    CustomDialogClickListener customDialogClickListener;
    TextView negativetext,textView1,textView2;
    Button positivebutton;
    EditText edit1,edit2;


    public OptionCodeTypeDialog(@NonNull Context context, CustomDialogClickListener customDialogClickListener) {
        super(context);
        this.context = context;
        this.customDialogClickListener = customDialogClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_codetype_dialog);

        positivebutton = findViewById(R.id.dia_button1);
        negativetext = findViewById(R.id.dia_negative);
        edit1 = findViewById(R.id.dia_pw1);
        edit2 = findViewById(R.id.dia_pw2);
        textView1 = findViewById(R.id.dia_text1);
        textView2 = findViewById(R.id.dia_text2);

        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String Signup_pw = edit1.getText().toString();
                String regex = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{8,15}.$";
                Boolean c = Signup_pw.matches(regex);
                if(c==true){
                    textView1.setText("올바른 형식입니다.");
                    textView1.setTextColor(context.getResources().getColor(R.color.TextGray));

                    edit2.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String Signup_pw1 = edit1.getText().toString();
                            String Signup_pw2 = edit2.getText().toString();
                            if(Signup_pw2.equals(Signup_pw1)&&Signup_pw2!=null){
                                textView2.setText("비밀번호가 일치합니다");
                                textView2.setTextColor(context.getResources().getColor(R.color.TextGray));
                                positivebutton.setEnabled(true);
                                positivebutton.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
                                positivebutton.setTextColor(context.getResources().getColor(R.color.colorBlack));
                            }

                            else{
                                textView2.setText("비밀번호가 일치하지 않습니다.");
                                textView2.setTextColor(context.getResources().getColor(R.color.colormiss));
                                positivebutton.setEnabled(false);
                                positivebutton.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
                                positivebutton.setTextColor(context.getResources().getColor(R.color.TextColor1));
                            }
                            if(Signup_pw2.equals("")||Signup_pw2 == null){
                                textView2.setText("");
                                positivebutton.setEnabled(false);
                            }
                            if(Signup_pw1.equals("")||Signup_pw1 == null){
                                textView2.setText("");
                                positivebutton.setEnabled(false);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else if(c==false){
                    textView1.setText("올바르지 않은 형식입니다.");
                    textView1.setTextColor(context.getResources().getColor(R.color.colormiss));
                }
                if (Signup_pw.equals("")||Signup_pw == null){
                    textView1.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        positivebutton.setOnClickListener(v -> {
            String edittext1 = edit1.getText().toString();
            String edittext2 = edit2.getText().toString();
            this.customDialogClickListener.onPositiveClick(edittext1,edittext2);
            dismiss();
        });
        negativetext.setOnClickListener(v -> {
            // 취소버튼 클릭
            this.customDialogClickListener.onNegativeClick();
            dismiss();
        });
    }
}