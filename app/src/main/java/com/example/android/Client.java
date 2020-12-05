package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client extends AppCompatActivity {

    public ServiceApi apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor);

        retrofit2.Retrofit retrofit = new Retrofit.Builder()
                //서버 url설정
                .baseUrl("ip")
                //데이터 파싱 설정
                .addConverterFactory(GsonConverterFactory.create())
                //객체정보 반환
                .build();

        apiService = retrofit.create(ServiceApi.class);

    }
}