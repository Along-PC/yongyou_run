package com.tourye.run.bean;

import java.util.List;

/**
 * Created by longlongren on 2018/11/1.
 * <p>
 * introduce:二级评论实体
 */

public class ChildReplyBean {

    /**
     * status : 0
     * timestamp : 1541058401
     * data : [{"id":92,"user_id":15,"nickname":"袁晓","content":"15","reply_to":"晨晨-不起就出局app"},{"id":91,"user_id":15,"nickname":"袁晓","content":"15","reply_to":"晨晨-不起就出局app"},{"id":90,"user_id":15,"nickname":"袁晓","content":"15","reply_to":"晨晨-不起就出局app"},{"id":89,"user_id":15,"nickname":"袁晓","content":"15","reply_to":"晨晨-不起就出局app"},{"id":88,"user_id":15,"nickname":"袁晓","content":"15","reply_to":"晨晨-不起就出局app"},{"id":87,"user_id":15,"nickname":"袁晓","content":"15","reply_to":"晨晨-不起就出局app"},{"id":86,"user_id":15,"nickname":"袁晓","content":"15","reply_to":"晨晨-不起就出局app"},{"id":85,"user_id":15,"nickname":"袁晓","content":"15","reply_to":"晨晨-不起就出局app"},{"id":84,"user_id":15,"nickname":"袁晓","content":"15","reply_to":"晨晨-不起就出局app"},{"id":83,"user_id":15,"nickname":"袁晓","content":"15","reply_to":"晨晨-不起就出局app"}]
     */

    private int status;
    private int timestamp;
    private List<ReplyEntity> data;

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

    public List<ReplyEntity> getData() {
        return data;
    }

    public void setData(List<ReplyEntity> data) {
        this.data = data;
    }
}
