package com.example.android.Test2;

import com.google.gson.annotations.SerializedName;
//서버에서 부터 받는 값
public class Post2 {
    @SerializedName("response")
    private String tokken;

    public String getTokken(){
        return tokken;
    }

}
