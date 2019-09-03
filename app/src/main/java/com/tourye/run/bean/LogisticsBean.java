package com.tourye.run.bean;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @ClassName:   LogisticsBean
 *
 * @Author:   along
 *
 * @Description:    物流实体
 *
 * @CreateDate:   2019/4/29 10:09 AM
 *
 */
public class LogisticsBean implements Serializable{

    /**
     * status : 0
     * timestamp : 1556503615
     * data : [{"serial_number":"xxx","status":"xxx","name":"xxx","spec":"xxx","image":"xxx","express_company":"xxxxxxxx","express_number":"xxxxxxxx","after_sale_phone":"xxx","after_sale_text":"xxx","after_sale_image":"xxx"}]
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
         * serial_number : xxx
         * status : xxx  // pending--待发货，sent--已发货
         * name : xxx
         * spec : xxx  // 规格
         * image : xxx
         * express_company : xxxxxxxx  // 快递公司
         * express_number : xxxxxxxx   // 快递单号
         * after_sale_phone : xxx
         * after_sale_text : xxx
         * after_sale_image : xxx
         */

        private String serial_number;
        private String status;
        private String name;
        private String spec;
        private String image;
        private String express_company;
        private String express_number;
        private String after_sale_phone;
        private String after_sale_text;
        private String after_sale_image;

        public String getSerial_number() {
            return serial_number;
        }

        public void setSerial_number(String serial_number) {
            this.serial_number = serial_number;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getExpress_company() {
            return express_company;
        }

        public void setExpress_company(String express_company) {
            this.express_company = express_company;
        }

        public String getExpress_number() {
            return express_number;
        }

        public void setExpress_number(String express_number) {
            this.express_number = express_number;
        }

        public String getAfter_sale_phone() {
            return after_sale_phone;
        }

        public void setAfter_sale_phone(String after_sale_phone) {
            this.after_sale_phone = after_sale_phone;
        }

        public String getAfter_sale_text() {
            return after_sale_text;
        }

        public void setAfter_sale_text(String after_sale_text) {
            this.after_sale_text = after_sale_text;
        }

        public String getAfter_sale_image() {
            return after_sale_image;
        }

        public void setAfter_sale_image(String after_sale_image) {
            this.after_sale_image = after_sale_image;
        }
    }
}
