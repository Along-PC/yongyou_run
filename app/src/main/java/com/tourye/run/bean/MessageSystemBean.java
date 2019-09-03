package com.tourye.run.bean;

import java.util.List;

/**
 *
 * @ClassName:   MessageSystemBean
 *
 * @Author:   along
 *
 * @Description:    系统消息实体
 *
 * @CreateDate:   2019/4/3 1:11 PM
 *
 */
public class MessageSystemBean {

    /**
     * status : 0
     * timestamp : 1554264126
     * data : [{"id":294,"title":"[阳光]评论\"dasdasd\",点击查看>","bg":null,"link":"http://hundred-test.xorout.com?#/community/78","created_at":"2019-04-03 10:32:30"},{"id":293,"title":"[阳光]评论\"asdasda\",点击查看>","bg":null,"link":"http://hundred-test.xorout.com?#/community/78","created_at":"2019-04-03 10:32:06"},{"id":288,"title":"[阳光]评论\"dasdas\",点击查看>","bg":null,"link":"http://hundred-test.xorout.com?#/community/77","created_at":"2019-04-03 10:20:58"},{"id":267,"title":"今日\u201c第十届百日跑\u201d的打卡审核通过。","bg":null,"link":null,"created_at":"2019-04-02 18:15:44"},{"id":114,"title":"1000奖金等你拿\n\n推荐好友来担任队长，就有机会获得奖金1000元，点击查看详情","bg":null,"link":"https://mp.weixin.qq.com/s/B8yF7QbjKNk6JSlYROXkew","created_at":"2019-03-29 16:00:05"},{"id":111,"title":"福利提醒\n\n点击获取海报给好友，邀请3人报名得鸿星尔克衣服，邀请10人报名的联想体脂称。","bg":null,"link":"http://hundred-test.xorout.com?activity_id=1#/team/detail/normal/3","created_at":"2019-03-29 16:00:05"},{"id":50,"title":"福利提醒\n\n点击获取海报给好友，邀请3人报名得鸿星尔克衣服，邀请10人报名的联想体脂称。","bg":null,"link":"http://hundred-test.xorout.com?activity_id=1#/team/detail/normal/3","created_at":"2019-03-28 18:00:10"},{"id":18,"title":"挑战无人区 | 呼伦贝尔草原站 第三届亲子徒步挑战赛299元代金券已发放，扫描下图二维码关注公众号\u201c陶冶户外\u201d使用。","bg":null,"link":null,"created_at":"2019-03-28 15:18:48"},{"id":17,"title":"恭喜百日跑报名成功，报名页点击左下角按钮领取邀请卡分享给好友，邀请好友报名有奖品哟","bg":null,"link":null,"created_at":"2019-03-28 15:18:45"}]
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
         * id : 294
         * title : [阳光]评论"dasdasd",点击查看>
         * bg : null
         * link : http://hundred-test.xorout.com?#/community/78
         * created_at : 2019-04-03 10:32:30
         */

        private int id;
        private String title;
        private String bg;
        private String link;
        private String created_at;

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

        public String getBg() {
            return bg;
        }

        public void setBg(String bg) {
            this.bg = bg;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}
