package com.ryd.gyy.guolinstudy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.RecyclerClass.DepthPageTransformer;
import com.ryd.gyy.guolinstudy.RecyclerClass.ViewAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 加载View的ViewPager
 * 参考：https://www.jianshu.com/p/73187304ffd7
 */
public class ViewPagerActivity extends AppCompatActivity {

    private static final String TAG = "ViewPagerActivity";

    private List<View> pages;
    private ViewPager viewPager;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        pages = getPages();
        initPager();


    }

    /**
     * 初始化ViewPager
     */
    private void initPager() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ViewAdapter(pages);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
    }

    /**
     * 获取加载的图片资源
     *
     * @return
     */
    private List<View> getPages() {
        List<View> pages = new ArrayList<>();
        Field[] fields = R.drawable.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                if (field.getName().startsWith("page")) {
                    ImageView view = new ImageView(this);
                    view.setImageResource(field.getInt(null));
                    view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    pages.add(view);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return pages;
    }

}