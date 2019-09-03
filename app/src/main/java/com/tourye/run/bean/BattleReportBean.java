package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   BattleReportBean
 *
 * @Author:   along
 *
 * @Description:战报列表实体
 *
 * @CreateDate:   2019/3/15 2:29 PM
 *
 */
public class BattleReportBean {

    /**
     * status : 0
     * timestamp : 1552631312
     * data : [{"id":11,"title":"这是第一条人工填写的战报","link":"https://www.baidu.com/","time":"2019-03-15"},{"id":10,"title":"不跑就出局第10届百日跑3月26日上午8点正式开放报名","link":null,"time":"2019-03-15"},{"id":9,"title":"【第1轮中奖】恭喜跑向远方贵溪战队 农中 ！","link":null,"time":"2019-03-15"},{"id":8,"title":"第2位幸运跑友已经诞生！恭喜[翟康惠]获得价值3499元的军拓户外运动智能腕表，战狼Ⅱ吴京同款！！","link":null,"time":"2019-03-15"}]
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
         * id : 11
         * title : 这是第一条人工填写的战报
         * link : https://www.baidu.com/
         * time : 2019-03-15
         */

        private int id;
        private String title;
        private String link;
        private String time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
