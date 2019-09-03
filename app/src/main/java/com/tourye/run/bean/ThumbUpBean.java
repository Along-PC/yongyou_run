package com.tourye.run.bean;

/**
 * Created by longlongren on 2018/10/31.
 * <p>
 * introduce:点赞实体
 */

public class ThumbUpBean {

    /**
     * status : 0
     * timestamp : 1540963093
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
