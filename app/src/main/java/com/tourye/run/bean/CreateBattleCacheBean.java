package com.tourye.run.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @ClassName:   CreateBattleCacheBean
 *
 * @Author:   along
 *
 * @Description:     创建战队缓存信息实体
 *
 * @CreateDate:   2019/4/17 11:48 AM
 *
 */
public class CreateBattleCacheBean implements Serializable {

    private String head_path;//头像路径
    private String battle_path;//战队二维码路径
    private String leader_path;//队长二维码路径
    private int city_id;
    private String city_name;
    private int level_id;
    private String battle_name;
    private String battle_description;
    private List<String> photos;

    public CreateBattleCacheBean() {
    }

    public CreateBattleCacheBean(String head_path, String battle_path, String leader_path, int city_id, String city_name, int level_id, String battle_name, String battle_description, List<String> photos) {
        this.head_path = head_path;
        this.battle_path = battle_path;
        this.leader_path = leader_path;
        this.city_id = city_id;
        this.city_name = city_name;
        this.level_id = level_id;
        this.battle_name = battle_name;
        this.battle_description = battle_description;
        this.photos = photos;
    }

    public String getHead_path() {
        return head_path;
    }

    public void setHead_path(String head_path) {
        this.head_path = head_path;
    }

    public String getBattle_path() {
        return battle_path;
    }

    public void setBattle_path(String battle_path) {
        this.battle_path = battle_path;
    }

    public String getLeader_path() {
        return leader_path;
    }

    public void setLeader_path(String leader_path) {
        this.leader_path = leader_path;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getLevel_id() {
        return level_id;
    }

    public void setLevel_id(int level_id) {
        this.level_id = level_id;
    }

    public String getBattle_name() {
        return battle_name;
    }

    public void setBattle_name(String battle_name) {
        this.battle_name = battle_name;
    }

    public String getBattle_description() {
        return battle_description;
    }

    public void setBattle_description(String battle_description) {
        this.battle_description = battle_description;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "CreateBattleCacheBean{" +
                "head_path='" + head_path + '\'' +
                ", battle_path='" + battle_path + '\'' +
                ", leader_path='" + leader_path + '\'' +
                ", city_id=" + city_id +
                ", city_name='" + city_name + '\'' +
                ", level_id=" + level_id +
                ", battle_name='" + battle_name + '\'' +
                ", battle_description='" + battle_description + '\'' +
                ", photos=" + photos +
                '}';
    }
}
