package com.ryd.gyy.guolinstudy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.ryd.gyy.guolinstudy.Fragment.MyFragment;
import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.RecyclerClass.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentPagerActivity extends AppCompatActivity {

    private static final String TAG = "FragmentPagerActivity";
    private List<Fragment> frags;
    private List<String> titles;
    private ViewPager pager;
    FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_pager);
        initData();
        initPager();
    }

    /**
     * 初始化Fragment
     * 这里有个bug:https://mp.weixin.qq.com/s/MOWdbI5IREjQP1Px-WJY1Q
     */
    private void initData() {
        frags = new ArrayList<>();
        frags.add(MyFragment.newInstance(R.layout.fragment_first));
        frags.add(MyFragment.newInstance(R.layout.fragment_second));
        frags.add(MyFragment.newInstance(R.layout.fragment_third));

        titles = new ArrayList<>();
        titles.add("First");
        titles.add("Second");
        titles.add("Third");
    }

    private void initPager() {
        pager = (ViewPager) findViewById(R.id.frag_pager);
        adapter = new FragmentAdapter(getSupportFragmentManager(), frags);
        pager.setAdapter(adapter);
        //滑动参考鸿洋代码：https://blog.csdn.net/lmj623565791/article/details/38026503
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * 页面滑动过程中调用
             * 在非第一页与最后一页时，滑动到下一页，position为当前页位置；滑动到上一页：position为当前页-1
             * @param position
             * @param positionOffset
             * @param positionOffsetPixels
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.d(TAG, "gyy onPageScrolled position: " + position);
                Log.d(TAG, "gyy onPageScrolled position: " + position);
            }

            /**
             * 页面滑动后调用
             * @param position
             */
            @Override
            public void onPageSelected(int position) {

            }

            /**
             * 页面状态改变时调用
             * @param state
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        Log.d(TAG, "onPageScrollStateChanged---SCROLL_STATE_IDLE: ");
                        break;

                    case ViewPager.SCROLL_STATE_DRAGGING:
                        Log.d(TAG, "onPageScrollStateChanged---SCROLL_STATE_DRAGGING: ");
                        break;

                    case ViewPager.SCROLL_STATE_SETTLING:
                        System.out.println("onPageScrollStateChanged--SCROLL_STATE_SETTLING---");
                        break;

                }
            }
        });
    }
}