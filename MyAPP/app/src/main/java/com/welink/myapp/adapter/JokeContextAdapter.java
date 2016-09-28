package com.welink.myapp.adapter;


import android.content.Context;
import android.view.View;

import com.welink.myapp.R;
import com.welink.myapp.entry.JokeEntry;

import java.util.List;

/**
 * Created by dell on 2016/8/24.
 */
public class JokeContextAdapter extends CommonAdapter {
    private List<JokeEntry> list;
    private Context context;
    private int type;

    public JokeContextAdapter(Context context, List mDatas, int itemLayoutId,int type) {
        super(context, mDatas, itemLayoutId);
        this.list=mDatas;
        this.context=context;
        this.type=type;
    }

    @Override
    public void convert(ViewHolder helper, Object item) {
        helper.setText(R.id.text_joke_context,((JokeEntry)item).getContent());
        helper.setText(R.id.text_joke_time,"更新时间："+((JokeEntry)item).getUpadtetime());
        if (type==2){
            helper.getView(R.id.text_joke_time).setVisibility(View.GONE);
        }
    }


}











//package com.welink.myapp.adapter;
//
//
//        import android.content.Context;
//        import android.view.LayoutInflater;
//        import android.view.View;
//        import android.view.ViewGroup;
//        import android.widget.BaseAdapter;
//        import android.widget.TextView;
//
//        import com.welink.myapp.R;
//        import com.welink.myapp.entry.JokeEntry;
//
//        import java.util.List;
//
///**
// * Created by dell on 2016/8/24.
// */
//public class JokeContextAdapter extends BaseAdapter {
//    private List<JokeEntry> list;
//    private Context context;
//    private ViewHolder viewHolder;
//
//    public JokeContextAdapter(List<JokeEntry> list, Context context) {
//        this.list = list;
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return list.get(position);
//    }
//
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//            convertView = LayoutInflater.from(context).inflate(R.layout.list_joke_context_item, null);
//            viewHolder.text_joke_context = (TextView) convertView.findViewById(R.id.text_joke_context);
//            viewHolder.text_joke_time = (TextView) convertView.findViewById(R.id.text_joke_time);
//            convertView.setTag(viewHolder);
//        }else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        viewHolder.text_joke_context.setText(list.get(position).getContent());
//        viewHolder.text_joke_time.setText("更新时间"+list.get(position).getUpadtetime());
//
//        return convertView;
//    }
//
//    public static class ViewHolder{
//        private TextView text_joke_context;
//        private TextView text_joke_time;
//    }
//}
