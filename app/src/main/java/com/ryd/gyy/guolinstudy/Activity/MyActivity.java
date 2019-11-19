package com.ryd.gyy.guolinstudy.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ryd.gyy.guolinstudy.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 子类的执行顺序应该严格按照父类的调用流程来执行
 * 此处：getLayoutId()——》initView()——》initData()——》onCreate
 * 先加载布局，再初始化布局，最后初始化数据。
 */
public class MyActivity extends BaseActivity {

    private static final String TAG = "yyy";


    @BindView(R.id.btn_start)
    Button btn_start;
    @BindView(R.id.btn_stop)
    Button btn_stop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: 0000000000000");
        setTitle("主界面");
    }

    /**
     * 继承父类的abstract,这里只要添加上@Override即可
     */
    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }


    /**
     * @param view 绑定多个点击事件
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
