package com.tourye.run.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.ui.activities.MainActivity;
import com.tourye.run.ui.activities.punch.MapActivity;
import com.tourye.run.ui.fragments.SignupFragment;

/**
 *
 * @ClassName:   LocationUtils
 *
 * @Author:   along
 *
 * @Description:    获取单次定位工具类
 *
 * @CreateDate:   2019/4/22 1:18 PM
 *
 */
public class LocationUtils {

    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private static LocationUtils mLocationUtils;

    private LocationUtils(){

    }

    public static LocationUtils getInstance(){
        if (mLocationUtils==null) {
            mLocationUtils=new LocationUtils();
        }
        return mLocationUtils;
    }

    /**
     * 启动定位
     */
    public void startLocation(AMapLocationListener locationListener) {
        mlocationClient = new AMapLocationClient(BaseApplication.mApplicationContext);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(locationListener);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(2000);
        mLocationOption.setOnceLocation(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }


}
