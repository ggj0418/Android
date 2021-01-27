package com.example.android.DTOS;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordDTO {

    public ChangePasswordDTO(String password){
        this.password = password;
    }

    @SerializedName("password")
    private String password;

    public String getpassword()
    {
        return password;
    }

    public void setpassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "ClassPojo password = "+password+"]";
    }

}