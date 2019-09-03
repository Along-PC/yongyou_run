package com.tourye.run.bean;

import java.util.List;

/**
 *
 * @ClassName:   SignupInfoBean
 *
 * @Author:   along
 *
 * @Description:    历届报名百日跑情况实体
 *
 * @CreateDate:   2019/5/22 10:16 AM
 *
 */
public class SignupInfoBean {

    /**
     * status : 0
     * timestamp : 1558491302
     * data : [{"id":1,"name":"第十届百日跑","start_date":"2019-05-16","finish_date":"2019-05-31","level":"3KM","total_days":"2"}]
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
         * id : 1
         * name : 第十届百日跑
         * start_date : 2019-05-16
         * finish_date : 2019-05-31
         * level : 3KM
         * total_days : 2
         */

        private String id;
        private String name;
        private String start_date;
        private String finish_date;
        private String level;
        private String total_days;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getFinish_date() {
            return finish_date;
        }

        public void setFinish_date(String finish_date) {
            this.finish_date = finish_date;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getTotal_days() {
            return total_days;
        }

        public void setTotal_days(String total_days) {
            this.total_days = total_days;
        }
    }
}
