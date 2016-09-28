package com.welink.myapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.design.widget.TabLayout;
import com.welink.myapp.R;
import com.welink.myapp.fragment.JokeFourFragment;
import com.welink.myapp.fragment.JokeOneFragment;
import com.welink.myapp.fragment.JokeThreeFragment;
import com.welink.myapp.fragment.JokeTwoFragment;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class JokeActivity extends AppCompatActivity {
    @ViewInject(R.id.toolbar_joke)
    private Toolbar toolbar_joke;
    private TabLayout tab_layout_joke;
    @ViewInject(R.id.viewpager_joke)
    private ViewPager viewpager_joke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        x.view().inject(this);
        tab_layout_joke=(TabLayout)findViewById(R.id.tab_layout_joke);

        toolbar_joke.setNavigationIcon(R.mipmap.common_icon_fanhui);
        toolbar_joke.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        MyPagerAdapter adapter=new MyPagerAdapter(getSupportFragmentManager());
        viewpager_joke.setAdapter(adapter);
        tab_layout_joke.setTabsFromPagerAdapter(adapter);
        tab_layout_joke.setupWithViewPager(viewpager_joke);



    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {


        private MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:return JokeOneFragment.newInstance();
                case 1:return JokeTwoFragment.newInstance();
                case 2:return JokeThreeFragment.newInstance();
                default:return JokeFourFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:return "最新笑话";
                case 1:return "最新趣图";
                case 2:return "笑话";
                case 3:return "趣图";
                default:return "笑话";
            }
        }
    }
}
