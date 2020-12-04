package com.ryd.gyy.guolinstudy.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.RecyclerClass.ScrollWindowAdapter;
import com.ryd.gyy.guolinstudy.RecyclerClass.ScrollWindowViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 参考博客：https://blog.csdn.net/qq_27053103/article/details/103721026
 */
public class ScrollWindowActivity extends Activity {

    private static final String TAG = "ScrollWindowActivity";
    private List<String> fruitList = new ArrayList<>();
    private String[] fruits = {"ZXC", "123", "456+", "15855", "15855", "15855", "15855", "15855", "15855", "15855", "15855", "15855", "15855"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_window);

        initData();

        RecyclerView recyclerView = findViewById(R.id.scroll_window_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ScrollWindowAdapter adapter = new ScrollWindowAdapter(this, fruitList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(scrollListener);

        adapter.notifyDataSetChanged();
    }

    private void initData() {
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        // 获取屏幕宽高 width = 1080,height = 2118
        Log.d(TAG, "width = " + width + ",height = " + height);

        fruitList.clear();
        for (int i = 0; i < 15; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {


        /**
         *
         *  SCROLL_STATE_IDLE（静止没有滚动）
         *  SCROLL_STATE_DRAGGING(正在被外部拖拽,一般为用户正在用手指滚动)
         *  SCROLL_STATE_SETTLING (自动滚动)
         *
         * @param recyclerView 当前在滚动的RecyclerView
         * @param newState 当前滚动状态.
         */
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //判断是否滑动到底部
            isScrollBottom(recyclerView);
        }

        /**
         * dx > 0 时为手指向左滚动,列表滚动显示右面的内容
         * dx < 0 时为手指向右滚动,列表滚动显示左面的内容
         * dy > 0 时为手指向上滚动,列表滚动显示下面的内容
         * dy < 0 时为手指向下滚动,列表滚动显示上面的内容
         *
         * @param recyclerView 当前滚动的view
         * @param dx 水平滚动距离
         * @param dy 垂直滚动距离
         */
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int childCount = layoutManager.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = layoutManager.getChildAt(i);
                Object tag = view.getTag();
                if (tag instanceof ScrollWindowViewHolder) {
                    ScrollWindowViewHolder holder = (ScrollWindowViewHolder) tag;
                    holder.onScrolled(recyclerView, view, dx, dy);
                }
            }
        }
    };


    /**
     * 判断RecyclerView是否滚动到底部
     *
     * @param recyclerView
     * @return
     */
    public boolean isVisBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        Log.e(TAG, "isVisBottom visibleItemCount: " + visibleItemCount +
                "\nlastVisibleItemPosition:" + lastVisibleItemPosition +
                "\ntotalItemCount:" + totalItemCount);
        return visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1;
    }

    /**
     * 判断RecyclerView是否滚动到底部
     *
     * public boolean canScrollVertically (int direction)
     * 这个方法是判断View在竖直方向是否还能向上，向下滑动。
     * 其中，direction为 -1 表示手指向下滑动（屏幕向上滑动）， 1 表示手指向上滑动（屏幕向下滑动）。
     *
     * public boolean canScrollHorizontally (int direction)
     * 这个方法用来判断 水平方向的滑动
     *
     * RecyclerView.canScrollVertically(1)的值表示是否能向下滚动，false表示已经滚动到底部
     * RecyclerView.canScrollVertically(-1)的值表示是否能向上滚动，false表示已经滚动到顶部
     */
    private void isScrollBottom(RecyclerView recyclerView) {
        if (!recyclerView.canScrollVertically(1)) {
            Log.e(TAG, "isScrollBottom: ---已经到底部了");
        }
    }


}
