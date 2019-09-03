package com.tourye.run.bean;
/**
 *
 * @ClassName:   CheckZfbStatusBean
 *
 * @Author:   along
 *
 * @Description:    核实支付宝是否支付成功
 *
 * @CreateDate:   2019/4/15 5:37 PM
 *
 */
public class CheckZfbStatusBean {

    /**
     * status : 0
     * timestamp : 1555310023
     * data : {"finished":true}
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
         * finished : true
         */

        private boolean finished;

        public boolean isFinished() {
            return finished;
        }

        public void setFinished(boolean finished) {
            this.finished = finished;
        }
    }
}
