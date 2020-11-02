package com.ryd.gyy.guolinstudy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.View.AnimatedSvgView;

/**
 * Android属性动画完全解析(上)，初识属性动画的基本用法
 * https://blog.csdn.net/guolin_blog/article/details/43536355
 */
public class AnimationActivity extends BaseActivity {

    AnimatedSvgView svgView;
    TextView text_info;

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
        initValueAnimation();
    }

    /**
     * 用属性动画实现了补间动画的四种类型淡入淡出、旋转、水平移动、垂直缩放
     * 说明：textview没有alpha这个属性，连它所有的父类也是没有这个属性的
     * 其实ObjectAnimator内部的工作机制并不是直接对我们传入的属性名进行操作的，而是会去寻找这个属性名对应的get和set方法
     * public void setAlpha(float value);
     * public float getAlpha();
     */
    private void initValueAnimation() {
//        text_info.setAlpha(1);
        ObjectAnimator animator = ObjectAnimator.ofFloat(text_info, "alpha", 1f, 0f, 1f);
        animator.setDuration(5000);

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(text_info, "rotation", 0f, 360f);
        animator.setDuration(5000);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(text_info, "scaleY", 1f, 3f, 1f);
        animator.setDuration(5000);
        animator.start();

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animator).with(animator1).with(animator2);
        animSet.setDuration(5000);
        animSet.start();

//        单独实现会比较明显
//        float curTranslationX = text_info.getTranslationX();
//        ObjectAnimator animator = ObjectAnimator.ofFloat(text_info, "translationX", curTranslationX, -500f, curTranslationX);
//        animator.setDuration(5000);
//        animator.start();
    }

    @Override
    void initView() {
        text_info = (TextView) findViewById(R.id.text_info);
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_animation;
    }
}