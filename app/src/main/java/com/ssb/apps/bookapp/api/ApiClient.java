package com.ssb.apps.bookapp.api;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ssb.apps.bookapp.activities.ActivitySplash;
import com.ssb.apps.bookapp.apps.BookApp;
import com.ssb.apps.bookapp.utils.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Mayur Rokade on 04/04/2019.
 * This class used for call retrofit method's
 */

public class ApiClient {

    private static final int TIMEOUT = 5;
    private static final ConnectionPool pool = new ConnectionPool(5, 10000, TimeUnit.MILLISECONDS);
    private static final OkHttpClient okHttpClient = new OkHttpClient().newBuilder().connectTimeout(TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(TIMEOUT, TimeUnit.MINUTES)
            .connectionPool(pool)
            .build();

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    //this method used for authenticate api call
    public static Retrofit getClient() {
        Retrofit retrofit = null;
        retrofit = new Retrofit.Builder()
                .baseUrl(BookApp.cache.readString(ActivitySplash.BASE_CONTEXT, Constant.URL, "").isEmpty() ?
                        "https://ssbdesignapps.in/" : BookApp.cache.readString(ActivitySplash.BASE_CONTEXT, Constant.URL, ""))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient.newBuilder().connectTimeout(TIMEOUT, TimeUnit.MINUTES).readTimeout(TIMEOUT, TimeUnit.MINUTES).writeTimeout(TIMEOUT, TimeUnit.MINUTES).build())
                .build();
        return retrofit;
    }
}
