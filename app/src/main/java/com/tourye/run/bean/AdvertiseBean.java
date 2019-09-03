package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   AdvertiseBean
 *
 * @Author:   along
 *
 * @Description:广告实体
 *
 * @CreateDate:   2019/3/15 5:41 PM
 *
 */
public class AdvertiseBean {

    /**
     * status : 0
     * timestamp : 1552642804
     * data : [{"image":"http://static.ro-test.xorout.com/footer_image/2019-03-15-13-46-05/d73d123a564244918b524ad28d63a1c8.png?auth_key=1552646404-259a4f7129f548b585d5c5e040cda34c-0-812cd63ccf0f1121e4aba9d46448c592","url":"https://www.baidu.com/"},{"image":"http://static.ro-test.xorout.com/footer_image/2019-03-15-13-44-00/7ea144e5392c4a109b045f15a9f4ee8d.png?auth_key=1552646404-5bc801ce20464ee9bd455f5bf71fe243-0-a6c341bb95633552657b597b09b44c99","url":"https://mall.jd.com/index-721032.html"}]
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
         * image : http://static.ro-test.xorout.com/footer_image/2019-03-15-13-46-05/d73d123a564244918b524ad28d63a1c8.png?auth_key=1552646404-259a4f7129f548b585d5c5e040cda34c-0-812cd63ccf0f1121e4aba9d46448c592
         * url : https://www.baidu.com/
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
