package com.tourye.run.bean;
/**
 *
 * @ClassName:   StatisticsBean
 *
 * @Author:   along
 *
 * @Description:    个人打卡距离统计实体
 *
 * @CreateDate:   2019/4/3 9:44 AM
 *
 */
public class StatisticsBean {

    /**
     * status : 0
     * timestamp : 1554255769
     * data : {"total_distance":"0.00","total_days":0,"total_time":0}
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
         * total_distance : 0.00
         * total_days : 0
         * total_time : 0
         */

        private String total_distance;
        private int total_days;
        private int total_time;

        public String getTotal_distance() {
            return total_distance;
        }

        public void setTotal_distance(String total_distance) {
            this.total_distance = total_distance;
        }

        public int getTotal_days() {
            return total_days;
        }

        public void setTotal_days(int total_days) {
            this.total_days = total_days;
        }

        public int getTotal_time() {
            return total_time;
        }

        public void setTotal_time(int total_time) {
            this.total_time = total_time;
        }
    }
}
