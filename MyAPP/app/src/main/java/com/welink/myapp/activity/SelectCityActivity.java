package com.welink.myapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.welink.myapp.MyApp;
import com.welink.myapp.R;
import com.welink.myapp.entry.City;
import com.welink.myapp.util.Utils;
import com.welink.myapp.view.MyLetterListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class SelectCityActivity extends Activity {

    private MyApp app;
    private ListAdapter adapter;
    private ListView personList;
    private ImageView imgback;
    private TextView overlay; // 对话框首字母textview
    private MyLetterListView letterListView; // A-Z listview
    private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
    private String[] sections;// 存放存在的汉语拼音首字母
    private Handler handler;
    private OverlayThread overlayThread; // 显示首字母对话框
    private ArrayList<City> allCity_lists; // 所有城市列表
    private ArrayList<City> ShowCity_lists; // 需要显示的城市列表-随搜索而改变
    private ArrayList<City> city_lists;// 城市列表
    private String lngCityName ="";//存放返回的城市名
    private JSONArray chineseCities ;
    private List<String> historys;
    private TextView text_select_city_clean;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);//定位时间
                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.getCountry();//国家信息
                    amapLocation.getProvince();//省信息
                    amapLocation.getCity();//城市信息
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                    amapLocation.getAoiName();//获取当前定位点的AOI信息
                    lng_city.setText(Utils.getOnPlace( amapLocation.getCity()));
                    lngCityName=amapLocation.getCity();
                    System.out.println(amapLocation.getAddress());
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    private EditText sh;
    private TextView lng_city;
    private LinearLayout lng_city_lay;
    private ProgressDialog progress;
    private static final int SHOWDIALOG = 2;
    private static final int DISMISSDIALOG = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        personList = (ListView) findViewById(R.id.list_view);
        app= (MyApp) getApplication();
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        allCity_lists = new ArrayList<City>();
        letterListView = (MyLetterListView) findViewById(R.id.MyLetterListView01);
        lng_city_lay = (LinearLayout) findViewById(R.id.lng_city_lay);
        text_select_city_clean=(TextView)findViewById(R.id.text_select_city_clean);
        sh = (EditText) findViewById(R.id.sh);
        lng_city = (TextView) findViewById(R.id.lng_city);
        imgback = (ImageView) findViewById(R.id.imgback);

        letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        alphaIndexer = new HashMap<String, Integer>();
        handler = new Handler();
        overlayThread = new OverlayThread();
        personList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent();
                System.out.println("----------------------------"+ShowCity_lists.get(arg2).name);
                intent.putExtra("lngCityName", ShowCity_lists.get(arg2).name);
                save( ShowCity_lists.get(arg2).name);
                setResult(99,intent);
                finish();
            }
        });
        text_select_city_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanHistory();
            }
        });
        lng_city_lay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("lngCityName",lngCityName);
                save(lngCityName);
                setResult(99,intent);
                finish();
            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initGps();
        initOverlay();
        handler2.sendEmptyMessage(SHOWDIALOG);
        Thread thread = new Thread(){
            @Override
            public void run() {
                hotCityInit();
                handler2.sendEmptyMessage(DISMISSDIALOG);
                super.run();
            }
        };
        thread.start();
    }
    public void save(String keyword) {
        // 获取搜索框信息
        String text = keyword;
        SharedPreferences mysp = getSharedPreferences("search_history", 0);
        String old_text = mysp.getString("history", "");

        // 利用StringBuilder.append新增内容，逗号便于读取内容时用逗号拆分开
        StringBuilder builder = new StringBuilder(old_text);
        builder.append(text + ",");

        // 判断搜索内容是否已经存在于历史文件，已存在则不重复添加
        if (!old_text.contains(text + ",")) {
            SharedPreferences.Editor myeditor = mysp.edit();
            myeditor.putString("history", builder.toString());
            myeditor.commit();

        }
    }
    /**
     * 热门城市
     */
    public void hotCityInit() {
        City city;
        String[] s=changeArray();
        if (s!=null) {
            for (int i = 0; i < s.length; i++) {
                city = new City(s[i], "");
                allCity_lists.add(city);
            }
        }
        city_lists = getCityList();
        allCity_lists.addAll(city_lists);
        ShowCity_lists=allCity_lists;
    }

    //清除搜索记录
    public void cleanHistory(){
        SharedPreferences sp =getSharedPreferences("search_history",0);
        SharedPreferences.Editor editor=sp.edit();
        editor.clear();
        editor.commit();
        allCity_lists.clear();
        hotCityInit();
        adapter.notifyDataSetChanged();

    }

    private String[] changeArray(){
        // 获取搜索记录文件内容
        SharedPreferences sp = getSharedPreferences("search_history", 0);
        String history = sp.getString("history", null);
        // 用逗号分割内容返回数组
        if (null!=history){
            String[] history_arr = history.split(",");
            return history_arr;
        }
        return  null;
    }
    private ArrayList<City> getCityList() {
        ArrayList<City> list = new ArrayList<City>();
        try {
            chineseCities = new JSONArray(getResources().getString(R.string.citys));
            for(int i=0;i<chineseCities.length();i++){
                JSONObject jsonObject = chineseCities.getJSONObject(i);
                City city = new City(jsonObject.getString("name"), jsonObject.getString("pinyin"));
                list.add(city);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(list, comparator);
        return list;
    }

    /**
     * a-z排序
     */
    Comparator comparator = new Comparator<City>() {
        @Override
        public int compare(City lhs, City rhs) {
            String a = lhs.getPinyi().substring(0, 1);
            String b = rhs.getPinyi().substring(0, 1);
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }

        }
    };

//    @Override
//    public void onLocationChanged(AMapLocation amapLocation) {
//        if (amapLocation != null) {
//            if (amapLocation.getErrorCode() == 0) {
//                //定位成功回调信息，设置相关消息
//                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                amapLocation.getLatitude();//获取纬度
//                amapLocation.getLongitude();//获取经度
//                amapLocation.getAccuracy();//获取精度信息
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date date = new Date(amapLocation.getTime());
//                df.format(date);//定位时间
//               String address= amapLocation.getAddress();
//                lng_city.setText(address);
//            } else {
//                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
//                Log.e("AmapError", "location Error, ErrCode:"
//                        + amapLocation.getErrorCode() + ", errInfo:"
//                        + amapLocation.getErrorInfo());
//            }
//        }
//    }

//	private void setAdapter(List<City> list) {
//		adapter = new ListAdapter(this, list);
//		personList.setAdapter(adapter);
//	}

    public class ListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        final int VIEW_TYPE = 3;

        public ListAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
            alphaIndexer = new HashMap<String, Integer>();
            sections = new String[ShowCity_lists.size()];
            for (int i = 0; i < ShowCity_lists.size(); i++) {
                // 当前汉语拼音首字母
                String currentStr = getAlpha(ShowCity_lists.get(i).getPinyi());
                // 上一个汉语拼音首字母，如果不存在为“ ”
                String previewStr = (i - 1) >= 0 ? getAlpha(ShowCity_lists.get(i - 1)
                        .getPinyi()) : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = getAlpha(ShowCity_lists.get(i).getPinyi());
                    alphaIndexer.put(name, i);
                    sections[i] = name;
                }
            }
        }

        @Override
        public int getCount() {
            return ShowCity_lists.size();
        }

        @Override
        public Object getItem(int position) {
            return ShowCity_lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            // TODO Auto-generated method stub
            int type = 2;

            if (position == 0&&sh.getText().length()==0) {//不是在搜索状态下
                type = 0;
            }
            return type;
        }

        @Override
        public int getViewTypeCount() {// 这里需要返回需要集中布局类型，总大小为类型的种数的下标
            return VIEW_TYPE;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            int viewType = getItemViewType(position);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item, null);
                holder = new ViewHolder();
                holder.alpha = (TextView) convertView
                        .findViewById(R.id.alpha);
                holder.name = (TextView) convertView
                        .findViewById(R.id.name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
//				if (sh.getText().length()==0) {//搜所状态
//					holder.name.setText(list.get(position).getName());
//					holder.alpha.setVisibility(View.GONE);
//				}else if(position>0){
            //显示拼音和热门城市，一次检查本次拼音和上一个字的拼音，如果一样则不显示，如果不一样则显示

            holder.name.setText(ShowCity_lists.get(position).getName());
            String currentStr = getAlpha(ShowCity_lists.get(position).getPinyi());//本次拼音
            String previewStr = (position-1) >= 0 ? getAlpha(ShowCity_lists.get(position-1).getPinyi()) : " ";//上一个拼音
            if (!previewStr.equals(currentStr)) {//不一样则显示
                holder.alpha.setVisibility(View.VISIBLE);
                if (currentStr.equals("#")) {
                    currentStr = "历史记录";
                }
                holder.alpha.setText(currentStr);
            } else {
                holder.alpha.setVisibility(View.GONE);
            }
//				}
            return convertView;
        }

        private class ViewHolder {
            TextView alpha; // 首字母标题
            TextView name; // 城市名字
        }
    }

    // 初始化汉语拼音首字母弹出提示框
    private void initOverlay() {
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    private class LetterListViewListener implements
            MyLetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                personList.setSelection(position);
                overlay.setText(sections[position]);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                // 延迟一秒后执行，让overlay为不可见
                handler.postDelayed(overlayThread, 3000);
            }
        }

    }

    // 设置overlay不可见
    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }

    }

    // 获得汉语拼音首字母
    private String getAlpha(String str) {

        if (str.equals("-")) {
            return "&";
        }
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式，判断首字母是否是英文字母
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else {
            return "#";
        }
    }

    private void initGps() {
        try{
            mLocationOption = new AMapLocationClientOption();
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
//设置是否只定位一次,默认为false
            mLocationOption.setOnceLocation(false);

            if(mLocationOption.isOnceLocationLatest()){
                mLocationOption.setOnceLocationLatest(true);
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
//如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会。
            }

//设置是否强制刷新WIFI，默认为强制刷新
            mLocationOption.setWifiActiveScan(true);
//设置是否允许模拟位置,默认为false，不允许模拟位置
            mLocationOption.setMockEnable(false);
//设置定位间隔,单位毫秒,默认为2000ms
            mLocationOption.setInterval(2000);
//给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
//启动定位
            mLocationClient.startLocation();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mLocationClient.stopLocation();
    }


//    //定位实现
//    private class MyLocationListenner implements BDLocationListener {
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//
//            if (location == null)
//                return;
//            StringBuffer sb = new StringBuffer(256);
//            if (location.getLocType() == BDLocation.TypeGpsLocation) {
////				sb.append(location.getAddrStr());
//                sb.append(location.getCity());
//            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
//                sb.append(location.getCity());
//            }
//            System.out.println("*********xx*****"+sb.toString());
//            if (sb.toString() != null && sb.toString().length() > 0) {
//                lngCityName=sb.toString();
//                System.out.println("*************************"+lngCityName);
//                lng_city.setText(lngCityName);
//            }
//
//        }
//
//        public void onReceivePoi(BDLocation poiLocation) {
//
//        }
//    }

    Handler handler2 = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SHOWDIALOG:
                    //progress = AppUtil.showProgress(ActivitySelectCity.this, "正在加载数据，请稍候...");
                    break;
                case DISMISSDIALOG:
                    if (progress != null)
                    {
                        progress.dismiss();
                    }
                    adapter = new ListAdapter(SelectCityActivity.this);
                    personList.setAdapter(adapter);
//				personList.setAdapter(adapter);

                    sh.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count,
                                                      int after) {
                        }
                        @Override
                        public void afterTextChanged(Editable s) {
                            //搜索符合用户输入的城市名
                            if(s.length()>0){
                                ArrayList<City> changecity = new ArrayList<City>();
                                for(int i=0;i<city_lists.size();i++){
                                    if(city_lists.get(i).name.indexOf(sh.getText().toString())!=-1){
                                        changecity.add(city_lists.get(i));
                                    }
                                }
                                ShowCity_lists = changecity;
                            }else{
                                ShowCity_lists = allCity_lists;
                            }
                            adapter.notifyDataSetChanged();
                        }
                    });
                    break;
                default:
                    break;
            }
        };
    };
}
