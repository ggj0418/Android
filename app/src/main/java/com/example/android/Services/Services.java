package com.example.android.Services;

import com.example.android.DTOS.AccessTokenDTO;
import com.example.android.DTOS.CartItemDTO;
import com.example.android.DTOS.ChangePasswordDTO;
import com.example.android.DTOS.FindingEmailDTO;
import com.example.android.DTOS.FindingPasswordDTO;
import com.example.android.DTOS.NewPasswordDTO;
import com.example.android.DTOS.SignupDTO;
import com.example.android.DTOS.SignupMessageDTO;
import com.example.android.DTOS.UserInfoDTO;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Services {

    @POST("users/signin")
    Call<AccessTokenDTO> requestSignin(@Body UserInfoDTO userInfo);


    @GET("users/signup")
    Call<ResponseBody> requestEmail(@Query("email") String email);


    @POST("users/signup")
    Call<ResponseBody> requestSignup(@Body SignupDTO signupDTO);


    @POST("users/signup/message")
    Call<ResponseBody> requestphone(@Body SignupMessageDTO signupMessageDTO);


    @POST("users/find/email")
    Call<ResponseBody> requestfindemail(@Body FindingEmailDTO dto_findemail);


    @POST("users/find/password/message")
    Call<ResponseBody> requestfindpassword(@Body FindingPasswordDTO findingPasswordDTO);


    @POST("users/find/password")
    Call<ResponseBody> requestnewpassword(@Body NewPasswordDTO dto_get_new_password);


    @POST("users/change/password")
    Call<ChangePasswordDTO> requestchange(@Body ChangePasswordDTO changePasswordDTO);


    @POST("test/token")
    Call<ResponseBody> verifyToken();


    @GET("carts/list")
    Call<List<CartItemDTO>> getCartList();


    @POST("carts")
    Call<ResponseBody> setCartItem(@Body HashMap<String, Integer> map);
}
