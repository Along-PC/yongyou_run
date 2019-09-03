package com.tourye.run.bean;

import java.util.List;

/**
 *
 * @ClassName:   BattleInfoBean
 *
 * @Author:   along
 *
 * @Description:战队详情实体
 *
 * @CreateDate:   2019/3/18 3:38 PM
 *
 */
public class BattleInfoBean {

    /**
     * status : 0
     * timestamp : 1552894632
     * data : {"name":"健人港健康跑团","logo":"http://static.ro-test.xorout.com/team_logo/2019-03-15-11-46-31/8a82c412c32a4554b68a6f31a4b0844b.png?auth_key=1552898193-6a28fe3335d84528a2a6acac93e6a876-0-584bc57795f5c0dccdcb510329ff462b","monitor_name":"我是苹果测试机没错苹果测试机就是","monitor_avatar":"http://static.ro-test.xorout.com/user_avatar/100002/2019-03-15/112251.jpg?auth_key=1552898193-6caff8ba4bc349f99a161696bc1eec7e-0-3876cb562ce5960d2e2e053c0ffe809c","description":"中国红十字会急救员，百公里挑战者，健康跑团团长，骑行爱好者，越野赛事挑战者，热爱美食制作和分享","photos":[{"thumbnail":"http://static.ro-test.xorout.com/team_photos/2019-03-15-11-51-41/30c5599381d94db38fb80435f4de859f.png?auth_key=1552898193-10586183033246818ba2921d76f6a62f-0-0c488561c392e42eaf3c043a59905362","url":"http://static.ro-test.xorout.com/team_photos/2019-03-15-11-51-41/30c5599381d94db38fb80435f4de859f.png?auth_key=1552898193-12f7c439e7ea4947a968d6e599e39eb4-0-40b12f6f2a0894330997bb0fed0cfbfe"},{"thumbnail":"http://static.ro-test.xorout.com/team_photos/2019-03-15-11-51-45/4ee7afbee94841b8876ffb6f1a1a792c.png?auth_key=1552898193-b9e5cd8d82a44acab214ab7971469722-0-69104abc9f6d02ff59b0d0283aa6f349","url":"http://static.ro-test.xorout.com/team_photos/2019-03-15-11-51-45/4ee7afbee94841b8876ffb6f1a1a792c.png?auth_key=1552898193-e216c1a7c8804b4b8cccd2774fb988bf-0-475693115e03170c8b86708ca1f17e35"},{"thumbnail":"http://static.ro-test.xorout.com/team_photos/2019-03-15-11-51-48/aff47d35f7744dbc92783523e8384746.png?auth_key=1552898193-b8c6c7f912d344bcabc688e874ca8781-0-fc98d3a2ae39eca4408705cbc50f0bf4","url":"http://static.ro-test.xorout.com/team_photos/2019-03-15-11-51-48/aff47d35f7744dbc92783523e8384746.png?auth_key=1552898193-0bf9545a752d4d84b4f0277af0a4c86c-0-2423880cb4e72c89e9a8c2f19f84e2d0"}],"group_qr_code":"http://static.ro-test.xorout.com/team_group_qr_code/2019-03-15-11-47-43/576b2093e6c949fa950878ac6b7003c7.jpg?auth_key=1552898193-75d49bd18cad4f199082ad72c007cacd-0-6876201d7c0283a0f0ca3e90b71cc165","monitor_qr_code":"http://static.ro-test.xorout.com/team_monitor_qr_code/2019-03-15-11-47-50/db91cf045f6a48c6a325e562458fa815.jpg?auth_key=1552898193-5282241f0d5f483091a447505e824035-0-a738b7648a6e8eaa8936bfd79d385043","city":"北京市","distance":1,"member_count":1,"today_sign_in_count":0}
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
         * name : 健人港健康跑团
         * logo : http://static.ro-test.xorout.com/team_logo/2019-03-15-11-46-31/8a82c412c32a4554b68a6f31a4b0844b.png?auth_key=1552898193-6a28fe3335d84528a2a6acac93e6a876-0-584bc57795f5c0dccdcb510329ff462b
         * monitor_name : 我是苹果测试机没错苹果测试机就是
         * monitor_avatar : http://static.ro-test.xorout.com/user_avatar/100002/2019-03-15/112251.jpg?auth_key=1552898193-6caff8ba4bc349f99a161696bc1eec7e-0-3876cb562ce5960d2e2e053c0ffe809c
         * description : 中国红十字会急救员，百公里挑战者，健康跑团团长，骑行爱好者，越野赛事挑战者，热爱美食制作和分享
         * photos : [{"thumbnail":"http://static.ro-test.xorout.com/team_photos/2019-03-15-11-51-41/30c5599381d94db38fb80435f4de859f.png?auth_key=1552898193-10586183033246818ba2921d76f6a62f-0-0c488561c392e42eaf3c043a59905362","url":"http://static.ro-test.xorout.com/team_photos/2019-03-15-11-51-41/30c5599381d94db38fb80435f4de859f.png?auth_key=1552898193-12f7c439e7ea4947a968d6e599e39eb4-0-40b12f6f2a0894330997bb0fed0cfbfe"},{"thumbnail":"http://static.ro-test.xorout.com/team_photos/2019-03-15-11-51-45/4ee7afbee94841b8876ffb6f1a1a792c.png?auth_key=1552898193-b9e5cd8d82a44acab214ab7971469722-0-69104abc9f6d02ff59b0d0283aa6f349","url":"http://static.ro-test.xorout.com/team_photos/2019-03-15-11-51-45/4ee7afbee94841b8876ffb6f1a1a792c.png?auth_key=1552898193-e216c1a7c8804b4b8cccd2774fb988bf-0-475693115e03170c8b86708ca1f17e35"},{"thumbnail":"http://static.ro-test.xorout.com/team_photos/2019-03-15-11-51-48/aff47d35f7744dbc92783523e8384746.png?auth_key=1552898193-b8c6c7f912d344bcabc688e874ca8781-0-fc98d3a2ae39eca4408705cbc50f0bf4","url":"http://static.ro-test.xorout.com/team_photos/2019-03-15-11-51-48/aff47d35f7744dbc92783523e8384746.png?auth_key=1552898193-0bf9545a752d4d84b4f0277af0a4c86c-0-2423880cb4e72c89e9a8c2f19f84e2d0"}]
         * group_qr_code : http://static.ro-test.xorout.com/team_group_qr_code/2019-03-15-11-47-43/576b2093e6c949fa950878ac6b7003c7.jpg?auth_key=1552898193-75d49bd18cad4f199082ad72c007cacd-0-6876201d7c0283a0f0ca3e90b71cc165
         * monitor_qr_code : http://static.ro-test.xorout.com/team_monitor_qr_code/2019-03-15-11-47-50/db91cf045f6a48c6a325e562458fa815.jpg?auth_key=1552898193-5282241f0d5f483091a447505e824035-0-a738b7648a6e8eaa8936bfd79d385043
         * city : 北京市
         * distance : 1
         * member_count : 1
         * today_sign_in_count : 0
         */
        // 以下内容仅在查看自己加入的队伍时返回
//            "today_sign_in_count":  12,
//            "group_qr_code":  "xxx",
//            "monitor_qr_code":  "xxx",

        private String name;
        private String logo;
        private String monitor_name;
        private String monitor_avatar;
        private String description;
        private String group_qr_code;
        private String monitor_qr_code;
        private String city;
        private int distance;
        private int member_count;
        private int today_sign_in_count;
        private List<PhotosBean> photos;

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

        public String getMonitor_name() {
            return monitor_name;
        }

        public void setMonitor_name(String monitor_name) {
            this.monitor_name = monitor_name;
        }

        public String getMonitor_avatar() {
            return monitor_avatar;
        }

        public void setMonitor_avatar(String monitor_avatar) {
            this.monitor_avatar = monitor_avatar;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getGroup_qr_code() {
            return group_qr_code;
        }

        public void setGroup_qr_code(String group_qr_code) {
            this.group_qr_code = group_qr_code;
        }

        public String getMonitor_qr_code() {
            return monitor_qr_code;
        }

        public void setMonitor_qr_code(String monitor_qr_code) {
            this.monitor_qr_code = monitor_qr_code;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getMember_count() {
            return member_count;
        }

        public void setMember_count(int member_count) {
            this.member_count = member_count;
        }

        public int getToday_sign_in_count() {
            return today_sign_in_count;
        }

        public void setToday_sign_in_count(int today_sign_in_count) {
            this.today_sign_in_count = today_sign_in_count;
        }

        public List<PhotosBean> getPhotos() {
            return photos;
        }

        public void setPhotos(List<PhotosBean> photos) {
            this.photos = photos;
        }

        public static class PhotosBean {
            /**
             * thumbnail : http://static.ro-test.xorout.com/team_photos/2019-03-15-11-51-41/30c5599381d94db38fb80435f4de859f.png?auth_key=1552898193-10586183033246818ba2921d76f6a62f-0-0c488561c392e42eaf3c043a59905362
             * url : http://static.ro-test.xorout.com/team_photos/2019-03-15-11-51-41/30c5599381d94db38fb80435f4de859f.png?auth_key=1552898193-12f7c439e7ea4947a968d6e599e39eb4-0-40b12f6f2a0894330997bb0fed0cfbfe
             */

            private String thumbnail;
            private String url;

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
