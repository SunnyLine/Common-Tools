package com.pullein.common.android.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * CommonDecoration.java<br>
 * describe : RecyclerView 分割线
 *
 * @author xugang
 * @date 2018/1/7
 */
public class CommonDecoration extends RecyclerView.ItemDecoration {

    private int mOrientation;
    private int mDividerHeight;
    private Drawable mDivider;
    private boolean isHideLastDecoration = false;
    public static final int STYLE_LINE_HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static final int STYLE_LINE_VERTICAL = LinearLayoutManager.VERTICAL;

    public CommonDecoration(Context mContext, @ColorInt int colorId) {
        this(mContext, STYLE_LINE_HORIZONTAL, 1, new ColorDrawable(colorId), false);
    }

    public CommonDecoration(Context mContext, @ColorInt int colorId, int mDividerHeight) {
        this(mContext, STYLE_LINE_HORIZONTAL, mDividerHeight, new ColorDrawable(colorId), false);
    }

    public CommonDecoration(Context mContext, @ColorInt int colorId, boolean isHideLastDecoration) {
        this(mContext, STYLE_LINE_HORIZONTAL, 1, new ColorDrawable(colorId), isHideLastDecoration);
    }

    public CommonDecoration(Context mContext, int mOrientation, int mDividerHeight, Drawable mDivider, boolean isHideLastDecoration) {
        this.mOrientation = mOrientation;
        this.mDividerHeight = mDividerHeight;
        this.mDivider = mDivider;
        this.isHideLastDecoration = isHideLastDecoration;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == STYLE_LINE_VERTICAL) {
            drawVerticalLine(c, parent, state);
        } else {
            drawHorizontalLine(c, parent, state);
        }
    }

    //画横线, 这里的parent其实是显示在屏幕显示的这部分
    public void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = isHideLastDecoration ? parent.getChildCount() - 1 : parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            //获得child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
            //Log.d("wnw", left + " " + top + " "+right+"   "+bottom+" "+i);
        }
    }

    //画竖线
    public void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = isHideLastDecoration ? parent.getChildCount() - 1 : parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            //获得child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == STYLE_LINE_HORIZONTAL) {
            //画横线，就是往下偏移一个分割线的高度
            outRect.set(0, 0, 0, mDividerHeight);
        } else {
            //画竖线，就是往右偏移一个分割线的宽度
            outRect.set(0, 0, mDividerHeight, 0);
        }
    }
}

