package com.ssb.apps.bookapp.apps;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.utils.FontOverride;
import com.ssb.apps.bookapp.utils.PreferenceConnector;


import androidx.appcompat.app.AppCompatDelegate;


/**
 * Created by prateek on 11/05/2018.
 */

public class BookApp extends Application {
    private static BookApp mInstance;

    private static Context mContext;
    public static String currency_code = "";
    public static PreferenceConnector cache;

    private static SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;
    private final String PREFS_NAME = "BookApp";
    public static String SessionKey = "j5aD9uweHEAncbhd";// Must have 16 character session key
    public static double lat = 0.0f, longi = 0.0f;
/*    Fabric fabric;*/

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        BookApp.mContext = getApplicationContext();

        mInstance = this;
        currency_code = getString(R.string.str_currency_code);
        //MultiDex.install(this);
        sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        // Make sure we use vector drawables
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        cache = new PreferenceConnector();

        /*fabric = new Fabric.Builder(mContext)
                .kits(new Crashlytics())
                .debuggable(true)  // Enables Crashlytics debugger
                .build();
        Fabric.with(fabric);*/


        FontOverride.setDefaultFont(this, "DEFAULT", "fonts/roboto_bold.ttf");
        FontOverride.setDefaultFont(this, "MONOSPACE", "fonts/gothicb.ttf");
        FontOverride.setDefaultFont(this, "SERIF", "fonts/century_gothic.ttf");
        FontOverride.setDefaultFont(this, "SANS_SERIF", "fonts/century_gothic.ttf");



    }

    public static synchronized BookApp getInstance() {
        return mInstance;
    }


}
