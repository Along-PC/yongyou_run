package com.tourye.run.utils;

import android.util.Log;

/**
 *
 * @ClassName:   DateCalculateUtils
 *
 * @Author:   along
 *
 * @Description:    时间差计算器
 *
 * @CreateDate:   2019/5/17 10:33 AM
 *
 */
public class DateCalculateUtils {

    public static final double DAY_UNIT=1000*60*60*24;
    public static final double HOUR_UNIT=1000*60*60;
    public static final double MINUTE_UNIT=1000*60;
    public static final double SECOND_UNIT=1000;

    public static String calculate(long start,long end){
        long difference = end - start;
        if (difference<=0) {
            return "报名已截止";
        }
        int day = (int) Math.floor(difference / DAY_UNIT);
        double hour_difference = difference - day * DAY_UNIT;
        int hour = (int) Math.floor(hour_difference / HOUR_UNIT);
        double minute_difference = hour_difference - hour * HOUR_UNIT;
        int minute = (int) Math.floor(minute_difference / MINUTE_UNIT);
        double second_difference = minute_difference - minute * MINUTE_UNIT;
        int second = (int) Math.floor(second_difference / SECOND_UNIT);
        String hour_str = String.format("%0" + 2 + "d", hour);
        String minute_str = String.format("%0" + 2 + "d", minute);
        String second_str = String.format("%0" + 2 + "d", second);
        return day+"天"+hour_str+":"+minute_str+":"+second_str+"报名截止";
    }

    public static int[] calculateCutdown(long start,long end){
        long difference = end - start;
        if (difference<=0) {
            return null;
        }
        int day = (int) Math.floor(difference / DAY_UNIT);
        double hour_difference = difference - day * DAY_UNIT;
        int hour = (int) Math.floor(hour_difference / HOUR_UNIT);
        double minute_difference = hour_difference - hour * HOUR_UNIT;
        int minute = (int) Math.floor(minute_difference / MINUTE_UNIT);
        double second_difference = minute_difference - minute * MINUTE_UNIT;
        int second = (int) Math.floor(second_difference / SECOND_UNIT);
        return new int[]{day,hour,minute,second};
    }


}
