package com.tourye.run.bean;

import java.io.Serializable;

/**
 *
 * @ClassName:   TeamBasicInfoBean
 *
 * @Author:   along
 *
 * @Description:
 *
 * @CreateDate:   2019/5/23 11:53 AM
 *
 */
public class TeamBasicInfoBean implements Serializable {
    /**
     * id : 15
     * name : 活力每天嗨跑团
     * logo : http://static.ro-test.xorout.com/team_logo/2019-03-15-13-01-38/7dd83fc9efa4485f93e69c63a4bf8250.png%21100h?auth_key=1552641397-9305c63f58d74c9cbc945cab8465d96d-0-d5343952c0da6892b732f6d8ad212c60
     * monitor : 我是苹果测试机没错苹果测试机就是
     * city : 天津市
     * distance : 3.00
     * member_count : 0
     * verified : false
     */

    private int id;
    private String name;
    private String logo;
    private String monitor;
    private String city;
    private String distance;
    private int member_count;
    private boolean verified;

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getMember_count() {
        return member_count;
    }

    public void setMember_count(int member_count) {
        this.member_count = member_count;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
