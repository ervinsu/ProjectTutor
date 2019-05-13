package com.example.scheduletutor.service;

import com.example.scheduletutor.model.Class.ClassTutorResponse;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ListMyClassService {
    @POST("getListMyClass.php")
    Observable<ClassTutorResponse> getMyClass(@Body JsonObject jsonObject);
}
