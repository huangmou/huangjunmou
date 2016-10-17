package com.welink.myapp;

import android.app.Application;

import org.xutils.x;

import cn.bmob.v3.Bmob;

/**
 * Created by dell on 2016/7/7.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "1374bcf49d890a8d4cb1a237d00e1139");
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

    }





}
