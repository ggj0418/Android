package com.example.android.Test1;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @GET("http://jsonplaceholder.typicode.com")
    Call<List<Post>> getData(@Query("userId")String id);

    //<>안의 자료형 -> JSON 데이터를 <>안의 자료형으로 받겠다.
    //따라서 Post.class를 직접 구현해야 함
    //id변수에 값을 담아서 userId를 쿼리함

    @FormUrlEncoded
    @POST("http://jsonplaceholder.typicode.com")
    Call<Post> postData(@FieldMap HashMap<String, Object> param);

    //@FieldMap : Field 형식을 통해 넘겨주는 값이 여러개일 경우
    //Field 형식 : key-value 형식으로 데이터를 전달하는 것
    //Field는 @FormUrlEncoded와 함께 쓰여아 함
    //@FieldMap 방법이 아닌 @Body(java objects를 직렬화)방식도 있음

}
