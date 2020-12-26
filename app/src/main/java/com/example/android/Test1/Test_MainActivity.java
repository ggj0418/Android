package com.example.android.Test1;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.R;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Test_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                //gson converter를 생성, Json을 자바 클래스로 바꾸기 위함
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        //-GET 생성한 인터페이스 getData에 쿼리할 id값을 넣고 인큐함.(하나하나 큐에 집어넣음)
        retrofitAPI.getData("1").enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()){    //안전장치
                    List<Post> data = response.body();
                    //우리가 원하는 데이터(response의 body 안의)를 자료형에 맞춰서 가져옴
                    Log.d("Teddy", "성공성공");
                    Log.d("Teddy",data.get(0).getTitle());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        //-Post HashMap형식의 데이터를 전달, onResponse가 호출됐을 경우 로그를 보여줌.
        HashMap<String,Object> input = new HashMap<>();
        input.put("userId",1);
        input.put("title","title title");
        input.put("body", "body body 당근 당근");

        retrofitAPI.postData(input).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()){
                    Post data = response.body();
                    Log.d("Tobby", "POST 성공성공");
                    Log.d("Tobby",data.getBody());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });


    }
}