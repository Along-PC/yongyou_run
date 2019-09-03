package com.tourye.run.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.tourye.run.utils.DensityUtils;

/**
 *
 * @ClassName:   TrapezoidView
 *
 * @Author:   along
 *
 * @Description: 梯形控件
 *
 * @CreateDate:   2019/3/22 10:29 AM
 *
 */
public class TrapezoidView extends View {

    private Paint mPaintBack;//背景画笔
    private Paint mPaintLine;//阴影画笔
    private int measureWidth;
    private int measureHeight;

    public TrapezoidView(Context context) {
        super(context);
    }

    public TrapezoidView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public void initPaint(){
        mPaintBack = new Paint();
        mPaintBack.setColor(Color.WHITE);
        mPaintBack.setAntiAlias(true);
        mPaintBack.setStyle(Paint.Style.FILL);
        mPaintBack.setShadowLayer(20f,1f,10f,Color.GRAY);

        mPaintLine = new Paint();
        mPaintLine.setAntiAlias(true);
        mPaintLine.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintLine.setStrokeWidth(10);
        mPaintLine.setColor(Color.WHITE);
        mPaintLine.setTextSize(DensityUtils.sp2px(20));
        mPaintLine.setShadowLayer(20f,10f,20f,Color.parseColor("#aaaaaa"));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取到的绘图区域宽高
        measureWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        measureHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

//        this.setLayerType(View.LAYER_TYPE_SOFTWARE,mPaintLine);

        Path path = new Path();
        path.moveTo(0,0);
        path.lineTo(measureWidth/5*2,0);
        path.lineTo(measureWidth/5*2+40,measureHeight/3*2);
        path.lineTo(measureWidth/5*3-40,measureHeight/3*2);
        path.lineTo(measureWidth/5*3,0);
        path.lineTo(measureWidth,0);
        path.lineTo(measureWidth,measureHeight);
        path.lineTo(0,measureHeight);
        path.close();
        canvas.drawPath(path,mPaintBack);

        Path path1 = new Path();
        path1.moveTo(0,-10);
        path1.lineTo(measureWidth/5*2,-10);
//        canvas.drawPath(path1,mPaintLine);

        Path path3 = new Path();
        path3.moveTo(measureWidth/5*3,-10);
        path3.lineTo(measureWidth,-10);
//        canvas.drawPath(path3,mPaintLine);

        Path path2 = new Path();
        path2.moveTo(measureWidth/5*2+40,measureHeight/3*2+10);
        path2.lineTo(measureWidth/5*3-40,measureHeight/3*2+10);
//        canvas.drawPath(path2,mPaintLine);

    }

}
