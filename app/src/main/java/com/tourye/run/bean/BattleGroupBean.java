package com.tourye.run.bean;

import java.util.List;

/**
 *
 * @ClassName:   BattleGroupBean
 *
 * @Author:   along
 *
 * @Description: 战队组别列表实体
 *
 * @CreateDate:   2019/3/15 3:48 PM
 *
 */
public class BattleGroupBean {


    /**
     * status : 0
     * timestamp : 1552636114
     * data : [{"id":1,"name":"1KM","distance":"1.00","recommend":false},{"id":2,"name":"2KM","distance":"2.00","recommend":false},{"id":3,"name":"3KM","distance":"3.00","recommend":false},{"id":4,"name":"5KM","distance":"5.00","recommend":false},{"id":5,"name":"10KM","distance":"10.00","recommend":false}]
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
         * id : 1
         * name : 1KM
         * distance : 1.00
         * recommend : false
         */

        private int id;
        private String name;
        private String distance;
        private boolean recommend;
        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
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

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public boolean isRecommend() {
            return recommend;
        }

        public void setRecommend(boolean recommend) {
            this.recommend = recommend;
        }
    }
}
