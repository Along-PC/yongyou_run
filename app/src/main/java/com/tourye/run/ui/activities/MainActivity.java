package com.tourye.run.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.bugly.crashreport.CrashReport;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.ui.adapter.CommonFragmentAdapter;
import com.tourye.run.ui.dialogs.CheckSigningDialog;
import com.tourye.run.ui.dialogs.LoadingDialog;
import com.tourye.run.ui.dialogs.RejectReasonDialog;
import com.tourye.run.ui.dialogs.UserProtocolDialog;
import com.tourye.run.ui.fragments.FindFragment;
import com.tourye.run.ui.fragments.MineFragment;
import com.tourye.run.ui.fragments.PunchFragment;
import com.tourye.run.ui.fragments.SignupFragment;
import com.tourye.run.utils.DateFormatUtils;
import com.tourye.run.utils.LocationUtils;
import com.tourye.run.utils.RecordUtils;
import com.tourye.run.utils.SaveUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager mVpActivityMain;
    private LinearLayout mLlActivityMainSignup;//报名
    private ImageView mImgActivityMainSignup;
    private TextView mTvActivityMainSignup;
    private LinearLayout mLlActivityMainPunch;//打卡
    private ImageView mImgActivityMainPunch;
    private TextView mTvActivityMainPunch;
    private LinearLayout mLlActivityMainFind;//社区
    private ImageView mImgActivityMainFind;
    private TextView mTvActivityMainFind;
    private LinearLayout mLlActivityMainMine;//个人中心
    private ImageView mImgActivityMainMine;
    private TextView mTvActivityMainMine;

    private TextView mCurrentText;//当前选中textview
    private ImageView mCurrentImage;//当前选中imageview

    private List<Fragment> mFragments = new ArrayList<>();

    private long lastClickTime;//上次点击退出时间

    @Override
    public void initView() {
        mVpActivityMain = findViewById(R.id.vp_activity_main);
        mLlActivityMainSignup = findViewById(R.id.ll_activity_main_signup);
        mImgActivityMainSignup = findViewById(R.id.img_activity_main_signup);
        mTvActivityMainSignup = findViewById(R.id.tv_activity_main_signup);
        mLlActivityMainPunch = findViewById(R.id.ll_activity_main_punch);
        mImgActivityMainPunch = findViewById(R.id.img_activity_main_punch);
        mTvActivityMainPunch = findViewById(R.id.tv_activity_main_punch);
        mLlActivityMainFind = findViewById(R.id.ll_activity_main_find);
        mImgActivityMainFind = findViewById(R.id.img_activity_main_find);
        mTvActivityMainFind = findViewById(R.id.tv_activity_main_find);
        mLlActivityMainMine = findViewById(R.id.ll_activity_main_mine);
        mImgActivityMainMine = findViewById(R.id.img_activity_main_mine);
        mTvActivityMainMine = findViewById(R.id.tv_activity_main_mine);

        setListener();

        initVp();
        
    }
    /**
     * 设置点击监听
     */
    private void setListener() {
        mLlActivityMainSignup.setOnClickListener(this);
        mLlActivityMainPunch.setOnClickListener(this);
        mLlActivityMainFind.setOnClickListener(this);
        mLlActivityMainMine.setOnClickListener(this);
    }

    /**
     * 初始化viewpager
     */
    private void initVp() {
        SignupFragment signupFragment = new SignupFragment();
        PunchFragment punchFragment = new PunchFragment();
        FindFragment findFragment = new FindFragment();
        MineFragment mineFragment = new MineFragment();
        mFragments.add(signupFragment);
        mFragments.add(punchFragment);
        mFragments.add(findFragment);
        mFragments.add(mineFragment);
        CommonFragmentAdapter commonFragmentAdapter = new CommonFragmentAdapter(getSupportFragmentManager());
        commonFragmentAdapter.setFragments(mFragments);
        mVpActivityMain.setAdapter(commonFragmentAdapter);
        mVpActivityMain.setOffscreenPageLimit(4);
        mVpActivityMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                chooseNavi(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void initData() {
        //测试bugly
//        CrashReport.testJavaCrash();

        if (!SaveUtil.getBoolean(SaveConstants.IS_SHOW_USER_PROTOCOL,false)) {
            final UserProtocolDialog userProtocolDialog = new UserProtocolDialog(mActivity);
            userProtocolDialog.setOnOperateListener(new UserProtocolDialog.OnOperateListener() {
                @Override
                public void onDisagree() {
                    userProtocolDialog.dismiss();
                    finish();
                }

                @Override
                public void onAgraee() {
                    SaveUtil.putBoolean(SaveConstants.IS_SHOW_USER_PROTOCOL,true);
                    userProtocolDialog.dismiss();
                }
            });
            userProtocolDialog.show();
        }

        //设置默认导航
        mCurrentImage = mImgActivityMainSignup;
        mCurrentText = mTvActivityMainSignup;
        chooseNavi(0);

        //todo
        SaveUtil.putString(SaveConstants.ACTION_ID, "1");

        //注册event-bus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    //切换当前选中页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateIndex(FindFragment.UpdateStatusBean updateStatusBean){
        int type = updateStatusBean.getType();
        if (type==1) {
            mVpActivityMain.setCurrentItem(2);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 选中底部导航栏
     *
     * @param index 导航栏索引
     */
    public void chooseNavi(int index) {

        mCurrentText.setSelected(false);
        mCurrentImage.setSelected(false);
        switch (index) {
            case 0:
                mCurrentText = mTvActivityMainSignup;
                mCurrentImage = mImgActivityMainSignup;
                break;
            case 1:
                mCurrentText = mTvActivityMainPunch;
                mCurrentImage = mImgActivityMainPunch;
                break;
            case 2:
                mCurrentText = mTvActivityMainFind;
                mCurrentImage = mImgActivityMainFind;
                break;
            case 3:
                mCurrentText = mTvActivityMainMine;
                mCurrentImage = mImgActivityMainMine;
                break;
            default:
                break;
        }
        mCurrentText.setSelected(true);
        mCurrentImage.setSelected(true);
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime-lastClickTime<1000) {
            finish();
        }else {
            Toast toast = Toast.makeText(mActivity, "再按一次退出程序", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
        lastClickTime=currentTime;
    }

    @Override
    public int getRootView() {
        return R.layout.activity_main;
    }

    @Override
    public boolean isNeedTitle() {
        return false;
    }

    @Override
    public boolean isNeedUpdateTypefaceSize() {
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int type = intent.getIntExtra("type", 998);
        if (type==1) {
            //打卡完成后发送动态跳转
            mVpActivityMain.setCurrentItem(2);
            FindFragment.UpdateStatusBean updateStatusBean = new FindFragment.UpdateStatusBean();
            updateStatusBean.setType(0);
            EventBus.getDefault().post(updateStatusBean);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_activity_main_signup:
                mVpActivityMain.setCurrentItem(0, false);
                chooseNavi(0);
                break;
            case R.id.ll_activity_main_punch:
                mVpActivityMain.setCurrentItem(1, false);
                chooseNavi(1);
                break;
            case R.id.ll_activity_main_find:
                mVpActivityMain.setCurrentItem(2, false);
                chooseNavi(2);
                break;
            case R.id.ll_activity_main_mine:
                mVpActivityMain.setCurrentItem(3, false);
                chooseNavi(3);
                break;
        }
    }

}
