package com.tourye.run.ui.activities.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.ui.adapter.RankingVpAdapter;
import com.tourye.run.ui.fragments.PersonTodayDistanceRankFragment;
import com.tourye.run.ui.fragments.PersonTotalDistanceRankingFragment;
import com.tourye.run.ui.fragments.PersonCompleteRankingFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TeamRankingActivity
 * @Author: along
 * @Description: 排行榜页面
 * @CreateDate: 2019/3/19 3:27 PM
 */
public class TeamRankingActivity extends BaseActivity implements View.OnClickListener {
    private MagicIndicator mIndicatorActivityRanking;
    private ViewPager mVpActivityRanking;
    private TextView mTvActivityRankingOrder;
    private ImageView mImgActivityRankingOrder;
    private LinearLayout mLlActivityRankingOrder;

    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> mFragments=new ArrayList<>();
    private RankingVpAdapter mRankingVpAdapter;
    private PersonTodayDistanceRankFragment mPersonTodayDistanceRankFragment;
    private PersonCompleteRankingFragment mPersonCompleteRankingFragment;
    private PersonTotalDistanceRankingFragment mPersonTotalDistanceRankingFragment;
    private int mType;
    private TeamRankingActivity.ThumbClickReceiver thumbClickReceiver = new TeamRankingActivity.ThumbClickReceiver();
    private IntentFilter intentFilter = new IntentFilter(TeamRankingActivity.THUMB_ACTION);
    @Override
    public void initView() {
        mIndicatorActivityRanking = (MagicIndicator) findViewById(R.id.indicator_activity_ranking);
        mVpActivityRanking = (ViewPager) findViewById(R.id.vp_activity_ranking);
        mTvActivityRankingOrder = (TextView) findViewById(R.id.tv_activity_ranking_order);
        mImgActivityRankingOrder = (ImageView) findViewById(R.id.img_activity_ranking_order);
        mLlActivityRankingOrder = (LinearLayout) findViewById(R.id.ll_activity_ranking_order);

        mTvTitle.setText("排行榜");
        mLlActivityRankingOrder.setOnClickListener(this);

    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        mType = intent.getIntExtra("type", 998);

        initVp();
        initIndicator();

        registerReceiver(thumbClickReceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(thumbClickReceiver);
    }

    private void initVp() {
        Intent intent = getIntent();
        String team_id = intent.getStringExtra("team_id");
        mPersonTodayDistanceRankFragment = new PersonTodayDistanceRankFragment();
        mPersonCompleteRankingFragment = new PersonCompleteRankingFragment();
        mPersonTotalDistanceRankingFragment = new PersonTotalDistanceRankingFragment();

        Bundle bundle = new Bundle();
        bundle.putString("team_id",team_id);
        mPersonTodayDistanceRankFragment.setArguments(bundle);
        mPersonCompleteRankingFragment.setArguments(bundle);
        mPersonTotalDistanceRankingFragment.setArguments(bundle);
        mFragments.add(mPersonTodayDistanceRankFragment);
        mFragments.add(mPersonCompleteRankingFragment);
        mFragments.add(mPersonTotalDistanceRankingFragment);
        mRankingVpAdapter = new RankingVpAdapter(getSupportFragmentManager(), mFragments);
        mVpActivityRanking.setAdapter(mRankingVpAdapter);
        mVpActivityRanking.setCurrentItem(0);
    }

    /**
     * 初始化导航栏
     */
    private void initIndicator() {
        mTitles.add("今日距离");
        mTitles.add("个人完赛");
        mTitles.add("总距离");
        CommonNavigator commonNavigator = new CommonNavigator(mActivity);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitles == null ? 0 : mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(getResources().getColor(R.color.color_font_black));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#CC1C24"));
                colorTransitionPagerTitleView.setText(mTitles.get(index));
                colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mVpActivityRanking.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#CC1C24"));
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return null;
            }
        });
        mIndicatorActivityRanking.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicatorActivityRanking, mVpActivityRanking);
        switch (mType) {
            case 1:
                commonNavigator.onPageSelected(0);
                mVpActivityRanking.setCurrentItem(0);
                commonNavigator.notifyDataSetChanged();
                break;
            case 2:
                commonNavigator.onPageSelected(1);
                mVpActivityRanking.setCurrentItem(1);
                commonNavigator.notifyDataSetChanged();
                break;
            case 3:
                commonNavigator.onPageSelected(2);
                mVpActivityRanking.setCurrentItem(2);
                commonNavigator.notifyDataSetChanged();
                break;
        }

    }

    @Override
    public int getRootView() {
        return R.layout.activity_ranking;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_activity_ranking_order:
                if (mImgActivityRankingOrder.isSelected()) {
                    mImgActivityRankingOrder.setSelected(false);
                }else{
                    mImgActivityRankingOrder.setSelected(true);
                }
                mPersonTodayDistanceRankFragment.changeOrder();
                mPersonCompleteRankingFragment.changeOrder();;
                mPersonTotalDistanceRankingFragment.changeOrder();
                break;
        }
    }

    public static final String THUMB_ACTION="com.tourye.run";
    public class ThumbClickReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(THUMB_ACTION)) {
                int type = intent.getIntExtra("type",998);
                boolean isThumb = intent.getBooleanExtra("isThumb",false);
                int thumbCount = intent.getIntExtra("thumbCount",998);
                int userId = intent.getIntExtra("userId",998);

                if (mPersonTodayDistanceRankFragment==null || mPersonCompleteRankingFragment==null || mPersonTotalDistanceRankingFragment==null) {
                    return;
                }
                mPersonTodayDistanceRankFragment.updateThumbStatus(userId,isThumb,thumbCount);
                mPersonCompleteRankingFragment.updateThumbStatus(userId,isThumb,thumbCount);
                mPersonTotalDistanceRankingFragment.updateThumbStatus(userId,isThumb,thumbCount);

            }
        }
    }
}
