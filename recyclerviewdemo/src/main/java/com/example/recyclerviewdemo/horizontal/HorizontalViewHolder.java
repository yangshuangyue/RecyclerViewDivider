package com.example.recyclerviewdemo.horizontal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.recyclerviewdemo.R;

public class HorizontalViewHolder extends RecyclerView.ViewHolder {
    public TextView tvContent;
    public HorizontalViewHolder(@NonNull View itemView) {
        super(itemView);
        tvContent = itemView.findViewById(R.id.tv_content);
    }
}
