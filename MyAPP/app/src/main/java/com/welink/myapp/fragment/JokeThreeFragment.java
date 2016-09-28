package com.welink.myapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import org.xutils.x;

import java.util.List;

public class JokeThreeFragment extends Fragment implements PullToRefreshListView.OnRefreshListener2{

    private PullToRefreshListView mListView;
    private List<JokeEntry> list;
    private JokeContextAdapter adapter;

    public static JokeThreeFragment newInstance() {
        return new JokeThreeFragment();
    }

    public JokeThreeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_joke_three, container, false);
        mListView=(PullToRefreshListView)v.findViewById(R.id.list_joke_three);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        Utils.overidePullTitle(getActivity(), null, mListView);
        mListView.setOnRefreshListener(this);
        getData();
        return v;
    }

    private void getData() {
        RequestParams params = new RequestParams(API.romdo_jokeImage);    // 网址(请替换成实际的网址)
        params.addBodyParameter("key", API.joke_key);
        params.addBodyParameter("type", "");

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
                String bean = Utils.getDataBean(arg0,2);
                list = Utils.fromJsonList(bean, JokeEntry.class);
                adapter = new JokeContextAdapter(getActivity(), list, R.layout.list_joke_context_item,2);
                mListView.setAdapter(adapter);
                mListView.onRefreshComplete();

            }
        });

    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        getData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getData();
    }
}
