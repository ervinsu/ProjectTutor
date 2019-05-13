package com.example.scheduletutor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseRetrofit {
    @SerializedName("resullt")
    @Expose
    private String response = null;

    public String getResponse() {
        return response;
    }

}
