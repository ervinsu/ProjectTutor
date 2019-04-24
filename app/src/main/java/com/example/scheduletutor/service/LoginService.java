package com.example.scheduletutor.service;

import com.example.scheduletutor.model.User.UserResponse;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    @POST("checkUserLogin.php")
    Observable<UserResponse> getUser(@Body JsonObject object);
}
