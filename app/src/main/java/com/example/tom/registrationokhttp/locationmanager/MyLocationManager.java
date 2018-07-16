package com.example.tom.registrationokhttp.locationmanager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.tom.registrationokhttp.app.Logger;
import com.example.tom.registrationokhttp.utils.GetPermission;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MyLocationManager implements ILocation{

    private static final int FASTEST_INTERVAL = 5000;
    private static final int UPDATE_INTERVAL = 10000;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private Location lastLocation;
    private Callback callback;
    private Context context;
    private LocationRequest locationRequest;

    public MyLocationManager(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        createLocationCallback();
        createLocationRequest();
        Logger.v("context ---> " + context );
        Logger.v("locationCallback ---> " + locationCallback.toString());
        Logger.v("fusedLocationClient ---> " + fusedLocationClient);
    }

    @SuppressWarnings("MissingPermission")
    public void getLastLocation(Activity activity) {
        fusedLocationClient.getLastLocation()
                .addOnCompleteListener(activity, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            lastLocation = task.getResult();
                        }
                    }
                });
    }

    @Override
    public void stopLocationUpdate() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    public interface Callback {
        void locationUpdate(Location location);
    }

    private void createLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Logger.v("onLocationResult ---> " + locationResult);
                lastLocation = locationResult.getLastLocation();
                if (lastLocation != null) {
                    callback.locationUpdate(lastLocation);
                    Logger.v("lastLocation ---> " + lastLocation.getLongitude());
                }

            }
        };
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void startLocationUpdate(@NonNull Callback callback) {
        this.callback = callback;
        GetPermission.checkPermission(context);
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    @NonNull
    private LocationCallback getLocationCallback() {
        return new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
//                List<Location> locationList = locationResult.getLocations();

                lastLocation = locationResult.getLastLocation();

                Log.v("TAG", "lastLocation ---> " + lastLocation.getLatitude());
                if (lastLocation != null) {
                    callback.locationUpdate(lastLocation);
                    Log.v("TAG", "lastLocation ---> " + lastLocation.getLongitude());
                }
            }
        };
    }

    private boolean checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED ||
                    context.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED);
            } return true;
        }
    }

