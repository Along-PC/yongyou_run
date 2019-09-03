package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   TeamThumbRankBean
 *
 * @Author:   along
 *
 * @Description:    战队人气排行实体
 *
 * @CreateDate:   2019/4/1 2:53 PM
 *
 */
public class TeamThumbRankBean {

    /**
     * status : 0
     * timestamp : 1554101547
     * data : {"current":null,"list":[{"id":9,"name":"好他","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/1-203258-wssSBU.jpg%21320w?auth_key=1554105147-8c4feac22d5a4448a8977080159c5e33-0-2421a57af820d664d219a7aaa8e7064d","count":0},{"id":8,"name":"好","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/1-203007-KvRhyo.jpg%21320w?auth_key=1554105147-eff404d016f64d8d8445be04251bb311-0-49a22bcdf9992fc95cdb8d6bad8f8352","count":0},{"id":7,"name":"running5","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/9-182817-iSFmId.jpg%21320w?auth_key=1554105147-58ac9cc79ab14646b22804459bb60fe1-0-c7acc4f6284d1a616ae10d631b9bf940","count":0},{"id":6,"name":"running","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/9-171410-WljDd4.jpg%21320w?auth_key=1554105147-9dedbed0a4e8445ebc59b2b470a1fab4-0-3616147bf937b0eb0eebc457e6125af0","count":0},{"id":5,"name":"明黄测试","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/13-155144-9Lge3h.jpg%21320w?auth_key=1554105147-361c33aefd714eab9f721c37c2ffa420-0-8e5859304e80339bbfeee407950d23a8","count":0},{"id":4,"name":"运动个鸟","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/8-153835-7dgHFf.jpg%21320w?auth_key=1554105147-f3d446b4322446ee8c803035ff3e4505-0-2c4406c5e2907059e0c19c511f565e36","count":0},{"id":3,"name":"珊珊222队🤡","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-24/3-235138-eSgyFe.jpg%21320w?auth_key=1554105147-1f18c2084d474a468f5d849cd1bd386f-0-97e827972975d5191815b3919850b2ec","count":0},{"id":2,"name":"废柴坚持不住🙁","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/5-164827-yCTa2X.jpg%21320w?auth_key=1554105147-92f333225a6c437687827bff3c93b45d-0-a5a8da1eb12673b1e5b1d1598d2efdb0","count":0},{"id":19,"name":"减肥22队","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-29/3-145845-5xeNn0.jpg%21320w?auth_key=1554105147-cfbed17971ff4588bed9d759170079b0-0-baafee1b4e8978cd29bf1db81560b28b","count":0},{"id":18,"name":"减肥队","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-29/3-144548-xyJob3.jpg%21320w?auth_key=1554105147-aaa8159124e54828a821cd44d58b9313-0-31968a4a119e6cf9fa0d6972cb184fae","count":0}]}
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
         * current : null
         * list : [{"id":9,"name":"好他","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/1-203258-wssSBU.jpg%21320w?auth_key=1554105147-8c4feac22d5a4448a8977080159c5e33-0-2421a57af820d664d219a7aaa8e7064d","count":0},{"id":8,"name":"好","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/1-203007-KvRhyo.jpg%21320w?auth_key=1554105147-eff404d016f64d8d8445be04251bb311-0-49a22bcdf9992fc95cdb8d6bad8f8352","count":0},{"id":7,"name":"running5","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/9-182817-iSFmId.jpg%21320w?auth_key=1554105147-58ac9cc79ab14646b22804459bb60fe1-0-c7acc4f6284d1a616ae10d631b9bf940","count":0},{"id":6,"name":"running","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/9-171410-WljDd4.jpg%21320w?auth_key=1554105147-9dedbed0a4e8445ebc59b2b470a1fab4-0-3616147bf937b0eb0eebc457e6125af0","count":0},{"id":5,"name":"明黄测试","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/13-155144-9Lge3h.jpg%21320w?auth_key=1554105147-361c33aefd714eab9f721c37c2ffa420-0-8e5859304e80339bbfeee407950d23a8","count":0},{"id":4,"name":"运动个鸟","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/8-153835-7dgHFf.jpg%21320w?auth_key=1554105147-f3d446b4322446ee8c803035ff3e4505-0-2c4406c5e2907059e0c19c511f565e36","count":0},{"id":3,"name":"珊珊222队🤡","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-24/3-235138-eSgyFe.jpg%21320w?auth_key=1554105147-1f18c2084d474a468f5d849cd1bd386f-0-97e827972975d5191815b3919850b2ec","count":0},{"id":2,"name":"废柴坚持不住🙁","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/5-164827-yCTa2X.jpg%21320w?auth_key=1554105147-92f333225a6c437687827bff3c93b45d-0-a5a8da1eb12673b1e5b1d1598d2efdb0","count":0},{"id":19,"name":"减肥22队","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-29/3-145845-5xeNn0.jpg%21320w?auth_key=1554105147-cfbed17971ff4588bed9d759170079b0-0-baafee1b4e8978cd29bf1db81560b28b","count":0},{"id":18,"name":"减肥队","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-29/3-144548-xyJob3.jpg%21320w?auth_key=1554105147-aaa8159124e54828a821cd44d58b9313-0-31968a4a119e6cf9fa0d6972cb184fae","count":0}]
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
             * id : 9
             * name : 好他
             * logo : http://static.ro-test.xorout.com/team_avatar/2019-03-23/1-203258-wssSBU.jpg%21320w?auth_key=1554105147-8c4feac22d5a4448a8977080159c5e33-0-2421a57af820d664d219a7aaa8e7064d
             * count : 0
             */

            private int id;
            private String name;
            private String logo;
            private int count;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }
        }
    }
}
