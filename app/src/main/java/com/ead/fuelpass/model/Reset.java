package com.ead.fuelpass.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Password reset model
 */
public class Reset {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("currentPassword")
    @Expose
    private String currentPassword;

    @SerializedName("newPassword")
    @Expose
    private String newPassword;

    public Reset(String id, String currentPassword, String newPassword) {
        this.id = id;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
