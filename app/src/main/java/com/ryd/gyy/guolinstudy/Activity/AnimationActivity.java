package com.ryd.gyy.guolinstudy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.View.AnimatedSvgView;

public class AnimationActivity extends BaseActivity {

    AnimatedSvgView svgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 实现原理：将SVG xml中的Path转化为android中的path数据，再使用自定义View将其画出来，再加上动画
     * 参考：https://www.jianshu.com/p/845ac47cb1f1
     */
    private void initSvgAnimated() {
        //不可以使用butterknife绑定id会报空指针异常
        svgView = (AnimatedSvgView) findViewById(R.id.animated_svg_view);
        svgView.postDelayed(new Runnable() {
            @Override
            public void run() {
                svgView.start();
            }
        }, 500);
    }

    @Override
    void initDataBeforeView() {
        initSvgAnimated();
        initFrameAnimated();
    }

    /**
     * 帧动画
     */
    private void initFrameAnimated() {
        ImageView animationImg1 = (ImageView) findViewById(R.id.animation1);
        animationImg1.setImageResource(R.drawable.frame_anim1);
        AnimationDrawable animationDrawable1 = (AnimationDrawable) animationImg1.getDrawable();
        animationDrawable1.start();
    }

    @Override
    void initDataAfterView() {

    }

    @Override
    void initView() {

    }

    @Override
    int getLayoutId() {
        return R.layout.activity_animation;
    }
}