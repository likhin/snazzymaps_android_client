package com.nikhilbawane.snazzymaps.collapsiblemaps;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nikhilbawane.snazzymaps.model.Page;
import com.nikhilbawane.snazzymaps.rest.ApiHandler;
import com.nikhilbawane.snazzymaps.rest.SnazzyAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Presenter class for CollapsibleMaps
 * <p>
 * Created by Nikhil on 08-08-2017.
 */

public class CollapsibleMapsPresenter implements CollapsibleMapsContract.Presenter {

    @NonNull
    private final CollapsibleMapsContract.View collapsibleMapsView;

    private String apiKey;

    CollapsibleMapsPresenter(@NonNull CollapsibleMapsContract.View collapsibleMapsView, String apiKey) {
        this.collapsibleMapsView = checkNotNull(collapsibleMapsView, "collapsibleMapsView cannot be null");
        this.apiKey = apiKey;
        this.collapsibleMapsView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void foo() {
        ApiHandler apiHandler = new ApiHandler(apiKey);

        SnazzyAPI snazzyAPI = apiHandler.start().getAPI();
        Call<Page> call = snazzyAPI.loadStylesOnPage("pokemon", null, null, null, null);
        call.enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Page page = response.body();
                collapsibleMapsView.initializeRecyclerViewAdapter(page.getStyles());
            }

            @Override
            public void onFailure(Call<Page> call, Throwable t) {

            }
        });
    }
}
