package com.welink.myapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.welink.myapp.R;
import com.welink.myapp.entry.Person;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class GifTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_test);
//        Bmob.initialize(this, "1374bcf49d890a8d4cb1a237d00e1139");

        Person p2 = new Person();
        p2.setName("lucky");
        p2.setAddress("北京海淀");
        p2.save(new SaveListener<String>() {
            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    Toast.makeText(GifTestActivity.this,"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(GifTestActivity.this,"创建数据失败：" + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}
