package com.tourye.run.ui.activities.mine;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;

import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.ui.adapter.CommonFragmentAdapter;
import com.tourye.run.ui.dialogs.CloudHelperDialog;
import com.tourye.run.ui.fragments.WithdrawalBankAccountFragment;
import com.tourye.run.ui.fragments.WithdrawalZfbAccountFragment;
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
 * @ClassName:   WithdrawalConfirmActivity
 *
 * @Author:   along
 *
 * @Description:    提现确认页面
 *
 * @CreateDate:   2019/4/25 2:41 PM
 *
 */
public class WithdrawalConfirmActivity extends BaseActivity {
    private MagicIndicator mIndicatorActivityWithdrawalConfirm;
    private ViewPager mVpActivityWithdrawalConfirm;


    @Override
    public void initView() {
        mIndicatorActivityWithdrawalConfirm = (MagicIndicator) findViewById(R.id.indicator_activity_withdrawal_confirm);
        mVpActivityWithdrawalConfirm = (ViewPager) findViewById(R.id.vp_activity_withdrawal_confirm);

        mTvTitle.setText("提现确认");

    }

    @Override
    public void initData() {

        CloudHelperDialog cloudHelperDialog = new CloudHelperDialog(mActivity);
        cloudHelperDialog.show();

        initIndicator();

        initVp();

    }

    private void initVp() {
        List<Fragment> fragments=new ArrayList<>();
        WithdrawalZfbAccountFragment withdrawalZfbAccountFragment = new WithdrawalZfbAccountFragment();
        WithdrawalBankAccountFragment withdrawalBankAccountFragment = new WithdrawalBankAccountFragment();
//        fragments.add(withdrawalZfbAccountFragment);
        fragments.add(withdrawalBankAccountFragment);

        CommonFragmentAdapter commonFragmentAdapter = new CommonFragmentAdapter(getSupportFragmentManager());
        commonFragmentAdapter.setFragments(fragments);
        mVpActivityWithdrawalConfirm.setAdapter(commonFragmentAdapter);
    }

    private void initIndicator() {
        final List<String> titles = Arrays.asList("支付宝", "银行卡");
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
                colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        commonNavigator.onPageSelected(index);
                        mVpActivityWithdrawalConfirm.setCurrentItem(index);
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
        mIndicatorActivityWithdrawalConfirm.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicatorActivityWithdrawalConfirm, mVpActivityWithdrawalConfirm);

    }

    @Override
    public int getRootView() {
        return R.layout.activity_withdrawal_confirm;
    }
}
