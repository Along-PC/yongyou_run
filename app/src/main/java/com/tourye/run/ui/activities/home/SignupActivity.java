package com.tourye.run.ui.activities.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.BattleInfoBean;
import com.tourye.run.bean.CheckZfbStatusBean;
import com.tourye.run.bean.CreateOrderBean;
import com.tourye.run.bean.PackageBean;
import com.tourye.run.bean.TeamBasicInfoBean;
import com.tourye.run.bean.ZFBOrder;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.pay.zfb.PayResult;
import com.tourye.run.ui.activities.common.CommonWebActivity;
import com.tourye.run.ui.adapter.SignupPackageAdapter;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * @ClassName: SignupActivity
 * @Author: along
 * @Description: 报名套餐页面
 * @CreateDate: 2019/3/26 4:18 PM
 */
public class SignupActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTvActivitySignupName;
    private ImageView mImgActivitySignupHead;
    private TextView mTvActivitySignupCity;
    private TextView mTvActivitySignupDistance;
    private TextView mTvActivitySignupCounts;
    private RecyclerView mRecyclerActivitySignupPackage;
    private CheckBox mCbActivitySignupProtocol;
    private TextView mTvActivitySignupPrice;
    private TextView mTvActivitySignupSubmit;
    private List<PackageBean.DataBean> packageList;//套餐数据
    private int packageIndex = 10086;
    private int mPackage_Id;//订单id
    private String mOrder_Id;
    private TextView mTvActivitySignupProtocol;
    private TextView mTvActivitySignupRule;

    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        check_zfb_pay_status();
                    } else {
                        Toast.makeText(mActivity, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
    private String mTeam_id;

    @Override
    public void initView() {
        mTvActivitySignupName = (TextView) findViewById(R.id.tv_activity_signup_name);
        mImgActivitySignupHead = (ImageView) findViewById(R.id.img_activity_signup_head);
        mTvActivitySignupCity = (TextView) findViewById(R.id.tv_activity_signup_city);
        mTvActivitySignupDistance = (TextView) findViewById(R.id.tv_activity_signup_distance);
        mTvActivitySignupCounts = (TextView) findViewById(R.id.tv_activity_signup_counts);
        mRecyclerActivitySignupPackage = (RecyclerView) findViewById(R.id.recycler_activity_signup_package);
        mTvActivitySignupPrice = (TextView) findViewById(R.id.tv_activity_signup_price);
        mTvActivitySignupSubmit = (TextView) findViewById(R.id.tv_activity_signup_submit);
        mCbActivitySignupProtocol = (CheckBox) findViewById(R.id.cb_activity_signup_protocol);
        mTvActivitySignupProtocol = (TextView) findViewById(R.id.tv_activity_signup_protocol);
        mTvActivitySignupRule = (TextView) findViewById(R.id.tv_activity_signup_rule);

        mTvTitle.setText("支付报名");
        mTvActivitySignupSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (packageIndex == 10086) {
                    Toast.makeText(mActivity, "请先选择套餐", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!mCbActivitySignupProtocol.isChecked()) {
                    Toast.makeText(mActivity, "请阅读协议", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
        mTvActivitySignupSubmit.setOnClickListener(this);
        mTvActivitySignupProtocol.setOnClickListener(this);
        mTvActivitySignupRule.setOnClickListener(this);
        mCbActivitySignupProtocol.setChecked(true);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mTeam_id = intent.getStringExtra("team_id");

        getTeamInfo(mTeam_id);

        getPackageData();

    }

    /**
     * 获取战队信息
     */
    private void getTeamInfo(final String teamId) {
        Map<String, String> map = new HashMap<>();
        map.put("id", teamId);
        HttpUtils.getInstance().get(Constants.TEAM_INFO, map, new HttpCallback<BattleInfoBean>() {
            @Override
            public void onSuccessExecute(BattleInfoBean battleInfoBean) {
                BattleInfoBean.DataBean data = battleInfoBean.getData();
                if (data != null) {
                    mTvActivitySignupCity.setText(data.getCity());
                    mTvActivitySignupName.setText(data.getName());
                    GlideUtils.getInstance().loadImage(data.getLogo(), mImgActivitySignupHead);
                    mTvActivitySignupDistance.setText(data.getDistance()+"km");
                    mTvActivitySignupCounts.setText(data.getMember_count() + "人");
                }
            }
        });
    }

    //获取套餐数据
    private void getPackageData() {

        Map<String, String> map = new HashMap<>();
        map.put("id", SaveUtil.getString("action_id", ""));
        HttpUtils.getInstance().get(Constants.PACKAGE_LIST, map, new HttpCallback<PackageBean>() {
            @Override
            public void onSuccessExecute(PackageBean packageBean) {
                List<PackageBean.DataBean> packageBeanData = packageBean.getData();
                if (packageBeanData != null && packageBeanData.size() > 0) {
                    packageList = packageBeanData;
                    packageList.get(0).setSelected(true);
                    mTvActivitySignupPrice.setText("小计:¥" + (float)packageList.get(0).getPrice()/100);
                    mPackage_Id = packageList.get(0).getId();
                    SignupPackageAdapter signupPackageAdapter = new SignupPackageAdapter(mActivity, packageList);
                    mRecyclerActivitySignupPackage.setLayoutManager(new LinearLayoutManager(mActivity));
                    signupPackageAdapter.setOnChooseCallback(new SignupPackageAdapter.OnChooseCallback() {
                        @Override
                        public void onChoose(int index) {
                            packageIndex = index;
                            mPackage_Id = packageList.get(index).getId();
                            mTvActivitySignupPrice.setText("小计:¥" + (float)packageList.get(index).getPrice()/100);
                        }
                    });
                    mRecyclerActivitySignupPackage.setAdapter(signupPackageAdapter);
                }
            }
        });
    }

    /**
     * 获取支付订单
     */
    public void getOrder(int package_Id){
        Map<String,String> map=new HashMap<>();
        map.put("team_id",mTeam_id+"");
        map.put("package_id",package_Id+"");
        map.put("activity_id",SaveUtil.getString(SaveConstants.ACTION_ID,""));

//        map.put("referrer","");
        HttpUtils.getInstance().post(Constants.CREATE_ORDER, map, new HttpCallback<CreateOrderBean>() {
            @Override
            public void onSuccessExecute(CreateOrderBean createOrderBean) {
                int order_id = createOrderBean.getData();
                signOrder(order_id + "");
            }
        });
    }

    /**
     * 支付宝订单加签
     */
    private void signOrder(String order_id) {
        Map<String, String> map = new HashMap<>();
        map.put("order_id", order_id);
        map.put("type", "order");//-------order--报名, upgrade_order--改套餐
        HttpUtils.getInstance().post(Constants.Sign_ORDER_ZFB, map, new HttpCallback<ZFBOrder>() {
            @Override
            public void onSuccessExecute(ZFBOrder zfbOrder) {
                ZFBOrder.DataBean data = zfbOrder.getData();
                if (data!=null) {
                    mOrder_Id = data.getId();
                    pay_zfb(data.getParams());
                }
            }
        });

    }

    /**
     * 支付宝进行支付
     * @param params
     */
    public void pay_zfb(final String params){
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(mActivity);
                Map<String, String> result = alipay.payV2(params, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 向服务器请求支付宝是否支付成功
     */
    private void check_zfb_pay_status() {
        Map<String,String> map=new HashMap<>();
        map.put("id", mOrder_Id +"");
        map.put("type","order");
        HttpUtils.getInstance().get(Constants.ZFB_PAY_STATUS, map, new HttpCallback<CheckZfbStatusBean>() {
            @Override
            public void onSuccessExecute(CheckZfbStatusBean checkZfbStatusBean) {
                CheckZfbStatusBean.DataBean data = checkZfbStatusBean.getData();
                if (data!=null) {
                    if (data.isFinished()) {
                        Toast.makeText(SignupActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(SignupActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.activity_signup;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_activity_signup_submit:
                if (!mCbActivitySignupProtocol.isChecked()) {
                    Toast.makeText(mActivity, "请仔细阅读用户协议和百日跑规则，并同意！", Toast.LENGTH_SHORT).show();
                    return;
                }
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_PHONE_STATE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) {
                                if (aBoolean) {
                                    getOrder(mPackage_Id);
                                } else {
                                    Toast.makeText(mActivity, "完成支付需要授权", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
            case R.id.tv_activity_signup_rule:
                Intent ruleIntent = new Intent(mActivity, CommonWebActivity.class);
                String rule = SaveUtil.getString(SaveConstants.RULE_URL,"");
                if (!TextUtils.isEmpty(rule)) {
                    ruleIntent.putExtra("url",rule);
                    ruleIntent.putExtra("title","百日跑规则");
                    startActivity(ruleIntent);
                }
                break;
            case R.id.tv_activity_signup_protocol:
                Intent protocolIntent = new Intent(mActivity, CommonWebActivity.class);
                String protocol = SaveUtil.getString(SaveConstants.SIGN_UP_PROTOCOL,"");
                if (!TextUtils.isEmpty(protocol)) {
                    protocolIntent.putExtra("url",protocol);
                    protocolIntent.putExtra("title","用户协议");
                    startActivity(protocolIntent);
                }
                break;
        }
    }

}
