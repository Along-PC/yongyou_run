package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   AlreadyEffectiveBean
 *
 * @Author:   along
 *
 * @Description:    已经提取生效的奖金实体
 *
 * @CreateDate:   2019/4/23 4:25 PM
 *
 */
public class AlreadyEffectiveBean {

    /**
     * status : 0
     * timestamp : 1556005499
     * data : [{"type":"xxx","description":"xxx","count":12300}]
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
         * type : xxx
         * description : xxx
         * count : 12300
         */

        private String type;
        private String description;
        private int count;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
