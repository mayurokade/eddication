package com.ssb.apps.bookapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.apps.BookApp;
import com.ssb.apps.bookapp.databinding.ActivitySplashBinding;
import com.ssb.apps.bookapp.utils.Constant;
import com.ssb.apps.bookapp.utils.IOUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class ActivitySplash extends AppCompatActivity {
    ActivitySplashBinding binding;
    Animation animFadein;
   public static Context BASE_CONTEXT;
    public View rootView;
    public static Context contextOfApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BASE_CONTEXT = ActivitySplash.this;
        contextOfApplication = getApplicationContext();

        //setContentView(R.layout.activity_splash);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        rootView = getWindow().getDecorView().findViewById(android.R.id.content);

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        }*/

// start the animation
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_left);
        binding.ivLogo.startAnimation(hyperspaceJumpAnimation);
        // binding.ivLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.cycle));
        IOUtils.startLoadingView(BASE_CONTEXT);


        //  Log.e("screen size", IOUtils.getScreenSize(this));
        //  Log.e("dpi", IOUtils.getDeviceDpi(this));

        if (IOUtils.isInternetPresent(BASE_CONTEXT)) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    boolean flag = BookApp.cache.readBoolean(ActivitySplash.this, Constant.ISLOGIN, false);
                    Log.e("test", "run:boolean value  "+flag );
                    IOUtils.stopLoadingView();
                    Intent intent;
                    if (flag)
                        intent = new Intent(BASE_CONTEXT, MainActivity.class);
                    else
                        intent = new Intent(BASE_CONTEXT, ActivityLogin2.class);

                        // Closing all the Activities
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        // Add new Flag to start new Activity
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter_right, R.anim.exit_left);
                    }
                },Constant.SPLASH_TIME_OUT);
            } else{
                IOUtils.stopLoadingView();
                IOUtils.mySnackBarInternet(BASE_CONTEXT, rootView);
            }

        }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    }
