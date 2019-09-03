package com.tourye.run.bean;
/**
 *
 * @ClassName:   ContractSignStatusBean
 *
 * @Author:   along
 *
 * @Description:    检查签约状态实体
 *
 * @CreateDate:   2019/4/25 6:07 PM
 *
 */
public class ContractSignStatusBean {

    /**
     * status : 0
     * timestamp : 1556179856
     * data : {"signed":true}
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
         * signed : true
         */

        private int signed;

        public int isSigned() {
            return signed;
        }

        public void setSigned(int signed) {
            this.signed = signed;
        }
    }
}
