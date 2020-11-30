package com.ryd.gyy.guolinstudy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.RecyclerClass.DemoPageAdapter;

/**
 * 类似于LinearLayout，ViewPager类直接继承了ViewGroup类，是一个容器，需要在里面添加我们想要显示的内容。
 */
public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new DemoPageAdapter(this));

    }
}