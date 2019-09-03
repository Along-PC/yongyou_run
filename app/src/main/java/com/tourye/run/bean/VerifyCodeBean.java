package com.tourye.run.bean;

/**
 * Created by longlongren on 2018/10/29.
 * <p>
 * introduce:验证码实体
 */

public class VerifyCodeBean {

    /**
     * status : 0
     * timestamp : 1540777940
     * data : null
     */

    private int status;
    private int timestamp;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
