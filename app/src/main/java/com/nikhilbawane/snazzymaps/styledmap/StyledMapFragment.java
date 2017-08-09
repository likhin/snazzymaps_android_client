package com.nikhilbawane.snazzymaps.styledmap;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.nikhilbawane.snazzymaps.util.Util;

/**
 * A MapFragment to apply the styles
 * <p>
 * Created by Nikhil on 09-08-2017.
 */

public class StyledMapFragment extends SupportMapFragment implements OnMapReadyCallback {

    private static int navBarHeight;
    private static int statBarHeight;
    private static int actBarHeight;
    private static int EIGHT_DP = Util.dpToPx(8);
    private GoogleMap mMap;
    private SharedPreferences preferences;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        this.getMapAsync(this);

        // Get measurements of system UI components
        navBarHeight = Util.getNavigationBarHeight(context);
        statBarHeight = Util.getStatusBarHeight(context);
        actBarHeight = Util.getActionBarHeight(context);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setPadding(0, (2 * EIGHT_DP) + statBarHeight + actBarHeight, 0, 0);
        mMap.setTrafficEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setCompassEnabled(true);
    }
}
