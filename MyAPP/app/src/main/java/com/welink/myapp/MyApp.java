package com.welink.myapp;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

import cn.bmob.v3.Bmob;

/**
 * Created by dell on 2016/7/7.
 */
public class MyApp extends Application {
    private static Context context;
    public static Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }




    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "1374bcf49d890a8d4cb1a237d00e1139");
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }







}
