package com.example.android.DTOS;

import com.google.gson.annotations.SerializedName;

public class AccessTokenDTO {
    @SerializedName("accessToken")
    private String accessToken;
    @SerializedName("tokenType")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
