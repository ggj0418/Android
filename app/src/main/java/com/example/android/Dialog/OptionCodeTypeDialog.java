package com.example.android.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.android.R;

public class OptionCodeTypeDialog extends Dialog {
    Context context;
    CustomDialogClickListener customDialogClickListener;
    TextView negativetext, textView1, textView2;
    Button positivebutton;
    EditText edit1, edit2;
    Boolean isCheck = false;

    public OptionCodeTypeDialog(@NonNull Context context, CustomDialogClickListener customDialogClickListener) {
        super(context);
        this.context = context;
        this.customDialogClickListener = customDialogClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_codetype_dialog);

        settings();

        positivebutton.setOnClickListener(v -> {
            String edittext1 = edit1.getText().toString();
            String edittext2 = edit2.getText().toString();

            this.customDialogClickListener.onPositiveClick(edittext1, edittext2);
            dismiss();
        });
        negativetext.setOnClickListener(v -> {
            // 취소버튼 클릭
            this.customDialogClickListener.onNegativeClick();
            dismiss();
        });
    }

    private void settings() {
        positivebutton = findViewById(R.id.dia_button1);
        negativetext = findViewById(R.id.dia_negative);
        edit1 = findViewById(R.id.dia_pw1);
        edit2 = findViewById(R.id.dia_pw2);
        textView1 = findViewById(R.id.dia_text1);
        textView2 = findViewById(R.id.dia_text2);
    }

//    private void BtnEvent() {
//        positivebutton.setOnClickListener(onClickListener);
//    }
//
//    private View.OnClickListener onClickListener = new View.OnClickListener() {
//        @SuppressLint("NonConstantResourceId")
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.dia_button1:
//                    String edittext1 = edit1.getText().toString();
//                    String edittext2 = edit2.getText().toString();
//                    this.customDialogClickListener.onPositiveClick(edittext1, edittext2);
//                    dismiss();
//                    break;
//            }
//        }
//    };

    private void textEvent() {
        textView1.addTextChangedListener(textWatcher01);
        textView2.addTextChangedListener(textWatcher02);
    }

    private final TextWatcher textWatcher01 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String Signup_pw = edit1.getText().toString();
            String regex = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{8,15}.$";
            boolean c = Signup_pw.matches(regex);
            if (c) {
                textView1.setText("올바른 형식입니다.");
                textView1.setTextColor(context.getResources().getColor(R.color.TextGray));
                isCheck = true;

            } else if (!c) {
                textView1.setText("올바르지 않은 형식입니다.");
                textView1.setTextColor(context.getResources().getColor(R.color.colormiss));
                isCheck = false;
            }
            if (Signup_pw.equals("")) {
                textView1.setText("");
                isCheck = false;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private final TextWatcher textWatcher02 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String Signup_pw1 = edit1.getText().toString();
            String Signup_pw2 = edit2.getText().toString();
            if (Signup_pw2.equals(Signup_pw1)) {
                changeText(isCheck);
            } else {
                changeText(false);
            }
            if (Signup_pw2.equals("")) {
                textView2.setText("");
                positivebutton.setEnabled(false);
            }
            if (Signup_pw1.equals("")) {
                textView2.setText("");
                positivebutton.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private void changeText(Boolean check){
        if (check){
            textView2.setText("비밀번호가 일치합니다");
            textView2.setTextColor(context.getResources().getColor(R.color.TextGray));
            positivebutton.setEnabled(true);
            positivebutton.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
            positivebutton.setTextColor(context.getResources().getColor(R.color.colorBlack));
        } else {
            textView2.setText("비밀번호가 일치하지 않습니다.");
            textView2.setTextColor(context.getResources().getColor(R.color.colormiss));
            positivebutton.setEnabled(false);
            positivebutton.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            positivebutton.setTextColor(context.getResources().getColor(R.color.TextColor1));
        }
    }
}