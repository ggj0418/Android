package com.example.android.Services;


import com.example.android.DTOS.FindingEmailDTO;
import com.example.android.DTOS.NewPasswordDTO;
import com.example.android.DTOS.SignupDTO;
import com.example.android.DTOS.UserInfoDTO;
import com.example.android.DAOS.SignupInfo;
import com.example.android.DAOS.LoginInfo;

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
    Call<LoginInfo> requestSignin(@Body UserInfoDTO userInfo);

    @POST("users/valid/email")
    Call<ResponseBody> requestEmail(@Query("email") String email);

    @POST("users/signup")
    Call<SignupInfo> requestSignup(@Body SignupDTO signupUserinfo);

    @POST("users/valid/phone")
    Call<ResponseBody> requestphone(@Query("phoneNo") String phoneNo);

    @POST("users/find/email")
    Call<ResponseBody> requestfindemail(@Body FindingEmailDTO dto_findemail);

    @POST("users/find/valid/phone")
    Call<ResponseBody> requestfindpassword(@Query("phoneNo") String phoneNo);

    @POST("users/find/reissue/password")
    Call<ResponseBody> requestnewpassword(@Body NewPasswordDTO dto_get_new_password);

    @POST("users/change/password")
    Call<NewPasswordDTO> requestchange(@Query("newPassword") String newPassword);

}
