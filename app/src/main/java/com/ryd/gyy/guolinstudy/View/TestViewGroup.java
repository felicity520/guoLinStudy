package com.ryd.gyy.guolinstudy.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class TestViewGroup extends ViewGroup {

    private static final String TAG = "TestViewGroup";

    public TestViewGroup(Context context) {
        super(context);
    }

    public TestViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 为什么非要重写generateLayoutParams（）函数了，
     * 就是因为默认的generateLayoutParams（）函数只会提取layout_width、layout_height的值，
     * 只有MarginLayoutParams（）才具有提取margin间距的功能！！！！
     *
     * @param p
     * @return
     */
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        //只关心子元素的 margin 信息，所以这里用 MarginLayoutParams，不加这句，下面会报错
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


//        当 TestViewGroup 测量模式为 MeasureSpec.AT_MOST 时，这就需要 TestViewGroup 自己计算尺寸数值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

//        默认还是父布局的大小
        int resultWidth = widthSize;
        int resultHeight = heightSize;

        int contentWidth = 0;
        int contentHeight = 0;

        /**对子元素进行尺寸的测量，这一步必不可少*/
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        MarginLayoutParams layoutParams;
        int count = getChildCount();
        Log.e(TAG, "onMeasure count: " + count);
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);

            if (childView.getVisibility() == View.GONE) {
//                进行下一次测量
                continue;
            }

            layoutParams = (MarginLayoutParams) childView.getLayoutParams();
//            总的宽度 = padding + margin + contentWidth
            contentWidth = contentWidth + getPaddingLeft() + getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin + childView.getMeasuredWidth();
            contentHeight = contentWidth + getPaddingTop() + getPaddingBottom() + layoutParams.topMargin + layoutParams.bottomMargin + childView.getMeasuredHeight();
        }


//        处理子View是wrap_content的情况
        if (widthMode == MeasureSpec.AT_MOST) {
            resultWidth = Math.min(contentWidth, widthSize);
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            resultHeight = Math.min(contentHeight, resultHeight);
        }

//        使用这个方法进行测量：传入测量内容的宽高
        setMeasuredDimension(resultWidth, resultHeight);

    }

    /**
     * 只要是继承自ViewGroup就必须实现onLayout方法
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        MarginLayoutParams layoutParams;
        int measureLeft = 0;
        int measureTop = 0;
        int count = getChildCount();
        Log.e(TAG, "onLayout count: " + count);
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);

            if (childView.getVisibility() == View.GONE) {
//                进行下一次测量
                continue;
            }

            layoutParams = (MarginLayoutParams) childView.getLayoutParams();
            measureLeft += layoutParams.leftMargin;
            measureTop += layoutParams.topMargin;

//            调用childView.layout完成绘制
//            一个View的宽= padding左右 + Margin左右 + view内容的宽
            childView.layout(measureLeft, measureTop,
                    measureLeft + layoutParams.rightMargin + childView.getMeasuredWidth() + getPaddingLeft() + getPaddingRight(),
                    measureTop + layoutParams.bottomMargin + childView.getMeasuredHeight() + getPaddingBottom() + getPaddingBottom());
//            这里计算的宗旨是：下一个布局的宽 = 上一个布局的宽 + 下一个布局的宽  也就是
//                               measureLeft = measureLeft + 下一个布局
            measureLeft += layoutParams.rightMargin + childView.getMeasuredWidth() + getPaddingLeft() + getPaddingRight();
            measureTop += layoutParams.bottomMargin + childView.getMeasuredHeight() + getPaddingBottom() + getPaddingBottom();

        }

    }
}
