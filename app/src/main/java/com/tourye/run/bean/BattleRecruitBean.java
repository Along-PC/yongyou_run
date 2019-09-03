package com.tourye.run.bean;

import java.util.List;

/**
 *
 * @ClassName:   BattleRecruitBean
 *
 * @Author:   along
 *
 * @Description:    战队招募排行榜实体
 *
 * @CreateDate:   2019/4/18 4:51 PM
 *
 */
public class BattleRecruitBean {

    /**
     * status : 0
     * timestamp : 1555577839
     * data : {"current":{"id":10,"nickname":"gggg","avatar":"http://static.ro-test.xorout.com/1/2019-04-18/10-140911-yLaQSZ.jpg%21320w?auth_key=1555581439-5866de6607604e41a99faa9b62904b9c-0-499c25fc720640aebb40de4daaed0133","score":1,"rank":6},"list":[{"id":5,"nickname":"Grace 🐣","avatar":"http://static.ro-test.xorout.com/user_avatar/100005/2019-03-23/152214.jpg%21320w?auth_key=1555581439-f99cca39bf7f4f9686559dc2c349261f-0-560144cfd32c8d035516f2d1b08b6a17","score":5},{"id":3,"nickname":"见一滴墨水的蓝见一滴墨水的蓝见一","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-23/3-160229-3CFkHU.jpg%21320w?auth_key=1555581439-1479d06697074450814535db9fa71295-0-a09a5fb582d3f1d66969b1def2ae91f9","score":4},{"id":6,"nickname":"小小","avatar":"http://static.ro-test.xorout.com/user_avatar/100006/2019-03-23/152230.jpg%21320w?auth_key=1555581439-bd36cb6cecc64e6abce13f87a6bcf95b-0-32506a8e48d1015a7a0d399d2e3ee99a","score":2},{"id":9,"nickname":"孙大剩","avatar":"http://static.ro-test.xorout.com/user_avatar/100009/2019-03-23/153528.jpg%21320w?auth_key=1555581439-a907ed4b84744a16be2824314f3c2222-0-5b22dac768014f1c9396350c79c65034","score":1},{"id":2,"nickname":"黄明黄明黄明黄明黄明黄明黄明黄明黄明黄明","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-23/2-172913-7CqYwu.jpg%21320w?auth_key=1555581439-c4539e299139496fa1abf170472d406d-0-72ff1d883c3d29b569f0eec45dfc4ef6","score":1},{"id":10,"nickname":"gggg","avatar":"http://static.ro-test.xorout.com/1/2019-04-18/10-140911-yLaQSZ.jpg%21320w?auth_key=1555581439-94571b3687f6402abc1f9b5300fcd443-0-60243e7bb894950881de126f57ffb35e","score":1},{"id":1,"nickname":"卢森煌","avatar":"http://static.ro-test.xorout.com/user_avatar/100001/2019-03-23/151825.jpg%21320w?auth_key=1555581439-e072bbfedc6d400c81ac3747ea7224a0-0-63517ad1db047f283c1a53be3a6e3b32","score":1}]}
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
         * current : {"id":10,"nickname":"gggg","avatar":"http://static.ro-test.xorout.com/1/2019-04-18/10-140911-yLaQSZ.jpg%21320w?auth_key=1555581439-5866de6607604e41a99faa9b62904b9c-0-499c25fc720640aebb40de4daaed0133","score":1,"rank":6}
         * list : [{"id":5,"nickname":"Grace 🐣","avatar":"http://static.ro-test.xorout.com/user_avatar/100005/2019-03-23/152214.jpg%21320w?auth_key=1555581439-f99cca39bf7f4f9686559dc2c349261f-0-560144cfd32c8d035516f2d1b08b6a17","score":5},{"id":3,"nickname":"见一滴墨水的蓝见一滴墨水的蓝见一","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-23/3-160229-3CFkHU.jpg%21320w?auth_key=1555581439-1479d06697074450814535db9fa71295-0-a09a5fb582d3f1d66969b1def2ae91f9","score":4},{"id":6,"nickname":"小小","avatar":"http://static.ro-test.xorout.com/user_avatar/100006/2019-03-23/152230.jpg%21320w?auth_key=1555581439-bd36cb6cecc64e6abce13f87a6bcf95b-0-32506a8e48d1015a7a0d399d2e3ee99a","score":2},{"id":9,"nickname":"孙大剩","avatar":"http://static.ro-test.xorout.com/user_avatar/100009/2019-03-23/153528.jpg%21320w?auth_key=1555581439-a907ed4b84744a16be2824314f3c2222-0-5b22dac768014f1c9396350c79c65034","score":1},{"id":2,"nickname":"黄明黄明黄明黄明黄明黄明黄明黄明黄明黄明","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-23/2-172913-7CqYwu.jpg%21320w?auth_key=1555581439-c4539e299139496fa1abf170472d406d-0-72ff1d883c3d29b569f0eec45dfc4ef6","score":1},{"id":10,"nickname":"gggg","avatar":"http://static.ro-test.xorout.com/1/2019-04-18/10-140911-yLaQSZ.jpg%21320w?auth_key=1555581439-94571b3687f6402abc1f9b5300fcd443-0-60243e7bb894950881de126f57ffb35e","score":1},{"id":1,"nickname":"卢森煌","avatar":"http://static.ro-test.xorout.com/user_avatar/100001/2019-03-23/151825.jpg%21320w?auth_key=1555581439-e072bbfedc6d400c81ac3747ea7224a0-0-63517ad1db047f283c1a53be3a6e3b32","score":1}]
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
             * nickname : gggg
             * avatar : http://static.ro-test.xorout.com/1/2019-04-18/10-140911-yLaQSZ.jpg%21320w?auth_key=1555581439-5866de6607604e41a99faa9b62904b9c-0-499c25fc720640aebb40de4daaed0133
             * score : 1
             * rank : 6
             */

            private int id;
            private String nickname;
            private String avatar;
            private int score;
            private String rank;

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

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }
        }

        public static class ListBean {
            /**
             * id : 5
             * nickname : Grace 🐣
             * avatar : http://static.ro-test.xorout.com/user_avatar/100005/2019-03-23/152214.jpg%21320w?auth_key=1555581439-f99cca39bf7f4f9686559dc2c349261f-0-560144cfd32c8d035516f2d1b08b6a17
             * score : 5
             */

            private int id;
            private String nickname;
            private String avatar;
            private int score;

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

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }
        }
    }
}
