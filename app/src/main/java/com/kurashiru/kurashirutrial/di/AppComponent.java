package com.kurashiru.kurashirutrial.di;


import com.kurashiru.kurashirutrial.MainApplication;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, AndroidModule.class})
public interface AppComponent {

    void inject(MainApplication application);
    ActivityComponent plus(ActivityModule module);
}
