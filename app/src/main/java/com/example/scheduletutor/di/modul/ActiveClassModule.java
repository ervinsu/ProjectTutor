package com.example.scheduletutor.di.modul;


import android.content.Context;

import com.example.scheduletutor.di.scope.CustomScope;
import com.example.scheduletutor.model.User.User;
import com.example.scheduletutor.model.User.UserLocalStore;
import com.example.scheduletutor.service.ListActiveClassService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ActiveClassModule {
    @Provides
    @CustomScope
    ListActiveClassService provideListClassService(Retrofit retrofit){
        return retrofit.create(ListActiveClassService.class);
    }


    private Context context;

    public ActiveClassModule(Context context) {
        this.context = context;
    }

    @Provides
    @CustomScope
    UserLocalStore provideUserLocalStore(){
        return new UserLocalStore(context);
    }


    @Provides
    @CustomScope
    User provideUser(){
        return provideUserLocalStore().getLoggedInUser();
    }
}
