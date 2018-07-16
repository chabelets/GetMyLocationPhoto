package com.example.tom.registrationokhttp.app;

import android.util.Log;

import com.example.tom.registrationokhttp.BuildConfig;

public class Logger {

    public static void v(String message) {
        if (BuildConfig.DEBUG){
            Log.v("myLogs", message);
        }
    }

    public static void e(String errorMessage) {
        if (BuildConfig.DEBUG){
            Log.e("myLogs", errorMessage);
        }
    }
}
