package com.example.android.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.R;

public class qractivity extends AppCompatActivity {

    View header;
    Button button1;
    EditText editText1, editText2;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qractivity);

        SharedPreferences pref = getSharedPreferences("key", MODE_PRIVATE);
        Boolean str = pref.getBoolean("semipassword",false);
        Log.d("*********",str.toString());

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.option_codetype_dialog, null);

        editText1 = view.findViewById(R.id.dia_pw1);
        editText2 = view.findViewById(R.id.dia_pw2);
        text = findViewById(R.id.qr_test);
        if(str.equals(true)) {
            OptionCodeTypeDialog octDialog = new OptionCodeTypeDialog(this, new CustomDialogClickListener() {
                @Override
                public void onPositiveClick() {
                    String edit1 = editText1.getText().toString();
                    String edit2 = editText2.getText().toString();
                    text.setText(edit1);
                    Log.d("******", edit1);

                }

                @Override
                public void onNegativeClick() {
                    Intent intent = new Intent(qractivity.this, Login.class);
                    startActivity(intent);
                }
            });
            Display display = getWindowManager().getDefaultDisplay();  // in Activity
            Point size = new Point();
            display.getRealSize(size); // or getSize(size)
            int width = (int) (size.x * 0.9444f);
            int height = (int) (size.y * 0.46f);
            octDialog.setCanceledOnTouchOutside(true);
            octDialog.setCancelable(true);

            octDialog.show();
            Window window = octDialog.getWindow();
            window.setLayout(width, height);
        }
    }
}