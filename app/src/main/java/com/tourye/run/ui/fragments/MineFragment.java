package com.tourye.run.ui.fragments;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseFragment;
import com.tourye.run.bean.CheckContractStatusBean;
import com.tourye.run.bean.CheckNewsBean;
import com.tourye.run.bean.LeaderApplyStatusBean;
import com.tourye.run.bean.MonitorTeamListbean;
import com.tourye.run.bean.StatisticsBean;
import com.tourye.run.bean.UserBasicInfoBean;
import com.tourye.run.bean.UserCenterAdBean;
import com.tourye.run.bean.WaitCheckCountBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.common.CommonWebActivity;
import com.tourye.run.ui.activities.common.SingleImageActivity;
import com.tourye.run.ui.activities.home.CreateBattleActivity;
import com.tourye.run.ui.activities.home.LeaderApplyActivity;
import com.tourye.run.ui.activities.mine.BonusWithdrawalActivity;
import com.tourye.run.ui.activities.mine.FunctionSettingActivity;
import com.tourye.run.ui.activities.mine.LogisticsActivity;
import com.tourye.run.ui.activities.mine.MessageNotificationActivity;
import com.tourye.run.ui.activities.mine.MultiTeamManageActivity;
import com.tourye.run.ui.activities.mine.PersonInfoActivity;
import com.tourye.run.ui.activities.mine.TeamManageActivity;
import com.tourye.run.ui.activities.punch.CheckPunchActivity;
import com.tourye.run.ui.activities.punch.PunchRecordActivity;
import com.tourye.run.ui.adapter.MineVpAdapter;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by longlongren on 2019/3/11.
 * <p>
 * introduce:个人中心页面
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mImgFragmentMineHead;//头像
    private TextView mTvFragmentMineName;//姓名
    private TextView mTvFragmentMineId;//id
    private ImageView mImgFragmentMineDetail;//修改个人信息
    private TextView mTvFragmentMineKilometerTotal;//累计公里
    private TextView mTvFragmentMinePunchDays;//打卡天数
    private TextView mTvFragmentMineTimeTotal;//累计时长
    private LinearLayout mLlFragmentMinePunchRecord;//打卡记录
    private LinearLayout mLlFragmentMinePunchReview;//打卡审核
    private LinearLayout mLlFragmentMineDynamic;//我的动态
    private ViewPager mVpFragmentMine;//广告
    private LinearLayout mLlFragmentMineSetting;//功能设置
    private LinearLayout mLlFragmentMineMessage;//消息通知
    private ImageView mImgFragmentMineMessagePoint;
    private LinearLayout mLlFragmentTeamManage;//队伍管理
    private LinearLayout mLlFragmentMinePrize;//奖金提现
    private LinearLayout mLlFragmentMineLogistics;//物流列表
    private ImageView mImgFragmentMinePunchReviewPoint;//是否有新的审核打卡
    private LinearLayout mLlFragmentBecomeLeader;//担任队长
    private LinearLayout mLlFragmentMineQuestionConsult;//问题咨询
    private SmartRefreshLayout mRefreshFragmentMine;
    private RelativeLayout mRlFragmentMinePerson;

    private CheckNewsBean.DataBean messageNotifybean;//是否有新的消息通知
    private List<MonitorTeamListbean.DataBean> mTeamBeans;

    private String userName;//用户名
    private String userHead;//用户头像

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10086:
                    int currentItem = mVpFragmentMine.getCurrentItem();
                    mVpFragmentMine.setCurrentItem(++currentItem);
                    mHandler.sendEmptyMessageDelayed(10086, 3000);
                    break;
            }
        }
    };
    private String mAvatar;//头像地址

    @Override
    public void initView(View view) {
        mImgFragmentMineHead = (ImageView) view.findViewById(R.id.img_fragment_mine_head);
        mTvFragmentMineName = (TextView) view.findViewById(R.id.tv_fragment_mine_name);
        mTvFragmentMineId = (TextView) view.findViewById(R.id.tv_fragment_mine_id);
        mImgFragmentMineDetail = (ImageView) view.findViewById(R.id.img_fragment_mine_detail);
        mTvFragmentMineKilometerTotal = (TextView) view.findViewById(R.id.tv_fragment_mine_kilometer_total);
        mTvFragmentMinePunchDays = (TextView) view.findViewById(R.id.tv_fragment_mine_punch_days);
        mTvFragmentMineTimeTotal = (TextView) view.findViewById(R.id.tv_fragment_mine_time_total);
        mLlFragmentMinePunchRecord = (LinearLayout) view.findViewById(R.id.ll_fragment_mine_punch_record);
        mLlFragmentMinePunchReview = (LinearLayout) view.findViewById(R.id.ll_fragment_mine_punch_review);
        mLlFragmentMineDynamic = (LinearLayout) view.findViewById(R.id.ll_fragment_mine_dynamic);
        mVpFragmentMine = (ViewPager) view.findViewById(R.id.vp_fragment_mine);
        mLlFragmentMineSetting = (LinearLayout) view.findViewById(R.id.ll_fragment_mine_setting);
        mLlFragmentMineMessage = (LinearLayout) view.findViewById(R.id.ll_fragment_mine_message);
        mImgFragmentMineMessagePoint = (ImageView) view.findViewById(R.id.img_fragment_mine_message_point);
        mLlFragmentTeamManage = (LinearLayout) view.findViewById(R.id.ll_fragment_team_manage);
        mLlFragmentMinePrize = (LinearLayout) view.findViewById(R.id.ll_fragment_mine_prize);
        mLlFragmentMineLogistics = (LinearLayout) view.findViewById(R.id.ll_fragment_mine_logistics);
        mImgFragmentMinePunchReviewPoint = (ImageView) view.findViewById(R.id.img_fragment_mine_punch_review_point);
        mLlFragmentBecomeLeader = (LinearLayout) view.findViewById(R.id.ll_fragment_become_leader);
        mLlFragmentMineQuestionConsult = (LinearLayout) view.findViewById(R.id.ll_fragment_mine_question_consult);
        mRefreshFragmentMine = (SmartRefreshLayout) view.findViewById(R.id.refresh_fragment_mine);
        mRlFragmentMinePerson = (RelativeLayout) view.findViewById(R.id.rl_fragment_mine_person);

        mTvTitle.setText("我的");
        mLlFragmentBecomeLeader.setOnClickListener(this);
        mRlFragmentMinePerson.setOnClickListener(this);
        mLlFragmentMineSetting.setOnClickListener(this);
        mLlFragmentMineMessage.setOnClickListener(this);
        mLlFragmentMinePunchRecord.setOnClickListener(this);
        mLlFragmentTeamManage.setOnClickListener(this);
        mLlFragmentMinePrize.setOnClickListener(this);
        mLlFragmentMineDynamic.setOnClickListener(this);
        mLlFragmentMineQuestionConsult.setOnClickListener(this);
        mImgFragmentMineHead.setOnClickListener(this);

        mLlFragmentMineLogistics.setOnClickListener(this);

        mRefreshFragmentMine.setEnableLoadMore(false);
        mRefreshFragmentMine.setEnableRefresh(false);

    }

    @Override
    public void initData() {

        initVp();

    }

    @Override
    public void onResume() {
        super.onResume();

        checkNews();

        getCheckNum();

        getUserBasicInfo();

        getUserStatsData();

        checkLeaderApplyStatus();

    }

    @Override
    public void refreshData() {
        super.refreshData();

        checkLeaderApplyStatus();

        checkNews();

        getCheckNum();

    }

    /**
     * 获取可审核打卡数
     */
    private void getCheckNum() {
        //可审核打卡数
        if (SaveUtil.getBoolean(SaveConstants.IS_JOINED, false)) {
            Map<String, String> map = new HashMap<>();
            map.put("id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
            HttpUtils.getInstance().get(Constants.CHECK_SIGN_IN_COUNT, map, new HttpCallback<WaitCheckCountBean>() {
                @Override
                public void onSuccessExecute(WaitCheckCountBean waitCheckCountBean) {
                    String data = waitCheckCountBean.getData();
                    if (data == null) {
                        return;
                    }
                    int number = Integer.parseInt(data);
                    if (number <= 0) {
                        mImgFragmentMinePunchReviewPoint.setVisibility(View.GONE);
                    } else {
                        mImgFragmentMinePunchReviewPoint.setVisibility(View.VISIBLE);
                    }
                    mLlFragmentMinePunchReview.setOnClickListener(MineFragment.this);
                }
            });
        } else {
            mImgFragmentMinePunchReviewPoint.setVisibility(View.GONE);
            mLlFragmentMinePunchReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mActivity, "报名后方可审核", Toast.LENGTH_SHORT).show();
                }
            });
        }
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
                    //todo
                    if (data.isApplied()) {
                        if ("accepted".equalsIgnoreCase(data.getStatus())) {
                            //队长申请成功
                            getTeamList();
                        }
                    }

                }
            }
        });
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
                    mTeamBeans = data;
                    mLlFragmentTeamManage.setVisibility(View.VISIBLE);
                    mLlFragmentBecomeLeader.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 获取用户打卡距离统计数据
     */
    private void getUserStatsData() {
        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().get(Constants.USER_STATS, map, new HttpCallback<StatisticsBean>() {
            @Override
            public void onSuccessExecute(StatisticsBean statisticsBean) {
                StatisticsBean.DataBean data = statisticsBean.getData();
                if (data == null) {
                    return;
                }
                int totalDistance = (int) Math.floor(Double.parseDouble(data.getTotal_distance()));
                mTvFragmentMineKilometerTotal.setText(totalDistance+"");
                mTvFragmentMinePunchDays.setText(data.getTotal_days() + "");
                int total_time = data.getTotal_time();
                if (total_time==0) {
                    mTvFragmentMineTimeTotal.setText("0");
                }else{
                    mTvFragmentMineTimeTotal.setText(Math.floor(total_time/60.0f*100)/100+"");
                }
            }
        });
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
                userName=data.getNickname();
                userHead=data.getAvatar();
                mTvFragmentMineName.setText(data.getNickname());
                mAvatar = data.getAvatar();
                GlideUtils.getInstance().loadCircleImage(data.getAvatar(), mImgFragmentMineHead);
                mTvFragmentMineId.setText("ID:" + data.getSerial_number() + "");
                SaveUtil.putString(SaveConstants.USER_NAME, data.getNickname());
                SaveUtil.putBoolean(SaveConstants.IS_VERIFIED, data.isReal_name_authenticated());
            }
        });
    }

    /**
     * 是否有最新消息
     */
    private void checkNews() {
        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().get(Constants.CHECK_NEWS, map, new HttpCallback<CheckNewsBean>() {

            @Override
            public void onSuccessExecute(CheckNewsBean checkNewsBean) {
                CheckNewsBean.DataBean data = checkNewsBean.getData();
                if (data == null) {
                    return;
                }
                messageNotifybean = data;
                if (data.isLike() || data.isNotice() || data.isReply()) {
                    mImgFragmentMineMessagePoint.setVisibility(View.VISIBLE);
                } else {
                    mImgFragmentMineMessagePoint.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 初始化广告轮播
     */
    private void initVp() {
        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().get(Constants.USER_CENTER_ADVERTISEMENT, map, new HttpCallback<UserCenterAdBean>() {
            @Override
            public void onSuccessExecute(UserCenterAdBean userCenterAdBean) {
                List<UserCenterAdBean.DataBean> data = userCenterAdBean.getData();
                if (data != null && data.size() > 0) {
                    MineVpAdapter mineVpAdapter = new MineVpAdapter(mActivity, data);
                    mVpFragmentMine.setAdapter(mineVpAdapter);
                    mHandler.sendEmptyMessageDelayed(10086, 3000);
                }
            }
        });
    }

    /**
     * 检查提现是否签约
     */
    public void checkContractStatus() {
        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().get(Constants.CHECK_CONTRACT, map, new HttpCallback<CheckContractStatusBean>() {
            @Override
            public void onSuccessExecute(CheckContractStatusBean checkContractStatusBean) {
                CheckContractStatusBean.DataBean data = checkContractStatusBean.getData();
                if (data == null) {
                    return;
                }
                Intent intent = new Intent(mActivity, BonusWithdrawalActivity.class);
                intent.putExtra("is_need_signing", data.isNeed_contract());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean isNeedTitle() {
        return true;
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_fragment_mine_person:
                Intent userIntent = new Intent(mActivity, PersonInfoActivity.class);
                startActivity(userIntent);
                break;
            case R.id.ll_fragment_mine_setting:
                startActivity(new Intent(mActivity, FunctionSettingActivity.class));
                break;
            case R.id.ll_fragment_mine_message:
                Intent messageIntent = new Intent(mActivity, MessageNotificationActivity.class);
                messageIntent.putExtra("data", messageNotifybean);
                startActivity(messageIntent);
                break;
            case R.id.ll_fragment_mine_punch_record:
                startActivity(new Intent(mActivity, PunchRecordActivity.class));
                break;
            case R.id.ll_fragment_team_manage:
                if (mTeamBeans != null && mTeamBeans.size() == 1) {
                    Intent teamManageIntent = new Intent(mActivity, TeamManageActivity.class);
                    teamManageIntent.putExtra("team_id", mTeamBeans.get(0).getId() + "");
                    startActivity(teamManageIntent);
                } else if (mTeamBeans != null && mTeamBeans.size() > 1) {
                    startActivity(new Intent(mActivity, MultiTeamManageActivity.class));
                }
                break;
            case R.id.ll_fragment_mine_prize:
                checkContractStatus();
                break;
            case R.id.ll_fragment_mine_dynamic:
                FindFragment.UpdateStatusBean updateStatusBean = new FindFragment.UpdateStatusBean();
                updateStatusBean.setType(1);
                EventBus.getDefault().post(updateStatusBean);
                break;
            case R.id.ll_fragment_mine_punch_review:
                startActivity(new Intent(mActivity, CheckPunchActivity.class));
                break;
            case R.id.ll_fragment_mine_logistics:
                startActivity(new Intent(mActivity, LogisticsActivity.class));
                break;
            case R.id.ll_fragment_become_leader:
                Intent leaderIntent = new Intent(mActivity, CommonWebActivity.class);
                leaderIntent.putExtra("url", "https://mp.weixin.qq.com/s/B8yF7QbjKNk6JSlYROXkew");
                startActivity(leaderIntent);
                break;
            case R.id.ll_fragment_mine_question_consult:
                Intent questionIntent = new Intent(mActivity, CommonWebActivity.class);
                String url = Constants.DOMAIN+"?env=gio&jwt="
                        + SaveUtil.getString("Authorization", "")
                        + "#/feedback";
                questionIntent.putExtra("url", url);
                questionIntent.putExtra("title","问题反馈");
                startActivity(questionIntent);
                break;
            case R.id.img_fragment_mine_head:
                if (TextUtils.isEmpty(mAvatar)) {
                    return;
                }
                Intent intent = new Intent(mActivity, SingleImageActivity.class);
                intent.putExtra("url",mAvatar);
                startActivity(intent);
                break;
        }
    }

}
