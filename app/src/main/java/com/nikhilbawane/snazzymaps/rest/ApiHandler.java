package com.nikhilbawane.snazzymaps.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Handles building a retrofit instance to the API
 * <p>
 * Created by Nikhil on 07-08-2017.
 */

public class ApiHandler {

    private static final String BASE_URL = "https://snazzymaps.com/";
    private Retrofit retrofit;
    private String apiKey;

    public ApiHandler(String apiKey) {
        this.apiKey = apiKey;
    }

    public ApiHandler start() {
        Gson gson = new GsonBuilder().setLenient().create();

        // According to the doc: https://snazzymaps.com/account/developer
        // the API key can be passed in the X-ApiKey header
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request originalRequest = chain.request();

                Request.Builder builder = originalRequest.newBuilder()
                        .header("X-ApiKey", apiKey);

                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return this;
    }

    public SnazzyAPI getAPI() {
        return retrofit.create(SnazzyAPI.class);
    }
}
