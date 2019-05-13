package com.example.scheduletutor.model.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("userFullName")
    @Expose
    private String userName;

    @SerializedName("userPhoto")
    @Expose
    private String userPhoto;

    @SerializedName("userRating")
    @Expose
    private String userRating;
    @SerializedName("userRoleID")
    @Expose
    private String userRoleID;
    @SerializedName("userEmail")
    @Expose
    private String userEmail;
    @SerializedName("userPhone")
    @Expose
    private String userPhone;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserRoleID() {
        return userRoleID;
    }

    public void setUserRoleID(String userRoleID) {
        this.userRoleID = userRoleID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }
}
