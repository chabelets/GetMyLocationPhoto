package com.example.tom.registrationokhttp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSettings {

    private final static String KEY_USER = "KEY_USER";

    private SharedPreferences mSharedPreferences;

    public AppSettings(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setIsLogged() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(KEY_USER, true);
        editor.apply();
    }

    public boolean isLogged() {
        return mSharedPreferences.getBoolean(KEY_USER, false);
    }

}
