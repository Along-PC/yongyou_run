package com.tourye.run.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/7/13.
 * 可以和scrollview一起使用的listview
 */
public class MeasureListView extends ListView {
    public MeasureListView(Context context) {
        super(context);
    }
    
    public MeasureListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
