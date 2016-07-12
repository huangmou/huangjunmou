package com.welink.myapp;

import android.app.Application;

import org.xutils.x;

/**
 * Created by dell on 2016/7/7.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }





}
