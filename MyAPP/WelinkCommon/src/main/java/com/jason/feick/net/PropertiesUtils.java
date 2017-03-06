package com.jason.feick.net;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by liuchao on 16/6/22.
 */
public class PropertiesUtils {
    public static String getProperty(Context context,String name){
        Properties pro = new Properties();
        InputStream is = null;
        try {
            is = context.getAssets().open("system.properties");
            pro.load(is);
            name=pro.getProperty(name);
            return name;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
