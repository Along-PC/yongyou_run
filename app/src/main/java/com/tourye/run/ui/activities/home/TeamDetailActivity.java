package com.tourye.run.ui.activities.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.BattleDetailDynamicBean;
import com.tourye.run.bean.BattleTeamListBean;
import com.tourye.run.bean.CommonBean;
import com.tourye.run.bean.DistanceRankingBean;
import com.tourye.run.bean.GameRankingBean;
import com.tourye.run.bean.BattleInfoBean;
import com.tourye.run.bean.BattleMemberBean;
import com.tourye.run.bean.MonitorTeamListbean;
import com.tourye.run.bean.TeamBasicInfoBean;
import com.tourye.run.bean.TeamInfoBean;
import com.tourye.run.bean.TodayDistanceRankBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.common.CommonWebActivity;
import com.tourye.run.ui.activities.mine.TeamManageActivity;
import com.tourye.run.ui.activities.mine.TeamMemberActivity;
import com.tourye.run.ui.adapter.DistanceRankingAdapter;
import com.tourye.run.ui.adapter.GameRankingAdapter;
import com.tourye.run.ui.adapter.TeamDetailAlbumAdapter;
import com.tourye.run.ui.adapter.TeamDetailDynamicAdapter;
import com.tourye.run.ui.adapter.TodayDistanceRankAdapter;
import com.tourye.run.ui.dialogs.ShareDialog;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;
import com.tourye.run.views.FlowHeadLayout;
import com.tourye.run.views.ScaleTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TeamDetailActivity
 * @Author: along
 * @Description: 战队详情页
 * @CreateDate: 2019/3/18 3:01 PM
 */
public class TeamDetailActivity extends BaseActivity implements View.OnClickListener {
    //战队头像
    private ImageView mImgTeamDetailTeamLogo;
    //战队名称
    private TextView mTvTeamDetailTeamName;
    //获取邀请卡
    private TextView mTvTeamDetailGetCard;
    //队长头像
    private ImageView mImgTeamDetailHead;
    //队长姓名
    private TextView mTvTeamDetailName;
    //主要成就
    private TextView mTvTeamDetailAchievement;
    //战队成员头像
    private FlowHeadLayout mFlowTeamDetailMemeber;
    //战队成员数
    private TextView mTvTeamDetailMemeberCount;
    private LinearLayout mLlTeamDetailMemeber;
    //相册
    private RecyclerView mRecyclerTeamDetailAlbum;
    //战队二维码
    private ImageView mImgTeamDetailQrcodeBattle;
    //队长二维码
    private ImageView mImgTeamDetailQrcodeLeader;
    //扫码加入
    private LinearLayout mLlTeamDetailQrcode;
    private View mViewTeamDetailQrcode;
    //动态
    private RecyclerView mRecyclerTeamDetailDynamic;
    private SmartRefreshLayout mRefreshActivityTeamDetail;
    private MagicIndicator mIndicatorActivityTeamDetail;//排行榜导航栏
    private RecyclerView mRecyclerTeamDetailRateRank;//个人完赛率排行
    private RecyclerView mRecyclerTeamDetailDistanceRank;//总距离排行榜
    private RecyclerView mRecyclerTeamDetailTodayDistanceRank;//今日距离排行榜
    private LinearLayout mLlActivityTeamDetailIndicator;//导航栏模块
    private TextView mTvActivityTeamDetailMore;//查看更多
    private TextView mTvActivityTeamDetailShare;//分享
    private TextView mTvActivityTeamDetailAddress;//收货地址
    private TextView mTvActivityTeamDetailUpPackage;//升级套餐
    private LinearLayout mLlActivityTeamDetailBottom;//底部功能条
    private TextView mTvActivityTeamDetailSignup;//报名
    private LinearLayout mLlActivityTeamDetailCustomerService;//客服
    private ImageView mImgTeamDetailTeamback;
    private LinearLayout mLlActivityTeamDetailShare;//分享
    private LinearLayout mLlTeamDetailAlbum;//相册模块
    private LinearLayout mLlTeamDetailDynamic;//动态模块
    private TextView mTvActivityTeamDetailNodataHint;//暂无数据的提示
    private TextView mTvTeamDetailManage;//队长管理

    private String mDynamic_last_id;//最后一条动态id
    private List<BattleDetailDynamicBean.DataBean> mDynamicBeans = new ArrayList<>();//动态数据
    private boolean isFirstLoad = true;//是否首次加载动态
    private TeamDetailDynamicAdapter mTeamDetailDynamicAdapter;
    private String mTeam_id;//队伍id

    private IntentFilter intentFilter = new IntentFilter(TeamRankingActivity.THUMB_ACTION);
    private ThumbClickReceiver mThumbClickReceiver = new ThumbClickReceiver();

    private TodayDistanceRankAdapter mTodayDistanceRankAdapter;
    private GameRankingAdapter mCompleteRankingAdapter;
    private DistanceRankingAdapter mTotalRankingAdapter;
    private List<GameRankingBean.DataBean.ListBean> mCompleteRankBean;
    private List<TodayDistanceRankBean.DataBean.CurrentBean> mTodayRankBean;
    private List<DistanceRankingBean.DataBean.ListBean> mTotalRankBean;
    private BattleInfoBean.DataBean mMemberData;

    @Override
    public void initView() {
        mImgTeamDetailTeamLogo = (ImageView) findViewById(R.id.img_team_detail_teamlogo);
        mTvTeamDetailTeamName = (TextView) findViewById(R.id.tv_team_detail_teamName);
        mTvTeamDetailGetCard = (TextView) findViewById(R.id.tv_team_detail_getCard);
        mImgTeamDetailHead = (ImageView) findViewById(R.id.img_team_detail_head);
        mTvTeamDetailName = (TextView) findViewById(R.id.tv_team_detail_name);
        mTvTeamDetailAchievement = (TextView) findViewById(R.id.tv_team_detail_achievement);
        mFlowTeamDetailMemeber = (FlowHeadLayout) findViewById(R.id.flow_team_detail_memeber);
        mTvTeamDetailMemeberCount = (TextView) findViewById(R.id.tv_team_detail_memeber_count);
        mLlTeamDetailMemeber = (LinearLayout) findViewById(R.id.ll_team_detail_memeber);
        mRecyclerTeamDetailAlbum = (RecyclerView) findViewById(R.id.recycler_team_detail_album);
        mImgTeamDetailQrcodeBattle = (ImageView) findViewById(R.id.img_team_detail_qrcode_battle);
        mImgTeamDetailQrcodeLeader = (ImageView) findViewById(R.id.img_team_detail_qrcode_leader);
        mLlTeamDetailQrcode = (LinearLayout) findViewById(R.id.ll_team_detail_qrcode);
        mRecyclerTeamDetailDynamic = (RecyclerView) findViewById(R.id.recycler_team_detail_dynamic);
        mRefreshActivityTeamDetail = (SmartRefreshLayout) findViewById(R.id.refresh_activity_team_detail);
        mIndicatorActivityTeamDetail = (MagicIndicator) findViewById(R.id.indicator_activity_team_detail);
        mRecyclerTeamDetailRateRank = (RecyclerView) findViewById(R.id.recycler_team_detail_rate_rank);
        mRecyclerTeamDetailDistanceRank = (RecyclerView) findViewById(R.id.recycler_team_detail_total_distance_rank);
        mLlActivityTeamDetailIndicator = (LinearLayout) findViewById(R.id.ll_activity_team_detail_indicator);
        mTvActivityTeamDetailMore = (TextView) findViewById(R.id.tv_activity_team_detail_more);
        mTvActivityTeamDetailShare = (TextView) findViewById(R.id.tv_activity_team_detail_share);
        mTvActivityTeamDetailAddress = (TextView) findViewById(R.id.tv_activity_team_detail_address);
        mTvActivityTeamDetailUpPackage = (TextView) findViewById(R.id.tv_activity_team_detail_package);
        mLlActivityTeamDetailBottom = (LinearLayout) findViewById(R.id.ll_activity_team_detail_bottom);
        mViewTeamDetailQrcode = (View) findViewById(R.id.view_team_detail_qrcode);
        mRecyclerTeamDetailTodayDistanceRank = (RecyclerView) findViewById(R.id.recycler_team_detail_today_distance_rank);
        mTvActivityTeamDetailSignup = (TextView) findViewById(R.id.tv_activity_team_detail_signup);
        mLlActivityTeamDetailCustomerService = (LinearLayout) findViewById(R.id.ll_activity_team_detail_customer_service);
        mImgTeamDetailTeamback = (ImageView) findViewById(R.id.img_team_detail_teamback);
        mLlActivityTeamDetailShare = (LinearLayout) findViewById(R.id.ll_activity_team_detail_share);
        mLlTeamDetailAlbum = (LinearLayout) findViewById(R.id.ll_team_detail_album);
        mLlTeamDetailDynamic = (LinearLayout) findViewById(R.id.ll_team_detail_dynamic);
        mTvActivityTeamDetailNodataHint = (TextView) findViewById(R.id.tv_activity_team_detail_nodata_hint);
        mTvTeamDetailManage = (TextView) findViewById(R.id.tv_team_detail_manage);

        mTvTitle.setText("战队详情");

        mTvTeamDetailGetCard.setOnClickListener(this);
        mTvActivityTeamDetailMore.setOnClickListener(this);
        mTvActivityTeamDetailShare.setOnClickListener(this);
        mTvActivityTeamDetailAddress.setOnClickListener(this);
        mTvActivityTeamDetailUpPackage.setOnClickListener(this);
        mLlTeamDetailMemeber.setOnClickListener(this);
        mLlActivityTeamDetailCustomerService.setOnClickListener(this);
        mLlActivityTeamDetailShare.setOnClickListener(this);
        resizeQrcodeSize();

    }

    //重置二维码宽高比
    private void resizeQrcodeSize() {
        int screenWidth = DensityUtils.getScreenWidth();
        int unit_width = (screenWidth - DensityUtils.dp2px(14) * 2 - DensityUtils.dp2px(20)) / 2;
        ViewGroup.LayoutParams layoutParamsLeader = mImgTeamDetailQrcodeLeader.getLayoutParams();
        layoutParamsLeader.height = unit_width;
        layoutParamsLeader.width = unit_width;
        mImgTeamDetailQrcodeLeader.setLayoutParams(layoutParamsLeader);
        ViewGroup.LayoutParams layoutParamsBattle = mImgTeamDetailQrcodeBattle.getLayoutParams();
        layoutParamsBattle.height = unit_width;
        layoutParamsBattle.width = unit_width;
        mImgTeamDetailQrcodeBattle.setLayoutParams(layoutParamsBattle);
    }

    private void initRefresh() {
        mRefreshActivityTeamDetail.setEnableRefresh(false);
        mRefreshActivityTeamDetail.setEnableAutoLoadMore(false);
        mRefreshActivityTeamDetail.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getDynamicdata(true, mTeam_id);
            }
        });
    }

    @Override
    public void initData() {

        initRefresh();

        initIndicator();

        Intent intent = getIntent();
        String team_id = intent.getStringExtra("team_id");
        if (!TextUtils.isEmpty(team_id)) {
            mTeam_id = team_id;
            getTeamMemberInfo(team_id);
            getTeamInfo(team_id);
            getRateRankData(team_id);
            getDistanceRankData(team_id);
            getTodayDistanceRank(team_id);
            getDynamicdata(false, team_id);
            recordTeamVisit(team_id);
            getTeamList();
        }

        registerReceiver(mThumbClickReceiver, intentFilter);

    }

    /**
     * 记录战队详情页访问
     */
    private void recordTeamVisit(final String teamId) {
        Map<String, String> map = new HashMap<>();
        map.put("team_id", teamId);
        HttpUtils.getInstance().post(Constants.RECORD_TEAM_VISIT, map, new HttpCallback<CommonBean>() {
            @Override
            public void onSuccessExecute(CommonBean commonBean) {
                if (commonBean.getStatus() == 0) {

                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mThumbClickReceiver);
    }

    /**
     * 处理底部功能条
     *
     * @param team_id
     */
    private void processBottomBar(String team_id) {
        //底部功能条模块
        boolean isJoined = SaveUtil.getBoolean(SaveConstants.IS_JOINED, false);
        boolean isSameTeam = team_id.equalsIgnoreCase(SaveUtil.getString(SaveConstants.TEAM_ID, ""));
        if (isJoined) {
            if (isSameTeam) {
                boolean is_can_upgrade_package = SaveUtil.getBoolean(SaveConstants.IS_CAN_UPGRADE_PACKAGE, false);
                if (is_can_upgrade_package) {
                    //底部功能模块
                    mLlActivityTeamDetailCustomerService.setVisibility(View.GONE);
                    mTvActivityTeamDetailAddress.setVisibility(View.VISIBLE);
                    mTvActivityTeamDetailUpPackage.setVisibility(View.VISIBLE);
                    mTvActivityTeamDetailSignup.setVisibility(View.GONE);
                } else {
                    //底部功能模块
                    mLlActivityTeamDetailCustomerService.setVisibility(View.VISIBLE);
                    mTvActivityTeamDetailAddress.setVisibility(View.VISIBLE);
                    mTvActivityTeamDetailUpPackage.setVisibility(View.GONE);
                    mTvActivityTeamDetailSignup.setVisibility(View.GONE);
                }
            } else {
                mLlActivityTeamDetailBottom.setVisibility(View.GONE);
            }
        } else {
            mLlActivityTeamDetailBottom.setVisibility(View.VISIBLE);
            //底部功能模块
            mLlActivityTeamDetailCustomerService.setVisibility(View.VISIBLE);
            mTvActivityTeamDetailAddress.setVisibility(View.GONE);
            mTvActivityTeamDetailUpPackage.setVisibility(View.GONE);
            mTvActivityTeamDetailSignup.setVisibility(View.VISIBLE);
            proccessSignupState(mTvActivityTeamDetailSignup);
        }
    }

    public void proccessSignupState(TextView textView) {
        if (mMemberData.getMember_count() >= 100) {
            textView.setText("战队已满");
            textView.setOnClickListener(null);
            return;
        }
        boolean is_sign_up_finish = false;//报名是否截止
        String sign_up_finish_date = SaveUtil.getString(SaveConstants.SIGN_UP_FINISH_DATE, "");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (!TextUtils.isEmpty(sign_up_finish_date)) {
            try {
                Date finishDate = simpleDateFormat.parse(sign_up_finish_date);
                Calendar instance = Calendar.getInstance();
                Date current = instance.getTime();
                instance.setTime(finishDate);
                instance.add(Calendar.DAY_OF_MONTH, 1);
                finishDate = instance.getTime();
                if (finishDate.before(current)) {
                    is_sign_up_finish = true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (is_sign_up_finish) {
            textView.setText("报名已截止");
            textView.setOnClickListener(null);
        } else {
            textView.setText("报名战队");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, SignupActivity.class);
                    intent.putExtra("team_id", mTeam_id);
                    mActivity.startActivity(intent);
                }
            });
        }
    }

    //初始化导航栏
    private void initIndicator() {

        final List<String> titles = new ArrayList<>();
        titles.add("今日距离");
        titles.add("个人完赛");
        titles.add("总距离");
        final CommonNavigator commonNavigator = new CommonNavigator(mActivity);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return titles == null ? 0 : titles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                final SimplePagerTitleView colorTransitionPagerTitleView = new ScaleTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#FF333333"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#FFFF1D1D"));
                colorTransitionPagerTitleView.setGravity(Gravity.CENTER);
                colorTransitionPagerTitleView.setText(titles.get(index));
                colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        commonNavigator.onPageSelected(index);
                        notifyDataSetChanged();
                        switch (index) {
                            case 0:
                                mRecyclerTeamDetailTodayDistanceRank.setVisibility(View.VISIBLE);
                                mRecyclerTeamDetailRateRank.setVisibility(View.GONE);
                                mRecyclerTeamDetailDistanceRank.setVisibility(View.GONE);
                                break;
                            case 1:
                                mRecyclerTeamDetailTodayDistanceRank.setVisibility(View.GONE);
                                mRecyclerTeamDetailRateRank.setVisibility(View.VISIBLE);
                                mRecyclerTeamDetailDistanceRank.setVisibility(View.GONE);
                                break;
                            case 2:
                                mRecyclerTeamDetailTodayDistanceRank.setVisibility(View.GONE);
                                mRecyclerTeamDetailRateRank.setVisibility(View.GONE);
                                mRecyclerTeamDetailDistanceRank.setVisibility(View.VISIBLE);
                                break;
                        }
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        mIndicatorActivityTeamDetail.setNavigator(commonNavigator);
        mRecyclerTeamDetailRateRank.setVisibility(View.GONE);
        mRecyclerTeamDetailDistanceRank.setVisibility(View.GONE);

    }

    /**
     * 获取已经创建的战队列表
     */
    public void getTeamList() {
        Map<String, String> map = new HashMap<>();
        map.put("activity_id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        HttpUtils.getInstance().get(Constants.MONITOR_TEAM_LIST, map, new HttpCallback<MonitorTeamListbean>() {
            @Override
            public void onSuccessExecute(MonitorTeamListbean monitorTeamListbean) {
                List<MonitorTeamListbean.DataBean> data = monitorTeamListbean.getData();
                if (data != null && data.size() > 0) {
                    for (int i = 0; i < data.size(); i++) {
                        MonitorTeamListbean.DataBean dataBean = data.get(i);
                        if (mTeam_id.equalsIgnoreCase(String.valueOf(dataBean.getId()))) {
                            mTvTeamDetailManage.setVisibility(View.VISIBLE);
                            mTvTeamDetailManage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(mActivity, TeamManageActivity.class);
                                    intent.putExtra("team_id",mTeam_id);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    /**
     * 获取个人完赛率排行榜数据
     */
    public void getRateRankData(final String team_id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", SaveUtil.getString("action_id", ""));
        map.put("team_id", team_id);
        map.put("count", "3");
        map.put("offset", "0");
        HttpUtils.getInstance().get(Constants.GAME_COMPLETE_RANKING, map, new HttpCallback<GameRankingBean>() {
            @Override
            public void onSuccessExecute(GameRankingBean gameRankingBean) {
                GameRankingBean.DataBean data = gameRankingBean.getData();
                GameRankingBean.DataBean.ListBean current = data.getCurrent();
                List<GameRankingBean.DataBean.ListBean> list = data.getList();
                if (list == null) {
                    list = new ArrayList<>();
                }
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setRank(i + 1);
                }
                if (team_id.equals(SaveUtil.getString(SaveConstants.TEAM_ID, ""))) {
                    list.add(0, current);
                }
                if (list.size() == 0) {
                    mTvActivityTeamDetailMore.setVisibility(View.GONE);
                    mTvActivityTeamDetailNodataHint.setVisibility(View.VISIBLE);
                }
                mCompleteRankBean = list;
                mCompleteRankingAdapter = new GameRankingAdapter(mActivity, mCompleteRankBean);
                mCompleteRankingAdapter.setCompleteRank(false);
                mCompleteRankingAdapter.setSameTeam(team_id.equals(SaveUtil.getString(SaveConstants.TEAM_ID, "")));
                mRecyclerTeamDetailRateRank.setNestedScrollingEnabled(false);
                mRecyclerTeamDetailRateRank.setHasFixedSize(true);
                mRecyclerTeamDetailRateRank.setLayoutManager(new LinearLayoutManager(mActivity));
                mRecyclerTeamDetailRateRank.setAdapter(mCompleteRankingAdapter);
            }
        });
    }

    /**
     * 获取今日距离排行榜
     */
    public void getTodayDistanceRank(final String team_id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        map.put("team_id", team_id);
        map.put("count", "3");
        map.put("offset", "0");
        HttpUtils.getInstance().get(Constants.DISTANCE_TODAY_RANKING, map, new HttpCallback<TodayDistanceRankBean>() {
            @Override
            public void onSuccessExecute(TodayDistanceRankBean todayDistanceRankingBean) {
                TodayDistanceRankBean.DataBean data = todayDistanceRankingBean.getData();
                if (data == null) {
                    return;
                }
                TodayDistanceRankBean.DataBean.CurrentBean current = data.getCurrent();
                List<TodayDistanceRankBean.DataBean.CurrentBean> list = data.getList();
                if (list == null) {
                    list = new ArrayList<>();
                }
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setRank(i + 1);
                }
                if (team_id.equals(SaveUtil.getString(SaveConstants.TEAM_ID, ""))) {
                    if (current != null) {
                        current.setMyself(true);
                        list.add(0, current);
                    }
                }
                mTodayRankBean = list;
                mTodayDistanceRankAdapter = new TodayDistanceRankAdapter(mActivity, mTodayRankBean);
                mTodayDistanceRankAdapter.setCompleteRank(false);
                mRecyclerTeamDetailTodayDistanceRank.setLayoutManager(new LinearLayoutManager(mActivity));
                mRecyclerTeamDetailTodayDistanceRank.setAdapter(mTodayDistanceRankAdapter);
            }
        });
    }

    /**
     * 获取总距离排行榜数据
     */
    public void getDistanceRankData(final String team_id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", SaveUtil.getString("action_id", ""));
        map.put("team_id", team_id);
        map.put("count", "3");
        map.put("offset", "0");
        HttpUtils.getInstance().get(Constants.DISTANCE_RANKING, map, new HttpCallback<DistanceRankingBean>() {
            @Override
            public void onSuccessExecute(DistanceRankingBean distanceRankingBean) {
                DistanceRankingBean.DataBean data = distanceRankingBean.getData();
                if (data != null) {
                    List<DistanceRankingBean.DataBean.ListBean> list = data.getList();
                    DistanceRankingBean.DataBean.ListBean current = data.getCurrent();
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setRank(i + 1);
                    }
                    if (team_id.equals(SaveUtil.getString(SaveConstants.TEAM_ID, ""))) {
                        list.add(0, current);
                    }
                    mTotalRankBean = list;
                    mTotalRankingAdapter = new DistanceRankingAdapter(mActivity, mTotalRankBean);
                    mTotalRankingAdapter.setSameTeam(team_id.equals(SaveUtil.getString(SaveConstants.TEAM_ID, "")) ? true : false);
                    mTotalRankingAdapter.setCompleteRank(false);
                    mRecyclerTeamDetailDistanceRank.setNestedScrollingEnabled(false);
                    mRecyclerTeamDetailDistanceRank.setHasFixedSize(true);
                    mRecyclerTeamDetailDistanceRank.setLayoutManager(new LinearLayoutManager(mActivity));
                    mRecyclerTeamDetailDistanceRank.setAdapter(mTotalRankingAdapter);

                }
            }
        });
    }

    /**
     * 初始化相册
     */
    private void initAlbum(List<BattleInfoBean.DataBean.PhotosBean> photos) {
        if (photos == null || photos.size() == 0) {
            mLlTeamDetailAlbum.setVisibility(View.GONE);
            return;
        }
        if (photos != null) {
            mRecyclerTeamDetailAlbum.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
            mRecyclerTeamDetailAlbum.setAdapter(new TeamDetailAlbumAdapter(mActivity, photos));
        }
    }

    /**
     * 获取动态列表
     */
    public void getDynamicdata(final boolean isLoadMore, String team_id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", team_id);//队伍id
        map.put("count", "10");
        if (!TextUtils.isEmpty(mDynamic_last_id)) {
            map.put("last_id", mDynamic_last_id);
        }
        HttpUtils.getInstance().get(Constants.TEAM_DETAIL_DYNAMIC, map, new HttpCallback<BattleDetailDynamicBean>() {
            @Override
            public void onPreExcute() {
                super.onPreExcute();
                if (isLoadMore) {
                    mRefreshActivityTeamDetail.finishLoadMore();
                }
            }

            @Override
            public void onSuccessExecute(BattleDetailDynamicBean battleDetailDynamicBean) {
                List<BattleDetailDynamicBean.DataBean> data = battleDetailDynamicBean.getData();
                if (data == null || data.size() == 0) {
                    if (!isLoadMore) {
                        mLlTeamDetailDynamic.setVisibility(View.GONE);
                        mRefreshActivityTeamDetail.setEnableLoadMore(false);
                    }
                }
                if (data != null && data.size() > 0) {
                    mDynamicBeans.addAll(data);
                    if (isFirstLoad) {
                        mTeamDetailDynamicAdapter = new TeamDetailDynamicAdapter(mActivity, mDynamicBeans);
                        //禁止滑动
                        mRecyclerTeamDetailDynamic.setNestedScrollingEnabled(false);
                        mRecyclerTeamDetailDynamic.setLayoutManager(new LinearLayoutManager(mActivity));
                        mRecyclerTeamDetailDynamic.setAdapter(mTeamDetailDynamicAdapter);
                        isFirstLoad = false;
                    } else {
                        mTeamDetailDynamicAdapter.setDataBeans(mDynamicBeans);
                    }
                    BattleDetailDynamicBean.DataBean dataBean = data.get(data.size() - 1);
                    mDynamic_last_id = dataBean.getId() + "";

                }
            }
        });
    }

    /**
     * 获取战队成员信息
     *
     * @param id 队伍id
     */
    private void getTeamMemberInfo(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        HttpUtils.getInstance().get(Constants.TEAM_MEMBER_INFO, map, new HttpCallback<BattleMemberBean>() {
            @Override
            public void onSuccessExecute(BattleMemberBean battleMemberBean) {
                List<BattleMemberBean.DataBean> data = battleMemberBean.getData();
                if (data != null && data.size() > 0) {
                    mTvTeamDetailMemeberCount.setText("战队" + data.size() + "人");
                    mFlowTeamDetailMemeber.setFlag(true);
                    //todo  ---替换图片
                    int count = data.size() < 6 ? data.size() : 6;
                    List<FlowHeadLayout.CascadingBean> cascadingBeans = new ArrayList<>();
                    if (count < 6) {
                        FlowHeadLayout.CascadingBean cascadingBean = new FlowHeadLayout.CascadingBean();
                        cascadingBean.setType(2);
                        cascadingBean.setResourceId(R.drawable.icon_head_default);
                        cascadingBeans.add(cascadingBean);
                    }
                    for (int i = count - 1; i >= 0; i--) {
                        FlowHeadLayout.CascadingBean cascadingBean = new FlowHeadLayout.CascadingBean();
                        cascadingBean.setType(1);
                        cascadingBean.setUrl(data.get(i).getAvatar());
                        cascadingBeans.add(cascadingBean);
                    }
                    mFlowTeamDetailMemeber.addHeadViews(cascadingBeans);
                } else {
                    mTvTeamDetailMemeberCount.setText("战队0人");
                    List<FlowHeadLayout.CascadingBean> cascadingBeans = new ArrayList<>();
                    FlowHeadLayout.CascadingBean cascadingBean = new FlowHeadLayout.CascadingBean();
                    cascadingBean.setType(2);
                    cascadingBean.setResourceId(R.drawable.icon_head_default);
                    cascadingBeans.add(cascadingBean);
                    mFlowTeamDetailMemeber.addHeadViews(cascadingBeans);
                }
            }
        });
    }

    /**
     * 获取战队信息
     */
    private void getTeamInfo(final String teamId) {
        Map<String, String> map = new HashMap<>();
        map.put("id", teamId);
        HttpUtils.getInstance().get(Constants.TEAM_INFO, map, new HttpCallback<BattleInfoBean>() {
            @Override
            public void onSuccessExecute(BattleInfoBean battleInfoBean) {
                mMemberData = battleInfoBean.getData();
                if (mMemberData != null) {
                    Glide.with(BaseApplication.mApplicationContext).load(mMemberData.getLogo()).into(mImgTeamDetailTeamLogo);
                    Glide.with(BaseApplication.mApplicationContext).load(mMemberData.getLogo()).into(mImgTeamDetailTeamback);
                    mTvTeamDetailTeamName.setText(mMemberData.getName());
                    GlideUtils.getInstance().loadCircleImage(mMemberData.getMonitor_avatar(),mImgTeamDetailHead);
                    mTvTeamDetailName.setText("队长：" + mMemberData.getMonitor_name());
                    mTvTeamDetailAchievement.setText(mMemberData.getDescription());

                    String team_id = SaveUtil.getString("team_id", "");

                    if (teamId.equals(team_id)) {
                        //导航栏模块
                        mLlTeamDetailQrcode.setVisibility(View.VISIBLE);
                        mViewTeamDetailQrcode.setVisibility(View.VISIBLE);
                        Glide.with(BaseApplication.mApplicationContext).load(mMemberData.getMonitor_qr_code()).into(mImgTeamDetailQrcodeLeader);
                        Glide.with(BaseApplication.mApplicationContext).load(mMemberData.getGroup_qr_code()).into(mImgTeamDetailQrcodeBattle);
                    } else {
                        mLlTeamDetailQrcode.setVisibility(View.GONE);
                        mViewTeamDetailQrcode.setVisibility(View.GONE);
                    }

                    initAlbum(mMemberData.getPhotos());

                    //todo
//                    processBottomBar(teamId);

                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.activity_team_detail;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ll_team_detail_memeber:
                intent.setClass(mActivity, TeamMemberActivity.class);
                intent.putExtra("team_id", mTeam_id);
                startActivity(intent);
                break;
            case R.id.tv_activity_team_detail_more:
                intent.setClass(mActivity, TeamRankingActivity.class);
                intent.putExtra("team_id", mTeam_id);
                if (mRecyclerTeamDetailTodayDistanceRank.getVisibility() == View.VISIBLE) {
                    intent.putExtra("type", 1);
                }
                if (mRecyclerTeamDetailRateRank.getVisibility() == View.VISIBLE) {
                    intent.putExtra("type", 2);
                }
                if (mRecyclerTeamDetailDistanceRank.getVisibility() == View.VISIBLE) {
                    intent.putExtra("type", 3);
                }
                startActivity(intent);
                break;
            case R.id.tv_activity_team_detail_address:
                startActivity(new Intent(mActivity, ShippingAddressActivity.class));
                break;
            case R.id.tv_activity_team_detail_package:
                startActivity(new Intent(mActivity, UpgradePackageActivity.class));
                break;
            case R.id.tv_team_detail_getCard:
                Intent inviteIntent = new Intent(mActivity, CommonWebActivity.class);
                String url = Constants.DOMAIN + "?env=gio&jwt="
                        + SaveUtil.getString("Authorization", "")
                        + "#/team/invite_card/" + SaveUtil.getString(SaveConstants.ACTION_ID, "");
                url = url + "/" + mTeam_id;
                inviteIntent.putExtra("url", url);
                inviteIntent.putExtra("title", "邀请卡");
                startActivity(inviteIntent);
                break;
            case R.id.ll_activity_team_detail_customer_service:
                Intent questionIntent = new Intent(mActivity, CommonWebActivity.class);
                String question_url = Constants.DOMAIN + "?env=gio&jwt="
                        + SaveUtil.getString("Authorization", "")
                        + "#/feedback";
                questionIntent.putExtra("url", question_url);
                questionIntent.putExtra("title", "问题咨询");
                startActivity(questionIntent);
                break;
            case R.id.ll_activity_team_detail_share:
                String link = Constants.DOMAIN + "?env=gio&jwt="
                        + SaveUtil.getString("Authorization", "")
                        + "#/team/detail/share/" + mTeam_id;
                ShareDialog shareDialog = new ShareDialog(mActivity);
                shareDialog.setData("点击进入百日跑社区，十万跑步大咖在这里", "万人坚持跑步打卡100天，看到坚持的力量",
                        link, "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1037789319,445993728&fm=26&gp=0.jpg");
                shareDialog.show();
                break;
        }
    }

    public static final String THUMB_ACTION = "com.tourye.run";

    public class ThumbClickReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(THUMB_ACTION)) {
                boolean isThumb = intent.getBooleanExtra("isThumb", false);
                int thumbCount = intent.getIntExtra("thumbCount", 998);
                int userId = intent.getIntExtra("userId", 998);

                if (mTodayDistanceRankAdapter == null || mCompleteRankingAdapter == null || mTotalRankingAdapter == null) {
                    return;
                }
                for (int i = 0; i < mTodayRankBean.size(); i++) {
                    TodayDistanceRankBean.DataBean.CurrentBean currentBean = mTodayRankBean.get(i);
                    if (currentBean.getId() == userId) {
                        currentBean.setThumb_up_count(thumbCount);
                        currentBean.setHas_thumb_up(isThumb);
                    }
                }
                for (int i = 0; i < mCompleteRankBean.size(); i++) {
                    GameRankingBean.DataBean.ListBean listBean = mCompleteRankBean.get(i);
                    if (listBean.getId() == userId) {
                        listBean.setHas_thumb_up(isThumb);
                        listBean.setThumb_up_count(thumbCount);
                    }
                }
                for (int i = 0; i < mTotalRankBean.size(); i++) {
                    DistanceRankingBean.DataBean.ListBean listBean = mTotalRankBean.get(i);
                    if (listBean.getId() == userId) {
                        listBean.setHas_thumb_up(isThumb);
                        listBean.setThumb_up_count(thumbCount);
                    }
                }
                mTodayDistanceRankAdapter.setRankBeans(mTodayRankBean);
                mCompleteRankingAdapter.setListBeans(mCompleteRankBean);
                mTotalRankingAdapter.setListBeans(mTotalRankBean);
            }
        }
    }
}
