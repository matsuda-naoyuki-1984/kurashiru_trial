package com.kurashiru.kurashirutrial;

import android.app.Application;
import android.support.annotation.NonNull;

import com.kurashiru.kurashirutrial.di.AndroidModule;
import com.kurashiru.kurashirutrial.di.AppComponent;
import com.kurashiru.kurashirutrial.di.AppModule;
import com.kurashiru.kurashirutrial.di.DaggerAppComponent;


public class MainApplication extends Application {

    AppComponent appComponent;

    @NonNull
    public AppComponent getComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .androidModule(new AndroidModule(this))
                .build();
        appComponent.inject(this);

    }
}
