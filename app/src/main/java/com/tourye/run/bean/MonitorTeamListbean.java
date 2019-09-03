package com.tourye.run.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @ClassName:   MonitorTeamListbean
 *
 * @Author:   along
 *
 * @Description:队长所有的队伍列表实体
 *
 * @CreateDate:   2019/4/16 4:50 PM
 *
 */
public class MonitorTeamListbean implements Serializable{

    /**
     * status : 0
     * timestamp : 1555404540
     * data : [{"id":1,"name":"xxx","logo":"xxx","monitor":"xxx","city":"xxx","distance":"xxx","member_count":16,"today_sign_in_count":68}]
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

    public static class DataBean implements Serializable {
        /**
         * id : 1
         * name : xxx
         * logo : xxx
         * monitor : xxx
         * city : xxx
         * distance : xxx
         * member_count : 16
         * today_sign_in_count : 68
         */

        private int id;
        private String name;
        private String logo;
        private String monitor;
        private String city;
        private String distance;
        private int member_count;
        private int today_sign_in_count;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getMonitor() {
            return monitor;
        }

        public void setMonitor(String monitor) {
            this.monitor = monitor;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public int getMember_count() {
            return member_count;
        }

        public void setMember_count(int member_count) {
            this.member_count = member_count;
        }

        public int getToday_sign_in_count() {
            return today_sign_in_count;
        }

        public void setToday_sign_in_count(int today_sign_in_count) {
            this.today_sign_in_count = today_sign_in_count;
        }
    }
}
