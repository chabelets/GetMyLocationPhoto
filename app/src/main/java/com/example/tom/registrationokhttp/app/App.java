package com.example.tom.registrationokhttp.app;

import android.app.Application;

import com.example.tom.registrationokhttp.observer.Net;
import com.example.tom.registrationokhttp.locationmanager.ILocation;
import com.example.tom.registrationokhttp.locationmanager.MyLocationManager;
import com.example.tom.registrationokhttp.network.Network;
import com.example.tom.registrationokhttp.utils.AppSettings;

public class App extends Application {

    private static Net net = new Network();
    private static AppSettings appSettings;
    private static App appContext;
    private static ILocation locationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        net = new Network();
        locationManager = new MyLocationManager(this);
        appSettings = new AppSettings(this);
        appContext = this;
    }

    public static Net getObserver() {
        return net;
    }

    public static AppSettings getAppSettings() {
        return appSettings;
    }

    public static App getAppContext() {
        return appContext;
    }

    public static ILocation getLocationManager(){
        return locationManager;
    }
}
