package com.example.android.Retrofit;

import com.example.android.Retrofit.Interceptor.AuthenticationInterceptor;
import com.example.android.Retrofit.Interceptor.SupportInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit getRetrofit(String accessToken) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = null;

        if(accessToken != null) {
            AuthenticationInterceptor authenticationInterceptor = new AuthenticationInterceptor("Bearer " + accessToken);

            client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addInterceptor(authenticationInterceptor)
                    .addInterceptor(new SupportInterceptor())
                    .build();
        } else {
            client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addInterceptor(new SupportInterceptor())
                    .build();
        }

        return new Retrofit.Builder()
                .baseUrl("http://52.78.123.79:80/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(interceptor)
//                .connectTimeout(2, TimeUnit.MINUTES)
//                .readTimeout(50, TimeUnit.SECONDS)
//                .writeTimeout(50, TimeUnit.SECONDS)
//                .addInterceptor(new SupportInterceptor())
//                .build();
//
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl("http://52.78.123.79:80")
//             // .addConverterFactory(new NullOnEmptyConverterFactory())
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(client)
//                .build();
//
//        return retrofit;
