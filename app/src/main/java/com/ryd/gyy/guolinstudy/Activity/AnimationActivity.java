package com.ryd.gyy.guolinstudy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ryd.gyy.guolinstudy.Model.MessageEvent;
import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.View.AnimatedSvgView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Android属性动画完全解析(上)，初识属性动画的基本用法
 * https://blog.csdn.net/guolin_blog/article/details/43536355
 * 转场动画
 */
public class AnimationActivity extends BaseActivity {

    AnimatedSvgView svgView;
    TextView text_info;
    Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消事件订阅
        EventBus.getDefault().unregister(this);
    }

    /**
     * 粘性事件
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void onMessageEvent(MessageEvent messageEvent) {
        Toast.makeText(this, "粘性事件：" + messageEvent.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
        initStart();
    }

    /**
     * 跳转的动作
     */
    private void initStart() {
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //第一种：通过overridePendingTransition实现转场动画---------------------------------
//                Intent intent = new Intent(AnimationActivity.this, GlideActivity.class);
//                startActivity(intent);
//                overridePendingTransition(0, 0);//无动画
//                overridePendingTransition(R.anim.pageup_enter, R.anim.pageup_exit);


                //第二种：通过setEnterTransition实现转场动画:----------------------------------------
                //有三种动画可选，可使用xml设置也可通过代码动态设定
                //1、Explode：从屏幕的中间进入或退出。
                //2、Slide：从屏幕的一边向另一边进入或退出。
                //3、Fade：通过改变透明度来出现或消失。

                //Window.setEnterTransition() 设置进场动画
                //Window.setExitTransition() 设置出场动画
                //Window().setReturnTransition() 设置返回activity时动画
                //Window().setReenterTransition() 设置重新进入时动画

                //例如：activityA进入到activityB,activity执行出场的setExitTransition，activityB执行setEnterTransition

                //通过代码======
//                getWindow().setEnterTransition(new Explode().setDuration(2000));
//                getWindow().setExitTransition(new Explode().setDuration(2000));
//                startActivity(new Intent(AnimationActivity.this, GlideActivity.class), ActivityOptions.makeSceneTransitionAnimation(AnimationActivity.this).toBundle());

                //通过xml======
//                Transition explode = TransitionInflater.from(AnimationActivity.this).inflateTransition(R.transition.explode);
//                getWindow().setExitTransition(explode);
//                startActivity(new Intent(AnimationActivity.this, GlideActivity.class), ActivityOptions.makeSceneTransitionAnimation(AnimationActivity.this).toBundle());

                //第三种：通过共享元素实现转场动画:----------------------------------------
                startActivity(new Intent(AnimationActivity.this, GlideActivity.class), ActivityOptions.makeSceneTransitionAnimation(AnimationActivity.this, btn_start, "shareBtn").toBundle());
            }
        });
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

        //animate()返回一个ViewPropertyAnimator：针对View设计的
        text_info.animate().x(400).y(400).setDuration(5000)
                .setInterpolator(new BounceInterpolator());
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