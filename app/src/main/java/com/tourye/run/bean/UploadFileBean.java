package com.tourye.run.bean;
/**
 *
 * @ClassName:   UploadFileBean
 *
 * @Author:   along
 *
 * @Description:
 *
 * @CreateDate:   2019/4/12 3:04 PM
 *
 */
public class UploadFileBean {

    /**
     * status : 0
     * timestamp : 1555051536
     * data : {"key":"1/2019-04-12/10-144536-d8snPY.jpg","url":"http://static.ro-test.xorout.com/1/2019-04-12/10-144536-d8snPY.jpg?auth_key=1555055136-d13ddbeda6464e5896b8da131ddf5dd9-0-bf9b7b3e8c95c39ce8fcd59d1dd2c4ec"}
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
         * key : 1/2019-04-12/10-144536-d8snPY.jpg
         * url : http://static.ro-test.xorout.com/1/2019-04-12/10-144536-d8snPY.jpg?auth_key=1555055136-d13ddbeda6464e5896b8da131ddf5dd9-0-bf9b7b3e8c95c39ce8fcd59d1dd2c4ec
         */

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
