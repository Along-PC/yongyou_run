package com.tourye.run.bean;

/**
 *
 * @ClassName:   RunningTraceBean
 *
 * @Author:   along
 *
 * @Description:    获取已经上传的跑步轨迹
 *
 * @CreateDate:   2019/6/12 1:45 PM
 *
 */
public class RunningTraceBean {
    private int status;
    private int timestamp;
    private TraceSubmitBean data;

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

    public TraceSubmitBean getData() {
        return data;
    }

    public void setData(TraceSubmitBean data) {
        this.data = data;
    }
}
