package com.example.scheduletutor.di.modul;

import com.example.scheduletutor.di.scope.CustomScope;
import com.example.scheduletutor.service.AdapterTutorService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ClassAdapterModule {

    @Provides
    @CustomScope
    AdapterTutorService.DeleteClassService provideDeleteClassService(Retrofit retrofit){
        return retrofit.create(AdapterTutorService.DeleteClassService.class);
    }

    @Provides
    @CustomScope
    AdapterTutorService.JoinClassService provideJoinClassService(Retrofit retrofit){
        return retrofit.create(AdapterTutorService.JoinClassService.class);
    }
}
