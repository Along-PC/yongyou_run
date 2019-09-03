package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   MessageThumbBean
 *
 * @Author:   along
 *
 * @Description:   消息通知点赞实体
 *
 * @CreateDate:   2019/4/3 4:06 PM
 *
 */
public class MessageThumbBean {

    /**
     * status : 0
     * timestamp : 1554278305
     * data : [{"like_id":61,"moment_id":82,"nickname":"设置名字","avatar":"http://static.ro-test.xorout.com/user_avatar/10000W/2019-03-28/094019.jpg?auth_key=1554281905-66c2085b2a6144e99f80aaf378d6c5cd-0-b042e46586f235bd81f59e17f53ff657","content":"QQ","image":["http://static.ro-test.xorout.com/community_photo/2019-04-03/10-155119-na7qhf.jpg?auth_key=1554281905-845cfe58bf7d4c5f9082b7c4f71098f3-0-533d729b0efa6e4bd7f5120a94ddc2a5"],"created_at":"2019-04-03 15:58:17"},{"like_id":59,"moment_id":81,"nickname":"设置名字","avatar":"http://static.ro-test.xorout.com/user_avatar/10000W/2019-03-28/094019.jpg?auth_key=1554281905-155f8f2eba5a41929b87528df66e962b-0-c39e8d8286c09049e5f8640a3fe561d1","content":"比较紧","image":null,"created_at":"2019-04-03 15:51:20"}]
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
         * like_id : 61
         * moment_id : 82
         * nickname : 设置名字
         * avatar : http://static.ro-test.xorout.com/user_avatar/10000W/2019-03-28/094019.jpg?auth_key=1554281905-66c2085b2a6144e99f80aaf378d6c5cd-0-b042e46586f235bd81f59e17f53ff657
         * content : QQ
         * image : ["http://static.ro-test.xorout.com/community_photo/2019-04-03/10-155119-na7qhf.jpg?auth_key=1554281905-845cfe58bf7d4c5f9082b7c4f71098f3-0-533d729b0efa6e4bd7f5120a94ddc2a5"]
         * created_at : 2019-04-03 15:58:17
         */

        private int like_id;
        private int moment_id;
        private String nickname;
        private String avatar;
        private String content;
        private String created_at;
        private List<String> image;

        public int getLike_id() {
            return like_id;
        }

        public void setLike_id(int like_id) {
            this.like_id = like_id;
        }

        public int getMoment_id() {
            return moment_id;
        }

        public void setMoment_id(int moment_id) {
            this.moment_id = moment_id;
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

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public List<String> getImage() {
            return image;
        }

        public void setImage(List<String> image) {
            this.image = image;
        }
    }
}
