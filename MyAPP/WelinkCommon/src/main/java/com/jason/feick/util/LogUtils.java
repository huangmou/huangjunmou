package com.jason.feick.util;

import android.util.Log;

/**
 * Created by liuchao on 16/9/7.
 */
public class LogUtils {
    public final static String Tag="GetData";

    public static boolean isDebug=true;

    public static void setDebugMode(boolean mode){
        isDebug=mode;
    }

    public static void e(String result){
        if (isDebug){
            Log.e(Tag,result);
            Log.e(Tag,"[----------------------------------------------------------------------------------------------------------------------------------------------]\n");
        }
    }

    public static void i(String result){
        if (isDebug){
            Log.i(Tag,result);
        }
    }

    public static void v(String result){
        if (isDebug){
            Log.v(Tag,result);
        }
    }

    public static void d(String result){
        if (isDebug){
            Log.d(Tag,result);
        }
    }
}
