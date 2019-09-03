package com.tourye.run.ui.activities.mine;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.CommonJsonBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.common.CommonWebActivity;
import com.tourye.run.utils.SaveUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: IdentityVerifiedActivity
 * @Author: along
 * @Description: 实名认证页面
 * @CreateDate: 2019/4/25 11:14 AM
 */
public class IdentityVerifiedActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEdtActivityIdentityVerifiedName;
    private EditText mEdtActivityIdentityVerifiedCode;
    private EditText mEdtActivityIdentityVerifiedPhone;
    private EditText mEdtActivityIdentityVerifiedCheckCode;
    private TextView mTvActivityIdentityVerifiedCheckCode;
    private TextView mTvActivityIdentityVerifiedConfirm;
    private TextView mTvActivityIdentityVerifiedHelp;

    private String mVerify_token;//校验token

    private int countdown=60;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10086:
                    countdown--;
                    if (countdown<=0) {
                        mTvActivityIdentityVerifiedCheckCode.setText("获取验证码");
                    }else{
                        mTvActivityIdentityVerifiedCheckCode.setText("剩余"+countdown+"秒");
                        mHandler.sendEmptyMessageDelayed(10086,1000);
                    }
                    break;
            }
        }
    };

    @Override
    public void initView() {
        mEdtActivityIdentityVerifiedName = (EditText) findViewById(R.id.edt_activity_identity_verified_name);
        mEdtActivityIdentityVerifiedCode = (EditText) findViewById(R.id.edt_activity_identity_verified_code);
        mEdtActivityIdentityVerifiedPhone = (EditText) findViewById(R.id.edt_activity_identity_verified_phone);
        mEdtActivityIdentityVerifiedCheckCode = (EditText) findViewById(R.id.edt_activity_identity_verified_check_code);
        mTvActivityIdentityVerifiedCheckCode = (TextView) findViewById(R.id.tv_activity_identity_verified_check_code);
        mTvActivityIdentityVerifiedConfirm = (TextView) findViewById(R.id.tv_activity_identity_verified_confirm);
        mTvActivityIdentityVerifiedHelp = (TextView) findViewById(R.id.tv_activity_identity_verified_help);

        mTvActivityIdentityVerifiedConfirm.setOnClickListener(this);
        mTvActivityIdentityVerifiedCheckCode.setOnClickListener(this);
        mTvActivityIdentityVerifiedHelp.setOnClickListener(this);

        mTvTitle.setText("实名认证");

    }

    @Override
    public void initData() {

    }

    /**
     * 获取验证码
     * @param name
     * @param id_card
     * @param mobile
     */
    public void initVerify(String name,String id_card,String mobile){
        Map<String,String> map=new HashMap<>();
        map.put("name",name);
        map.put("id_card",id_card);
        map.put("mobile",mobile);
        HttpUtils.getInstance().post(Constants.INIT_REAL_NAME_VERIFY, map, new HttpCallback<CommonJsonBean>() {
            @Override
            public void onSuccessExecute(CommonJsonBean commonJsonBean) {
                if (commonJsonBean.getStatus()==0) {
                    mTvActivityIdentityVerifiedCheckCode.setText("剩余"+countdown+"秒");
                    mTvActivityIdentityVerifiedCheckCode.setOnClickListener(null);
                    mHandler.sendEmptyMessageDelayed(10086,1000);
                    mVerify_token = commonJsonBean.getData();
                }else{
                    Toast.makeText(IdentityVerifiedActivity.this, "信息有误，请检查并重新提交", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 校验实名认证
     * @param code
     */
    public void checkVerify(String code){
        Map<String,String> map=new HashMap<>();
        if (TextUtils.isEmpty(mVerify_token)) {
            return;
        }
        map.put("token",mVerify_token);
        map.put("code",code);
        HttpUtils.getInstance().post(Constants.CHECK_REAL_NAME_VERIFY, map, new HttpCallback<CommonJsonBean>() {
            @Override
            public void onSuccessExecute(CommonJsonBean commonJsonBean) {
                if (commonJsonBean.getStatus()==0) {
                    String is_success = commonJsonBean.getData();
                    if ("true".equalsIgnoreCase(is_success)) {
                        startActivity(new Intent(mActivity,PrizeSigningActivity.class));
                    }else{
                        Toast.makeText(IdentityVerifiedActivity.this, "校验失败", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(IdentityVerifiedActivity.this, "校验失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    public int getRootView() {
        return R.layout.activity_identity_verified;
    }

    @Override
    public void onClick(View v) {
        String name = mEdtActivityIdentityVerifiedName.getText().toString();
        String id_card = mEdtActivityIdentityVerifiedCode.getText().toString();
        String phone = mEdtActivityIdentityVerifiedPhone.getText().toString();
        String verify_code = mEdtActivityIdentityVerifiedCheckCode.getText().toString();
        switch (v.getId()) {
            case R.id.tv_activity_identity_verified_confirm:
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(id_card) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(verify_code)) {
                    checkVerify(verify_code);
                }else{
                    Toast.makeText(mActivity, "请填写全部信息", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_activity_identity_verified_check_code:
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(id_card) && !TextUtils.isEmpty(phone) ) {
                    initVerify(name,id_card,phone);
                }else{
                    Toast.makeText(mActivity, "请填写全部信息", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_activity_identity_verified_help:
                Intent questionIntent = new Intent(mActivity, CommonWebActivity.class);
                String url = Constants.DOMAIN+"?env=gio&jwt="
                        + SaveUtil.getString("Authorization", "")
                        + "#/feedback";
                questionIntent.putExtra("url", url);
                questionIntent.putExtra("title","问题反馈");
                startActivity(questionIntent);
                break;
        }
    }

}
