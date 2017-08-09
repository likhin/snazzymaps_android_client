package com.nikhilbawane.snazzymaps.util;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Nikhil on 19-07-2017.
 */

public class PermissionHandler {

    public static final int BULK_PERMISSIONS_REQUEST = 101;
    public static final int STORAGE_PERMISSIONS_REQUEST = 201;
    public static final int LOCATION_PERMISSIONS_REQUEST = 202;
    private final String TAG = "PermissionHandler";

    public void requestBulkPermissions(Context context, String[] permissions) {
        ActivityCompat.requestPermissions((AppCompatActivity) context,
                permissions, BULK_PERMISSIONS_REQUEST);
    }

    public boolean isStoragePermissionGranted(Context context) {
        return !isRuntimePermissionsSupported()
                || ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    public void requestStoragePermission(Context context) {
        if (!isStoragePermissionGranted(context)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((AppCompatActivity) context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showRationaleDialog(context,
                        "Storage permission required.",
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_PERMISSIONS_REQUEST);
            } else {
                ActivityCompat.requestPermissions((AppCompatActivity) context,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_PERMISSIONS_REQUEST);

            }
        }
    }

    public boolean isLocationPermissionGranted(Context context) {
        return !isRuntimePermissionsSupported()
                || ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    public void requestLocationPermission(Context context) {
        if (!isLocationPermissionGranted(context)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((AppCompatActivity) context,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                showRationaleDialog(context,
                        "Location permission required.",
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSIONS_REQUEST);
            } else {
                ActivityCompat.requestPermissions((AppCompatActivity) context,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSIONS_REQUEST);

            }
        }
    }

    private boolean isRuntimePermissionsSupported() {
        return Build.VERSION.SDK_INT >= 23;
    }

    private void showRationaleDialog(final Context context, String message, final String[] permissions, final int requestCode) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions((AppCompatActivity) context, permissions, requestCode);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((AppCompatActivity) context).finish();
                    }
                })
                .create();
        alertDialog.show();
    }
}
