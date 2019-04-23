package com.example.scheduletutor.di.modul;


import com.example.scheduletutor.di.scope.CustomScope;
import com.example.scheduletutor.service.LoginService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class LoginModule {

    @Provides
    @CustomScope
    LoginService provideUserService(Retrofit retrofit){
        return retrofit.create(LoginService.class);
    }
}
