package com.ryd.gyy.guolinstudy.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 学习Paint
 * Path绘制基本图形
 * Canvas绘制基本图形
 */
public class CustomView extends View {

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //设置画笔的基本属性
//        Paint paint = new Paint();
//        paint.setColor(Color.BLUE); //设置画笔颜色
//        paint.setStyle(Paint.Style.STROKE); //设置填充样式
//        paint.setStrokeWidth(10);    //设置画笔宽度
        //drawColor为画布的背景绘制颜色,0和Color.TRANSPARENT都会使画布显示黑色
//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//        canvas.drawColor(Color.BLACK);

//        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
//        canvas.drawCircle(190, 200, 50, paint);

        //绘制点
//        canvas.drawPoint(50, 50, paint);

        // 在坐标(50,50)(50,250)之间绘制一条直线
//        canvas.drawLine(60, 60, 100, 100, paint);

        //绘制矩形,没有做处理
//        canvas.drawRect(new RectF(110, 80, 150, 130), paint);

        //绘制圆角矩形,没有做处理
//        canvas.drawRoundRect(new RectF(50, 140, 150, 200), 3, 5, paint);

//学习Path线操作----------------------
        //设置Paint
//        Paint paint1 = new Paint();
//        paint1.setColor(Color.RED);
//        paint1.setStyle(Paint.Style.STROKE);
//        paint1.setStrokeWidth(10f);
////设置Path
//        Path path = new Path();
////屏幕左上角（0,0）到（200,400）画一条直线
//        path.lineTo(200, 400);
////(200, 400)到（400,600）画一条直线
//        path.lineTo(400, 600);
////以（400,600）为起始点（0,0）偏移量为（400,600）画一条直线。
////其终点坐标实际在屏幕的位置为（800,1200）
//        path.rLineTo(400, 600);
//        canvas.drawPath(path, paint1);


        //学习Path点操作----------------------
        //初始化Paint
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(10f);
////初始化Path
//        Path path = new Path();
////将坐标系原点从（0,0）移动到（100,100）
//        path.moveTo(100, 100);
////画从（100,100）到（400,400）之间的直线
//        path.lineTo(400, 400);
////        接下来要操作的起点位置为（x+dx,y+dy）
////        path.rMoveTo(0, 100);
////        改变接下来操作的起点位置为（x,y）
////        path.moveTo(0, 100);
//        //新加的setLastPoint:改变前一步操作点的位置。会改变前一步的操作
//        path.setLastPoint(100, 800);
//        path.lineTo(400, 800);
//        canvas.drawPath(path, paint);


        //Path.Direction-----------------------------------
        //初始化Paint
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(2f);
//        paint.setTextSize(40f);
//初始化Path
//        Path path = new Path();
//以（600,600）为圆心。300为半径绘制圆
//文字的方向:Path.Direction.CW顺时针绘制圆 Path.Direction.CCW逆时针绘制圆
//        path.addCircle(600, 600, 300, Path.Direction.CW);
//沿path绘制文字
//        canvas.drawTextOnPath("痛苦最好是别人的，快乐才是自己的；麻烦将是临时的，朋友总是永恒的。 ", path, 0, 0, paint);
//        canvas.drawPath(path, paint);


//绘制常规图形演示样例-----------------------------------
//初始化Paint
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(10f);
//        Path path = new Path();
////以（400,200）为圆心，半径为100绘制圆
//        path.addCircle(400, 200, 100, Path.Direction.CW);
//
////绘制椭圆
//        RectF rectF = new RectF(100, 350, 500, 600);
////第一种方法绘制椭圆
//        path.addOval(rectF, Path.Direction.CW);
////另外一种方法绘制椭圆
//        path.addOval(600, 350, 1000, 600, Path.Direction.CW);
//
////绘制矩形
//        RectF rect = new RectF(100, 650, 500, 900);
////第一种方法绘制矩形
//        path.addRect(rect, Path.Direction.CW);
////第一种方法绘制矩形
//        path.addRect(600, 650, 1000, 900, Path.Direction.CCW);
//
////绘制圆角矩形
//        RectF roundRect = new RectF(100, 950, 300, 1100);
////第一种方法绘制圆角矩形
//        path.addRoundRect(roundRect, 20, 20, Path.Direction.CW);
////另外一种方法绘制圆角矩形
//        path.addRoundRect(350, 950, 550, 1100, 10, 50, Path.Direction.CCW);
////第三种方法绘制圆角矩形
////float[] radii中有8个值，依次为左上角，右上角，右下角，左下角的rx,ry
//        RectF roundRectT = new RectF(600, 950, 800, 1100);
//        path.addRoundRect(roundRectT, new float[]{50, 50, 50, 50, 50, 50, 0, 0}, Path.Direction.CCW);
////第四种方法绘制圆角矩形
//        path.addRoundRect(850, 950, 1050, 1100, new float[]{0, 0, 0, 0, 50, 50, 50, 50}, Path.Direction.CCW);
//        canvas.drawPath(path, paint);

//        学习绘制椭圆-----------------------
        //初始化Paint
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(10f);
//        Path path = new Path();
//        //在(400, 200, 600, 400)区域内绘制一个300度的圆弧
//        RectF rectF = new RectF(400, 200, 600, 400);
//        path.addArc(rectF, 0, 300);
////在(400, 600, 600, 800)区域内绘制一个90度的圆弧。而且不连接两个点
//        RectF rectFTo = new RectF(400, 600, 600, 800);
//        //forceMoveTo：是否强制将path最后一个点移动到圆弧起点。
////true是强制移动，即为不连接两个点。false则连接两个点
//        path.arcTo(rectFTo, 0, 90, false);
////等价于path.addArc(rectFTo, 0, 90);
//        canvas.drawPath(path, paint);


//        闭合path--------------------------------
        //初始化Paint
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(10f);
////初始化Path
//        Path path = new Path();
////将坐标原点移动到（300,300,）
//        path.moveTo(300, 300);
////连接(300, 300)和(300, 600)成一条线
//        path.lineTo(300, 600);
////连接(300, 600)和(600, 600)成一条线
//        path.lineTo(600, 600);
//        //假设path的终点和起始点不是同一个点的话，close()连接这两个点，形成一个封闭的图形
//        path.close();
//        canvas.drawPath(path, paint);

//学习quadTo和rQuadTo-----------------------
        //初始化Paint
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10f);
//初始化Path
        Path path = new Path();

//        绘制曲线
        path.moveTo(200, 200);
        //二阶贝赛尔:其中x1和y1是指控制点的坐标，x2和y2是终点的坐标
//        path.quadTo(300, 300, 400, 200);
//        path.quadTo(500, 100, 600, 200);
//        等同于
        path.rQuadTo(100, 100, 200, 0);
        //rQuadTo的参数的值是以上一个曲线的终点作为参照的相对值
        //dx1和dy1作为当前曲线的控制点相对于上一个终点增加或减少的相对值，可正可负。dx2和dy2则作为当前曲线终点的相对值
        path.rQuadTo(100, -100, 200, 0);
        path.rQuadTo(100, 100, 200, 0);
        path.rQuadTo(100, -100, 200, 0);


//        path.quadTo(300,300,400,200);
//        path.quadTo(500,100,600,200);

//        path.close();
        canvas.drawPath(path, paint);


    }
}
