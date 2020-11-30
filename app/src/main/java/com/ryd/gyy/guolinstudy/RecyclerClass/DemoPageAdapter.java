package com.ryd.gyy.guolinstudy.RecyclerClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.ryd.gyy.guolinstudy.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DemoPageAdapter extends PagerAdapter {
    private List<View> mViewList = new ArrayList<>();

    public DemoPageAdapter(Context context) {
        LayoutInflater lf = LayoutInflater.from(context);
        View view1 = lf.inflate(R.layout.view_pager_text, null);
        View view2 = lf.inflate(R.layout.activity_test, null);
        View view3 = lf.inflate(R.layout.activity_thread, null);

        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
    }

    /**
     * 返回界面数量
     */
    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 添加界面，一般会添加当前页和左右两边的页面
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    /**
     * 去除页面
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

}

