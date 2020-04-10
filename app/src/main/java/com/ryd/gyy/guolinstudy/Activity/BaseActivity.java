package com.ryd.gyy.guolinstudy.Activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ryd.gyy.guolinstudy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "yyy";

    //通用的Toolbar标题
    @BindView(R.id.commom_title)
    TextView commonTitleTv;
    //通用的ToolBar
    @BindView(R.id.toolbar)
    Toolbar commonTitleTb;
    //内容区域
    @BindView(R.id.content)
    FrameLayout content;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = getLayoutInflater().inflate(R.layout.activity_base, null);
        LinearLayout rootLayout = (LinearLayout) rootView.findViewById(R.id.root_layout);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(getLayoutId(), null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootLayout.addView(contentView, params);
        setContentView(rootView);
        ButterKnife.bind(this);
        initLocalView();
        initView();
        initData();
    }


    /**
     * 初始化本地的布局
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initLocalView() {
        setSupportActionBar(commonTitleTb);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    //子类调用 重新设置Toolbar
//    public void setToolBar(int layout) {
//        hidetoolBar();
//        commonTitleTb = content.findViewById(layout);
//        setSupportActionBar(commonTitleTb);
//        //设置actionbar标题是否显示 对应ActionBar.DISPLAY_SHOW_TITLE
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//    }

    //隐藏Toolbar 通过setToolbar重新制定Toolbar
//    public void hidetoolBar() {
//        commonTitleTb.setVisibility(View.GONE);
//    }
//
//    //menu的点击事件
//    public void setToolBarMenuOnClick(Toolbar.OnMenuItemClickListener onClick) {
//        commonTitleTb.setOnMenuItemClickListener(onClick);
//    }

    //设置左上角back按钮
//    public void setBackArrow() {
//        final Drawable upArrow = getResources().getDrawable(R.loading.back);
//        //给Toolbar设置左侧的图标
//        getSupportActionBar().setHomeAsUpIndicator(upArrow);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        //设置返回按钮的点击事件
//        commonTitleTb.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    //在res中创建menu文件夹，新建xml来定义控件
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "You clicked Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
    */

    //设置标题
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            commonTitleTv.setText(title);
        }
    }

    /**
     * 初始化数据
     * 注意：
     * 1.抽象方法不能有方法主体。也就是不能是 abstract void initData(){}。不能有{}
     * 2.一旦类中包含了abstract方法，那类该类必须声明为abstract类。所以BaseActivity必须也声明为abstract
     */
    abstract void initData();


    /**
     * 初始化布局
     */
    abstract void initView();

    /**
     * 获取具体页面的layout id
     */
    abstract @LayoutRes
    int getLayoutId();
}
