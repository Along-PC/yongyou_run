package com.tourye.run.ui.activities.punch;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.utils.DateFormatUtils;
import com.tourye.run.utils.RevealAnimation;
import com.tourye.run.utils.SaveUtil;
import com.tourye.run.utils.SensorEventHelper;

import java.util.ArrayList;
import java.util.List;

import static com.tourye.run.ui.activities.punch.RunningActivity.RECEIVER_ACTION;


/**
 * @ClassName: MapActivity
 * @Author: along
 * @Description: 地图页面
 * @CreateDate: 2019/3/14 3:27 PM
 */
public class MapActivity extends BaseActivity implements AMap.OnMapTouchListener, View.OnClickListener {

    private MapView mMapActivityMap;
    private TextView mTvActivityMapTime;
    private TextView mTvActivityMapDistance;
    private ImageView mImgActivityMapClose;
    private LinearLayout mRootLayout;

    RevealAnimation mRevealAnimation;//页面动画

    //接受后台定位服务的广播action
    private IntentFilter intentFilter = new IntentFilter(RECEIVER_ACTION);
    private LocationReceiver locationReceiver = new LocationReceiver();

    //定位点集合
    private ArrayList<LatLng> mLatLngs = new ArrayList<LatLng>();
    private LatLng mLatLngLast = null;//上次定位的点
    boolean useMoveToLocationWithMapMode = true;//地图是否跟随移动
    private MyCancelCallback myCancelCallback = new MyCancelCallback();

    //自定义定位小蓝点的Marker
    Marker locationMarker;
    private SensorEventHelper mSensorHelper;
    private AMap aMap;

    //坐标和经纬度转换工具
    Projection projection;
    private UiSettings mUiSettings;

    private float mTotalDistance;//总共行走距离
    private long mTotal_time;//跑步持续时间
    private long mLastTime;//定位开始时间

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10086:
                    long l = System.currentTimeMillis();
                    long second_unit = 1000;
                    long minute_unit = second_unit * 60;
                    long hour_unit = minute_unit * 60;
                    mTotal_time += l - mLastTime;
                    long hour = mTotal_time / hour_unit;
                    long minute = (mTotal_time - hour * hour_unit) / minute_unit;
                    long second = (mTotal_time - hour * hour_unit - minute * minute_unit) / second_unit;

                    //格式化时间
                    String hour_str = String.format("%0" + 2 + "d", hour);
                    String minute_str = String.format("%0" + 2 + "d", minute);
                    String second_str = String.format("%0" + 2 + "d", second);

                    mLastTime = l;
                    mTvActivityMapTime.setText(hour_str + ":" + minute_str + ":" + second_str);
                    mHandler.sendEmptyMessageDelayed(10086, 1000);
                    break;
            }
        }
    };
    private boolean mIs_pause;//是否处于暂停状态

    @Override
    public void initView() {

        mMapActivityMap = (MapView) findViewById(R.id.map_activity_map);
        mTvActivityMapTime = (TextView) findViewById(R.id.tv_activity_map_time);
        mTvActivityMapDistance = (TextView) findViewById(R.id.tv_activity_map_distance);
        mImgActivityMapClose = (ImageView) findViewById(R.id.img_activity_map_close);

        //开始动画
//        startAnimation();

        mImgActivityMapClose.setOnClickListener(this);

    }

    public void initData() {
        Intent intent = getIntent();
        mTotalDistance = intent.getFloatExtra("total_distance", 0f);
        mTotal_time = intent.getLongExtra("total_time", 0);
        mIs_pause = intent.getBooleanExtra("is_pause", false);
        mLatLngs = intent.getParcelableArrayListExtra("locations");
        mLastTime = System.currentTimeMillis();
        if (!mIs_pause) {
            mHandler.sendEmptyMessageDelayed(10086, 1000);
            //注册定位广播
            registerReceiver(locationReceiver, intentFilter);
        }
        mTvActivityMapDistance.setText(String.format("%.2f", mTotalDistance / 1000) + "");

        //如果设置了屏幕常亮
        if (SaveUtil.getBoolean(SaveConstants.IS_LONG_LIGHT, false)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        initMap();

    }

    /**
     * 开启进入动画
     */
    private void startAnimation() {
        Intent intent = this.getIntent();   //get the intent to recieve the x and y coords, that you passed before
        mRootLayout = (LinearLayout) findViewById(R.id.ll_activity_map_root); //there you have to get the root layout of your second activity
        mRevealAnimation = new RevealAnimation(mRootLayout, intent, this);
        mRevealAnimation.setOnAnimatorEndCallback(new RevealAnimation.OnAnimatorEndCallback() {
            @Override
            public void onEnd() {
                initMap();
            }
        });
    }

    /**
     * 初始化地图控件
     */
    private void initMap() {
        mSensorHelper = new SensorEventHelper(this);
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapActivityMap.onCreate(mSavedInstanceState);
        if (aMap == null) {
            aMap = mMapActivityMap.getMap();
            setUpMap();
            setAlreadyData();
        }
    }

    /**
     * 设置当前已经存在的数据
     */
    public void setAlreadyData() {
        Intent intent = getIntent();
        mTotalDistance = intent.getFloatExtra("total_distance", 0f);
        mTotal_time = intent.getLongExtra("total_time", 0);
        ArrayList<LatLng> latLngs = intent.getParcelableArrayListExtra("locations");
        //绘制已经定位过的点
        if (aMap != null && latLngs != null && latLngs.size()>0) {
            mLatLngLast=latLngs.get(latLngs.size() - 1);
            Polyline polyline = aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(10).color(Color.parseColor("#4A90E2")));
            //首次定位
            locationMarker = aMap.addMarker(new MarkerOptions().position(latLngs.get(latLngs.size() - 1))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker))
                    .anchor(0.5f, 0.5f));
            //首次定位,选择移动到地图中心点并修改级别到15级
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(latLngs.size() - 1), 17));
            //设置旋转角
            mSensorHelper.setCurrentMarker(locationMarker);//定位图标旋转
        }
        mTvActivityMapDistance.setText(String.format("%.2f", mTotalDistance / 1000) + "");
        mTvActivityMapTime.setText(DateFormatUtils.formatTimestamp(mTotal_time));
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        //设置点击定位返回定位点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
        aMap.setMyLocationStyle(myLocationStyle);
        // 设置默认定位按钮是否显示
//        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(false);
        mUiSettings = aMap.getUiSettings();
        //显示指南针
        mUiSettings.setCompassEnabled(true);
        //设置卫星地图
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        //显示比例尺
        mUiSettings.setScaleControlsEnabled(true);
        aMap.setOnMapTouchListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapActivityMap.onDestroy();
        if (!mIs_pause) {
            //解除广播
            unregisterReceiver(locationReceiver);
        }

        if (mSensorHelper != null) {
            mSensorHelper.unRegisterSensorListener();
            mSensorHelper.setCurrentMarker(null);
            mSensorHelper = null;
        }
    }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapActivityMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapActivityMap.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapActivityMap.onSaveInstanceState(outState);
    }

    @Override
    public void onTouch(MotionEvent motionEvent) {
        useMoveToLocationWithMapMode = false;
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //关闭地图和小蓝点一起移动的模式
                useMoveToLocationWithMapMode = false;
                break;
            case MotionEvent.ACTION_UP:
                //开启地图和小蓝点一起移动的模式
                useMoveToLocationWithMapMode = true;
                break;
        }
    }

    /**
     * 处理定位返回的数据
     *
     * @param amapLocation
     */
    public void processMapData(AMapLocation amapLocation) {
        if (aMap == null) {
            return;
        }
        if (mLatLngs==null) {
            return;
        }
        //定位成功
        if (amapLocation != null && amapLocation.getErrorCode() == 0) {
            LatLng latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
            //展示自定义定位小蓝点
            if (locationMarker == null) {
                //首次定位
                locationMarker = aMap.addMarker(new MarkerOptions().position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker))
                        .anchor(0.5f, 0.5f));
                //首次定位,选择移动到地图中心点并修改级别到15级
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            } else {
                if (useMoveToLocationWithMapMode) {
                    //二次以后定位，使用sdk中没有的模式，让地图和小蓝点一起移动到中心点（类似导航锁车时的效果）
                    startMoveLocationAndMap(amapLocation);
                } else {
                    startChangeLocation(amapLocation);
                }
            }

            //绘制移动线
            mLatLngs.add(latLng);

            List<LatLng> latLngs = new ArrayList<LatLng>();
            if (locationMarker == null) {
                //首次定位
                latLngs.add(latLng);
            } else {
                latLngs.add(mLatLngLast);
                latLngs.add(latLng);
            }
            if (mLatLngLast != null) {
                float distance = AMapUtils.calculateLineDistance(mLatLngLast, latLng);
                mTotalDistance += distance;
                mTvActivityMapDistance.setText(String.format("%.2f", mTotalDistance / 1000) + "");
                Polyline polyline = aMap.addPolyline(new PolylineOptions().
                        addAll(latLngs).width(10).color(Color.parseColor("#4A90E2")));
            } else {
                Polyline polyline = aMap.addPolyline(new PolylineOptions().
                        addAll(mLatLngs).width(10).color(Color.parseColor("#4A90E2")));
            }
            mLatLngLast = latLng;

        }
    }

    /**
     * 修改自定义定位小蓝点的位置
     *
     * @param amapLocation
     */
    private void startChangeLocation(AMapLocation amapLocation) {
        LatLng latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());

        if (locationMarker != null) {
            LatLng curLatlng = locationMarker.getPosition();
            if (curLatlng == null || !curLatlng.equals(latLng)) {
                locationMarker.setPosition(latLng);
                //设置旋转角
                mSensorHelper.setCurrentMarker(locationMarker);//定位图标旋转
            }
        }
    }

    /**
     * 同时修改自定义定位小蓝点和地图的位置
     *
     * @param amapLocation
     */
    private void startMoveLocationAndMap(AMapLocation amapLocation) {
        try {
            LatLng latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
            //将小蓝点提取到屏幕上
            if (projection == null) {
                projection = aMap.getProjection();
            }
            if (locationMarker != null && projection != null) {
                LatLng markerLocation = locationMarker.getPosition();
                Point screenPosition = aMap.getProjection().toScreenLocation(markerLocation);
                locationMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
                //设置旋转角
//            locationMarker.setRotateAngle(360-amapLocation.getBearing());
                mSensorHelper.setCurrentMarker(locationMarker);//定位图标旋转
            }
            //移动地图，移动结束后，将小蓝点放到放到地图上
            myCancelCallback.setTargetLatlng(latLng);
            //动画移动的时间，最好不要比定位间隔长，如果定位间隔2000ms 动画移动时间最好小于2000ms，可以使用1000ms
            //如果超过了，需要在myCancelCallback中进行处理被打断的情况
            aMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng), 1000, myCancelCallback);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
//        mMapActivityMap.setVisibility(View.GONE);
//        mRevealAnimation.unRevealActivity();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_activity_map_close:
//                mMapActivityMap.setVisibility(View.INVISIBLE);
//                mRevealAnimation.unRevealActivity();
                finish();
                break;
        }
    }

    //接受定位数据广播
    public class LocationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(RECEIVER_ACTION)) {
                String locationResult = intent.getStringExtra("result");
                AMapLocation aMapLocation = (AMapLocation) intent.getParcelableExtra("data");
                processMapData(aMapLocation);
            }
        }

    }

    /**
     * 监控地图动画移动情况，如果结束或者被打断，都需要执行响应的操作
     */
    class MyCancelCallback implements AMap.CancelableCallback {

        LatLng targetLatlng;

        public void setTargetLatlng(LatLng latlng) {
            this.targetLatlng = latlng;
        }

        @Override
        public void onFinish() {
            if (locationMarker != null && targetLatlng != null) {
                locationMarker.setPosition(targetLatlng);
            }
        }

        @Override
        public void onCancel() {
            if (locationMarker != null && targetLatlng != null) {
                locationMarker.setPosition(targetLatlng);
            }
        }
    }

    @Override
    public boolean isNeedTitle() {
        return false;
    }

    @Override
    public boolean isNeedPortrait() {
        return false;
    }

    @Override
    public int getRootView() {
        return R.layout.activity_map;
    }

}
