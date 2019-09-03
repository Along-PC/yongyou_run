package com.tourye.run.bean;
/**
 *
 * @ClassName:   ScreenPunchCardBean
 *
 * @Author:   along
 *
 * @Description:    截图打卡获取成就卡实体
 *
 * @CreateDate:   2019/4/15 2:37 PM
 *
 */
public class ScreenPunchCardBean {

    /**
     * status : 0
     * timestamp : 1555310023
     * data : wwwww
     */

    private int status;
    private int timestamp;
    private String data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
