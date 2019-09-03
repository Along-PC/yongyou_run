package com.tourye.run.bean;
/**
 *
 * @ClassName:   CreateBattleBean
 *
 * @Author:   along
 *
 * @Description:    创建战队实体
 *
 * @CreateDate:   2019/4/15 11:45 AM
 *
 */
public class CreateBattleBean {

    /**
     * status : 0
     * timestamp : 1555299855
     * data : 25
     */

    private int status;
    private int timestamp;
    private int data;
    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
