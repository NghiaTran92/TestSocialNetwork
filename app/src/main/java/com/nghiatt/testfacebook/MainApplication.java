package com.nghiatt.testfacebook;

import android.app.Application;

import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

public class MainApplication extends Application {
    private static MainApplication singleton;

    private TwitterAuthConfig authConfig;

    public static MainApplication getInstance(){
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //https://apps.twitter.com
        authConfig
                = new TwitterAuthConfig(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_SECRET);
        Fabric.with(this,new Twitter(authConfig));
    }
}
