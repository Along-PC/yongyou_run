package com.tourye.run.bean;

import java.util.List;

/**
 *
 * @ClassName:   BattleDetailDynamicBean
 *
 * @Author:   along
 *
 * @Description: 战队详情动态实体
 *
 * @CreateDate:   2019/3/19 9:55 AM
 *
 */
public class BattleDetailDynamicBean {


    /**
     * status : 0
     * timestamp : 1552960450
     * data : [{"id":34,"nickname":"黄明","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-18/3-161949-KuSMTu.jpg?auth_key=1552964050-e3759daf9ba14634a87354bf54eb2d36-0-6d50f9a20208f7258f52667f68e4f84d","content":null,"images":[{"thumbnail":"http://static.ro-test.xorout.com/community_photo/2019-03-18/3-183421-XCS93U.jpg%21100h?auth_key=1552964050-5dd472a9722643ba8251d89ac303fb21-0-81bd728e52f2871cf1c9b93aae697fcc","url":"http://static.ro-test.xorout.com/community_photo/2019-03-18/3-183421-XCS93U.jpg?auth_key=1552964050-3b23d329cf944fcebbf3dddb0cbb9db9-0-cd1865089bd49918015c08ee509c9571"}]},{"id":33,"nickname":"黄明","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-18/3-161949-KuSMTu.jpg?auth_key=1552964050-1e88a931bd504b799e8cbb4e699dad8d-0-9a68a042f502e1e96eec658757cfe065","content":"测试","images":[]}]
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
         * id : 34
         * nickname : 黄明
         * avatar : http://static.ro-test.xorout.com/user_avatar/2019-03-18/3-161949-KuSMTu.jpg?auth_key=1552964050-e3759daf9ba14634a87354bf54eb2d36-0-6d50f9a20208f7258f52667f68e4f84d
         * content : null
         * images : [{"thumbnail":"http://static.ro-test.xorout.com/community_photo/2019-03-18/3-183421-XCS93U.jpg%21100h?auth_key=1552964050-5dd472a9722643ba8251d89ac303fb21-0-81bd728e52f2871cf1c9b93aae697fcc","url":"http://static.ro-test.xorout.com/community_photo/2019-03-18/3-183421-XCS93U.jpg?auth_key=1552964050-3b23d329cf944fcebbf3dddb0cbb9db9-0-cd1865089bd49918015c08ee509c9571"}]
         */

        private int id;
        private String nickname;
        private String avatar;
        private String content;
        private List<ImagesBean> images;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean {
            /**
             * thumbnail : http://static.ro-test.xorout.com/community_photo/2019-03-18/3-183421-XCS93U.jpg%21100h?auth_key=1552964050-5dd472a9722643ba8251d89ac303fb21-0-81bd728e52f2871cf1c9b93aae697fcc
             * url : http://static.ro-test.xorout.com/community_photo/2019-03-18/3-183421-XCS93U.jpg?auth_key=1552964050-3b23d329cf944fcebbf3dddb0cbb9db9-0-cd1865089bd49918015c08ee509c9571
             */

            private String thumbnail;
            private String url;

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
