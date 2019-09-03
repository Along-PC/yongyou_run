package com.tourye.run.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName:   DynamicDetailBean
 *
 * @Author:   along
 *
 * @Description:    动态详情实体
 *
 * @CreateDate:   2019/4/11 4:28 PM
 *
 */
public class DynamicDetailBean {

    /**
     * status : 0
     * timestamp : 1554970867
     * data : {"id":96,"user_id":6,"avatar":"http://static.ro-test.xorout.com/user_avatar/100006/2019-03-23/152230.jpg?auth_key=1554974467-a7add88a119b4babb5eb3015b0fbe60f-0-37210f53a1f119f81e0d687eec9f03c1","nickname":"小小","content":null,"images":["http://static.ro-test.xorout.com/community_photo/2019-04-10/6-104918-eG2T5f.jpg?auth_key=1554974467-37da497c3f0c4b5f9c9f8438a4a32b3f-0-6d7cdd990228b665fba1190cd6dbe71e"],"like_count":3,"comment_count":14,"already_like":true,"position":null,"create_time":"2019-04-10 10:49:19","total_days":1,"sign_in_data":{"image":"http://static.ro-test.xorout.com/sign_in/2019-04-10/6-104858-h5pYav.jpg?auth_key=1554974467-b81a0e096b4645a4a8751f4392febddc-0-ed88209c43afd02ff5513e227bc1105a","distance":"3.5","time":60}}
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
         * id : 96
         * user_id : 6
         * avatar : http://static.ro-test.xorout.com/user_avatar/100006/2019-03-23/152230.jpg?auth_key=1554974467-a7add88a119b4babb5eb3015b0fbe60f-0-37210f53a1f119f81e0d687eec9f03c1
         * nickname : 小小
         * content : null
         * images : ["http://static.ro-test.xorout.com/community_photo/2019-04-10/6-104918-eG2T5f.jpg?auth_key=1554974467-37da497c3f0c4b5f9c9f8438a4a32b3f-0-6d7cdd990228b665fba1190cd6dbe71e"]
         * like_count : 3
         * comment_count : 14
         * already_like : true
         * position : null
         * create_time : 2019-04-10 10:49:19
         * total_days : 1
         * sign_in_data : {"image":"http://static.ro-test.xorout.com/sign_in/2019-04-10/6-104858-h5pYav.jpg?auth_key=1554974467-b81a0e096b4645a4a8751f4392febddc-0-ed88209c43afd02ff5513e227bc1105a","distance":"3.5","time":60}
         */

        private int id;
        private int user_id;
        private String avatar;
        private String nickname;
        private String content;
        private int like_count;
        private int comment_count;
        private boolean already_like;
        private String position;
        private String create_time;
        private int total_days;
        private SignInDataBean sign_in_data;
        private ArrayList<String> images;

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getLike_count() {
            return like_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public boolean isAlready_like() {
            return already_like;
        }

        public void setAlready_like(boolean already_like) {
            this.already_like = already_like;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getTotal_days() {
            return total_days;
        }

        public void setTotal_days(int total_days) {
            this.total_days = total_days;
        }

        public SignInDataBean getSign_in_data() {
            return sign_in_data;
        }

        public void setSign_in_data(SignInDataBean sign_in_data) {
            this.sign_in_data = sign_in_data;
        }

        public ArrayList<String> getImages() {
            return images;
        }

        public void setImages(ArrayList<String> images) {
            this.images = images;
        }

        public static class SignInDataBean {
            /**
             * image : http://static.ro-test.xorout.com/sign_in/2019-04-10/6-104858-h5pYav.jpg?auth_key=1554974467-b81a0e096b4645a4a8751f4392febddc-0-ed88209c43afd02ff5513e227bc1105a
             * distance : 3.5
             * time : 60
             */

            private String image;
            private String distance;
            private int time;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }
        }
    }
}
