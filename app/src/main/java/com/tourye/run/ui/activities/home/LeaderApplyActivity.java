package com.tourye.run.ui.activities.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.CommonBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.utils.SaveUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: LeaderApplyActivity
 * @Author: along
 * @Description: 队长申请页面
 * @CreateDate: 2019/3/26 10:18 AM
 */
public class LeaderApplyActivity extends BaseActivity {

    private EditText mEdtActivityLeaderApplyMemberCount;
    private EditText mEdtActivityLeaderApplyReferrerPhone;
    private Button mBtActivityLeaderApplySubmit;

    @Override
    public void initView() {
        mEdtActivityLeaderApplyMemberCount = (EditText) findViewById(R.id.edt_activity_leader_apply_member_count);
        mEdtActivityLeaderApplyReferrerPhone = (EditText) findViewById(R.id.edt_activity_leader_apply_referrer_phone);
        mBtActivityLeaderApplySubmit = (Button) findViewById(R.id.bt_activity_leader_apply_submit);

        mTvTitle.setText("信息提交");
        mBtActivityLeaderApplySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memberCount = mEdtActivityLeaderApplyMemberCount.getText().toString();
                if (TextUtils.isEmpty(memberCount)) {
                    Toast.makeText(mActivity, "请输入预计战队成员数", Toast.LENGTH_SHORT).show();
                    return;
                }
                apply_leader(memberCount);
            }
        });
    }

    /**
     * 申请成为队长
     * @param member_count
     */
    public void apply_leader(String member_count){
        Map<String, String> map = new HashMap<>();
        map.put("activity_id",SaveUtil.getString(SaveConstants.ACTION_ID,""));
        map.put("predicted_member_count",member_count);
//        map.put("referrer","");
        String referrerPhone = mEdtActivityLeaderApplyReferrerPhone.getText().toString();
        if (!TextUtils.isEmpty(referrerPhone)) {
            map.put("referrer_mobile",referrerPhone);
        }
        HttpUtils.getInstance().post(Constants.LEADER_APPLY, map, new HttpCallback<CommonBean>() {
            @Override
            public void onSuccessExecute(CommonBean commonBean) {
                if (commonBean.getStatus()==0) {
                    Toast.makeText(mActivity, "已经提交申请", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(mActivity, commonBean.getMessage()+"", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public int getRootView() {
        return R.layout.activity_leader_apply;
    }

}
