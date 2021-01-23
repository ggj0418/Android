package com.example.android.DAOS;

import com.google.gson.annotations.SerializedName;

public class get_Login {
            @SerializedName("email")
            private String email;
            @SerializedName("password")
            private String password;
//    public Login(String accessToken, String tokenType) {
//        this.accessToken = accessToken;
//        this.tokenType = tokenType;
//    }
//    public Login() {};
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
