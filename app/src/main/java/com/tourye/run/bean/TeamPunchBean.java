package com.tourye.run.bean;
/**
 *
 * @ClassName:   TeamPunchBean
 *
 * @Author:   along
 *
 * @Description:    队伍打卡情况
 *
 * @CreateDate:   2019/4/23 10:28 AM
 *
 */
public class TeamPunchBean {

    /**
     * status : 0
     * timestamp : 1555986488
     * data : {"member_count":4,"success_count":0,"no_sign_in_count":4,"normal_count":0,"rejected_count":0}
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
         * member_count : 4
         * success_count : 0
         * no_sign_in_count : 4
         * normal_count : 0
         * rejected_count : 0
         */

        private int member_count;
        private int success_count;
        private int no_sign_in_count;
        private int normal_count;
        private int rejected_count;

        public int getMember_count() {
            return member_count;
        }

        public void setMember_count(int member_count) {
            this.member_count = member_count;
        }

        public int getSuccess_count() {
            return success_count;
        }

        public void setSuccess_count(int success_count) {
            this.success_count = success_count;
        }

        public int getNo_sign_in_count() {
            return no_sign_in_count;
        }

        public void setNo_sign_in_count(int no_sign_in_count) {
            this.no_sign_in_count = no_sign_in_count;
        }

        public int getNormal_count() {
            return normal_count;
        }

        public void setNormal_count(int normal_count) {
            this.normal_count = normal_count;
        }

        public int getRejected_count() {
            return rejected_count;
        }

        public void setRejected_count(int rejected_count) {
            this.rejected_count = rejected_count;
        }
    }
}
