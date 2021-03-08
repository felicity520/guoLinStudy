package com.ryd.gyy.guolinstudy.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.ryd.gyy.guolinstudy.R;

public class TestView extends View {

    private static final String TAG = "TestView";

    Paint mPaint;
    String mText;
    int mTextSize;

    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//注意这里将自定义属性的名字（TestView）改成和自定义View的名字（TestView）一样即可
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TestView);
        mText = typedArray.getString(R.styleable.TestView_android_text);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.TestView_android_textSize, 60);
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
//        注意：这里要用画笔设置文本的大小，要不然默认字体会非常小
        mPaint.setTextSize(mTextSize);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setTextAlign(Paint.Align.CENTER);

    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, "onMeasure: ---------");
        if (mText.isEmpty()) {
            return;
        }

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

//        默认宽度是最宽的
        int resultWidth = widthSize;
        int resultHeight = heightSize;

//       处理子View是wrap_content却显示match_parent的时候，wrap_content对应的模式是-> MeasureSpec.AT_MOST
        if (widthMode == MeasureSpec.AT_MOST) {
            int contentWidth = (int) mPaint.measureText(mText);
            resultWidth = Math.min(contentWidth, widthSize);
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            int contentHeight = (int) mPaint.measureText(mText);
            resultHeight = Math.min(contentHeight, heightSize);
        }

        setMeasuredDimension(resultWidth, resultHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mText.isEmpty()) {
            return;
        }

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        int x = (getWidth()) / 2;
        int y = (getHeight()) / 2;
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;

        canvas.drawColor(Color.GREEN);
//      drawText竟然不是以中点来画的，而是以baseline来画的
//        https://blog.csdn.net/zly921112/article/details/50401976
        canvas.drawText(mText, x, (y - top / 2 - bottom / 2), mPaint);
//        canvas.drawText(mText, x, y, mPaint);
    }
}
