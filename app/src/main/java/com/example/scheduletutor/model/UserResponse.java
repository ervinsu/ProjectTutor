package com.example.scheduletutor.model;

import com.example.scheduletutor.model.User.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {
    @SerializedName("result")
    @Expose
    private List<User> User = null;

    public List<User> getUser() {
        return User;
    }
}
