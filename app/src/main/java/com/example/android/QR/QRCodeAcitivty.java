package com.example.android.QR;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.Activites.UserAccount.LoginActivity;
import com.example.android.DTOS.NewPasswordDTO;
import com.example.android.Dialog.CustomDialogClickListener;
import com.example.android.Dialog.OptionCodeTypeDialog;
import com.example.android.R;
import com.example.android.Retrofit.RetrofitClient;
import com.example.android.Services.Services;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QRCodeAcitivty extends AppCompatActivity {

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
                public void onPositiveClick(String pw1,String pw2) {
                    Services retrofitAPI2 = RetrofitClient.getRetrofit().create(Services.class);
                    Call<NewPasswordDTO> newpassCall = retrofitAPI2.requestchange(pw1);
                    newpassCall.enqueue(new Callback<NewPasswordDTO>() {
                        @Override
                        public void onResponse(Call<NewPasswordDTO> call, Response<NewPasswordDTO> response) {
                            switch (response.code()) {
                                case 200:
                                    Toast.makeText(QRCodeAcitivty.this, "OK", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(QRCodeAcitivty.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                    break;

                                case 201:
                                    Toast.makeText(QRCodeAcitivty.this,"정상적으로 비밀번호를 변경, 새로운 토큰을 발급", Toast.LENGTH_LONG).show();
                                    Intent intent2 = new Intent(QRCodeAcitivty.this, LoginActivity.class);
                                    startActivity(intent2);
                                    break;

                                case 401:
                                    Toast.makeText(QRCodeAcitivty.this, "토큰 만료로 인해 비밀번호 변경 불가 -> 새로운 토큰 발급", Toast.LENGTH_LONG).show();
                                    break;

                                case 403:
                                    Toast.makeText(QRCodeAcitivty.this, "일치하는 회원이 존재하지 않아 비밀번호 변경에 실패하였습니다.", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<NewPasswordDTO> call, Throwable t) {
                            Log.e("##########","Fail",t);
                        }
                    });
                }

                @Override
                public void onNegativeClick() {
                    Intent intent = new Intent(QRCodeAcitivty.this, LoginActivity.class);
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