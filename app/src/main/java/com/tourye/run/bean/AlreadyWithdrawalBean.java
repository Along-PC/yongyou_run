package com.tourye.run.bean;

import java.util.List;

/**
 *
 * @ClassName:   AlreadyWithdrawalBean
 *
 * @Author:   along
 *
 * @Description:    已经发起的提现实体
 *
 * @CreateDate:   2019/4/23 3:46 PM
 *
 */
public class AlreadyWithdrawalBean {


    /**
     * status : 0
     * timestamp : 1556005499
     * data : [{"account":"xxx","status":"xxx","time":"2019-01-01 12 :34:56","count":10000}]
     */

    private int status;
    private int timestamp;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * account : xxx
         * status : xxx
         * time : 2019-01-01 12 :34:56
         * count : 10000
         */

        private String account;
        private String status;
        private String time;
        private int count;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
