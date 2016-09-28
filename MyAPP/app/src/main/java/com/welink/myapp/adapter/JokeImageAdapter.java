package com.welink.myapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.welink.myapp.R;
import com.welink.myapp.entry.JokeEntry;
import com.welink.myapp.util.AlxGifHelper;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by dell on 2016/9/27.
 */
public class JokeImageAdapter  extends CommonAdapter {
    private List<JokeEntry> list;
    private Context context;
    private int type;
    private int screenWidth;

    public JokeImageAdapter(Context context, List mDatas, int itemLayoutId,int type) {
        super(context, mDatas, itemLayoutId);
        this.list=mDatas;
        this.context=context;
        this.type=type;
        screenWidth=getScreenWidth((Activity) context);
    }

    @Override
    public void convert(ViewHolder helper, Object item) {
        helper.setText(R.id.text_joke_context,((JokeEntry)item).getContent());
//        helper.setText(R.id.text_joke_time,"更新时间："+((JokeEntry)item).getUpadtetime());
        setSrcGif(helper.getView(R.id.gif_group_1),((JokeEntry)item).getUrl());

    }

    private void setSrcGif(View view, String src){
        AlxGifHelper.displayImage(src,
                (GifImageView) view.findViewById(R.id.gif_photo_view),
                (ProgressWheel) view.findViewById(R.id.progress_wheel),
                (TextView) view.findViewById(R.id.tv_progress),
                screenWidth-100//gif显示的宽度为屏幕的宽度减去边距
        );
    }
    public int getScreenWidth(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }
}
