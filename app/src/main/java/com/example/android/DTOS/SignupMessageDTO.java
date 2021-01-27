package com.example.android.DTOS;

import com.google.gson.annotations.SerializedName;

public class SignupMessageDTO {

    public SignupMessageDTO(String phoneNo){
        this.phoneNo = phoneNo;
    }

    @SerializedName("phoneNo")
    private String phoneNo;

    public String getphoneNo()
    {
        return phoneNo;
    }

    public void setphoneNo(String phoneNo)
    {
        this.phoneNo = phoneNo;
    }

    @Override
    public String toString()
    {
        return "ClassPojo phoneNo = "+phoneNo+"]";
    }

}