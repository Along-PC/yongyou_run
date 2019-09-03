package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   PersonThumbRankBean
 *
 * @Author:   along
 *
 * @Description:    个人点赞排行榜实体
 *
 * @CreateDate:   2019/4/1 5:02 PM
 *
 */
public class PersonThumbRankBean {

    /**
     * status : 0
     * timestamp : 1554109212
     * data : {"current":{"nickname":"阳光","avatar":"http://static.ro-test.xorout.com/user_avatar/10000A/2019-03-23/153708.jpg%21320w?auth_key=1554112812-c5d1f41c431d48fd9b54211efe68a8f5-0-9cf46639694d8fa3d1f37b1ff5356beb","thumb_up_count":0,"rank":12,"has_thumb_up":false},"list":[{"id":7,"nickname":"于湛","avatar":"http://static.ro-test.xorout.com/user_avatar/100007/2019-03-23/152448.jpg%21320w?auth_key=1554112812-970224936f30402cbd4d5f04472d0c9a-0-e425fdd041bde7026e740d9c7f6d1a29","thumb_up_count":0,"has_thumb_up":false},{"id":6,"nickname":"小小","avatar":"http://static.ro-test.xorout.com/user_avatar/100006/2019-03-23/152230.jpg%21320w?auth_key=1554112812-4260c54552eb4773a862173225584d59-0-40c0bac3251124e0f474c13b2f3645a9","thumb_up_count":0,"has_thumb_up":false},{"id":35,"nickname":"a.oY","avatar":"http://static.ro-test.xorout.com/user_avatar/10000Z/2019-03-28/152847.jpg%21320w?auth_key=1554112812-99f23d6ba4c34be881a9b1e35f06d5b6-0-b51e744cef6a739b19adc93d8c324184","thumb_up_count":0,"has_thumb_up":false},{"id":34,"nickname":"兔子\u2019susie","avatar":"http://static.ro-test.xorout.com/user_avatar/10000Y/2019-03-28/150440.jpg%21320w?auth_key=1554112812-2b75c68e50494bdd8d3c0229937838ee-0-0ec4114d367a4336fcd8f262a23931c6","thumb_up_count":0,"has_thumb_up":false},{"id":32,"nickname":"设置名字","avatar":"http://static.ro-test.xorout.com/user_avatar/10000W/2019-03-28/094019.jpg%21320w?auth_key=1554112812-b429ae1345f14446881ea6d46be0f4cb-0-f0758b1b68c20d3378b0bbb6327606b5","thumb_up_count":0,"has_thumb_up":false},{"id":3,"nickname":"见一滴墨水的蓝见一滴墨水的蓝见一","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-23/3-160229-3CFkHU.jpg%21320w?auth_key=1554112812-6257927afe2548e29ca718993df6bf15-0-52512a86c81bed4f8f33797b5017e559","thumb_up_count":0,"has_thumb_up":false},{"id":21,"nickname":"我是苹果测试机没错苹果测试机就是","avatar":"http://static.ro-test.xorout.com/user_avatar/10000L/2019-03-25/100041.jpg%21320w?auth_key=1554112812-b636eb241fa74e22bde8e36e64ff7c01-0-0f60b41a89228ecde07000bcf51dc77f","thumb_up_count":0,"has_thumb_up":false},{"id":20,"nickname":"wqb","avatar":"http://static.ro-test.xorout.com/user_avatar/10000K/2019-03-24/175021.jpg%21320w?auth_key=1554112812-21d93ac8f7dc480593f262d725149ccf-0-17dd00e98418216462b620ad80993166","thumb_up_count":0,"has_thumb_up":false},{"id":2,"nickname":"黄明黄明黄明黄明黄明黄明黄明黄明黄明黄明","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-23/2-172913-7CqYwu.jpg%21320w?auth_key=1554112812-02ded13a98414b19aac44423a76304c4-0-8ef25be764ad08d038695752184ba64e","thumb_up_count":0,"has_thumb_up":false},{"id":15,"nickname":"Innocence","avatar":"http://static.ro-test.xorout.com/user_avatar/10000F/2019-03-23/170706.jpg%21320w?auth_key=1554112812-4be2110ccd0849f687ce535d019ffc74-0-6fadd6a0cfed36633bca6cc250467e52","thumb_up_count":0,"has_thumb_up":false}]}
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
         * current : {"nickname":"阳光","avatar":"http://static.ro-test.xorout.com/user_avatar/10000A/2019-03-23/153708.jpg%21320w?auth_key=1554112812-c5d1f41c431d48fd9b54211efe68a8f5-0-9cf46639694d8fa3d1f37b1ff5356beb","thumb_up_count":0,"rank":12,"has_thumb_up":false}
         * list : [{"id":7,"nickname":"于湛","avatar":"http://static.ro-test.xorout.com/user_avatar/100007/2019-03-23/152448.jpg%21320w?auth_key=1554112812-970224936f30402cbd4d5f04472d0c9a-0-e425fdd041bde7026e740d9c7f6d1a29","thumb_up_count":0,"has_thumb_up":false},{"id":6,"nickname":"小小","avatar":"http://static.ro-test.xorout.com/user_avatar/100006/2019-03-23/152230.jpg%21320w?auth_key=1554112812-4260c54552eb4773a862173225584d59-0-40c0bac3251124e0f474c13b2f3645a9","thumb_up_count":0,"has_thumb_up":false},{"id":35,"nickname":"a.oY","avatar":"http://static.ro-test.xorout.com/user_avatar/10000Z/2019-03-28/152847.jpg%21320w?auth_key=1554112812-99f23d6ba4c34be881a9b1e35f06d5b6-0-b51e744cef6a739b19adc93d8c324184","thumb_up_count":0,"has_thumb_up":false},{"id":34,"nickname":"兔子\u2019susie","avatar":"http://static.ro-test.xorout.com/user_avatar/10000Y/2019-03-28/150440.jpg%21320w?auth_key=1554112812-2b75c68e50494bdd8d3c0229937838ee-0-0ec4114d367a4336fcd8f262a23931c6","thumb_up_count":0,"has_thumb_up":false},{"id":32,"nickname":"设置名字","avatar":"http://static.ro-test.xorout.com/user_avatar/10000W/2019-03-28/094019.jpg%21320w?auth_key=1554112812-b429ae1345f14446881ea6d46be0f4cb-0-f0758b1b68c20d3378b0bbb6327606b5","thumb_up_count":0,"has_thumb_up":false},{"id":3,"nickname":"见一滴墨水的蓝见一滴墨水的蓝见一","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-23/3-160229-3CFkHU.jpg%21320w?auth_key=1554112812-6257927afe2548e29ca718993df6bf15-0-52512a86c81bed4f8f33797b5017e559","thumb_up_count":0,"has_thumb_up":false},{"id":21,"nickname":"我是苹果测试机没错苹果测试机就是","avatar":"http://static.ro-test.xorout.com/user_avatar/10000L/2019-03-25/100041.jpg%21320w?auth_key=1554112812-b636eb241fa74e22bde8e36e64ff7c01-0-0f60b41a89228ecde07000bcf51dc77f","thumb_up_count":0,"has_thumb_up":false},{"id":20,"nickname":"wqb","avatar":"http://static.ro-test.xorout.com/user_avatar/10000K/2019-03-24/175021.jpg%21320w?auth_key=1554112812-21d93ac8f7dc480593f262d725149ccf-0-17dd00e98418216462b620ad80993166","thumb_up_count":0,"has_thumb_up":false},{"id":2,"nickname":"黄明黄明黄明黄明黄明黄明黄明黄明黄明黄明","avatar":"http://static.ro-test.xorout.com/user_avatar/2019-03-23/2-172913-7CqYwu.jpg%21320w?auth_key=1554112812-02ded13a98414b19aac44423a76304c4-0-8ef25be764ad08d038695752184ba64e","thumb_up_count":0,"has_thumb_up":false},{"id":15,"nickname":"Innocence","avatar":"http://static.ro-test.xorout.com/user_avatar/10000F/2019-03-23/170706.jpg%21320w?auth_key=1554112812-4be2110ccd0849f687ce535d019ffc74-0-6fadd6a0cfed36633bca6cc250467e52","thumb_up_count":0,"has_thumb_up":false}]
         */

        private ListBean current;
        private List<ListBean> list;

        public ListBean getCurrent() {
            return current;
        }

        public void setCurrent(ListBean current) {
            this.current = current;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }


        public static class ListBean {
            /**
             * id : 7
             * nickname : 于湛
             * avatar : http://static.ro-test.xorout.com/user_avatar/100007/2019-03-23/152448.jpg%21320w?auth_key=1554112812-970224936f30402cbd4d5f04472d0c9a-0-e425fdd041bde7026e740d9c7f6d1a29
             * thumb_up_count : 0
             * has_thumb_up : false
             */

            private int id;
            private String nickname;
            private String avatar;
            private int thumb_up_count;
            private boolean has_thumb_up;
            private int rank;

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

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

            public int getThumb_up_count() {
                return thumb_up_count;
            }

            public void setThumb_up_count(int thumb_up_count) {
                this.thumb_up_count = thumb_up_count;
            }

            public boolean isHas_thumb_up() {
                return has_thumb_up;
            }

            public void setHas_thumb_up(boolean has_thumb_up) {
                this.has_thumb_up = has_thumb_up;
            }
        }
    }
}
