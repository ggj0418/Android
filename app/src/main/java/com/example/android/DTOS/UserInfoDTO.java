package com.example.android.DTOS;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class UserInfoDTO {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public UserInfoDTO(String email, String password){
        this.email = email;
        this.password = password;
    }

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

    @NotNull
    @Override
    public String toString()
    {
        return "ClassPojo [name = " + email + ", pass = " + password + "]";
    }
}
