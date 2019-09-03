package com.tourye.run.bean;

import java.util.Date;

/**
 *
 * @ClassName:   TeamManageCalendarBean
 *
 * @Author:   along
 *
 * @Description:    队伍管理日历实体
 *
 * @CreateDate:   2019/5/27 10:02 AM
 *
 */
public class TeamManageCalendarBean {
    private Date date;
    private String date_index;//日历中的索引
    private String dateOfMonth;//dayofmonth
    private boolean is_selected;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDate_index() {
        return date_index;
    }

    public void setDate_index(String date_index) {
        this.date_index = date_index;
    }

    public String getDateOfMonth() {
        return dateOfMonth;
    }

    public void setDateOfMonth(String dateOfMonth) {
        this.dateOfMonth = dateOfMonth;
    }

    public boolean isIs_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }
}
