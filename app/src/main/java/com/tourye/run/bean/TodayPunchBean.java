package com.tourye.run.bean;
/**
 *
 * @ClassName:   TodayPunchBean
 *
 * @Author:   along
 *
 * @Description:    今日打卡信息实体
 *
 * @CreateDate:   2019/5/22 10:42 AM
 *
 */
public class TodayPunchBean {

    /**
     * status : 0
     * timestamp : 1558492933
     * data : {"today_sign_in":{"image":"xxxxxx","image_url":"xxx","time":"xx:xx","distance":"xxxx","cross_country_run":false,"status":"normal"},"total_days":"2"}
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
         * today_sign_in : {"image":"xxxxxx","image_url":"xxx","time":"xx:xx","distance":"xxxx","cross_country_run":false,"status":"normal"}
         * total_days : 2
         */

        private TodaySignInBean today_sign_in;
        private String total_days;

        public TodaySignInBean getToday_sign_in() {
            return today_sign_in;
        }

        public void setToday_sign_in(TodaySignInBean today_sign_in) {
            this.today_sign_in = today_sign_in;
        }

        public String getTotal_days() {
            return total_days;
        }

        public void setTotal_days(String total_days) {
            this.total_days = total_days;
        }

        public static class TodaySignInBean {
            /**
             * image : xxxxxx
             * image_url : xxx
             * time : xx:xx
             * distance : xxxx
             * cross_country_run : false
             * status : normal
             */

            private String image;
            private String image_url;
            private String time;
            private String distance;
            private int cross_country_run;
            private String status;

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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public int isCross_country_run() {
                return cross_country_run;
            }

            public void setCross_country_run(int cross_country_run) {
                this.cross_country_run = cross_country_run;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
