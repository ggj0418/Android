package com.example.android;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceApi {
    @GET("통신하기 위한/Api Server 주소")
    @FormUrlEncoded
    @POST("/user/signin")
    Call<JsonArray> login(@Field("user_id")String id, @Field("user_pw")String pw);

    @FormUrlEncoded
    @POST("/user/signup")
    Call<JsonArray> logup(@Field("res_id")String res_id,
                                 @Field("res_pw")String res_pw
                               //@Field("res_birth")String res_birth,
                               //@Field("res_adress")String res_address,
                               //@Field("res_phone")String res_phone
                                 );

    Call<JsonArray> getretrofitdata();
}
