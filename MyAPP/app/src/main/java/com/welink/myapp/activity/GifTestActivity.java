package com.welink.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.welink.myapp.R;
import com.welink.myapp.util.AlxGifHelper;

import pl.droidsonroids.gif.GifImageView;

public class GifTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_test);
        int screenWidth = getScreenWidth(this);
        AlxGifHelper gifHelper = new AlxGifHelper();
        View gifGroup1 = findViewById(R.id.gif_group_1);

        AlxGifHelper.displayImage("https://qraved-staging.s3.amazonaws.com/images/journal/data/2016/08/15/54113f968e243.gif",
                (GifImageView) gifGroup1.findViewById(R.id.gif_photo_view),
                (ProgressWheel) gifGroup1.findViewById(R.id.progress_wheel),
                (TextView) gifGroup1.findViewById(R.id.tv_progress),
                screenWidth-100//gif显示的宽度为屏幕的宽度减去边距
        );
    }


    public int getScreenWidth(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }
}
