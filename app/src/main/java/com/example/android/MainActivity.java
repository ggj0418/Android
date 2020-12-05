package com.example.android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.Kakao.SessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

public class MainActivity extends AppCompatActivity {

    private Button kakao_logout_button, naver_login_button, naver_logout_button;

    // 카카오 세션관리
    private SessionCallback sessionCallback = new SessionCallback();
    private Session session;

    // 네이버 세션관리
    private OAuthLogin mNaverLoginModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);

        // 로그인 상태 확인
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Toast.makeText(getApplicationContext(), "로그아웃 상태입니다", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(MeV2Response result) {
                Toast.makeText(getApplicationContext(), "로그인 되어있습니다\n" + result.getId(), Toast.LENGTH_LONG).show();
            }
        });

        // 로그아웃
        kakao_logout_button = findViewById(R.id.btn_kakao_login_out);
        kakao_logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManagement
                        .getInstance()
                        .requestLogout(new LogoutResponseCallback() {
                            @Override
                            public void onSuccess(Long result) {
                                Toast.makeText(getApplicationContext(), "로그아웃 success", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(ErrorResult errorResult) {
                                Toast.makeText(getApplicationContext(), errorResult.toString(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCompleteLogout() {
                                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        naver_login_button = findViewById(R.id.btn_naver_login);
        naver_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNaverLoginModule = OAuthLogin.getInstance();
                mNaverLoginModule.init(
                        getApplicationContext(),
                        getString(R.string.naver_client_id),
                        getString(R.string.naver_client_secret),
                        getString(R.string.naver_client_name)
                );

                @SuppressLint("HandlerLeak")
                OAuthLoginHandler mNaverLoginHandler = new OAuthLoginHandler() {
                    @Override
                    public void run(boolean success) {
                        if(success) {
                            Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            String errorCode = mNaverLoginModule
                                    .getLastErrorCode(getApplicationContext())
                                    .getCode();
                            String errorDesc = mNaverLoginModule
                                    .getLastErrorDesc(getApplicationContext());

                            Toast.makeText(getApplicationContext(), "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                mNaverLoginModule.startOauthLoginActivity(MainActivity.this, mNaverLoginHandler);
            }
        });

        naver_logout_button = findViewById(R.id.btn_naver_login_out);
        naver_logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNaverLoginModule.logout(getApplicationContext());
                Toast.makeText(getApplicationContext(), "네이버 로그아웃 되었습니다.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}