package com.tourye.run.ui.dialogs;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.RejectReasonBean;
import com.tourye.run.ui.adapter.RejectReasonAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RejectReasonDialog extends BaseDialog{
    private RecyclerView mRecyclerDialogRejectReason;
    private TextView mTvDialogRejectReasonSubmit;

    public OnReasonSelectedListener mOnReasonSelectedListener;

    public RejectReasonDialog(Context context) {
        super(context);
    }

    @Override
    protected void initData() {
        List<RejectReasonBean> mRejectReasonBeans=new ArrayList<>();
        List<String> list = Arrays.asList("日期，截图日期错误或缺失", "截图，上传不相关图片", "距离，公里数未达目标", "输入，输入距离和截图不符", "配速，每公里超过十五分钟");
        for (int i = 0; i < list.size(); i++) {
            RejectReasonBean rejectReasonBean = new RejectReasonBean();
            rejectReasonBean.setReason(list.get(i));
            mRejectReasonBeans.add(rejectReasonBean);
        }
        RejectReasonBean rejectReasonBean = new RejectReasonBean();
        mRejectReasonBeans.add(rejectReasonBean);
        RejectReasonAdapter rejectReasonAdapter = new RejectReasonAdapter(mContext, mRejectReasonBeans);
        rejectReasonAdapter.setOnSelectedListener(new RejectReasonAdapter.OnSelectedListener() {
            @Override
            public void onSelected(final String reason) {
                if (TextUtils.isEmpty(reason)) {
                    mTvDialogRejectReasonSubmit.setAlpha(0.6F);
                }else{
                    mTvDialogRejectReasonSubmit.setAlpha(1F);
                    mTvDialogRejectReasonSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnReasonSelectedListener.onSelected(reason);
                            dismiss();
                        }
                    });
                }
            }
        });
        mRecyclerDialogRejectReason.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerDialogRejectReason.setAdapter(rejectReasonAdapter);
    }

    public void setOnReasonSelectedListener(OnReasonSelectedListener onReasonSelectedListener) {
        mOnReasonSelectedListener = onReasonSelectedListener;
    }

    @Override
    protected void initView() {
        mRecyclerDialogRejectReason = (RecyclerView) findViewById(R.id.recycler_dialog_reject_reason);
        mTvDialogRejectReasonSubmit = (TextView) findViewById(R.id.tv_dialog_reject_reason_submit);
    }

    @Override
    protected boolean isNeedMatchParent() {
        return false;
    }

    @Override
    public boolean isLocationBottom() {
        return false;
    }

    @Override
    protected int getRootView() {
        return R.layout.dialog_reject_reason;
    }

    public interface OnReasonSelectedListener{
        public void onSelected(String reason);
    }
}
