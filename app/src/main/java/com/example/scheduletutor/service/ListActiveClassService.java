package com.example.scheduletutor.service;

import com.example.scheduletutor.model.Class.ClassTutorResponse;
import com.example.scheduletutor.model.ResponseRetrofit;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ListActiveClassService {

    @POST("getListActiveClass.php")
    Observable<ClassTutorResponse> getActiveClasses(@Body JsonObject object);

    @POST("deleteListActiveClass.php")
    Observable<ResponseRetrofit> deleteActiveClass(@Body JsonObject object);

}
