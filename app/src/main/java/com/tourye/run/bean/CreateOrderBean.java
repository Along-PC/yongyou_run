package com.tourye.run.bean;
/**
 *
 * @ClassName:   CreateOrderBean
 *
 * @Author:   along
 *
 * @Description:    创建支付订单
 *
 * @CreateDate:   2019/4/15 5:50 PM
 *
 */
public class CreateOrderBean {

    /**
     * status : 0
     * timestamp : 1555321831
     * data : 79
     */

    private int status;
    private int timestamp;
    private int data;

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
