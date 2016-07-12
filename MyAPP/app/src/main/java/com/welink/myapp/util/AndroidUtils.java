package com.welink.myapp.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.TelephonyManager;

/**
 * Created by dell on 2016/7/11.
 */
public class AndroidUtils {

    /**
     * Returns version code
     * 获取版本号
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns version name
     * 获取版本名称
     * @return
     */
    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieve device ID
     *
     * @param context
     * @return
     */
    public static String getDeviceID(Context context) {
        String IMEI = "";
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        IMEI = telephonyManager.getDeviceId();

        return IMEI;
    }

    /**
     * 拨打电话
     * @param context
     * @param telNo
     */
    public static void call(Context context,String telNo){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri
                .parse("tel:" + telNo));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 系统下载
     * @param context
     * @param url
     */
    public static void dowloadBySystem(Context context, String url){
        Intent i = new Intent(Intent.ACTION_VIEW , Uri.parse(url));
        context.startActivity(i);
    }
}
