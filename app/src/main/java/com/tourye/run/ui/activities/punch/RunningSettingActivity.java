package com.tourye.run.ui.activities.punch;

import android.widget.CompoundButton;
import android.widget.Switch;

import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.utils.SaveUtil;

/**
 * RunningSettingActivity
 * author:along
 * 2019/3/12 6:00 PM
 *
 * 描述:跑步设置页面
 */

public class RunningSettingActivity extends BaseActivity {

    private Switch mSwitchActivityRunningSettingLight;
    private Switch mSwitchActivityRunningSettingLock;
    private Switch mSwitchActivityRunningSettingBroadcast;

    @Override
    public void initView() {
        mSwitchActivityRunningSettingLight = (Switch) findViewById(R.id.switch_activity_running_setting_light);
        mSwitchActivityRunningSettingLock = (Switch) findViewById(R.id.switch_activity_running_setting_lock);
        mSwitchActivityRunningSettingBroadcast = (Switch) findViewById(R.id.switch_activity_running_setting_broadcast);

        mTvTitle.setText("跑步设置");

        mSwitchActivityRunningSettingLight.setChecked(SaveUtil.getBoolean(SaveConstants.IS_LONG_LIGHT,true));
        mSwitchActivityRunningSettingLock.setChecked(SaveUtil.getBoolean(SaveConstants.IS_DEFAULT_LOCK,true));
        mSwitchActivityRunningSettingBroadcast.setChecked(SaveUtil.getBoolean(SaveConstants.IS_VOICE_BROADCAST,true));

        mSwitchActivityRunningSettingLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SaveUtil.putBoolean(SaveConstants.IS_LONG_LIGHT,true);
                }else{
                    SaveUtil.putBoolean(SaveConstants.IS_LONG_LIGHT,false);
                }
            }
        });
        mSwitchActivityRunningSettingLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SaveUtil.putBoolean(SaveConstants.IS_DEFAULT_LOCK,true);
                }else{
                    SaveUtil.putBoolean(SaveConstants.IS_DEFAULT_LOCK,false);
                }
            }
        });
        mSwitchActivityRunningSettingBroadcast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SaveUtil.putBoolean(SaveConstants.IS_VOICE_BROADCAST,true);
                }else{
                    SaveUtil.putBoolean(SaveConstants.IS_VOICE_BROADCAST,false);
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public int getRootView() {
        return R.layout.activity_running_setting;
    }

}
