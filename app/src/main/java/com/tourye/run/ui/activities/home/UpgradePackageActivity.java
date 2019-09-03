package com.tourye.run.ui.activities.home;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.CheckZfbStatusBean;
import com.tourye.run.bean.CreateOrderBean;
import com.tourye.run.bean.PackageBean;
import com.tourye.run.bean.ZFBOrder;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.pay.zfb.PayResult;
import com.tourye.run.ui.adapter.SignupPackageAdapter;
import com.tourye.run.utils.SaveUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: UpgradePackageActivity
 * @Author: along
 * @Description: 升级套餐页面
 * @CreateDate: 2019/3/28 1:24 PM
 */
public class UpgradePackageActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView mRecyclerActivityUpgradePackage;
    private TextView mTvActivityUpgradePackagePrice;
    private TextView mTvActivityUpgradePackagePay;

    private List<PackageBean.DataBean> packageList;//套餐数据
    private int packageIndex = 10086;
    private int mPackage_Id;//订单id
    private String mOrder_Id;

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

    @Override
    public void initView() {
        mTvTitle.setText("升级套餐");
        mRecyclerActivityUpgradePackage = (RecyclerView) findViewById(R.id.recycler_activity_upgrade_package);
        mTvActivityUpgradePackagePrice = (TextView) findViewById(R.id.tv_activity_upgrade_package_price);
        mTvActivityUpgradePackagePay = (TextView) findViewById(R.id.tv_activity_upgrade_package_pay);

        mTvActivityUpgradePackagePay.setOnClickListener(this);
    }

    @Override
    public void initData() {
        getPackageData();
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

                    String package_id = SaveUtil.getString(SaveConstants.PACKAGE_ID, "");
                    int packageId = Integer.parseInt(package_id);
                    int currentPrice=0;
                    for (int i = 0; i < packageBeanData.size(); i++) {
                        if (packageBeanData.get(i).getId()==packageId) {
                            currentPrice=packageBeanData.get(i).getPrice();
                        }
                    }
                    List<PackageBean.DataBean> needPackages=new ArrayList<>();
                    for (int i = 0; i < packageBeanData.size(); i++) {
                        if (packageBeanData.get(i).getPrice()>currentPrice) {
                            needPackages.add(packageBeanData.get(i));
                        }
                    }
                    if (needPackages.size()<=0) {
                        return;
                    }
                    packageList = needPackages;
                    packageList.get(0).setSelected(true);
                    mTvActivityUpgradePackagePrice.setText("小计:¥" + (float)packageList.get(0).getPrice()/100);
                    mPackage_Id = packageList.get(0).getId();
                    SignupPackageAdapter signupPackageAdapter = new SignupPackageAdapter(mActivity, packageList);
                    mRecyclerActivityUpgradePackage.setLayoutManager(new LinearLayoutManager(mActivity));
                    signupPackageAdapter.setOnChooseCallback(new SignupPackageAdapter.OnChooseCallback() {
                        @Override
                        public void onChoose(int index) {
                            packageIndex = index;
                            mPackage_Id = packageList.get(index).getId();
                            mTvActivityUpgradePackagePrice.setText("小计:¥" + (float)packageList.get(index).getPrice()/100);
                        }
                    });
                    mRecyclerActivityUpgradePackage.setAdapter(signupPackageAdapter);
                }
            }
        });
    }

    /**
     * 获取支付订单
     */
    public void getOrder(String package_Id){
        Map<String,String> map=new HashMap<>();
        map.put("team_id",SaveUtil.getString(SaveConstants.TEAM_ID,""));
        map.put("package_id",package_Id);
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
                        Toast.makeText(mActivity, "支付成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(mActivity, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.activity_upgrade_package;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_activity_upgrade_package_pay:
                getOrder(SaveUtil.getString(SaveConstants.PACKAGE_ID,""));
                break;
        }
    }

}
