package com.ryd.gyy.guolinstudy.RecyclerClass;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 这里有个易错点的bug
 * https://mp.weixin.qq.com/s?__biz=MzAxMTI4MTkwNQ==&mid=2650830309&idx=1&sn=6819e6e29cf2b965c842a9a4145a29cb&chksm=80b7a17bb7c0286d634e3ca2c69858f8b9e1bd5749636a31a0a949732a52bdbe9111266623d6&scene=21#wechat_redirect
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }
}



