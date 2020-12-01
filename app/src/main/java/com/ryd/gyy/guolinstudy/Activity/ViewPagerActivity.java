package com.ryd.gyy.guolinstudy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.RecyclerClass.DemoPageAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 类似于LinearLayout，ViewPager类直接继承了ViewGroup类，是一个容器，需要在里面添加我们想要显示的内容。
 */
public class ViewAdapter extends AppCompatActivity {

    private List<View> pages;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        pages = getPages();
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new DemoPageAdapter(this));

        PagerAdapter adapter = new ViewAdapter(pages);
        pager.setAdapter(adapter);

    }

    private void initPager() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        PagerAdapter adapter = new ViewAdapter(pages);
        viewPager.setAdapter(adapter);

        switch (getIntent().getIntExtra(TransformerType.TYPE, TransformerType.NORMAL)) {
            case TransformerType.SCALE:
                pager.setPageTransformer(true, new ScalePageTransformer());
                break;
            case TransformerType.ROTATE:
                pager.setPageTransformer(true, new RotatePageTransformer());
                break;
            default:
                break;
        }
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