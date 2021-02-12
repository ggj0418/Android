package com.example.android.Services;

import com.example.android.DTOS.AccessTokenDTO;
import com.example.android.DTOS.CartDTO;
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
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Services {

    /**
     * 로그인 api
     * @param userInfo 사용자 이메일, 비밀번호를 필드로 가지는 요청 객체
     * @return 토큰 정보를 반환값으로 받는 Call
     */
    @POST("users/signin")
    Call<AccessTokenDTO> requestSignin(@Body UserInfoDTO userInfo);

    /**
     * 이메일 중복 체크 api
     * 이메일이 DB에 있으면 로그인 프로세스
     * 이메일이 DB에 없으면 회원가입 프로세스
     * @param email 사용자 로그인 이메일
     * @return 단순 응답 바디를 반환값으로 받는 Call
     */
    @GET("users/signup")
    Call<ResponseBody> requestEmail(@Query("email") String email);

    /**
     * 회원가입 api
     * @param signupDTO 사용자 이메일, 비밀번호, 이름, 휴대폰번호를 필드로 가지는 요청 객체
     * @return 단순 응답 바디를 반환값으로 받는 Call
     * TODO 응답값 반환 매핑 클래스를 지정할 필요가 없으므로 ResponseBody로 바꿔야 함
     */
    @POST("users/signup")
    Call<ResponseBody> requestSignup(@Body SignupDTO signupDTO);

    /**
     * 회원가입 시, 휴대폰 본인인증 api
     * @param signupMessageDTO 사용자 휴대폰번호를 필드로 가지는 요청 객체
     * @return 단순 응답 바디를 반환값으로 받는 Call
     */
    @POST("users/signup/message")
    Call<ResponseBody> requestphone(@Body SignupMessageDTO signupMessageDTO);

    /**
     * 이메일 찾기 api
     * @param dto_findemail 사용자 이름과 휴대폰번호를 필드로 가지는 요청 객체
     * @return 단순 응답 바디를 반환값으로 받는 Call
     */
    @POST("users/find/email")
    Call<ResponseBody> requestfindemail(@Body FindingEmailDTO dto_findemail);

    /**
     * 비밀번호 찾기 시, 휴대폰 본인인증 api
     * @param findingPasswordDTO 사용자 휴대폰번호를 필드로 가지는 요청 객체
     * @return 단순 응답 바디를 반환값으로 받는 Call
     */
    @POST("users/find/password/message")
    Call<ResponseBody> requestfindpassword(@Body FindingPasswordDTO findingPasswordDTO);

    /**
     * 임시 비밀번호 발급 api
     * @param dto_get_new_password 사용자 이름과 휴대폰 번호를 필드로 가지는 요청 객체
     * @return 단순 응답 바디를 반환값으로 받는 Call
     */
    @POST("users/find/password")
    Call<ResponseBody> requestnewpassword(@Body NewPasswordDTO dto_get_new_password);

    /**
     * ????? 아마도 비밀번호 변경 api 인 것 같은데 swagger에 관련 내용이 없음
     * @param changePasswordDTO
     * @return
     */
    @POST("users/change/password")
    Call<ChangePasswordDTO> requestchange(@Body ChangePasswordDTO changePasswordDTO);

    /**
     * accessToken 인증 api
     * @return 단순 응답 바디를 반환값으로 받는 Call
     */
    @POST("test/token")
    Call<ResponseBody> verifyToken();

    /**
     * 장바구니 조회 api
     * @return 장바구니에 있는 상품 리스트를 반환값으로 받는 Call
     */
    @GET("carts/list")
    Call<CartDTO> getCartList();

    /**
     * 장바구니 상품 추가 api
     * @param map 상품 바코드번호 매핑 변수
     * @return 단순 응답 바디를 반환값으로 받는 Call
     */
    @POST("carts")
    Call<ResponseBody> setCartItem(@Body HashMap<String, Integer> map);

    /**
     * 장바구니 상품 삭제 api
     * @param map 상품 바코드번호 매핑 변수
     * @return 단순 응답 바디를 반환값으로 받는 Call
     */
    @HTTP(method = "DELETE", path = "carts", hasBody = true)
//    @DELETE("carts")
    Call<ResponseBody> deleteCartItem(@Body HashMap<String, Integer> map);

    /**
     * 장바구니 상품 개수 증가 api
     * @param productNo 상품 고유번호
     * @return 단순 응답 바디를 반환값으로 받는 Call
     */
    @PUT("carts/add/{productNo}")
    Call<ResponseBody> addCartItemCount(@Path("productNo") int productNo);

    /**
     * 장바구니 상품 개수 감소 api
     * @param productNo 상품 고유번호
     * @return 단순 응답 바디를 반환값으로 받는 Call
     */
    @PUT("carts/sub/{productNo}")
    Call<ResponseBody> subCartItemCount(@Path("productNo") int productNo);

    /**
     * 사용자별로 고유한 merchant_uid를 발급받는 api
     * @param map merchant_new Date().now
     * @return hash값이 추가된 merchant_new Date().now
     */
    @POST("payment/merchant/me")
    Call<ResponseBody> getHashUid(@Body HashMap<String, String> map);

    @GET("payment/certification")
    Call<ResponseBody> certifyPayment(@Query("cartNo") int cartNo, @Query("imp_success") boolean imp_success, @Query("imp_uid") String imp_uid, @Query("merchant_uid") String merchant_uid);
}
