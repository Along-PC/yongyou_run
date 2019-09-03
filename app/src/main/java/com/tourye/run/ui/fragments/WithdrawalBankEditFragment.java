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
public class WithdrawalBankEditFragment extends BaseFragment implements View.OnClickListener {
    private EditText mEdtFragmentWithdrawalBankAccount;
    private TextView mTvFragmentWithdrawalBankSave;

    @Override
    public void initView(View view) {

        mEdtFragmentWithdrawalBankAccount = (EditText) view.findViewById(R.id.edt_fragment_withdrawal_bank_account);
        mTvFragmentWithdrawalBankSave = (TextView) view.findViewById(R.id.tv_fragment_withdrawal_bank_save);

        mTvFragmentWithdrawalBankSave.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    /**
     * 更新提现账户
     */
    public void updateWithdrawalAccount(String account){
        Map<String,String> map=new HashMap<>();
        map.put("type","bank_card");
        map.put("account",account);
        HttpUtils.getInstance().post(Constants.UPDATE_WITHDRAWAL_ACCOUNT, map, new HttpCallback<CommonBean>() {
            @Override
            public void onPreExcute() {
                super.onPreExcute();
                mEdtFragmentWithdrawalBankAccount.setText("");
            }

            @Override
            public void onSuccessExecute(CommonBean commonBean) {
                if (commonBean.getStatus()==0) {
                    Toast.makeText(mActivity, "绑定账户成功", Toast.LENGTH_SHORT).show();
                    mActivity.sendBroadcast(new Intent(WithdrawalConfirmEditActivity.CLOSE_ACTION));
                }
            }
        });
    }


    @Override
    public int getRootView() {
        return R.layout.fragment_withdrawal_bank_edit;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_fragment_withdrawal_bank_save:
                String account = mEdtFragmentWithdrawalBankAccount.getText().toString();
                if (TextUtils.isEmpty(account)) {
                    Toast.makeText(mActivity, "请填写账户", Toast.LENGTH_SHORT).show();
                    return;
                }
                updateWithdrawalAccount(account);
                break;

        }
    }
}
