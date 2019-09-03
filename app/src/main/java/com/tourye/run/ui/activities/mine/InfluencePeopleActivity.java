package com.tourye.run.ui.activities.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.base.BaseFragment;
import com.tourye.run.ui.adapter.CommonFragmentAdapter;
import com.tourye.run.ui.fragments.InfluencePeoplefragment;
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
 * @ClassName: InfluencePeopleActivity
 * @Author: along
 * @Description: 影响人数
 * @CreateDate: 2019/4/19 5:24 PM
 */
public class InfluencePeopleActivity extends BaseActivity {
    private MagicIndicator mIndicatorActivityInfluencePeople;
    private ViewPager mVpActivityInfluencePeople;

    @Override
    public void initView() {
        mIndicatorActivityInfluencePeople = (MagicIndicator) findViewById(R.id.indicator_activity_influence_people);
        mVpActivityInfluencePeople = (ViewPager) findViewById(R.id.vp_activity_influence_people);

        mTvTitle.setText("影响人数");
    }

    @Override
    public void initData() {
        initIndicator();

        initVp();
    }

    private void initVp() {
        Intent intent = getIntent();
        String team_id = intent.getStringExtra("team_id");
        List<Fragment> fragments = new ArrayList<>();
        //已报名
        Bundle joinedBundle = new Bundle();
        joinedBundle.putString("team_id", team_id);
        joinedBundle.putInt("is_joined", 1);
        InfluencePeoplefragment influencePeopleJoined = new InfluencePeoplefragment();
        influencePeopleJoined.setArguments(joinedBundle);
        //未报名
        Bundle unJoinedBundle = new Bundle();
        unJoinedBundle.putString("team_id", team_id);
        unJoinedBundle.putInt("is_joined", 0);
        InfluencePeoplefragment influencePeopleUnJoined = new InfluencePeoplefragment();
        influencePeopleUnJoined.setArguments(unJoinedBundle);

        fragments.add(influencePeopleJoined);
        fragments.add(influencePeopleUnJoined);
        CommonFragmentAdapter commonFragmentAdapter = new CommonFragmentAdapter(getSupportFragmentManager());
        commonFragmentAdapter.setFragments(fragments);
        mVpActivityInfluencePeople.setAdapter(commonFragmentAdapter);

    }

    private void initIndicator() {
        final List<String> titles = Arrays.asList("已报名", "未报名");
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
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#FF999999"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#FFFF1D1D"));
                colorTransitionPagerTitleView.setText(titles.get(index));
                colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        commonNavigator.onPageSelected(index);
                        mVpActivityInfluencePeople.setCurrentItem(index);
                        notifyDataSetChanged();
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#FFFF1D1D"));
                indicator.setLineWidth(DensityUtils.dp2px(30));
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                return indicator;
            }
        });
        mIndicatorActivityInfluencePeople.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicatorActivityInfluencePeople, mVpActivityInfluencePeople);

    }

    @Override
    public int getRootView() {
        return R.layout.activity_influence_people;
    }
}
