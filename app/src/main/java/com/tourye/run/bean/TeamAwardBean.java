package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   TeamAwardBean
 *
 * @Author:   along
 *
 * @Description: 队伍管理---队伍奖励实体
 *
 * @CreateDate:   2019/3/26 2:27 PM
 *
 */
public class TeamAwardBean {

    /**
     * status : 0
     * timestamp : 1553581580
     * data : [{"member_count":10,"name":"战队最终满10人","image":"http://static.ro-test.xorout.com/activity_team_prize/2019-03-22-09-45-08/6f0af6689940423abec845467c58380d.jpg?auth_key=1553585180-3e258315d796447aafbfa6392778dee3-0-75e0715c41d168b384d059ae235d34f1","value":10000},{"member_count":30,"name":"战队最终满30人","image":"http://static.ro-test.xorout.com/activity_team_prize/2019-03-22-09-45-29/787d8385a36540c1b3825eb6538a0254.jpg?auth_key=1553585180-68a2863a85dc4d19a507536a99a48443-0-9474a5f4015485c742dcea5c16087f9b","value":30000},{"member_count":50,"name":"战队最终满50人","image":"http://static.ro-test.xorout.com/activity_team_prize/2019-03-22-09-45-44/526ea31c51214a2fb6f36d56fcb19eae.jpg?auth_key=1553585180-fd5cda3c27694ab2b4e0dd67c68fce90-0-fb8fc662e1fd97e4692ab6862d370f72","value":50000},{"member_count":70,"name":"战队最终满70人","image":"http://static.ro-test.xorout.com/activity_team_prize/2019-03-22-09-46-04/504abd6929dc424b903a860467cbc298.jpg?auth_key=1553585180-b943e399326b4db29ebbf28776834609-0-075357e8f6d03e2a84193cea58afd9ee","value":70000},{"member_count":100,"name":"战队最终满百<br>(完赛率80%后)","image":"http://static.ro-test.xorout.com/activity_team_prize/2019-03-22-09-46-47/ab418326c9ca45eeb26983f67378f886.jpg?auth_key=1553585180-e7425ca40941402da1280f33573f43c0-0-67c95136c6fdb7a054ab6263c236c920","value":100000},{"member_count":300,"name":"满百队伍3支<br>(完赛率80%后)","image":"http://static.ro-test.xorout.com/activity_team_prize/2019-03-22-09-47-17/f5d5054f3876485b9fe7181c3134beb8.jpg?auth_key=1553585180-3bc608dd02d54ff9b0e64d1c479a5759-0-3a388e20ae2b1a12a66642d4ae497509","value":300000}]
     */

    private int status;
    private int timestamp;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * member_count : 10
         * name : 战队最终满10人
         * image : http://static.ro-test.xorout.com/activity_team_prize/2019-03-22-09-45-08/6f0af6689940423abec845467c58380d.jpg?auth_key=1553585180-3e258315d796447aafbfa6392778dee3-0-75e0715c41d168b384d059ae235d34f1
         * value : 10000
         */

        private int member_count;
        private String name;
        private String image;
        private int value;
        private String link;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getMember_count() {
            return member_count;
        }

        public void setMember_count(int member_count) {
            this.member_count = member_count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
