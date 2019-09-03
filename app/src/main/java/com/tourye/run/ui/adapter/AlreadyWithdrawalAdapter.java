package com.tourye.run.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.AlreadyWithdrawalBean;

import java.util.List;

/**
 *
 * @ClassName:   AlreadyWithdrawalAdapter
 *
 * @Author:   along
 *
 * @Description:    已经发起提现的适配器
 *
 * @CreateDate:   2019/4/23 4:15 PM
 *
 */
public class AlreadyWithdrawalAdapter extends RecyclerView.Adapter<AlreadyWithdrawalAdapter.AlreadyWithdrawalHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<AlreadyWithdrawalBean.DataBean> mDataBeans;


    public AlreadyWithdrawalAdapter(Context context, List<AlreadyWithdrawalBean.DataBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDataBeans(List<AlreadyWithdrawalBean.DataBean> dataBeans) {
        mDataBeans = dataBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlreadyWithdrawalHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AlreadyWithdrawalHolder(mLayoutInflater.inflate(R.layout.item_already_withdrawal,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlreadyWithdrawalHolder alreadyWithdrawalHolder, int i) {
        AlreadyWithdrawalBean.DataBean dataBean = mDataBeans.get(i);
        alreadyWithdrawalHolder.mTvItemAlreadyWithdrawalAccount.setText(dataBean.getAccount());
        alreadyWithdrawalHolder.mTvItemAlreadyWithdrawalStatus.setText(dataBean.getStatus());
        alreadyWithdrawalHolder.mTvItemAlreadyWithdrawalDate.setText(dataBean.getTime());
        int count = dataBean.getCount();
        float withdrawalMoney = count / 100.0f;
        alreadyWithdrawalHolder.mTvItemAlreadyWithdrawalCount.setText("￥"+withdrawalMoney);
    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public class AlreadyWithdrawalHolder extends RecyclerView.ViewHolder {
        private TextView mTvItemAlreadyWithdrawalAccount;
        private TextView mTvItemAlreadyWithdrawalStatus;
        private TextView mTvItemAlreadyWithdrawalDate;
        private TextView mTvItemAlreadyWithdrawalCount;


        public AlreadyWithdrawalHolder(@NonNull View itemView) {
            super(itemView);
            mTvItemAlreadyWithdrawalAccount = (TextView) itemView.findViewById(R.id.tv_item_already_withdrawal_account);
            mTvItemAlreadyWithdrawalStatus = (TextView) itemView.findViewById(R.id.tv_item_already_withdrawal_status);
            mTvItemAlreadyWithdrawalDate = (TextView) itemView.findViewById(R.id.tv_item_already_withdrawal_date);
            mTvItemAlreadyWithdrawalCount = (TextView) itemView.findViewById(R.id.tv_item_already_withdrawal_count);

        }
    }
}
