package com.nikhilbawane.snazzymaps.rest;

import android.support.annotation.Nullable;

import com.nikhilbawane.snazzymaps.model.Page;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface for the RestAPI
 * <p>
 * Created by Nikhil on 06-08-2017.
 */

public interface SnazzyAPI {

    @GET("explore.json/")
    Call<Page> loadStyles();

    @GET("explore.json/")
    Call<Page> loadStylesWithText(@Query("text") String text);

    @GET("explore.json/")
    Call<Page> loadStylesWithSort(@Query("sort") String sort);

    @GET("explore.json/")
    Call<Page> loadStylesWithTag(@Query("tag") String tag);

    @GET("explore.json/")
    Call<Page> loadStylesWithColor(@Query("color") String color);

    @GET("explore.json/")
    Call<Page> loadStylesOnPage(@Nullable @Query("text") String text,
                                @Nullable @Query("sort") String sort,
                                @Nullable @Query("tag") String tag,
                                @Nullable @Query("color") String color,
                                @Nullable @Query("page") Integer page);
}
