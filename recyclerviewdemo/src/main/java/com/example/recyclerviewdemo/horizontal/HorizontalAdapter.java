package com.example.recyclerviewdemo.horizontal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recyclerviewdemo.R;

import java.util.ArrayList;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalViewHolder> {

    private ArrayList<String> mDataList = new ArrayList<>();

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_horizontal_layout, viewGroup, false);
        HorizontalViewHolder viewHolder = new HorizontalViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder horizontalViewHolder, int i) {
        final String item = mDataList.get(i);
        horizontalViewHolder.tvContent.setText(item);
        horizontalViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onItemClick(item);
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
        //所有都能添加进去。
        mDataList.add(0,data);
        notifyDataSetChanged();
        /**
         * 像是替换功能，但又不是，第零个仿佛替换了新的，
         * 滑出一屏之后，会才会把被替换的条目显示出来。
         */
//        mDataList.add(0,data);
//        notifyItemChanged(0);
        /**
         * 添加进去了，但是刷不出来，需要滑出一屏之后，才能刷出来。
         */
//        mDataList.add(0,data);
//        notifyItemInserted(0);
    }

    public void remove(){
        if(mDataList.size()<=0){
            return;
        }
        mDataList.remove(0);
//        notifyItemRemoved(0);
        notifyDataSetChanged();
    }

    private OnItemClickListener mListener;

    public void setmListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }


    public interface OnItemClickListener{
        void onItemClick(String s);
    }
}
