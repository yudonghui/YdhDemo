package com.railway.ydhdemo.component;

import com.railway.ydhdemo.inject.module.AppModule;
import com.railway.ydhdemo.inject.module.FragmentModule;
import com.railway.ydhdemo.ui.fragment.HomeFragment;

import dagger.Component;

@Component(modules = {FragmentModule.class, AppModule.class})
public interface FragmentAppComponent {
    void inject(HomeFragment homeFragment);
}
