package com.railway.ydhdemo.inject.module;

import android.content.Context;

import com.railway.ydhdemo.inject.service.ApiService;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule extends BaseModule {
    public AppModule(Context application) {
        super(application);
    }

    @Provides
    public ApiService provideApiService() {
        return mRetrofit.create(ApiService.class);
    }
}
