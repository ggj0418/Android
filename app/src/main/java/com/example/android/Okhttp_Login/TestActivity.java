package com.example.android.Okhttp_Login;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.example.android.R;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";
    private HttpConnection httpConn = HttpConnection.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    }

    /** 웹 서버로 데이터 전송 */
    public static void sendData() {
        new Thread() {           //네트워크 통신하는 작업은 무조건 작업스레드를 생성해서 호출
            public void run() {
                                 //파라미터 2개와 미리정의해논 콜백함수를 매개변수로 전달하여 호출
                HttpConnection hc = HttpConnection.getInstance();
                hc.requestWebServer(LoginActivity.id.getText().toString(), LoginActivity.pw.getText().toString(), callback);
            }
        }.start();
    }

    public static Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("tag", "콜백오류:"+e.getMessage());
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().toString();
            Log.d("tag", "서버에서 응답한 Body:"+body);
        }
    };

}

