package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   PackageBean
 *
 * @Author:   along
 *
 * @Description: 套餐实体
 *
 * @CreateDate:   2019/3/26 4:46 PM
 *
 */
public class PackageBean {

    /**
     * status : 0
     * timestamp : 1553589926
     * data : [{"id":1,"image":"http://static.ro-test.xorout.com/activity_package/2019-03-15-13-37-44/f77778cf02bb43d3a5214313c8bac53f.png%21750w?auth_key=1553593526-6a4288e6350b4d94ab3be4d370ae4a11-0-396b73e30ad19c4a8c81feffa34ee936","price":1},{"id":2,"image":"http://static.ro-test.xorout.com/activity_package/2019-03-15-13-38-09/c707749dbc4e47508202afced4cd5991.png%21750w?auth_key=1553593526-846ffd09f9ac4bf8bf5d96d6131b7ab2-0-e68b6b6f9d5cd582615730f2f03aa87a","price":2},{"id":3,"image":"http://static.ro-test.xorout.com/activity_package/2019-03-21-14-45-50/6325232160734632ab4216dcd97fd621.png%21750w?auth_key=1553593526-8a7e465caa5a4760b44fe2114b91325d-0-d1ecd093616ef16d83f6c2ea0c3a0df0","price":3}]
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
         * id : 1
         * image : http://static.ro-test.xorout.com/activity_package/2019-03-15-13-37-44/f77778cf02bb43d3a5214313c8bac53f.png%21750w?auth_key=1553593526-6a4288e6350b4d94ab3be4d370ae4a11-0-396b73e30ad19c4a8c81feffa34ee936
         * price : 1
         */

        private int id;
        private String image;
        private int price;
        private boolean isSelected;
        private String name;
        private String detail_image;
        private boolean isShowDetailImage;//是否显示套餐大图

        public boolean isShowDetailImage() {
            return isShowDetailImage;
        }

        public void setShowDetailImage(boolean showDetailImage) {
            isShowDetailImage = showDetailImage;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDetail_image() {
            return detail_image;
        }

        public void setDetail_image(String detail_image) {
            this.detail_image = detail_image;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
