package com.tourye.run.ui.dialogs;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.ui.activities.mine.ViewContractActivity;

/**
 *
 * @ClassName:   CheckSigningDialog
 *
 * @Author:   along
 *
 * @Description:    查询是否签约弹窗
 *
 * @CreateDate:   2019/4/23 1:38 PM
 *
 */
public class CheckSigningDialog extends BaseDialog{
    private TextView mTvDialogCheckSigningCertain;
    private TextView mTvDialogCheckSigningCancel;

    private OnCancelCliskListener mOnCancelCliskListener;

    public CheckSigningDialog(Context context) {
        super(context);
    }

    public void setOnCancelCliskListener(OnCancelCliskListener onCancelCliskListener) {
        mOnCancelCliskListener = onCancelCliskListener;
        mTvDialogCheckSigningCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mOnCancelCliskListener.onCancel();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTvDialogCheckSigningCertain = (TextView) findViewById(R.id.tv_dialog_check_signing_certain);
        mTvDialogCheckSigningCancel = (TextView) findViewById(R.id.tv_dialog_check_signing_cancel);
        mTvDialogCheckSigningCertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,ViewContractActivity.class));
            }
        });
        setCancelable(false);

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected int getRootView() {
        return R.layout.dialog_check_signing;
    }

    public interface OnCancelCliskListener{
        public void onCancel();
    }
}
