package com.tourye.run.bean;

import java.util.List;

/**
 * @ClassName: ActionInfoBean
 * @Author: along
 * @Description:活动信息实体
 * @CreateDate: 2019/3/15 3:10 PM
 */
public class ActionInfoBean {

    /**
     * status : 0
     * timestamp : 1552633755
     * data : {"name":"第十届百日跑","sign_up_start_date":"2019-03-15","sign_up_finish_date":"2019-04-10","sign_in_start_date":"2019-04-15","sign_in_finish_date":"2019-08-01","cover_image":"http://static.ro-test.xorout.com/activity_cover_image/2019-03-15-10-54-09/d4865f8b132b4146acbe2bb7f2d1c8ac.png?auth_key=1552637355-cea76ea5bd8b49ebb3ba931d8bbd8635-0-46dcb3ce191ac4855add2bb491f38a99","rule_images":["http://static.ro-test.xorout.com/activity_rule_images/2019-03-15-11-10-36/c562e566c6064b7db74365d7a63a784a.png?auth_key=1552637355-c701e8ea40424d7a82be7fe4456bd87c-0-506acd33696f89cbea4dc2b3148d722f"],"share_logo":"activity_share_logo/2019-03-15-11-25-20/03a48b0280684bfb9cd73aa686cb6531.png","share_title_not_joined":"第九届\u201c百日跑\u201d，报名就送799元澳洲酷拉锐跑鞋，点此报名减5元","share_text_not_joined":"最后限量名额抢占中","share_title_joined":"我是第N名\u201c百日跑\u201d报名者，我已获得酷拉锐799元跑鞋，报名领走","share_text_joined":"最后限量名额抢占中","rule_url":"https://weibo.com/u/3865389390/home","agreement_url":"https://weibo.com/u/3865389390/home","joined":false,"total_join_count":0}
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
         * name : 第十届百日跑
         * sign_up_start_date : 2019-03-15
         * sign_up_finish_date : 2019-04-10
         * sign_in_start_date : 2019-04-15
         * sign_in_finish_date : 2019-08-01
         * cover_image : http://static.ro-test.xorout.com/activity_cover_image/2019-03-15-10-54-09/d4865f8b132b4146acbe2bb7f2d1c8ac.png?auth_key=1552637355-cea76ea5bd8b49ebb3ba931d8bbd8635-0-46dcb3ce191ac4855add2bb491f38a99
         * rule_images : ["http://static.ro-test.xorout.com/activity_rule_images/2019-03-15-11-10-36/c562e566c6064b7db74365d7a63a784a.png?auth_key=1552637355-c701e8ea40424d7a82be7fe4456bd87c-0-506acd33696f89cbea4dc2b3148d722f"]
         * share_logo : activity_share_logo/2019-03-15-11-25-20/03a48b0280684bfb9cd73aa686cb6531.png
         * share_title_not_joined : 第九届“百日跑”，报名就送799元澳洲酷拉锐跑鞋，点此报名减5元
         * share_text_not_joined : 最后限量名额抢占中
         * share_title_joined : 我是第N名“百日跑”报名者，我已获得酷拉锐799元跑鞋，报名领走
         * share_text_joined : 最后限量名额抢占中
         * rule_url : https://weibo.com/u/3865389390/home
         * agreement_url : https://weibo.com/u/3865389390/home
         * joined : false
         * total_join_count : 0
         */

        private String name;
        private String sign_up_start_date;
        private String sign_up_finish_date;
        private String sign_in_start_date;
        private String sign_in_finish_date;
        private String cover_image;
        private String share_logo;
        private String share_title_not_joined;
        private String share_text_not_joined;
        private String share_title_joined;
        private String share_text_joined;
        private String monitor_apply_start_date;
        private String monitor_apply_finish_date;
        private String rule_url;
        private String agreement_url;
        private boolean joined;
        private int total_join_count;
        private List<String> rule_images;
        private String tips_cover;
        private String share_team_first_title;
        private String share_team_second_title;

        //        以下内容在joined为true时存在
        private int participation_id;
        private String team_id;
        private int level_id;
        private boolean can_change_package;
        private boolean package_changed;
        private int package_id;
        private String join_time;
        private String monitor_rule_staff_qr_code;
        private String join_index;

        public String getTips_cover() {
            return tips_cover;
        }

        public void setTips_cover(String tips_cover) {
            this.tips_cover = tips_cover;
        }

        public String getJoin_time() {
            return join_time;
        }

        public void setJoin_time(String join_time) {
            this.join_time = join_time;
        }

        public String getMonitor_rule_staff_qr_code() {
            return monitor_rule_staff_qr_code;
        }

        public void setMonitor_rule_staff_qr_code(String monitor_rule_staff_qr_code) {
            this.monitor_rule_staff_qr_code = monitor_rule_staff_qr_code;
        }

        public String getJoin_index() {
            return join_index;
        }

        public void setJoin_index(String join_index) {
            this.join_index = join_index;
        }

        public int getParticipation_id() {
            return participation_id;
        }

        public void setParticipation_id(int participation_id) {
            this.participation_id = participation_id;
        }

        public String getTeam_id() {
            return team_id;
        }

        public void setTeam_id(String team_id) {
            this.team_id = team_id;
        }

        public int getLevel_id() {
            return level_id;
        }

        public void setLevel_id(int level_id) {
            this.level_id = level_id;
        }

        public boolean isCan_change_package() {
            return can_change_package;
        }

        public void setCan_change_package(boolean can_change_package) {
            this.can_change_package = can_change_package;
        }

        public boolean isPackage_changed() {
            return package_changed;
        }

        public void setPackage_changed(boolean package_changed) {
            this.package_changed = package_changed;
        }

        public int getPackage_id() {
            return package_id;
        }

        public void setPackage_id(int package_id) {
            this.package_id = package_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSign_up_start_date() {
            return sign_up_start_date;
        }

        public void setSign_up_start_date(String sign_up_start_date) {
            this.sign_up_start_date = sign_up_start_date;
        }

        public String getSign_up_finish_date() {
            return sign_up_finish_date;
        }

        public void setSign_up_finish_date(String sign_up_finish_date) {
            this.sign_up_finish_date = sign_up_finish_date;
        }

        public String getSign_in_start_date() {
            return sign_in_start_date;
        }

        public void setSign_in_start_date(String sign_in_start_date) {
            this.sign_in_start_date = sign_in_start_date;
        }

        public String getSign_in_finish_date() {
            return sign_in_finish_date;
        }

        public void setSign_in_finish_date(String sign_in_finish_date) {
            this.sign_in_finish_date = sign_in_finish_date;
        }

        public String getCover_image() {
            return cover_image;
        }

        public void setCover_image(String cover_image) {
            this.cover_image = cover_image;
        }

        public String getShare_logo() {
            return share_logo;
        }

        public void setShare_logo(String share_logo) {
            this.share_logo = share_logo;
        }

        public String getShare_title_not_joined() {
            return share_title_not_joined;
        }

        public void setShare_title_not_joined(String share_title_not_joined) {
            this.share_title_not_joined = share_title_not_joined;
        }

        public String getShare_text_not_joined() {
            return share_text_not_joined;
        }

        public void setShare_text_not_joined(String share_text_not_joined) {
            this.share_text_not_joined = share_text_not_joined;
        }

        public String getShare_title_joined() {
            return share_title_joined;
        }

        public void setShare_title_joined(String share_title_joined) {
            this.share_title_joined = share_title_joined;
        }

        public String getShare_text_joined() {
            return share_text_joined;
        }

        public void setShare_text_joined(String share_text_joined) {
            this.share_text_joined = share_text_joined;
        }

        public String getRule_url() {
            return rule_url;
        }

        public void setRule_url(String rule_url) {
            this.rule_url = rule_url;
        }

        public String getAgreement_url() {
            return agreement_url;
        }

        public void setAgreement_url(String agreement_url) {
            this.agreement_url = agreement_url;
        }

        public boolean isJoined() {
            return joined;
        }

        public void setJoined(boolean joined) {
            this.joined = joined;
        }

        public int getTotal_join_count() {
            return total_join_count;
        }

        public void setTotal_join_count(int total_join_count) {
            this.total_join_count = total_join_count;
        }

        public List<String> getRule_images() {
            return rule_images;
        }

        public void setRule_images(List<String> rule_images) {
            this.rule_images = rule_images;
        }

        public String getMonitor_apply_start_date() {
            return monitor_apply_start_date;
        }

        public void setMonitor_apply_start_date(String monitor_apply_start_date) {
            this.monitor_apply_start_date = monitor_apply_start_date;
        }

        public String getMonitor_apply_finish_date() {
            return monitor_apply_finish_date;
        }

        public void setMonitor_apply_finish_date(String monitor_apply_finish_date) {
            this.monitor_apply_finish_date = monitor_apply_finish_date;
        }

        public String getShare_team_first_title() {
            return share_team_first_title;
        }

        public void setShare_team_first_title(String share_team_first_title) {
            this.share_team_first_title = share_team_first_title;
        }

        public String getShare_team_second_title() {
            return share_team_second_title;
        }

        public void setShare_team_second_title(String share_team_second_title) {
            this.share_team_second_title = share_team_second_title;
        }
    }
}
