package com.example.scheduletutor.di.modul;

import android.content.Context;

import com.example.scheduletutor.di.scope.CustomScope;
import com.example.scheduletutor.model.User.User;
import com.example.scheduletutor.model.User.UserLocalStore;
import com.example.scheduletutor.service.ListClassService;
import com.example.scheduletutor.service.LoginService;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class  HomeModule {
    private Context context;

    public HomeModule(Context context) {
        this.context = context;
    }

    @Provides
    @CustomScope
    UserLocalStore provideUserLocalStore(){
        return new UserLocalStore(context);
    }


//    @Provides
//    @CustomScope
//    User provideUser(){
//        return provideUserLocalStore().getLoggedInUser();
//    }
}
