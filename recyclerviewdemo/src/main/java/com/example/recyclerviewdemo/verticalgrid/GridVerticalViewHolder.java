package com.example.recyclerviewdemo.verticalgrid;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.recyclerviewdemo.R;

public class GridVerticalViewHolder extends RecyclerView.ViewHolder {
    public TextView tvContent;
    public GridVerticalViewHolder(@NonNull View itemView) {
        super(itemView);
        tvContent = itemView.findViewById(R.id.tv_content);
    }
}
