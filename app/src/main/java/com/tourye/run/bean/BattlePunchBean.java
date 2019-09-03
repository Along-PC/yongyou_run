package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   BattlePunchBean
 *
 * @Author:   along
 *
 * @Description:  战队打卡情况实体
 *
 * @CreateDate:   2019/3/20 10:02 AM
 *
 */
public class BattlePunchBean {

    /**
     * status : 0
     * timestamp : 1553047273
     * data : [{"avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-18/3-161949-KuSMTu.jpg%21200h?auth_key=1553050873-87ac558ead504177bd2210b149ce670f-0-fd15cbf9e3b876b50113568785189cee","nickname":"黄明","has_sign_in":false}]
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
         * avatar : http://static.ro-test.xorout.com/user_avatar/2019-03-18/3-161949-KuSMTu.jpg%21200h?auth_key=1553050873-87ac558ead504177bd2210b149ce670f-0-fd15cbf9e3b876b50113568785189cee
         * nickname : 黄明
         * has_sign_in : false
         */

        private String avatar;
        private String nickname;
        private boolean has_sign_in;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public boolean isHas_sign_in() {
            return has_sign_in;
        }

        public void setHas_sign_in(boolean has_sign_in) {
            this.has_sign_in = has_sign_in;
        }
    }
}
