package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   UserCenterAdBean
 *
 * @Author:   along
 *
 * @Description: 个人中心广告实体
 *
 * @CreateDate:   2019/3/26 9:37 AM
 *
 */
public class UserCenterAdBean {

    /**
     * status : 0
     * timestamp : 1553563641
     * data : [{"image":"http://static.ro-test.xorout.com/footer_image/2019-03-23-17-14-45/d1011c87705f45479da1e8078b0ec00e.png?auth_key=1553567241-551ea91e1fc0419d80f334828894c230-0-79e67dd10c69eaa18adbd7b6973f937e","url":"http://hundred-test.xorout.com/admin/user_center_advertisements/create"}]
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
         * image : http://static.ro-test.xorout.com/footer_image/2019-03-23-17-14-45/d1011c87705f45479da1e8078b0ec00e.png?auth_key=1553567241-551ea91e1fc0419d80f334828894c230-0-79e67dd10c69eaa18adbd7b6973f937e
         * url : http://hundred-test.xorout.com/admin/user_center_advertisements/create
         */

        private String image;
        private String url;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
