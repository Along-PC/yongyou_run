package com.tourye.run.bean;

import java.util.List;
/**
 *  
 * @ClassName:   GameRankingBean
 *
 * @Author:   along
 * 
 * @Description: 个人完赛率排行榜实体
 * 
 * @CreateDate:   2019/3/19 3:53 PM
 * 
 */
public class GameRankingBean {

    /**
     * status : 0
     * timestamp : 1552981920
     * data : {"current":{"nickname":"黄明","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-18/3-161949-KuSMTu.jpg%21320w?auth_key=1552985520-39b7da050f1a474c96132bd64d4c1888-0-8f36411d28bebce2260798321becdd76","rate":0,"total_days":"0","thumb_up_count":0,"rank":1,"has_sign_in":null,"has_thumb_up":false},"list":[{"id":3,"nickname":"黄明","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-18/3-161949-KuSMTu.jpg%21320w?auth_key=1552985520-57ffd16d9987499d98359e91954f355a-0-e81e96f5c81bb828b806a293d05e1f0b","rate":0,"total_days":0,"thumb_up_count":0,"has_thumb_up":false,"has_sign_in":null}]}
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
         * current : {"nickname":"黄明","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-18/3-161949-KuSMTu.jpg%21320w?auth_key=1552985520-39b7da050f1a474c96132bd64d4c1888-0-8f36411d28bebce2260798321becdd76","rate":0,"total_days":"0","thumb_up_count":0,"rank":1,"has_sign_in":null,"has_thumb_up":false}
         * list : [{"id":3,"nickname":"黄明","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-18/3-161949-KuSMTu.jpg%21320w?auth_key=1552985520-57ffd16d9987499d98359e91954f355a-0-e81e96f5c81bb828b806a293d05e1f0b","rate":0,"total_days":0,"thumb_up_count":0,"has_thumb_up":false,"has_sign_in":null}]
         */

        private ListBean current;
        private List<ListBean> list;

        public ListBean getCurrent() {
            return current;
        }

        public void setCurrent(ListBean current) {
            this.current = current;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class CurrentBean {
            /**
             * nickname : 黄明
             * avatar : http://static.ro-test.xorout.com/user_avatar/2019-03-18/3-161949-KuSMTu.jpg%21320w?auth_key=1552985520-39b7da050f1a474c96132bd64d4c1888-0-8f36411d28bebce2260798321becdd76
             * rate : 0
             * total_days : 0
             * thumb_up_count : 0
             * rank : 1
             * has_sign_in : null
             * has_thumb_up : false
             */

            private String nickname;
            private String avatar;
            private int rate;
            private String total_days;
            private int thumb_up_count;
            private int rank;
            private Object has_sign_in;
            private boolean has_thumb_up;

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

            public int getRate() {
                return rate;
            }

            public void setRate(int rate) {
                this.rate = rate;
            }

            public String getTotal_days() {
                return total_days;
            }

            public void setTotal_days(String total_days) {
                this.total_days = total_days;
            }

            public int getThumb_up_count() {
                return thumb_up_count;
            }

            public void setThumb_up_count(int thumb_up_count) {
                this.thumb_up_count = thumb_up_count;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public Object getHas_sign_in() {
                return has_sign_in;
            }

            public void setHas_sign_in(Object has_sign_in) {
                this.has_sign_in = has_sign_in;
            }

            public boolean isHas_thumb_up() {
                return has_thumb_up;
            }

            public void setHas_thumb_up(boolean has_thumb_up) {
                this.has_thumb_up = has_thumb_up;
            }
        }

        public static class ListBean {
            /**
             * id : 3
             * nickname : 黄明
             * avatar : http://static.ro-test.xorout.com/user_avatar/2019-03-18/3-161949-KuSMTu.jpg%21320w?auth_key=1552985520-57ffd16d9987499d98359e91954f355a-0-e81e96f5c81bb828b806a293d05e1f0b
             * rate : 0
             * total_days : 0
             * thumb_up_count : 0
             * has_thumb_up : false
             * has_sign_in : null
             */

            private int id;
            private String nickname;
            private String avatar;
            private int rate;
            private int total_days;
            private int thumb_up_count;
            private boolean has_thumb_up;
            private boolean has_sign_in;
            private int rank;

            public boolean isHas_sign_in() {
                return has_sign_in;
            }

            public void setHas_sign_in(boolean has_sign_in) {
                this.has_sign_in = has_sign_in;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
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

            public int getRate() {
                return rate;
            }

            public void setRate(int rate) {
                this.rate = rate;
            }

            public int getTotal_days() {
                return total_days;
            }

            public void setTotal_days(int total_days) {
                this.total_days = total_days;
            }

            public int getThumb_up_count() {
                return thumb_up_count;
            }

            public void setThumb_up_count(int thumb_up_count) {
                this.thumb_up_count = thumb_up_count;
            }

            public boolean isHas_thumb_up() {
                return has_thumb_up;
            }

            public void setHas_thumb_up(boolean has_thumb_up) {
                this.has_thumb_up = has_thumb_up;
            }
        }
    }
}
