package com.ryd.gyy.guolinstudy.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import com.ryd.gyy.guolinstudy.Thread.ThreadStudy;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.security.auth.login.LoginException;

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

    public static int TEST_ID = 1;
    private static final String TAG = "MyActivity";

    @BindView(R.id.btn_start)
    Button btn_start;
    @BindView(R.id.btn_stop)
    Button btn_stop;

    RecyclerView recyclerView;

    DemoAdapter adapter;

    private List<DemoBean> demoList = new ArrayList<>();
    private DemoBean[] demoFruits = {
            new DemoBean("数据库", R.drawable.ic_svg11, DataSaveActivity.class),
            new DemoBean("Thread", R.drawable.ic_svg9, ThreadStudy.class),
            new DemoBean("ScrollWindow", R.drawable.ic_svg9, ScrollWindowActivity.class),
            new DemoBean("View", R.drawable.ic_svg12, ViewActivity.class),
            new DemoBean("Orange", R.drawable.ic_svg10, NotificationActivity.class),
            new DemoBean("乘风破浪的小船", R.drawable.ic_svg1, MainActivity.class),
            new DemoBean("动画", R.drawable.ic_svg2, AnimationActivity.class),
            new DemoBean("补间动画(xml+java)", R.drawable.ic_svg3, TweenedAnimationActivity.class),
            new DemoBean("ValueAnimation", R.drawable.ic_svg4, ValueAnimationActivity.class),
            new DemoBean("ViewPager", R.drawable.ic_svg5, ViewPagerActivity.class),
            new DemoBean("FragmentPager", R.drawable.ic_svg6, FragmentPagerActivity.class),
            new DemoBean("MotionLayout", R.drawable.ic_svg7, MotionLayoutActivity.class),
            new DemoBean("Tablayout", R.drawable.ic_svg8, TablayoutActivity.class),
            new DemoBean("Orange", R.drawable.ic_svg11, PerContactsActivity.class),
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

        testProcess();
    }

    /**
     * 测试多进程的代码
     */
    private void testProcess() {
        TEST_ID = 2;
        Log.e(TAG, "MyActivity onCreate TEST_ID: " + TEST_ID);
        //app1中获取app2的上下文：
        try {
            Context mContext = this.createPackageContext("ryd.gyy.anative", Context.CONTEXT_IGNORE_SECURITY);
            //获取Context有效com.ryd.gyy.guolinstudy E/MyActivity: onCreate mContext: ryd.gyy.anative

            //获取sp数据有效：onCreate mContext: ryd.gyy.anative------app_name:account
            SharedPreferences sp = mContext.getSharedPreferences("appInfo", MODE_PRIVATE);
            String str2 = sp.getString("appname", "service");

            //先获取资源id
            int appNameId = mContext.getResources().getIdentifier("app_name", "string", "ryd.gyy.anative");
            //再用资源id去获取实际的值,亲测有效
            String appName = mContext.getResources().getString(appNameId);
            //E/MyActivity: onCreate mContext: ryd.gyy.anative------app_name:account--appName:native
            Log.e(TAG, "onCreate mContext: " + mContext.getPackageName() + "------app_name:" + str2
                    + "--appName:" + appName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
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

//        添加下拉刷新
        RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });

//设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
//设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
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
