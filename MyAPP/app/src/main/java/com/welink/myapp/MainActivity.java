package com.welink.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.welink.myapp.activity.SelectCityActivity;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_main_weather:
                startActivity(new Intent(this, SelectCityActivity.class));
                break;
        }
    }
}
