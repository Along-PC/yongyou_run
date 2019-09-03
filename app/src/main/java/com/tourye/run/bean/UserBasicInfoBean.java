package com.tourye.run.bean;
/**
 *
 * @ClassName:   UserBasicInfoBean
 *
 * @Author:   along
 *
 * @Description:    用户基本信息实体
 *
 * @CreateDate:   2019/4/3 9:47 AM
 *
 */
public class UserBasicInfoBean {

    /**
     * status : 0
     * timestamp : 1554255994
     * data : {"id":10,"serial_number":"10000A","nickname":"阳光","avatar":"http://static.ro-test.xorout.com/user_avatar/10000A/2019-03-23/153708.jpg?auth_key=1554259594-3bcfb3a76d1349e582eb8ab9176bc4f2-0-8534a65fa0be6ea5ff7f428221df6ca8","mobile_bound":true,"real_name_authenticated":false,"subscribe":false}
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
         * id : 10
         * serial_number : 10000A
         * nickname : 阳光
         * avatar : http://static.ro-test.xorout.com/user_avatar/10000A/2019-03-23/153708.jpg?auth_key=1554259594-3bcfb3a76d1349e582eb8ab9176bc4f2-0-8534a65fa0be6ea5ff7f428221df6ca8
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
