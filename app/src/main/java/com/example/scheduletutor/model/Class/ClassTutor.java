package com.example.scheduletutor.model.Class;

import com.example.scheduletutor.model.User.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassTutor extends User {
    @SerializedName("classID")
    @Expose
    private String classID;
    @SerializedName("className")
    @Expose
    private String className;
    @SerializedName("classTime")
    @Expose
    private String classTime;
    @SerializedName("classShift")
    @Expose
    private int classShift;

    @SerializedName("classLocation")
    @Expose
    private String classLocation;

    public String getClassLocation() {
        return classLocation;
    }

    public void setClassLocation(String classLocation) {
        this.classLocation = classLocation;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public int getClassShift() {
        return classShift;
    }

    public void setClassShift(int classShift) {
        this.classShift = classShift;
    }
}
