package com.example.android.Activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.widget.TextView;

import com.example.android.R;

public class Send_SMS extends AppCompatActivity {

    public final int MY_PERMISSION_REQUEST_SMS = 1001;


    public void sendSMS(String phoneNumber, String message, Context context) {
        {
            SmsManager sms = SmsManager.getDefault();
            PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_SENT_ACTION"), 0);
            PendingIntent deliveredIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_DELIVERED_ACTION"), 0); // 서버로부터 비콘을 제대로 수신하지 못한 경우
            sms.sendTextMessage(phoneNumber, null, message, sentIntent, deliveredIntent);
        }
    }

    public void permission(Context context){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("info");
                builder.setMessage("This app won't work prop erly unless you grant SMS permission.");
                builder.setIcon(android.R.drawable.ic_dialog_info);

                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(Send_SMS.this, new String[]{
                                Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST_SMS);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.SEND_SMS},MY_PERMISSION_REQUEST_SMS);
            }
        }
    }
}

