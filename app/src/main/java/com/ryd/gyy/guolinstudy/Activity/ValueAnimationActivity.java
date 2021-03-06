package com.ryd.gyy.guolinstudy.Activity;

import androidx.annotation.Nullable;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.View.PointAnimView;

/**
 * 属性动画
 * https://www.jianshu.com/p/420629118c10
 */
public class ValueAnimationActivity extends BaseActivity implements View.OnClickListener {
    PointAnimView view;

    private RadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    void initDataBeforeView() {
        initView();
    }

    @Override
    void initDataAfterView() {

    }

    @Override
    public void initView() {
        view = (PointAnimView) findViewById(R.id.view);
        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb3 = (RadioButton) findViewById(R.id.rb3);
        rb4 = (RadioButton) findViewById(R.id.rb4);
        rb5 = (RadioButton) findViewById(R.id.rb5);
        rb6 = (RadioButton) findViewById(R.id.rb6);
        rb7 = (RadioButton) findViewById(R.id.rb7);
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_value_animation;
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (view != null) {
            //要释放资源，否则会内存溢出
            view.stopAnimation();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                if (view != null) {
                    if (rb1.isChecked()) {
                        type = 1;
                    } else if (rb2.isChecked()) {
                        type = 2;
                    } else if (rb3.isChecked()) {
                        type = 3;
                    } else if (rb4.isChecked()) {
                        type = 4;
                    } else if (rb5.isChecked()) {
                        type = 5;
                    } else if (rb6.isChecked()) {
                        type = 6;
                    } else if (rb7.isChecked()) {
                        type = 7;
                    } else {
                        type = 0;
                    }
                    view.stopAnimation();
                    view.setInterpolatorType(type);
                    view.StartAnimation();
                }
                break;
            case R.id.stop:
                if (view != null) {
                    view.stopAnimation();
                }
                break;
            default:
                break;
        }
    }


}
