package com.tourye.run.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by along on 2017/8/4 0004.
 * 获取移动设备当前网络状态
 */

public class NetStateUtils {
    //所有的网络类型
    public static final int NETWORK_STATE_NONE=0;
    public static final int NETWORK_STATE_WIFI=1;
    public static final int NETWORK_STATE_2G=2;
    public static final int NETWORK_STATE_3G=3;
    public static final int NETWORK_STATE_4G=4;
    public static final int NETWORK_STATE_MOBILE=5;

    //获取网络状态
    public static int getNetState(Context context){

        //获取手机的网络服务
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //如果手机不能上网
        if (connectivityManager == null) {
            return NETWORK_STATE_NONE;
        }

        //当前没有可使用的网络
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
            return NETWORK_STATE_NONE;
        }

        //判断链接的是不是wifi
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null) {
            NetworkInfo.State state = wifiInfo.getState();
            if (state == NetworkInfo.State.CONNECTED) {
                return NETWORK_STATE_WIFI;
            }
        }

        //获取移动数据类型
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileInfo != null) {
            String subtypeName = mobileInfo.getSubtypeName();
            NetworkInfo.State state = mobileInfo.getState();
            if (null != state) {
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    switch (activeNetworkInfo.getSubtype()) {
                        //如果是2g类型
                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            return NETWORK_STATE_2G;
                        //如果是3g类型
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            return NETWORK_STATE_3G;
                        //如果是4g类型
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return NETWORK_STATE_4G;
                        default:
                            //中国移动 联通 电信 三种3G制式
                            if (subtypeName.equalsIgnoreCase("TD-SCDMA") || subtypeName.equalsIgnoreCase("WCDMA") || subtypeName.equalsIgnoreCase("CDMA2000")) {
                                return NETWORK_STATE_3G;
                            } else {
                                return NETWORK_STATE_MOBILE;
                            }
                    }
                }
            }

        }
        return NETWORK_STATE_NONE;
    }

    //获取当前网络连接的类型信息
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                Log.e("along","type:"+mNetworkInfo.getType());
                return mNetworkInfo.getType();
            }
        }
        Log.e("along","type:-1");
        return -1;
    }


}
