package com.tourye.run.ui.activities.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.CheckContractStatusBean;
import com.tourye.run.bean.CommonJsonBean;
import com.tourye.run.bean.ContractSignStatusBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.CommonFragmentAdapter;
import com.tourye.run.ui.dialogs.CheckSigningDialog;
import com.tourye.run.ui.fragments.AlreadyEffectiveFragment;
import com.tourye.run.ui.fragments.AlreadyWithdrawalFragment;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.SaveUtil;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:   BonusWithdrawalActivity
 *
 * @Author:   along
 *
 * @Description:    奖金提现页面
 *
 * @CreateDate:   2019/4/23 2:42 PM
 *
 */
public class BonusWithdrawalActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvActivityBonusWithdrawalMoney;
    private TextView mTvActivityBonusWithdrawalGet;
    private MagicIndicator mIndicatorActivityBonusWithdrawal;
    private ViewPager mVpActivityBonusWithdrawal;
    private boolean mIs_need_signing;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10086:
                    checkSignStatus();
                    break;
            }
        }
    };
    private float mAccount;
    private CheckSigningDialog mCheckSigningDialog;

    @Override
    public void initView() {
        mTvActivityBonusWithdrawalMoney = (TextView) findViewById(R.id.tv_activity_bonus_withdrawal_money);
        mTvActivityBonusWithdrawalGet = (TextView) findViewById(R.id.tv_activity_bonus_withdrawal_get);
        mIndicatorActivityBonusWithdrawal = (MagicIndicator) findViewById(R.id.indicator_activity_bonus_withdrawal);
        mVpActivityBonusWithdrawal = (ViewPager) findViewById(R.id.vp_activity_bonus_withdrawal);

        mTvTitle.setText("奖金提现");



    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mIs_need_signing = intent.getBooleanExtra("is_need_signing", false);
        if (mIs_need_signing) {
            mCheckSigningDialog = new CheckSigningDialog(mActivity);
            mCheckSigningDialog.setOnCancelCliskListener(new CheckSigningDialog.OnCancelCliskListener() {
                @Override
                public void onCancel() {
                    finish();
                }
            });
            mCheckSigningDialog.show();
        }

        initVp();
        getPrizeAccount();
        initIndicator();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initVp() {
        List<Fragment> fragments=new ArrayList<>();
        AlreadyWithdrawalFragment alreadyWithdrawalFragment = new AlreadyWithdrawalFragment();
        AlreadyEffectiveFragment alreadyEffectiveFragment = new AlreadyEffectiveFragment();
        fragments.add(alreadyWithdrawalFragment);
        fragments.add(alreadyEffectiveFragment);

        CommonFragmentAdapter commonFragmentAdapter = new CommonFragmentAdapter(getSupportFragmentManager());
        commonFragmentAdapter.setFragments(fragments);
        mVpActivityBonusWithdrawal.setAdapter(commonFragmentAdapter);

    }

    /**
     * 获取奖金余额
     */
    public void getPrizeAccount(){
        Map<String,String> map=new HashMap<>();
        HttpUtils.getInstance().get(Constants.PRIZE_ACCOUNT, map, new HttpCallback<CommonJsonBean>() {
            @Override
            public void onSuccessExecute(CommonJsonBean commonJsonBean) {
                String prize_account = commonJsonBean.getData();
                if (!TextUtils.isEmpty(prize_account)) {
                    int temp = Integer.parseInt(prize_account);
                    if (temp==0) {
                        mTvActivityBonusWithdrawalGet.setSelected(true);
                    }
                    if (temp>0) {
                        mTvActivityBonusWithdrawalGet.setOnClickListener(BonusWithdrawalActivity.this);
                    }
                    mAccount = temp / 100f;
                    if (mIs_need_signing) {
                        String str= "￥"+ mAccount;
                        for (int i = 0; i < str.length(); i++) {
                            str=str.replace(str.charAt(i),'*');
                        }
                        mTvActivityBonusWithdrawalMoney.setText(str);
                    }else{
                        if (mAccount>0) {
                            mTvActivityBonusWithdrawalMoney.setText("￥"+ mAccount);
                        }else{
                            mTvActivityBonusWithdrawalMoney.setText("￥ 0");
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("along", "onRestart() called");
    }

    private void initIndicator() {
        final List<String> titles = Arrays.asList("已提现", "已生效");
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
                        mVpActivityBonusWithdrawal.setCurrentItem(index);
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
        mIndicatorActivityBonusWithdrawal.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicatorActivityBonusWithdrawal, mVpActivityBonusWithdrawal);

    }

    public void checkSignStatus(){
        Map<String,String> map=new HashMap<>();
        map.put("id",SaveUtil.getString(SaveConstants.ACTION_ID,""));
        HttpUtils.getInstance().get(Constants.CONTRACT_SIGN_STATUS, map, new HttpCallback<ContractSignStatusBean>() {
            @Override
            public void onSuccessExecute(ContractSignStatusBean contractSignStatusBean) {
                ContractSignStatusBean.DataBean data = contractSignStatusBean.getData();
                if (data==null) {
                    return;
                }
                if (data.isSigned()==1) {
                    mIs_need_signing=false;
                    getPrizeAccount();
                    mCheckSigningDialog.dismiss();
                }else{
                    mHandler.sendEmptyMessageDelayed(10086,1000);
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkSignStatus();
    }

    @Override
    public int getRootView() {
        return R.layout.activity_bonus_withdrawal;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_activity_bonus_withdrawal_get:
                startActivity(new Intent(mActivity,WithdrawalConfirmActivity.class));
                break;
        }
    }
}
