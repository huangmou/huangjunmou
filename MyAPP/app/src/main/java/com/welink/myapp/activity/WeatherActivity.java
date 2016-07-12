package com.welink.myapp.activity;

import android.app.Activity;
import android.os.Bundle;

import com.welink.myapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

@ContentView(R.layout.activity_weather)
public class WeatherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }
}
