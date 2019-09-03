package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   TeamVisitBean
 *
 * @Author:   along
 *
 * @Description:    战队访问量实体
 *
 * @CreateDate:   2019/4/19 1:15 PM
 *
 */
public class TeamVisitBean {

    /**
     * status : 0
     * timestamp : 1555650563
     * data : {"joined":"0","not_joined":"1","list":[{"nickname":"gggg","avatar":"http://static.ro-test.xorout.com/1/2019-04-18/10-140911-yLaQSZ.jpg%21200h?auth_key=1555654163-b761da3b03694976bb0ab1136dbea2c4-0-9281741090d066bd7dbd9a2bae9c1a7f","joined":false}]}
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
         * joined : 0
         * not_joined : 1
         * list : [{"nickname":"gggg","avatar":"http://static.ro-test.xorout.com/1/2019-04-18/10-140911-yLaQSZ.jpg%21200h?auth_key=1555654163-b761da3b03694976bb0ab1136dbea2c4-0-9281741090d066bd7dbd9a2bae9c1a7f","joined":false}]
         */

        private String joined;
        private String not_joined;
        private List<ListBean> list;

        public String getJoined() {
            return joined;
        }

        public void setJoined(String joined) {
            this.joined = joined;
        }

        public String getNot_joined() {
            return not_joined;
        }

        public void setNot_joined(String not_joined) {
            this.not_joined = not_joined;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * nickname : gggg
             * avatar : http://static.ro-test.xorout.com/1/2019-04-18/10-140911-yLaQSZ.jpg%21200h?auth_key=1555654163-b761da3b03694976bb0ab1136dbea2c4-0-9281741090d066bd7dbd9a2bae9c1a7f
             * joined : false
             */

            private String nickname;
            private String avatar;
            private boolean joined;

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

            public boolean isJoined() {
                return joined;
            }

            public void setJoined(boolean joined) {
                this.joined = joined;
            }
        }
    }
}
