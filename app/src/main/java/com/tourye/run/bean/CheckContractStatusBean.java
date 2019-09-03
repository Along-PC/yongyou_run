package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   CheckContractStatusBean
 *
 * @Author:   along
 *
 * @Description:    检查提现是否签约实体
 *
 * @CreateDate:   2019/4/25 10:02 AM
 *
 */
public class CheckContractStatusBean {

    /**
     * status : 0
     * timestamp : 1556157694
     * data : {"need_contract":false,"activity_ids":[]}
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
         * need_contract : false
         * activity_ids : []
         */

        private boolean need_contract;
        private List<?> activity_ids;

        public boolean isNeed_contract() {
            return need_contract;
        }

        public void setNeed_contract(boolean need_contract) {
            this.need_contract = need_contract;
        }

        public List<?> getActivity_ids() {
            return activity_ids;
        }

        public void setActivity_ids(List<?> activity_ids) {
            this.activity_ids = activity_ids;
        }
    }
}
