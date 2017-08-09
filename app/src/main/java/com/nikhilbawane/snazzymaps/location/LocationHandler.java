package com.nikhilbawane.snazzymaps.location;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.model.LatLng;
import com.nikhilbawane.snazzymaps.util.PermissionHandler;

/**
 * Created by Nikhil on 29-07-2017.
 */

public class LocationHandler implements LocationSource, LocationListener {

    private Context context;
    private PermissionHandler permissionHandler;
    private LocationManager locationManager;
    private CompassListener compassListener;
    private GoogleMap googleMap;
    private OnLocationChangedListener locationChangedListener;

    private Location location;
    private double latitude;
    private double longitude;
    private boolean moveMapClicked;

    private static final float ZOOM_LEVEL = 16.0f;
    private static final String TAG = "LocationHandler";

    public LocationHandler(Context context) {
        this.context = context;
        permissionHandler = new PermissionHandler();
        compassListener = new CompassListener(context, this);
    }

    public boolean checkAndRequestLocationPermission() {
        if(permissionHandler.isLocationPermissionGranted(context))
            return true;
        else {
            permissionHandler.requestLocationPermission(context);
            return false;
        }
    }

    public boolean moveMapToCurrentLocation() {

        moveMapClicked = true;

        try {
            if (checkAndRequestLocationPermission()) {
                googleMap.setMyLocationEnabled(true);
                getLocation();
                return true;
            } else {
                return false;
            }
        } catch (SecurityException e) {
            Log.i(TAG, "moveMapToCurrentLocation: SecurityException = " + e.getMessage());
            return false;
        }
    }

    private void getLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, true);

            location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                compassListener.setLocation(location);
                Log.w(TAG, "getLocation: " + "GPS is on");
                Log.i(TAG, "getLocation: " + "latitude:" + latitude + " longitude:" + longitude);
                LatLng latLng = new LatLng(latitude, longitude);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_LEVEL));
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                Log.i(TAG, "provider:" + bestProvider);
            }
        } catch (SecurityException e) {
            Log.i(TAG, "getLocation: " + "SecurityException = " + e.getMessage());
        }
    }

    @Override
    public void onLocationChanged(Location changedLocation) {

        if(changedLocation != null) {
            location = changedLocation;
            compassListener.setLocation(location);
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            if (locationChangedListener != null)
                locationChangedListener.onLocationChanged(location);
            if (moveMapClicked) {
                googleMap.setLocationSource(this);
                LatLng latLng = new LatLng(latitude, longitude);
                Log.i(TAG, "onLocationChanged: " + "lat = " + latitude + "  long = " + longitude);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_LEVEL));
                moveMapClicked = false;
            }
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(context, "Enabled new provider " + s,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(context, "Disabled provider " + s,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        locationChangedListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        locationManager.removeUpdates(this);
    }
}
