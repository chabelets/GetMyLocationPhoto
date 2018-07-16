package com.example.tom.registrationokhttp.locationmanager;

import android.support.annotation.NonNull;

public interface ILocation {

    void startLocationUpdate(@NonNull MyLocationManager.Callback callback);

    void stopLocationUpdate();

}
