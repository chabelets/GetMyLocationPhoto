package com.example.tom.registrationokhttp.ui;


import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tom.registrationokhttp.R;
import com.example.tom.registrationokhttp.app.App;
import com.example.tom.registrationokhttp.app.Logger;
import com.example.tom.registrationokhttp.locationmanager.MyLocationManager;
import com.example.tom.registrationokhttp.utils.Starter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewFragment extends Fragment implements MyLocationManager.Callback{

    MapView mapView;
    private GoogleMap mMap;
    private App context = App.getAppContext();
    private double dLatitude;
    private double dLongitude;
    TextView latitude;
    TextView longitude;

    void addMarker() {
        LatLng myCoordinate = new LatLng(dLatitude, dLongitude);
        MapViewFragment.this.mMap.clear();
        MapViewFragment.this.mMap.addMarker(new MarkerOptions().position(myCoordinate).title("Marker Title").snippet("Marker Description"));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(myCoordinate).zoom(12).build();
        MapViewFragment.this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void locationUpdate(Location location) {
        this.dLatitude = location.getLatitude();
        this.dLongitude = location.getLongitude();
        addMarker();
        latitude.setText(String.valueOf(dLatitude));
        longitude.setText(String.valueOf(dLongitude));
        Logger.v("Location update >>>>" + dLatitude + " " + dLongitude);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_location, container, false);
        FloatingActionButton takePhoto = rootView.findViewById(R.id.addPhotoButton);
        takePhoto.setOnClickListener(createAddPhotoListener());
//        App.getLocationManager().startLocationUpdate(this);
         latitude = rootView.findViewById(R.id.latitude);
         longitude = rootView.findViewById(R.id.longitude);
        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                MapViewFragment.this.mMap = mMap;
                if (Build.VERSION.SDK_INT >= 23) {
                    if (context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                            context.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                 mMap.setMyLocationEnabled(true);
                    }
                } else {
                    mMap.setMyLocationEnabled(true);
                }
                LatLng sydney = new LatLng(-34, 151);
                MapViewFragment.this.mMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        App.getLocationManager().startLocationUpdate(this);
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        App.getLocationManager().stopLocationUpdate();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private View.OnClickListener createAddPhotoListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Starter.startAddPhotoActivity(getActivity());
            }
        };
    }
}

