package com.tourye.run.ui.fragments;

import android.content.Intent;
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
 * @Description:    提现到银行卡
 *
 * @CreateDate:   2019/4/25 3:21 PM
 *
 */
public class WithdrawalBankAccountFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout mLlFragmentWithdrawalBankPickup;
    private RelativeLayout mRlFragmentWithdrawalBankAccount;
    private TextView mTvFragmentWithdrawalBankAccount;
    private ImageView mImgFragmentWithdrawalBankDetail;
    private LinearLayout mLlFragmentWithdrawalZfbIdcard;
    private TextView mTvFragmentWithdrawalBankIdcard;
    private TextView mTvFragmentWithdrawalBankPickup;
    private String mBank_card;

    @Override
    public void initView(View view) {
        mLlFragmentWithdrawalBankPickup = (LinearLayout) view.findViewById(R.id.ll_fragment_withdrawal_bank_pickup);
        mRlFragmentWithdrawalBankAccount = (RelativeLayout) view.findViewById(R.id.rl_fragment_withdrawal_bank_account);
        mTvFragmentWithdrawalBankAccount = (TextView) view.findViewById(R.id.tv_fragment_withdrawal_bank_account);
        mImgFragmentWithdrawalBankDetail = (ImageView) view.findViewById(R.id.img_fragment_withdrawal_bank_detail);
        mLlFragmentWithdrawalZfbIdcard = (LinearLayout) view.findViewById(R.id.ll_fragment_withdrawal_zfb_idcard);
        mTvFragmentWithdrawalBankIdcard = (TextView) view.findViewById(R.id.tv_fragment_withdrawal_bank_idcard);
        mTvFragmentWithdrawalBankPickup = (TextView) view.findViewById(R.id.tv_fragment_withdrawal_bank_pickup);

        mImgFragmentWithdrawalBankDetail.setOnClickListener(this);
        mTvFragmentWithdrawalBankPickup.setOnClickListener(this);


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
                mBank_card = data.getBank_card();
                String id_card = data.getId_card();
                String name = data.getName();
                if (TextUtils.isEmpty(mBank_card)) {
                    mLlFragmentWithdrawalZfbIdcard.setVisibility(View.GONE);
                    mTvFragmentWithdrawalBankAccount.setText("暂无绑定银行卡账号");
                }else{
                    mLlFragmentWithdrawalZfbIdcard.setVisibility(View.VISIBLE);
                    String cardSubstring = mBank_card.substring(mBank_card.length() - 4, mBank_card.length());
                    String nameSubString = name.substring(name.length() - 1, name.length());
                    mTvFragmentWithdrawalBankAccount.setText("**"+nameSubString+"  **** **** **** ***"+cardSubstring);
                    String headSubstring = id_card.substring(0, 4);
                    String endSubstring = id_card.substring(id_card.length() - 4, id_card.length());
                    mTvFragmentWithdrawalBankIdcard.setText(headSubstring+"************"+endSubstring);
                }
            }
        });
    }

    public void withdraw(){
        Map<String,String> map=new HashMap<>();
        map.put("type","bank_card");
        map.put("account",mBank_card);
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
        return R.layout.fragment_withdrawal_bank;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_fragment_withdrawal_bank_detail:
                Intent intent = new Intent(mActivity, WithdrawalConfirmEditActivity.class);
                intent.putExtra("type",2);
                startActivity(intent);
                break;
            case R.id.tv_fragment_withdrawal_bank_pickup:
                if (TextUtils.isEmpty(mBank_card)) {
                    Toast.makeText(mActivity, "请检查账户", Toast.LENGTH_SHORT).show();
                    return;
                }
                withdraw();
                break;

        }
    }
}
