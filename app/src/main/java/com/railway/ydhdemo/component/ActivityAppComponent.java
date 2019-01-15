package com.railway.ydhdemo.component;


import com.railway.ydhdemo.MainActivity;
import com.railway.ydhdemo.inject.module.ActivityModule;
import com.railway.ydhdemo.inject.module.ApiModule;

import dagger.Component;

@Component(modules = {ActivityModule.class, ApiModule.class})
public interface ActivityAppComponent {
    void inject(MainActivity mainActivity);
}
