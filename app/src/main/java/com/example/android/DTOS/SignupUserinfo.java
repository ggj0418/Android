package com.example.android.DTOS;

import com.google.gson.annotations.SerializedName;

public class SignupUserinfo {
    SignupUserinfo(){
    }

    public SignupUserinfo(String email, String name, String password, String tel){
        this.email = email;
        this.name = name;
        this.password = password;
        this.tel = tel;
    }
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("name")
    private String name;

    @SerializedName("tel")
    private String tel;

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

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getTel ()
    {
        return tel;
    }

    public void setTel (String tel)
    {
        this.tel = tel;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [email = "+email+", name = "+name+", pass = "+password+"], tel ="+tel+"";
    }

}

