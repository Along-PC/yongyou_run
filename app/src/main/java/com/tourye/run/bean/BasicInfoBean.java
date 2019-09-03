package com.tourye.run.bean;

/**
 *
 * @ClassName:   BasicInfoBean
 *
 * @Author:   along
 *
 * @Description:用户基本信息实体
 *
 * @CreateDate:   2019/3/15 2:10 PM
 *
 */
public class BasicInfoBean {

    /**
     * status : 0
     * timestamp : 1552630117
     * data : {"id":3,"serial_number":"100003","nickname":"黄明","avatar":"http://static.ro-test.xorout.com/user_avatar/100003/2019-03-15/112708.jpg?auth_key=1552633717-cc3fbbc5d3a94d8a9986a0a726313f5f-0-237db54dee45ca179a7373383a838ef6","mobile_bound":true,"real_name_authenticated":false,"subscribe":false}
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
         * id : 3
         * serial_number : 100003
         * nickname : 黄明
         * avatar : http://static.ro-test.xorout.com/user_avatar/100003/2019-03-15/112708.jpg?auth_key=1552633717-cc3fbbc5d3a94d8a9986a0a726313f5f-0-237db54dee45ca179a7373383a838ef6
         * mobile_bound : true
         * real_name_authenticated : false
         * subscribe : false
         */

        private int id;
        private String serial_number;
        private String nickname;
        private String avatar;
        private boolean mobile_bound;
        private boolean real_name_authenticated;
        private boolean subscribe;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSerial_number() {
            return serial_number;
        }

        public void setSerial_number(String serial_number) {
            this.serial_number = serial_number;
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

        public boolean isMobile_bound() {
            return mobile_bound;
        }

        public void setMobile_bound(boolean mobile_bound) {
            this.mobile_bound = mobile_bound;
        }

        public boolean isReal_name_authenticated() {
            return real_name_authenticated;
        }

        public void setReal_name_authenticated(boolean real_name_authenticated) {
            this.real_name_authenticated = real_name_authenticated;
        }

        public boolean isSubscribe() {
            return subscribe;
        }

        public void setSubscribe(boolean subscribe) {
            this.subscribe = subscribe;
        }
    }
}
