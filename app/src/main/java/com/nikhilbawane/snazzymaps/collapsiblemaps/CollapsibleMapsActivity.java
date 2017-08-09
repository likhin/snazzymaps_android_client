package com.nikhilbawane.snazzymaps.collapsiblemaps;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.nikhilbawane.snazzymaps.R;
import com.nikhilbawane.snazzymaps.adapter.StylesAdapter;
import com.nikhilbawane.snazzymaps.util.Util;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.styles_recycler) RecyclerView stylesRecycler;
    private SharedPreferences preferences;

    private CollapsibleMapsContract.Presenter collapsibleMapsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsible);
        ButterKnife.bind(this);

        preferences = getSharedPreferences("map_prefs", MODE_PRIVATE);
        isNightModeOn = preferences.getBoolean("night_mode", false);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Get measurements of system UI components
        navBarHeight = Util.getNavigationBarHeight(this);
        statBarHeight = Util.getStatusBarHeight(this);
        actBarHeight = Util.getActionBarHeight(this);

        // Set status bar offset and margins to the Toolbar
        // The offset is needed when status bar is translucent
        if (Util.isStatusBarTranslucent(this)) {
            Log.i("CollapsibleMaps", "statusbar is translucent.");
            //CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) toolbar.getLayoutParams();
            CollapsingToolbarLayout.LayoutParams params = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
            params.topMargin = EIGHT_DP + statBarHeight;
            toolbar.setLayoutParams(params);
        }

        new CollapsibleMapsPresenter(this, API_KEY);

        stylesRecycler.setLayoutManager(new LinearLayoutManager(this));

        collapsibleMapsPresenter.foo();

    }

    @Override
    public void setPresenter(CollapsibleMapsContract.Presenter presenter) {
        collapsibleMapsPresenter = checkNotNull(presenter);
    }

    @Override
    public void initializeRecyclerViewAdapter(List styles) {
        stylesRecycler.setAdapter(new StylesAdapter(styles));
    }
}
