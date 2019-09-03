package com.tourye.run.ui.activities.punch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.mapcore.util.dm;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.CommonJsonBean;
import com.tourye.run.bean.RunningTraceBean;
import com.tourye.run.bean.ScreenPunchBean;
import com.tourye.run.bean.TraceSubmitBean;
import com.tourye.run.bean.UploadFileBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.MainActivity;
import com.tourye.run.ui.activities.community.CreateDynamicActivity;
import com.tourye.run.ui.dialogs.LoadingDialog;
import com.tourye.run.utils.CacheUtils;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.KalmanUtils;
import com.tourye.run.utils.PathSmoothTool;
import com.tourye.run.utils.SaveUtil;
import com.tourye.run.utils.ShareUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @ClassName: RunningResultActivity
 *
 * @Author: along
 *
 * @Description: 轨迹打卡结果页面
 *
 * @CreateDate: 2019/5/9 11:05 AM
 *
 */
public class RunningResultActivity extends BaseActivity implements AMap.OnMapTouchListener, View.OnClickListener, AMap.OnMapScreenShotListener {

    private MapView mMapActivityRunningResult;
    private LinearLayout mLlActivityRunningResultDynamic;
    private TextView mTvActivityRunningResultTime;
    private TextView mTvActivityRunningResultDistance;
    private TextView mTvActivityRunningResultSpeed;
    private TextView mTvActivityRunningResultPeriod;
    private TextView mTvActivityRunningResultAchievement;
    private TextView mTvActivityRunningResultShare;
    private RelativeLayout mRlActivityRunningRoot;
    private ImageView mImgActivityRunningResultMapstatus;
    private ImageView mImgActivityRunningResultBack;

    //定位点集合
    private ArrayList<LatLng> mLatLngs = new ArrayList<LatLng>();
    ArrayList<TraceSubmitBean.PointsBean> mPointsBeans=new ArrayList<>();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //起点和终点的marker
    private MarkerOptions mMarkerStartOption;
    private MarkerOptions mMarkerEndOption;
    private Marker mMarkerStart;
    private Marker mMarkerEnd;
    private AMap aMap;

    private UiSettings mUiSettings;
    private String mPeriod;//
    private String mSpeed;//跑步配速
    private String mDistance;//跑步距离
    private Polygon mPolygon;//地图遮罩
    private String mTrace_id;//上传轨迹id
    private String mScreenUrl;//截图上传后的地址
    private long mTotal_minute;//跑步分钟数
    private int mPunchId=-1;//打卡id
    private int mType;//页面来源---1。处理跑步轨迹页面传递的数据  2。处理打卡列表页面传递的数据

    private LoadingDialog mLoadingDialog;//加载弹窗

    @Override
    public void initView() {
        mMapActivityRunningResult = (MapView) findViewById(R.id.map_activity_running_result);
        mLlActivityRunningResultDynamic = (LinearLayout) findViewById(R.id.ll_activity_running_result_dynamic);
        mTvActivityRunningResultTime = (TextView) findViewById(R.id.tv_activity_running_result_time);
        mTvActivityRunningResultDistance = (TextView) findViewById(R.id.tv_activity_running_result_distance);
        mTvActivityRunningResultSpeed = (TextView) findViewById(R.id.tv_activity_running_result_speed);
        mTvActivityRunningResultPeriod = (TextView) findViewById(R.id.tv_activity_running_result_period);
        mTvActivityRunningResultAchievement = (TextView) findViewById(R.id.tv_activity_running_result_achievement);
        mTvActivityRunningResultShare = (TextView) findViewById(R.id.tv_activity_running_result_share);
        mRlActivityRunningRoot = (RelativeLayout) findViewById(R.id.rl_activity_running_root);
        mImgActivityRunningResultMapstatus = (ImageView) findViewById(R.id.img_activity_running_result_mapstatus);
        mImgActivityRunningResultBack = (ImageView) findViewById(R.id.img_activity_running_result_back);

        mTvActivityRunningResultAchievement.setOnClickListener(this);
        mTvActivityRunningResultShare.setOnClickListener(this);
        mImgActivityRunningResultMapstatus.setOnClickListener(this);
        mLlActivityRunningResultDynamic.setOnClickListener(this);
        mImgActivityRunningResultBack.setOnClickListener(this);
    }

    @Override
    public void initData() {

        initMap();

        Intent intent = getIntent();
        mType = intent.getIntExtra("type", 0);
        if (mType ==1) {
            mLoadingDialog=new LoadingDialog(mActivity);
            mLoadingDialog.show();
            //处理跑步轨迹页面传递的数据
            mPeriod = intent.getStringExtra("period");
            mSpeed = intent.getStringExtra("speed");
            mDistance = intent.getStringExtra("distance");
            mTrace_id = intent.getStringExtra("trace_id");
            mTotal_minute = intent.getLongExtra("total_minute",0);
//            mPointsBeans= (ArrayList<TraceSubmitBean.PointsBean>) intent.getSerializableExtra("location_points");
            mPointsBeans= CacheUtils.getInstance().restoreData("location",mPointsBeans);
            mTvActivityRunningResultPeriod.setText(mPeriod);
            mTvActivityRunningResultSpeed.setText(mSpeed);
            mTvActivityRunningResultDistance.setText(mDistance);
            mTvActivityRunningResultTime.setText(simpleDateFormat.format(new Date()));
            drawMarker();
        }else if(mType ==2){
            //处理打卡列表页面传递的数据
            String punchTime = intent.getStringExtra("punch_time");
            String trace_id=intent.getStringExtra("trace_data");
            mTvActivityRunningResultTime.setText(punchTime);
            String punch_distance = intent.getStringExtra("punch_distance");
            mTvActivityRunningResultDistance.setText(punch_distance);
            getTrace(trace_id);
            mPunchId=intent.getIntExtra("punch_id",-1);
            mScreenUrl=intent.getStringExtra("punch_image");
        }

    }

    /**
     * 获取已经上传的轨迹
     * @param trace_id
     */
    public void getTrace(String trace_id){
        Map<String,String> map=new HashMap<>();
        map.put("trace_id",trace_id);
        HttpUtils.getInstance().get(Constants.GET_TRACE, map, new HttpCallback<RunningTraceBean>() {
            @Override
            public void onSuccessExecute(RunningTraceBean runningTraceBean) {
                TraceSubmitBean traceSubmitBean = runningTraceBean.getData();
                if (traceSubmitBean!=null) {
                    mTvActivityRunningResultSpeed.setText(traceSubmitBean.getSpeed());
                    mTvActivityRunningResultPeriod.setText(traceSubmitBean.getTime_str());
//                    mTvActivityRunningResultDistance.setText(traceSubmitBean.getDistance());
                    mPointsBeans=traceSubmitBean.getPoints();
                    drawMarker();
                }
            }
        });
    }

    /**
     * 初始化地图控件
     */
    private void initMap() {
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapActivityRunningResult.onCreate(mSavedInstanceState);
        if (aMap == null) {
            aMap = mMapActivityRunningResult.getMap();
            aMap.setMapTextZIndex(2);
            mUiSettings = aMap.getUiSettings();
            mUiSettings.setZoomControlsEnabled(false);
            //禁止旋转
            mUiSettings.setRotateGesturesEnabled(false);

        }
    }

    /**
     * 绘制轨迹和marker
     */
    private void drawMarker() {
        if (aMap==null) {
            return;
        }

        //用一个数组来存放颜色，渐变色，四个点需要设置四个颜色
        List<Integer> colorList = new ArrayList<Integer>();
        KalmanUtils kalmanUtils = new KalmanUtils();
        kalmanUtils.setIntensity(4);
        //处理后的点
        if (mPointsBeans==null) {
            mPointsBeans=new ArrayList<>();
        }
        List<TraceSubmitBean.PointsBean> processPointsBeans = kalmanUtils.pathOptimize(mPointsBeans);
        for (int i = 0; i < processPointsBeans.size(); i++) {
            TraceSubmitBean.PointsBean pointsBean = processPointsBeans.get(i);
            mLatLngs.add(new LatLng(pointsBean.getLatitude(), pointsBean.getLongitude()));
            double speed = pointsBean.getSpeed();
            speed=speed*3.6;
            if(speed<5){
                colorList.add(Color.GREEN);
            }if(speed>=5 && speed<10){
                colorList.add(Color.YELLOW);
            }else if(speed>=10){
                colorList.add(Color.RED);
            }
        }

        if (mLatLngs == null || mLatLngs.size() <= 0) {
            return;
        }

        //设置地图的最大展示范围
        LatLng latLngleft = new LatLng(mLatLngs.get(0).latitude - 2, mLatLngs.get(0).longitude - 2);
        LatLng latLngright = new LatLng(mLatLngs.get(0).latitude + 2, mLatLngs.get(0).longitude + 2);
        LatLngBounds latLngBounds = new LatLngBounds(latLngleft, latLngright);
        aMap.setMapStatusLimits(latLngBounds);

        zoomToSpan(mLatLngs);

        //平滑轨迹
        PathSmoothTool mpathSmoothTool = new PathSmoothTool();
        mpathSmoothTool.setIntensity(4);
//        List<LatLng> pathoptimizeList = mpathSmoothTool.pathOptimize(mLatLngs);
        PolylineOptions options = new PolylineOptions();
        options.width(DensityUtils.dp2px(5));//设置宽度
        options.addAll(mLatLngs);
        //加入对应的颜色,使用colorValues 即表示使用多颜色，使用color表示使用单色线
        options.colorValues(colorList);
        //加上这个属性，表示使用渐变线
        options.useGradient(true);
        Polyline calmanPolyline = aMap.addPolyline(options);
        //设置轨迹线的层级，为了隐藏地图背景时不被隐藏
        calmanPolyline.setZIndex(1);

        //绘制起点和终点
        mMarkerStartOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location_start))
                .position(mLatLngs.get(0))
                .draggable(true);
        mMarkerEndOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location_end))
                .position(mLatLngs.get(mLatLngs.size() - 1))
                .draggable(true);
        mMarkerStart = aMap.addMarker(mMarkerStartOption);
        mMarkerEnd = aMap.addMarker(mMarkerEndOption);
        //如果是从跑步页面过来，一段时间后进行打卡
        if (mType==1) {
            //todo
            mMapActivityRunningResult.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //获取屏幕截图
                    if (aMap != null) {
                        aMap.getMapScreenShot(RunningResultActivity.this);
                    }
                }
            },3000);
        }

    }

    /**
     * 显示地图背景
     */
    public void showMapBack() {
        if (mPolygon != null) {
            mPolygon.remove();
        }
        //显示地图文字标注
        aMap.showMapText(true);
    }

    /**
     * 隐藏地图背景
     */
    public void hideMapBack() {
        if (mLatLngs == null || mLatLngs.size() <= 0) {
            return;
        }
        PolygonOptions polygonOptions = new PolygonOptions()
                .addAll(createRectangle(mLatLngs.get(0), 2, 2))
                .fillColor(Color.parseColor("#FFF4F6FF")).strokeColor(Color.parseColor("#FFF4F6FF")).strokeWidth(10);

        mPolygon = aMap.addPolygon(polygonOptions);
        //隐藏地图文字标注
        aMap.showMapText(false);

    }

    /**
     * 生成一个长方形的四个坐标点---地图显示区域
     */
    private List<LatLng> createRectangle(LatLng center, double halfWidth, double halfHeight) {
        List<LatLng> latLngs = new ArrayList<LatLng>();
        latLngs.add(new LatLng(center.latitude - halfHeight, center.longitude - halfWidth));
        latLngs.add(new LatLng(center.latitude - halfHeight, center.longitude + halfWidth));
        latLngs.add(new LatLng(center.latitude + halfHeight, center.longitude + halfWidth));
        latLngs.add(new LatLng(center.latitude + halfHeight, center.longitude - halfWidth));
        return latLngs;
    }

    /**
     * 根据路线的位置对地图进行缩放并设置轨迹距离边线的距离
     *
     * @param latLngs
     */
    private void zoomToSpan(List<LatLng> latLngs) {
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (LatLng latLng : latLngs) {
            b.include(latLng);
        }
        LatLngBounds bounds = b.build();
        int top_padding = DensityUtils.dp2px(80);
        int bottom_padding = DensityUtils.dp2px(500);
        int left_right_padding = DensityUtils.dp2px(50);
        aMap.moveCamera(CameraUpdateFactory.newLatLngBoundsRect(bounds, left_right_padding, left_right_padding, top_padding, bottom_padding));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁地图
        mMapActivityRunningResult.onDestroy();
    }

    @Override
    public boolean isNeedTitle() {
        return false;
    }

    @Override
    public int getRootView() {
        return R.layout.activity_running_result;
    }

    @Override
    public void onTouch(MotionEvent motionEvent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_activity_running_result_achievement:
                if (mPunchId!=-1) {
                    Intent achievementIntent = new Intent(mActivity, AchievementCardActivity.class);
                    achievementIntent.putExtra("punch_id",mPunchId);
                    startActivity(achievementIntent);
                }else{
                    Toast.makeText(mActivity, "打卡失败，未获得成就卡", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_activity_running_result_share:
                if (mScreenUrl!=null) {
                    Intent intent = new Intent(mActivity, RunningShareActivity.class);
                    intent.putExtra("url",mScreenUrl);
                    startActivity(intent);
                }
                break;
            case R.id.img_activity_running_result_mapstatus:
                if (mImgActivityRunningResultMapstatus.isSelected()) {
                    mImgActivityRunningResultMapstatus.setSelected(false);
                    showMapBack();
                } else {
                    mImgActivityRunningResultMapstatus.setSelected(true);
                    hideMapBack();
                }
                break;
            case R.id.ll_activity_running_result_dynamic:
                if (mPunchId!=-1) {
                    Intent dynamicIntent = new Intent(mActivity, CreateDynamicActivity.class);
                    dynamicIntent.putExtra("type", "1");
                    dynamicIntent.putExtra("punch_id", mPunchId);
                    startActivity(dynamicIntent);
                }else{
                    Toast.makeText(mActivity, "打卡失败，请前往社区发动态", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.img_activity_running_result_back:
                if (mType==1) {
                    startActivity(new Intent(mActivity,MainActivity.class));
                }else{
                    finish();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mType==1) {
            startActivity(new Intent(mActivity,MainActivity.class));
        }else{
            finish();
        }
    }

    /**
     * 地图截图回调
     *
     * @param bitmap
     */
    @Override
    public void onMapScreenShot(Bitmap bitmap) {
        LayoutInflater layoutInflater = mActivity.getLayoutInflater();
        final View inflate = layoutInflater.inflate(R.layout.activity_running_result_share_data, mRlActivityRunningRoot, false);

        ImageView imgActivityResultShareDataMap = (ImageView) inflate.findViewById(R.id.img_activity_result_share_data_map);
        RelativeLayout rlActivityResultShareDataContent = (RelativeLayout) inflate.findViewById(R.id.rl_activity_result_share_data_content);
        TextView tvActivityResultShareDataTime = (TextView) inflate.findViewById(R.id.tv_activity_result_share_data_time);
        TextView tvActivityResultShareDataDistance = (TextView) inflate.findViewById(R.id.tv_activity_result_share_data_distance);
        TextView tvActivityResultShareDataSpeed = (TextView) inflate.findViewById(R.id.tv_activity_result_share_data_speed);
        TextView tvActivityResultShareDataPeriod = (TextView) inflate.findViewById(R.id.tv_activity_result_share_data_period);
        final ImageView imgActivityResultShareDataHead = (ImageView) inflate.findViewById(R.id.img_activity_result_share_data_head);
        TextView tvActivityResultShareDataName = (TextView) inflate.findViewById(R.id.tv_activity_result_share_data_name);

        tvActivityResultShareDataTime.setText(simpleDateFormat.format(new Date()));
        tvActivityResultShareDataPeriod.setText(mPeriod);
        tvActivityResultShareDataSpeed.setText(mSpeed);
        tvActivityResultShareDataDistance.setText(mDistance);
        tvActivityResultShareDataName.setText(SaveUtil.getString(SaveConstants.USER_NAME, ""));
        imgActivityResultShareDataMap.setImageBitmap(bitmap);
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(mActivity).asBitmap()
                .load(SaveUtil.getString(SaveConstants.USER_HEAD, ""))
                .apply(requestOptions)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        mRlActivityRunningRoot.addView(inflate, 0);
                        inflate.setDrawingCacheEnabled(true);
                        inflate.post(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap screenShot = inflate.getDrawingCache();
                                uploadImage(screenShot);
                            }
                        });
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imgActivityResultShareDataHead.setImageBitmap(resource);
                        mRlActivityRunningRoot.addView(inflate, 0);
                        inflate.setDrawingCacheEnabled(true);
                        inflate.post(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap screenShot= inflate.getDrawingCache();
                                uploadImage(screenShot);
                            }
                        });
                    }
                });

    }

    /**
     * 地图截图回调
     *
     * @param bitmap
     * @param i
     */
    @Override
    public void onMapScreenShot(Bitmap bitmap, int i) {

    }

    /**
     * 上传图片
     */
    public void uploadImage(Bitmap bitmap) {
        if (bitmap==null) {
            return;
        }
        //系统相册路径
        String cachePath = mActivity.getExternalCacheDir().getAbsolutePath();
        final File file = new File(cachePath, System.currentTimeMillis() + ".jpg");
        FileOutputStream fos = null;
        try {
            int quality = 100;
            fos = new FileOutputStream(file);
            //第一个参数代表要转化成什么格式的图片   第二个参数代表压缩程度   第三个参数转化后文件的输出流对象
            // Bitmap.CompressFormat.JPEG   Bitmap.CompressFormat.PNG
            //Bitmap.CompressFormat.WEBP
            boolean compress =bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
            if (compress) {
                Map<String, String> map = new HashMap<>();
                map.put("type", "trace_punch");
                map.put("square", "0");
                HttpUtils.getInstance().upload(Constants.UPLOAD_IMAGE, map, "file", file, new HttpCallback<UploadFileBean>() {
                    @Override
                    public void onSuccessExecute(UploadFileBean uploadFileBean) {
                        if (uploadFileBean.getStatus() == 0) {
                            UploadFileBean.DataBean data = uploadFileBean.getData();
                            mScreenUrl = data.getUrl();
                            String key = data.getKey();
                            punch(key);
                            file.delete();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 提交打卡
     * @param image_key
     */
    private void punch(String image_key) {
        Map<String,String> map=new HashMap<>();
        map.put("image",image_key);
        map.put("time",mTotal_minute+"");
        map.put("distance",mDistance);
        map.put("cross_country_run","0");
        map.put("trace_id",mTrace_id);
        HttpUtils.getInstance().post(Constants.SCREEN_PUNCH, map, new HttpCallback<ScreenPunchBean>() {
            @Override
            public void onPreExcute() {
                super.onPreExcute();
                if (mLoadingDialog!=null) {
                    mLoadingDialog.dismiss();
                }
            }

            @Override
            public void onFailureExecute() {
                super.onFailureExecute();
                if (mLoadingDialog!=null) {
                    mLoadingDialog.dismiss();
                }
            }

            @Override
            public void onSuccessExecute(ScreenPunchBean screenPunchBean) {
                ScreenPunchBean.DataBean data = screenPunchBean.getData();
                if (screenPunchBean.getStatus()!=0) {
                    String message = screenPunchBean.getMessage();
                    Toast toast = Toast.makeText(mActivity, message, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                if (data!=null) {
                    mPunchId = data.getId();
                    Toast toast = Toast.makeText(mActivity, "上传成功", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
            }
        });
    }

}
