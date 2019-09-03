package com.tourye.run.ui.activities.home;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;

import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.ui.adapter.CommonFragmentAdapter;
import com.tourye.run.ui.fragments.BattleCompleteRankingFragment;
import com.tourye.run.ui.fragments.BattlePopularRankingFragment;
import com.tourye.run.ui.fragments.PersonInviteRankingFragment;
import com.tourye.run.utils.DensityUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  
 * @ClassName:   BattleRankingActivity
 *
 * @Author:   along
 * 
 * @Description:    战队排行榜页面
 * 
 * @CreateDate:   2019/4/1 9:37 AM
 * 
 */
public class BattleRankingActivity extends BaseActivity {

    private MagicIndicator mIndicatorActivityBattleRanking;
    private ViewPager mVpActivityBattleRanking;
    List<Fragment> mFragments=new ArrayList<>();

    @Override
    public void initView() {
        mIndicatorActivityBattleRanking = (MagicIndicator) findViewById(R.id.indicator_activity_battle_ranking);
        mVpActivityBattleRanking = (ViewPager) findViewById(R.id.vp_activity_battle_ranking);

        mTvTitle.setText("排行榜");

        initIndicator();
        initVp();
    }

    /**
     * 初始化viewpager
     */
    private void initVp() {
        mFragments.add(new BattleCompleteRankingFragment());
        mFragments.add(new BattlePopularRankingFragment());
        mFragments.add(new PersonInviteRankingFragment());
        CommonFragmentAdapter commonFragmentAdapter = new CommonFragmentAdapter(getSupportFragmentManager());
        commonFragmentAdapter.setFragments(mFragments);
        mVpActivityBattleRanking.setAdapter(commonFragmentAdapter);
        mVpActivityBattleRanking.setOffscreenPageLimit(2);
    }

    /**
     * 初始化导航栏
     */
    private void initIndicator() {
        final List<String> titles=Arrays.asList("战队完赛","人气点赞","邀请报名");
        final CommonNavigator commonNavigator = new CommonNavigator(mActivity);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles == null ? 0 : titles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                final ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#FF333333"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#FFF60E18"));
                colorTransitionPagerTitleView.setText(titles.get(index));
                colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        commonNavigator.onPageSelected(index);
                        mVpActivityBattleRanking.setCurrentItem(index);
                        notifyDataSetChanged();

                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#FFF60E18"));
                indicator.setLineWidth(DensityUtils.dp2px(30));
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                return indicator;
            }
        });
        mIndicatorActivityBattleRanking.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicatorActivityBattleRanking, mVpActivityBattleRanking);
    }

    @Override
    public void initData() {

    }

    @Override
    public int getRootView() {
        return R.layout.activity_battle_ranking;
    }
}
