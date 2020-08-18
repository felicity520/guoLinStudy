package com.ryd.gyy.guolinstudy.View;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ryd.gyy.guolinstudy.R;

/**
 * 原创作者：谷哥的小弟
 * <p>
 * 博客地址：
 * http://blog.csdn.net/lfdfhl
 */
public class CollapseView extends LinearLayout {

    private static final String TAG = "CollapseView";

    private long duration = 350;
    private Context mContext;
    private TextView mNumberTextView;
    private TextView mTitleTextView;
    private RelativeLayout mContentRelativeLayout;
    private RelativeLayout mTitleRelativeLayout;
    private ImageView mArrowImageView;
    int parentWidthMeasureSpec;
    int parentHeightMeasureSpec;

    public CollapseView(Context context) {
        this(context, null);
    }

    public CollapseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.collapse_layout, this);
        initView();
    }


    private void initView() {
        mNumberTextView = (TextView) findViewById(R.id.numberTextView);
        mTitleTextView = (TextView) findViewById(R.id.titleTextView);
        mTitleRelativeLayout = (RelativeLayout) findViewById(R.id.titleRelativeLayout);
        mContentRelativeLayout = (RelativeLayout) findViewById(R.id.contentRelativeLayout);
        mArrowImageView = (ImageView) findViewById(R.id.arrowImageView);
        mTitleRelativeLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateArrow();
            }
        });

        collapse(mContentRelativeLayout);
    }


    public void setNumber(String number) {
        if (!TextUtils.isEmpty(number)) {
            mNumberTextView.setText(number);
        }
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitleTextView.setText(title);
        }
    }

    public void setContent(int resId) {
        View view = LayoutInflater.from(mContext).inflate(resId, null);
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        mContentRelativeLayout.addView(view);
    }


    public void rotateArrow() {
        int degree = 0;
//        setTag是存储数据或者标记作用，这里第一次进入没有set，获取get = null
        if (mArrowImageView.getTag() == null || mArrowImageView.getTag().equals(true)) {
            Log.e(TAG, "rotateArrow getTag() is null");
            mArrowImageView.setTag(false);
            degree = -180;
            expand(mContentRelativeLayout);
        } else {
            Log.e(TAG, "rotateArrow getTag() is not null");
            degree = 0;
            mArrowImageView.setTag(true);
            collapse(mContentRelativeLayout);
        }
        mArrowImageView.animate().setDuration(duration).rotation(degree);
    }

    /**
     * 多次调用onMeasure
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        parentWidthMeasureSpec = widthMeasureSpec;
        parentHeightMeasureSpec = heightMeasureSpec;
        Log.e(TAG, "parentWidthMeasureSpec: " + parentWidthMeasureSpec + "parentHeightMeasureSpec:" + parentHeightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    // 展开
    private void expand(final View view) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        //手动测量View的方式获取View的大小
        view.measure(parentWidthMeasureSpec, parentHeightMeasureSpec);
        final int measuredWidth = view.getMeasuredWidth();
        final int measuredHeight = view.getMeasuredHeight();
        view.setVisibility(View.VISIBLE);

        Animation animation = new Animation() {
            /**
             * 在绘制Animation的过程中会反复的调用applyTransformation函数，
             * @param interpolatedTime 同样也是跑两次if
             * @param t
             */
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.getLayoutParams().height = measuredHeight;
                    Log.e(TAG, "expand 1: ");
                } else {
                    Log.e(TAG, "expand 0: ");
                    view.getLayoutParams().height = (int) (measuredHeight * interpolatedTime);
                }
                view.requestLayout();//刷新界面
            }


            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    // 折叠
    private void collapse(final View view) {
        final int measuredHeight = view.getMeasuredHeight();
        Animation animation = new Animation() {
            /**
             * @param interpolatedTime 绘制的过程中是0，绘制完成是1，所以是不断的调用else，最后调用两次if，为什么是两次，还要需要研究
             * @param t
             */
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                    Log.e(TAG, "collapse 1: ");
                } else {
                    Log.e(TAG, "collapse 0: ");
                    view.getLayoutParams().height = measuredHeight - (int) (measuredHeight * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
    }
}
