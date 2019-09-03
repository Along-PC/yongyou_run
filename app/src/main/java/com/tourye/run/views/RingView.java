package com.tourye.run.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.tourye.run.R;

/**
 * Created by along
 * 创建时间 2017/9/26 0026.
 */

public class RingView extends View {

    private Paint mPaintRing;//圆环画笔
    private Paint mPaintBack;//圆环画笔
    private int measuredHeight;
    private int measuredWidth;
    private float radius;
    private float degreeLast;
    private float degreeCurrent;

    private int widthEdge=20;//边缘宽度
    private int widthCircle=20;//圆环宽度

    private Context mContext;

    public RingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        initPaint();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredHeight = getMeasuredHeight()-getPaddingTop()-getPaddingBottom();
        measuredWidth = getMeasuredWidth()-getPaddingLeft()-getPaddingRight();

        radius = measuredHeight > measuredWidth ? measuredWidth /2: measuredHeight /2;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void initPaint(){



        mPaintRing=new Paint();
        mPaintRing.setAntiAlias(true);
        mPaintRing.setStrokeWidth(widthCircle);
        mPaintRing.setStyle(Paint.Style.STROKE);
        int[] colors=new int[]{Color.parseColor("#FFFFB119"),Color.parseColor("#FFFF1D1D"),Color.parseColor("#FFFFB119")};
        SweepGradient sweepGradient=new SweepGradient(radius, radius,colors,null);
        //设置渐变色
        mPaintRing.setShader(sweepGradient);
        //设置画笔圆头
        mPaintRing.setStrokeCap(Paint.Cap.ROUND);

        mPaintBack=new Paint();
        mPaintBack.setAntiAlias(true);
        mPaintBack.setStrokeWidth(widthCircle);
        mPaintBack.setStyle(Paint.Style.STROKE);
        mPaintBack.setColor(Color.parseColor("#FFEFEFEF"));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
//        canvas.translate(getMeasuredWidth()/2,getMeasuredWidth()/2); //将坐标中心平移到要围绕的坐标点x,y
        canvas.rotate(-90,getMeasuredWidth()/2,getMeasuredWidth()/2);

        RectF rectF = new RectF(widthEdge+widthCircle/2, widthEdge+widthCircle/2,
                measuredWidth-widthEdge-widthCircle/2, measuredHeight-widthEdge-widthCircle/2);
        canvas.drawCircle(radius,radius,radius-widthEdge-widthCircle/2,mPaintBack);
        canvas.drawArc(rectF,degreeLast,degreeCurrent,false,mPaintRing);

    }

    public void startAnim(float radian) {
        //添加属性动画
        ValueAnimator valueAnimator= ValueAnimator.ofFloat(0f,radian);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float animatedValue = (Float) valueAnimator.getAnimatedValue();
                degreeCurrent=animatedValue.floatValue();
                invalidate();
            }
        });
        valueAnimator.setTarget(degreeCurrent);
        valueAnimator.start();
    }

}
