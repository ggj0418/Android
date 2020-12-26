package com.example.android.Test2;

import com.example.android.Test1.Post;

import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI2 {
    @GET("http://13.125.12.125:3000/api/member/idPw/")
    Call<Post2> getData(@Query("user_hash_id")String id, @Query("user_hash_pw")String pw);

    @FormUrlEncoded
    @POST("http://13.125.12.125:3000/api/member/idPw/")
    Call<Post2> postData(@FieldMap HashMap<String, Object> param);
}
