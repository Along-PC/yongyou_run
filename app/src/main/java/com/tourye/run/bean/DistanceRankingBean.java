package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   DistanceRankingBean
 *
 * @Author:   along
 *
 * @Description:  个人总距离排行实体
 *
 * @CreateDate:   2019/3/20 11:22 AM
 *
 */
public class DistanceRankingBean {

    /**
     * status : 0
     * timestamp : 1553052074
     * data : {"current":{"nickname":"黄明","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-18/3-161949-KuSMTu.jpg%21320w?auth_key=1553055674-b376b8012524491a9f1c3ce466cfc729-0-8147b815b58e640d9844a2c415acc5d8","total_distance":0,"thumb_up_count":0,"rank":1,"has_thumb_up":false},"list":[{"id":3,"nickname":"黄明","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-18/3-161949-KuSMTu.jpg%21320w?auth_key=1553055674-d252acddb7524654a114db012d8b6ef5-0-95c92967304dec0c71678b53e7ad721e","total_distance":0,"thumb_up_count":0,"has_thumb_up":false}]}
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
         * current : {"nickname":"黄明","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-18/3-161949-KuSMTu.jpg%21320w?auth_key=1553055674-b376b8012524491a9f1c3ce466cfc729-0-8147b815b58e640d9844a2c415acc5d8","total_distance":0,"thumb_up_count":0,"rank":1,"has_thumb_up":false}
         * list : [{"id":3,"nickname":"黄明","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-18/3-161949-KuSMTu.jpg%21320w?auth_key=1553055674-d252acddb7524654a114db012d8b6ef5-0-95c92967304dec0c71678b53e7ad721e","total_distance":0,"thumb_up_count":0,"has_thumb_up":false}]
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
             * avatar : http://static.ro-test.xorout.com/user_avatar/2019-03-18/3-161949-KuSMTu.jpg%21320w?auth_key=1553055674-b376b8012524491a9f1c3ce466cfc729-0-8147b815b58e640d9844a2c415acc5d8
             * total_distance : 0
             * thumb_up_count : 0
             * rank : 1
             * has_thumb_up : false
             */

            private String nickname;
            private String avatar;
            private int total_distance;
            private int thumb_up_count;
            private int rank;
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

            public int getTotal_distance() {
                return total_distance;
            }

            public void setTotal_distance(int total_distance) {
                this.total_distance = total_distance;
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
             * avatar : http://static.ro-test.xorout.com/user_avatar/2019-03-18/3-161949-KuSMTu.jpg%21320w?auth_key=1553055674-d252acddb7524654a114db012d8b6ef5-0-95c92967304dec0c71678b53e7ad721e
             * total_distance : 0
             * thumb_up_count : 0
             * has_thumb_up : false
             */

            private int id;
            private String nickname;
            private String avatar;
            private float total_distance;
            private int thumb_up_count;
            private boolean has_thumb_up;
            private int rank;

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

            public float getTotal_distance() {
                return total_distance;
            }

            public void setTotal_distance(float total_distance) {
                this.total_distance = total_distance;
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
