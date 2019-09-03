package com.tourye.run.bean;
/**
 *
 * @ClassName:   WithdrawalAccountBean
 *
 * @Author:   along
 *
 * @Description:    提现账户实体
 *
 * @CreateDate:   2019/4/25 4:11 PM
 *
 */
public class WithdrawalAccountBean {

    /**
     * status : 0
     * timestamp : 1556179856
     * data : {"alipay":null,"bank_card":null,"id_card":"14040219940429361X","name":"任龙龙"}
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
         * alipay : null
         * bank_card : null
         * id_card : 14040219940429361X
         * name : 任龙龙
         */

        private String alipay;
        private String bank_card;
        private String id_card;
        private String name;

        public String getAlipay() {
            return alipay;
        }

        public void setAlipay(String alipay) {
            this.alipay = alipay;
        }

        public String getBank_card() {
            return bank_card;
        }

        public void setBank_card(String bank_card) {
            this.bank_card = bank_card;
        }

        public String getId_card() {
            return id_card;
        }

        public void setId_card(String id_card) {
            this.id_card = id_card;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
