package com.example.scheduletutor.di.modul;


import android.content.Context;

import com.example.scheduletutor.di.scope.CustomScope;
import com.example.scheduletutor.model.User.UserLocalStore;
import com.example.scheduletutor.service.AddClassService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AddClassTutorModule {
    @Provides
    @CustomScope
    AddClassService provideAddClassService(Retrofit retrofit){
        return retrofit.create(AddClassService.class);
    }

    private Context context;

    public AddClassTutorModule(Context context) {
        this.context = context;
    }

    @Provides
    @CustomScope
    UserLocalStore provideUserLocalStore(){
        return new UserLocalStore(context);
    }

}
