package com.tourye.run.bean;

import java.util.Date;

public class PunchCalendarBean {

    /**
     * distance : 5
     * status : rejected
     * mobile : 13110093833
     * dateStr : 2019-04-15
     */

    private String distance;
    private String status="no_sign_in";
    private String mobile;
    private String dateStr;
    private String lastStatus="";
    private String nextStatus="";
    private String day_index;//日历号
    private int tag;//用来标识在一周的第几天
    private Date mDate;
    private boolean isShowPoint;
    private boolean isEmptyBean;//是否是空白日期
    private boolean isAfterDay;//日期是否大于今天

    public boolean isEmptyBean() {
        return isEmptyBean;
    }

    public void setEmptyBean(boolean emptyBean) {
        isEmptyBean = emptyBean;
    }

    public boolean isAfterDay() {
        return isAfterDay;
    }

    public void setAfterDay(boolean afterDay) {
        isAfterDay = afterDay;
    }

    public boolean isShowPoint() {
        return isShowPoint;
    }

    public void setShowPoint(boolean showPoint) {
        isShowPoint = showPoint;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getDay_index() {
        return day_index;
    }

    public void setDay_index(String day_index) {
        this.day_index = day_index;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    public String getNextStatus() {
        return nextStatus;
    }

    public void setNextStatus(String nextStatus) {
        this.nextStatus = nextStatus;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }
}
