package com.example.recyclerviewdemo.verticalgrid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class GridVerticalDividerItemDecoration extends RecyclerView.ItemDecoration {

    private int[] attrs = {android.R.attr.listDivider};
    private final Drawable mDrawable;

    public GridVerticalDividerItemDecoration(Context context) {
        TypedArray a = context.obtainStyledAttributes(attrs);
        mDrawable = a.getDrawable(0);
        a.recycle();
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        Log.i("ysy", "onDraw");
        drawVerticalLine(c, parent);
        drawHorizontalLine(c, parent);
    }

    /**
     * 话竖线
     *
     * @param c
     * @param parent
     */
    private void drawHorizontalLine(Canvas c, RecyclerView parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getTop() - layoutParams.topMargin;
            //如果分割线有高的话，那么分割线的交汇处，就会有空隙，mDrawable.getIntrinsicHeight()就是为了补空隙
            int bottom = child.getBottom() + layoutParams.bottomMargin + mDrawable.getIntrinsicHeight();
            int left = child.getRight() + layoutParams.rightMargin;
            int right = left + mDrawable.getIntrinsicWidth();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }

    /**
     * 画水横线
     *
     * @param c
     * @param parent
     */
    private void drawVerticalLine(Canvas c, RecyclerView parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            /**
             *  如果这里打印的值，是我算法算出的值，就可以证明在getItemOffsets方法中，设置的距离就是layoutParams
             *  经验证，发现并非如此，可以推断getItemOffsets方法中的设置，设置的每一个孩子的偏移位置。
             */
            int left = child.getLeft() - layoutParams.leftMargin;
            int right = child.getRight() + layoutParams.rightMargin;
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }


    /**
     * 先走getItemOffsets方法，再走onDraw方法，所以这里设置的值影响onDraw画线，根据onDraw画线编码的逻辑，可以判断出，这里设置的距离，是给RecyclerView的子View设置偏移量,这是我的想法。
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect,view,parent,state);
        Log.i("ysy", "getItemOffsets");
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        int spanCount = layoutManager.getSpanCount();
        int itemPosition = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();

        if (isVerticalLastRow(itemPosition, spanCount, itemCount)) {//如果是最后一行，则不需要底部偏移，但是左右需要偏移距离，与当前条目的列数有关，左边偏移的距离是分割线宽度的(itemPosition%spanCount)/spanCount，右边偏移的距离是分割线宽度的(spanCount-1-(itemPosition%spanCount))/spanCount
            if (isVerticalFirstColun(itemPosition, spanCount)) {//如果是第一列，则左边的位置不变，右边需要向左移动分割线宽度的（spanCount-1）/spanCount,从而达到每一个Item的大小都相等。
                outRect.set(0, 0, mDrawable.getIntrinsicWidth() * (spanCount - 1) / spanCount, 0);
            }else if(isVerticalLastColumn(itemPosition, spanCount)){
                outRect.set(mDrawable.getIntrinsicWidth() * (spanCount - 1) / spanCount, 0, 0, 0);
            }else{
                outRect.set(mDrawable.getIntrinsicWidth() * (itemPosition % spanCount) / spanCount, 0, mDrawable.getIntrinsicWidth() * (spanCount - 1 - (itemPosition % spanCount)) / spanCount, 0);
            }
        } else {//如果不是最后一列，也不是第一列，更不是最后一行，则右需要偏移距离，与当前条目的列数有关，左边偏移的距离是分割线宽度的(itemPosition%spanCount)/spanCount，右边偏移的距离是分割线宽度的(spanCount-1-(itemPosition%spanCount))/spanCount
            if (isVerticalLastColumn(itemPosition, spanCount)) {//如果是最后一列，则右边的位置不变，左边的线需要移动分割线宽度的（spanCount-1）/spanCount,从而达到每一个Item的大小都相等。
                outRect.set(mDrawable.getIntrinsicWidth() * (spanCount - 1) / spanCount, 0, 0, mDrawable.getIntrinsicHeight());
            } else if (isVerticalFirstColun(itemPosition, spanCount)) {//如果是第一列，则左边的位置不变，右边需要向左移动分割线宽度的（spanCount-1）/spanCount,从而达到每一个Item的大小都相等。
                outRect.set(0, 0, mDrawable.getIntrinsicWidth() * (spanCount - 1) / spanCount, mDrawable.getIntrinsicHeight());
            }else{
                outRect.set(mDrawable.getIntrinsicWidth() * (itemPosition % spanCount) / spanCount, 0, mDrawable.getIntrinsicWidth() * (spanCount - 1 - (itemPosition % spanCount)) / spanCount, mDrawable.getIntrinsicHeight());
            }
        }
    }

    /**
     * Grid的时候，orientation为Vertical时,判断是不是最后一行
     *
     * @param itemPosition
     * @param spanCount
     * @param itemCount
     * @return
     */
    public boolean isVerticalLastRow(int itemPosition, int spanCount, int itemCount) {
        /**
         * itemPosition大于等于最后一行的第一个位置的index都是最后一行
         */

        if (itemCount%spanCount == 0) {
            int temp = spanCount * (itemCount / spanCount - 1);
            if (itemPosition >= temp) {
                return true;
            }
        }else{
            itemCount = itemCount - itemCount % spanCount;
            if (itemPosition >= itemCount) {
                return true;
            }
        }
        return false;
    }

    public boolean isVerticalLastColumn(int itemPosition, int spanCount) {
        if ((spanCount - 1) == itemPosition % spanCount)
            return true;
        return false;
    }

    public boolean isVerticalFirstColun(int itemPosition, int spanCount) {
        if (0 == itemPosition % spanCount)
            return true;
        return false;
    }
}
