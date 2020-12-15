package com.ryd.gyy.guolinstudy.RecyclerClass;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ryd.gyy.guolinstudy.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder> {

    private static final String TAG = "FruitAdapter";

    private Context mContext;

    private List<DemoBean> mDemoList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.demo_image)
        ImageView demoImage;

        @BindView(R.id.demo_name)
        TextView demoName;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
//            cardView = (CardView) view;
//            demoImage = (ImageView) view.findViewById(R.id.demo_image);
//            demoName = (TextView) view.findViewById(R.id.demo_name);
        }
    }

    public DemoAdapter(List<DemoBean> demoList) {
        mDemoList = demoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.demo_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                DemoBean demoBean = mDemoList.get(position);
                Log.i(TAG, "action: " + demoBean.getAction());
                Intent intent = new Intent(mContext, demoBean.getAction());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DemoBean demoBean = mDemoList.get(position);
        holder.demoName.setText(demoBean.getName());
        holder.demoImage.setImageResource(demoBean.getImageId());
//        Glide.with(mContext).load(demoBean.getImageId()).into(holder.demoImage);
    }

    @Override
    public int getItemCount() {
        return mDemoList.size();
    }

}
