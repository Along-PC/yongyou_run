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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.ui.adapter.CommonFragmentAdapter;
import com.tourye.run.ui.fragments.PunchAllFragment;
import com.tourye.run.ui.fragments.PunchAlreadyFragment;
import com.tourye.run.ui.fragments.PunchNotFragment;

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
 *
 * @ClassName:   PunchDetailActivity
 *
 * @Author:   along
 *
 * @Description:    打卡明细页面
 *
 * @CreateDate:   2019/4/22 6:20 PM
 *
 */
public class PunchDetailActivity extends BaseActivity {
    private MagicIndicator mIndicatorActivityPunchDetail;
    private TextView mTvActivityPunchDetailOrder;
    private ImageView mImgActivityPunchDetailOrder;
    private ViewPager mVpActivityPunchDetail;
    private LinearLayout mLlActivityPunchDetailOrder;

    private View.OnClickListener mOrderOnclickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mImgActivityPunchDetailOrder.isSelected()) {
                mImgActivityPunchDetailOrder.setSelected(false);
            }else{
                mImgActivityPunchDetailOrder.setSelected(true);
            }
            mPunchAlreadyFragment.changeOrder();
        }
    };
    private PunchAlreadyFragment mPunchAlreadyFragment;

    @Override
    public void initView() {
        mIndicatorActivityPunchDetail = (MagicIndicator) findViewById(R.id.indicator_activity_punch_detail);
        mTvActivityPunchDetailOrder = (TextView) findViewById(R.id.tv_activity_punch_detail_order);
        mImgActivityPunchDetailOrder = (ImageView) findViewById(R.id.img_activity_punch_detail_order);
        mVpActivityPunchDetail = (ViewPager) findViewById(R.id.vp_activity_punch_detail);
        mLlActivityPunchDetailOrder = (LinearLayout) findViewById(R.id.ll_activity_punch_detail_order);

        mTvTitle.setText("打卡明细");
    }

    @Override
    public void initData() {

        initVp();

        initIndicator();

    }

    /**
     * 初始化导航栏
     */
    private void initIndicator() {
        final List<String> titles=new ArrayList<>();
        titles.add("全部");
        titles.add("已打卡");
        titles.add("未打卡");
        final CommonNavigator commonNavigator = new CommonNavigator(mActivity);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return titles == null ? 0 : titles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(getResources().getColor(R.color.color_font_black));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#CC1C24"));
                colorTransitionPagerTitleView.setText(titles.get(index));
                colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mVpActivityPunchDetail.setCurrentItem(index);
                        if (index==1) {
                            mLlActivityPunchDetailOrder.setVisibility(View.VISIBLE);
                            mLlActivityPunchDetailOrder.setOnClickListener(mOrderOnclickListener);
                        }else{
                            mLlActivityPunchDetailOrder.setVisibility(View.INVISIBLE);
                            mLlActivityPunchDetailOrder.setOnClickListener(null);
                        }
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
        mIndicatorActivityPunchDetail.setNavigator(commonNavigator);
        mVpActivityPunchDetail.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i==1) {
                    mLlActivityPunchDetailOrder.setVisibility(View.VISIBLE);
                    mLlActivityPunchDetailOrder.setOnClickListener(mOrderOnclickListener);
                }else{
                    mLlActivityPunchDetailOrder.setVisibility(View.INVISIBLE);
                    mLlActivityPunchDetailOrder.setOnClickListener(null);
                }
                commonNavigator.onPageSelected(i);
                commonNavigator.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    private void initVp() {
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String team_id = intent.getStringExtra("team_id");

        List<Fragment> fragments=new ArrayList<>();
        PunchAllFragment punchAllFragment = new PunchAllFragment();
        mPunchAlreadyFragment = new PunchAlreadyFragment();
        PunchNotFragment punchNotFragment = new PunchNotFragment();

        Bundle bundle = new Bundle();
        bundle.putString("date",date);
        bundle.putString("team_id",team_id);
        punchAllFragment.setArguments(bundle);
        mPunchAlreadyFragment.setArguments(bundle);
        punchNotFragment.setArguments(bundle);

        fragments.add(punchAllFragment);
        fragments.add(mPunchAlreadyFragment);
        fragments.add(punchNotFragment);

        CommonFragmentAdapter commonFragmentAdapter = new CommonFragmentAdapter(getSupportFragmentManager());
        commonFragmentAdapter.setFragments(fragments);
        mVpActivityPunchDetail.setAdapter(commonFragmentAdapter);
    }

    @Override
    public int getRootView() {
        return R.layout.activity_punch_detail;
    }
}
