package com.ryd.gyy.guolinstudy.View;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

/**
 * Android属性动画完全解析(中)，ValueAnimator和ObjectAnimator的高级用法
 * https://blog.csdn.net/guolin_blog/article/details/43816093
 */
public class MyAnimView extends View {

    public static final float RADIUS = 50f;

    private Point currentPoint;

    private Paint mPaint;

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }


    public MyAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    private void startAnimation() {
//        Point startPoint = new Point(RADIUS, RADIUS);
//        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
        Point startPoint = new Point(getWidth() / 2, RADIUS);
        Point endPoint = new Point(getWidth() / 2, getHeight() - RADIUS);
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
//        anim.setInterpolator(new AccelerateInterpolator(2f));
        //弹跳的，模拟真实运动的Interpolator
        anim.setInterpolator(new BounceInterpolator());
        anim.setInterpolator(new DecelerateAccelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });

        ObjectAnimator anim2 = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(),
                "#0000FF", "#FF0000");
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim).with(anim2);
        animSet.setDuration(5000);
        animSet.start();

    }

}
