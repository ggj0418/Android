package com.example.android.Services;


import com.example.android.DTOS.Dto_findemail;
import com.example.android.DTOS.Dto_get_new_password;
import com.example.android.DTOS.SignupUserinfo;
import com.example.android.DTOS.UserInfo;
import com.example.android.DAOS.Signup;
import com.example.android.DAOS.get_Login;
import com.example.android.DAOS.get_email_confirm;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Services {
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST("users/signin")
    Call<get_Login> requestSignin(@Body UserInfo userInfo);

    @POST("users/valid/email")
    Call<get_email_confirm> requestEmail(@Query("email") String email);

    @POST("users/signup")
    Call<Signup> requestSignup(@Body SignupUserinfo signupUserinfo);

    @POST("users/valid/phone")
    Call<ResponseBody> requestphone(@Query("phoneNo") String phoneNo);

    @POST("users/find/email")
    Call<ResponseBody> requestfindemail(@Body Dto_findemail dto_findemail);

    @POST("users/find/valid/phone")
    Call<ResponseBody> requestfindpassword(@Query("phoneNo") String phoneNo);

    @POST("users/find/reissue/password")
    Call<ResponseBody> requestnewpassword(@Body Dto_get_new_password dto_get_new_password);

    @POST("users/change/password")
    Call<Dto_get_new_password> requestchange(@Query("newPassword") String newPassword);

}
