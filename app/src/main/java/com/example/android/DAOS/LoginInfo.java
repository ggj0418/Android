package com.example.android.DAOS;

import com.google.gson.annotations.SerializedName;

public class LoginInfo {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public String getEmail() {
        return this.email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ClassPojo [response_name = " + email + ", response_pass = " + password + "]";
    }
}
