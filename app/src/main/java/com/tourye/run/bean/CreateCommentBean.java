package com.tourye.run.bean;

/**
 * Created by longlongren on 2018/11/2.
 * <p>
 * introduce:创建评论实体
 */

public class CreateCommentBean {

    /**
     * status : 0
     * timestamp : 1541135687
     * data : 43
     */

    private int status;
    private int timestamp;
    private int data;//评论id

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

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
