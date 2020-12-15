package com.ryd.gyy.guolinstudy.View;

import android.animation.TypeEvaluator;

/**
 * TypeEvaluator决定了动画如何从初始值过渡到结束值。这个TypeEvaluator是个接口，我们可以实现这个接口。
 * 决定了变化的轨迹
 */
public class PointSinEvaluator implements TypeEvaluator {

    /**
     * @param fraction   当前动画完成的百分比
     * @param startValue 动画的初始值
     * @param endValue   动画的结束值
     * @return
     */
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());

        float y = (float) (Math.sin(x * Math.PI / 180) * 100) + endPoint.getY() / 2;
        Point point = new Point(x, y);
        return point;
    }
}

