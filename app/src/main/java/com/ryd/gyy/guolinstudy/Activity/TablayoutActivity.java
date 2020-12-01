package com.ryd.gyy.guolinstudy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.tabs.TabLayout;
import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.RecyclerClass.MyFragmentPagerAdapter;

/**
 * 参考：
 * https://blog.csdn.net/csdnxia/article/details/105947804
 * https://blog.csdn.net/qq_41873558/article/details/85160867
 */
public class TablayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tablayout);

        //初始化视图
        initViews();
    }

    private void initViews() {
        //使用适配器将ViewPager与Fragment绑定在一起
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPager);
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView == null) {
                    tab.setCustomView(R.layout.activity_tablayout_icon);
                }
                TextView textView = tab.getCustomView().findViewById(android.R.id.text1);
                textView.setTextAppearance(TablayoutActivity.this, R.style.TabLayoutTextSelected);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView == null) {
                    tab.setCustomView(R.layout.activity_tablayout_icon);
                }
//              这里需要注意的是textView的id必须是android.R.id.text1
                TextView textView = tab.getCustomView().findViewById(android.R.id.text1);
                textView.setTextAppearance(TablayoutActivity.this, R.style.TabLayoutTextUnSelected);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //获取Tab的位置，setCustomView可以自定义显示的view
//        TabLayout.Tab one = mTabLayout.getTabAt(0).setCustomView();
//        TabLayout.Tab two = mTabLayout.getTabAt(1);
//        TabLayout.Tab three = mTabLayout.getTabAt(2);
//        TabLayout.Tab four = mTabLayout.getTabAt(3);
//        mTabLayout.getTabCount();

        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));
        linearLayout.setDividerPadding(10);
    }




}