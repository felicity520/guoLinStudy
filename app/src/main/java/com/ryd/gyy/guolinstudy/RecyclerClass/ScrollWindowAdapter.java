package com.ryd.gyy.guolinstudy.RecyclerClass;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryd.gyy.guolinstudy.R;

import java.util.List;

public class ScrollWindowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ScrollWindowAdapter";

    private List<String> mList;

    private LayoutInflater inflater;

    public ScrollWindowAdapter(Context context, List<String> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e(TAG, "onCreateViewHolder viewType: " + viewType);
        if (viewType == 1) {
            return new ScrollWindowViewHolder(inflater.inflate(R.layout.item_scroll_ad_window, parent, false));
        }
        return new NormalViewHolder(inflater.inflate(R.layout.item_scroll_normal, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == 6) {
            ((ScrollWindowViewHolder) holder).imageView.setImageResource(R.drawable.pet);
        } else {
            ((NormalViewHolder)holder).normalText.setText(mList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 6) {
            return 1;
        }
        return 0;
    }
}
