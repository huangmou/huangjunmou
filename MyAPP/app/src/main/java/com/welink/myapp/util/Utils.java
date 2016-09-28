package com.welink.myapp.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by dell on 2016/7/11.
 */
public class Utils {
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    private static final double EARTH_RADIUS = 6378137;

    /**
     * 获取项目SHA1
     *
     * @param context
     * @return
     */
    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            return hexString.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
     *
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static double GetDistance(double lng1, double lat1, double lng2,
                                     double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }


    /**
     * 获取SD卡已用 容量 KIB 单位
     *
     * @return
     */
    public static long getAvailaleSize() {

        File path = Environment.getExternalStorageDirectory(); // 取得sdcard文件路径
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return (availableBlocks * blockSize) / 1024;

        // (availableBlocks * blockSize)/1024 KIB 单位

        // (availableBlocks * blockSize)/1024 /1024 MIB单位

    }

    /**
     * 获取SD卡总容量KIB 单位
     *
     * @return
     */
    public static long getAllSize() {

        File path = Environment.getExternalStorageDirectory();

        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getBlockCount();
        return (availableBlocks * blockSize) / 1024;
    }

    /**
     * 获取SD卡剩余容量
     */
    public static long getCompareSize() {
        long size = getAllSize() - getAvailaleSize();
        return size;
    }

    /**
     * 电话号码隐藏处理
     *
     * @param phone
     * @return
     */
    public static String getPasswordPhone(String phone) {
        try {
            String one = phone.substring(0, 3);
            String two = phone.substring(7);
            return one + "****" + two;
        } catch (Exception e) {
            return "";
        }

    }

    public static String getOnPlace(String s) {
        int i = s.length();
        String ss = s.substring(0, i - 1);
        return ss;
    }

    public static String getWeek(String week) {
        if (week.equals("一")) {
            return "MON";
        } else if (week.equals("二")) {
            return "TUE";
        } else if (week.equals("三")) {
            return "WED";
        } else if (week.equals("四")) {
            return "THU";
        } else if (week.equals("五")) {
            return "FRI";
        } else if (week.equals("六")) {
            return "SAT";
        } else if (week.equals("日")) {
            return "SUN";
        }
        return "";
    }

    public static void overidePullTitle(Context context, PullToRefreshScrollView pull_to_refresh_scrollView, PullToRefreshListView pull_to_refresh_listView) {
        ILoadingLayout startLabels = null;
        ILoadingLayout endLabels = null;
        if (pull_to_refresh_scrollView != null) {
            startLabels = pull_to_refresh_scrollView.getLoadingLayoutProxy(true, false);
            endLabels = pull_to_refresh_scrollView.getLoadingLayoutProxy(false, true);
        }

        if (pull_to_refresh_listView != null) {
            startLabels = pull_to_refresh_listView.getLoadingLayoutProxy(true, false);
            endLabels = pull_to_refresh_listView.getLoadingLayoutProxy(false, true);
        }

        startLabels.setPullLabel("下拉刷新...");
        startLabels.setRefreshingLabel("努力载入中...");
        startLabels.setReleaseLabel("释放刷新...");
        // startLabels.setLoadingDrawable(context.getResources().getDrawable(R.mipmap.default_ptr_flip));

        endLabels.setPullLabel("上拉加载...");
        endLabels.setRefreshingLabel("正在加载...");
        endLabels.setReleaseLabel("释放加载...");
        // endLabels.setLoadingDrawable(context.getResources().getDrawable(R.mipmap.default_ptr_flip));
    }

    public static String getDataBean(String arg0,int s) {
        int error_code = 1;
        String dat = null;
        if (s==1) {
            try {
                JSONObject json = new JSONObject(arg0);
                error_code = json.getInt("error_code");
                JSONObject result = json.getJSONObject("result");
                JSONArray data = result.getJSONArray("data");
                dat = String.valueOf(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(s==2){
            try {
                JSONObject json = new JSONObject(arg0);
                error_code = json.getInt("error_code");
//                JSONObject result = json.getJSONObject("result");
                JSONArray result = json.getJSONArray("result");
                dat = String.valueOf(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (error_code == 0) {
            return dat;
        } else {
            return null;
        }

    }

    public static <T> T getPerson(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return t;
    }

    public static <T> List<T> getPersons(String jsonString, final Class<T> type) {
        Gson gson = new Gson();
        List<T> result = gson.fromJson(jsonString, new TypeToken<List<T>>() {
        }.getType());
        return result;

    }

    public static  <T> ArrayList<T> fromJsonList(String json, Class<T> cls) {
        ArrayList<T> mList = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        Gson mGson = new Gson();
        for (final JsonElement elem : array) {
            mList.add(mGson.fromJson(elem, cls));
        }
        return mList;
    }



}
