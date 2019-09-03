package com.tourye.run.bean;
/**
 *
 * @ClassName:   LeaderApplyStatusBean
 *
 * @Author:   along
 *
 * @Description:    队长申请状态实体
 *
 * @CreateDate:   2019/4/16 12:01 PM
 *
 */
public class LeaderApplyStatusBean {

    /**
     * status : 0
     * timestamp : 1555387254
     * data : {"ever_became_monitor":true,"referrer":1,"referrer_nickname":"卢森煌","referrer_avatar":"http://static.ro-test.xorout.com/user_avatar/100001/2019-03-23/151825.jpg%21200h?auth_key=1555390854-750139ee35fb483d982c1c42d54ace6e-0-a63262956e09733cb7ba467de17701f8","applied":true,"agreement_read":false,"contract_signed":false,"extra_info_filled":true}
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
         * ever_became_monitor : true
         * referrer : 1
         * referrer_nickname : 卢森煌
         * referrer_avatar : http://static.ro-test.xorout.com/user_avatar/100001/2019-03-23/151825.jpg%21200h?auth_key=1555390854-750139ee35fb483d982c1c42d54ace6e-0-a63262956e09733cb7ba467de17701f8
         * applied : true
         * agreement_read : false
         * contract_signed : false
         * extra_info_filled : true
         */

        private boolean ever_became_monitor;
        private int referrer;
        private String referrer_nickname;
        private String referrer_avatar;
        private boolean applied;
        private boolean agreement_read;
        private boolean contract_signed;
        private boolean extra_info_filled;
        private String status;

        public boolean isEver_became_monitor() {
            return ever_became_monitor;
        }

        public void setEver_became_monitor(boolean ever_became_monitor) {
            this.ever_became_monitor = ever_became_monitor;
        }

        public int getReferrer() {
            return referrer;
        }

        public void setReferrer(int referrer) {
            this.referrer = referrer;
        }

        public String getReferrer_nickname() {
            return referrer_nickname;
        }

        public void setReferrer_nickname(String referrer_nickname) {
            this.referrer_nickname = referrer_nickname;
        }

        public String getReferrer_avatar() {
            return referrer_avatar;
        }

        public void setReferrer_avatar(String referrer_avatar) {
            this.referrer_avatar = referrer_avatar;
        }

        public boolean isApplied() {
            return applied;
        }

        public void setApplied(boolean applied) {
            this.applied = applied;
        }

        public boolean isAgreement_read() {
            return agreement_read;
        }

        public void setAgreement_read(boolean agreement_read) {
            this.agreement_read = agreement_read;
        }

        public boolean isContract_signed() {
            return contract_signed;
        }

        public void setContract_signed(boolean contract_signed) {
            this.contract_signed = contract_signed;
        }

        public boolean isExtra_info_filled() {
            return extra_info_filled;
        }

        public void setExtra_info_filled(boolean extra_info_filled) {
            this.extra_info_filled = extra_info_filled;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
