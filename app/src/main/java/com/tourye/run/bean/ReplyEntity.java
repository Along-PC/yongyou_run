package com.tourye.run.bean;

/**
 * Created by longlongren on 2018/11/1.
 * <p>
 * introduce:二级评论数据实体
 */

public class ReplyEntity {
    /**
     * id : 92
     * user_id : 15
     * nickname : 袁晓
     * content : 15
     * reply_to : 晨晨-不起就出局app
     */

    private int id;
    private int user_id;
    private String nickname;
    private String content;
    private String reply_to;
    private int comment_id;

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply_to() {
        return reply_to;
    }

    public void setReply_to(String reply_to) {
        this.reply_to = reply_to;
    }
}
