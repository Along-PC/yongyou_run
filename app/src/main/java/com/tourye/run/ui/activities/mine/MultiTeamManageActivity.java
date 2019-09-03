package com.tourye.run.ui.activities.mine;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
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
import com.tourye.run.bean.RecruitStatusBean;
import com.tourye.run.bean.TeamAwardBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.common.CommonWebActivity;
import com.tourye.run.ui.adapter.SignupAdvertiseAdapter;
import com.tourye.run.ui.adapter.TeamAwardAdapter;
import com.tourye.run.ui.adapter.TeamManageBattleAdapter;
import com.tourye.run.utils.DateCalculateUtils;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;
import com.tourye.run.views.MeasureGridView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:   MultiTeamManageActivity
 *
 * @Author:   along
 *
 * @Description:    多支战队管理页面
 *
 * @CreateDate:   2019/4/16 3:20 PM
 *
 */
public class MultiTeamManageActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImgActivityMultiTeamManageLogo;
    private TextView mTvActivityMultiTeamManageName;
    private TextView mTvActivityMultiTeamManageCutoff;
    private TextView mTvActivityMultiTeamManageMemberCount;
    private TextView mTvActivityMultiTeamManageBonus;
    private TextView mTvActivityMultiTeamManageWithdraw;
    private RecyclerView mRecyclerActivityMultiTeamManageTeamList;
    private MeasureGridView mGridActivityMultiTeamManageAward;
    private LinearLayout mLlActivityMultiTeamManageRecruit;
    private ViewPager mVpActivityMultiTeamManageAdvertising;
    private RadioGroup mRgActivityMultiTeamManageAdvertising;

    private RelativeLayout mRlActivityMultiTeamUpPunchRate;
    private RelativeLayout mRlActivityMultiTeamAttractHundred;
    private RelativeLayout mRlActivityMultiTeamActivityRule;
    private RelativeLayout mRlActivityMultiTeamNeedKnowledge;

    private RelativeLayout mRlRecruitRankFirst;
    private ImageView mImgRecruitRankFirst;
    private ImageView mImgRecruitRankGold;
    private RelativeLayout mRlRecruitRankSecond;
    private ImageView mImgRecruitRankSecond;
    private ImageView mImgRecruitRankSilver;
    private RelativeLayout mRlRecruitRankThird;
    private ImageView mImgRecruitRankThird;
    private ImageView mImgRecruitRankCopper;
    private TextView mTvMultiTeamManageContract;//查看队长合同
    private View mViewMultiTeamManageContract;
    private LinearLayout mLlMultiTeamManageContract;

    private int mAdvertiseSize;//广告数量
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10010:
                    int currentItem = mVpActivityMultiTeamManageAdvertising.getCurrentItem();
                    ++currentItem;
                    RadioButton childAt = (RadioButton) mRgActivityMultiTeamManageAdvertising.getChildAt(currentItem%mAdvertiseSize);
                    childAt.setChecked(true);
                    mVpActivityMultiTeamManageAdvertising.setCurrentItem(currentItem);
                    mHandler.sendEmptyMessageDelayed(10010, 3000);
                    break;
                case 998:
                    mTvActivityMultiTeamManageCutoff.setText(DateCalculateUtils.calculate(System.currentTimeMillis(), mFinalDateTime));
                    mHandler.sendEmptyMessageDelayed(998,1000);
                    break;
            }
        }
    };
    private long mFinalDateTime;//报名截止时间

    @Override
    public void initView() {
        mImgActivityMultiTeamManageLogo = (ImageView) findViewById(R.id.img_activity_multi_team_manage_logo);
        mTvActivityMultiTeamManageName = (TextView) findViewById(R.id.tv_activity_multi_team_manage_name);
        mTvActivityMultiTeamManageCutoff = (TextView) findViewById(R.id.tv_activity_multi_team_manage_cutoff);
        mTvActivityMultiTeamManageMemberCount = (TextView) findViewById(R.id.tv_activity_multi_team_manage_memberCount);
        mTvActivityMultiTeamManageBonus = (TextView) findViewById(R.id.tv_activity_multi_team_manage_bonus);
        mTvActivityMultiTeamManageWithdraw = (TextView) findViewById(R.id.tv_activity_multi_team_manage_withdraw);
        mRecyclerActivityMultiTeamManageTeamList = (RecyclerView) findViewById(R.id.recycler_activity_multi_team_manage_teamList);
        mGridActivityMultiTeamManageAward = (MeasureGridView) findViewById(R.id.grid_activity_multi_team_manage_award);
        mRlActivityMultiTeamUpPunchRate = (RelativeLayout) findViewById(R.id.rl_activity_multi_team_up_punchRate);
        mRlActivityMultiTeamAttractHundred = (RelativeLayout) findViewById(R.id.rl_activity_multi_team_attract_hundred);
        mRlActivityMultiTeamActivityRule = (RelativeLayout) findViewById(R.id.rl_activity_multi_team_activity_rule);
        mRlActivityMultiTeamNeedKnowledge = (RelativeLayout) findViewById(R.id.rl_activity_multi_team_need_knowledge);
        mLlActivityMultiTeamManageRecruit = (LinearLayout) findViewById(R.id.ll_activity_multi_team_manage_recruit);
        mVpActivityMultiTeamManageAdvertising = (ViewPager) findViewById(R.id.vp_activity_multi_team_manage_advertising);
        mRgActivityMultiTeamManageAdvertising = (RadioGroup) findViewById(R.id.rg_activity_multi_team_manage_advertising);
        mRlRecruitRankFirst = (RelativeLayout) findViewById(R.id.rl_recruit_rank_first);
        mImgRecruitRankFirst = (ImageView) findViewById(R.id.img_recruit_rank_first);
        mImgRecruitRankGold = (ImageView) findViewById(R.id.img_recruit_rank_gold);
        mRlRecruitRankSecond = (RelativeLayout) findViewById(R.id.rl_recruit_rank_second);
        mImgRecruitRankSecond = (ImageView) findViewById(R.id.img_recruit_rank_second);
        mImgRecruitRankSilver = (ImageView) findViewById(R.id.img_recruit_rank_silver);
        mRlRecruitRankThird = (RelativeLayout) findViewById(R.id.rl_recruit_rank_third);
        mImgRecruitRankThird = (ImageView) findViewById(R.id.img_recruit_rank_third);
        mImgRecruitRankCopper = (ImageView) findViewById(R.id.img_recruit_rank_copper);
        mTvMultiTeamManageContract = (TextView) findViewById(R.id.tv_multi_team_manage_contract);
        mViewMultiTeamManageContract = (View) findViewById(R.id.view_multi_team_manage_contract);
        mLlMultiTeamManageContract = (LinearLayout) findViewById(R.id.ll_multi_team_manage_contract);

        mTvTitle.setText("队伍管理");
        mLlActivityMultiTeamManageRecruit.setOnClickListener(this);
        mRlActivityMultiTeamUpPunchRate.setOnClickListener(this);
        mRlActivityMultiTeamAttractHundred.setOnClickListener(this);
        mRlActivityMultiTeamActivityRule.setOnClickListener(this);
        mRlActivityMultiTeamNeedKnowledge.setOnClickListener(this);
        mTvActivityMultiTeamManageWithdraw.setOnClickListener(this);
    }

    @Override
    public void initData() {

        getRecruitData();

        getTeamList();

        getTeamAward();

        initAdvertise();

        getRankData();

        calculateCutoff();

        getLeaderContract();

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
                mTvActivityMultiTeamManageCutoff.setText(DateCalculateUtils.calculate(System.currentTimeMillis(), mFinalDateTime));
                mHandler.sendEmptyMessageDelayed(998,1000);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取招募信息
     */
    public void getRecruitData(){
        Map<String,String> map=new HashMap<>();
        map.put("activity_id",SaveUtil.getString(SaveConstants.ACTION_ID,""));
        HttpUtils.getInstance().get(Constants.MONITOR_RECRUIT_STATS, map, new HttpCallback<RecruitStatusBean>() {
            @Override
            public void onSuccessExecute(RecruitStatusBean recruitStatusBean) {
                RecruitStatusBean.DataBean data = recruitStatusBean.getData();
                if (data!=null) {
                    mTvActivityMultiTeamManageBonus.setText(10000*data.getTeam_count()+"");
                    mTvActivityMultiTeamManageMemberCount.setText(data.getMember_count()+"");
                }
            }
        });
    }

    /**
     * 获取创建的队伍列表
     */
    public void getTeamList(){
        Map<String,String> map=new HashMap<>();
        map.put("activity_id",SaveUtil.getString(SaveConstants.ACTION_ID,""));
        HttpUtils.getInstance().get(Constants.MONITOR_TEAM_LIST, map, new HttpCallback<MonitorTeamListbean>() {
            @Override
            public void onSuccessExecute(MonitorTeamListbean monitorTeamListbean) {
                List<MonitorTeamListbean.DataBean> data = monitorTeamListbean.getData();
                if (data!=null && data.size()>0) {
                    TeamManageBattleAdapter teamManageBattleAdapter = new TeamManageBattleAdapter(data, mActivity);
                    mRecyclerActivityMultiTeamManageTeamList.setLayoutManager(new LinearLayoutManager(mActivity));
                    mRecyclerActivityMultiTeamManageTeamList.setAdapter(teamManageBattleAdapter);
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
                    mGridActivityMultiTeamManageAward.setAdapter(teamAwardAdapter);
                }
            }
        });
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
                        mRgActivityMultiTeamManageAdvertising.addView(radioButton);
                    }
                    RadioButton childAt = (RadioButton) mRgActivityMultiTeamManageAdvertising.getChildAt(0);
                    childAt.setChecked(true);
                    SignupAdvertiseAdapter signupAdvertiseAdapter = new SignupAdvertiseAdapter(mActivity, data);
                    mVpActivityMultiTeamManageAdvertising.setAdapter(signupAdvertiseAdapter);
                    mHandler.sendEmptyMessageDelayed(10010, 3000);
                    mVpActivityMultiTeamManageAdvertising.setOnTouchListener(new View.OnTouchListener() {
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
                    mTvMultiTeamManageContract.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mActivity, CommonWebActivity.class);
                            intent.putExtra("url",contract);
                            startActivity(intent);
                        }
                    });
                }else{
                    mTvMultiTeamManageContract.setVisibility(View.GONE);
                    mViewMultiTeamManageContract.setVisibility(View.GONE);
                    mLlMultiTeamManageContract.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.activity_multi_team_manage;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_activity_multi_team_up_punchRate:
                goWeb("https://mp.weixin.qq.com/s/qnKtWKLuBvV3KDYSrPktgg");
                break;
            case R.id.rl_activity_multi_team_attract_hundred:
                goWeb("https://mp.weixin.qq.com/s/95Bd3fAPuGCNrzTzIKxcFw");
                break;
            case R.id.rl_activity_multi_team_activity_rule:
                goWeb("https://mp.weixin.qq.com/s/JB9G-HDdJnZxPyfKXGlhxg");
                break;
            case R.id.rl_activity_multi_team_need_knowledge:
                goWeb("https://mp.weixin.qq.com/s/nnpxHBBxwSp86eXfpekGAQ");
                break;
            case R.id.ll_activity_multi_team_manage_recruit:
                startActivity(new Intent(mActivity,RecruitRankActivity.class));
                break;
            case R.id.tv_activity_multi_team_manage_withdraw:
                checkContractStatus();
                break;
        }
    }
}
