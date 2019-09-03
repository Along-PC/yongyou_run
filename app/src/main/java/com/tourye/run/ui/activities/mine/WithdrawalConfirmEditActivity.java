package com.tourye.run.ui.activities.mine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;

import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.ui.adapter.CommonFragmentAdapter;
import com.tourye.run.ui.fragments.WithdrawalBankAccountFragment;
import com.tourye.run.ui.fragments.WithdrawalBankEditFragment;
import com.tourye.run.ui.fragments.WithdrawalZfbAccountFragment;
import com.tourye.run.ui.fragments.WithdrawalZfbEditFragment;
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
 * @ClassName:   WithdrawalConfirmEditActivity
 *
 * @Author:   along
 *
 * @Description:    提现确认填写账户页面
 *
 * @CreateDate:   2019/4/26 9:46 AM
 *
 */
public class WithdrawalConfirmEditActivity extends BaseActivity {

    private MagicIndicator mIndicatorActivityWithdrawalConfirmEdit;
    private ViewPager mVpActivityWithdrawalConfirmEdit;
    private OnCloseReceiver mOnCloseReceiver;

    public static String CLOSE_ACTION="com.tourye.withdrawal_comfirm_edit";
    private IntentFilter mIntentFilter;

    @Override
    public void initView() {
        mIndicatorActivityWithdrawalConfirmEdit = (MagicIndicator) findViewById(R.id.indicator_activity_withdrawal_confirm_edit);
        mVpActivityWithdrawalConfirmEdit = (ViewPager) findViewById(R.id.vp_activity_withdrawal_confirm_edit);

        mTvTitle.setText("提现确认");

    }

    @Override
    public void initData() {

        initIndicator();

        initVp();

        mOnCloseReceiver = new OnCloseReceiver();
        mIntentFilter = new IntentFilter(CLOSE_ACTION);
        registerReceiver(mOnCloseReceiver,mIntentFilter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mOnCloseReceiver);
    }

    private void initVp() {
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 998);
        List<Fragment> fragments=new ArrayList<>();
        WithdrawalZfbEditFragment withdrawalZfbEditFragment = new WithdrawalZfbEditFragment();
        WithdrawalBankEditFragment withdrawalBankEditFragment = new WithdrawalBankEditFragment();

//        fragments.add(withdrawalZfbEditFragment);
        fragments.add(withdrawalBankEditFragment);

        CommonFragmentAdapter commonFragmentAdapter = new CommonFragmentAdapter(getSupportFragmentManager());
        commonFragmentAdapter.setFragments(fragments);
        mVpActivityWithdrawalConfirmEdit.setAdapter(commonFragmentAdapter);
        if (type==1) {
            mVpActivityWithdrawalConfirmEdit.setCurrentItem(0);
        }else if(type==2){
            mVpActivityWithdrawalConfirmEdit.setCurrentItem(1);
        }
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
                        mVpActivityWithdrawalConfirmEdit.setCurrentItem(index);
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
        mIndicatorActivityWithdrawalConfirmEdit.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicatorActivityWithdrawalConfirmEdit, mVpActivityWithdrawalConfirmEdit);

    }



    @Override
    public int getRootView() {
        return R.layout.activity_withdrawal_confirm_edit;
    }


    public class OnCloseReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }

}
