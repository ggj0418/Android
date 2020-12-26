package com.example.android.Test1;

import com.google.gson.annotations.SerializedName;

public class Post {
    //앞서 본 Json 데이터들을 class로 받아오기 위한 작업
    //저 데이터에 key값들을 그대로 변수화 하는 것
    //@SerializedName()으로 Json 객체를 매칭, 여기서 gson을 사용하게 됨
    //변수 선언 후 변수에 대한 겟터 셋터를 만들어 줌
    @SerializedName("userId")
    private int userId;
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String body;

    public  int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void getTitle(String title){
        this.title = title;
    }

    public String getBody(){
        return body;
    }

    public void setBody(String body){
        this.body = body;
    }

}
