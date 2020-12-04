package com.ryd.gyy.guolinstudy.RecyclerClass;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.View.ScrollWindowImageView;

public class ScrollWindowViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "ScrollWindowViewHolder";

    public ScrollWindowImageView imageView;

    ScrollWindowViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setTag(this);
        imageView = itemView.findViewById(R.id.ad_window_image_view);
    }

    public void onScrolled(RecyclerView recyclerView, View itemView, int dx, int dy) {
        int parentBottom = recyclerView.getBottom();
        int itemBottom = itemView.getBottom();
        Log.e(TAG, "onScrolled parentBottom: " + parentBottom + "------itemBottom:" + itemBottom);
        if (itemBottom < parentBottom) {
            int scrollY = parentBottom - itemBottom;
            imageView.scrollWindow(scrollY);
        }
    }
}
