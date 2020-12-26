package com.example.android.QR;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.android.R;
import com.kakao.util.helper.Utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class QRActivity2 extends AppCompatActivity {
    private Button createQRBtn;
    private Button scanQRBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r2);

        Log.d("#############", getKeyHashBase64(getApplicationContext()));

        createQRBtn = (Button) findViewById(R.id.createQR);
        scanQRBtn = (Button) findViewById(R.id.scanQR);

        createQRBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(QRActivity2.this, CreateQR2.class);
                startActivity(intent);
            }
        });

        scanQRBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(QRActivity2.this, ScanQR.class);
                startActivity(intent);
            }
        });
    }

    public String getKeyHashBase64(Context context) { PackageInfo packageInfo = Utility.getPackageInfo(context, PackageManager.GET_SIGNATURES); if (packageInfo == null) return null; for (Signature signature : packageInfo.signatures) { try { MessageDigest md = MessageDigest.getInstance("SHA"); md.update(signature.toByteArray()); return Base64.encodeToString(md.digest(), Base64.DEFAULT); } catch (NoSuchAlgorithmException e) { e.printStackTrace(); } } return null; }
}
