package com.b2.myapplication;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;


/**
 * Created by Rizwan Ahmed on 09/10/2020.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
