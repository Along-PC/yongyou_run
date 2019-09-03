package com.tourye.run.views;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 *
 * @ClassName:   MyScrollview
 *
 * @Author:   along
 *
 * @Description:    重写滚动监听的scrollview
 *
 * @CreateDate:   2019/4/9 6:31 PM
 *
 */
public class MyScrollview extends NestedScrollView {

    private OnScrollListener listener;

    public void setOnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }

    public MyScrollview(Context context) {
        super(context);
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //设置接口
    public interface OnScrollListener{
        void onScroll(int scrollY);
    }

    // 1. 第一个参数是目前水平滑动后的距离
    // 2. 第二个参数是目前垂直滑动后的距离
    // 3. 第三个参数是之前水平滑动前的距离
    // 4. 第四个参数是之前垂直滑动前的距离
    //重写原生onScrollChanged方法，将参数传递给接口，由接口传递出去
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(listener != null){
            //这里我只传了垂直滑动的距离
            listener.onScroll(t);
        }
    }
}
