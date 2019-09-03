package com.tourye.run.ui.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.tourye.run.BuildConfig;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.CommonJsonBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.utils.NetStateUtils;
import com.tourye.run.utils.NoneNetUtils;
import com.tourye.run.utils.SaveUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * SplashActivity
 * author:along
 * 2018/9/18 下午2:02
 *
 * 描述:启动页
 */

public class SplashActivity extends BaseActivity {

    private Activity mActivity;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10086:
                    String authorization = SaveUtil.getString(SaveConstants.AUTHORIZATION, "");
                    if (!TextUtils.isEmpty(authorization)) {
                        int netState = NetStateUtils.getNetState(mActivity);
                        if (netState==NetStateUtils.NETWORK_STATE_NONE) {
                            Intent intent = new Intent(mActivity, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            refreshToken();
                        }
                    }else{
                        startActivity(new Intent(mActivity,LoginActivity.class));
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    public void initView() {

    }

    /**
     * 刷新用户token
     */
    public void refreshToken(){
        Map<String,String> map=new HashMap<>();
        HttpUtils.getInstance().post(Constants.REFRESH_TOKEN, map, new HttpCallback<CommonJsonBean>() {
            @Override
            public void onSuccessExecute(CommonJsonBean commonJsonBean) {
                if (commonJsonBean.getStatus()==0) {
                    SaveUtil.putString(SaveConstants.AUTHORIZATION,commonJsonBean.getData());
                    Intent intent = new Intent(mActivity, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void initData() {
        mActivity=this;
        mHandler.sendEmptyMessageDelayed(10086,100);
    }

    @Override
    public boolean isNeedTitle() {
        return false;
    }

    @Override
    protected boolean isNeedCheckNet() {
        return false;
    }

    @Override
    public int getRootView() {
        return R.layout.activity_splash;
    }

}
