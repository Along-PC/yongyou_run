package com.tourye.run.services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.tourye.run.ui.activities.punch.MapActivity;

import static com.tourye.run.ui.activities.punch.RunningActivity.RECEIVER_ACTION;

/**
 *
 * @ClassName:   LocationTempService
 *
 * @Author:   along
 *
 * @Description:    跑步定位后台服务
 *
 * @CreateDate:   2019/4/10 10:53 AM
 *
 */
public class LocationTempService extends Service {

    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    private int locationCount;//定位次数

    private static final String NOTIFICATION_CHANNEL_NAME = "BackgroundLocation";
    private NotificationManager notificationManager = null;
    boolean isCreateChannel = false;

    public LocationTempService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public IBinder onBind(Intent intent) {

        startLocation();

        startForeground(998,buildNotification());

        return new LocationBinder();

    }

    @Override
    public boolean onUnbind(Intent intent) {

        stopLocation();

        stopForeground(true);

        return super.onUnbind(intent);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 启动定位
     */
    public void startLocation() {

        stopLocation();

        if (null == mLocationClient) {
            mLocationClient = new AMapLocationClient(this.getApplicationContext());
        }

        mLocationOption = new AMapLocationClientOption();
        //设置为高精度定位模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);
        //是指定位间隔
        mLocationOption.setInterval(2000);
        mLocationOption.setSensorEnable(true);
        //设置gps优先
//            mLocationOption.setGpsFirst(true);
        //可选，设置是否使用传感器。默认是false
        // 使用连续
        mLocationOption.setOnceLocation(false);
        mLocationOption.setLocationCacheEnable(false);
        // 地址信息
        mLocationOption.setNeedAddress(true);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(locationListener);
        mLocationClient.startLocation();
    }

    /**
     * 停止定位
     */
    public void stopLocation() {
        if (null != mLocationClient) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
    }

    @SuppressLint("NewApi")
    private Notification buildNotification() {
        Notification.Builder builder = null;
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            //Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
            if (null == notificationManager) {
                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            }
            String channelId = getPackageName();
            if (!isCreateChannel) {
                NotificationChannel notificationChannel = new NotificationChannel(channelId,
                        NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.enableLights(true);//是否在桌面icon右上角展示小圆点
                notificationChannel.setLightColor(Color.BLUE); //小圆点颜色
                notificationChannel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
                notificationManager.createNotificationChannel(notificationChannel);
                isCreateChannel = true;
            }
            builder = new Notification.Builder(getApplicationContext(), channelId);
        } else {
            builder = new Notification.Builder(getApplicationContext());
        }

        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification = builder.build();
        } else {
            return builder.getNotification();
        }
        return notification;
    }

    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation==null) {
                return;
            }
            if (aMapLocation.getLocationType()==6) {
                return;
            }
            //发送结果的通知
            sendLocationBroadcast(aMapLocation);
        }

        private void sendLocationBroadcast(AMapLocation aMapLocation) {
            //记录信息并发送广播
            locationCount++;
            StringBuffer sb = new StringBuffer();
            sb.append("定位完成 第" + locationCount + "次\n");
            sb.append("纬度："+aMapLocation.getAltitude()+"~~精度："+aMapLocation.getLongitude());
            Intent mIntent = new Intent(RECEIVER_ACTION);
            String content = sb.toString();
            mIntent.putExtra("result", content);
            mIntent.putExtra("data",aMapLocation);
            //发送广播
            sendBroadcast(mIntent);
        }

    };

    public class LocationBinder extends Binder {

    }
}
