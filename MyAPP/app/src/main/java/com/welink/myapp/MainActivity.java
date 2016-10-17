package com.welink.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.welink.myapp.activity.GifTestActivity;
import com.welink.myapp.activity.JokeActivity;
import com.welink.myapp.activity.WeatherActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_main)
public class MainActivity extends Activity implements View.OnClickListener{
    @ViewInject(R.id.lin_main_weather)
    private RelativeLayout lin_main_weather;
    @ViewInject(R.id.lin_main_joke)
    private RelativeLayout lin_main_joke;
    @ViewInject(R.id.lin_main_xc)
    private RelativeLayout lin_main_xc;
    @ViewInject(R.id.lin_main_phone)
    private RelativeLayout lin_main_phone;
    @ViewInject(R.id.lin_main_dai)
    private RelativeLayout lin_main_dai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        registerListener();
    }
    private void registerListener() {
        lin_main_weather.setOnClickListener(this);
        lin_main_joke.setOnClickListener(this);
        lin_main_xc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_main_weather:
                startActivity(new Intent(this, WeatherActivity.class));
                break;
            case R.id.lin_main_joke:
                startActivity(new Intent(this, JokeActivity.class));
                break;
            case R.id.lin_main_xc:
                startActivity(new Intent(this, GifTestActivity.class));
                break;
        }
    }

    /**
     * 按两次退出
     */
    private long mExitTime;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
