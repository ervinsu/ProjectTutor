package com.example.scheduletutor.di.modul;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    private String mBaseUrl;

    public NetworkModule(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    @Provides
    @Singleton
    GsonConverterFactory providesGsonConverterFactory(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return GsonConverterFactory.create(gson);
    }
    @Provides
    @Singleton
    RxJava2CallAdapterFactory providesRxAdapterFactory(){
        return RxJava2CallAdapterFactory.create();
    }


    @Provides
    @Singleton
    Retrofit provideRetrofit(GsonConverterFactory gsonConverterFactory, RxJava2CallAdapterFactory adapterFactory){

        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(adapterFactory)
                .build();
    }
}
