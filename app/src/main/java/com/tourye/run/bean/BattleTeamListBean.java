package com.tourye.run.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @ClassName:   BattleTeamListBean
 *
 * @Author:   along
 *
 * @Description:战队列表实体
 *
 * @CreateDate:   2019/3/15 4:18 PM
 *
 */
public class BattleTeamListBean implements Serializable{

    /**
     * status : 0
     * timestamp : 1552637797
     * data : [{"id":15,"name":"活力每天嗨跑团","logo":"http://static.ro-test.xorout.com/team_logo/2019-03-15-13-01-38/7dd83fc9efa4485f93e69c63a4bf8250.png%21100h?auth_key=1552641397-9305c63f58d74c9cbc945cab8465d96d-0-d5343952c0da6892b732f6d8ad212c60","monitor":"我是苹果测试机没错苹果测试机就是","city":"天津市","distance":"3.00","member_count":0,"verified":false},{"id":16,"name":"昌吉遇见跑团","logo":"http://static.ro-test.xorout.com/team_logo/2019-03-15-13-03-31/271e9dad63444901a6fa2bca91f6e8d6.png%21100h?auth_key=1552641397-fae4d103995343099e58fe3f5f23922a-0-9c81e2fb687f1592ac8d2aabe54b4fa4","monitor":"我是苹果测试机没错苹果测试机就是","city":"太原市","distance":"3.00","member_count":0,"verified":false},{"id":24,"name":"哈哈","logo":"http://static.ro-test.xorout.com/team_avatar/2019-03-15/6-132554-gefPCm.jpg%21100h?auth_key=1552641397-75161b4220fe48b6a76b74a4175bd43e-0-1bee6598368800e6e9125930b37665f2","monitor":"卢森煌","city":"长治市","distance":"3.00","member_count":0,"verified":false}]
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
