package com.tourye.run.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.necer.calendar.ICalendar;
import com.necer.painter.CalendarPainter;
import com.necer.utils.CalendarUtil;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName:   PunchCalendarPainter
 *
 * @Author:   along
 *
 * @Description:    打卡日历绘制
 *
 * @CreateDate:   2019/7/11 4:39 PM
 *
 */
public class PunchCalendarPainter implements CalendarPainter {


    protected Paint mTextPaint;
    protected Paint mBgPaint;
    protected Paint mPassPaint;
    protected Paint mNoPassPaint;
    private int mCircleRadius;
    private Context mContext;
    private ICalendar mICalendar;

    protected List<LocalDate> mNoPassDays;
    protected List<LocalDate> mPassDays;

    public PunchCalendarPainter(Context context, ICalendar iCalendar) {

        mContext = context;
        mICalendar = iCalendar;
        mTextPaint = getPaint();
        mBgPaint = getPaint();
        mPassPaint = getPaint();
        mNoPassPaint = getPaint();

        mBgPaint.setColor(Color.parseColor("#FFFF1D1D"));
        mPassPaint.setColor(Color.parseColor("#164A90E2"));
        mNoPassPaint.setColor(Color.parseColor("#16FF250C"));
        mCircleRadius = (int) CalendarUtil.dp2px(context, 18);

        mNoPassDays = new ArrayList<>();
        mPassDays = new ArrayList<>();

    }

    private Paint getPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        return paint;
    }


    @Override
    public void onDrawToday(Canvas canvas, RectF rectF, LocalDate localDate, List<LocalDate> selectedDateList) {
        drawSelectBg(canvas, rectF, selectedDateList.contains(localDate), true);
        drawSolar(canvas, rectF, localDate, selectedDateList.contains(localDate), true);
        drawNoPassdays(canvas, rectF, localDate, mNoPassDays.contains(localDate), true);
        drawPassdays(canvas, rectF, localDate, mPassDays.contains(localDate), true);
    }

    @Override
    public void onDrawCurrentMonthOrWeek(Canvas canvas, RectF rectF, LocalDate localDate, List<LocalDate> selectedDateList) {
        drawSelectBg(canvas, rectF, selectedDateList.contains(localDate), true);
        drawSolar(canvas, rectF, localDate, selectedDateList.contains(localDate), true);
        drawNoPassdays(canvas, rectF, localDate, mNoPassDays.contains(localDate), true);
        drawPassdays(canvas, rectF, localDate, mPassDays.contains(localDate), true);
    }

    @Override
    public void onDrawLastOrNextMonth(Canvas canvas, RectF rectF, LocalDate localDate, List<LocalDate> selectedDateList) {
        drawSelectBg(canvas, rectF, selectedDateList.contains(localDate), false);
        drawSolar(canvas, rectF, localDate, selectedDateList.contains(localDate), false);
        drawNoPassdays(canvas, rectF, localDate, mNoPassDays.contains(localDate), false);
        drawPassdays(canvas, rectF, localDate, mPassDays.contains(localDate), false);
    }

    @Override
    public void onDrawDisableDate(Canvas canvas, RectF rectF, LocalDate localDate) {

    }


    //绘制选中背景
    private void drawSelectBg(Canvas canvas, RectF rectF, boolean isisSelected, boolean isCurrectMonthOrWeek) {
        if (isisSelected) {
            mBgPaint.setAlpha(isCurrectMonthOrWeek ? 255 : 100);
            mBgPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(rectF.centerX(), rectF.centerY()+DensityUtils.dp2px(15), DensityUtils.dp2px(3), mBgPaint);
        }
    }

    //绘制公历
    private void drawSolar(Canvas canvas, RectF rectF, LocalDate localDate, boolean isSelected, boolean isCurrectMonthOrWeek) {
        mTextPaint.setTextSize(CalendarUtil.dp2px(mContext, 14));
//        mTextPaint.setColor(isSelected ? Color.WHITE : Color.BLACK);
        mTextPaint.setAlpha(isCurrectMonthOrWeek ? 255 : 100);
        canvas.drawText(localDate.getDayOfMonth() + "", rectF.centerX(), getBaseLineY(rectF), mTextPaint);
    }

    //公历文字的竖直中心y
    private int getSolarTextCenterY(float centerY) {
        mTextPaint.setTextSize(CalendarUtil.dp2px(mContext, 18));
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int ascent = fontMetricsInt.ascent;
        int descent = fontMetricsInt.descent;
        int textCenterY = (int) (descent / 2 + centerY + ascent / 2);//文字的中心y
        return textCenterY;
    }

    //绘制节假日
    private void drawNoPassdays(Canvas canvas, RectF rectF, LocalDate localDate, boolean isSelected, boolean isCurrectMonthOrWeek) {

        LocalDate lastLocalDate = localDate.minusDays(1);
        LocalDate nextLocalDate = localDate.plusDays(1);

        if (mNoPassDays.contains(localDate)) {
            if (mNoPassDays.contains(lastLocalDate) && mNoPassDays.contains(nextLocalDate) && CalendarUtil.isEqualsMonth(lastLocalDate, nextLocalDate)) {
                //画全整个矩形
                RectF rectF1 = new RectF(rectF.left, rectF.centerY() - mCircleRadius, rectF.right, rectF.centerY() + mCircleRadius);
                canvas.drawRect(rectF1, mNoPassPaint);
            } else if (mNoPassDays.contains(lastLocalDate) && (!mNoPassDays.contains(nextLocalDate) || !CalendarUtil.isEqualsMonth(nextLocalDate, localDate)) && CalendarUtil.isEqualsMonth(lastLocalDate, localDate)) {
                //左矩形 右圆
                RectF rectF1 = new RectF(rectF.left, rectF.centerY() - mCircleRadius, rectF.centerX(), rectF.centerY() + mCircleRadius);
                canvas.drawRect(rectF1, mNoPassPaint);
                RectF rectF2 = new RectF(rectF.centerX() - mCircleRadius, rectF.centerY() - mCircleRadius, rectF.centerX() + mCircleRadius, rectF.centerY() + mCircleRadius);
                canvas.drawArc(rectF2, -90, 180, false, mNoPassPaint);//右半圆
            } else if ((!mNoPassDays.contains(lastLocalDate) || !CalendarUtil.isEqualsMonth(lastLocalDate, localDate)) && mNoPassDays.contains(nextLocalDate) && CalendarUtil.isEqualsMonth(nextLocalDate, localDate)) {
                //右矩形 左圆
                RectF rectF1 = new RectF(rectF.centerX(), rectF.centerY() - mCircleRadius, rectF.right, rectF.centerY() + mCircleRadius);
                canvas.drawRect(rectF1, mNoPassPaint);
                RectF rectF2 = new RectF(rectF.centerX() - mCircleRadius, rectF.centerY() - mCircleRadius, rectF.centerX() + mCircleRadius, rectF.centerY() + mCircleRadius);
                canvas.drawArc(rectF2, 90, 180, false, mNoPassPaint);//右半圆
            } else {
                //圆形
                canvas.drawCircle(rectF.centerX(), rectF.centerY(), mCircleRadius, mNoPassPaint);
            }
        }
    }


    private void drawPassdays(Canvas canvas, RectF rectF, LocalDate localDate, boolean isSelected, boolean isCurrectMonthOrWeek) {

        LocalDate lastLocalDate = localDate.minusDays(1);
        LocalDate nextLocalDate = localDate.plusDays(1);

        if (mPassDays.contains(localDate)) {
            if (mPassDays.contains(lastLocalDate) && mPassDays.contains(nextLocalDate) && CalendarUtil.isEqualsMonth(lastLocalDate, nextLocalDate)) {
                //画全整个矩形
                RectF rectF1 = new RectF(rectF.left, rectF.centerY() - mCircleRadius, rectF.right, rectF.centerY() + mCircleRadius);
                canvas.drawRect(rectF1, mPassPaint);
            } else if (mPassDays.contains(lastLocalDate) && (!mPassDays.contains(nextLocalDate) || !CalendarUtil.isEqualsMonth(nextLocalDate, localDate)) && CalendarUtil.isEqualsMonth(lastLocalDate, localDate)) {
                //左矩形 右圆
                RectF rectF1 = new RectF(rectF.left, rectF.centerY() - mCircleRadius, rectF.centerX(), rectF.centerY() + mCircleRadius);
                canvas.drawRect(rectF1, mPassPaint);
                RectF rectF2 = new RectF(rectF.centerX() - mCircleRadius, rectF.centerY() - mCircleRadius, rectF.centerX() + mCircleRadius, rectF.centerY() + mCircleRadius);
                canvas.drawArc(rectF2, -90, 180, false, mPassPaint);//右半圆
            } else if ((!mPassDays.contains(lastLocalDate) || !CalendarUtil.isEqualsMonth(lastLocalDate, localDate)) && mPassDays.contains(nextLocalDate) && CalendarUtil.isEqualsMonth(nextLocalDate, localDate)) {
                //右矩形 左圆
                RectF rectF1 = new RectF(rectF.centerX(), rectF.centerY() - mCircleRadius, rectF.right, rectF.centerY() + mCircleRadius);
                canvas.drawRect(rectF1, mPassPaint);
                RectF rectF2 = new RectF(rectF.centerX() - mCircleRadius, rectF.centerY() - mCircleRadius, rectF.centerX() + mCircleRadius, rectF.centerY() + mCircleRadius);
                canvas.drawArc(rectF2, 90, 180, false, mPassPaint);//右半圆
            } else {
                //圆形
                canvas.drawCircle(rectF.centerX(), rectF.centerY(), mCircleRadius, mPassPaint);
            }
        }
    }


    //canvas.drawText的基准线
    private int getBaseLineY(RectF rectF) {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int baseLineY = (int) (rectF.centerY() - top / 2 - bottom / 2);
        return baseLineY;
    }

    public void setNoPassDays(List<LocalDate> noPassDays) {
        if (noPassDays !=null) {
            mNoPassDays.clear();
            mNoPassDays.addAll(noPassDays);
            mICalendar.notifyCalendar();
        }
    }

    public void setPassDays(List<LocalDate> passDays) {
        if (passDays !=null) {
            mPassDays = passDays;
            mICalendar.notifyCalendar();
        }
    }

    public void setPunchResult(List<LocalDate> passDays,List<LocalDate> noPassDays) {
        if (passDays !=null) {
            mPassDays = passDays;
        }
        if (noPassDays !=null) {
            mNoPassDays.clear();
            mNoPassDays.addAll(noPassDays);
        }
        mICalendar.notifyCalendar();
    }
}
