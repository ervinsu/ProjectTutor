package com.example.scheduletutor.model.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClassTutorResponse {
    @SerializedName("result")
    @Expose
    private List<ClassTutor> classTutors = null;

    public List<ClassTutor> getClasses() {
        return classTutors;
    }
}
