package com.tourye.run.bean;

/**
 * Created by longlongren on 2018/11/1.
 * <p>
 * introduce:发表回复的实体
 */

public class CreateReplyBean {

    /**
     * status : 0
     * timestamp : 1541065233
     * data : 105
     */

    private int status;
    private int timestamp;
    private int data;//该回复的id----删除时需要

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
