package com.tourye.run.ui.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseFragment;
import com.tourye.run.bean.AdvertiseBean;
import com.tourye.run.bean.CalendarPunchBean;
import com.tourye.run.bean.SignupInfoBean;
import com.tourye.run.bean.TodayPunchBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.punch.RunningActivity;
import com.tourye.run.ui.activities.punch.ScreenPunchActivity;
import com.tourye.run.ui.adapter.CalendarAdapter;
import com.tourye.run.ui.adapter.SignupAdvertiseAdapter;
import com.tourye.run.utils.DateFormatUtils;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.LocationUtils;
import com.tourye.run.utils.SaveUtil;
import com.tourye.run.views.TrapezoidView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

/**
 * Created by longlongren on 2019/3/11.
 * <p>
 * introduce:打卡页面
 */
public class PunchFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView mRecyclerFragmentPunchWeek;//周日历
    private RecyclerView mRecyclerFragmentPunchMonth;//月日历
    private TrapezoidView mTrapeziodFragmentPunch;
    private ImageView mImgFragmentPunchLast;//上个月
    private TextView mTvFragmentPunchCurrent;//当前日期
    private ImageView mImgFragmentPunchNext;//下个月
    private LinearLayout mLlFragmentPunchDate;
    private TextView mTvFragmentPunchDays;//累计打卡天数
    private TextView mTvFragmentPunchScreenshot;//截图打卡
    private TextView mTvFragmentPunchRun;//轨迹打卡
    private TextView mTvFragmentPunchGroup;
    private TextView mTvFragmentPunchInteval;//活动起止日期
    private TextView mTvFragmentPunchState;//打卡状态
    private ViewPager mVpFragmentPunchAdvertising;//广告轮播
    private RadioGroup mRgFragmentPunchAdvertising;
    private CardView mCardFragmentPunchJoined;
    private CardView mCardFragmentPunchNojoined;
    private LinearLayout mLlFragmentPunchContent;
    private RelativeLayout rlFragmentPunchLast;//上个月点击区域
    private RelativeLayout rlFragmentPunchNext;//下个月点击区域
    private ImageView mImgFragmentPunchDirective;

    private int mOffset;//月份偏移量
    private int mStartHeight = DensityUtils.dp2px(250);//日历高度
    private int mDateHeight = DensityUtils.dp2px(30);//日历标题高度

    private List<CalendarPunchBean.DataBeanX> weekCalendarBeans = new ArrayList<>();
    private List<CalendarPunchBean.DataBeanX> monthCalendarBeans = new ArrayList<>();

    private CalendarAdapter weekAdapter;
    private CalendarAdapter monthAdapter;

    private int mAdvertiseSize;//广告数量

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10010:
                    int currentItem = mVpFragmentPunchAdvertising.getCurrentItem();
                    ++currentItem;
                    RadioButton childAt = (RadioButton) mRgFragmentPunchAdvertising.getChildAt(currentItem % mAdvertiseSize);
                    childAt.setChecked(true);
                    mVpFragmentPunchAdvertising.setCurrentItem(currentItem);
                    mHandler.sendEmptyMessageDelayed(10010, 3000);
                    break;
            }
        }
    };

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!v.isSelected()) {
                v.setSelected(true);
                changeToMonth();
            } else {
                changeToWeek();
                v.setSelected(false);
            }
        }
    };

    @Override
    public void initView(View view) {
        mRecyclerFragmentPunchWeek = (RecyclerView) view.findViewById(R.id.recycler_fragment_punch_week);
        mRecyclerFragmentPunchMonth = (RecyclerView) view.findViewById(R.id.recycler_fragment_punch_month);
        mTrapeziodFragmentPunch = (TrapezoidView) view.findViewById(R.id.trapeziod_fragment_punch);
        mImgFragmentPunchLast = (ImageView) view.findViewById(R.id.img_fragment_punch_last);
        mTvFragmentPunchCurrent = (TextView) view.findViewById(R.id.tv_fragment_punch_current);
        mImgFragmentPunchNext = (ImageView) view.findViewById(R.id.img_fragment_punch_next);
        mLlFragmentPunchDate = (LinearLayout) view.findViewById(R.id.ll_fragment_punch_date);
        mTvFragmentPunchDays = (TextView) view.findViewById(R.id.tv_fragment_punch_days);
        mTvFragmentPunchScreenshot = (TextView) view.findViewById(R.id.tv_fragment_punch_screenshot);
        mTvFragmentPunchRun = (TextView) view.findViewById(R.id.tv_fragment_punch_run);
        mTvFragmentPunchGroup = (TextView) view.findViewById(R.id.tv_fragment_punch_group);
        mTvFragmentPunchInteval = (TextView) view.findViewById(R.id.tv_fragment_punch_inteval);
        mTvFragmentPunchState = (TextView) view.findViewById(R.id.tv_fragment_punch_state);
        mVpFragmentPunchAdvertising = (ViewPager) view.findViewById(R.id.vp_fragment_punch_advertising);
        mRgFragmentPunchAdvertising = (RadioGroup) view.findViewById(R.id.rg_fragment_punch_advertising);
        mCardFragmentPunchJoined = (CardView) view.findViewById(R.id.card_fragment_punch_joined);
        mCardFragmentPunchNojoined = (CardView) view.findViewById(R.id.card_fragment_punch_nojoined);
        mLlFragmentPunchContent = (LinearLayout) view.findViewById(R.id.ll_fragment_punch_content);
        rlFragmentPunchLast = (RelativeLayout) view.findViewById(R.id.rl_fragment_punch_last);
        rlFragmentPunchNext = (RelativeLayout) view.findViewById(R.id.rl_fragment_punch_next);
        mImgFragmentPunchDirective = (ImageView) view.findViewById(R.id.img_fragment_punch_directive);

        mTvTitle.setText("打卡");
        String level_group = SaveUtil.getString(SaveConstants.LEVEL_GROUP, "");
        mTvFragmentPunchGroup.setText(level_group + "公里组别");

        mRecyclerFragmentPunchMonth.setLayoutManager(new GridLayoutManager(mActivity, 7));
        monthAdapter = new CalendarAdapter(mActivity);
        mRecyclerFragmentPunchMonth.setAdapter(monthAdapter);

        initListener();

    }

    @Override
    public void initData() {

        initMonth(0);

        initWeek();

        initAdvertise();

        getSignupList();

    }

    private void initListener() {
        mTrapeziodFragmentPunch.setOnClickListener(mOnClickListener);
        mTvFragmentPunchScreenshot.setOnClickListener(this);
        mTvFragmentPunchRun.setOnClickListener(this);
        rlFragmentPunchLast.setOnClickListener(this);
        rlFragmentPunchNext.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && !mFirstLoad) {
            getCalendarData();
            getTodayPunchData();
        }

    }

    @Override
    public void refreshData() {
        super.refreshData();
        getTodayPunchData();
    }

    private void changeToMonth() {
        mTrapeziodFragmentPunch.setOnClickListener(null);
        mLlFragmentPunchDate.setVisibility(View.VISIBLE);
        mRecyclerFragmentPunchMonth.setVisibility(View.INVISIBLE);
        mRecyclerFragmentPunchWeek.setVisibility(View.INVISIBLE);
        mRecyclerFragmentPunchMonth.setAdapter(null);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mRecyclerFragmentPunchWeek.getHeight(), mStartHeight);
        ValueAnimator dateAnimator = ValueAnimator.ofFloat(0, mDateHeight);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(mImgFragmentPunchDirective, "rotation", 0, 180);
        dateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mLlFragmentPunchDate.getLayoutParams();
                layoutParams.height = (int) value;
                mLlFragmentPunchDate.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRecyclerFragmentPunchMonth.getLayoutParams();
                layoutParams.height = (int) value;
                mRecyclerFragmentPunchMonth.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mRecyclerFragmentPunchMonth.setVisibility(View.VISIBLE);
                mRecyclerFragmentPunchMonth.setAdapter(monthAdapter);
                mTrapeziodFragmentPunch.setOnClickListener(mOnClickListener);
                mRecyclerFragmentPunchMonth.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        });
        dateAnimator.setDuration(300);
        dateAnimator.start();
        valueAnimator.setDuration(300);
        valueAnimator.start();
        rotation.setDuration(300);
        rotation.start();
    }

    private void changeToWeek() {
        mTrapeziodFragmentPunch.setOnClickListener(null);
        mRecyclerFragmentPunchWeek.setVisibility(View.INVISIBLE);
        mRecyclerFragmentPunchMonth.setVisibility(View.INVISIBLE);
        mStartHeight = mRecyclerFragmentPunchMonth.getHeight();
        mDateHeight = mLlFragmentPunchDate.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mRecyclerFragmentPunchMonth.getHeight(), mRecyclerFragmentPunchWeek.getHeight());
        ValueAnimator dateAnimator = ValueAnimator.ofFloat(mLlFragmentPunchDate.getHeight(), 0);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(mImgFragmentPunchDirective, "rotation", 180, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mRecyclerFragmentPunchMonth.getLayoutParams().height= (int) value;
                mRecyclerFragmentPunchMonth.requestLayout();
            }
        });
        dateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mLlFragmentPunchDate.getLayoutParams();
                layoutParams.height = (int) value;
                mLlFragmentPunchDate.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mRecyclerFragmentPunchWeek.setVisibility(View.VISIBLE);
                mRecyclerFragmentPunchMonth.setVisibility(View.GONE);
                mTrapeziodFragmentPunch.setOnClickListener(mOnClickListener);
            }
        });
        valueAnimator.setDuration(300);
        valueAnimator.start();
        dateAnimator.setDuration(300);
        dateAnimator.start();
        rotation.setDuration(300);
        rotation.start();
    }

    public void initMonth(int offset) {
        Calendar calendar = Calendar.getInstance();
        mOffset += offset;
        calendar.add(Calendar.MONTH, mOffset);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
        int first = calendar.get(Calendar.DAY_OF_WEEK);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
        String yearAndMonth = simpleDateFormat.format(calendar.getTime());
        mTvFragmentPunchCurrent.setText(yearAndMonth);

        monthCalendarBeans.clear();
        for (int i = 0; i < first - 1; i++) {
            monthCalendarBeans.add(new CalendarPunchBean.DataBeanX());
        }
        for (int i = 0; i < day; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, 1 + i);
            Date time = calendar.getTime();
            String date = DateFormatUtils.formatDate(time);
            CalendarPunchBean.DataBeanX calendarBean = new CalendarPunchBean.DataBeanX();
            calendarBean.setDate(date);
            calendarBean.setDay_index(1 + i + "");
            monthCalendarBeans.add(calendarBean);
        }

        int weeks = monthCalendarBeans.size() / 7;//整周有几个
        int week_retain = monthCalendarBeans.size()% 7;//最后一周剩几天
        if (week_retain>0) {
            weeks++;
        }
        mStartHeight=DensityUtils.dp2px(50)*weeks;
        monthAdapter.setOffset(mOffset);
        monthAdapter.setCalendarBeans(monthCalendarBeans);

        getCalendarData();
    }

    public void initWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, 7);
        int week_end_month = calendar.get(Calendar.DAY_OF_MONTH);//本周最后一天在当月是第多少天

        if (week_end_month < 7) {
            calendar.add(Calendar.MONTH, -1);
            // 获取本月天数
            int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int difference = 7 - week_end_month;
            //本周最后一天小于七号，补充上个月数据---月底以此类推
            for (int i = 1; i <= difference; i++) {
                calendar.set(Calendar.DAY_OF_MONTH, actualMaximum - difference + i);
                Date time = calendar.getTime();
                String date = DateFormatUtils.formatDate(time);
                CalendarPunchBean.DataBeanX calendarBean = new CalendarPunchBean.DataBeanX();
                calendarBean.setDate(date);
                calendarBean.setDay_index(actualMaximum - difference + i + "");
                weekCalendarBeans.add(calendarBean);
            }
            for (int i = 0; i < week_end_month; i++) {
                calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DAY_OF_MONTH, 1 + i);
                Date time = calendar.getTime();
                String date = DateFormatUtils.formatDate(time);
                CalendarPunchBean.DataBeanX calendarBean = new CalendarPunchBean.DataBeanX();
                calendarBean.setDate(date);
                calendarBean.setDay_index(1 + i + "");
                weekCalendarBeans.add(calendarBean);
            }
        } else {
            for (int i = 0; i < 7; i++) {
                calendar.set(Calendar.DAY_OF_MONTH, week_end_month - 6 + i);
                Date time = calendar.getTime();
                String date = DateFormatUtils.formatDate(time);
                CalendarPunchBean.DataBeanX calendarBean = new CalendarPunchBean.DataBeanX();
                calendarBean.setDate(date);
                calendarBean.setDay_index(week_end_month - 6 + i + "");
                weekCalendarBeans.add(calendarBean);
            }
        }
        mRecyclerFragmentPunchWeek.setLayoutManager(new GridLayoutManager(mActivity, 7));
        weekAdapter = new CalendarAdapter(mActivity, weekCalendarBeans);
        mRecyclerFragmentPunchWeek.setAdapter(weekAdapter);
    }

    /**
     * 获取日历打卡记录
     */
    public void getCalendarData() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, mOffset);
        int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH) + 1;
        Map<String, String> map = new HashMap<>();
        map.put("month", year + "-" + month);
        HttpUtils.getInstance().get(Constants.CALENDAR_PUNCH_RECORD, map, new HttpCallback<CalendarPunchBean>() {
            @Override
            public void onSuccessExecute(CalendarPunchBean calendarPunchBean) {
                List<CalendarPunchBean.DataBeanX> data = calendarPunchBean.getData();
                if (data == null) {
                    return;
                }
                for (int i = 0; i < data.size(); i++) {
                    CalendarPunchBean.DataBeanX dataBeanX = data.get(i);
                    String date = dataBeanX.getDate();

                    //当且仅当在当月更新周日历的数据
                    if (mOffset == 0) {
                        for (int j = 0; j < weekCalendarBeans.size(); j++) {
                            CalendarPunchBean.DataBeanX weekBean = weekCalendarBeans.get(j);
                            if (date.equals(weekBean.getDate())) {
                                weekBean.setSignin(true);
                                weekBean.setTotal_distance(dataBeanX.getTotal_distance());
                                weekBean.setData(dataBeanX.getData());
                            }
                        }
                    }
                    //刷新月日历的数据
                    for (int j = 0; j < monthCalendarBeans.size(); j++) {
                        CalendarPunchBean.DataBeanX monthBean = monthCalendarBeans.get(j);
                        if (date.equals(monthBean.getDate())) {
                            monthBean.setSignin(true);
                            monthBean.setTotal_distance(dataBeanX.getTotal_distance());
                            monthBean.setData(dataBeanX.getData());
                        }
                    }
                    monthAdapter.setOffset(mOffset);
                    monthAdapter.setCalendarBeans(monthCalendarBeans);
                    weekAdapter.setCalendarBeans(weekCalendarBeans);

                }
            }
        });
    }

    /**
     * 初始化广告
     */
    private void initAdvertise() {
        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().get(Constants.PUNCH_ADVERTISE, map, new HttpCallback<AdvertiseBean>() {
            @Override
            public void onSuccessExecute(AdvertiseBean advertiseBean) {
                List<AdvertiseBean.DataBean> data = advertiseBean.getData();
                if (data != null && data.size() > 0) {
                    mAdvertiseSize = data.size();
                    for (int i = 0; i < data.size(); i++) {
                        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.rightMargin = DensityUtils.dp2px(5);
                        RadioButton radioButton = new RadioButton(mActivity);
                        radioButton.setWidth(DensityUtils.dp2px(6));
                        radioButton.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
                        radioButton.setHeight(DensityUtils.dp2px(6));
                        radioButton.setBackgroundResource(R.drawable.selector_advertise_rb);
                        radioButton.setLayoutParams(layoutParams);
                        mRgFragmentPunchAdvertising.addView(radioButton);
                    }
                    RadioButton childAt = (RadioButton) mRgFragmentPunchAdvertising.getChildAt(0);
                    childAt.setChecked(true);

                    SignupAdvertiseAdapter signupAdvertiseAdapter = new SignupAdvertiseAdapter(mActivity, data);
                    mVpFragmentPunchAdvertising.setAdapter(signupAdvertiseAdapter);
                    if (data.size()==1) {
                        mRgFragmentPunchAdvertising.setVisibility(View.GONE);
                    }else{
                        mHandler.sendEmptyMessageDelayed(10010, 3000);
                    }
                }
            }
        });
        mVpFragmentPunchAdvertising.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (mAdvertiseSize==0) {
                    return;
                }
                int currentIndex = i % mAdvertiseSize;
                RadioButton childAt = (RadioButton) mRgFragmentPunchAdvertising.getChildAt(currentIndex);
                childAt.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /**
     * 获取今日打卡情况
     */
    public void getTodayPunchData() {
        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().get(Constants.TODAY_PUNCH_DETAIL, map, new HttpCallback<TodayPunchBean>() {
            @Override
            public void onSuccessExecute(TodayPunchBean todayPunchBean) {
                TodayPunchBean.DataBean data = todayPunchBean.getData();
                if (data == null) {
                    mTvFragmentPunchState.setText("今日未打卡");
                    return;
                }
                if (data.getToday_sign_in() == null) {
                    mTvFragmentPunchState.setText("今日未打卡");
                    return;
                }
                TodayPunchBean.DataBean.TodaySignInBean today_sign_in = data.getToday_sign_in();
                switch (today_sign_in.getStatus()) {
                    case "normal":
                        mTvFragmentPunchState.setText("今日待审核");
                        break;
                    case "accepted":
                        mTvFragmentPunchState.setText("今日打卡成功");
                        break;
                    case "rejected":
                        mTvFragmentPunchState.setText("今日审核不通过");
                        break;
                    default:
                        mTvFragmentPunchState.setText("今日未打卡");
                        break;
                }
            }
        });
    }

    /**
     * 获取报名列表
     */
    public void getSignupList() {
        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().get(Constants.SIGNUP_INFO, map, new HttpCallback<SignupInfoBean>() {
            @Override
            public void onSuccessExecute(SignupInfoBean signupInfoBean) {
                List<SignupInfoBean.DataBean> data = signupInfoBean.getData();
                if (data == null || data.size() == 0) {
                    mCardFragmentPunchJoined.setVisibility(View.GONE);
                    mCardFragmentPunchNojoined.setVisibility(View.VISIBLE);
                } else {

                    for (int i = 0; i < data.size(); i++) {
                        SignupInfoBean.DataBean dataBean = data.get(i);
                        String start_date = dataBean.getStart_date();
                        String finish_date = dataBean.getFinish_date();
                        if (DateFormatUtils.afterCurrentTime(finish_date) && DateFormatUtils.beforeCurrentTime(start_date)) {
                            mCardFragmentPunchJoined.setVisibility(View.VISIBLE);
                            mCardFragmentPunchNojoined.setVisibility(View.GONE);
                        }else{
                            mCardFragmentPunchJoined.setVisibility(View.GONE);
                            mCardFragmentPunchNojoined.setVisibility(View.VISIBLE);
                        }
                        String actionId = SaveUtil.getString(SaveConstants.ACTION_ID, "");
                        if (dataBean.getId().equalsIgnoreCase(actionId)) {
                            mTvFragmentPunchDays.setText("已累计跑步" + dataBean.getTotal_days() + "天");
                            mTvFragmentPunchInteval.setText(dataBean.getStart_date() + "～" + dataBean.getFinish_date());
                            mTvFragmentPunchGroup.setText(dataBean.getLevel() + "组别");
                        }
                    }
                }
            }
        });
    }

    /**
     * 是否需要引导用户取打开gps
     * @param context
     */
    public void isNeedGps(final Activity context){
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启，如果没有则开启
        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("打开GPS")
                    .setMessage("请在手机设置中打开GPS定位服务")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setPositiveButton("去打开", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // 转到手机设置界面，用户设置GPS
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            context.startActivity(intent);
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else {
            startActivity(new Intent(mActivity, RunningActivity.class));
        }
    }

    @Override
    public boolean isNeedTitle() {
        return true;
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_punch;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_fragment_punch_screenshot:
                startActivity(new Intent(mActivity, ScreenPunchActivity.class));
                break;
            case R.id.tv_fragment_punch_run:
                isNeedGps(mActivity);
                break;
            case R.id.rl_fragment_punch_last:
                initMonth(-1);
                break;
            case R.id.rl_fragment_punch_next:
                initMonth(1);
                break;
        }
    }

}
