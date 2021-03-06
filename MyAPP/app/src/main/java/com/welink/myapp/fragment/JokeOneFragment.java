package com.welink.myapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.welink.myapp.API;
import com.welink.myapp.R;
import com.welink.myapp.adapter.JokeContextAdapter;
import com.welink.myapp.entry.JokeEntry;
import com.welink.myapp.util.Utils;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JokeOneFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2 {
    @ViewInject(R.id.list_joke_one)
    private PullToRefreshListView mListView;
    @ViewInject(R.id.image_joke_one_up)
    private ImageView image_joke_one_up;

    private JokeContextAdapter adapter;
    private int page;
    private List<JokeEntry> list;
    private List<JokeEntry> lists;

    public static JokeOneFragment newInstance() {
        return new JokeOneFragment();
    }

    public JokeOneFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_joke_one, container, false);
        mListView = (PullToRefreshListView) v.findViewById(R.id.list_joke_one);
        image_joke_one_up=(ImageView)v.findViewById(R.id.image_joke_one_up);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        Utils.overidePullTitle(getActivity(), null, mListView);
        mListView.setOnRefreshListener(this);
        image_joke_one_up.bringToFront();
        image_joke_one_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回顶部
                mListView.getRefreshableView().setSelection(0);
            }
        });
        page = 1;
        getData();

        return v;
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page = 1;
        getData();
       // mListView.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page += 1;
        getData();
        //mListView.onRefreshComplete();
    }

    public void getData() {
        RequestParams params = new RequestParams(API.new_joke);    // 网址(请替换成实际的网址)
        params.addBodyParameter("key", API.joke_key);
        params.addBodyParameter("pagesize", "10");
        params.addBodyParameter("page", String.valueOf(page));// 参数(请替换成实际的参数与值)

        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
                mListView.onRefreshComplete();
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
                mListView.onRefreshComplete();

            }

            // 不管成功或者失败最后都会回调该接口
            @Override
            public void onFinished() {
                mListView.onRefreshComplete();
            }

            @Override
            public void onSuccess(String arg0) {
                System.out.println(arg0);
                String bean = Utils.getDataBean(arg0,1);
                lists = Utils.fromJsonList(bean, JokeEntry.class);
                if (page == 1) {
                    list = lists;
                    adapter = new JokeContextAdapter(getActivity(), list, R.layout.list_joke_context_item,1);
                    mListView.setAdapter(adapter);
                } else {
                    list.addAll(lists);
                    adapter.notifyDataSetChanged();
                }
                mListView.onRefreshComplete();

            }
        });

    }

}
