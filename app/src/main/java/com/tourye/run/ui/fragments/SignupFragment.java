package com.tourye.run.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.base.BaseFragment;
import com.tourye.run.bean.ActionInfoBean;
import com.tourye.run.bean.ActivityCommentBean;
import com.tourye.run.bean.AdvertiseBean;
import com.tourye.run.bean.BattleGroupBean;
import com.tourye.run.bean.BattleInfoBean;
import com.tourye.run.bean.BattleReportBean;
import com.tourye.run.bean.BattleTeamListBean;
import com.tourye.run.bean.LeaderApplyStatusBean;
import com.tourye.run.bean.ParseLocationBean;
import com.tourye.run.bean.TeamBasicInfoBean;
import com.tourye.run.bean.UserBasicInfoBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.common.CommonWebActivity;
import com.tourye.run.ui.activities.home.BattleRankingActivity;
import com.tourye.run.ui.activities.home.BattleReportActivity;
import com.tourye.run.ui.activities.home.CreateBattleActivity;
import com.tourye.run.ui.activities.home.KitActivity;
import com.tourye.run.ui.activities.home.LeaderApplyActivity;
import com.tourye.run.ui.activities.home.SearchBattleActivity;
import com.tourye.run.ui.activities.home.TeamDetailActivity;
import com.tourye.run.ui.adapter.ActivityCommentAdapter;
import com.tourye.run.ui.adapter.SignupBattleReportAdapter;
import com.tourye.run.ui.adapter.BattleTeamListAdapter;
import com.tourye.run.ui.adapter.SignupAdvertiseAdapter;
import com.tourye.run.utils.DateCalculateUtils;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.LocationUtils;
import com.tourye.run.utils.SaveUtil;
import com.tourye.run.views.AdvertiseView;
import com.tourye.run.views.MyScrollview;
import com.tourye.run.views.ScaleTransitionPagerTitleView;
import com.tourye.run.views.SmoothScrollLayoutManager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.reactivex.functions.Consumer;

/**
 * Created by longlongren on 2019/3/11.
 * <p>
 * introduce:报名页面
 */
public class SignupFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout mLlFragmentSignupSearch;//搜索
    private ImageView mImgFragmentSignupGraduation;//毕业跑
    private ImageView mImgFragmentSignupRanking;//排行榜
    private ImageView mImgFragmentSignupRule;//规则
    private RecyclerView mRecyclerFragmentSignupBattleReport;//战报轮播榜
    private ImageView mImgFragmentSignupBattleReport;
    private RecyclerView mRecyclerFragmentSignupRunningFriend;//跑友说
    private MagicIndicator mIndicatorFragmentSignup;//导航栏
    private ImageView mImgFragmentSignupBanner;
    private RecyclerView mRecyclerFragmentSignupBattleList;
    private ViewPager mVpFragmentSignupAdvertising;//广告
    private AdvertiseView mLlSignupBattleReport;//战报
    private MyScrollview mScrollFragmentSignup;
    private MagicIndicator mIndicatorFragmentSignupSuspension;
    private TextView mTvFragmentSignupCount;//报名人数

    //倒计时
    private LinearLayout mLlFragmentSignupCountdown;
    private TextView mTvFragmentSignupCountdownDays;
    private TextView mTvFragmentSignupCountdownHours;
    private TextView mTvFragmentSignupCountdownMinutes;
    private TextView mTvFragmentSignupCountdownSeconds;

    private List<String> mBattleGroup = new ArrayList<>();//战队组别列表
    private Map<Integer, Integer> mBattleGroupMap = new HashMap<>();//战队组别映射
    private int mDefaultGroup = 2;//默认组别
    private int mBattleLevel = 0;//默认战队列表level
    private int mBattleListOffset = 0;//战队列表索引
    private int mBattleListCount = 10;//每次请求战队列表数量
    private BattleTeamListAdapter mBattleTeamListAdapter;//战队列表适配器
    private List<TeamBasicInfoBean> mBattleList = new ArrayList<>();//战队列表数据
    private TextView mTvSignupLoadMore;
    private ImageView mImgFragmentSignupInvite;//邀请的百元礼包

    private LinearLayout mLlFragmentSignupGraduation;//毕业跑
    private LinearLayout mLlFragmentSignupRanking;//排行榜
    private LinearLayout mLlFragmentSignupKit;//锦囊
    private LinearLayout mLlFragmentSignupRule;//规则
    private LinearLayout mLlFragmentSignupLeader;//队长

    //自己所在战队
    private TextView mTvSignupOwnBattlePunchTime;//打卡开始时间
    private ImageView mImgFragmentSignupOwnBattle;//战队logo
    private TextView mTvFragmentSignupOwnBattleName;//战队名
    private LinearLayout mLlFragmentSignupOwnBattle;
    private TextView mTvFragmentSignupOwnBattlePosition;//战队所在城市
    private TextView mTvFragmentSignupOwnBattleDistance;//战队所属组别
    private TextView mTvFragmentSignupOwnBattleCount;//战队人数
    private TextView mTvFragmentSignupOwnBattleIntro;//战队介绍
    private TextView mTvFragmentSignupOwnBattleSignup;//战队详情
    private LinearLayout mLlSignupOwnBattle;
    private RadioGroup mRgFragmentSignupAdvertising;

    private String mCityId="";//当前定位城市

    private boolean mIsLoaded=false;//是否已经加载过页面
    private int mAdvertiseSize;//广告数量
    private int battleReportIndex = 0;//战报索引
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10086:
                    if (mReportData==null || mReportData.size()==0) {
                        return;
                    }
                    mRecyclerFragmentSignupBattleReport.smoothScrollToPosition(++battleReportIndex);
                    mHandler.sendEmptyMessageDelayed(10086, 2000);
                    break;
                case 10010:
                    if (mAdvertiseData==null || mAdvertiseData.size()==0) {
                        return;
                    }
                    int currentItem = mVpFragmentSignupAdvertising.getCurrentItem();
                    ++currentItem;
                    RadioButton childAt = (RadioButton) mRgFragmentSignupAdvertising.getChildAt(currentItem % mAdvertiseSize);
                    childAt.setChecked(true);
                    mVpFragmentSignupAdvertising.setCurrentItem(currentItem);
                    mHandler.sendEmptyMessageDelayed(10010, 3000);
                    break;
                case 998:
                    initCountdown();
                    break;
            }
        }
    };
    private List<BattleReportBean.DataBean> mReportData;//战报数据
    private List<AdvertiseBean.DataBean> mAdvertiseData;//广告数据

    @Override
    public void initView(View view) {
        mLlFragmentSignupSearch = (LinearLayout) view.findViewById(R.id.ll_fragment_signup_search);
        mImgFragmentSignupGraduation = (ImageView) view.findViewById(R.id.img_fragment_signup_graduation);
        mImgFragmentSignupRanking = (ImageView) view.findViewById(R.id.img_fragment_signup_ranking);
        mImgFragmentSignupRule = (ImageView) view.findViewById(R.id.img_fragment_signup_rule);
        mRecyclerFragmentSignupBattleReport = (RecyclerView) view.findViewById(R.id.recycler_fragment_signup_battleReport);
        mImgFragmentSignupBattleReport = (ImageView) view.findViewById(R.id.img_fragment_signup_battleReport);
        mRecyclerFragmentSignupRunningFriend = (RecyclerView) view.findViewById(R.id.recycler_fragment_signup_runningFriend);
        mIndicatorFragmentSignup = (MagicIndicator) view.findViewById(R.id.indicator_fragment_signup);
        mImgFragmentSignupBanner = (ImageView) view.findViewById(R.id.img_fragment_signup_banner);
        mRecyclerFragmentSignupBattleList = (RecyclerView) view.findViewById(R.id.recycler_fragment_signup_battleList);
        mVpFragmentSignupAdvertising = (ViewPager) view.findViewById(R.id.vp_fragment_signup_advertising);
        mTvSignupLoadMore = (TextView) view.findViewById(R.id.tv_signup_load_more);
        mLlSignupBattleReport = (AdvertiseView) view.findViewById(R.id.ll_signup_battle_report);
        mLlFragmentSignupGraduation = (LinearLayout) view.findViewById(R.id.ll_fragment_signup_graduation);
        mLlFragmentSignupRanking = (LinearLayout) view.findViewById(R.id.ll_fragment_signup_ranking);
        mLlFragmentSignupKit = (LinearLayout) view.findViewById(R.id.ll_fragment_signup_kit);
        mLlFragmentSignupRule = (LinearLayout) view.findViewById(R.id.ll_fragment_signup_rule);
        mTvSignupOwnBattlePunchTime = (TextView) view.findViewById(R.id.tv_signup_own_battle_punchTime);
        mImgFragmentSignupOwnBattle = (ImageView) view.findViewById(R.id.img_fragment_signup_own_battle);
        mTvFragmentSignupOwnBattleName = (TextView) view.findViewById(R.id.tv_fragment_signup_own_battle_name);
        mLlFragmentSignupOwnBattle = (LinearLayout) view.findViewById(R.id.ll_fragment_signup_own_battle);
        mTvFragmentSignupOwnBattlePosition = (TextView) view.findViewById(R.id.tv_fragment_signup_own_battle_position);
        mTvFragmentSignupOwnBattleDistance = (TextView) view.findViewById(R.id.tv_fragment_signup_own_battle_distance);
        mTvFragmentSignupOwnBattleCount = (TextView) view.findViewById(R.id.tv_fragment_signup_own_battle_count);
        mTvFragmentSignupOwnBattleIntro = (TextView) view.findViewById(R.id.tv_fragment_signup_own_battle_intro);
        mTvFragmentSignupOwnBattleSignup = (TextView) view.findViewById(R.id.tv_fragment_signup_own_battle_signup);
        mLlSignupOwnBattle = (LinearLayout) view.findViewById(R.id.ll_signup_own_battle);
        mScrollFragmentSignup = (MyScrollview) view.findViewById(R.id.scroll_fragment_signup);
        mIndicatorFragmentSignupSuspension = (MagicIndicator) view.findViewById(R.id.indicator_fragment_signup_suspension);
        mLlFragmentSignupLeader = (LinearLayout) view.findViewById(R.id.ll_fragment_signup_leader);
        mRgFragmentSignupAdvertising = (RadioGroup) view.findViewById(R.id.rg_fragment_signup_advertising);
        mImgFragmentSignupInvite = (ImageView) view.findViewById(R.id.img_fragment_signup_invite);
        mTvFragmentSignupCountdownDays = (TextView) view.findViewById(R.id.tv_fragment_signup_countdown_days);
        mTvFragmentSignupCountdownHours = (TextView) view.findViewById(R.id.tv_fragment_signup_countdown_hours);
        mTvFragmentSignupCountdownMinutes = (TextView) view.findViewById(R.id.tv_fragment_signup_countdown_minutes);
        mTvFragmentSignupCountdownSeconds = (TextView) view.findViewById(R.id.tv_fragment_signup_countdown_seconds);
        mLlFragmentSignupCountdown = (LinearLayout) view.findViewById(R.id.ll_fragment_signup_countdown);
        mTvFragmentSignupCount = (TextView) view.findViewById(R.id.tv_fragment_signup_count);

        mTvTitle.setText("百日跑");

        mLlFragmentSignupGraduation.setOnClickListener(this);
        mLlFragmentSignupRanking.setOnClickListener(this);
        mLlFragmentSignupKit.setOnClickListener(this);
        mLlFragmentSignupRule.setOnClickListener(this);
        mLlFragmentSignupSearch.setOnClickListener(this);
        mLlFragmentSignupLeader.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        getActionBasicInfo();
        //todo---是否需要每次刷新列表
        if (mIsLoaded) {
            mBattleListOffset = 0;
            getBattleList(false);
        }
        mIsLoaded=true;
    }

    @Override
    public void initData() {

        getUserBasicInfo();

        initBattleReport();

        getActivityComment();

        initAdvertise();

        getLocationData();

        //悬浮导航栏效果
        mScrollFragmentSignup.setOnScrollListener(new MyScrollview.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                int top = mIndicatorFragmentSignup.getTop();
                if (scrollY >= top) {
                    mIndicatorFragmentSignupSuspension.setVisibility(View.VISIBLE);
                } else {
                    mIndicatorFragmentSignupSuspension.setVisibility(View.GONE);
                }
                int mImgFragmentSignupInviteTop = mImgFragmentSignupInvite.getTop();
                if (mImgFragmentSignupInviteTop > scrollY) {
                    int startX = getResources().getDisplayMetrics().widthPixels - DensityUtils.dp2px(84);
                    mImgFragmentSignupInvite.setX(startX + (scrollY * 1.0f / mImgFragmentSignupInviteTop) * mImgFragmentSignupInvite.getWidth()/2);
                }
            }
        });

    }

    /**
     * 初始化倒计时
     */
    private void initCountdown() {
        boolean is_joined = SaveUtil.getBoolean(SaveConstants.IS_JOINED, false);
        if (is_joined) {
            return;
        }

        String sign_up_finish_date = SaveUtil.getString(SaveConstants.SIGN_UP_FINISH_DATE, "");
        if (!TextUtils.isEmpty(sign_up_finish_date)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parse = simpleDateFormat.parse(sign_up_finish_date);
                Calendar instance = Calendar.getInstance();
                instance.setTime(parse);
                instance.add(Calendar.DAY_OF_MONTH,1);
                Date final_date = instance.getTime();
                long finalDateTime = final_date.getTime();
                int[] ints = DateCalculateUtils.calculateCutdown(System.currentTimeMillis(), finalDateTime);
                if (ints==null) {
                    mLlFragmentSignupCountdown.setVisibility(View.GONE);
                }else{
                    mLlFragmentSignupCountdown.setVisibility(View.VISIBLE);
                    mTvFragmentSignupCountdownDays.setText(ints[0]+"");
                    mTvFragmentSignupCountdownHours.setText(ints[1]+"");
                    mTvFragmentSignupCountdownMinutes.setText(ints[2]+"");
                    mTvFragmentSignupCountdownSeconds.setText(ints[3]+"");
                    mHandler.sendEmptyMessageDelayed(998,1000);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 获取用户基本信息
     */
    private void getUserBasicInfo() {
        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().get(Constants.USER_BASIC_INFO, map, new HttpCallback<UserBasicInfoBean>() {
            @Override
            public void onSuccessExecute(UserBasicInfoBean userBasicInfoBean) {
                UserBasicInfoBean.DataBean data = userBasicInfoBean.getData();
                if (data == null) {
                    return;
                }
                SaveUtil.putInt(SaveConstants.USER_ID, data.getId());
                SaveUtil.putString(SaveConstants.USER_NAME, data.getNickname());
                SaveUtil.putString(SaveConstants.USER_HEAD, data.getAvatar());
                SaveUtil.putBoolean(SaveConstants.IS_VERIFIED, data.isReal_name_authenticated());
            }
        });
    }

    /**
     * 获取活动基本信息
     */
    private void getActionBasicInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        HttpUtils.getInstance().get(Constants.ACTION_INFO, map, new HttpCallback<ActionInfoBean>() {
            @Override
            public void onSuccessExecute(ActionInfoBean actionInfoBean) {
                ActionInfoBean.DataBean data = actionInfoBean.getData();
                if (data != null) {
                    mImgFragmentSignupInvite.setOnClickListener(SignupFragment.this);
                    SaveUtil.putString(SaveConstants.SIGN_IN_START_DATE, data.getSign_in_start_date());
                    SaveUtil.putString(SaveConstants.SIGN_IN_FINISH_DATE, data.getSign_in_finish_date());
                    SaveUtil.putString(SaveConstants.SIGN_UP_PROTOCOL, data.getAgreement_url());
                    SaveUtil.putString(SaveConstants.SIGN_UP_START_DATE, data.getSign_up_start_date());
                    SaveUtil.putString(SaveConstants.SIGN_UP_FINISH_DATE, data.getSign_up_finish_date());
                    SaveUtil.putString(SaveConstants.TIPS_COVER, data.getTips_cover());
                    SaveUtil.putString(SaveConstants.RULE_URL, data.getRule_url());
                    SaveUtil.putString(SaveConstants.MONITOR_APPLY_START_DATE,data.getMonitor_apply_start_date());
                    SaveUtil.putString(SaveConstants.MONITOR_APPLY_FINISH_DATE,data.getMonitor_apply_finish_date());
                    SaveUtil.putString(SaveConstants.SHARE_FIRST_TITLE,data.getShare_team_first_title());
                    SaveUtil.putString(SaveConstants.SHARE_SECOND_TITLE,data.getShare_team_second_title());
                    mTvFragmentSignupCount.setText(data.getTotal_join_count()+"");
                    GlideUtils.getInstance().loadRoundImage(data.getCover_image(),mImgFragmentSignupBanner,8);
                    if (data.isJoined()) {
                        mLlFragmentSignupKit.setVisibility(View.VISIBLE);
                        SaveUtil.putBoolean(SaveConstants.IS_JOINED, true);
                        SaveUtil.putString(SaveConstants.TEAM_ID, data.getTeam_id());
                        SaveUtil.putString(SaveConstants.LEVEL_ID, data.getLevel_id() + "");
                        SaveUtil.putString(SaveConstants.PACKAGE_ID, data.getPackage_id() + "");
                        SaveUtil.putBoolean(SaveConstants.IS_CAN_UPGRADE_PACKAGE, data.isCan_change_package());
                        getBattleInfo(data.getTeam_id());
                        mLlSignupOwnBattle.setVisibility(View.VISIBLE);
                    } else {
                        SaveUtil.putBoolean(SaveConstants.IS_JOINED, false);
                        SaveUtil.putString(SaveConstants.TEAM_ID, "");
                        SaveUtil.putString(SaveConstants.LEVEL_ID,  "");
                        SaveUtil.putString(SaveConstants.PACKAGE_ID, "");
                        SaveUtil.putBoolean(SaveConstants.IS_CAN_UPGRADE_PACKAGE, false);
                        mLlSignupOwnBattle.setVisibility(View.GONE);
                        mLlFragmentSignupKit.setVisibility(View.GONE);
                    }

                    initCountdown();
                }

            }
        });
    }

    /**
     * 获取所在战队信息
     *
     * @param team_id 战队id
     */
    public void getBattleInfo(final String team_id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", team_id);
        HttpUtils.getInstance().get(Constants.TEAM_INFO, map, new HttpCallback<BattleInfoBean>() {
            @Override
            public void onSuccessExecute(BattleInfoBean battleInfoBean) {
                final BattleInfoBean.DataBean data = battleInfoBean.getData();
                if (data != null) {
                    Glide.with(BaseApplication.mApplicationContext).load(data.getLogo()).into(mImgFragmentSignupOwnBattle);
                    mTvFragmentSignupOwnBattleName.setText(data.getName());
                    mTvFragmentSignupOwnBattleCount.setText(data.getMember_count() + "人");
                    mTvFragmentSignupOwnBattleDistance.setText(data.getDistance() + "km");
                    SaveUtil.putString(SaveConstants.LEVEL_GROUP, data.getDistance() + "");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String sign_in_start_date = SaveUtil.getString(SaveConstants.SIGN_IN_START_DATE, "");
                    if (!TextUtils.isEmpty(sign_in_start_date)) {
                        try {
                            Date parse = simpleDateFormat.parse(sign_in_start_date);
                            Calendar instance = Calendar.getInstance();
                            instance.setTime(parse);
                            mTvSignupOwnBattlePunchTime.setText((instance.get(Calendar.MONTH) + 1) + "月" + instance.get(Calendar.DAY_OF_MONTH) + "日开始打卡");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    String city = data.getCity();
                    if (TextUtils.isEmpty(city)) {
                        city="全国";
                    }
                    mTvFragmentSignupOwnBattlePosition.setText(city);
                    mTvFragmentSignupOwnBattleIntro.setText("队长:" + data.getMonitor_name());
                    mLlSignupOwnBattle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mActivity, TeamDetailActivity.class);
                            intent.putExtra("team_id", team_id);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }

    /**
     * 初始化战报列表
     */
    private void initBattleReport() {
        Map<String, String> map = new HashMap<>();
        map.put("id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        map.put("count", "1000");
        map.put("lastid", "");
        HttpUtils.getInstance().get(Constants.BATTLE_REPORT_LIST, map, new HttpCallback<BattleReportBean>() {
            @Override
            public void onSuccessExecute(BattleReportBean battleReportBean) {
                mReportData = battleReportBean.getData();

                if (mReportData != null && mReportData.size() > 0) {
                    battleReportIndex = 0;
                    SignupBattleReportAdapter signupBattleReportAdapter = new SignupBattleReportAdapter(mActivity, mReportData);
                    mRecyclerFragmentSignupBattleReport.setLayoutManager(new SmoothScrollLayoutManager(mActivity));
                    mRecyclerFragmentSignupBattleReport.setAdapter(signupBattleReportAdapter);
                    mHandler.sendEmptyMessageDelayed(10086, 2000);
                    mRecyclerFragmentSignupBattleReport.setHasFixedSize(true);
                    //禁用滑动事件
                    mRecyclerFragmentSignupBattleReport.setNestedScrollingEnabled(false);
                    mLlSignupBattleReport.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(mActivity, BattleReportActivity.class));
                        }
                    });
                    mLlSignupBattleReport.setOnAdvertiseClick(new AdvertiseView.OnAdvertiseClick() {
                        @Override
                        public void onClick() {
                            startActivity(new Intent(mActivity, BattleReportActivity.class));
                        }
                    });
                }
            }
        });
    }

    /**
     * 获取活动评价
     */
    private void getActivityComment() {
        Map<String, String> map = new HashMap<>();
        map.put("id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        HttpUtils.getInstance().get(Constants.ACTIVITY_COMMENT, map, new HttpCallback<ActivityCommentBean>() {
            @Override
            public void onSuccessExecute(ActivityCommentBean activityCommentBean) {
                List<ActivityCommentBean.DataBean> data = activityCommentBean.getData();

                if (data != null && data.size() > 0) {
                    ActivityCommentAdapter activityCommentAdapter = new ActivityCommentAdapter(mActivity, data);
                    mRecyclerFragmentSignupRunningFriend.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
                    mRecyclerFragmentSignupRunningFriend.setAdapter(activityCommentAdapter);
                }
            }
        });
    }

    /**
     * 初始化战队组别列表
     */
    public void initBattleGroup() {
        Map<String, String> map = new HashMap<>();
        map.put("id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        if (!TextUtils.isEmpty(mCityId)) {
            map.put("city_id", mCityId);
        }
        HttpUtils.getInstance().get(Constants.BATTLE_GROUP_LIST, map, new HttpCallback<BattleGroupBean>() {
            @Override
            public void onSuccessExecute(BattleGroupBean battleGroupBean) {
                List<BattleGroupBean.DataBean> data = battleGroupBean.getData();
                if (data != null && data.size() > 0) {
                    for (int i = 0; i < data.size(); i++) {
                        BattleGroupBean.DataBean dataBean = data.get(i);
                        mBattleGroup.add(dataBean.getName());
                        mBattleGroupMap.put(i, dataBean.getId());
                        if (i == 0) {
                            mDefaultGroup = i;
                            mBattleLevel = dataBean.getId();
                        }
                        if (dataBean.isRecommend()) {
                            mDefaultGroup = i;
                            mBattleLevel = dataBean.getId();
                        }
                    }
                    initIndicator(mIndicatorFragmentSignup);
                    initIndicator(mIndicatorFragmentSignupSuspension);
                    getBattleList(false);
                }
            }
        });
    }

    /**
     * 获取定位数据
     */
    public void getLocationData() {
        RxPermissions rxPermissions = new RxPermissions(mActivity);
        rxPermissions
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        if (aBoolean) {
                            LocationUtils.getInstance().startLocation(new AMapLocationListener() {
                                @Override
                                public void onLocationChanged(AMapLocation amapLocation) {
                                    if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                                        //定位成功回调信息，设置相关消息
                                        double latitude = amapLocation.getLatitude();//获取纬度
                                        double longitude = amapLocation.getLongitude();//获取经度
                                        parseLocation(longitude+"",latitude+"");
                                    }else{
                                        //定位失败---各种原因
                                        initBattleGroup();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(mActivity, "请前往设置开启定位权限", Toast.LENGTH_SHORT).show();
                            //定位失败---各种原因
                            initBattleGroup();
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 根据经纬度解析地理位置
     * @param lot
     * @param lat
     */
    public void parseLocation(String lot,String lat){
        Map<String,String> map=new HashMap<>();
        map.put("longitude",lot);
        map.put("latitude",lat);
        HttpUtils.getInstance().get(Constants.PARSE_LOCATION, map, new HttpCallback<ParseLocationBean>() {
            @Override
            public void onSuccessExecute(ParseLocationBean parseLocationBean) {
                ParseLocationBean.DataBean data = parseLocationBean.getData();
                if (data==null) {
                    return;
                }
                mCityId=data.getId()+"";
                initBattleGroup();
            }
        });
    }

    /**
     * 获取战队列表数据
     *
     * @param isLoadMore 是否是加载更多
     */
    private void getBattleList(final boolean isLoadMore) {
        Map<String, String> map = new HashMap<>();
        map.put("id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        map.put("level_id", mBattleLevel + "");
        if (!TextUtils.isEmpty(mCityId)) {
            map.put("city_id", mCityId);
        }
        map.put("count", mBattleListCount + "");
        map.put("offset", mBattleListOffset + "");
        HttpUtils.getInstance().get(Constants.BATTLE_LIST, map, new HttpCallback<BattleTeamListBean>() {
            @Override
            public void onSuccessExecute(BattleTeamListBean battleTeamListBean) {
                List<TeamBasicInfoBean> data = battleTeamListBean.getData();
                if (data != null && data.size() > 0) {
                    mRecyclerFragmentSignupBattleList.setVisibility(View.VISIBLE);
                    //当数据数量小于请求条数时隐藏加载更多
                    if (!(data.size() < mBattleListCount)) {
                        mTvSignupLoadMore.setSelected(false);
                        mTvSignupLoadMore.setText("查看更多");
                        mTvSignupLoadMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getBattleList(true);
                            }
                        });
                    } else {
                        mTvSignupLoadMore.setSelected(true);
                        mTvSignupLoadMore.setText("没有更多了");
                        mTvSignupLoadMore.setOnClickListener(null);
                    }
                    if (isLoadMore) {
                        mBattleList.addAll(data);
                        mBattleTeamListAdapter.setBattleTeamListBeans(mBattleList);
                    } else {
                        mBattleList.clear();
                        mBattleList.addAll(data);
                        mBattleTeamListAdapter = new BattleTeamListAdapter(mActivity, data);
                        //解决数据加载不完的问题
                        mRecyclerFragmentSignupBattleList.setNestedScrollingEnabled(false);
                        mRecyclerFragmentSignupBattleList.setHasFixedSize(true);
                        mRecyclerFragmentSignupBattleList.setLayoutManager(new LinearLayoutManager(mActivity));
                        mRecyclerFragmentSignupBattleList.setAdapter(mBattleTeamListAdapter);
                    }
                    mBattleListOffset += data.size();

                } else {
                    mBattleList.clear();
                    if (mBattleTeamListAdapter!=null) {
                        mBattleTeamListAdapter.setBattleTeamListBeans(mBattleList);
                        mRecyclerFragmentSignupBattleList.setAdapter(mBattleTeamListAdapter);
                    }
                    mTvSignupLoadMore.setSelected(true);
                    mTvSignupLoadMore.setText("没有更多了");
                    mTvSignupLoadMore.setOnClickListener(null);
                }
            }
        });
    }

    /**
     * 初始化导航栏
     */
    private void initIndicator(final MagicIndicator magicIndicator) {
        final CommonNavigator commonNavigator = new CommonNavigator(mActivity);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mBattleGroup == null ? 0 : mBattleGroup.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
//                final ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                final SimplePagerTitleView colorTransitionPagerTitleView = new ScaleTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#FF999999"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#FFFF1D1D"));
                colorTransitionPagerTitleView.setText(mBattleGroup.get(index));
                colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        CommonNavigator navigator = (CommonNavigator) mIndicatorFragmentSignup.getNavigator();
                        CommonNavigator navigatorSuspension = (CommonNavigator) mIndicatorFragmentSignupSuspension.getNavigator();
                        navigator.onPageSelected(index);
                        navigator.notifyDataSetChanged();
                        navigatorSuspension.onPageSelected(index);
                        navigatorSuspension.notifyDataSetChanged();
                        mBattleListOffset = 0;
                        mBattleLevel = mBattleGroupMap.get(index);
                        getBattleList(false);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        //正常导航栏
        magicIndicator.setNavigator(commonNavigator);
        commonNavigator.onPageSelected(mDefaultGroup);
        commonNavigator.notifyDataSetChanged();
    }

    /**
     * 初始化广告
     */
    private void initAdvertise() {
        Map<String, String> map = new HashMap<>();
        map.put("id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        map.put("type", "index");
        HttpUtils.getInstance().get(Constants.ADVERTISE, map, new HttpCallback<AdvertiseBean>() {
            @Override
            public void onSuccessExecute(AdvertiseBean advertiseBean) {
                mAdvertiseData = advertiseBean.getData();
                if (mAdvertiseData != null && mAdvertiseData.size() > 0) {
                    mAdvertiseSize = mAdvertiseData.size();
                    for (int i = 0; i < mAdvertiseData.size(); i++) {
                        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.rightMargin = DensityUtils.dp2px(5);
                        RadioButton radioButton = new RadioButton(mActivity);
                        radioButton.setWidth(DensityUtils.dp2px(6));
                        radioButton.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
                        radioButton.setHeight(DensityUtils.dp2px(6));
                        radioButton.setBackgroundResource(R.drawable.selector_advertise_rb);
                        radioButton.setLayoutParams(layoutParams);
                        mRgFragmentSignupAdvertising.addView(radioButton);
                    }
                    RadioButton childAt = (RadioButton) mRgFragmentSignupAdvertising.getChildAt(0);
                    childAt.setChecked(true);

                    SignupAdvertiseAdapter signupAdvertiseAdapter = new SignupAdvertiseAdapter(mActivity, mAdvertiseData);
                    mVpFragmentSignupAdvertising.setAdapter(signupAdvertiseAdapter);
                    if (mAdvertiseData.size()==1) {
                        mRgFragmentSignupAdvertising.setVisibility(View.GONE);
                    }else{
                        mHandler.sendEmptyMessageDelayed(10010, 3000);
                    }
                }
            }
        });
        mVpFragmentSignupAdvertising.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (mAdvertiseData==null || mAdvertiseData.size()==0) {
                    return;
                }
                int currentIndex = i % mAdvertiseData.size();
                RadioButton childAt = (RadioButton) mRgFragmentSignupAdvertising.getChildAt(currentIndex);
                childAt.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /**
     * 检查队长申请状态
     */
    private void checkLeaderApplyStatus() {
        Map<String, String> map = new HashMap<>();
        map.put("activity_id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        HttpUtils.getInstance().get(Constants.LEADER_APPLY_STATUS, map, new HttpCallback<LeaderApplyStatusBean>() {
            @Override
            public void onSuccessExecute(LeaderApplyStatusBean leaderApplyStatusBean) {
                LeaderApplyStatusBean.DataBean data = leaderApplyStatusBean.getData();
                if (data != null) {
                    //队长申请成功
                    if (data.isApplied()) {
                        if ("accepted".equalsIgnoreCase(data.getStatus())) {
                            String sign_up_start_date = SaveUtil.getString(SaveConstants.SIGN_UP_START_DATE, "");
                            String sign_up_finish_date = SaveUtil.getString(SaveConstants.SIGN_UP_FINISH_DATE, "");
                            if (!TextUtils.isEmpty(sign_up_start_date) && !TextUtils.isEmpty(sign_up_finish_date)) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date startDate = simpleDateFormat.parse(sign_up_start_date);
                                    Date finishDate = simpleDateFormat.parse(sign_up_finish_date);
                                    Date currentDate = Calendar.getInstance().getTime();
                                    if (currentDate.compareTo(startDate)<0 || currentDate.compareTo(finishDate)>0) {
                                        Toast.makeText(mActivity, "当前不在战队创建时间内", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            startActivity(new Intent(mActivity, CreateBattleActivity.class));
                        }else {
                            String monitor_apply_start_date = SaveUtil.getString(SaveConstants.MONITOR_APPLY_START_DATE, "");
                            String monitor_apply_finish_date = SaveUtil.getString(SaveConstants.MONITOR_APPLY_FINISH_DATE, "");
                            if (!TextUtils.isEmpty(monitor_apply_start_date) && !TextUtils.isEmpty(monitor_apply_finish_date)) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date startDate = simpleDateFormat.parse(monitor_apply_start_date);
                                    Date finishDate = simpleDateFormat.parse(monitor_apply_finish_date);
                                    Date currentDate = Calendar.getInstance().getTime();
                                    if (currentDate.compareTo(startDate)<0 || currentDate.compareTo(finishDate)>0) {
                                        Toast.makeText(mActivity, "当前不在队长申请时间内", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            startActivity(new Intent(mActivity, LeaderApplyActivity.class));
                        }
                    } else {
                        String monitor_apply_start_date = SaveUtil.getString(SaveConstants.MONITOR_APPLY_START_DATE, "");
                        String monitor_apply_finish_date = SaveUtil.getString(SaveConstants.MONITOR_APPLY_FINISH_DATE, "");
                        if (!TextUtils.isEmpty(monitor_apply_start_date) && !TextUtils.isEmpty(monitor_apply_finish_date)) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                Date startDate = simpleDateFormat.parse(monitor_apply_start_date);
                                Date finishDate = simpleDateFormat.parse(monitor_apply_finish_date);
                                Date currentDate = Calendar.getInstance().getTime();
                                if (currentDate.compareTo(startDate)<0 || currentDate.compareTo(finishDate)>0) {
                                    Toast.makeText(mActivity, "当前不在队长申请时间内", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        startActivity(new Intent(mActivity, LeaderApplyActivity.class));
                    }
                }
            }
        });
    }

    @Override
    public boolean isNeedTitle() {
        return true;
    }

    @Override
    public void refreshData() {
        super.refreshData();
        if (mAdvertiseData!=null && mAdvertiseData.size()>1) {
            mHandler.sendEmptyMessageDelayed(10010,3000);
        }
        mHandler.sendEmptyMessageDelayed(10086,2000);
    }

    @Override
    public void onInvisible() {
        super.onInvisible();
        mHandler.removeMessages(10086);
        if (mAdvertiseData!=null && mAdvertiseData.size()>1) {
            mHandler.removeMessages(10010);
        }
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_signup;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ll_fragment_signup_graduation:

                break;
            case R.id.ll_fragment_signup_ranking:
                intent.setClass(mActivity, BattleRankingActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_fragment_signup_kit:
                intent.setClass(mActivity, KitActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_fragment_signup_rule:
                Intent rule = new Intent(mActivity, CommonWebActivity.class);
                rule.putExtra("url", SaveUtil.getString(SaveConstants.RULE_URL, ""));
                rule.putExtra("title", "活动规则");
                startActivity(rule);
                break;
            case R.id.ll_fragment_signup_search:
                startActivity(new Intent(mActivity, SearchBattleActivity.class));
                break;
            case R.id.ll_fragment_signup_leader:
                checkLeaderApplyStatus();
                break;
            case R.id.img_fragment_signup_invite:
                Intent inviteIntent = new Intent(mActivity, CommonWebActivity.class);
                String url = Constants.DOMAIN+"?env=gio&jwt="
                        + SaveUtil.getString("Authorization", "")
                        + "#/team/invite_card/" + SaveUtil.getString(SaveConstants.ACTION_ID, "");
                if (!TextUtils.isEmpty(SaveUtil.getString(SaveConstants.TEAM_ID, ""))) {
                    url = url + "/" + SaveUtil.getString(SaveConstants.TEAM_ID, "");
                }
                inviteIntent.putExtra("url", url);
                inviteIntent.putExtra("title", "邀请卡");
                startActivity(inviteIntent);
                break;
        }
    }

}
