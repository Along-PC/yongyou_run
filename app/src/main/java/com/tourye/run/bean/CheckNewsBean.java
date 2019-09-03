package com.tourye.run.bean;

import java.io.Serializable;

/**
 *
 * @ClassName:   CheckNewsBean
 *
 * @Author:   along
 *
 * @Description:    检查是否有最新消息实体
 *
 * @CreateDate:   2019/4/3 11:37 AM
 *
 */
public class CheckNewsBean implements Serializable {

    /**
     * status : 0
     * timestamp : 1554262611
     * data : {"notice":true,"reply":true,"like":false}
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

    public static class DataBean implements Serializable {
        /**
         * notice : true
         * reply : true
         * like : false
         */

        private boolean notice;
        private boolean reply;
        private boolean like;

        public boolean isNotice() {
            return notice;
        }

        public void setNotice(boolean notice) {
            this.notice = notice;
        }

        public boolean isReply() {
            return reply;
        }

        public void setReply(boolean reply) {
            this.reply = reply;
        }

        public boolean isLike() {
            return like;
        }

        public void setLike(boolean like) {
            this.like = like;
        }
    }
}
