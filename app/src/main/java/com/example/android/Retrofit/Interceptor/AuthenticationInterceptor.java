package com.example.android.Retrofit.Interceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationInterceptor implements Interceptor {
    private final String userToken;

    /**
     *
     * @param userToken
     */
    public AuthenticationInterceptor(String userToken) {
        this.userToken = userToken;
    }

    /**
     *
     * @param chain
     * @return
     * @throws IOException
     */
    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("Authorization", userToken);

        Request request = builder.build();
        return chain.proceed(request);
    }
}
