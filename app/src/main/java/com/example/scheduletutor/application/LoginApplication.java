package com.example.scheduletutor.application;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import com.example.scheduletutor.BuildConfig;
import com.example.scheduletutor.di.component.DaggerLoginComponent;
import com.example.scheduletutor.di.component.DaggerNetworkComponent;
import com.example.scheduletutor.di.component.LoginComponent;
import com.example.scheduletutor.di.component.NetworkComponent;
import com.example.scheduletutor.di.modul.NetworkModule;

public class LoginApplication extends Application {
    public LoginComponent loginComponent;

    @Override
    public void onCreate() {
        resolveDepedency();
        super.onCreate();
    }

    private void resolveDepedency() {
        loginComponent = DaggerLoginComponent.builder()
                .networkComponent(getNetworkComponent())
                .build();
    }

    private NetworkComponent getNetworkComponent() {
        return  DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule(BuildConfig.API_KEY))
                .build();
    }

    public LoginComponent getLoginComponent() {
        return loginComponent;
    }
}
