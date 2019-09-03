package com.tourye.run.bean;
/**
 *
 * @ClassName:   RejectReasonBean
 *
 * @Author:   along
 *
 * @Description:    打卡审核拒绝原因实体
 *
 * @CreateDate:   2019/4/29 2:47 PM
 *
 */
public class RejectReasonBean {

    private String reason;
    private boolean isSelected;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
