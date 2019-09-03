package com.tourye.run.bean;

import java.util.List;

public class TeamSubmitBean {


    /**
     * status : 0
     * timestamp : 1557127067
     * data : {"logo":"xxx","logo_url":"xxx","name":"xxx","city_id":1,"city_name":"xxx","level_id":1,"description":"xxx","group_qr_code":"xxx","group_qr_code_url":"xxx","monitor_qr_code":"xxx","monitor_qr_code_url":"xxx","photos":["xxx","ssss"]}
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
         * logo : xxx
         * logo_url : xxx
         * name : xxx
         * city_id : 1
         * city_name : xxx
         * level_id : 1
         * description : xxx
         * group_qr_code : xxx
         * group_qr_code_url : xxx
         * monitor_qr_code : xxx
         * monitor_qr_code_url : xxx
         * photos : ["xxx","ssss"]
         */

        private String logo;
        private String logo_url;
        private String name;
        private int city_id;
        private String city_name;
        private int level_id;
        private String description;
        private String group_qr_code;
        private String group_qr_code_url;
        private String monitor_qr_code;
        private String monitor_qr_code_url;
        private List<PhotoBean> photos;

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public int getLevel_id() {
            return level_id;
        }

        public void setLevel_id(int level_id) {
            this.level_id = level_id;
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

        public String getGroup_qr_code_url() {
            return group_qr_code_url;
        }

        public void setGroup_qr_code_url(String group_qr_code_url) {
            this.group_qr_code_url = group_qr_code_url;
        }

        public String getMonitor_qr_code() {
            return monitor_qr_code;
        }

        public void setMonitor_qr_code(String monitor_qr_code) {
            this.monitor_qr_code = monitor_qr_code;
        }

        public String getMonitor_qr_code_url() {
            return monitor_qr_code_url;
        }

        public void setMonitor_qr_code_url(String monitor_qr_code_url) {
            this.monitor_qr_code_url = monitor_qr_code_url;
        }

        public List<PhotoBean> getPhotos() {
            return photos;
        }

        public void setPhotos(List<PhotoBean> photos) {
            this.photos = photos;
        }
        public static class PhotoBean{

            private String key;
            private String url;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
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
