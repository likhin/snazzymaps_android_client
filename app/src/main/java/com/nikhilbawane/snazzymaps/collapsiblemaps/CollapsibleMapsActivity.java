package com.nikhilbawane.snazzymaps.collapsiblemaps;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nikhilbawane.snazzymaps.R;
import com.nikhilbawane.snazzymaps.adapter.StylesAdapter;
import com.nikhilbawane.snazzymaps.location.LocationHandler;
import com.nikhilbawane.snazzymaps.styledmap.StyledMapFragment;
import com.nikhilbawane.snazzymaps.util.Util;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by Nikhil on 08-08-2017.
 */

public class CollapsibleMapsActivity extends AppCompatActivity
        implements CollapsibleMapsContract.View {

    private static boolean isNightModeOn;
    private static int navBarHeight;
    private static int statBarHeight;
    private static int actBarHeight;
    private static int EIGHT_DP = Util.dpToPx(8);

    @BindString(R.string.snazzy_key) String API_KEY;
    @BindView(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.app_bar) AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_layout) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar_card) CardView toolbarCard;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.pull_up) TextView pullUp;
    @BindView(R.id.styles_recycler) RecyclerView stylesRecycler;
    @BindView(R.id.fabLocation) FloatingActionButton fabLocation;
    @BindView(R.id.fabDayNight) FloatingActionButton fabDayNight;
    private LocationHandler locationHandler;
    private SharedPreferences preferences;

    private CollapsibleMapsContract.Presenter collapsibleMapsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsible);
        ButterKnife.bind(this);

        // Get measurements of system UI components
        navBarHeight = Util.getNavigationBarHeight(this);
        statBarHeight = Util.getStatusBarHeight(this);
        actBarHeight = Util.getActionBarHeight(this);

        preferences = getSharedPreferences("map_prefs", MODE_PRIVATE);
        isNightModeOn = preferences.getBoolean("night_mode", false);
        if (isNightModeOn)
            setFabToNight();
        else
            setFabToDay();

        locationHandler = new LocationHandler(this);
        new CollapsibleMapsPresenter(this, API_KEY);
        collapsibleMapsPresenter.foo();

        stylesRecycler.setLayoutManager(new LinearLayoutManager(this));
        stylesRecycler.setPadding(0, (2*EIGHT_DP) + statBarHeight, 0, navBarHeight);

        setSupportActionBar(toolbar);
        coordinatorLayout.bringChildToFront(toolbarCard);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setCustomView(R.layout.toolbar_map);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
        }

        // Set status bar offset and margins to the Toolbar
        // The offset is needed when status bar is translucent
        if (Util.isStatusBarTranslucent(this)) {
            Log.i("CollapsibleMaps", "statusbar is translucent.");
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) toolbarCard.getLayoutParams();
            //CollapsingToolbarLayout.LayoutParams params = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
            params.topMargin = EIGHT_DP + statBarHeight;
            toolbarCard.setLayoutParams(params);
        }

        // Hide the "Pull up" handle when dragged up
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset < -actBarHeight) {
                    pullUp.setVisibility(View.INVISIBLE);
                } else {
                    pullUp.setVisibility(View.VISIBLE);
                }
            }
        });

        // Disable "Drag" for AppBarLayout
        // i.e. map can be used without appbar intercepting touches
        if (appBarLayout.getLayoutParams() != null) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
            AppBarLayout.Behavior appBarLayoutBehaviour = new AppBarLayout.Behavior();
            appBarLayoutBehaviour.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                @Override
                public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                    return false;
                }
            });
            layoutParams.setBehavior(appBarLayoutBehaviour);
        }

        // Set the height for the map window,
        // enough to leave space for the pull-up handle
        coordinatorLayout.post(new Runnable() {
            @Override
            public void run() {
                Point point = new Point();
                getWindowManager().getDefaultDisplay().getSize(point);
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
                params.height = point.y - actBarHeight;
                Log.i("CollapsibleMaps", "point.y = " + point.y);
                appBarLayout.setLayoutParams(params);
            }
        });
    }

    @OnClick(R.id.pull_up)
    public void onClickPullUp() {
        appBarLayout.setExpanded(false, true);
    }

    @OnClick(R.id.fabLocation)
    public void onClickLocationFab() {
        if (!locationHandler.moveMapToCurrentLocation(
                ((StyledMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getmMap()
        )) {
            Snackbar.make(fabLocation, "Location permission required.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @OnClick(R.id.fabDayNight)
    public void onClickDayNightFab() {
        if (isNightModeOn) {
            preferences.edit().putBoolean("night_mode", false).apply();
            setFabToDay();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            preferences.edit().putBoolean("night_mode", true).apply();
            setFabToNight();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        recreate();
    }

    private void setFabToDay() {
        fabDayNight.setImageResource(R.drawable.ic_dn_sun);
    }

    private void setFabToNight() {
        fabDayNight.setImageResource(R.drawable.ic_dn_moon);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setPresenter(CollapsibleMapsContract.Presenter presenter) {
        collapsibleMapsPresenter = checkNotNull(presenter);
    }

    @Override
    public void initializeRecyclerViewAdapter(List styles) {
        stylesRecycler.setAdapter(new StylesAdapter(styles));
    }
}
