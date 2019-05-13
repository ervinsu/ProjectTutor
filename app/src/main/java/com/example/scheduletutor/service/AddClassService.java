package com.example.scheduletutor.service;

import com.example.scheduletutor.model.ResponseRetrofit;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AddClassService {
    @POST("addClass.php")
    Observable<ResponseRetrofit> addClass(@Body JsonObject object);
}
