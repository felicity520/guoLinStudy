package com.ryd.gyy.guolinstudy.RecyclerClass;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ryd.gyy.guolinstudy.R;

public class NormalViewHolder extends RecyclerView.ViewHolder {

    public TextView normalText;

    public NormalViewHolder(View view) {
        super(view);
        normalText = (TextView) view.findViewById(R.id.normalText);
    }

}