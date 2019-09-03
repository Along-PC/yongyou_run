package com.tourye.run.utils;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.tourye.run.bean.TraceSubmitBean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName:   KalmanUtils
 *
 * @Author:   along
 *
 * @Description:    卡尔曼滤波、圆滑轨迹工具类
 *
 * @CreateDate:   2019/6/20 10:21 AM
 *
 */
public class KalmanUtils {
    private int mIntensity = 3;
    private float mThreshhold = 0.3f;
    private float mNoiseThreshhold = 10;

    public KalmanUtils(){

    }

    public int getIntensity() {
        return mIntensity;
    }

    public void setIntensity(int mIntensity) {
        this.mIntensity = mIntensity;
    }

    public float getThreshhold() {
        return mThreshhold;
    }

    public void setThreshhold(float mThreshhold) {
        this.mThreshhold = mThreshhold;
    }

    public void setNoiseThreshhold(float mnoiseThreshhold) {
        this.mNoiseThreshhold = mnoiseThreshhold;
    }

    /**
     * 轨迹平滑优化
     * @param originlist 原始轨迹list,list.size大于2
     * @return 优化后轨迹list
     */
    public List<TraceSubmitBean.PointsBean> pathOptimize(List<TraceSubmitBean.PointsBean> originlist){

        List<TraceSubmitBean.PointsBean> list = removeNoisePoint(originlist);//去噪
        List<TraceSubmitBean.PointsBean> afterList = kalmanFilterPath(list,mIntensity);//滤波
        List<TraceSubmitBean.PointsBean> pathoptimizeList = reducerVerticalThreshold(afterList,mThreshhold);//抽稀
//        Log.i("MY","originlist: "+originlist.size());
//        Log.i("MY","list: "+list.size());
//        Log.i("MY","afterList: "+afterList.size());
//        Log.i("MY","pathoptimizeList: "+pathoptimizeList.size());
        return pathoptimizeList;
    }

    /**
     * 轨迹线路滤波
     * @param originlist 原始轨迹list,list.size大于2
     * @return 滤波处理后的轨迹list
     */
    public List<TraceSubmitBean.PointsBean> kalmanFilterPath(List<TraceSubmitBean.PointsBean> originlist) {
        return kalmanFilterPath(originlist,mIntensity);
    }


    /**
     * 轨迹去噪，删除垂距大于20m的点
     * @param originlist 原始轨迹list,list.size大于2
     * @return
     */
    public List<TraceSubmitBean.PointsBean> removeNoisePoint(List<TraceSubmitBean.PointsBean> originlist){
        return reduceNoisePoint(originlist,mNoiseThreshhold);
    }

    /**
     * 单点滤波
     * @param lastLoc 上次定位点坐标
     * @param curLoc 本次定位点坐标
     * @return 滤波后本次定位点坐标值
     */
    public TraceSubmitBean.PointsBean kalmanFilterPoint(TraceSubmitBean.PointsBean lastLoc, TraceSubmitBean.PointsBean curLoc) {
        return kalmanFilterPoint(lastLoc,curLoc,mIntensity);
    }

    /**
     * 轨迹抽稀
     * @param inPoints 待抽稀的轨迹list，至少包含两个点，删除垂距小于mThreshhold的点
     * @return 抽稀后的轨迹list
     */
    public List<TraceSubmitBean.PointsBean> reducerVerticalThreshold(List<TraceSubmitBean.PointsBean> inPoints) {
        return reducerVerticalThreshold(inPoints,mThreshhold);
    }

    /********************************************************************************************************/
    /**
     * 轨迹线路滤波
     * @param originlist 原始轨迹list,list.size大于2
     * @param intensity 滤波强度（1—5）
     * @return
     */
    private List<TraceSubmitBean.PointsBean> kalmanFilterPath(List<TraceSubmitBean.PointsBean> originlist, int intensity) {
        List<TraceSubmitBean.PointsBean> kalmanFilterList = new ArrayList<TraceSubmitBean.PointsBean>();
        if (originlist == null || originlist.size() <= 2)
            return kalmanFilterList;
        initial();//初始化滤波参数
        TraceSubmitBean.PointsBean latLng = null;
        TraceSubmitBean.PointsBean lastLoc = originlist.get(0);
        kalmanFilterList.add(lastLoc);
        for (int i = 1; i < originlist.size(); i++) {
            TraceSubmitBean.PointsBean curLoc = originlist.get(i);
            latLng = kalmanFilterPoint(lastLoc,curLoc,intensity);
            if (latLng != null) {
                kalmanFilterList.add(latLng);
                lastLoc = latLng;
            }
        }
        return kalmanFilterList;
    }

    /**
     * 单点滤波
     * @param lastLoc 上次定位点坐标
     * @param curLoc 本次定位点坐标
     * @param intensity 滤波强度（1—5）
     * @return 滤波后本次定位点坐标值
     */
    private TraceSubmitBean.PointsBean kalmanFilterPoint(TraceSubmitBean.PointsBean lastLoc, TraceSubmitBean.PointsBean curLoc, int intensity) {
        if (pdelt_x == 0 || pdelt_y == 0 ){
            initial();
        }
        TraceSubmitBean.PointsBean kalmanLatlng = null;
        if (lastLoc == null || curLoc == null){
            return kalmanLatlng;
        }
        if (intensity < 1){
            intensity = 1;
        } else if (intensity > 5){
            intensity = 5;
        }
        for (int j = 0; j < intensity; j++){
            kalmanLatlng = kalmanFilter(lastLoc.longitude,curLoc.longitude,lastLoc.latitude,curLoc.latitude,curLoc.timestamp,curLoc.speed);
            curLoc = kalmanLatlng;
        }
        return kalmanLatlng;
    }


    /***************************卡尔曼滤波开始********************************/
    private double lastLocation_x; //上次位置
    private double currentLocation_x;//这次位置
    private double lastLocation_y; //上次位置
    private double currentLocation_y;//这次位置
    private double estimate_x; //修正后数据
    private double estimate_y; //修正后数据
    private double pdelt_x; //自预估偏差
    private double pdelt_y; //自预估偏差
    private double mdelt_x; //上次模型偏差
    private double mdelt_y; //上次模型偏差
    private double gauss_x; //高斯噪音偏差
    private double gauss_y; //高斯噪音偏差
    private double kalmanGain_x; //卡尔曼增益
    private double kalmanGain_y; //卡尔曼增益

    private double m_R= 0;
    private double m_Q= 0;
    //初始模型
    private void initial(){
        pdelt_x =  0.001;
        pdelt_y =  0.001;
//        mdelt_x = 0;
//        mdelt_y = 0;
        mdelt_x =  5.698402909980532E-4;
        mdelt_y =  5.698402909980532E-4;
    }
    private TraceSubmitBean.PointsBean kalmanFilter(double oldValue_x, double value_x, double oldValue_y, double value_y,long timestamp,double speed){
        lastLocation_x = oldValue_x;
        currentLocation_x= value_x;
        gauss_x = Math.sqrt(pdelt_x * pdelt_x + mdelt_x * mdelt_x)+m_Q;     //计算高斯噪音偏差
        kalmanGain_x = Math.sqrt((gauss_x * gauss_x)/(gauss_x * gauss_x + pdelt_x * pdelt_x)) +m_R; //计算卡尔曼增益
        estimate_x = kalmanGain_x * (currentLocation_x - lastLocation_x) + lastLocation_x;    //修正定位点
        mdelt_x = Math.sqrt((1-kalmanGain_x) * gauss_x *gauss_x);      //修正模型偏差

        lastLocation_y = oldValue_y;
        currentLocation_y = value_y;
        gauss_y = Math.sqrt(pdelt_y * pdelt_y + mdelt_y * mdelt_y)+m_Q;     //计算高斯噪音偏差
        kalmanGain_y = Math.sqrt((gauss_y * gauss_y)/(gauss_y * gauss_y + pdelt_y * pdelt_y)) +m_R; //计算卡尔曼增益
        estimate_y = kalmanGain_y * (currentLocation_y - lastLocation_y) + lastLocation_y;    //修正定位点
        mdelt_y = Math.sqrt((1-kalmanGain_y) * gauss_y * gauss_y);      //修正模型偏差

        TraceSubmitBean.PointsBean latlng = new TraceSubmitBean.PointsBean(estimate_y,estimate_x,timestamp,speed);


        return latlng;
    }
    /***************************卡尔曼滤波结束**********************************/

    /***************************抽稀算法*************************************/
    private List<TraceSubmitBean.PointsBean> reducerVerticalThreshold(List<TraceSubmitBean.PointsBean> inPoints,
                                                  float threshHold) {
        if (inPoints == null) {
            return null;
        }
        if (inPoints.size() <= 2) {
            return inPoints;
        }
        List<TraceSubmitBean.PointsBean> ret = new ArrayList<TraceSubmitBean.PointsBean>();
        for (int i = 0; i < inPoints.size(); i++) {
            TraceSubmitBean.PointsBean pre = getLastLocation(ret);
            TraceSubmitBean.PointsBean cur = inPoints.get(i);
            if (pre == null || i == inPoints.size() - 1) {
                ret.add(cur);
                continue;
            }
            TraceSubmitBean.PointsBean next = inPoints.get(i + 1);
            double distance = calculateDistanceFromPoint(cur, pre, next);
            if (distance > threshHold){
                ret.add(cur);
            }
        }
        return ret;
    }
    private static TraceSubmitBean.PointsBean getLastLocation(List<TraceSubmitBean.PointsBean> oneGraspList) {
        if (oneGraspList == null || oneGraspList.size() == 0) {
            return null;
        }
        int locListSize = oneGraspList.size();
        TraceSubmitBean.PointsBean lastLocation = oneGraspList.get(locListSize - 1);
        return lastLocation;
    }

    /**
     * 计算当前点到线的垂线距离
     * @param p 当前点
     * @param lineBegin 线的起点
     * @param lineEnd 线的终点
     *
     */
    private static double calculateDistanceFromPoint(TraceSubmitBean.PointsBean p, TraceSubmitBean.PointsBean lineBegin,
                                                     TraceSubmitBean.PointsBean lineEnd) {
        double A = p.longitude - lineBegin.longitude;
        double B = p.latitude - lineBegin.latitude;
        double C = lineEnd.longitude - lineBegin.longitude;
        double D = lineEnd.latitude - lineBegin.latitude;

        double dot = A * C + B * D;
        double len_sq = C * C + D * D;
        double param = dot / len_sq;

        double xx, yy;

        if (param < 0 || (lineBegin.longitude == lineEnd.longitude
                && lineBegin.latitude == lineEnd.latitude)) {
            xx = lineBegin.longitude;
            yy = lineBegin.latitude;
//            return -1;
        } else if (param > 1) {
            xx = lineEnd.longitude;
            yy = lineEnd.latitude;
//            return -1;
        } else {
            xx = lineBegin.longitude + param * C;
            yy = lineBegin.latitude + param * D;
        }
        return AMapUtils.calculateLineDistance(new LatLng(p.latitude,p.longitude),new LatLng(yy,xx));
    }
    /***************************抽稀算法结束*********************************/

    private List<TraceSubmitBean.PointsBean> reduceNoisePoint(List<TraceSubmitBean.PointsBean> inPoints, float threshHold) {
        if (inPoints == null) {
            return null;
        }
        if (inPoints.size() <= 2) {
            return inPoints;
        }
        List<TraceSubmitBean.PointsBean> ret = new ArrayList<TraceSubmitBean.PointsBean>();
        for (int i = 0; i < inPoints.size(); i++) {
            TraceSubmitBean.PointsBean pre = getLastLocation(ret);
            TraceSubmitBean.PointsBean cur = inPoints.get(i);
            if (pre == null || i == inPoints.size() - 1) {
                ret.add(cur);
                continue;
            }
            TraceSubmitBean.PointsBean next = inPoints.get(i + 1);
            double distance = calculateDistanceFromPoint(cur, pre, next);
            if (distance < threshHold){
                ret.add(cur);
            }
        }
        return ret;
    }
}
