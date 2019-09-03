package com.tourye.run.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @ClassName:   TraceSubmitBean
 *
 * @Author:   along
 *
 * @Description:    轨迹上传实体
 *
 * @CreateDate:   2019/5/28 11:39 AM
 *
 */
public class TraceSubmitBean implements Serializable {

    /**
     * points : [{"longitude":20,"latitude":20,"timestamp":20}]
     * distance : 3
     * time : 10
     */
    private String distance;
    private float time;
    private ArrayList<PointsBean> points;
    private String time_str;
    private String speed;

    private int id;
    private int user_id;
    private String created_at;
    private String updated_at;

    public TraceSubmitBean() {
    }

    public TraceSubmitBean(String distance, int time, ArrayList<PointsBean> points) {
        this.distance = distance;
        this.time = time;
        this.points = points;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public ArrayList<PointsBean> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<PointsBean> points) {
        this.points = points;
    }

    public String getTime_str() {
        return time_str;
    }

    public void setTime_str(String time_str) {
        this.time_str = time_str;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public static class PointsBean implements Serializable{
        /**
         * longitude : 20
         * latitude : 20
         * timestamp : 20
         */

        public double longitude;
        public double latitude;
        public long timestamp;
        public double speed;

        public PointsBean(double longitude, double latitude, long timestamp) {
            this.longitude = longitude;
            this.latitude = latitude;
            this.timestamp = timestamp;
        }

        public PointsBean(double latitude,double longitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public PointsBean(double latitude, double longitude, long timestamp, double speed) {
            this.longitude = longitude;
            this.latitude = latitude;
            this.timestamp = timestamp;
            this.speed = speed;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }


    }
}
