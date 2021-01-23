package com.example.android.DAOS;

import com.google.gson.annotations.SerializedName;

public class get_pw {
    @SerializedName("newPassword")
    private String newPassword;

    public String getNewPassword() {
        return this.newPassword;
    }

    private void setNewPassword(String email) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "ClassPojo [response_name = " + newPassword +"]";
    }
}
