package com.tourye.run.bean;

import java.util.List;

/**
 *
 * @ClassName:   MessageCommentBean
 *
 * @Author:   along
 *
 * @Description:    评论消息通知实体
 *
 * @CreateDate:   2019/4/3 2:16 PM
 *
 */
public class MessageCommentBean {

    /**
     * status : 0
     * timestamp : 1554272156
     * data : [{"id":135,"type":"reply","reply_id":45,"moment_id":78,"user_id":10,"nickname":"阳光","reply_nickname":"阳光","avatar":"http://static.ro-test.xorout.com/user_avatar/10000A/2019-03-23/153708.jpg?auth_key=1554275756-c9cf9d98b36e481c9e5a29e19a71ba35-0-6b3ff888980476cc1dd8346f2c87070b","moment_content":"厉害了","reply_content":"dasdasd","image":null,"create_time":"2019-04-03 10:32:29"},{"id":134,"type":"reply","reply_id":43,"moment_id":78,"user_id":10,"nickname":"阳光","reply_nickname":"阳光","avatar":"http://static.ro-test.xorout.com/user_avatar/10000A/2019-03-23/153708.jpg?auth_key=1554275756-83eab3d8b2a1445aa7272cab2245b9ca-0-99977580fda3bb6cce48e2d9c79ed87c","moment_content":"厉害了","reply_content":"asdasda","image":null,"create_time":"2019-04-03 10:32:04"},{"id":129,"type":"reply","reply_id":42,"moment_id":77,"user_id":10,"nickname":"阳光","reply_nickname":"阳光","avatar":"http://static.ro-test.xorout.com/user_avatar/10000A/2019-03-23/153708.jpg?auth_key=1554275756-a1e7c8874cd74586aadb6082a1e167dc-0-a72bcbe135c1e67e833cc9aab0a11934","moment_content":"春光正好","reply_content":"dasdas","image":null,"create_time":"2019-04-03 10:20:57"}]
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
         * id : 135
         * type : reply
         * reply_id : 45
         * moment_id : 78
         * user_id : 10
         * nickname : 阳光
         * reply_nickname : 阳光
         * avatar : http://static.ro-test.xorout.com/user_avatar/10000A/2019-03-23/153708.jpg?auth_key=1554275756-c9cf9d98b36e481c9e5a29e19a71ba35-0-6b3ff888980476cc1dd8346f2c87070b
         * moment_content : 厉害了
         * reply_content : dasdasd
         * image : null
         * create_time : 2019-04-03 10:32:29
         */

        private int id;
        private String type;
        private int reply_id;
        private int moment_id;
        private int user_id;
        private String nickname;
        private String reply_nickname;
        private String avatar;
        private String moment_content;
        private String reply_content;
        private String comment_content;
        private List<String> image;
        private String create_time;

        public String getComment_content() {
            return comment_content;
        }

        public void setComment_content(String comment_content) {
            this.comment_content = comment_content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getReply_id() {
            return reply_id;
        }

        public void setReply_id(int reply_id) {
            this.reply_id = reply_id;
        }

        public int getMoment_id() {
            return moment_id;
        }

        public void setMoment_id(int moment_id) {
            this.moment_id = moment_id;
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

        public String getReply_nickname() {
            return reply_nickname;
        }

        public void setReply_nickname(String reply_nickname) {
            this.reply_nickname = reply_nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getMoment_content() {
            return moment_content;
        }

        public void setMoment_content(String moment_content) {
            this.moment_content = moment_content;
        }

        public String getReply_content() {
            return reply_content;
        }

        public void setReply_content(String reply_content) {
            this.reply_content = reply_content;
        }

        public List<String> getImage() {
            return image;
        }

        public void setImage(List<String> image) {
            this.image = image;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
