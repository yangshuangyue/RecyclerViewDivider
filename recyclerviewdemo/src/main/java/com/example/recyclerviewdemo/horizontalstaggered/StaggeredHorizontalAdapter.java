package com.example.recyclerviewdemo.horizontalstaggered;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.Random;

public class StaggeredHorizontalAdapter extends RecyclerView.Adapter<StaggeredHorizontalViewHolder> {

    private ArrayList<String> mDataList = new ArrayList<>();
    private Random mRandom = new Random();

    @NonNull
    @Override
    public StaggeredHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_horizontal_staggered_layout, viewGroup, false);
        StaggeredHorizontalViewHolder viewHolder = new StaggeredHorizontalViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StaggeredHorizontalViewHolder staggeredHorizontalViewHolder, int i) {
        final String s = mDataList.get(i);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) staggeredHorizontalViewHolder.itemView.getLayoutParams();
        int nextInt = mRandom.nextInt(100);
        layoutParams.width = 100+nextInt;
        staggeredHorizontalViewHolder.itemView.setLayoutParams(layoutParams);
        staggeredHorizontalViewHolder.tvContent.setText(s);
        staggeredHorizontalViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onItemClick(s);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
    public void addAll(ArrayList<String> datas){
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void add(String data){
//        mDataList.add(0,data);
//        //所有都能添加进去。
//        notifyDataSetChanged();
        /**
         * 像是替换功能，但又不是，第零个仿佛替换了新的，
         * 滑出一屏之后，会才会把被替换的条目显示出来。
         */
//        mDataList.add(0,data);
//        notifyItemChanged(0);
        /**
         * 添加进去了，但是刷不出来，需要滑出一屏之后，才能刷出来。
         */
        mDataList.add(0,data);
        notifyItemInserted(0);
    }

    public void remove(){
        if(mDataList.size()<=0){
            return;
        }
        mDataList.remove(0);
        notifyItemRemoved(0);
    }

    private OnItemClickListener mListener;

    public void setmListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }


    public interface OnItemClickListener{
        void onItemClick(String s);
    }
}
