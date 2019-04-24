package com.example.scheduletutor.di.modul;


import android.content.Context;

import com.example.scheduletutor.di.scope.CustomScope;
import com.example.scheduletutor.model.User.User;
import com.example.scheduletutor.model.User.UserLocalStore;
import com.example.scheduletutor.service.ListClassService;
import com.example.scheduletutor.service.ListMyClassService;
import com.example.scheduletutor.service.LoginService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ListFragmentModule {

    @Provides
    @CustomScope
    ListClassService provideListClassService(Retrofit retrofit){
        return retrofit.create(ListClassService.class);
    }

    @Provides
    @CustomScope
    ListMyClassService provideListMyClassService(Retrofit retrofit){
        return retrofit.create(ListMyClassService.class);
    }

    private Context context;

    public ListFragmentModule(Context context) {
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
