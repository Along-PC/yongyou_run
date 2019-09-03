package com.tourye.run.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.tourye.run.R;
import com.tourye.run.base.BaseFragment;
import com.tourye.run.ui.activities.community.CreateDynamicActivity;
import com.tourye.run.ui.adapter.CommonFragmentAdapter;
import com.tourye.run.utils.DensityUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by longlongren on 2019/3/11.
 * <p>
 * introduce:社区页面
 */
public class FindFragment extends BaseFragment {

    private MagicIndicator mIndicatorFragmentFind;
    private ViewPager mVpFragmentFind;
    private ImageView mImgFragmentFindEdit;


    @Override
    public void initView(View view) {
        mIndicatorFragmentFind = (MagicIndicator) view.findViewById(R.id.indicator_fragment_find);
        mVpFragmentFind = (ViewPager) view.findViewById(R.id.vp_fragment_find);
        mImgFragmentFindEdit = (ImageView) view.findViewById(R.id.img_fragment_find_edit);

        mTvTitle.setText("发现");
        initVp();
        initIndicator();

        mImgFragmentFindEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity,CreateDynamicActivity.class));
            }
        });

    }

    private void initVp() {
        List<Fragment> fragments=new ArrayList<>();
        fragments.add(new HotDynamicFragment());
        fragments.add(new AllDynamicFragment());
        fragments.add(new MineDynamicFragment());
        CommonFragmentAdapter commonFragmentAdapter = new CommonFragmentAdapter(getChildFragmentManager());
        commonFragmentAdapter.setFragments(fragments);
        mVpFragmentFind.setAdapter(commonFragmentAdapter);
    }

    private void initIndicator() {
        final List<String> titles = Arrays.asList("热门动态", "所有动态", "我的动态");
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
                colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        commonNavigator.onPageSelected(index);
                        mVpFragmentFind.setCurrentItem(index);
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
        mIndicatorFragmentFind.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicatorFragmentFind, mVpFragmentFind);
        commonNavigator.onPageSelected(1);
        mVpFragmentFind.setCurrentItem(1);
        commonNavigator.notifyDataSetChanged();

    }

    @Override
    public void initData() {

        //注册event-bus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //当前选中页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateIndex(UpdateStatusBean updateStatusBean){
        int type = updateStatusBean.getType();
        if (type==1) {
            mVpFragmentFind.setCurrentItem(2);
        }else if(type==0){
            mVpFragmentFind.setCurrentItem(1);
        }
    }

    @Override
    public boolean isNeedTitle() {
        return true;
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_find;
    }

    public static class UpdateStatusBean{
        private int type;//1--跳转个人动态   0--跳转所有动态

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

}
