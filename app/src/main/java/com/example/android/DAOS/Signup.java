package com.example.android.DAOS;

import com.google.gson.annotations.SerializedName;

public class Signup {

    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("password")
    private String password;
    @SerializedName("tel")
    private String tel;

    //public Login(String accessToken, String tokenType) {
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

    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return this.tel;
    }

    private void setTel(String tel) {
        this.tel = tel;
    }


    @Override
    public String toString() {
        return "ClassPojo [email = " + email + ", name = " + name + ", pass = " + password + "], tel =" + tel + "";
    }
}


