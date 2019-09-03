package com.tourye.run.bean;

import java.util.List;

/**
 *
 * @ClassName:   CreateBattleSubmitBean
 *
 * @Author:   along
 *
 * @Description:    创建战队提交信息
 *
 * @CreateDate:   2019/4/15 1:20 PM
 *
 */
public class CreateBattleSubmitBean {

    /**
     * activity_id : 1
     * logo : 1/2019-04-15/10-100323-XDTsPK.jpg
     * name : along
     * level_id : 1
     * description : haha
     * group_qr_code : 1/2019-04-15/10-100323-XDTsPK.jpg
     * monitor_qr_code : 1/2019-04-15/10-100323-XDTsPK.jpg
     */

    private String activity_id;
    private String logo;
    private String name;
    private int level_id;
    private String description;
    private String group_qr_code;
    private String monitor_qr_code;
    private String city_id;
    private List<String> photos;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel_id() {
        return level_id;
    }

    public void setLevel_id(int level_id) {
        this.level_id = level_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroup_qr_code() {
        return group_qr_code;
    }

    public void setGroup_qr_code(String group_qr_code) {
        this.group_qr_code = group_qr_code;
    }

    public String getMonitor_qr_code() {
        return monitor_qr_code;
    }

    public void setMonitor_qr_code(String monitor_qr_code) {
        this.monitor_qr_code = monitor_qr_code;
    }
}
