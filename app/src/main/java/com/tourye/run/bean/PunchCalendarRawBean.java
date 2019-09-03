package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   PunchCalendarRawBean
 *
 * @Author:   along
 *
 * @Description:    打卡日历服务器返回实体
 *
 * @CreateDate:   2019/4/24 11:07 AM
 *
 */
public class PunchCalendarRawBean {

    /**
     * status : 0
     * timestamp : 1556073240
     * data : {"nickname":"卢森煌","avatar":"http://static.ro-test.xorout.com/user_avatar/100001/2019-03-23/151825.jpg?auth_key=1556076840-67c2b8a6f7c04d4aa6ff617e5019f043-0-29e61ec8604c16abab83f247edc364ab","total_days":"0","total_distance":"0.00","future_day":116,"need_day":100,"data":[{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-01"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-02"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-03"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-04"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-05"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-06"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-07"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-08"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-09"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-10"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-11"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-12"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-13"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-14"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-15"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-16"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-17"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-18"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-19"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-20"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-21"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-22"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-23"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-24"}]}
     */

    private int status;
    private int timestamp;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * nickname : 卢森煌
         * avatar : http://static.ro-test.xorout.com/user_avatar/100001/2019-03-23/151825.jpg?auth_key=1556076840-67c2b8a6f7c04d4aa6ff617e5019f043-0-29e61ec8604c16abab83f247edc364ab
         * total_days : 0
         * total_distance : 0.00
         * future_day : 116
         * need_day : 100
         * data : [{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-01"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-02"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-03"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-04"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-05"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-06"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-07"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-08"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-09"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-10"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-11"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-12"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-13"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-14"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-15"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-16"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-17"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-18"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-19"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-20"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-21"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-22"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-23"},{"status":"no_sign_in","mobile":"18810541362","date":"2019-4-24"}]
         */

        private String nickname;
        private String avatar;
        private String total_days;
        private String total_distance;
        private int future_day;
        private int need_day;
        private List<DataBean> data;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTotal_days() {
            return total_days;
        }

        public void setTotal_days(String total_days) {
            this.total_days = total_days;
        }

        public String getTotal_distance() {
            return total_distance;
        }

        public void setTotal_distance(String total_distance) {
            this.total_distance = total_distance;
        }

        public int getFuture_day() {
            return future_day;
        }

        public void setFuture_day(int future_day) {
            this.future_day = future_day;
        }

        public int getNeed_day() {
            return need_day;
        }

        public void setNeed_day(int need_day) {
            this.need_day = need_day;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * status : no_sign_in
             * mobile : 18810541362
             * date : 2019-4-01
             */

            private String status;
            private String mobile;
            private String date;
            private String distance;

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }
}
