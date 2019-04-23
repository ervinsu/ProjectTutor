package com.example.scheduletutor.service.Login;

import com.example.scheduletutor.model.User.User;
import com.example.scheduletutor.model.UserResponse;

import java.util.List;

import io.reactivex.Observable;

public interface LoginViewInterface {
    void complete(List<User> userResponses);

    void error(String message);

    void addUser(List<User> user);

    Observable<UserResponse> getUser();
}
