package com.ryd.gyy.guolinstudy.View;

import android.animation.TimeInterpolator;

/**
 * 先减速后加速
 */
public class DecelerateAccelerateInterpolator implements TimeInterpolator {

    @Override
    public float getInterpolation(float input) {
        float result;
        if (input <= 0.5) {
            result = (float) (Math.sin(Math.PI * input)) / 2;
        } else {
            result = (float) (2 - Math.sin(Math.PI * input)) / 2;
        }
        return result;
    }

}
