package com.welink.myapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.welink.myapp.API;
import com.welink.myapp.R;
import com.welink.myapp.util.TimeUtils;
import com.welink.myapp.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

@ContentView(R.layout.activity_weather)
public class WeatherActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.imgback)
    private ImageView imgback;
    @ViewInject(R.id.image_weather_today)
    private ImageView image_weather_today;
    @ViewInject(R.id.text_weather_change)
    private TextView text_weather_change;
    @ViewInject(R.id.text_weather_time)
    private TextView text_weather_time;
    @ViewInject(R.id.text_weather_adress)
    private TextView text_weather_adress;
    @ViewInject(R.id.text_weather_nongli)
    private TextView text_weather_nongli;
    @ViewInject(R.id.text_weather_data)
    private TextView text_weather_data;
    @ViewInject(R.id.text_weather_wendu)
    private TextView text_weather_wendu;
    @ViewInject(R.id.text_weather_chuanyi)
    private TextView text_weather_chuanyi;
    @ViewInject(R.id.text_weather_ganmao)
    private TextView text_weather_ganmao;
    @ViewInject(R.id.text_weather_kongtiao)
    private TextView text_weather_kongtiao;
    @ViewInject(R.id.text_weather_wuran)
    private TextView text_weather_wuran;
    @ViewInject(R.id.text_weather_yundong)
    private TextView text_weather_yundong;
    @ViewInject(R.id.text_weather_ziwai)
    private TextView text_weather_ziwai;
    @ViewInject(R.id.text_weather_xiche)
    private TextView text_weather_xiche;
    @ViewInject(R.id.text_weather_data1)
    private TextView text_weather_data1;
    @ViewInject(R.id.text_weather_wendu1)
    private TextView text_weather_wendu1;
    @ViewInject(R.id.image_weather_tian1)
    private ImageView image_weather_tian1;
    @ViewInject(R.id.text_weather_data2)
    private TextView text_weather_data2;
    @ViewInject(R.id.text_weather_wendu2)
    private TextView text_weather_wendu2;
    @ViewInject(R.id.image_weather_tian2)
    private ImageView image_weather_tian2;
    @ViewInject(R.id.text_weather_data3)
    private TextView text_weather_data3;
    @ViewInject(R.id.text_weather_wendu3)
    private TextView text_weather_wendu3;
    @ViewInject(R.id.image_weather_tian3)
    private ImageView image_weather_tian3;
    @ViewInject(R.id.text_weather_data4)
    private TextView text_weather_data4;
    @ViewInject(R.id.text_weather_wendu4)
    private TextView text_weather_wendu4;
    @ViewInject(R.id.image_weather_tian4)
    private ImageView image_weather_tian4;
    @ViewInject(R.id.text_weather_data5)
    private TextView text_weather_data5;
    @ViewInject(R.id.text_weather_wendu5)
    private TextView text_weather_wendu5;
    @ViewInject(R.id.image_weather_tian5)
    private ImageView image_weather_tian5;
    @ViewInject(R.id.text_weather_tian)
    private TextView text_weather_tian;
    private String city;


    private Handler handler  = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                text_weather_data.setText(TimeUtils.getData_yyyy_MM_dd());
                text_weather_time.setText(TimeUtils.getData_HH()+":"+TimeUtils.getData_mm());
            }
        }
    };


    private Timer timer = new Timer(true);
    private TimerTask task = new TimerTask() {
        public void run() {
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
        }
    };
    private Object data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        registerListener();
        setDate();
        timer.schedule(task, 0, 3 * 1000);
        getData();
    }

    private void setDate() {
        text_weather_data.setText(TimeUtils.getData_yyyy_MM_dd());
        text_weather_time.setText(TimeUtils.getData_HH()+":"+TimeUtils.getData_mm());
        SharedPreferences mysp = getSharedPreferences("search_history", 0);
        city = mysp.getString("city", "北京");
        text_weather_adress.setText(city);
    }

    private void registerListener() {
        imgback.setOnClickListener(this);
        text_weather_change.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgback:
                finish();
                break;
            case R.id.text_weather_change:
                startActivityForResult(new Intent(this,SelectCityActivity.class),99);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 99:
                if (data!=null){
                    city=data.getStringExtra("lngCityName");
                    text_weather_adress.setText(city);
                    SharedPreferences mysp = getSharedPreferences("search_history", 0);
                    SharedPreferences.Editor myeditor = mysp.edit();
                    myeditor.putString("city", city);
                    myeditor.commit();
                    getData();
                }
                break;

        }
    }

    public void getData() {
        RequestParams params = new RequestParams(API.weather);    // 网址(请替换成实际的网址)
                params.addBodyParameter("key", API.weather_key);
                params.addBodyParameter("cityname",city);// 参数(请替换成实际的参数与值)

                x.http().post(params, new Callback.CommonCallback<String>() {

                                @Override
                        public void onCancelled(CancelledException arg0) {

                            }

                                // 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
                                // 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
                                @Override
                        public void onError(Throwable ex, boolean isOnCallback) {

                                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                                if (ex instanceof HttpException) { // 网络错误
                                        HttpException httpEx = (HttpException) ex;
                                        int responseCode = httpEx.getCode();
                                        String responseMsg = httpEx.getMessage();
                                        String errorResult = httpEx.getResult();
                                    System.out.println("2");
                                        // ...
                                    } else { // 其他错误
                                    System.out.println("1");
                                    }

                            }

                                // 不管成功或者失败最后都会回调该接口
                                @Override
                        public void onFinished() {

                            }
                        @Override
                        public void onSuccess(String arg0) {
                            System.out.println(arg0);
                            getDatas(arg0);
                            }


                });

    }
    private void getDatas(String arg0) {
        int error_code;
        String nongli;
        String wendu;
        String tianqi;
        String kong;
        String yun;
        String ziwai;
        String gan;
        String xi;
        String wu;
        String chuan;
        try {
            JSONObject json=new JSONObject(arg0);
            error_code=json.getInt("error_code");
            JSONObject  result=json.getJSONObject("result");
            JSONObject data=result.getJSONObject("data");
            JSONObject realtime=data.getJSONObject("realtime");
            nongli=realtime.getString("moon");
            JSONObject weather=realtime.getJSONObject("weather");
            wendu=weather.getString("temperature");
            tianqi=weather.getString("info");
            JSONObject life=data.getJSONObject("life");
            JSONObject info=life.getJSONObject("info");
            JSONArray kongtiao=info.getJSONArray("kongtiao");
            kong=kongtiao.get(0)+","+kongtiao.get(1);
            JSONArray yundong=info.getJSONArray("yundong");
            yun=yundong.get(0)+","+yundong.get(1);
            JSONArray ziwaixian=info.getJSONArray("ziwaixian");
            ziwai=ziwaixian.get(0)+","+ziwaixian.get(1);
            JSONArray ganmao=info.getJSONArray("ganmao");
            gan=ganmao.get(0)+","+ganmao.get(1);
            JSONArray xiche=info.getJSONArray("xiche");
            xi=xiche.get(0)+","+xiche.get(1);
//            JSONArray wuran=info.getJSONArray("wuran");
//            wu=wuran.get(0)+","+wuran.get(1);
            JSONArray chuanyi=info.getJSONArray("chuanyi");
            chuan=chuanyi.get(0)+","+chuanyi.get(1);

            JSONArray weathers=data.getJSONArray("weather");
            for (int i=1;i<weathers.length();i++){
                String week=weathers.getJSONObject(i).getString("week");
                String day=weathers.getJSONObject(i).getJSONObject("info").getJSONArray("day").getString(1);
                String wend=weathers.getJSONObject(i).getJSONObject("info").getJSONArray("day").getString(2);
                setDates(i,week,day,wend);
            }


            if (error_code==0){
                text_weather_nongli.setText("农历："+nongli);
                text_weather_wendu.setText(wendu+"℃");
                text_weather_chuanyi.setText("穿衣指数："+chuan);
                text_weather_ganmao.setText("感冒指数："+gan);
                text_weather_kongtiao.setText("空调指数："+kong);
                text_weather_wuran.setText("污染指数：");
                text_weather_yundong.setText("运动指数："+yun);
                text_weather_ziwai.setText("紫外线指数："+ziwai);
                text_weather_xiche.setText("洗车指数："+xi);
                setImage(image_weather_today,tianqi);
                text_weather_tian.setText(tianqi);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    private void setDates(int i, String week, String day, String wend) {
        switch (i){
            case 1:
                text_weather_data1.setText(Utils.getWeek(week));
                setImage(image_weather_tian1,day);
                text_weather_wendu1.setText(wend+"℃");
                break;
            case 2:
                text_weather_data2.setText(Utils.getWeek(week));
                setImage(image_weather_tian2,day);
                text_weather_wendu2.setText(wend+"℃");
                break;
            case 3:
                text_weather_data3.setText(Utils.getWeek(week));
                setImage(image_weather_tian3,day);
                text_weather_wendu3.setText(wend+"℃");
                break;
            case 4:
                text_weather_data4.setText(Utils.getWeek(week));
                setImage(image_weather_tian4,day);
                text_weather_wendu4.setText(wend+"℃");
                break;
            case 5:
                text_weather_data5.setText(Utils.getWeek(week));
                setImage(image_weather_tian5,day);
                text_weather_wendu5.setText(wend+"℃");
                break;
        }
    }

    private void setImage(ImageView image, String day) {
        if (day.equals("多云") || day.equals("阴")){
            image.setImageResource(R.mipmap.ic_icon_yun);
        }else if (day.equals("雷阵雨") ){
            image.setImageResource(R.mipmap.ic_icon_lei);
        }else if (day.equals("晴") ){
            image.setImageResource(R.mipmap.ic_icon_tai);
        }else if (day.equals("小雨") || day.equals("中雨")|| day.equals("阵雨")){
            image.setImageResource(R.mipmap.ic_icon_yu);
        }
    }
}
