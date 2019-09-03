package com.tourye.run.bean;
/**
 *
 * @ClassName:   RunnerInfoBean
 *
 * @Author:   along
 *
 * @Description:    用户个人基本信息实体
 *
 * @CreateDate:   2019/7/3 5:35 PM
 *
 */
public class RunnerInfoBean {

    /**
     * status : 0
     * timestamp : 1562146483
     * data : {"name":"adasdas","gender":"male","age":"26","mobile":"17610823875","district_1_id":1,"district_2_id":0,"district_3_id":3,"address":"wqeqweqweqweq","nickname":"daxia酷睿哟","avatar":"1/2019-07-03/10-172839-4NkYLw.jpg","avatar_url":"http://static.ro-test.xorout.com/1/2019-07-03/10-172839-4NkYLw.jpg%21200h"}
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
         * name : adasdas
         * gender : male
         * age : 26
         * mobile : 17610823875
         * district_1_id : 1
         * district_2_id : 0
         * district_3_id : 3
         * address : wqeqweqweqweq
         * nickname : daxia酷睿哟
         * avatar : 1/2019-07-03/10-172839-4NkYLw.jpg
         * avatar_url : http://static.ro-test.xorout.com/1/2019-07-03/10-172839-4NkYLw.jpg%21200h
         */

        private String name;
        private String gender;
        private String age;
        private String mobile;
        private int district_1_id;
        private int district_2_id;
        private int district_3_id;
        private String address;
        private String nickname;
        private String avatar;
        private String avatar_url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getDistrict_1_id() {
            return district_1_id;
        }

        public void setDistrict_1_id(int district_1_id) {
            this.district_1_id = district_1_id;
        }

        public int getDistrict_2_id() {
            return district_2_id;
        }

        public void setDistrict_2_id(int district_2_id) {
            this.district_2_id = district_2_id;
        }

        public int getDistrict_3_id() {
            return district_3_id;
        }

        public void setDistrict_3_id(int district_3_id) {
            this.district_3_id = district_3_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }
    }
}
