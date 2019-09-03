package com.tourye.run.bean;

import java.util.List;

/**
 *
 * @ClassName:   DistrictsBean
 *
 * @Author:   along
 *
 * @Description:  地区实体
 *
 * @CreateDate:   2019/3/20 6:09 PM
 *
 */
public class DistrictsBean {

    /**
     * status : 0
     * timestamp : 1553076509
     * data : [{"id":1,"name":"北京市","level":1},{"id":19,"name":"天津市","level":1},{"id":37,"name":"河北省","level":1},{"id":228,"name":"山西省","level":1},{"id":368,"name":"内蒙古自治区","level":1},{"id":493,"name":"辽宁省","level":1},{"id":622,"name":"吉林省","level":1},{"id":700,"name":"黑龙江省","level":1},{"id":855,"name":"上海市","level":1},{"id":873,"name":"江苏省","level":1},{"id":996,"name":"浙江省","level":1},{"id":1108,"name":"安徽省","level":1},{"id":1246,"name":"福建省","level":1},{"id":1350,"name":"江西省","level":1},{"id":1473,"name":"山东省","level":1},{"id":1643,"name":"河南省","level":1},{"id":1836,"name":"湖北省","level":1},{"id":1965,"name":"湖南省","level":1},{"id":2115,"name":"广东省","level":1},{"id":2279,"name":"广西壮族自治区","level":1},{"id":2419,"name":"海南省","level":1},{"id":2453,"name":"重庆市","level":1},{"id":2494,"name":"四川省","level":1},{"id":2717,"name":"贵州省","level":1},{"id":2818,"name":"云南省","level":1},{"id":2972,"name":"西藏自治区","level":1},{"id":3055,"name":"陕西省","level":1},{"id":3183,"name":"甘肃省","level":1},{"id":3296,"name":"青海省","level":1},{"id":3351,"name":"宁夏回族自治区","level":1},{"id":3384,"name":"新疆维吾尔自治区","level":1},{"id":3506,"name":"台湾省","level":1},{"id":3507,"name":"香港特别行政区","level":1},{"id":3526,"name":"澳门特别行政区","level":1}]
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
         * name : 北京市
         * level : 1
         */

        private int id;
        private String name;
        private int level;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }
}
