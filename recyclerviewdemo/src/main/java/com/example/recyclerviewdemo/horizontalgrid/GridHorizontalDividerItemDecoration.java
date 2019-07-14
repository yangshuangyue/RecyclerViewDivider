package com.example.recyclerviewdemo.horizontalgrid;

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

public class GridHorizontalDividerItemDecoration extends RecyclerView.ItemDecoration {

    private int[] attrs = {android.R.attr.listDivider};
    private final Drawable mDrawable;

    public GridHorizontalDividerItemDecoration(Context context) {
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
            int bottom = child.getBottom() + layoutParams.bottomMargin;
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
            Log.i("ysy", "drawVerticalLine layoutParams.leftMargin:" + layoutParams.leftMargin);
            int left = child.getLeft() - layoutParams.leftMargin;
            Log.i("ysy", "child.getLeft():" + child.getLeft());
            int right = child.getRight() + layoutParams.rightMargin+mDrawable.getIntrinsicWidth();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mDrawable.getIntrinsicHeight();

            if(i == 2){
                Log.i("ysy","left:"+left+" right:"+right+" top:"+top+" bottom:"+bottom);
            }
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
        int itemPosition = parent.getChildLayoutPosition(view);

        int itemCount = parent.getAdapter().getItemCount();
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        int spanCount = layoutManager.getSpanCount();
        if(isHorizontalLastColumn(itemPosition,spanCount,itemCount)){
            Log.i("ysy","itemPosition"+itemPosition);
            if(isHorizontalLastRow(itemPosition,spanCount)){
                outRect.set(0,mDrawable.getIntrinsicHeight()*(spanCount-1)/spanCount,0,0);
            }else if(isHorizontalFirstRow(itemPosition,spanCount)){
                outRect.set(0,0,0,mDrawable.getIntrinsicHeight()*(spanCount-1)/spanCount);
            }else {
                outRect.set(0,mDrawable.getIntrinsicHeight()*(spanCount-1-itemPosition%spanCount)/spanCount,0,mDrawable.getIntrinsicHeight()*(itemPosition%spanCount)/spanCount);
            }
        }else{
            if(isHorizontalLastRow(itemPosition,spanCount)){
                outRect.set(0,mDrawable.getIntrinsicHeight()*(spanCount-1)/spanCount,mDrawable.getIntrinsicWidth(),0);
            }else if(isHorizontalFirstRow(itemPosition,spanCount)){
                outRect.set(0,0,mDrawable.getIntrinsicWidth(),mDrawable.getIntrinsicHeight()*(spanCount-1)/spanCount);
            }else {
                outRect.set(0,mDrawable.getIntrinsicHeight()*(spanCount-1-itemPosition%spanCount)/spanCount,mDrawable.getIntrinsicWidth(),mDrawable.getIntrinsicHeight()*(itemPosition%spanCount)/spanCount);
            }
        }
    }

    /**
     * Grid的时候，orientation为Vertical时,判断是不是最后一行
     *
     * @param itemPosition
     * @param spanCount
     * @return
     */
    public boolean isHorizontalLastRow(int itemPosition, int spanCount) {
        if(itemPosition%spanCount == (spanCount-1)){
            return true;
        }
        return false;
    }

    /**
     * 判断是不是横向的最后一列
     * @param itemPosition
     * @param spanCount
     * @param itemCount 该参数不能传为int childCount = parent.getChildCount();它返回的是当前显示的子View个数
     * @return
     */
    public boolean isHorizontalLastColumn(int itemPosition, int spanCount, int itemCount) {
        Log.i("ysy","itemPosition:"+itemPosition+" spanCount:"+spanCount+" itemCount:"+itemCount);
        if(itemCount%spanCount == 0){
            if(itemPosition>=spanCount*(itemCount/spanCount-1)){
                return true;
            }
        }else{
            int temp  = itemCount-itemCount%spanCount;
            Log.i("ysy","temp:"+temp);
            if(itemPosition>=temp){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是不是横向的最后一行
     * @param itemPosition
     * @param spanCount
     * @return
     */
    public boolean isHorizontalFirstRow(int itemPosition, int spanCount){
        if(itemPosition%spanCount == 0){
            return true;
        }
        return false;
    }
}
