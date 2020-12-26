package com.example.android.Okhttp_Login;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpConnection {
    public static OkHttpClient client = new OkHttpClient();
    public static HttpConnection instance = new HttpConnection();
    public static HttpConnection getInstance(){
        return instance;
    }
        /** 웹 서버로 요청을 한다. */
        public void requestWebServer(String parameter, String parameter2, Callback callback) {
            RequestBody body = new FormBody.Builder()     //요청할 body 형성, add안의 parameter는 php에서 post로 미리 받기로 정의된 것
                    .add("parameter", parameter)
                    .add("parameter2", parameter2)
                    .build();
            Request request = new Request.Builder()       //url과 해당 url에 보낼 post형식으로 다음 body를 요청사항으로 만들어줌
                    .url("http://mydomain.com/sendData")  //일단 아무 Url 넣어놓았습니다!..
                    .post(body)
                    .build();
            client.newCall(request).enqueue(callback);    //id를 보낸 결과를 php가 json 형태로 변환한것을 받아올 것이므로 callback을 사용해야함
    }
}
