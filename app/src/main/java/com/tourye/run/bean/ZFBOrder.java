package com.tourye.run.bean;
/**
 *
 * @ClassName:   ZFBOrder
 *
 * @Author:   along
 *
 * @Description:    支付宝订单实体
 *
 * @CreateDate:   2019/4/15 4:09 PM
 *
 */
public class ZFBOrder {


    /**
     * status : 0
     * timestamp : 1555310023
     * data : {"id":"xxx","params":""}
     */

    private int status;
    private int timestamp;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : xxx
         * params :
         */

        private String id;
        private String params;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParams() {
            return params;
        }

        public void setParams(String params) {
            this.params = params;
        }
    }
}
