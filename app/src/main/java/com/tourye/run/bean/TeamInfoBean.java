package com.tourye.run.bean;
/**
 *
 * @ClassName:   TeamInfoBean
 *
 * @Author:   along
 *
 * @Description: 队伍管理页面队伍信息实体
 *
 * @CreateDate:   2019/3/26 11:17 AM
 *
 */
public class TeamInfoBean {

    /**
     * status : 0
     * timestamp : 1553569709
     * data : {"name":"黄明测试","sign_up_finish_date":"2019-03-25","member_count":0,"prize_value":0}
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
         * name : 黄明测试
         * sign_up_finish_date : 2019-03-25
         * member_count : 0
         * prize_value : 0
         */

        private String name;
        private String sign_up_finish_date;
        private int member_count;
        private int prize_value;
        private String logo;
        private String rate;

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSign_up_finish_date() {
            return sign_up_finish_date;
        }

        public void setSign_up_finish_date(String sign_up_finish_date) {
            this.sign_up_finish_date = sign_up_finish_date;
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

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }
    }
}
