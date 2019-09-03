package com.tourye.run.bean;
/**
 *
 * @ClassName:   CommonStatusBean
 *
 * @Author:   along
 *
 * @Description:    通用data为布尔值实体
 *
 * @CreateDate:   2019/4/17 3:18 PM
 *
 */
public class CommonStatusBean {

    /**
     * status : 0
     * timestamp : 1555485433
     * data : true
     */

    private int status;
    private int timestamp;
    private boolean data;

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

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
