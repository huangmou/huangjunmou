package com.welink.myapp.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.welink.myapp.API;
import com.welink.myapp.R;
import com.welink.myapp.entry.JokeEntry;
import com.welink.myapp.util.AlxGifHelper;
import com.welink.myapp.util.Utils;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class JokeFourFragment extends Fragment implements View.OnClickListener {
    private TextView text_joke_context_four;
    private View gif_group;
    private Button bt_next,bt_on;
    private List<JokeEntry> lists;
    private List<JokeEntry> list;
    private int num;
    private int screenWidth;


    public static JokeFourFragment newInstance(){
        return new JokeFourFragment();
    }
    public JokeFourFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_joke_four, container, false);
        text_joke_context_four=(TextView)v.findViewById(R.id.text_joke_context_four);
        gif_group=(View)v.findViewById(R.id.gif_group);
        bt_next=(Button)v.findViewById(R.id.bt_next);
        bt_on=(Button)v.findViewById(R.id.bt_on);
        list=new ArrayList<>();
        bt_on.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        num=0;
        screenWidth=getScreenWidth(getActivity());
        getData();
        return v;
    }


    public void getData() {
        RequestParams params = new RequestParams(API.romdo_jokeImage);    // 网址(请替换成实际的网址)
        params.addBodyParameter("key", API.joke_key);
        params.addBodyParameter("type", "pic");

        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
//                mListView.onRefreshComplete();
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
//                mListView.onRefreshComplete();

            }

            // 不管成功或者失败最后都会回调该接口
            @Override
            public void onFinished() {
//                mListView.onRefreshComplete();
            }

            @Override
            public void onSuccess(String arg0) {
                System.out.println("0"+arg0);
                String bean = Utils.getDataBean(arg0,2);
                lists = Utils.fromJsonList(bean, JokeEntry.class);
//                list.addAll(lists);
                FailterList(lists);
                setImage();
            }
        });

    }

    public void FailterList(List<JokeEntry> list1){
        for(int i=0;i<list1.size();i++){
            String url=list1.get(i).getUrl();
            String url1=url.substring(url.length()-4,url.length());
            if (url1.equals(".gif")){
                list.add(list1.get(i));
            }
        }
    }

    private void setImage(){
            if (list.size() != num + 1) {
                if (list.size() != 0) {
                    AlxGifHelper gifHelper = new AlxGifHelper();
                    text_joke_context_four.setText(list.get(num).getContent());

                    AlxGifHelper.displayImage(list.get(num).getUrl(),
                            (GifImageView) gif_group.findViewById(R.id.gif_photo_view),
                            (ProgressWheel) gif_group.findViewById(R.id.progress_wheel),
                            (TextView) gif_group.findViewById(R.id.tv_progress),
                            screenWidth - 100//gif显示的宽度为屏幕的宽度减去边距
                    );

                }else {
                    getData();
                }
            } else {
                System.out.println("1234");
                getData();
            }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_next:
                num++;
                setImage();
                break;
            case R.id.bt_on:
                if (num!=0){
                    num--;
                }
                setImage();
                break;
        }
    }

    public int getScreenWidth(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }
}
