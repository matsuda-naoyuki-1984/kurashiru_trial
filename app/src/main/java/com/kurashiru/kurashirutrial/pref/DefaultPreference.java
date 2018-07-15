package com.kurashiru.kurashirutrial.pref;

import android.content.Context;

public class DefaultPreference extends PreferenceSchema {

    private static final String TABLE_NAME = "com.kurashiru.kurashirutrial.preferences";

    private static DefaultPreference singleton;

    private DefaultPreference(Context context) {
        init(context, TABLE_NAME);
    }

    public static DefaultPreference get(Context context) {
        if (singleton != null) return singleton;
        synchronized (DefaultPreference.class) {
            if (singleton == null) {
                singleton = new DefaultPreference(context);
            }
        }
        return singleton;
    }

    public String getFavorites() {
        return getString("favorites", "");
    }

    public void setFavorites(String favorites) {
        putString("favorites", favorites);
    }
}
