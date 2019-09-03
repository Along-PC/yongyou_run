package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   PersonInviteRankBean
 *
 * @Author:   along
 *
 * @Description:    个人邀请排行榜适配器
 *
 * @CreateDate:   2019/4/2 10:08 AM
 *
 */
public class PersonInviteRankBean {

    /**
     * status : 0
     * timestamp : 1554170844
     * data : {"current":{"id":10,"nickname":"阳光","avatar":"http://static.ro-test.xorout.com/user_avatar/10000A/2019-03-23/153708.jpg%21320w?auth_key=1554174444-555e3d7943e8448e92b0f715866569a6-0-93d1054aa304058e50efdc410f47f4c7","referrer_count":0,"rank":"未上榜"},"list":[{"id":21,"nickname":"我是苹果测试机没错苹果测试机就是","avatar":"http://static.ro-test.xorout.com/user_avatar/10000L/2019-03-25/100041.jpg%21320w?auth_key=1554174444-a4a3966e2bfd497ebc46840c5a676a94-0-f7bfee46f7d0622a7db03d35d013ea92","referrer_count":3},{"id":3,"nickname":"见一滴墨水的蓝见一滴墨水的蓝见一","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-23/3-160229-3CFkHU.jpg%21320w?auth_key=1554174444-eec9c5c5cf7747ee87395230969f95a6-0-95b5d8b9bc9b45da050e89f9fa12878c","referrer_count":1}]}
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
         * current : {"id":10,"nickname":"阳光","avatar":"http://static.ro-test.xorout.com/user_avatar/10000A/2019-03-23/153708.jpg%21320w?auth_key=1554174444-555e3d7943e8448e92b0f715866569a6-0-93d1054aa304058e50efdc410f47f4c7","referrer_count":0,"rank":"未上榜"}
         * list : [{"id":21,"nickname":"我是苹果测试机没错苹果测试机就是","avatar":"http://static.ro-test.xorout.com/user_avatar/10000L/2019-03-25/100041.jpg%21320w?auth_key=1554174444-a4a3966e2bfd497ebc46840c5a676a94-0-f7bfee46f7d0622a7db03d35d013ea92","referrer_count":3},{"id":3,"nickname":"见一滴墨水的蓝见一滴墨水的蓝见一","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-23/3-160229-3CFkHU.jpg%21320w?auth_key=1554174444-eec9c5c5cf7747ee87395230969f95a6-0-95b5d8b9bc9b45da050e89f9fa12878c","referrer_count":1}]
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

        public static class ListBean {
            /**
             * id : 21
             * nickname : 我是苹果测试机没错苹果测试机就是
             * avatar : http://static.ro-test.xorout.com/user_avatar/10000L/2019-03-25/100041.jpg%21320w?auth_key=1554174444-a4a3966e2bfd497ebc46840c5a676a94-0-f7bfee46f7d0622a7db03d35d013ea92
             * referrer_count : 3
             */

            private int id;
            private String nickname;
            private String avatar;
            private int referrer_count;
            private String rank;

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
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

            public int getReferrer_count() {
                return referrer_count;
            }

            public void setReferrer_count(int referrer_count) {
                this.referrer_count = referrer_count;
            }
        }
    }
}
