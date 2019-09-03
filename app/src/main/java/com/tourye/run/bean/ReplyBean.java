package com.tourye.run.bean;

import java.util.List;

/**
 * Created by longlongren on 2018/11/1.
 * <p>
 * introduce:发现---动态回复实体
 */

public class ReplyBean {

    /**
     * status : 0
     * timestamp : 1541051317
     * data : [{"id":19,"user_id":10,"nickname":"晨晨-不起就出局app","avatar":"http://thirdwx.qlogo.cn/mmopen/XvJev9ib59MXcPGRa6kSc1MW4gic5nzcicJSblsPicsMibhG3ozjOXAFGpnpXoyrhibE3zL6NMc7E0OXtcmyWn200UxsP2dhJglabD/132","content":"好漂亮","reply_count":47,"replies":[{"id":93,"user_id":15,"nickname":"袁晓","content":"15","reply_to":"晨晨-不起就出局app"},{"id":92,"user_id":15,"nickname":"袁晓","content":"15","reply_to":"晨晨-不起就出局app"}]}]
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
         * id : 19
         * user_id : 10
         * nickname : 晨晨-不起就出局app
         * avatar : http://thirdwx.qlogo.cn/mmopen/XvJev9ib59MXcPGRa6kSc1MW4gic5nzcicJSblsPicsMibhG3ozjOXAFGpnpXoyrhibE3zL6NMc7E0OXtcmyWn200UxsP2dhJglabD/132
         * content : 好漂亮
         * reply_count : 47
         * replies : [{"id":93,"user_id":15,"nickname":"袁晓","content":"15","reply_to":"晨晨-不起就出局app"},{"id":92,"user_id":15,"nickname":"袁晓","content":"15","reply_to":"晨晨-不起就出局app"}]
         */

        private int id;
        private int user_id;
        private String nickname;
        private String avatar;
        private String content;
        private int reply_count;
        private List<ReplyEntity> replies;
        private int remainder_count;//剩余回复数
        private String create_time;
        private int last_reply_id;//最后一条回复的id

        public int getLast_reply_id() {
            return last_reply_id;
        }

        public void setLast_reply_id(int last_reply_id) {
            this.last_reply_id = last_reply_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getRemainder_count() {
            return remainder_count;
        }

        public void setRemainder_count(int remainder_count) {
            this.remainder_count = remainder_count;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getReply_count() {
            return reply_count;
        }

        public void setReply_count(int reply_count) {
            this.reply_count = reply_count;
        }

        public List<ReplyEntity> getReplies() {
            return replies;
        }

        public void setReplies(List<ReplyEntity> replies) {
            this.replies = replies;
        }
    }
}
