package com.ryd.gyy.guolinstudy.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class TestView1 extends View {

    private static final String TAG = "TestView1";

    public TestView1(Context context) {
        super(context);
    }

    public TestView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

//        onMeasure widthMode------:
//onMeasure heightMode------:
//onMeasure widthSize: 1080 heightSize:2055
//        正在显示蓝色，且测量模式是MeasureSpec.AT_MOST,宽高都是父类的宽高

        if (widthMode == MeasureSpec.AT_MOST) {
            Log.e(TAG, "onMeasure widthMode------: ");
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            Log.e(TAG, "onMeasure heightMode------: ");
        }

        Log.e(TAG, "onMeasure widthSize: " + widthSize + " heightSize:" + heightSize);

    }


}
