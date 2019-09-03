package com.tourye.run.bean;

import java.util.List;

public class TodayDistanceRankBean {

    /**
     * status : 0
     * timestamp : 1554965592
     * data : {"current":{"id":10,"nickname":"阳光","avatar":"http://static.ro-test.xorout.com/user_avatar/10000A/2019-03-23/153708.jpg?auth_key=1554969192-8e10e8e4d58f4b0bb3ca98fa607119a2-0-ed9153a1b784056e69051ece878ea3c1","distance":0,"passed_days":"0","thumb_up_count":2,"has_thumb_up ":true,"rank":3},"list":[{"id":13,"nickname":"明黄","avatar":"http://static.ro-test.xorout.com/user_avatar/10000D/2019-03-23/155110.jpg?auth_key=1554969192-0d73ea1906d0486da3bbf7b4bf3aae5e-0-9215e3033b75f9643ae75960123cbfef","distance":3,"passed_days":"2","thumb_up_count":2,"has_thumb_up ":true},{"id":14,"nickname":"蒋建雨","avatar":"http://static.ro-test.xorout.com/user_avatar/10000E/2019-03-23/160421.jpg?auth_key=1554969192-9613822b5dcd4f849f9242a3619a37ad-0-5ea84b372c07153ebeb65d6cae43ff1c","distance":0,"passed_days":"0","thumb_up_count":1,"has_thumb_up ":true},{"id":10,"nickname":"阳光","avatar":"http://static.ro-test.xorout.com/user_avatar/10000A/2019-03-23/153708.jpg?auth_key=1554969192-2ebbe98ae89344cba7dc38ee3805dafc-0-55d31e5a9bf238c7db8767ab56e09b69","distance":0,"passed_days":"0","thumb_up_count":2,"has_thumb_up ":true}]}
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
         * current : {"id":10,"nickname":"阳光","avatar":"http://static.ro-test.xorout.com/user_avatar/10000A/2019-03-23/153708.jpg?auth_key=1554969192-8e10e8e4d58f4b0bb3ca98fa607119a2-0-ed9153a1b784056e69051ece878ea3c1","distance":0,"passed_days":"0","thumb_up_count":2,"has_thumb_up ":true,"rank":3}
         * list : [{"id":13,"nickname":"明黄","avatar":"http://static.ro-test.xorout.com/user_avatar/10000D/2019-03-23/155110.jpg?auth_key=1554969192-0d73ea1906d0486da3bbf7b4bf3aae5e-0-9215e3033b75f9643ae75960123cbfef","distance":3,"passed_days":"2","thumb_up_count":2,"has_thumb_up ":true},{"id":14,"nickname":"蒋建雨","avatar":"http://static.ro-test.xorout.com/user_avatar/10000E/2019-03-23/160421.jpg?auth_key=1554969192-9613822b5dcd4f849f9242a3619a37ad-0-5ea84b372c07153ebeb65d6cae43ff1c","distance":0,"passed_days":"0","thumb_up_count":1,"has_thumb_up ":true},{"id":10,"nickname":"阳光","avatar":"http://static.ro-test.xorout.com/user_avatar/10000A/2019-03-23/153708.jpg?auth_key=1554969192-2ebbe98ae89344cba7dc38ee3805dafc-0-55d31e5a9bf238c7db8767ab56e09b69","distance":0,"passed_days":"0","thumb_up_count":2,"has_thumb_up ":true}]
         */

        private CurrentBean current;
        private List<CurrentBean> list;

        public CurrentBean getCurrent() {
            return current;
        }

        public void setCurrent(CurrentBean current) {
            this.current = current;
        }

        public List<CurrentBean> getList() {
            return list;
        }

        public void setList(List<CurrentBean> list) {
            this.list = list;
        }

        public static class CurrentBean {
            /**
             * id : 10
             * nickname : 阳光
             * avatar : http://static.ro-test.xorout.com/user_avatar/10000A/2019-03-23/153708.jpg?auth_key=1554969192-8e10e8e4d58f4b0bb3ca98fa607119a2-0-ed9153a1b784056e69051ece878ea3c1
             * distance : 0
             * passed_days : 0
             * thumb_up_count : 2
             * has_thumb_up  : true
             * rank : 3
             */

            private int id;
            private String nickname;
            private String avatar;
            private float distance;
            private String passed_days;
            private int thumb_up_count;
            private boolean has_thumb_up;
            private int rank;
            private boolean isMyself;//是否是自己的数据

            public boolean isMyself() {
                return isMyself;
            }

            public void setMyself(boolean myself) {
                isMyself = myself;
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

            public float getDistance() {
                return distance;
            }

            public void setDistance(float distance) {
                this.distance = distance;
            }

            public String getPassed_days() {
                return passed_days;
            }

            public void setPassed_days(String passed_days) {
                this.passed_days = passed_days;
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

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }
        }

        public static class ListBean {
            /**
             * id : 13
             * nickname : 明黄
             * avatar : http://static.ro-test.xorout.com/user_avatar/10000D/2019-03-23/155110.jpg?auth_key=1554969192-0d73ea1906d0486da3bbf7b4bf3aae5e-0-9215e3033b75f9643ae75960123cbfef
             * distance : 3
             * passed_days : 2
             * thumb_up_count : 2
             * has_thumb_up  : true
             */

            private int id;
            private String nickname;
            private String avatar;
            private int distance;
            private String passed_days;
            private int thumb_up_count;
            private boolean has_thumb_up;

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

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
            }

            public String getPassed_days() {
                return passed_days;
            }

            public void setPassed_days(String passed_days) {
                this.passed_days = passed_days;
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
