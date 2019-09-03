package com.tourye.run.bean;

import java.util.List;
/**
 *
 * @ClassName:   SearchBattleBean
 *
 * @Author:   along
 *
 * @Description:    搜索战队实体
 *
 * @CreateDate:   2019/4/3 5:52 PM
 *
 */
public class SearchBattleBean {

    /**
     * status : 0
     * timestamp : 1554285101
     * data : [{"id":17,"name":"测试战队5人","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-29/3-130449-cbYW76.jpg%21200h?auth_key=1554288701-7896c012fea148b9953bdf41108ededb-0-e400cc1f4dacd38980de776df6ffb8a8","monitor":"见一滴墨水的蓝见一滴墨水的蓝见一","city":"天津市","distance":1,"member_count":3,"verified":false},{"id":11,"name":"黄明战队1","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-24/2-221000-n5wMZp.jpg%21200h?auth_key=1554288701-3a89adb32e324bb2a48f482c6852030c-0-d119ca79db6002d7cfe610106cfb794d","monitor":"黄明黄明黄明黄明黄明黄明黄明黄明黄明黄明","city":"晋城市","distance":3,"member_count":1,"verified":false}]
     */

    private int status;
    private int timestamp;
    private List<TeamBasicInfoBean> data;

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

    public List<TeamBasicInfoBean> getData() {
        return data;
    }

    public void setData(List<TeamBasicInfoBean> data) {
        this.data = data;
    }

}
