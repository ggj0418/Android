package com.example.android.DAOS;

import com.google.gson.annotations.SerializedName;

public class ResponseBody2 {
    @SerializedName("name")
    private String name;
    @SerializedName("phone")
    private String phone;

    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    private void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ClassPojo [response_name = " + name + ", response_phone = " + phone + "]";
    }
}
