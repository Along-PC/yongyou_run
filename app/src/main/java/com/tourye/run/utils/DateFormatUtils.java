package com.tourye.run.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by longlongren on 2018/10/31.
 * <p>
 * introduce:日期格式化工具
 */

public class DateFormatUtils {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdf_point = new SimpleDateFormat("yyyy.MM.dd");
    private static SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");

    public static long second_unit=1000;
    public static long minute_unit=second_unit*60;
    public static long hour_unit=minute_unit*60;

    public static String format(String text) {
        try {
            Date parse = simpleDateFormat.parse(text);
            String format = sdf.format(parse);
            return format;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatDate(Date date) {
        String format = sdf.format(date);
        return format;
    }

    public static String formatDateInPoint(Date date) {
        String format = sdf_point.format(date);
        return format;
    }

    public static Date getDate(String temp){
        try {
            Date parse = sdf.parse(temp);
            return parse;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int compareDate(String date,String otherDate){
        Date parse_date ;
        Date parse_other ;
        try {
            parse_date = sdf.parse(date);
            parse_other = sdf.parse(date);
            return parse_date.compareTo(parse_other);
        } catch (ParseException e) {
            e.printStackTrace();
            return 998;
        }
    }

    /**
     * 格式化时间戳为00：00：00样式
     * @param timestamp
     * @return
     */
    public static String formatTimestamp(long timestamp){

        long hour = timestamp/hour_unit;
        long minute = (timestamp-hour*hour_unit)/minute_unit;
        long second = (timestamp-hour*hour_unit-minute*minute_unit)/second_unit;

        //格式化时间
        String hour_str = String.format("%0"+2+"d", hour);
        String minute_str = String.format("%0"+2+"d", minute);
        String second_str = String.format("%0"+2+"d", second);

        return hour_str+":"+minute_str+":"+second_str;
    }

    /**
     * 比较目标日期和当前日期的先后
     * @param date
     * @return
     */
    public static boolean beforeCurrentTime(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date goalDate;
        try {
            goalDate = simpleDateFormat.parse(date);
            Date currentDate = Calendar.getInstance().getTime();
            return goalDate.before(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 比较目标日期和当前日期的先后
     * @param date
     * @return
     */
    public static boolean afterCurrentTime(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date goalDate;
        try {
            goalDate = simpleDateFormat.parse(date);
            Date currentDate = Calendar.getInstance().getTime();
            return goalDate.after(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

}
