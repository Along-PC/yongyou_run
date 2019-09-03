package com.tourye.run.bean;
/**
 *
 * @ClassName:   PunchDetailBean
 *
 * @Author:   along
 *
 * @Description:    打卡详情实体
 *
 * @CreateDate:   2019/4/16 10:34 AM
 *
 */
public class PunchDetailBean {

    /**
     * status : 0
     * timestamp : 1555381577
     * data : {"image":"1/2019-04-16/10-102504-vP0ZPA.jpg","image_url":"http://static.ro-test.xorout.com/1/2019-04-16/10-102504-vP0ZPA.jpg?auth_key=1555385177-91ee68427f2a404e812dd2868eee6a84-0-9fbc36676ae52411a643d688e53dc475","time":10,"distance":"1","cross_country_run":0,"background":"http://static.ro-test.xorout.com/sign_in_background/2019-04-16-09-57-23/d950cfcbb17140b984fff931a931e23c.png%21750w1334h?auth_key=1555385177-0498967d7fd14492a94bf905793986cf-0-ee83845a819da530225e684fe5bc0189","activity_total_days":null,"total_days":5,"total_distance":"75.00"}
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
         * image : 1/2019-04-16/10-102504-vP0ZPA.jpg
         * image_url : http://static.ro-test.xorout.com/1/2019-04-16/10-102504-vP0ZPA.jpg?auth_key=1555385177-91ee68427f2a404e812dd2868eee6a84-0-9fbc36676ae52411a643d688e53dc475
         * time : 10
         * distance : 1
         * cross_country_run : 0
         * background : http://static.ro-test.xorout.com/sign_in_background/2019-04-16-09-57-23/d950cfcbb17140b984fff931a931e23c.png%21750w1334h?auth_key=1555385177-0498967d7fd14492a94bf905793986cf-0-ee83845a819da530225e684fe5bc0189
         * activity_total_days : null
         * total_days : 5
         * total_distance : 75.00
         */

        private String image;
        private String image_url;
        private int time;
        private String distance;
        private int cross_country_run;
        private String background;
        private Object activity_total_days;
        private int total_days;
        private String total_distance;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public int getCross_country_run() {
            return cross_country_run;
        }

        public void setCross_country_run(int cross_country_run) {
            this.cross_country_run = cross_country_run;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public Object getActivity_total_days() {
            return activity_total_days;
        }

        public void setActivity_total_days(Object activity_total_days) {
            this.activity_total_days = activity_total_days;
        }

        public int getTotal_days() {
            return total_days;
        }

        public void setTotal_days(int total_days) {
            this.total_days = total_days;
        }

        public String getTotal_distance() {
            return total_distance;
        }

        public void setTotal_distance(String total_distance) {
            this.total_distance = total_distance;
        }
    }
}
