package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   BattleRankingCompleteBean
 *
 * @Author:   along
 *
 * @Description:    战队完赛率排行榜实体
 *
 * @CreateDate:   2019/4/1 10:58 AM
 *
 */
public class BattleRankingCompleteBean {


    /**
     * status : 0
     * timestamp : 1554091193
     * data : {"current":{"id":3,"name":"珊珊222队🤡","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-24/3-235138-eSgyFe.jpg%21320w?auth_key=1554094793-da0268ef490d4225abdcfc2ec8349571-0-06398e53d34fd7eae52918128815d3a0","member_count":4,"rank":1,"rate":0,"total_distance":0},"list":[{"id":9,"name":"好他","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/1-203258-wssSBU.jpg%21320w?auth_key=1554094793-b60d768e619b41d99b3bd1dd22be6109-0-c808f879b094f99cbe122d3719ab35fc","member_count":0,"rate":0,"total_distance":0},{"id":8,"name":"好","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/1-203007-KvRhyo.jpg%21320w?auth_key=1554094793-d9cf1960cfc94e5493d27712f64dca5c-0-5d5a7e735f16a2e646606714df9920e4","member_count":0,"rate":0,"total_distance":0},{"id":5,"name":"明黄测试","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/13-155144-9Lge3h.jpg%21320w?auth_key=1554094793-e945c98088b249f7a86d7fd53e064c34-0-88ae2b942a8d651fb80a6104bd408337","member_count":49,"rate":0,"total_distance":0},{"id":4,"name":"运动个鸟","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/8-153835-7dgHFf.jpg%21320w?auth_key=1554094793-0c878f73ba7b4bd388cba5f074a4905f-0-c56616abe348cafc4aa15a808e14ca28","member_count":50,"rate":0,"total_distance":0},{"id":15,"name":"珊珊44队🙌","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-25/3-162141-9EMd9r.jpg%21320w?auth_key=1554094793-a36b8ce469e845b49903b3a23768400b-0-55529449c72e0d31198b69c72af251b4","member_count":0,"rate":0,"total_distance":0},{"id":14,"name":"珊珊33队🙌","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-25/3-161709-JuJ8G2.jpg%21320w?auth_key=1554094793-69f823a35ce84cfb9247ded609d71dfe-0-c00ece57a569aa0d67b9c87fd6bfd68f","member_count":0,"rate":0,"total_distance":0}]}
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
         * current : {"id":3,"name":"珊珊222队🤡","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-24/3-235138-eSgyFe.jpg%21320w?auth_key=1554094793-da0268ef490d4225abdcfc2ec8349571-0-06398e53d34fd7eae52918128815d3a0","member_count":4,"rank":1,"rate":0,"total_distance":0}
         * list : [{"id":9,"name":"好他","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/1-203258-wssSBU.jpg%21320w?auth_key=1554094793-b60d768e619b41d99b3bd1dd22be6109-0-c808f879b094f99cbe122d3719ab35fc","member_count":0,"rate":0,"total_distance":0},{"id":8,"name":"好","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/1-203007-KvRhyo.jpg%21320w?auth_key=1554094793-d9cf1960cfc94e5493d27712f64dca5c-0-5d5a7e735f16a2e646606714df9920e4","member_count":0,"rate":0,"total_distance":0},{"id":5,"name":"明黄测试","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/13-155144-9Lge3h.jpg%21320w?auth_key=1554094793-e945c98088b249f7a86d7fd53e064c34-0-88ae2b942a8d651fb80a6104bd408337","member_count":49,"rate":0,"total_distance":0},{"id":4,"name":"运动个鸟","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-23/8-153835-7dgHFf.jpg%21320w?auth_key=1554094793-0c878f73ba7b4bd388cba5f074a4905f-0-c56616abe348cafc4aa15a808e14ca28","member_count":50,"rate":0,"total_distance":0},{"id":15,"name":"珊珊44队🙌","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-25/3-162141-9EMd9r.jpg%21320w?auth_key=1554094793-a36b8ce469e845b49903b3a23768400b-0-55529449c72e0d31198b69c72af251b4","member_count":0,"rate":0,"total_distance":0},{"id":14,"name":"珊珊33队🙌","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-25/3-161709-JuJ8G2.jpg%21320w?auth_key=1554094793-69f823a35ce84cfb9247ded609d71dfe-0-c00ece57a569aa0d67b9c87fd6bfd68f","member_count":0,"rate":0,"total_distance":0}]
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
             * logo : http://static.ro-test.xorout.com/team_avatar/2019-03-23/1-203258-wssSBU.jpg%21320w?auth_key=1554094793-b60d768e619b41d99b3bd1dd22be6109-0-c808f879b094f99cbe122d3719ab35fc
             * member_count : 0
             * rate : 0
             * total_distance : 0
             */

            private int id;
            private String name;
            private String logo;
            private int member_count;
            private float rate;
            private int total_distance;
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

            public int getMember_count() {
                return member_count;
            }

            public void setMember_count(int member_count) {
                this.member_count = member_count;
            }

            public float getRate() {
                return rate;
            }

            public void setRate(float rate) {
                this.rate = rate;
            }

            public int getTotal_distance() {
                return total_distance;
            }

            public void setTotal_distance(int total_distance) {
                this.total_distance = total_distance;
            }
        }
    }
}
