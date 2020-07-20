package com.ryd.gyy.guolinstudy.View;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/***
 * 自定义RecyclerView，在TV上用的比较多
 */
public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(@NonNull Context context) {
        super(context);
    }

    /***
     * 在TV上解决item放大时候，被其他item遮挡
     * 可参考：https://blog.csdn.net/jdsjlzx/article/details/107375540
     * @param childCount
     * @param i
     * @return
     */
    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        setChildrenDrawingOrderEnabled(true);
        View view = getLayoutManager().getFocusedChild();
        if (null == view) {
            return super.getChildDrawingOrder(childCount, i);
        }
        int position = indexOfChild(view);
        if (position < 0) {
            return super.getChildDrawingOrder(childCount, i);
        }
        if (i == childCount - 1) {
            return position;
        }
        if (i == position) {
            return childCount - 1;
        }
        return super.getChildDrawingOrder(childCount, i);
    }

}
