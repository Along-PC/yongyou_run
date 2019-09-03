package com.tourye.run.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * @ClassName: AdvertiseView
 * @Author: along
 * @Description: 拦截点击事件的线性布局
 * @CreateDate: 2019/7/11 9:31 AM
 */
public class AdvertiseView extends LinearLayout {

    private OnAdvertiseClick mOnAdvertiseClick;

    private GestureDetector mGestureDetector = new GestureDetector(new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    });

    public AdvertiseView(Context context) {
        super(context);
    }

    public AdvertiseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdvertiseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnAdvertiseClick(OnAdvertiseClick onAdvertiseClick) {
        mOnAdvertiseClick = onAdvertiseClick;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    private long down_time;
    private long up_time;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down_time=System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                up_time=System.currentTimeMillis();
                if (up_time-down_time<300) {
                    if (mOnAdvertiseClick != null) {
                        mOnAdvertiseClick.onClick();
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    public interface OnAdvertiseClick {
        public void onClick();
    }
}
