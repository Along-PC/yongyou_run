package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   TeamInfluenceBean
 *
 * @Author:   along
 *
 * @Description: 队伍管理 影响人数实体
 *
 * @CreateDate:   2019/3/26 11:34 AM
 *
 */
public class TeamInfluenceBean {

    /**
     * status : 0
     * timestamp : 1553571225
     * data : {"joined":"0","not_joined":"2","list":[{"nickname":"于湛","avatar":"http://static.ro-test.xorout.com/user_avatar/100007/2019-03-23/152448.jpg%21200h?auth_key=1553574825-4b12419d492e4a18a1a5992c999e388d-0-f80c80763a42e14be7d10a015a2c38f0","joined":false},{"nickname":"李凯。","avatar":"http://static.ro-test.xorout.com/user_avatar/10000J/2019-03-24/112426.jpg%21200h?auth_key=1553574825-345f82fc8ff34e3db4c9737de6ebc506-0-9e6425584d5d4e87382469cbb27c6f63","joined":false}]}
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
         * not_joined : 2
         * list : [{"nickname":"于湛","avatar":"http://static.ro-test.xorout.com/user_avatar/100007/2019-03-23/152448.jpg%21200h?auth_key=1553574825-4b12419d492e4a18a1a5992c999e388d-0-f80c80763a42e14be7d10a015a2c38f0","joined":false},{"nickname":"李凯。","avatar":"http://static.ro-test.xorout.com/user_avatar/10000J/2019-03-24/112426.jpg%21200h?auth_key=1553574825-345f82fc8ff34e3db4c9737de6ebc506-0-9e6425584d5d4e87382469cbb27c6f63","joined":false}]
         */

        private int joined;
        private int not_joined;
        private List<ListBean> list;

        public int getJoined() {
            return joined;
        }

        public void setJoined(int joined) {
            this.joined = joined;
        }

        public int getNot_joined() {
            return not_joined;
        }

        public void setNot_joined(int not_joined) {
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
             * nickname : 于湛
             * avatar : http://static.ro-test.xorout.com/user_avatar/100007/2019-03-23/152448.jpg%21200h?auth_key=1553574825-4b12419d492e4a18a1a5992c999e388d-0-f80c80763a42e14be7d10a015a2c38f0
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
