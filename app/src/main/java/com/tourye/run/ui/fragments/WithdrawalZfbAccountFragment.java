package com.tourye.run.ui.fragments;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.base.BaseFragment;
import com.tourye.run.bean.CommonBean;
import com.tourye.run.bean.WithdrawalAccountBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.mine.WithdrawalConfirmEditActivity;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @ClassName:   WithdrawalZfbAccountFragment
 *
 * @Author:   along
 *
 * @Description:    提现到支付宝
 *
 * @CreateDate:   2019/4/25 3:21 PM
 *
 */
public class WithdrawalZfbAccountFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout mLlFragmentWithdrawalZfbPickup;
    private TextView mTvFragmentWithdrawalZfbAccount;
    private ImageView mImgFragmentWithdrawalZfbDetail;
    private TextView mTvFragmentWithdrawalZfbIdcard;
    private TextView mTvFragmentWithdrawalZfbPickup;
    private RelativeLayout mRlFragmentWithdrawalZfbAccount;
    private LinearLayout mLlFragmentWithdrawalZfbIdcard;
    private String mAlipay;//提现账户


    @Override
    public void initView(View view) {
        mLlFragmentWithdrawalZfbPickup = (LinearLayout) view.findViewById(R.id.ll_fragment_withdrawal_zfb_pickup);
        mTvFragmentWithdrawalZfbAccount = (TextView) view.findViewById(R.id.tv_fragment_withdrawal_zfb_account);
        mImgFragmentWithdrawalZfbDetail = (ImageView) view.findViewById(R.id.img_fragment_withdrawal_zfb_detail);
        mTvFragmentWithdrawalZfbIdcard = (TextView) view.findViewById(R.id.tv_fragment_withdrawal_zfb_idcard);
        mTvFragmentWithdrawalZfbPickup = (TextView) view.findViewById(R.id.tv_fragment_withdrawal_zfb_pickup);
        mRlFragmentWithdrawalZfbAccount = (RelativeLayout) view.findViewById(R.id.rl_fragment_withdrawal_zfb_account);
        mLlFragmentWithdrawalZfbIdcard = (LinearLayout) view.findViewById(R.id.ll_fragment_withdrawal_zfb_idcard);

        mImgFragmentWithdrawalZfbDetail.setOnClickListener(this);
        mTvFragmentWithdrawalZfbPickup.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getWithdrawalAccount();
    }

    /**
     * 获取提现账户
     */
    public void getWithdrawalAccount(){
        Map<String,String> map=new HashMap<>();
        HttpUtils.getInstance().get(Constants.GET_WITHDRAWAL_ACCOUNT, map, new HttpCallback<WithdrawalAccountBean>() {
            @Override
            public void onSuccessExecute(WithdrawalAccountBean withdrawalAccountBean) {
                WithdrawalAccountBean.DataBean data = withdrawalAccountBean.getData();
                if (data==null) {
                    return;
                }
                mAlipay = data.getAlipay();
                String id_card = data.getId_card();
                String name = data.getName();
                if (TextUtils.isEmpty(mAlipay)) {
                    mLlFragmentWithdrawalZfbIdcard.setVisibility(View.GONE);
                    mTvFragmentWithdrawalZfbAccount.setText("暂无绑定支付宝账号");
                    mLlFragmentWithdrawalZfbIdcard.setVisibility(View.GONE);
                }else{
                    mLlFragmentWithdrawalZfbIdcard.setVisibility(View.VISIBLE);
                    String nameSubString = name.substring(name.length() - 1, name.length());
                    mTvFragmentWithdrawalZfbAccount.setText("**"+nameSubString+" 支付宝 "+ mAlipay);
                    String headSubstring = id_card.substring(0, 4);
                    String endSubstring = id_card.substring(id_card.length() - 4, id_card.length());
                    mTvFragmentWithdrawalZfbIdcard.setText(headSubstring+"************"+endSubstring);
                }
            }
        });
    }

    public void withdraw(){
        Map<String,String> map=new HashMap<>();
        map.put("type","alipay");
        map.put("account",mAlipay);
        HttpUtils.getInstance().post(Constants.WITHDRAW, map, new HttpCallback<CommonBean>() {
            @Override
            public void onSuccessExecute(CommonBean commonBean) {
                if (commonBean.getStatus()==0) {
                    Toast.makeText(mActivity, "提现成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_withdrawal_zfb;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_fragment_withdrawal_zfb_detail:
                Intent intent = new Intent(mActivity, WithdrawalConfirmEditActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            case R.id.tv_fragment_withdrawal_zfb_pickup:
                if (TextUtils.isEmpty(mAlipay)) {
                    Toast.makeText(mActivity, "请检查账户", Toast.LENGTH_SHORT).show();
                    return;
                }
                withdraw();
                break;
        }
    }
}
