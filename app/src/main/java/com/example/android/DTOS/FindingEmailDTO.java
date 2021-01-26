package com.example.android.DTOS;

import com.google.gson.annotations.SerializedName;

public class FindingEmailDTO {
    FindingEmailDTO(){
    }
    public FindingEmailDTO(String name, String phone){
        this.name = name;
        this.phone = phone;
    }
    @SerializedName("name")
    private String name;

    @SerializedName("phone")
    private String phone;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", phone = "+phone+"]";
    }

}
