package com.tourye.run.bean;
/**
 *
 * @ClassName:   WaitCheckCountBean
 *
 * @Author:   along
 *
 * @Description:    等待审核卡数量实体
 *
 * @CreateDate:   2019/4/8 11:27 AM
 *
 */
public class WaitCheckCountBean {

    /**
     * status : 0
     * timestamp : 1554693802
     * data : 0
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
