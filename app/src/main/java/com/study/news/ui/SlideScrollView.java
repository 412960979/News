package com.study.news.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 监听ScrollView滑动状态的改变
 */

public class SlideScrollView extends ScrollView {
    private OnScollChangedListener onScollChangedListener = null;

    public SlideScrollView(Context context) {
        super(context);
    }

    public SlideScrollView(Context context, AttributeSet attrs,
                           int defStyle) {
        super(context, attrs, defStyle);
    }

    public SlideScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScollChangedListener(OnScollChangedListener onScollChangedListener) {
        this.onScollChangedListener = onScollChangedListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (onScollChangedListener != null) {
            onScollChangedListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public interface OnScollChangedListener {

        void onScrollChanged(SlideScrollView scrollView, int x, int y, int oldx, int oldy);

    }
}