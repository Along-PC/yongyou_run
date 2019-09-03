package com.tourye.run.bean;
/**
 *
 * @ClassName:   RecruitStatusBean
 *
 * @Author:   along
 *
 * @Description:队长招募信息
 *
 * @CreateDate:   2019/4/16 4:48 PM
 *
 */
public class RecruitStatusBean {

    /**
     * status : 0
     * timestamp : 1555404427
     * data : {"team_count":0,"member_count":0,"prize_value":0}
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
         * team_count : 0
         * member_count : 0
         * prize_value : 0
         */

        private int team_count;
        private int member_count;
        private int prize_value;

        public int getTeam_count() {
            return team_count;
        }

        public void setTeam_count(int team_count) {
            this.team_count = team_count;
        }

        public int getMember_count() {
            return member_count;
        }

        public void setMember_count(int member_count) {
            this.member_count = member_count;
        }

        public int getPrize_value() {
            return prize_value;
        }

        public void setPrize_value(int prize_value) {
            this.prize_value = prize_value;
        }
    }
}
