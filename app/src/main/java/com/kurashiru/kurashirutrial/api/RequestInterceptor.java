package com.kurashiru.kurashirutrial.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class RequestInterceptor implements Interceptor {

    private final ConnectivityManager mConnectivityManager;

    private final Context mContext;

    @Inject
    RequestInterceptor(Context context, ConnectivityManager connectivityManager) {
        mContext = context;
        mConnectivityManager = connectivityManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder r = chain.request().newBuilder();

        if (isConnected()) {
            int maxAge = 2 * 60;
            r.addHeader("cache-control", "public, max-age=" + maxAge);
        } else {
            int maxStale = 30 * 24 * 60 * 60;
            r.addHeader("cache-control", "public, only-if-cached, max-stale=" + maxStale);
        }

        return chain.proceed(r.build());
    }

    private boolean isConnected() {
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
