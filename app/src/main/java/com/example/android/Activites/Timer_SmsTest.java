package com.example.android.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.R;

import java.util.Random;

public class Timer_SmsTest extends AppCompatActivity {
    private final int MY_PERMISSION_REQUEST_SMS = 1001;
    Button button;
    EditText editText;
    String random_message = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer__sms_test);

       button = findViewById(R.id.test_button);
       editText = findViewById(R.id.test_edit1);
       Send_SMS timerAndSms = new Send_SMS();

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               timerAndSms.permission(Timer_SmsTest.this);
               Random rand = new Random();
               String edit2 = editText.getText().toString();
               random_message  = String.format("%0 5d",rand.nextInt(100000));
               timerAndSms.sendSMS(edit2,random_message,Timer_SmsTest.this);
               Toast.makeText(Timer_SmsTest.this,"Message sent.",Toast.LENGTH_SHORT).show();
           }
       });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSION_REQUEST_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}