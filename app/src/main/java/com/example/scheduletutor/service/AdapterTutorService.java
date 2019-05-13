package com.example.scheduletutor.service;

import com.example.scheduletutor.model.Class.ClassTutorResponse;
import com.example.scheduletutor.model.ResponseRetrofit;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class AdapterTutorService {
    public interface DeleteClassService {
        @POST("deleteClass.php")
        Observable<ResponseRetrofit> deleteClass(@Body JsonObject object);
    }

    public interface JoinClassService {
        @GET("addClass.php")
        Observable<Void> joinClass(@Body JsonObject object);
    }
}
