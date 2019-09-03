package com.tourye.run.bean;
/**
 *
 * @ClassName:   ParseLocationBean
 *
 * @Author:   along
 *
 * @Description:    经纬度实体
 *
 * @CreateDate:   2019/5/23 1:32 PM
 *
 */
public class ParseLocationBean {

    /**
     * status : 0
     * timestamp : 1558589501
     * data : {"id":1,"name":"北京市"}
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
         * id : 1
         * name : 北京市
         */

        private int id;
        private String name;

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
    }
}
