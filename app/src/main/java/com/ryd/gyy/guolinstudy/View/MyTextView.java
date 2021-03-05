package com.ryd.gyy.guolinstudy.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.ryd.gyy.guolinstudy.R;

public class MyTextView extends View {

    private static final String TAG = "MyTextView";

    public MyTextView(Context context) {
        super(context);
    }

    //在View的构造方法中通过TypedArray获取
    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
        String text = typedArray.getString(R.styleable.MyTextView_text);
        int textAttr = typedArray.getInt(R.styleable.MyTextView_textAttr, 111);
        Log.e(TAG, "MyTextView text: " + text + " textAttr:" + textAttr);
        typedArray.recycle();
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measureMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureSize = MeasureSpec.getSize(widthMeasureSpec);

        // 通过Mode 和 Size 生成新的SpecMode
        MeasureSpec.makeMeasureSpec(measureSize, measureMode);


    }
}
