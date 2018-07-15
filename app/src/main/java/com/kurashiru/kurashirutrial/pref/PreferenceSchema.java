package com.kurashiru.kurashirutrial.pref;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class PreferenceSchema {
    private SharedPreferences prefs;

    public PreferenceSchema() {
    }

    protected void init(Context context, String tableName) {
        this.prefs = context.getSharedPreferences(tableName, 0);
    }

    protected void init(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    protected void putBoolean(String key, boolean value) {
        this.prefs.edit().putBoolean(key, value).apply();
    }

    protected void putString(String key, String value) {
        this.prefs.edit().putString(key, value).apply();
    }

    protected void putFloat(String key, float value) {
        this.prefs.edit().putFloat(key, value).apply();
    }

    protected void putInt(String key, int value) {
        this.prefs.edit().putInt(key, value).apply();
    }

    protected void putLong(String key, long value) {
        this.prefs.edit().putLong(key, value).apply();
    }

    protected void putStringSet(String key, Set<String> value) {
        this.prefs.edit().putStringSet(key, value).apply();
    }

    protected boolean getBoolean(String key, boolean defValue) {
        return this.prefs.getBoolean(key, defValue);
    }

    protected String getString(String key, String defValue) {
        return this.prefs.getString(key, defValue);
    }

    protected float getFloat(String key, float defValue) {
        return this.prefs.getFloat(key, defValue);
    }

    protected int getInt(String key, int defValue) {
        return this.prefs.getInt(key, defValue);
    }

    protected long getLong(String key, long defValue) {
        return this.prefs.getLong(key, defValue);
    }

    protected Set<String> getStringSet(String key, Set<String> defValue) {
        return this.prefs.getStringSet(key, defValue);
    }

    protected boolean has(String key) {
        return this.prefs.contains(key);
    }

    protected void remove(String key) {
        this.prefs.edit().remove(key).apply();
    }

    public void clear() {
        this.prefs.edit().clear().apply();
    }
}