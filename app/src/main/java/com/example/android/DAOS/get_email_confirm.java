package com.example.android.DAOS;

import com.google.gson.annotations.SerializedName;

public class get_email_confirm {
    @SerializedName("email")
    private String email;

    public String getEmail() {
        return this.email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ClassPojo [response_name = " + email +"]";
    }
}
