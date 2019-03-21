package com.junshan.pub.listener;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;

/**
 * Created by chenjunshan on 17-6-19.
 */

public abstract class RecyclerViewScrollDetector extends OnScrollListener {
    private int mScrollThreshold;

    protected abstract void onScrollUp();

    protected abstract void onScrollDown();

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
        if (isSignificantDelta) {
            if (Math.abs(dy)<20){
                return;
            }
            if (dy > 0) {
                onScrollUp();
            } else {
                onScrollDown();
            }
        }
    }

    public void setScrollThreshold(int scrollThreshold) {
        mScrollThreshold = scrollThreshold;
    }
}
