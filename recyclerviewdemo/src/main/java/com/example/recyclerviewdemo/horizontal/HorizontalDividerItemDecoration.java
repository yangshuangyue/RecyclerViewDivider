package com.example.recyclerviewdemo.horizontal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class HorizontalDividerItemDecoration extends RecyclerView.ItemDecoration {

    private int[] attrs = {android.R.attr.listDivider};
    private final Drawable mDrawable;


    public HorizontalDividerItemDecoration(Context context) {
        TypedArray a = context.obtainStyledAttributes(attrs);
        mDrawable = a.getDrawable(0);
        a.recycle();
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawHorizontalLine(c,parent);
    }

    private void drawHorizontalLine(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        for(int i=0;i<parent.getChildCount();i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight()+layoutParams.rightMargin;
            int right = left+mDrawable.getIntrinsicWidth();
            mDrawable.setBounds(left,top,right,bottom);
            mDrawable.draw(c);
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0,0,mDrawable.getIntrinsicWidth(),0);
    }
}
