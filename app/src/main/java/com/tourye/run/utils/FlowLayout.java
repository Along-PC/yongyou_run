package com.tourye.run.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Title:$type_name
 * <p>
 * Description:流式布局
 * </p>
 * Author along
 * Date 2017/10/23 0023 ${Time}
 */

public class FlowLayout extends ViewGroup {

    //获取每一行的集合
    private List<List<View>> mLineList=new ArrayList<>();
    //获取每行行高的集合
    private List<Integer> mLineHeight=new ArrayList<>();

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取父控件的宽高和测量模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //最大宽高
        int width = 0;
        int height = 0;

        //当前行宽高
        int lineWidth=0;
        int lineHeight=0;

        //子控件数量
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {

            View child = getChildAt(i);
            //测量子控件
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams marginLayoutParams= (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            int childHeight = child.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;

            if (childWidth+lineWidth>widthSize-getPaddingLeft()-getPaddingRight()) {
                width=Math.max(lineWidth,width);
                lineWidth=childWidth;
                height+=lineHeight;
                lineHeight=childHeight;

            }else{
                lineWidth+=childWidth;
                lineHeight=Math.max(lineHeight,childHeight);
            }

            //处理最后一行
            if (i==childCount-1) {
                height+=lineHeight;
                width=Math.max(width,lineWidth);
            }
        }
        height=height+getPaddingTop()+getPaddingBottom();
        setMeasuredDimension(
                widthMode==MeasureSpec.EXACTLY?widthSize:width,
                heightMode==MeasureSpec.EXACTLY?heightSize:height
        );
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        //必须先清空----不然前面的子view就消失了
        mLineList.clear();
        mLineHeight.clear();

        int width = getWidth();

        int lineWidth=0;
        int lineHeight=0;

        int childCount = getChildCount();

        //记录当前行的view
        List<View> lineViews=new ArrayList<>();
        for (int num = 0; num < childCount; num++) {
            View view = getChildAt(num);

            MarginLayoutParams marginLayoutParams= (MarginLayoutParams) view.getLayoutParams();
            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();

            if (measuredWidth+lineWidth+marginLayoutParams.leftMargin+marginLayoutParams.rightMargin>width-getPaddingLeft()-getPaddingRight()) {
                //记录当前行view
                mLineList.add(lineViews);
                //记录当前行的行高
                mLineHeight.add(lineHeight);
                //重置行的宽高
                lineHeight=measuredHeight;
                lineWidth=0;
                lineViews=new ArrayList<>();
            }
            lineWidth+=measuredWidth+marginLayoutParams.leftMargin+marginLayoutParams.rightMargin;
            lineHeight=Math.max(lineHeight,measuredHeight+marginLayoutParams.topMargin+marginLayoutParams.bottomMargin);
            lineViews.add(view);
        }

        //处理最后一行
        mLineHeight.add(lineHeight);
        mLineList.add(lineViews);

        //设置子view的位置
        int left=getPaddingLeft();
        int top=getPaddingTop();

        //获取行数
        int lineNum = mLineList.size();
        for (int i4 = 0; i4 < lineNum; i4++) {
            //当前行的views和高度
            List<View> viewList = mLineList.get(i4);
            //当前行高度
            Integer heightCurrent = mLineHeight.get(i4);

            //重置左上角位置
            left=getPaddingLeft();

            for (int i5 = 0; i5 < viewList.size(); i5++) {
                View view = viewList.get(i5);
                MarginLayoutParams marginLayoutParams= (MarginLayoutParams) view.getLayoutParams();
                //当前子控件的左上右下位置
                int cLeft=left+marginLayoutParams.leftMargin;
                int cTop=top+marginLayoutParams.topMargin;
                int cRight=cLeft+view.getMeasuredWidth();
                int cBottom=cTop+view.getMeasuredHeight();
                //进行子view的布局
                view.layout(cLeft,cTop,cRight,cBottom);
                left+=view.getMeasuredWidth()+marginLayoutParams.rightMargin+marginLayoutParams.leftMargin;
            }
            top+=heightCurrent;
        }

    }

}
