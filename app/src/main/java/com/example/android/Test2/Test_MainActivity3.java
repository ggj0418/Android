package com.example.android.Test2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.android.R;
import com.example.android.Test1.Post;
import com.example.android.Test1.RetrofitAPI;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Test_MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test__main3);

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("http://13.125.12.125:3000/api/member/idPw/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI2 retrofitAPI2 = retrofit2.create(RetrofitAPI2.class);

        retrofitAPI2.getData("ggj0418", "sdfwerasdf").enqueue(new Callback<Post2>() {
            @Override
            public void onResponse(Call<Post2> call, Response<Post2> response) {
                Post2 data = response.body();
                Log.d("Teddy", "성공성공");
                Log.d("Teddy", data.toString());
                //Log.d("Teddy",data.getTokken());
            }

            @Override
            public void onFailure(Call<Post2> call, Throwable t) {
            }
        });

        HashMap<String,Object> input = new HashMap<>();
        input.put("id","suen66");
        input.put("pw","toby1122");

        retrofitAPI2.postData(input).enqueue(new Callback<Post2>() {
            @Override
            public void onResponse(Call<Post2> call, Response<Post2> response) {
                if (response.isSuccessful()){
                    Post2 data = response.body();
                    Log.d("Tobby", "POST 성공성공");

                }
            }

            @Override
            public void onFailure(Call<Post2> call, Throwable t) {
            }
        });
    }
}