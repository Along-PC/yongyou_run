package com.tourye.run.ui.activities.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.CommonBean;
import com.tourye.run.bean.CommonJsonBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ViewContractActivity
 * @Author: along
 * @Description: 查看合同页面
 * @CreateDate: 2019/4/24 5:44 PM
 */
public class ViewContractActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mLlActivityViewContract;
    private CheckBox mCbActivityViewContractProtocol;
    private TextView mTvActivityViewContractProtocol;
    private TextView mTvActivityViewContractSubmit;


    @Override
    public void initView() {
        mLlActivityViewContract = (LinearLayout) findViewById(R.id.ll_activity_view_contract);
        mCbActivityViewContractProtocol = (CheckBox) findViewById(R.id.cb_activity_view_contract_protocol);
        mTvActivityViewContractProtocol = (TextView) findViewById(R.id.tv_activity_view_contract_protocol);
        mTvActivityViewContractSubmit = (TextView) findViewById(R.id.tv_activity_view_contract_submit);

        mTvTitle.setText("签署合同");
        mTvActivityViewContractProtocol.setOnClickListener(this);
        mTvActivityViewContractSubmit.setOnClickListener(this);
        mCbActivityViewContractProtocol.setChecked(true);

    }

    @Override
    public void initData() {

        getIdentity();

    }

    public void getIdentity() {
        Map<String, String> map = new HashMap<>();
        map.put("id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        HttpUtils.getInstance().get(Constants.CONTRACT_TYPE, map, new HttpCallback<CommonJsonBean>() {
            @Override
            public void onSuccessExecute(CommonJsonBean commonJsonBean) {
                String data = commonJsonBean.getData();
                List<String> contracts = new ArrayList<>();
                if ("monitor".equalsIgnoreCase(data)) {
                    //队长合同
                    contracts.add("https://static.run100.runorout.cn/meta/monitor_proto_20190422/1.jpg");
                    contracts.add("https://static.run100.runorout.cn/meta/monitor_proto_20190422/2.jpg");
                    contracts.add("https://static.run100.runorout.cn/meta/monitor_proto_20190422/3.jpg");
                    contracts.add("https://static.run100.runorout.cn/meta/monitor_proto_20190422/4.jpg");
                    contracts.add("https://static.run100.runorout.cn/meta/monitor_proto_20190422/5.jpg");
                } else if ("common".equalsIgnoreCase(data)) {
                    //队员合同
                    contracts.add("https://static.run100.runorout.cn/meta/recommend_proto_20190422/1.jpg");
                    contracts.add("https://static.run100.runorout.cn/meta/recommend_proto_20190422/2.jpg");
                    contracts.add("https://static.run100.runorout.cn/meta/recommend_proto_20190422/3.jpg");
                    contracts.add("https://static.run100.runorout.cn/meta/recommend_proto_20190422/4.jpg");
                }

                for (int i = 0; i < contracts.size(); i++) {
                    ImageView imageView = new ImageView(mActivity);
                    int imageWidth = DensityUtils.getScreenWidth();
                    int imageHeight = (int) (3507 / (float) 2480 * imageWidth);
                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(imageWidth, imageHeight);
                    imageView.setLayoutParams(layoutParams);
                    GlideUtils.getInstance().loadImage(contracts.get(i), imageView);
                    mLlActivityViewContract.addView(imageView);
                }
            }
        });

    }

    /**
     * 发起签约
     */
    public void createContract() {
        Map<String, String> map = new HashMap<>();
        map.put("id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        map.put("agreement_read", "1");
        HttpUtils.getInstance().post(Constants.CREATE_CONTRACT, map, new HttpCallback<CommonBean>() {
            @Override
            public void onSuccessExecute(CommonBean commonBean) {
                if (commonBean.getStatus() == 0) {
                    //是否实名认证成功
                    boolean is_verified = SaveUtil.getBoolean(SaveConstants.IS_VERIFIED, false);
                    if (is_verified) {
                        startActivity(new Intent(mActivity,PrizeSigningActivity.class));
                    }else{
                        startActivity(new Intent(mActivity,IdentityVerifiedActivity.class));

                    }
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.activity_view_contract;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_activity_view_contract_protocol:
                startActivity(new Intent(mActivity, DigitalProtocolActivity.class));
                break;
            case R.id.tv_activity_view_contract_submit:
                if (!mCbActivityViewContractProtocol.isChecked()) {
                    Toast.makeText(mActivity, "请阅读并同意合同", Toast.LENGTH_SHORT).show();
                    return;
                }
                createContract();
                break;
        }
    }
}
