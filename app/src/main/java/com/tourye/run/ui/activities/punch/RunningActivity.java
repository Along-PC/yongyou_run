package com.tourye.run.ui.activities.punch;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.CommonJsonBean;
import com.tourye.run.bean.TraceSubmitBean;
import com.tourye.run.location.LocationService;
import com.tourye.run.location.LocationStatusManager;
import com.tourye.run.location.Utils;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.receiver.LocationAlarmReceiver;
import com.tourye.run.ui.dialogs.RunningCancelDialog;
import com.tourye.run.ui.dialogs.RunningPromptDialog;
import com.tourye.run.utils.CacheUtils;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.NetStateUtils;
import com.tourye.run.utils.NoneNetUtils;
import com.tourye.run.utils.RecordUtils;
import com.tourye.run.utils.SaveUtil;
import com.tourye.run.views.EndView;
import com.tourye.run.views.SlideLockView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import io.reactivex.functions.Consumer;

/**
 * RunningActivity
 * author:along
 * 2019/3/12 5:16 PM
 * <p>
 * 描述:跑步页面
 */

public class RunningActivity extends BaseActivity implements View.OnClickListener, TextToSpeech.OnInitListener {

    private TextView mTvActivityRunningDistance;
    private RelativeLayout mRlActivityRunningLock;
    private SlideLockView mSlideActivityRunning;
    private RelativeLayout mRlActivityRunningCountdown;//倒计时模块
    private TextView mTvActivityRunningCountdown;
    private RelativeLayout mRlActivityRunningRoot;
    private ImageView mImgActivityRunningMap;//地图页面入口
    private ImageView mImgActivityRunningSetting;//跑步设置
    private LinearLayout mLlActivityRunningOperate;
    private ImageView mImgActivityRunningResume;//继续
    private EndView mImgActivityRunningExit;//结束
    private TextView mTvActivityRunningSpeed;//配速
    private TextView mTvActivityRunningTime;//用时
    private TextView mTvActivityRunningGps;//gps信号
    private ImageView mImgActivityRunningLockStatus;//锁屏状态
    private ImageView mImgActivityRunningStart;//开始跑步
    private int unlock_need_show = 1;//解锁之后需要显示的模块 1=开始暂停模块  2=继续结束模块

    private boolean mIsStartRunning;//是否开启定位
    private boolean mIsCloseLocationResouce;//是否关闭了和定位有关的资源
    private boolean mIsPause;//是否暂停跑步

    private long mTotal_time = 60 * 63 * 1000 + 375;//总共用时
    private long mLast_time;//上一秒时间
    private long mTotal_minute;//总共分钟数
    private int runningGoal = 0;//跑步目标距离
    private long lastKilometreTimer;//上次满百公里的时间
    private float mTotalDistance = 10000;//总共行走距离
    private long gpsLastBroadcastTime;//gps信号上次播报时间

    private int countdown = 3;//倒计时数字
    private static final String TAG = "RunningActivity";

    private TextToSpeech mTextToSpeech;//语音朗读
    private RunningCancelDialog runningCancelDialog;//长按提示弹窗

    //定位点集合
    private ArrayList<LatLng> mLatLngs = new ArrayList<LatLng>();
    private ArrayList<TraceSubmitBean.PointsBean> mPointsBeans = new ArrayList<>();
    private LatLng mLatLngLast = null;//上次定位的点
    //接受后台定位服务的广播action
    public static final String RECEIVER_ACTION = "run_location_in_background";
    private IntentFilter intentFilter = new IntentFilter(RECEIVER_ACTION);
    private LocationReceiver locationReceiver;
    //todo
    //创建Alarm并启动
    Intent alarmIntent;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    private String finalDistance;//最终的距离
    private String finalSpeed;//最终的配速
    private String finalTime;//最终的时间
    private int mSpeed_munite;//配速---分
    private int mSpeed_second;//配速---秒

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mImgActivityRunningStart.isSelected()) {
                speech("运动已暂停");
                mIsPause = true;
                mLatLngLast = null;
                mLlActivityRunningOperate.setVisibility(View.VISIBLE);
                mImgActivityRunningStart.setVisibility(View.GONE);
                mHandler.removeMessages(10086);
                mHandler.removeMessages(10010);
            } else {
                mRlActivityRunningCountdown.setVisibility(View.VISIBLE);
                countdown = 3;
                //倒计时动画
                mTvActivityRunningCountdown.setTextColor(Color.parseColor("#FFFFFF"));
                mTvActivityRunningCountdown.setText(countdown + "");
                startCountdown(countdown);
            }
        }
    };

    private AlertDialog mNetCheckDialog;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10086:
                    long currentTime = System.currentTimeMillis();
                    mTotal_time += currentTime - mLast_time;

                    long second_unit = 1000;
                    long minute_unit = second_unit * 60;
                    long hour_unit = minute_unit * 60;

                    long hour = mTotal_time / hour_unit;
                    long minute = (mTotal_time - hour * hour_unit) / minute_unit;
                    long second = (mTotal_time - hour * hour_unit - minute * minute_unit) / second_unit;

                    mTotal_minute = hour * 60 + minute;
                    //格式化时间
                    String hour_str = String.format("%0" + 2 + "d", hour);
                    String minute_str = String.format("%0" + 2 + "d", minute);
                    String second_str = String.format("%0" + 2 + "d", second);

                    mLast_time = currentTime;
                    mTvActivityRunningTime.setText(hour_str + "：" + minute_str + "：" + second_str);
                    mHandler.sendEmptyMessageDelayed(10086, 1000);
                    break;
                case 10010:
                    mHandler.sendEmptyMessageDelayed(10010, 30000);
                    int netState = NetStateUtils.getNetState(mActivity);
                    if (netState == NetStateUtils.NETWORK_STATE_NONE) {
                        speech("当前网络状态异常，请检查网络状态");
                        if (mNetCheckDialog == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                                    .setMessage("当前网络状态异常，请检查网络状态")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            mNetCheckDialog = null;
                                        }
                                    });
                            mNetCheckDialog = builder.create();
                        }
                        mNetCheckDialog.show();
                    }
                    break;
            }
        }
    };

    @Override
    public void initView() {
        mRlActivityRunningRoot = (RelativeLayout) findViewById(R.id.rl_activity_running_root);
        mTvActivityRunningDistance = (TextView) findViewById(R.id.tv_activity_running_distance);
        mRlActivityRunningLock = (RelativeLayout) findViewById(R.id.rl_activity_running_lock);
        mSlideActivityRunning = (SlideLockView) findViewById(R.id.slide_activity_running);
        mRlActivityRunningCountdown = (RelativeLayout) findViewById(R.id.rl_activity_running_countdown);
        mTvActivityRunningCountdown = (TextView) findViewById(R.id.tv_activity_running_countdown);
        mImgActivityRunningMap = (ImageView) findViewById(R.id.img_activity_running_map);
        mImgActivityRunningSetting = (ImageView) findViewById(R.id.img_activity_running_setting);
        mLlActivityRunningOperate = (LinearLayout) findViewById(R.id.ll_activity_running_operate);
        mImgActivityRunningResume = (ImageView) findViewById(R.id.img_activity_running_resume);
        mImgActivityRunningExit = (EndView) findViewById(R.id.img_activity_running_exit);
        mTvActivityRunningSpeed = (TextView) findViewById(R.id.tv_activity_running_speed);
        mTvActivityRunningTime = (TextView) findViewById(R.id.tv_activity_running_time);
        mTvActivityRunningGps = (TextView) findViewById(R.id.tv_activity_running_gps);
        mImgActivityRunningLockStatus = (ImageView) findViewById(R.id.img_activity_running_lock_status);
        mImgActivityRunningStart = (ImageView) findViewById(R.id.img_activity_running_start);

        //获取状态栏高度设置padding---适配状态栏
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int result = this.getResources().getDimensionPixelSize(resourceId);
            mRlActivityRunningRoot.setPadding(0, result, 0, 0);
        }

        setListener();
    }

    @Override
    public void initData() {
        if (SaveUtil.getBoolean(SaveConstants.IS_SHOW_RUNNING_DIALOG, true)) {
            RunningPromptDialog runningPromptDialog = new RunningPromptDialog(mActivity);
            runningPromptDialog.show();
        }
        SaveUtil.putBoolean(SaveConstants.IS_SHOW_RUNNING_DIALOG, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SaveUtil.getBoolean(SaveConstants.IS_VOICE_BROADCAST, true)) {
            //关闭tts资源
            if (mTextToSpeech != null) {
                mTextToSpeech.stop();
                mTextToSpeech.shutdown();
            }
            mTextToSpeech = new TextToSpeech(this, this);
        } else {
            //关闭tts资源
            if (mTextToSpeech != null) {
                mTextToSpeech.stop();
                mTextToSpeech.shutdown();
            }
        }
        //如果设置了屏幕常亮
        if (SaveUtil.getBoolean(SaveConstants.IS_LONG_LIGHT, true)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    private void setListener() {

        mImgActivityRunningStart.setOnClickListener(mOnClickListener);
        mImgActivityRunningMap.setOnClickListener(this);
        mSlideActivityRunning.setOnEndCallback(new SlideLockView.OnEndCallback() {
            @Override
            public void onEnd() {
                mRlActivityRunningLock.setVisibility(View.GONE);
                mImgActivityRunningLockStatus.setSelected(false);
                if (unlock_need_show == 1) {
                    mImgActivityRunningStart.setVisibility(View.VISIBLE);
                    mLlActivityRunningOperate.setVisibility(View.GONE);
                } else if (unlock_need_show == 2) {
                    mImgActivityRunningStart.setVisibility(View.GONE);
                    mLlActivityRunningOperate.setVisibility(View.VISIBLE);
                }
            }
        });
        mImgActivityRunningSetting.setOnClickListener(this);
        mImgActivityRunningResume.setOnClickListener(this);
        mImgActivityRunningExit.setOnEndProxy(new EndView.OnEndProxy() {
            @Override
            public void onEnd() {
                stopLocation();
                mLlActivityRunningOperate.setVisibility(View.GONE);
                mImgActivityRunningStart.setVisibility(View.VISIBLE);
                mImgActivityRunningStart.setSelected(false);
                filterData(mPointsBeans, mTotalDistance, mTotal_time);
                if (runningCancelDialog != null) {
                    runningCancelDialog.dismiss();
                }
            }

            @Override
            public void onCancel() {
                if (runningCancelDialog == null) {
                    runningCancelDialog = new RunningCancelDialog(mActivity);
                }
                //向上便宜自身和目标控件的高度
                runningCancelDialog.showAsDropDown(mImgActivityRunningExit, 0, -DensityUtils.dp2px(128));
            }
        });
        mImgActivityRunningLockStatus.setOnClickListener(this);
    }

    /**
     * 初始化tts
     *
     * @param status
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTextToSpeech.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 语音播报内容
     *
     * @param word
     */
    public void speech(String word) {
        if (mTextToSpeech == null) {
            return;
        }
        //播报倒计时
        // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
        mTextToSpeech.setPitch(1.0f);
        //设定语速 ，默认1.0正常语速
        mTextToSpeech.setSpeechRate(1.0f);
        //朗读，注意这里三个参数的added in API level 4   四个参数的added in API level 21  TextToSpeech.QUEUE_ADD会把新的朗读任务放在队列之后
        mTextToSpeech.speak(word, TextToSpeech.QUEUE_ADD, null);
    }

    /**
     * 倒计时动画
     */
    private void startCountdown(int index) {
        if (index > 0) {
            speech(index + "");
        }
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setInterpolator(new LinearInterpolator());
        scaleAnimation.setDuration(1000);
        mTvActivityRunningCountdown.startAnimation(scaleAnimation);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                countdown--;
                if (countdown > -1) {
                    if (countdown == 0) {
                        speech("开始跑步");
                        mTvActivityRunningCountdown.setTextColor(Color.parseColor("#FF5034"));
                        mTvActivityRunningCountdown.setText("GO");
                        startLocation();
                    } else {
                        mTvActivityRunningCountdown.setTextColor(Color.parseColor("#FFFFFF"));
                        mTvActivityRunningCountdown.setText(countdown + "");
                    }

                    startCountdown(countdown);
                } else {
                    mImgActivityRunningLockStatus.setVisibility(View.VISIBLE);
                    mRlActivityRunningCountdown.setVisibility(View.GONE);
                    boolean is_default_lock = SaveUtil.getBoolean(SaveConstants.IS_DEFAULT_LOCK, true);
                    mImgActivityRunningStart.setSelected(true);

                    if (is_default_lock) {
                        //如果设置了默认锁屏
                        mImgActivityRunningStart.setVisibility(View.GONE);
                        mRlActivityRunningLock.setVisibility(View.VISIBLE);
                        mImgActivityRunningLockStatus.setSelected(true);
                        unlock_need_show = 1;
                    } else {
                        //如果没有设置默认锁屏
                        mImgActivityRunningLockStatus.setSelected(false);
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 开始定位
     */
    private void startLocation() {
        //开始定位
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        if (aBoolean) {
                            //开始计时
                            mTotal_time = 0;
                            mLast_time = System.currentTimeMillis();
                            mHandler.sendEmptyMessageDelayed(10086, 1000);

                            mIsStartRunning = true;
                            mIsPause = false;
                            mTotalDistance = 0;
                            mHandler.sendEmptyMessageDelayed(10010, 30000);
//                            Intent intent = new Intent(mActivity, LocationTempService.class);
//                            bindService(intent,mServiceConnection,Context.BIND_AUTO_CREATE);
                            alarmIntent = new Intent(mActivity, LocationAlarmReceiver.class);
                            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            pendingIntent = PendingIntent.getBroadcast(mActivity, 0, alarmIntent, 0);
                            // 一段时间唤醒一次
                            long second = 1000 * 60;
                            // pendingIntent 为发送广播
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+second, pendingIntent);
//                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                                alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis()+second, pendingIntent);
//                            } else {
//                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), second, pendingIntent);
//                            }
                            locationReceiver = new LocationReceiver();
                            registerReceiver(locationReceiver, intentFilter);
                            startService(new Intent(mActivity, LocationService.class));
                            LocationStatusManager.getInstance().resetToInit(getApplicationContext());

                        } else {
                            Toast.makeText(mActivity, "请前往设置开启定位权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 处理定位返回的数据
     *
     * @param amapLocation
     */
    public void processMapData(AMapLocation amapLocation) {
        //定位成功
        if (amapLocation != null && amapLocation.getErrorCode() == 0) {
            //todo
            //精度大于200拦截
            if (amapLocation.getAccuracy() > 200) {
                return;
            }
            LatLng latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
            float distance = AMapUtils.calculateLineDistance(mLatLngLast, latLng);
            //todo
            //两次定位期间间距大于50舍弃
//            if (distance>50) {
//                return;
//            }
            TraceSubmitBean.PointsBean pointsBean = new TraceSubmitBean.PointsBean(latLng.longitude, latLng.latitude, amapLocation.getTime());
            pointsBean.setSpeed(amapLocation.getSpeed());
            mPointsBeans.add(pointsBean);
            long currentTime = System.currentTimeMillis();
            boolean isBroadcastGpsStatus = currentTime - gpsLastBroadcastTime > 30 * 1000;
            switch (amapLocation.getGpsAccuracyStatus()) {
                case AMapLocation.GPS_ACCURACY_BAD:
                    if (isBroadcastGpsStatus) {
                        gpsLastBroadcastTime = currentTime;
                        speech("gps信号弱");
                    }
                    mTvActivityRunningGps.setText("GPS 弱");
                    break;
                case AMapLocation.GPS_ACCURACY_GOOD:
                    mTvActivityRunningGps.setText("GPS 强");
                    break;
                case AMapLocation.GPS_ACCURACY_UNKNOWN:
                    mTvActivityRunningGps.setText("GPS 一般");
                    break;
            }
            if (mLatLngLast != null) {
                mTotalDistance += distance;
                mTvActivityRunningDistance.setText(String.format("%.2f", mTotalDistance / 1000));
                float timeSpacingMinute = mTotal_time / 1000f / 60;
                if (mTotalDistance > 0) {
                    float speed = timeSpacingMinute / (mTotalDistance / 1000);
                    mSpeed_munite = (int) speed;
                    mSpeed_second = (int) ((speed - mSpeed_munite) * 60);
                    mTvActivityRunningSpeed.setText(mSpeed_munite + "'" + mSpeed_second + "''");

                    speechDisTance(mTotalDistance);
                }
            }
            mLatLngs.add(latLng);
            mLatLngLast = latLng;
        }
    }

    /**
     * 到达固定公里数进行播报
     *
     * @param totalDistance
     */
    private void speechDisTance(float totalDistance) {

        if (totalDistance - runningGoal * 1000 > 1000) {
            runningGoal++;
            long second_unit = 1000;
            long minute_unit = second_unit * 60;
            long hour_unit = minute_unit * 60;

            long minute = mTotal_time / minute_unit;
            long second = (mTotal_time - minute * minute_unit) / second_unit;

            long currentTime = System.currentTimeMillis();

            String speechWord = "叮咚，你已经跑了" + runningGoal + "公里，用时" + minute + "分钟" + second + "秒，";
            if (lastKilometreTimer != 0) {
                long interval = currentTime - lastKilometreTimer;
                long thisTimeMinute = interval / minute_unit;
                long thisTimeSecond = (interval - thisTimeMinute * minute_unit) / second_unit;
                speechWord = speechWord + "最近一公里耗时" + thisTimeMinute + "分钟" + thisTimeSecond + "秒";
            }
            boolean is_joined = SaveUtil.getBoolean(SaveConstants.IS_JOINED, false);
            if (is_joined) {
                String level_group = SaveUtil.getString(SaveConstants.LEVEL_GROUP, "");
                int parseInt = Integer.parseInt(level_group);
                if (parseInt > runningGoal) {
                    speechWord = speechWord + "，距离打卡目标还有" + (parseInt - runningGoal) + "公里，加油";
                } else if (parseInt == runningGoal) {
                    speechWord = speechWord + "已达成今日打卡目标" + parseInt + "公里，太棒了";
                }
            }
            speech(speechWord);
            lastKilometreTimer = currentTime;

        }

    }

    /**
     * 过滤定位收集数据
     *
     * @param pointsBeans
     * @param distance
     * @param total_time
     */
    private void filterData(final ArrayList<TraceSubmitBean.PointsBean> pointsBeans, float distance, long total_time) {

        int netState = NetStateUtils.getNetState(mActivity);

        //上传时断网弹窗提示
        if (netState == NetStateUtils.NETWORK_STATE_NONE) {
            NoneNetUtils.showDialog(mActivity);
            return;
        }

        if (distance < 10) {
            Toast toast = Toast.makeText(mActivity, "打卡距离过短", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        if (total_time < 1000 * 60) {
            Toast toast = Toast.makeText(mActivity, "打卡时间过短", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }

        boolean is_joined = SaveUtil.getBoolean(SaveConstants.IS_JOINED, false);
        if (is_joined) {
            String level_group = SaveUtil.getString(SaveConstants.LEVEL_GROUP, "");
            int running_level = Integer.parseInt(level_group);
            if (mSpeed_munite > 15 || mSpeed_munite < 2 || mTotalDistance < running_level * 1000) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                        .setMessage("本次跑步记录未符合百日跑打卡要求，不会记打卡成功哟")
                        .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                submitTrace(pointsBeans, mTotalDistance, mTotal_time);
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();
                return;
            } else {
                submitTrace(pointsBeans, mTotalDistance, mTotal_time);
            }
        } else {
            submitTrace(pointsBeans, mTotalDistance, mTotal_time);
        }
    }

    /**
     * 上传打卡轨迹
     */
    public void submitTrace(ArrayList<TraceSubmitBean.PointsBean> pointsBeans, float totalDistance, float totalTime) {

        TraceSubmitBean traceSubmitBean = new TraceSubmitBean();
        traceSubmitBean.setDistance(new DecimalFormat("0.00").format(totalDistance / 1000));
        traceSubmitBean.setTime((int) (totalTime / 1000 / 60));
        traceSubmitBean.setPoints(pointsBeans);
        traceSubmitBean.setSpeed(finalSpeed);
        traceSubmitBean.setTime_str(finalTime);
        Gson gson = new Gson();
        String json = gson.toJson(traceSubmitBean);
        HttpUtils.getInstance().post_json(Constants.STORE_TRACE, json, new HttpCallback<CommonJsonBean>() {
            @Override
            public void onSuccessExecute(CommonJsonBean commonJsonBean) {
                String trace_id = commonJsonBean.getData();
                if (!TextUtils.isEmpty(trace_id)) {
                    try{
                        Intent intent = new Intent(mActivity, RunningResultActivity.class);
                        intent.putExtra("period", finalTime);
                        intent.putExtra("total_minute", mTotal_minute);
                        intent.putExtra("distance", finalDistance);
                        intent.putExtra("speed", finalSpeed);
//                        intent.putExtra("locations", mLatLngs);
                        intent.putExtra("trace_id", trace_id);
                        intent.putExtra("type", 1);
                        CacheUtils.getInstance().cachedata("location",mPointsBeans);
//                        intent.putExtra("location_points", mPointsBeans);
                        startActivity(intent);
                        speech("跑步结束，做做拉伸，放松下肌肉吧");
                    }catch (Exception e){
                        e.printStackTrace();
                    }

//                    finish();
                } else {
                    Toast.makeText(RunningActivity.this, "上传轨迹失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 停止定位
     */
    public void stopLocation() {

        finalDistance = mTvActivityRunningDistance.getText().toString();
        finalSpeed = mTvActivityRunningSpeed.getText().toString();
        finalTime = mTvActivityRunningTime.getText().toString();
        mTvActivityRunningDistance.setText("0.00");
        mTvActivityRunningSpeed.setText("0’00”");
        mTvActivityRunningTime.setText("00：00：00");

        mIsStartRunning = false;
        mIsCloseLocationResouce = true;
        if (locationReceiver != null) {
            //解除广播
            unregisterReceiver(locationReceiver);
            //关闭服务
            sendBroadcast(Utils.getCloseBrodecastIntent());
        }
        //解绑服务
//        unbindService(mServiceConnection);
        //todo
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            alarmManager = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsStartRunning && !mIsCloseLocationResouce) {
            //解除广播
            unregisterReceiver(locationReceiver);
            //解绑服务
//            unbindService(mServiceConnection);
            //关闭服务
            sendBroadcast(Utils.getCloseBrodecastIntent());
        }
        //关闭tts资源
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            alarmManager = null;
        }
        if (mNetCheckDialog != null) {
            mNetCheckDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        //锁屏状态下取消返回事件
        if (mRlActivityRunningLock.getVisibility() == View.VISIBLE) {
            return;
        }
        if (mIsStartRunning) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle("结束打卡")
                    .setMessage("确定结束打卡吗？")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            stopLocation();
                            dialogInterface.dismiss();
                            finish();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean isNeedTitle() {
        return false;
    }

    @Override
    public boolean isNeedDarkState() {
        return false;
    }

    @Override
    public int getRootView() {
        return R.layout.activity_running;
    }

    @Override
    public void onClick(View v) {
        //锁屏状态下取消点击事件
        if (mRlActivityRunningLock.getVisibility() == View.VISIBLE) {
            return;
        }
        switch (v.getId()) {
            case R.id.img_activity_running_setting:
                startActivity(new Intent(mActivity, RunningSettingActivity.class));
                break;
            case R.id.img_activity_running_resume:
                speech("运动已恢复");
                mIsPause = false;
                mHandler.sendEmptyMessageDelayed(10010, 30000);
                //隐藏提示弹窗
                if (runningCancelDialog != null) {
                    runningCancelDialog.dismiss();
                }
                mLast_time = System.currentTimeMillis();
                mHandler.sendEmptyMessageDelayed(10086, 1000);
                mLlActivityRunningOperate.setVisibility(View.GONE);
                mImgActivityRunningStart.setVisibility(View.VISIBLE);
                break;
            case R.id.img_activity_running_lock_status:
                if (!mImgActivityRunningLockStatus.isSelected()) {
                    mImgActivityRunningLockStatus.setSelected(true);
                    if (mLlActivityRunningOperate.getVisibility() == View.VISIBLE) {
                        unlock_need_show = 2;
                    } else {
                        unlock_need_show = 1;
                    }
                    mRlActivityRunningLock.setVisibility(View.VISIBLE);
                    mLlActivityRunningOperate.setVisibility(View.GONE);
                    mImgActivityRunningStart.setVisibility(View.GONE);
                }
                break;
            case R.id.img_activity_running_map:
                if (mIsStartRunning) {
//                    startRevealActivity(v);
                    Intent intent = new Intent(mActivity, MapActivity.class);
                    intent.putExtra("total_distance", mTotalDistance);
                    intent.putExtra("total_time", mTotal_time);
                    intent.putExtra("is_pause", mIsPause);
                    intent.putParcelableArrayListExtra("locations", mLatLngs);
                    startActivity(intent);
                }
                break;
        }
    }

    //接受定位数据广播
    public class LocationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (mIsPause) {
                return;
            }
            String action = intent.getAction();
            if (action.equals(RECEIVER_ACTION)) {
                String locationResult = intent.getStringExtra("result");
                AMapLocation aMapLocation = (AMapLocation) intent.getParcelableExtra("data");
                processMapData(aMapLocation);
            }
        }

    }

}
