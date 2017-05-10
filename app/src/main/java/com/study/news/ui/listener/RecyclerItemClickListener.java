package com.study.news.ui.listener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Recycler Item 触摸事件监听类（点击和长按）
 */

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongPressListener mOnItemLongPress;
    private GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        mOnItemClickListener = listener;

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemLongPressListener listener){
        mOnItemLongPress = listener;

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());

                if (childView != null) {
                    mOnItemLongPress.onItemLongPress(childView, recyclerView.getChildAdapterPosition(childView));
                }
            }
        });
    }

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener clickListener, OnItemLongPressListener longPressListener){
        mOnItemClickListener = clickListener;
        mOnItemLongPress = longPressListener;

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());

                if (childView != null && mOnItemClickListener != null) {
                    mOnItemLongPress.onItemLongPress(childView, recyclerView.getChildAdapterPosition(childView));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());

        if (childView != null && mOnItemClickListener != null && mGestureDetector.onTouchEvent(e)) {
            mOnItemClickListener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}