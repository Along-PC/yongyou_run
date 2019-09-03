package com.tourye.run.bean;

/**
 * Created by longlongren on 2018/10/29.
 * <p>
 * introduce:登录实体
 */

public class LoginBean {

    /**
     * status : 0
     * timestamp : 1540785468
     * data : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NDEzOTAyNjgsImlhdCI6MTU0MDc4NTQ2OCwidWlkIjoiMTAwMDFCIiwiY2xpZW50IjoiYXBwIiwibm9uY2UiOiJ6Q3VxSWlTWCJ9.jl0mUVTR_T1nmFKi8oC81nLDhuenNCp-vjV4NtXmtlo
     */

    private int status;
    private int timestamp;
    private DataBean data;
    private String message;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class DataBean{
        private String token;
        private boolean is_new;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public boolean isIs_new() {
            return is_new;
        }

        public void setIs_new(boolean is_new) {
            this.is_new = is_new;
        }
    }
}
