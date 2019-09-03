package com.tourye.run.ui.activities.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.AdvertiseBean;
import com.tourye.run.bean.BattleRecruitBean;
import com.tourye.run.bean.CheckContractStatusBean;
import com.tourye.run.bean.CommonJsonBean;
import com.tourye.run.bean.MonitorTeamListbean;
import com.tourye.run.bean.TeamAwardBean;
import com.tourye.run.bean.TeamInfluenceBean;
import com.tourye.run.bean.TeamInfoBean;
import com.tourye.run.bean.TeamPunchBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.common.CommonWebActivity;
import com.tourye.run.ui.activities.home.CreateBattleActivity;
import com.tourye.run.ui.activities.home.TeamDetailActivity;
import com.tourye.run.ui.adapter.SignupAdvertiseAdapter;
import com.tourye.run.ui.adapter.TeamAwardAdapter;
import com.tourye.run.ui.adapter.TeamManageInfluenceAdapter;
import com.tourye.run.ui.dialogs.ShareDialog;
import com.tourye.run.ui.dialogs.TeamManageCalendarDialog;
import com.tourye.run.utils.DateCalculateUtils;
import com.tourye.run.utils.DateFormatUtils;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;
import com.tourye.run.views.MeasureGridView;
import com.tourye.run.views.RingView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  
 * @ClassName:   TeamManageActivity
 *
 * @Author:   along
 * 
 * @Description:  队伍管理页面
 * 
 * @CreateDate:   2019/3/22 11:12 AM
 * 
 */
public class TeamManageActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImgActivityTeamManageLogo;
    private TextView mTvActivityTeamManageName;
    private TextView mTvActivityTeamManageCutoff;//截止日期
    private ImageView mImgActivityTeamManageSetting;
    private TextView mTvActivityTeamManageMemberCount;
    private TextView mTvActivityTeamManageBonus;
    private TextView mTvActivityTeamManageWithdraw;
    private TextView mTvActivityTeamManageVisitCount;
    private RecyclerView mRecyclerActivityTeamManageVisit;
    private MeasureGridView mGridActivityTeamManageAward;//阶梯奖励
    private LinearLayout mLlActivityTeamManageRecruit;
    private RelativeLayout mRlRecruitRankFirst;
    private ImageView mImgRecruitRankFirst;
    private ImageView mImgRecruitRankGold;
    private RelativeLayout mRlRecruitRankSecond;
    private ImageView mImgRecruitRankSecond;
    private ImageView mImgRecruitRankSilver;
    private RelativeLayout mRlRecruitRankThird;
    private ImageView mImgRecruitRankThird;
    private ImageView mImgRecruitRankCopper;
    private RelativeLayout mRlActivityTeamUpPunchRate;
    private RelativeLayout mRlActivityTeamAttractHundred;
    private RelativeLayout mRlActivityTeamActivityRule;
    private RelativeLayout mRlActivityTeamNeedKnowledge;
    private ViewPager mVpActivityTeamManageAdvertising;
    private RadioGroup mRgActivityTeamManageAdvertising;
    private RingView mRingActivityTeamManage;
    private LinearLayout mLlActivityTeamManageVisit;
    private TextView mTvActivityTeamManageUnpunch;//未打卡
    private TextView mTvActivityTeamManageTocheck;//待审核
    private TextView mTvActivityTeamManageNopass;//未通过
    private LinearLayout mLlActivityTeamManagePunch;//队内打卡情况
    private TextView mTvActivityTeamManageSuccessToAll;//成功人数对比总人数
    private TextView mTvActivityTeamManagePunchDetail;//打卡详情
    private ImageView mImgActivityTeamManageScale;
    private TextView mTvActivityTeamManageContract;
    private TextView mTvActivityTeamManagePunchDate;//选择查看打卡日期
    private TextView mTvActivityTeamManagePunchHint;
    private RelativeLayout mRlActivityTeamManagePunch;
    private ImageView mImgActivityTeamManageMongolianLayer;//蒙层背景
    private LinearLayout mLlActivityTeamManageShare;
    private TextView mTvActivityTeamManageInvite;
    private TextView mTvActivityTeamManageHost;
    private LinearLayout mLlActivityTeamManageMember;
    private LinearLayout mLlActivityTeamManageFinishRate;
    private TextView mTvActivityTeamManageFinishRate;
    private LinearLayout mLlActivityTeamManageBonus;
    private LinearLayout mLlActivityTeamManageSetting;
    private RelativeLayout mRlActivityTeamManageContract;
    private LinearLayout mLlActivityTeamManageFlagApply;

    private String mTeamId;//队伍id
    private long mFinalDateTime;//报名截止时间
    //日期选择弹窗
    private TeamManageCalendarDialog teamManageCalendarDialog ;

    private int mAdvertiseSize;//广告数量
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10010:
                    int currentItem = mVpActivityTeamManageAdvertising.getCurrentItem();
                    ++currentItem;
                    RadioButton childAt = (RadioButton) mRgActivityTeamManageAdvertising.getChildAt(currentItem%mAdvertiseSize);
                    childAt.setChecked(true);
                    mVpActivityTeamManageAdvertising.setCurrentItem(currentItem);
                    mHandler.sendEmptyMessageDelayed(10010, 3000);
                    break;
                case 998:
                    mTvActivityTeamManageCutoff.setText(DateCalculateUtils.calculate(System.currentTimeMillis(), mFinalDateTime));
                    mHandler.sendEmptyMessageDelayed(998,1000);
                    break;
            }
        }
    };
    private TeamInfoBean.DataBean mTeamInfoBeanData;

    @Override
    public void initView() {
        mImgActivityTeamManageLogo = (ImageView) findViewById(R.id.img_activity_team_manage_logo);
        mTvActivityTeamManageName = (TextView) findViewById(R.id.tv_activity_team_manage_name);
        mTvActivityTeamManageCutoff = (TextView) findViewById(R.id.tv_activity_team_manage_cutoff);
        mImgActivityTeamManageSetting = (ImageView) findViewById(R.id.img_activity_team_manage_setting);
        mTvActivityTeamManageMemberCount = (TextView) findViewById(R.id.tv_activity_team_manage_memberCount);
        mTvActivityTeamManageBonus = (TextView) findViewById(R.id.tv_activity_team_manage_bonus);
        mTvActivityTeamManageWithdraw = (TextView) findViewById(R.id.tv_activity_team_manage_withdraw);
        mTvActivityTeamManageVisitCount = (TextView) findViewById(R.id.tv_activity_team_manage_visitCount);
        mRecyclerActivityTeamManageVisit = (RecyclerView) findViewById(R.id.recycler_activity_team_manage_visit);
        mGridActivityTeamManageAward = (MeasureGridView) findViewById(R.id.grid_activity_team_manage_award);
        mLlActivityTeamManageRecruit = (LinearLayout) findViewById(R.id.ll_activity_team_manage_recruit);
        mRlRecruitRankFirst = (RelativeLayout) findViewById(R.id.rl_recruit_rank_first);
        mImgRecruitRankFirst = (ImageView) findViewById(R.id.img_recruit_rank_first);
        mImgRecruitRankGold = (ImageView) findViewById(R.id.img_recruit_rank_gold);
        mRlRecruitRankSecond = (RelativeLayout) findViewById(R.id.rl_recruit_rank_second);
        mImgRecruitRankSecond = (ImageView) findViewById(R.id.img_recruit_rank_second);
        mImgRecruitRankSilver = (ImageView) findViewById(R.id.img_recruit_rank_silver);
        mRlRecruitRankThird = (RelativeLayout) findViewById(R.id.rl_recruit_rank_third);
        mImgRecruitRankThird = (ImageView) findViewById(R.id.img_recruit_rank_third);
        mImgRecruitRankCopper = (ImageView) findViewById(R.id.img_recruit_rank_copper);
        mRlActivityTeamUpPunchRate = (RelativeLayout) findViewById(R.id.rl_activity_team_up_punchRate);
        mRlActivityTeamAttractHundred = (RelativeLayout) findViewById(R.id.rl_activity_team_attract_hundred);
        mRlActivityTeamActivityRule = (RelativeLayout) findViewById(R.id.rl_activity_team_activity_rule);
        mRlActivityTeamNeedKnowledge = (RelativeLayout) findViewById(R.id.rl_activity_team_need_knowledge);
        mVpActivityTeamManageAdvertising = (ViewPager) findViewById(R.id.vp_activity_team_manage_advertising);
        mRgActivityTeamManageAdvertising = (RadioGroup) findViewById(R.id.rg_activity_team_manage_advertising);
        mLlActivityTeamManageVisit = (LinearLayout) findViewById(R.id.ll_activity_team_manage_visit);
        mRingActivityTeamManage = (RingView) findViewById(R.id.ring_activity_team_manage);
        mTvActivityTeamManageUnpunch = (TextView) findViewById(R.id.tv_activity_team_manage_unpunch);
        mTvActivityTeamManageTocheck = (TextView) findViewById(R.id.tv_activity_team_manage_tocheck);
        mTvActivityTeamManageNopass = (TextView) findViewById(R.id.tv_activity_team_manage_nopass);
        mLlActivityTeamManagePunch = (LinearLayout) findViewById(R.id.ll_activity_team_manage_punch);
        mTvActivityTeamManageSuccessToAll = (TextView) findViewById(R.id.tv_activity_team_manage_successToAll);
        mTvActivityTeamManagePunchDetail = (TextView) findViewById(R.id.tv_activity_team_manage_punch_detail);
        mImgActivityTeamManageScale = (ImageView) findViewById(R.id.img_activity_team_manage_scale);
        mTvActivityTeamManageContract = (TextView) findViewById(R.id.tv_activity_team_manage_contract);
        mTvActivityTeamManagePunchDate = (TextView) findViewById(R.id.tv_activity_team_manage_punch_date);
        mTvActivityTeamManagePunchHint = (TextView) findViewById(R.id.tv_activity_team_manage_punch_hint);
        mRlActivityTeamManagePunch = (RelativeLayout) findViewById(R.id.rl_activity_team_manage_punch);
        mImgActivityTeamManageMongolianLayer = (ImageView) findViewById(R.id.img_activity_team_manage_mongolianLayer);
        mLlActivityTeamManageShare = (LinearLayout) findViewById(R.id.ll_activity_team_manage_share);
        mTvActivityTeamManageInvite = (TextView) findViewById(R.id.tv_activity_team_manage_invite);
        mTvActivityTeamManageHost = (TextView) findViewById(R.id.tv_activity_team_manage_host);
        mLlActivityTeamManageMember = (LinearLayout) findViewById(R.id.ll_activity_team_manage_member);
        mLlActivityTeamManageFinishRate = (LinearLayout) findViewById(R.id.ll_activity_team_manage_finish_rate);
        mTvActivityTeamManageFinishRate = (TextView) findViewById(R.id.tv_activity_team_manage_finish_rate);
        mLlActivityTeamManageBonus = (LinearLayout) findViewById(R.id.ll_activity_team_manage_bonus);
        mLlActivityTeamManageSetting = (LinearLayout) findViewById(R.id.ll_activity_team_manage_setting);
        mRlActivityTeamManageContract = (RelativeLayout) findViewById(R.id.rl_activity_team_manage_contract);
        mLlActivityTeamManageFlagApply = (LinearLayout) findViewById(R.id.ll_activity_team_manage_flag_apply);

        mTvTitle.setText("队伍管理");
        mTvTitle.setFocusable(true);
        mTvTitle.setFocusableInTouchMode(true);
        mTvTitle.requestFocus();
        mRlActivityTeamUpPunchRate.setOnClickListener(this);
        mRlActivityTeamAttractHundred.setOnClickListener(this);
        mRlActivityTeamActivityRule.setOnClickListener(this);
        mRlActivityTeamNeedKnowledge.setOnClickListener(this);
        mLlActivityTeamManageRecruit.setOnClickListener(this);
        mLlActivityTeamManageVisit.setOnClickListener(this);
        mTvActivityTeamManageWithdraw.setOnClickListener(this);
        mLlActivityTeamManageSetting.setOnClickListener(this);
        mTvActivityTeamManagePunchDate.setOnClickListener(this);
        mLlActivityTeamManageShare.setOnClickListener(this);
        mTvActivityTeamManageInvite.setOnClickListener(this);
        mTvActivityTeamManageHost.setOnClickListener(this);
        mLlActivityTeamManageFlagApply.setOnClickListener(this);

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String team_id = intent.getStringExtra("team_id");
        mTeamId=team_id;

        isInActivityPeriod();

        getTeamInfoData(team_id);

        getTeamInfluence(team_id);

        getTeamAward();

        getRankData();

        initAdvertise();

        getLeaderContract();

        calculateCutoff();

        String date = DateFormatUtils.formatDateInPoint(Calendar.getInstance().getTime());
        mTvActivityTeamManagePunchDate.setText(date);
        cutoverStatus(DateFormatUtils.formatDate(Calendar.getInstance().getTime()));

        teamManageCalendarDialog= new TeamManageCalendarDialog(mActivity);
        teamManageCalendarDialog.setOnChooseListener(new TeamManageCalendarDialog.OnChooseListener() {
            @Override
            public void onChoose(String date) {
                cutoverStatus(date);
                date=date.replace("-",".");
                mTvActivityTeamManagePunchDate.setText(date);
            }
        });
        teamManageCalendarDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                teamManageCalendarDialog.hide();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (teamManageCalendarDialog!=null) {
            teamManageCalendarDialog.dismiss();
        }
    }

    /**
     * 是否在活动期间
     */
    private void isInActivityPeriod() {
        String sign_in_start_date = SaveUtil.getString(SaveConstants.SIGN_IN_START_DATE, "");
        String sign_in_finish_date = SaveUtil.getString(SaveConstants.SIGN_IN_FINISH_DATE, "");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date finishDate = simpleDateFormat.parse(sign_in_finish_date);
            Date startDate = simpleDateFormat.parse(sign_in_start_date);
            Date currenrDate = Calendar.getInstance().getTime();
            if (currenrDate.after(startDate) && currenrDate.before(finishDate)) {
                mLlActivityTeamManageFinishRate.setVisibility(View.VISIBLE);
                mLlActivityTeamManageMember.setVisibility(View.GONE);
                mLlActivityTeamManagePunch.setVisibility(View.VISIBLE);
            }else{
                mLlActivityTeamManageFinishRate.setVisibility(View.GONE);
                mLlActivityTeamManageMember.setVisibility(View.VISIBLE);
                mLlActivityTeamManagePunch.setVisibility(View.GONE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取队伍每日打卡情况
     */
    public void getTeamPunchData(final String date){
        Map<String,String> map=new HashMap<>();
        map.put("activity_id",SaveUtil.getString(SaveConstants.ACTION_ID,""));
        map.put("team_id",mTeamId);
        map.put("date",date);
        HttpUtils.getInstance().get(Constants.TEAM_SIGN_IN, map, new HttpCallback<TeamPunchBean>() {
            @Override
            public void onSuccessExecute(TeamPunchBean teamPunchBean) {
                final TeamPunchBean.DataBean data = teamPunchBean.getData();
                if (data!=null) {
                    mTvActivityTeamManageNopass.setText(data.getRejected_count()+"");
                    mTvActivityTeamManageUnpunch.setText(data.getNo_sign_in_count()+"");
                    mTvActivityTeamManageTocheck.setText(data.getNormal_count()+"");
                    mTvActivityTeamManageSuccessToAll.setText(data.getSuccess_count()+"/"+data.getMember_count());
                    final float temp = data.getSuccess_count() / (float)data.getMember_count() * 360;
                    if (temp>0) {
                        mRingActivityTeamManage.post(new Runnable() {
                            @Override
                            public void run() {
                                mRingActivityTeamManage.startAnim(temp);
                            }
                        });
                    }
                    mTvActivityTeamManagePunchDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mActivity, PunchDetailActivity.class);
                            intent.putExtra("date",date);
                            intent.putExtra("team_id",mTeamId);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }

    /**
     * 获取队伍奖励
     */
    private void getTeamAward() {
        Map<String,String> map=new HashMap<>();
        map.put("activity_id",SaveUtil.getString("action_id",""));
        HttpUtils.getInstance().get(Constants.TEAM_AWARD, map, new HttpCallback<TeamAwardBean>() {

            @Override
            public void onSuccessExecute(TeamAwardBean teamAwardBean) {
                List<TeamAwardBean.DataBean> data = teamAwardBean.getData();
                if (data!=null && data.size()>0) {
                    TeamAwardAdapter teamAwardAdapter = new TeamAwardAdapter(mActivity, data);
                    mGridActivityTeamManageAward.setAdapter(teamAwardAdapter);
                }
            }
        });
    }

    /**
     * 获取影响人数
     */
    private void getTeamInfluence(String team_id) {
        Map<String,String> map=new HashMap<>();
        map.put("activity_id",SaveUtil.getString("action_id",""));
        map.put("team_id",team_id);
        HttpUtils.getInstance().get(Constants.TEAM_INFLUENCE, map, new HttpCallback<TeamInfluenceBean>() {
            @Override
            public void onSuccessExecute(TeamInfluenceBean teamInfluenceBean) {
                TeamInfluenceBean.DataBean data = teamInfluenceBean.getData();
                if (data!=null) {
                    List<TeamInfluenceBean.DataBean.ListBean> list = data.getList();
                    mTvActivityTeamManageVisitCount.setText("太棒了，你影响了"+(data.getJoined()+data.getNot_joined())+"人，其中"+data.getJoined()+"人已报名该战队！");
                    if (list!=null && list.size()>0) {
                        TeamManageInfluenceAdapter teamManageInfluenceAdapter = new TeamManageInfluenceAdapter(mActivity, list);
                        mRecyclerActivityTeamManageVisit.setLayoutManager(new GridLayoutManager(mActivity,4,LinearLayoutManager.VERTICAL,false));
                        mRecyclerActivityTeamManageVisit.setAdapter(teamManageInfluenceAdapter);
                    }else{
                        mRecyclerActivityTeamManageVisit.setVisibility(View.GONE);
                        mTvActivityTeamManageVisitCount.setText("暂时没有数据");
                    }
                }else{
                    mRecyclerActivityTeamManageVisit.setVisibility(View.GONE);
                    mTvActivityTeamManageVisitCount.setText("暂时没有数据");
                }
            }
        });

    }

    /**
     * 获取队伍数据
     */
    private void getTeamInfoData(String team_id) {
        Map<String,String> map=new HashMap<>();
        map.put("id",team_id+"");
        HttpUtils.getInstance().get(Constants.TEAM_DATA, map, new HttpCallback<TeamInfoBean>() {
            @Override
            public void onSuccessExecute(TeamInfoBean teamInfoBean) {
                mTeamInfoBeanData = teamInfoBean.getData();
                if (mTeamInfoBeanData !=null) {
                    mTvActivityTeamManageName.setText(mTeamInfoBeanData.getName());
                    mTvActivityTeamManageMemberCount.setText(mTeamInfoBeanData.getMember_count()+"");
                    mTvActivityTeamManageBonus.setText("10000");
                    GlideUtils.getInstance().loadImage(mTeamInfoBeanData.getLogo(),mImgActivityTeamManageScale);
                    GlideUtils.getInstance().loadImage(mTeamInfoBeanData.getLogo(), mImgActivityTeamManageLogo);
                    mImgActivityTeamManageMongolianLayer.setVisibility(View.VISIBLE);
                    mTvActivityTeamManageFinishRate.setText(mTeamInfoBeanData.getRate()+"%");
                }
            }
        });
    }

    /**
     * 计算报名截止时间
     */
    private void calculateCutoff() {
        String sign_up_finish_date = SaveUtil.getString(SaveConstants.SIGN_UP_FINISH_DATE, "");
        if (!TextUtils.isEmpty(sign_up_finish_date)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parse = simpleDateFormat.parse(sign_up_finish_date);
                Calendar instance = Calendar.getInstance();
                instance.setTime(parse);
                instance.add(Calendar.DAY_OF_MONTH,1);
                Date final_date = instance.getTime();
                mFinalDateTime = final_date.getTime();
                mTvActivityTeamManageCutoff.setText(DateCalculateUtils.calculate(System.currentTimeMillis(), mFinalDateTime));
                mHandler.sendEmptyMessageDelayed(998,1000);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 获取队伍招募排行榜
     */
    public void getRankData(){
        Map<String,String> map=new HashMap<>();
        map.put("id",SaveUtil.getString(SaveConstants.ACTION_ID,""));
        map.put("count",10+"");
        map.put("offset","0");
        HttpUtils.getInstance().get(Constants.team_recruit_rank, map, new HttpCallback<BattleRecruitBean>() {
            @Override
            public void onSuccessExecute(BattleRecruitBean battleRecruitBean) {
                BattleRecruitBean.DataBean data = battleRecruitBean.getData();
                if (data==null) {
                    return;
                }
                BattleRecruitBean.DataBean.CurrentBean current = data.getCurrent();
                List<BattleRecruitBean.DataBean.CurrentBean> list = data.getList();
                if (list!=null && list.size()>0) {
                    for (int i = 0; i < list.size(); i++) {
                        switch (i) {
                            case 0:
                                mRlRecruitRankFirst.setVisibility(View.VISIBLE);
                                GlideUtils.getInstance().loadCircleImage(list.get(i).getAvatar(),mImgRecruitRankFirst);
                                break;
                            case 1:
                                mRlRecruitRankSecond.setVisibility(View.VISIBLE);
                                GlideUtils.getInstance().loadCircleImage(list.get(i).getAvatar(),mImgRecruitRankSecond);
                                break;
                            case 2:
                                mRlRecruitRankThird.setVisibility(View.VISIBLE);
                                GlideUtils.getInstance().loadCircleImage(list.get(i).getAvatar(),mImgRecruitRankThird);
                                break;
                        }
                    }
                }
            }
        });

    }

    /**
     * 初始化广告
     */
    private void initAdvertise() {
        Map<String, String> map = new HashMap<>();
        map.put("id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        map.put("type", "team_manager");
        HttpUtils.getInstance().get(Constants.ADVERTISE, map, new HttpCallback<AdvertiseBean>() {
            @Override
            public void onSuccessExecute(AdvertiseBean advertiseBean) {
                List<AdvertiseBean.DataBean> data = advertiseBean.getData();
                if (data != null && data.size() > 0) {
                    mAdvertiseSize=data.size();
                    for (int i = 0; i < data.size(); i++) {
                        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.rightMargin=DensityUtils.dp2px(5);
                        RadioButton radioButton = new RadioButton(mActivity);
                        radioButton.setWidth(DensityUtils.dp2px(6));
                        radioButton.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
                        radioButton.setHeight(DensityUtils.dp2px(6));
                        radioButton.setBackgroundResource(R.drawable.selector_advertise_rb);
                        radioButton.setLayoutParams(layoutParams);
                        mRgActivityTeamManageAdvertising.addView(radioButton);
                    }
                    RadioButton childAt = (RadioButton) mRgActivityTeamManageAdvertising.getChildAt(0);
                    childAt.setChecked(true);
                    SignupAdvertiseAdapter signupAdvertiseAdapter = new SignupAdvertiseAdapter(mActivity, data);
                    mVpActivityTeamManageAdvertising.setAdapter(signupAdvertiseAdapter);
                    mHandler.sendEmptyMessageDelayed(10010, 3000);
                    mVpActivityTeamManageAdvertising.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    mHandler.removeMessages(10010);
                                    break;
                                case MotionEvent.ACTION_UP:
                                    mHandler.sendEmptyMessageDelayed(10010, 3000);
                                    break;
                            }
                            return false;
                        }
                    });
                }
            }
        });
    }

    /**
     * 获取队长合同
     */
    public void getLeaderContract(){
        Map<String,String> map=new HashMap<>();
        map.put("id",SaveUtil.getString(SaveConstants.ACTION_ID,""));
        HttpUtils.getInstance().get(Constants.GET_LEADER_CONTRACT, map, new HttpCallback<CommonJsonBean>() {
            @Override
            public void onSuccessExecute(CommonJsonBean commonJsonBean) {
                final String contract = commonJsonBean.getData();
                if (!TextUtils.isEmpty(contract)) {
                    mTvActivityTeamManageContract.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mActivity, CommonWebActivity.class);
                            intent.putExtra("url",contract);
                            startActivity(intent);
                        }
                    });
                }else{
                    mRlActivityTeamManageContract.setVisibility(View.GONE);
                }
            }
        });
    }

    public void goWeb(String url){
        Intent intent = new Intent(mActivity,CommonWebActivity.class);
        intent.putExtra("url",url);
        mActivity.startActivity(intent);
    }

    /**
     * 检查提现是否签约
     */
    public void checkContractStatus(){
        Map<String,String> map=new HashMap<>();
        HttpUtils.getInstance().get(Constants.CHECK_CONTRACT, map, new HttpCallback<CheckContractStatusBean>() {
            @Override
            public void onSuccessExecute(CheckContractStatusBean checkContractStatusBean) {
                CheckContractStatusBean.DataBean data = checkContractStatusBean.getData();
                if (data==null) {
                    return;
                }
                Intent intent = new Intent(mActivity, BonusWithdrawalActivity.class);
                intent.putExtra("is_need_signing",data.isNeed_contract());
                startActivity(intent);
            }
        });
    }

    /**
     * 切换打卡明细模块状态
     */
    public void cutoverStatus(String date){
        String finish_date = SaveUtil.getString(SaveConstants.SIGN_IN_FINISH_DATE, "");
        String start_date = SaveUtil.getString(SaveConstants.SIGN_IN_START_DATE, "");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        Date finishDate;
        Date chooseDate;
        try {
            startDate=simpleDateFormat.parse(start_date);
            finishDate=simpleDateFormat.parse(finish_date);
            chooseDate=simpleDateFormat.parse(date);
            int i = chooseDate.compareTo(startDate);
            int i1 = chooseDate.compareTo(finishDate);
            if (i>=0 && i1<=0) {
                mRlActivityTeamManagePunch.setVisibility(View.VISIBLE);
                mTvActivityTeamManagePunchHint.setVisibility(View.GONE);
                getTeamPunchData(date);
            }else{
                mRlActivityTeamManagePunch.setVisibility(View.GONE);
                mTvActivityTeamManagePunchHint.setVisibility(View.VISIBLE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRootView() {
        return R.layout.activity_team_manage;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_activity_team_up_punchRate:
                goWeb("https://mp.weixin.qq.com/s/qnKtWKLuBvV3KDYSrPktgg");
                break;
            case R.id.rl_activity_team_attract_hundred:
                goWeb("https://mp.weixin.qq.com/s/95Bd3fAPuGCNrzTzIKxcFw");
                break;
            case R.id.rl_activity_team_activity_rule:
                goWeb("https://mp.weixin.qq.com/s/JB9G-HDdJnZxPyfKXGlhxg");
                break;
            case R.id.rl_activity_team_need_knowledge:
                goWeb("https://mp.weixin.qq.com/s/nnpxHBBxwSp86eXfpekGAQ");
                break;
            case R.id.ll_activity_team_manage_recruit:
                startActivity(new Intent(mActivity,RecruitRankActivity.class));
                break;
            case R.id.ll_activity_team_manage_visit:
                Intent influenceIntent = new Intent(mActivity, InfluencePeopleActivity.class);
                influenceIntent.putExtra("team_id",mTeamId);
                startActivity(influenceIntent);
                break;
            case R.id.tv_activity_team_manage_withdraw:
                checkContractStatus();
                break;
            case R.id.ll_activity_team_manage_setting:
                Intent intent = new Intent(mActivity, CreateBattleActivity.class);
                intent.putExtra("type",1);
                intent.putExtra("team_id",mTeamId);
                startActivity(intent);
                break;
            case R.id.tv_activity_team_manage_punch_date:
                teamManageCalendarDialog.show();
                break;
            case R.id.tv_activity_team_manage_host:
                Intent detailIntent = new Intent(mActivity, TeamDetailActivity.class);
                detailIntent.putExtra("team_id",mTeamId);
                startActivity(detailIntent);
                break;
            case R.id.ll_activity_team_manage_share:
                String share_first_title = SaveUtil.getString(SaveConstants.SHARE_FIRST_TITLE, "");
                String share_second_title = SaveUtil.getString(SaveConstants.SHARE_SECOND_TITLE, "");
                String user_name = SaveUtil.getString(SaveConstants.USER_NAME, "");
                if (!TextUtils.isEmpty(share_first_title)) {
                    share_first_title=share_first_title.replace("${user_name}",user_name);
                    if (mTeamInfoBeanData!=null) {
                        share_first_title=share_first_title.replace("${team_name}",mTeamInfoBeanData.getName());
                    }
                }
                String link = Constants.DOMAIN + "?env=gio"
                        + "#/activity/submit/order/"+SaveUtil.getString(SaveConstants.ACTION_ID,"")+"/" + mTeamId;
                ShareDialog shareDialog = new ShareDialog(mActivity);
                shareDialog.setData(share_first_title, share_second_title,
                        link, "https://static.run100.runorout.cn/meta/logo2.png");
                shareDialog.show();
                break;
            case R.id.ll_activity_team_manage_flag_apply:
                Intent flag = new Intent(mActivity, CommonWebActivity.class);
                String flagUrl=Constants.DOMAIN + "?env=gio&jwt="
                        + SaveUtil.getString("Authorization", "")
                        +"#/team/identification";
                flag.putExtra("url",flagUrl);
                flag.putExtra("title","队旗申请");
                startActivity(flag);
                break;
            case R.id.tv_activity_team_manage_invite:
                Intent inviteIntent = new Intent(mActivity, CommonWebActivity.class);
                String inviteUrl = Constants.DOMAIN + "?env=gio&jwt="
                        + SaveUtil.getString("Authorization", "")
                        + "#/team/invite_card/" + SaveUtil.getString(SaveConstants.ACTION_ID, "");
                inviteUrl = inviteUrl + "/" + mTeamId;
                inviteIntent.putExtra("url", inviteUrl);
                inviteIntent.putExtra("title", "邀请卡");
                startActivity(inviteIntent);
                break;
        }
    }

}
