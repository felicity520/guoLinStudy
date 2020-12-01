package com.ryd.gyy.guolinstudy.RecyclerClass;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ryd.gyy.guolinstudy.Fragment.Fragment1;
import com.ryd.gyy.guolinstudy.Fragment.Fragment2;
import com.ryd.gyy.guolinstudy.Fragment.Fragment3;
import com.ryd.gyy.guolinstudy.Fragment.Fragment4;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Carson_Ho on 16/7/22.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"首页", "发现", "进货单", "我的", "测试代码", "GYY", "LLYY", "ATB1"};

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new Fragment2();
        } else if (position == 2) {
            return new Fragment3();
        } else if (position == 3) {
            return new Fragment4();
        }
        return new Fragment1();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    /**
     * ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
     * 一定要重写，不然TabLayout中的标签是看不到的，也就是看不到text
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}

