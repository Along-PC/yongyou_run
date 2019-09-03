package com.tourye.run.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.ViewFlipper;

import com.tourye.run.base.BaseApplication;

public class SmoothScrollLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled=false;

    public SmoothScrollLayoutManager(Context context) {
        super(context);
    }

//    @Override
//    public boolean canScrollVertically() {
//        return isScrollEnabled && super.canScrollVertically();
//    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, final int position) {

        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            // 返回：滑过1px时经历的时间(ms)。
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 1000f / displayMetrics.densityDpi;
            }
        };
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);

    }

}
