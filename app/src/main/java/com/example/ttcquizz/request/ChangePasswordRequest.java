package com.example.ttcquizz.request;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest {
    @SerializedName("old_password")
    private String oldPassword;
    @SerializedName("new_password")
    private String newPassword;

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

