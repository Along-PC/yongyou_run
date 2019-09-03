package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   TeamMemberPunchBean
 *
 * @Author:   along
 *
 * @Description:    队员打卡情况实体
 *
 * @CreateDate:   2019/4/23 9:39 AM
 *
 */
public class TeamMemberPunchBean {

    /**
     * status : 0
     * timestamp : 1555983492
     * data : [{"user_id":6,"nickname":"小小","total_days":"2","avatar":"http://static.ro-test.xorout.com/user_avatar/100006/2019-03-23/152230.jpg?auth_key=1555987092-2e74a896da4843d1957296237c6c8304-0-186ef6a0f40eb86dc0bd5819bf337dfc","status":"accepted","distance":4},{"user_id":15,"nickname":"Innocence","total_days":"1","avatar":"http://static.ro-test.xorout.com/user_avatar/10000F/2019-03-23/170706.jpg?auth_key=1555987092-0718797fe30e43898c34b03744c5bc0f-0-6e669ea9634369d8cd228e536d80de1a","status":"no_sign_in","distance":"0"},{"user_id":36,"nickname":"晨晨","total_days":"2","avatar":"http://static.ro-test.xorout.com/user_avatar/100010/2019-04-01/133841.jpg?auth_key=1555987092-ee7d976713ea4b6ab08b68af06916e94-0-cf6ec10cefa960b9e80a1913bb760f60","status":"no_sign_in","distance":"0"}]
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
         * user_id : 6
         * nickname : 小小
         * total_days : 2
         * avatar : http://static.ro-test.xorout.com/user_avatar/100006/2019-03-23/152230.jpg?auth_key=1555987092-2e74a896da4843d1957296237c6c8304-0-186ef6a0f40eb86dc0bd5819bf337dfc
         * status : accepted
         * distance : 4
         */

        private int user_id;
        private String nickname;
        private String total_days;
        private String avatar;
        private String status;
        private float distance;
        private String mobile;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getTotal_days() {
            return total_days;
        }

        public void setTotal_days(String total_days) {
            this.total_days = total_days;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public float getDistance() {
            return distance;
        }

        public void setDistance(float distance) {
            this.distance = distance;
        }
    }
}
