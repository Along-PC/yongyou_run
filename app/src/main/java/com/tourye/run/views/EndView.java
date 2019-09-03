package com.tourye.run.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.tourye.run.utils.DensityUtils;

/**
 * Created by longlongren on 2019/3/12.
 * <p>
 * introduce:跑步结束按钮
 */
public class EndView extends View {

    private Context mContext;
    private GestureDetector mGestureDetector;

    private Paint mPaintText;//文字画笔
    private Paint mPaintBack;//背景画笔
    private Paint mPaintAnimation;//动画画笔
    private Paint mPaintScale;//刻度画笔
    private Paint mCurrentScale;//当前刻度画笔
    private Paint mRadianPaint;//当前刻度弧度画笔
    private int widthCircle = 20;//圆环宽度
    private int widthEdge = 20;//边缘宽度
    private float measuredWidth;
    private float measuredHeight;
    private float radius;
    private boolean canAnimation;//是否播放绘制圆弧界面
    private float degreeCurrent;
    private static final String TAG = "EndView";

    private OnEndProxy mOnEndProxy;

    private long downTime;//上次点击按下的时间

    public EndView(Context context) {
        super(context);

    }

    public void setOnEndProxy(OnEndProxy onEndProxy) {
        mOnEndProxy = onEndProxy;
    }

    public EndView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mGestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.e(TAG, "onSingleTapUp()触发 called with: e = [" + e + "]");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                canAnimation = true;
                startAnim();
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return true;
            }
        });
    }


    public void initPaint() {
        mPaintText = new Paint();
        mPaintText.setColor(Color.WHITE);
        mPaintText.setAntiAlias(true);
        mPaintText.setStyle(Paint.Style.FILL);

        //获取到的绘图区域宽高
        measuredWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        measuredHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        if (measuredHeight > measuredWidth) {
            radius = measuredWidth / 2;
        } else {
            radius = measuredHeight / 2;
        }

        int[] SWEEP_GRADIENT_COLORS = new int[]{Color.parseColor("#FFFF7C47"), Color.parseColor("#FFFF1D1D"), Color.parseColor("#FFFF7C47")};
        SweepGradient sweepGradient = new SweepGradient(measuredWidth / 2, measuredHeight / 2, SWEEP_GRADIENT_COLORS, null);
        mPaintBack = new Paint();
        mPaintBack.setColor(Color.WHITE);
        mPaintBack.setAntiAlias(true);
        mPaintBack.setShader(sweepGradient);
        mPaintBack.setStyle(Paint.Style.FILL);

        mPaintAnimation = new Paint();
        mPaintAnimation.setColor(Color.WHITE);
        mPaintAnimation.setAntiAlias(true);

        mRadianPaint = new Paint();
        mRadianPaint.setAntiAlias(true);
        mRadianPaint.setStrokeWidth(widthCircle);
        mRadianPaint.setStyle(Paint.Style.STROKE);
        int[] colors=new int[]{Color.parseColor("#00ffFF"),Color.parseColor("#00ff00"),Color.parseColor("#00ffFF")};
        SweepGradient sweepCircle = new SweepGradient(radius, radius, colors, null);
        //设置渐变色
        mRadianPaint.setShader(sweepCircle);

        mPaintScale = new Paint();
        mPaintScale.setColor(Color.parseColor("#FFD8D8D8"));
        mPaintScale.setAntiAlias(true);
        mPaintScale.setStrokeWidth(DensityUtils.sp2px(mContext, 3));

        mCurrentScale = new Paint();
        mCurrentScale.setColor(Color.WHITE);
        mCurrentScale.setAntiAlias(true);
        mCurrentScale.setStrokeWidth(DensityUtils.sp2px(mContext, 3));

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downTime=System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                if (System.currentTimeMillis()-downTime<1000) {
                    mOnEndProxy.onCancel();
                }
                degreeCurrent = 0;
                canAnimation = false;
                invalidate();
                break;
        }
        return mGestureDetector.onTouchEvent(event);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            //绘制背景
            canvas.drawCircle(measuredWidth / 2, measuredHeight / 2, radius, mPaintBack);
            //绘制矩形和文字
            int left = (int) (radius - DensityUtils.dp2px(mContext, 11));
            int top = (int) (radius - DensityUtils.dp2px(mContext, 22));
            int right = (int) (radius + DensityUtils.dp2px(mContext, 11));
            int bottom = (int) radius;
            Rect rect = new Rect(left, top, right, bottom);
            canvas.drawRect(rect, mPaintText);
            mPaintText.setTextSize(DensityUtils.sp2px(mContext, 14));
            float[] textSize = getTextSize("结束", mPaintText);
            canvas.drawText("结束", radius - textSize[0] / 2, radius + textSize[1] + DensityUtils.dp2px(mContext, 10), mPaintText);

            RectF rectF = new RectF( widthCircle / 2, widthCircle / 2,
                    measuredWidth - widthCircle / 2, measuredHeight  - widthCircle / 2);
            canvas.drawArc(rectF, -90, degreeCurrent, false, mRadianPaint);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startAnim() {
        //添加属性动画
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 360);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (canAnimation) {
                    Float animatedValue = (Float) valueAnimator.getAnimatedValue();
                    degreeCurrent = animatedValue.floatValue();
                    if (degreeCurrent>=360f) {
                        canAnimation = false;
                        if (mOnEndProxy != null) {
                            mOnEndProxy.onEnd();
                        }
                        degreeCurrent=0;
                    }
                    invalidate();
                }else{
                    valueAnimator.cancel();
                }
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }
        });
        valueAnimator.setTarget(degreeCurrent);
        valueAnimator.start();
    }

    /**
     * 获取字符串宽高
     *
     * @param paint
     * @return
     */
    public float[] getTextSize(String text, Paint paint) {
        float[] floats = new float[2];
        Rect rect = new Rect();
        String s = text;
        paint.getTextBounds(s, 0, s.length(), rect);
        floats[0] = Math.max(rect.width(), floats[0]);
        floats[1] = Math.max(rect.height(), floats[1]);
        return floats;
    }

    public interface OnEndProxy {
        public void onEnd();

        public void onCancel();
    }

}
