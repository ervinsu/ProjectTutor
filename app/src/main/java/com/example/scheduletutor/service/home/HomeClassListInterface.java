package com.example.scheduletutor.service.home;

import com.example.scheduletutor.model.Class.ClassTutor;
import com.example.scheduletutor.model.Class.ClassTutorResponse;
import com.example.scheduletutor.model.User.User;
import com.example.scheduletutor.model.User.UserResponse;

import java.util.List;

import io.reactivex.Observable;

public interface HomeClassListInterface {
    void complete(List<ClassTutor> classTutors);

    void error(String message);

    Observable<ClassTutorResponse> getClasses();
}
