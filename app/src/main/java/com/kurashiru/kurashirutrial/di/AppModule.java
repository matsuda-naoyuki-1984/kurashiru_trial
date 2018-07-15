package com.kurashiru.kurashirutrial.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kurashiru.kurashirutrial.api.RequestInterceptor;
import com.kurashiru.kurashirutrial.api.service.KurashiruService;
import com.kurashiru.kurashirutrial.pref.DefaultPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    static final String SHARED_PREF_NAME = "preferences";

    private Context context;

    public AppModule(Application app) {
        context = app;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Provides
    public Interceptor provideRequestInterceptor(RequestInterceptor interceptor) {
        return interceptor;
    }

    @Provides
    public DefaultPreference provideDefaultPreference() {
        return DefaultPreference.get(context);
    }

    @Provides
    public SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public KurashiruService provideKurashiruService(OkHttpClient client) {
        return new Retrofit.Builder().client(client)
                .baseUrl("https://s3-ap-northeast-1.amazonaws.com/data.kurashiru.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .build()
                .create(KurashiruService.class);
    }

    private static Gson createGson() {
        return new GsonBuilder().setDateFormat("yyyy/MM/dd HH:mm:ss").create();
    }
}
