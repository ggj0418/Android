package com.example.android.DTOS;

import com.google.gson.annotations.SerializedName;

public class UserInfo {
    UserInfo(){
    }
    public UserInfo(String email, String password){
        this.email = email;
        this.password = password;
    }
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+email+", pass = "+password+"]";
    }

}
