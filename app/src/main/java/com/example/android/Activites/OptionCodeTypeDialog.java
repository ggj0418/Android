package com.example.android.Activites;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.android.R;

public class OptionCodeTypeDialog extends Dialog {

    Context context;
    CustomDialogClickListener customDialogClickListener;
    TextView negativetext;
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

        positivebutton.setOnClickListener(v -> {

            this.customDialogClickListener.onPositiveClick();
            dismiss();
        });
        negativetext.setOnClickListener(v -> {
            // 취소버튼 클릭
            this.customDialogClickListener.onNegativeClick();
            dismiss();
        });
    }
}