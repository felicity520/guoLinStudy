package com.ryd.gyy.guolinstudy.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.RecyclerClass.DemoAdapter;
import com.ryd.gyy.guolinstudy.RecyclerClass.DemoBean;
import com.ryd.gyy.guolinstudy.RecyclerClass.Fruit;
import com.ryd.gyy.guolinstudy.RecyclerClass.FruitAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 子类的执行顺序应该严格按照父类的调用流程来执行
 * 此处：getLayoutId()——》initDataBeforeView()——》initView()——》initDataAfterView()——》onCreate
 * 先加载布局，再初始化布局，最后初始化数据。
 * 设置为主页面的布局：正式的一个demo
 * 插画来源：
 * https://www.iconfont.cn/illustrations/detail?spm=a313x.7781069.1998910419.d9df05512&cid=25077
 */
public class MyActivity extends BaseActivity {

    private static final String TAG = "MyActivity";

    @BindView(R.id.btn_start)
    Button btn_start;
    @BindView(R.id.btn_stop)
    Button btn_stop;

    RecyclerView recyclerView;

    DemoAdapter adapter;

    private List<DemoBean> demoList = new ArrayList<>();
    private DemoBean[] demoFruits = {
            new DemoBean("乘风破浪的小船", R.drawable.ic_svg1, MainActivity.class),
            new DemoBean("动画", R.drawable.ic_svg2, AnimationActivity.class),
            new DemoBean("补间动画(xml+java)", R.drawable.ic_svg3, TweenedAnimationActivity.class),
            new DemoBean("ValueAnimation", R.drawable.ic_svg4, ValueAnimationActivity.class),
            new DemoBean("ViewPager", R.drawable.ic_svg5, ViewPagerActivity.class),
            new DemoBean("FragmentPager", R.drawable.ic_svg6, FragmentPagerActivity.class),
            new DemoBean("MotionLayout", R.drawable.ic_svg7, MotionLayoutActivity.class),
            new DemoBean("Tablayout", R.drawable.ic_svg8, TablayoutActivity.class),
            new DemoBean("Orange", R.drawable.ic_svg9, MaterialActivity.class),
            new DemoBean("Orange", R.drawable.ic_svg10, MaterialActivity.class),
            new DemoBean("Orange", R.drawable.ic_svg11, MaterialActivity.class),
            new DemoBean("Orange", R.drawable.ic_svg12, MaterialActivity.class),
            new DemoBean("Orange", R.drawable.ic_svg13, MaterialActivity.class),
            new DemoBean("Orange", R.drawable.ic_svg14, MaterialActivity.class),
            new DemoBean("Orange", R.drawable.ic_svg15, MaterialActivity.class),
            new DemoBean("Orange", R.drawable.ic_svg16, MaterialActivity.class),
            new DemoBean("Orange", R.drawable.ic_svg17, MaterialActivity.class),
            new DemoBean("Orange", R.drawable.ic_svg18, MaterialActivity.class),
            new DemoBean("Orange", R.drawable.ic_svg19, MaterialActivity.class),
            new DemoBean("Orange", R.drawable.ic_svg20, MaterialActivity.class),
            new DemoBean("Orange", R.drawable.ic_svg21, MaterialActivity.class),
            new DemoBean("Orange", R.drawable.ic_svg22, MaterialActivity.class),
            new DemoBean("Mango", R.drawable.ic_svg23, MaterialActivity.class)};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("主界面");
    }

    /**
     * 继承父类的abstract,这里只要添加上@Override即可
     */
    @Override
    public void initDataBeforeView() {
        demoList.clear();
        demoList.addAll(Arrays.asList(demoFruits));
    }

    @Override
    public void initDataAfterView() {

    }

    @Override
    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DemoAdapter(demoList);
        recyclerView.setAdapter(adapter);
    }

    /**
     * @param view 绑定多个点击事件
     *             ButterKnife绑定button的例子
     */
    @OnClick({R.id.btn_start, R.id.btn_stop})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                btn_start.setText("你哈");
                Toast.makeText(this, "你按到我了啦11111111!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_stop:
                Toast.makeText(this, "你按到我了啦1!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my;
    }


}
