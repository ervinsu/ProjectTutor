package com.example.scheduletutor.service;

import com.example.scheduletutor.model.Class.ClassTutorResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ListMyClassService {
    @GET("getListClass.php")
    Observable<ClassTutorResponse> getMyClass();
}
