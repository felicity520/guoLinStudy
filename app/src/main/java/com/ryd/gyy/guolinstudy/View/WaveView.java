package com.ryd.gyy.guolinstudy.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.ryd.gyy.guolinstudy.R;

import java.util.LinkedList;
import java.util.Random;

public class WaveView extends View {

    private static final String TAG = "WaveView";

    private Context mContext;

    private LinkedList<Float> heightList = new LinkedList<Float>();

    //  一个界面里，有几个波浪
    private int mWaveNumber = 3;

    private Paint mPaint;
    private Path mPath;
    //一个波浪长，相当于两个二阶贝塞尔曲线的长度
    private int mItemWaveLength = 0;
    //波浪在Y轴方向的位置
    int mStartY;
    //波浪幅度:range没有用到
    private int range = 100;
    private int dx;

    //    小船
    private Bitmap boat;

    Random random;

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        boat = BitmapFactory.decodeResource(getResources(), R.drawable.ic_boat);
        random = new Random();
        for (int i = 0; i < 20; i++) {
            heightList.add(Float.valueOf(String.valueOf(random.nextInt(500))));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        int halfWaveLen = mItemWaveLength / 2; //半个波长，即一个贝塞尔曲线长度
//        这里需要多绘制一个波浪，也就是起点多画一个波浪
        mPath.moveTo(-mItemWaveLength + dx, mStartY); //波浪的开始位置

//        测试代码----保留
        //每一次for循环添加一个波浪的长度到path中，根据view的宽度来计算一共可以添加多少个波浪长度
//        for (int i = 0; i <= getWidth() + mItemWaveLength; i += mItemWaveLength) {
//            //波浪先上后下
//            mPath.rQuadTo(halfWaveLen / 2, -range, halfWaveLen, 0);
//            mPath.rQuadTo(halfWaveLen / 2, range, halfWaveLen, 0);
//        }

        int waveIndex = 0;
        int i = -mItemWaveLength;
        while (i <= getWidth()) {
            mPath.rQuadTo(halfWaveLen / 2, -heightList.get(waveIndex), halfWaveLen, 0);
            mPath.rQuadTo(halfWaveLen / 2, heightList.get(waveIndex), halfWaveLen, 0);
            i += mItemWaveLength;
            waveIndex++;
        }

        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close(); //封闭path路径

        canvas.drawPath(mPath, mPaint);

        int x = getWidth() / 2;
        Region region = new Region();
        Region region2 = new Region();
        Region clip = new Region(new Double(x - 0.1).intValue(), 0, x, getHeight());
        Region clip2 = new Region(new Double(x - 10).intValue(), 0, x - 9, getHeight());
        region.setPath(mPath, clip);
        region2.setPath(mPath, clip2);
        Rect rect = region.getBounds();
        Rect rect2 = region2.getBounds();
        double fl = Math.atan2(Double.valueOf(-rect.top) + Double.valueOf(rect2.top), 9.5f) * 180 / Math.PI;
        canvas.save();
        canvas.rotate(
                (float) fl, Float.valueOf(String.valueOf(rect.right)),
                Float.valueOf(String.valueOf(rect.top))
        );
        canvas.drawBitmap(
                boat,
                //将小船的底部，贴着浪
                rect.right - boat.getWidth() / 2,
                rect.top - boat.getHeight() / 4 * 3,
                mPaint
        );
        canvas.restore();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mItemWaveLength = getWidth() / mWaveNumber;
        mStartY = getWidth() / 2;
        startAnim();
    }

    public void startAnim() {
        //根据一个动画不断得到0~mItemWaveLength的值dx，通过dx的增加不断去改变波浪开始的位置，dx的变化范围刚好是一个波浪的长度，
        //所以可以形成一个完整的波浪动画，假如dx最大小于mItemWaveLength的话， 就会不会画出一个完整的波浪形状
        ValueAnimator animator = ValueAnimator.ofInt(0, mItemWaveLength);
        animator.setDuration(1500);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);


            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                Log.e(TAG, "onAnimationRepeat: -------------------");
                heightList.addFirst(Float.valueOf(String.valueOf(random.nextInt(500))));
                heightList.remove(10);
//                getHandler().post(new Runnable() {
//                    @Override
//                    public void run() {
//                        heightList.addFirst(Float.valueOf(String.valueOf(random.nextInt(500))));
//                        heightList.remove(10);
//                    }
//                });

            }
        });
        animator.start();
    }

}
