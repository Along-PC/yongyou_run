package com.tourye.run.bean;

import java.io.Serializable;

/**
 *
 * @ClassName:   CheckPunchBean
 *
 * @Author:   along
 *
 * @Description:    获取审核打卡信息实体
 *
 * @CreateDate:   2019/4/8 2:04 PM
 *
 */
public class CheckPunchBean implements Serializable {

    /**
     * status : 0
     * timestamp : 1554703384
     * data : {"id":129,"nickname":"黄明黄明黄明黄明黄明黄明黄明黄明黄明黄明","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-23/2-172913-7CqYwu.jpg%21320w?auth_key=1554706984-961350c6791f4a48ac5f3a0ff7205ae6-0-19dd62a7c6e8b9302e3997a324c17a5d","date":"2019-04-08","image":"http://static.ro-test.xorout.com/sign_in/2019-04-08/2-134939-pC1r9x.jpg%21750w?auth_key=1554706984-98575c4d65774d1b8d37e8b086af581b-0-4fdbcb714599ed46758e39df254f2a2c","distance":"10","time":62,"cross_country_run":0}
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

    public static class DataBean implements Serializable{
        /**
         * id : 129
         * nickname : 黄明黄明黄明黄明黄明黄明黄明黄明黄明黄明
         * avatar : http://static.ro-test.xorout.com/user_avatar/2019-03-23/2-172913-7CqYwu.jpg%21320w?auth_key=1554706984-961350c6791f4a48ac5f3a0ff7205ae6-0-19dd62a7c6e8b9302e3997a324c17a5d
         * date : 2019-04-08
         * image : http://static.ro-test.xorout.com/sign_in/2019-04-08/2-134939-pC1r9x.jpg%21750w?auth_key=1554706984-98575c4d65774d1b8d37e8b086af581b-0-4fdbcb714599ed46758e39df254f2a2c
         * distance : 10
         * time : 62
         * cross_country_run : 0
         */

        private int id;
        private String nickname;
        private String avatar;
        private String date;
        private String image;
        private String distance;
        private int time;
        private int cross_country_run;
        private int sign_in_rejected_count;//审核打卡被拒绝次数
        private boolean is_success;//审核结果
        private boolean has_operate;//是否操作过

        public boolean isHas_operate() {
            return has_operate;
        }

        public void setHas_operate(boolean has_operate) {
            this.has_operate = has_operate;
        }

        public int getSign_in_rejected_count() {
            return sign_in_rejected_count;
        }

        public void setSign_in_rejected_count(int sign_in_rejected_count) {
            this.sign_in_rejected_count = sign_in_rejected_count;
        }

        public boolean isIs_success() {
            return is_success;
        }

        public void setIs_success(boolean is_success) {
            this.is_success = is_success;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public int getCross_country_run() {
            return cross_country_run;
        }

        public void setCross_country_run(int cross_country_run) {
            this.cross_country_run = cross_country_run;
        }
    }
}
