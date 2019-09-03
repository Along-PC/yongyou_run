package com.tourye.run.bean;

import java.util.List;

/**
 *
 * @ClassName:   CalendarPunchBean
 *
 * @Author:   along
 *
 * @Description:     日历打卡记录实体
 *
 * @CreateDate:   2019/4/9 5:21 PM
 *
 */
public class CalendarPunchBean {

    /**
     * status : 0
     * timestamp : 1554798077
     * data : [{"date":"2019-04-04","total_distance":15,"data":[{"sign_in_id":109,"distance":"5","time":20,"image":"http://static.ro-test.xorout.com/sign_in/2019-04-04/10-161417-dfKhvn.jpg?auth_key=1554801677-e6fc73730519488eb72750bfd3e94a58-0-f7b4e126a6e9dec03acdf7c7f9bf82ef","sign_in_time":"2019-04-04 16:14:18"},{"sign_in_id":110,"distance":"10","time":24,"image":"http://static.ro-test.xorout.com/sign_in/2019-04-04/10-161417-dfKhvn.jpg?auth_key=1554801677-05388a87d4f548a7868c119a7841ea5e-0-8f399b72920993ad85af3ae78f7a0b8d","sign_in_time":"2019-04-04 16:40:29"}]},{"date":"2019-04-08","total_distance":8,"data":[{"sign_in_id":133,"distance":"5","time":28,"image":"http://static.ro-test.xorout.com/sign_in/2019-04-08/10-141608-78t1wm.jpg?auth_key=1554801677-3630c436015c40899d03a586a057204e-0-cf17de1fa7c934acb5a0acfb6124e486","sign_in_time":"2019-04-08 14:18:34"},{"sign_in_id":134,"distance":"3","time":19,"image":"http://static.ro-test.xorout.com/sign_in/2019-04-08/10-142057-lqAGBp.jpg?auth_key=1554801677-0811ef09b016450ca140d137cc821f65-0-1b5da0951baaba7a6654a0e6de0f045a","sign_in_time":"2019-04-08 14:20:58"}]}]
     */

    private int status;
    private int timestamp;
    private List<DataBeanX> data;

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

    public List<DataBeanX> getData() {
        return data;
    }

    public void setData(List<DataBeanX> data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * date : 2019-04-04
         * total_distance : 15
         * data : [{"sign_in_id":109,"distance":"5","time":20,"image":"http://static.ro-test.xorout.com/sign_in/2019-04-04/10-161417-dfKhvn.jpg?auth_key=1554801677-e6fc73730519488eb72750bfd3e94a58-0-f7b4e126a6e9dec03acdf7c7f9bf82ef","sign_in_time":"2019-04-04 16:14:18"},{"sign_in_id":110,"distance":"10","time":24,"image":"http://static.ro-test.xorout.com/sign_in/2019-04-04/10-161417-dfKhvn.jpg?auth_key=1554801677-05388a87d4f548a7868c119a7841ea5e-0-8f399b72920993ad85af3ae78f7a0b8d","sign_in_time":"2019-04-04 16:40:29"}]
         */

        private String date;
        private double total_distance;
        private List<DataBean> data;
        private String day_index;//日历号
        private boolean isSignin;//是否打卡了

        public String getDay_index() {
            return day_index;
        }

        public void setDay_index(String day_index) {
            this.day_index = day_index;
        }

        public boolean isSignin() {
            return isSignin;
        }

        public void setSignin(boolean signin) {
            isSignin = signin;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public double getTotal_distance() {
            return total_distance;
        }

        public void setTotal_distance(double total_distance) {
            this.total_distance = total_distance;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * sign_in_id : 109
             * distance : 5
             * time : 20
             * image : http://static.ro-test.xorout.com/sign_in/2019-04-04/10-161417-dfKhvn.jpg?auth_key=1554801677-e6fc73730519488eb72750bfd3e94a58-0-f7b4e126a6e9dec03acdf7c7f9bf82ef
             * sign_in_time : 2019-04-04 16:14:18
             */

            private int sign_in_id;
            private String distance;
            private int time;
            private String image;
            private String sign_in_time;
            private String trace_id;

            public int getSign_in_id() {
                return sign_in_id;
            }

            public void setSign_in_id(int sign_in_id) {
                this.sign_in_id = sign_in_id;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getSign_in_time() {
                return sign_in_time;
            }

            public void setSign_in_time(String sign_in_time) {
                this.sign_in_time = sign_in_time;
            }

            public String getTrace_id() {
                return trace_id;
            }

            public void setTrace_id(String trace_id) {
                this.trace_id = trace_id;
            }
        }
    }
}
