package com.tourye.run.bean;

import java.util.List;

/**
 *
 * @ClassName:   BattleMemberBean
 *
 * @Author:   along
 *
 * @Description:战队队员信息
 *
 * @CreateDate:   2019/3/18 4:10 PM
 *
 */
public class BattleMemberBean {

    /**
     * status : 0
     * timestamp : 1552896550
     * data : [{"nickname":"xxx","avatar":"xxx","already_sign_in":true}]
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
         * nickname : xxx
         * avatar : xxx
         * already_sign_in : true
         */

        private String nickname;
        private String avatar;
        private boolean already_sign_in;

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

        public boolean isAlready_sign_in() {
            return already_sign_in;
        }

        public void setAlready_sign_in(boolean already_sign_in) {
            this.already_sign_in = already_sign_in;
        }
    }
}
