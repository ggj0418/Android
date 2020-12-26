package com.example.android.Retrofit_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.android.R;

public class SharedPref extends AppCompatActivity {
    String LOGIN_SESSION = "login.session";
    SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_pref);
    }

    public void openSharedPrep(Context context) {
        this.sharedPreferences = context.getSharedPreferences(LOGIN_SESSION, Context.MODE_PRIVATE);
    }

    public void writeLoginSession(String data){
        if(this.sharedPreferences == null) {
            Log.e("sh1", "Plz start openSahredPrep() !");
        } else {
            sharedPreferences.edit().putString("session",data).apply();
        }
    }

    public void readLoginSession() {
        if(this.sharedPreferences==null){
            Log.e("DSMAD", "Plz start openSahredPrep() !");
        }
        else sharedPreferences.getString("session",null);
    }
}
