package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   InfluencePeopleBean
 *
 * @Author:   along
 *
 * @Description:    影响人员实体
 *
 * @CreateDate:   2019/4/19 5:36 PM
 *
 */
public class InfluencePeopleBean {

    /**
     * status : 0
     * timestamp : 1555666540
     * data : [{"nickname":"gggg","avatar":"http://static.ro-test.xorout.com/1/2019-04-18/10-140911-yLaQSZ.jpg%21200h?auth_key=1555670140-ec9aae86b357459597ac4d99624101bb-0-469519bcbba4aaf141ed07dc265b88f1"}]
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
         * nickname : gggg
         * avatar : http://static.ro-test.xorout.com/1/2019-04-18/10-140911-yLaQSZ.jpg%21200h?auth_key=1555670140-ec9aae86b357459597ac4d99624101bb-0-469519bcbba4aaf141ed07dc265b88f1
         */

        private String nickname;
        private String avatar;
        private String mobile;// 只有joined为true时才会返回mobile

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
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
    }
}
