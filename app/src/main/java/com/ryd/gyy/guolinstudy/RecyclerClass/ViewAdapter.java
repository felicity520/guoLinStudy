package com.ryd.gyy.guolinstudy.RecyclerClass;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * ViewPager必须要实现的适配器PagerAdapter
 */
public class ViewAdapter extends PagerAdapter {
    private List<View> datas;

    public ViewAdapter(List<View> list) {
        datas = list;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = datas.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(datas.get(position));
    }
}

