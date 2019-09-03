package com.tourye.run.ui.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.LoginBean;
import com.tourye.run.bean.VerifyCodeBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.mine.EditInformationActivity;
import com.tourye.run.utils.AreaUtils;
import com.tourye.run.utils.SaveUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:   LoginActivity
 *
 * @Author:   along
 *
 * @Description:登录页面
 *
 * @CreateDate:   2019/3/14 11:53 AM
 *
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEdtActivityLoginPhone;
    private TextView mTvActivityLoginPhone;
    private EditText mEdtActivityLoginCode;
    private TextView mTvActivityLoginGetCode;
    private Button mBtActivityLogin;
    private TextView mTvActivityLoginHelp;

    private String mArea = "86";//区域选择编码  0-大陆  1-香港  2-澳门  3-台湾
    private int verify_code_time = 60;//短信验证码下次可用时间

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    verify_code_time--;
                    if (verify_code_time > 0) {
                        mTvActivityLoginGetCode.setText("剩余"+verify_code_time + "s");
                        mHandler.sendEmptyMessageDelayed(1, 1000);
                    } else {
                        verify_code_time=60;
                        mTvActivityLoginGetCode.setText("获取动态密码");
                        mTvActivityLoginGetCode.setOnClickListener(LoginActivity.this);
                    }
                    break;
            }
        }
    };

    @Override
    public void initView() {
        mEdtActivityLoginPhone = (EditText) findViewById(R.id.edt_activity_login_phone);
        mTvActivityLoginPhone = (TextView) findViewById(R.id.tv_activity_login_phone);
        mEdtActivityLoginCode = (EditText) findViewById(R.id.edt_activity_login_code);
        mTvActivityLoginGetCode = (TextView) findViewById(R.id.tv_activity_login_getCode);
        mBtActivityLogin = (Button) findViewById(R.id.bt_activity_login);
        mTvActivityLoginHelp = (TextView) findViewById(R.id.tv_activity_login_help);

        mTvActivityLoginGetCode.setOnClickListener(this);
        mBtActivityLogin.setOnClickListener(this);
        mTvActivityLoginHelp.setOnClickListener(this);
        mTvActivityLoginPhone.setOnClickListener(this);

        bindEdt();

        List<AreaUtils.AreaBean> area = AreaUtils.getArea();
        System.out.println(Arrays.toString(area.toArray()));

    }

    private void bindEdt() {
        mEdtActivityLoginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()>=11 && !TextUtils.isEmpty(mEdtActivityLoginCode.getText().toString())) {
                    mBtActivityLogin.setAlpha(1);
                }else{
                    mBtActivityLogin.setAlpha(0.6f);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEdtActivityLoginCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = s.toString();
                if (temp.length()>=6 && !TextUtils.isEmpty(mEdtActivityLoginPhone.getText().toString())) {
                    mBtActivityLogin.setAlpha(1);
                }else{
                    mBtActivityLogin.setAlpha(0.6f);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void initData() {

    }

    /**
     * 登录
     */
    private void login() {
        String phone = mEdtActivityLoginPhone.getText().toString();
        String code = mEdtActivityLoginCode.getText().toString();
        String device_id = "";//信鸽推送token
        Map<String,String> map=new HashMap<>();
        map.put("area_code",mArea);
        map.put("phone",phone);
        map.put("code",code);
        map.put("device_id", device_id);
        map.put("type", "android");
        HttpUtils.getInstance().post(Constants.USER_LOGIN, map, new HttpCallback<LoginBean>() {
            @Override
            public void onSuccessExecute(LoginBean loginBean) {
                if (loginBean.getStatus()==0) {
                    LoginBean.DataBean data = loginBean.getData();
                    if (!TextUtils.isEmpty(data.getToken())) {
                        SaveUtil.putString(SaveConstants.AUTHORIZATION,data.getToken());
                        if (!data.isIs_new()) {
                            startActivity(new Intent(mActivity,MainActivity.class));
                            finish();
                        }else{
                            startActivity(new Intent(mActivity,EditInformationActivity.class));
                            finish();
                        }
                    }
                }else{
                    Toast.makeText(LoginActivity.this, loginBean.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 获取验证码code
     */
    private void getVerifyCode() {
        String phone = mEdtActivityLoginPhone.getText().toString();
        phone=phone.trim();
        if (phone.length()!=11 && "86".equalsIgnoreCase(mArea)) {
            Toast.makeText(mActivity, "请检查手机号码是否正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.isEmpty(phone)) {
            HashMap<String, String> map = new HashMap<>();
            map.put("phone", phone);
            map.put("area_code", mArea);
            HttpUtils.getInstance().post(Constants.GET_VERIFY_CODE, map, new HttpCallback<VerifyCodeBean>() {
                @Override
                public void onSuccessExecute(VerifyCodeBean verifyCodeBean) {
                    if (verifyCodeBean.getStatus() == 0) {
                        Toast.makeText(mActivity, "验证码发送成功", Toast.LENGTH_SHORT).show();
                        mTvActivityLoginGetCode.setText("剩余"+verify_code_time + "s");
                        mTvActivityLoginGetCode.setOnClickListener(null);
                        mHandler.sendEmptyMessageDelayed(1, 1000);
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==AreaActivity.RESULT_CODE) {
            mArea=data.getStringExtra("code");
            mTvActivityLoginPhone.setText("+"+mArea+" >");
        }
    }

    @Override
    public int getRootView() {
        return R.layout.activity_login;
    }

    @Override
    public boolean isNeedTitle() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_activity_login_getCode:
                getVerifyCode();
                break;
            case R.id.bt_activity_login:
                login();
                break;
            case R.id.tv_activity_login_help:

                break;
            case R.id.tv_activity_login_phone:
                Intent countryIntent = new Intent(mActivity, AreaActivity.class);
                startActivityForResult(countryIntent,AreaActivity.REQUEST_CODE);
                break;
        }
    }
}
