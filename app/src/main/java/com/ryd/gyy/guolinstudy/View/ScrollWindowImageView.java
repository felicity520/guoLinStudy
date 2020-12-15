package com.ryd.gyy.guolinstudy.View;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class ScrollWindowImageView extends AppCompatImageView {
    private boolean initialized;
    private Matrix matrix;

    public ScrollWindowImageView(Context context) {
        this(context, null);
    }

    public ScrollWindowImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollWindowImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //对图片的放缩策略和显示方式采用matrix方式，即矩阵变换
        setScaleType(ScaleType.MATRIX);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initialized = true;
        initImage();
    }

    private void initImage() {
        final Drawable drawable = getDrawable();
        if (!initialized || drawable == null) {
            return;
        }
        final int viewWidth = getWidth();
        final int viewHeight = getHeight();
        //getIntrinsicWidth()和getIntrinsicHeight()返回的宽高应该是dp为单位的
        final int drawableWidth = drawable.getIntrinsicWidth();
        final int drawableHeight = drawable.getIntrinsicHeight();
        float scaleX = (float) viewWidth / drawableWidth;
        matrix = getImageMatrix();
        matrix.setScale(scaleX, scaleX);
        matrix.postTranslate(0, -(drawableHeight * scaleX - viewHeight));
        setImageMatrix(matrix);
    }

    public void scrollWindow(float scrollY) {
        final Drawable drawable = getDrawable();
        if (!initialized || drawable == null) {
            return;
        }
        float[] imageMatrixValues = new float[9];
        matrix.getValues(imageMatrixValues);
        float scale = Math.abs(imageMatrixValues[0]) + Math.abs(imageMatrixValues[1]);
        matrix.setScale(scale, scale);
        float translate = scrollY - (drawable.getIntrinsicHeight() * scale - getHeight());
        if (translate > 0) {
            translate = 0;
        }
        matrix.postTranslate(0, translate);
        setImageMatrix(matrix);
    }

}