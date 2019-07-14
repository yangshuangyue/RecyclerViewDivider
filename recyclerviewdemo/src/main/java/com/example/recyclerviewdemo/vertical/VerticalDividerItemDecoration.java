package com.example.recyclerviewdemo.vertical;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalDividerItemDecoration extends RecyclerView.ItemDecoration {
    private int[] attrs = {android.R.attr.listDivider};
    private final Drawable mDrawable;

    public VerticalDividerItemDecoration(Context context) {
        TypedArray a = context.obtainStyledAttributes(attrs);
        mDrawable = a.getDrawable(0);
        a.recycle();
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        drawVerticalLine(c,parent);
    }

    private void drawVerticalLine(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth()-parent.getPaddingRight();
        for(int i=0;i<parent.getChildCount();i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom()+layoutParams.bottomMargin;
            int bottom = top+mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left,top,right,bottom);
            mDrawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(0,0,0,mDrawable.getIntrinsicHeight());
    }
}
