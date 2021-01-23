package com.example.android.DAOS;

import com.google.gson.annotations.SerializedName;

public class get_valid_phone {
    @SerializedName("phoneNo")
    private String phoneNo;

    public String getPhoneNo() {
        return this.phoneNo;
    }

    private void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return "ClassPojo [phoneNo = " + phoneNo +"]";
    }
}
