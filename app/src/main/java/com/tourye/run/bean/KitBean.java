package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   KitBean
 *
 * @Author:   along
 *
 * @Description:    锦囊实体
 *
 * @CreateDate:   2019/4/2 3:21 PM
 *
 */
public class KitBean {

    /**
     * status : 0
     * timestamp : 1554189605
     * data : [{"title":"一言不\"合\"就举牌！这家餐厅细思极好","url":null},{"title":"受贿、贪污3700余万元！季缃绮一审被判14年","url":null},{"title":"速看！考研下一阶段即将有变化！官方提醒来了","url":null},{"title":"语文书上的插图，居然是他们画的\u2026\u2026","url":null}]
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
         * title : 一言不"合"就举牌！这家餐厅细思极好
         * url : null
         */

        private String title;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
