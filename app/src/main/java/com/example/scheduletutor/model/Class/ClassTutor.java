package com.example.scheduletutor.model.Class;

import com.example.scheduletutor.model.User.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassTutor {
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

    @SerializedName("classCategory")
    @Expose
    private String classCategory;

    @SerializedName("tutorName")
    @Expose
    private String tutorName;

    @SerializedName("tutorPhoto")
    @Expose
    private String tutorPhoto;

    public String getClassLocation() {
        return classLocation;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getTutorPhoto() {
        return tutorPhoto;
    }

    public void setTutorPhoto(String tutorPhoto) {
        this.tutorPhoto = tutorPhoto;
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
