package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   ActivityCommentBean
 *
 * @Author:   along
 *
 * @Description:活动评价实体
 *
 * @CreateDate:   2019/3/15 3:18 PM
 *
 */
public class ActivityCommentBean {


    /**
     * status : 0
     * timestamp : 1552634232
     * data : [{"nickname":"见一滴墨水的蓝","avatar":"http://static.ro-test.xorout.com/user_avatar/100008/2019-03-15/133858.jpg%21200h?auth_key=1552637778-06c28157583b47dfa9e115061b32ae04-0-0058f07d90784749eb8d3e10368e4788","content":"有感于\u201c百日跑8\u201d 今天是2018年11月21日，\u201c百日跑8\u201d的第108天，到昨天为止，我已完成104天的打卡。我真的跑了100多天！报名之初，我还是一个\u201c跑步小白\u201d，心怀\u201c减重\u201d的初衷，加入\u201c百日跑8\u201d的\u201c 新疆独山子\u201d战队。新疆，我一直认为那是一个神奇的地","tag":"百日跑8用户"},{"nickname":"见一滴墨水的蓝","avatar":"http://static.ro-test.xorout.com/user_avatar/100008/2019-03-15/133858.jpg%21200h?auth_key=1552637778-deff83b019dc4fba9f58426d760eeb32-0-476b45fb965e2d898e535df7a9a735e2","content":"千里之行，始于足下！百日跑会带给你不一样的体验，有身体上的健康，有养成一个坚持下去的习惯，更会让我们遇到更好的自己！ To be bettet!","tag":"收获感悟"},{"nickname":"见一滴墨水的蓝","avatar":"http://static.ro-test.xorout.com/user_avatar/100008/2019-03-15/133858.jpg%21200h?auth_key=1552637778-51b5897f695a4b3c88ed88f4cee226e8-0-d38017ff7cda270791f750f33b9197fb","content":"一百天很简单，因为有目标有动力，一百天也很难，需要每一天的坚持，但是如果有机会给自己一个动力吧，看看自己有没有什么变化，我在闪电1k娃娃团等你","tag":"坚持就是胜利"},{"nickname":"见一滴墨水的蓝","avatar":"http://static.ro-test.xorout.com/user_avatar/100008/2019-03-15/133858.jpg%21200h?auth_key=1552637778-d8348818fc9e4a2382205747d4e4f98b-0-fcd5ad2613c3bb932616adc067a30f5f","content":"在这里清晰地看见100天带来的改变。","tag":"改变"},{"nickname":"见一滴墨水的蓝","avatar":"http://static.ro-test.xorout.com/user_avatar/100008/2019-03-15/133858.jpg%21200h?auth_key=1552637778-0d2225823454476497de3d637457ace3-0-deff70ce4dac5ecd9cae49f43e8be166","content":"第九届百日跑必加入闪电队三大理由： 1，系列战队，底蕴深厚，从基础的一公里，两公里；三公里，五公里进阶；8公里迷你马，半马，全马，拥有深厚的经验积淀和专业的理解指导。 2，资源最大，在不跑就出局中，闪电系列队伍独占鳌头，加入闪电，可以享用不跑就出局最庞大的资源，包括专业的运动伤","tag":"百日跑9用户"}]
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
         * nickname : 见一滴墨水的蓝
         * avatar : http://static.ro-test.xorout.com/user_avatar/100008/2019-03-15/133858.jpg%21200h?auth_key=1552637778-06c28157583b47dfa9e115061b32ae04-0-0058f07d90784749eb8d3e10368e4788
         * content : 有感于“百日跑8” 今天是2018年11月21日，“百日跑8”的第108天，到昨天为止，我已完成104天的打卡。我真的跑了100多天！报名之初，我还是一个“跑步小白”，心怀“减重”的初衷，加入“百日跑8”的“ 新疆独山子”战队。新疆，我一直认为那是一个神奇的地
         * tag : 百日跑8用户
         */

        private String nickname;
        private String avatar;
        private String content;
        private String tag;

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

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }
}
