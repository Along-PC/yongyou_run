package com.tourye.run.bean;
/**
 *
 * @ClassName:   ScreenPunchBean
 *
 * @Author:   along
 *
 * @Description:    截图打卡实体
 *
 * @CreateDate:   2019/4/15 2:10 PM
 *
 */
public class ScreenPunchBean {

    /**
     * status : 0
     * timestamp : 1555308599
     * data : {"id":215,"background":null,"total_days":4,"total_distance":66}
     */

    private int status;
    private int timestamp;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * id : 215
         * background : null
         * total_days : 4
         * total_distance : 66
         */

        private int id;
        private String background;
        private int total_days;
        private float total_distance;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public int getTotal_days() {
            return total_days;
        }

        public void setTotal_days(int total_days) {
            this.total_days = total_days;
        }

        public float getTotal_distance() {
            return total_distance;
        }

        public void setTotal_distance(float total_distance) {
            this.total_distance = total_distance;
        }
    }
}
