package com.tourye.run.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by longlongren on 2018/10/31.
 * <p>
 * introduce:发表动态列表实体
 */

public class DynamicListBean implements Serializable {

    /**
     * status : 0
     * timestamp : 1540955080
     * data : [{"id":20,"user_id":10,"avatar":"http://thirdwx.qlogo.cn/mmopen/XvJev9ib59MXcPGRa6kSc1MW4gic5nzcicJSblsPicsMibhG3ozjOXAFGpnpXoyrhibE3zL6NMc7E0OXtcmyWn200UxsP2dhJglabD/132","nickname":"晨晨-不起就出局app","content":null,"images":["https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/9b1bdf8f-43ef-4fb3-997d-8f90b0c79c16/12cb5fc41ad542229b4d37b4c150a288"],"like_count":2,"comment_count":0,"already_like":false},{"id":19,"user_id":10,"avatar":"http://thirdwx.qlogo.cn/mmopen/XvJev9ib59MXcPGRa6kSc1MW4gic5nzcicJSblsPicsMibhG3ozjOXAFGpnpXoyrhibE3zL6NMc7E0OXtcmyWn200UxsP2dhJglabD/132","nickname":"晨晨-不起就出局app","content":null,"images":["https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/9b1bdf8f-43ef-4fb3-997d-8f90b0c79c16/09d5a6b688804a2cadc68fe062ca8d43"],"like_count":2,"comment_count":0,"already_like":false},{"id":18,"user_id":10,"avatar":"http://thirdwx.qlogo.cn/mmopen/XvJev9ib59MXcPGRa6kSc1MW4gic5nzcicJSblsPicsMibhG3ozjOXAFGpnpXoyrhibE3zL6NMc7E0OXtcmyWn200UxsP2dhJglabD/132","nickname":"晨晨-不起就出局app","content":null,"images":["https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/9b1bdf8f-43ef-4fb3-997d-8f90b0c79c16/d90e88845a8f48ecbc9536902e64d11f"],"like_count":0,"comment_count":0,"already_like":false},{"id":17,"user_id":10,"avatar":"http://thirdwx.qlogo.cn/mmopen/XvJev9ib59MXcPGRa6kSc1MW4gic5nzcicJSblsPicsMibhG3ozjOXAFGpnpXoyrhibE3zL6NMc7E0OXtcmyWn200UxsP2dhJglabD/132","nickname":"晨晨-不起就出局app","content":null,"images":["https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/9b1bdf8f-43ef-4fb3-997d-8f90b0c79c16/5a33b9b853504824a9ddc6cfad7c6af9"],"like_count":0,"comment_count":0,"already_like":false},{"id":16,"user_id":10,"avatar":"http://thirdwx.qlogo.cn/mmopen/XvJev9ib59MXcPGRa6kSc1MW4gic5nzcicJSblsPicsMibhG3ozjOXAFGpnpXoyrhibE3zL6NMc7E0OXtcmyWn200UxsP2dhJglabD/132","nickname":"晨晨-不起就出局app","content":null,"images":["https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/9b1bdf8f-43ef-4fb3-997d-8f90b0c79c16/3afc87cd1e9d4bea85d6ba9fe0fb722e"],"like_count":2,"comment_count":1,"already_like":false},{"id":15,"user_id":10,"avatar":"http://thirdwx.qlogo.cn/mmopen/XvJev9ib59MXcPGRa6kSc1MW4gic5nzcicJSblsPicsMibhG3ozjOXAFGpnpXoyrhibE3zL6NMc7E0OXtcmyWn200UxsP2dhJglabD/132","nickname":"晨晨-不起就出局app","content":"斤斤计较几节课","images":["https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/9b1bdf8f-43ef-4fb3-997d-8f90b0c79c16/b52fdc82cdb6448c99ff4136f305f0e0","https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/9b1bdf8f-43ef-4fb3-997d-8f90b0c79c16/8cca88e4fd5c4053be55b4810053c352","https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/9b1bdf8f-43ef-4fb3-997d-8f90b0c79c16/3138fbbd77c94abb90fdca44fb4e3c72"],"like_count":1,"comment_count":2,"already_like":false},{"id":13,"user_id":3,"avatar":"http://thirdwx.qlogo.cn/mmopen/2bv1RSicOuFkXhnph8XPctNlu8Z57a6dwZ8pbK6aJ54UjjYJVNYgsgDiaEIJnVBzylGMTbXrSa9VGzeRexEeQoCPQe4Pg8ogkw/132","nickname":"见一滴墨水的蓝","content":"来来来","images":["https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/39d1c18a-919d-4c38-970f-88864adab964/0ae24d458c714c8593dbbe885f0b98b3","https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/39d1c18a-919d-4c38-970f-88864adab964/9cf4fde2f8c24c6caed06f42b89ef97b","https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/39d1c18a-919d-4c38-970f-88864adab964/ac3243859e914d7cbce1bb7a755ba8fe"],"like_count":1,"comment_count":1,"already_like":false},{"id":12,"user_id":17,"avatar":"http://thirdwx.qlogo.cn/mmopen/BfNQn5ptpOV3wUlibRH72VQhnphlRicKKhDUwS88304A61qgFwC3T6icRNkzJeZjpccphgc8heLCbZialWCQ8jQKfWZvZIjzDAZI/132","nickname":"在这里看到自己曾经的美好时光不想","content":"对于人际关系，我逐渐总结出一个最合乎我的性情的原则就是互相尊重，亲疏随缘。","images":["https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/fc19ec20-1ef8-42e3-9370-d33f29c23e27/d33586ac9e40468b810c4d439a0048ff","https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/fc19ec20-1ef8-42e3-9370-d33f29c23e27/8cf8b12729ea414d92e9559481c2b327","https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/fc19ec20-1ef8-42e3-9370-d33f29c23e27/03dbaeb45e744106b3bdba98f83757ad","https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/fc19ec20-1ef8-42e3-9370-d33f29c23e27/2e6482111bcf41cabd9ac386f0506b10","https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/fc19ec20-1ef8-42e3-9370-d33f29c23e27/74a05936ff7b414ca9080a1d0ef388d4","https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/fc19ec20-1ef8-42e3-9370-d33f29c23e27/59a7a15e85764fe7b83b0cb9cd4507ab","https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/fc19ec20-1ef8-42e3-9370-d33f29c23e27/35d2362027ef4767bb8ffd92e25a243f","https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/fc19ec20-1ef8-42e3-9370-d33f29c23e27/95335a8f4a1040cda28d9ddfb8dafe42","https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/fc19ec20-1ef8-42e3-9370-d33f29c23e27/d2ef350d836244bc8afa4a3d3281e939"],"like_count":3,"comment_count":6,"already_like":false},{"id":10,"user_id":10,"avatar":"http://thirdwx.qlogo.cn/mmopen/XvJev9ib59MXcPGRa6kSc1MW4gic5nzcicJSblsPicsMibhG3ozjOXAFGpnpXoyrhibE3zL6NMc7E0OXtcmyWn200UxsP2dhJglabD/132","nickname":"晨晨-不起就出局app","content":"群","images":[],"like_count":0,"comment_count":1,"already_like":false},{"id":9,"user_id":10,"avatar":"http://thirdwx.qlogo.cn/mmopen/XvJev9ib59MXcPGRa6kSc1MW4gic5nzcicJSblsPicsMibhG3ozjOXAFGpnpXoyrhibE3zL6NMc7E0OXtcmyWn200UxsP2dhJglabD/132","nickname":"晨晨-不起就出局app","content":"色同意法国红酒付看看赶紧看看飞机哈哈非凡哥夫君君仿古街刚刚还好吧","images":[],"like_count":0,"comment_count":0,"already_like":false}]
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

    public static class DataBean implements Serializable {
        /**
         * id : 20
         * user_id : 10
         * avatar : http://thirdwx.qlogo.cn/mmopen/XvJev9ib59MXcPGRa6kSc1MW4gic5nzcicJSblsPicsMibhG3ozjOXAFGpnpXoyrhibE3zL6NMc7E0OXtcmyWn200UxsP2dhJglabD/132
         * nickname : 晨晨-不起就出局app
         * content : null
         * images : ["https://ro-test.oss-cn-beijing.aliyuncs.com/image/2018-10-15/9b1bdf8f-43ef-4fb3-997d-8f90b0c79c16/12cb5fc41ad542229b4d37b4c150a288"]
         * like_count : 2
         * comment_count : 0
         * already_like : false
         */

        private int id;
        private int user_id;
        private String avatar;
        private String nickname;
        private String content;
        private int like_count;
        private int comment_count;
        private boolean already_like;
        private ArrayList<String> images;
        private ArrayList<String> image_thumbnails;
        private String create_time;
        private int content_state;//文字显示状态  0未设置 1显示  2隐藏  3文字内容未超越限制

        private String position;
        private int total_days;
        private SignInDataBean sign_in_data;
        private boolean stick;//是否置顶

        public boolean isStick() {
            return stick;
        }

        public void setStick(boolean stick) {
            this.stick = stick;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
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

        public int getContent_state() {
            return content_state;
        }

        public void setContent_state(int content_state) {
            this.content_state = content_state;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
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

        public ArrayList<String> getImages() {
            return images;
        }

        public void setImages(ArrayList<String> images) {
            this.images = images;
        }

        public ArrayList<String> getImage_thumbnails() {
            return image_thumbnails;
        }

        public void setImage_thumbnails(ArrayList<String> image_thumbnails) {
            this.image_thumbnails = image_thumbnails;
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
