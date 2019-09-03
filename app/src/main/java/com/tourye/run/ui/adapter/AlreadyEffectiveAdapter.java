package com.tourye.run.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.AlreadyEffectiveBean;

import java.util.List;

/**
 *
 * @ClassName:   AlreadyEffectiveAdapter
 *
 * @Author:   along
 *
 * @Description:       已经生效的奖金提取适配器
 *
 * @CreateDate:   2019/4/23 4:31 PM
 *
 */
public class AlreadyEffectiveAdapter extends RecyclerView.Adapter<AlreadyEffectiveAdapter.AlreadyEffectiveHodler> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<AlreadyEffectiveBean.DataBean> mDataBeans;


    public AlreadyEffectiveAdapter(Context context, List<AlreadyEffectiveBean.DataBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setDataBeans(List<AlreadyEffectiveBean.DataBean> dataBeans) {
        mDataBeans = dataBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlreadyEffectiveHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AlreadyEffectiveHodler(mLayoutInflater.inflate(R.layout.item_already_effective,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlreadyEffectiveHodler alreadyEffectiveHodler, int i) {
        AlreadyEffectiveBean.DataBean dataBean = mDataBeans.get(i);
        alreadyEffectiveHodler.mTvItemAlreadyEffectiveTitle.setText(dataBean.getType());
        float effectiveMoney = dataBean.getCount() / 100.0f;
        alreadyEffectiveHodler.mTvItemAlreadyEffectiveMoney.setText("￥"+effectiveMoney);
        alreadyEffectiveHodler.mTvItemAlreadyEffectiveLabel.setText(dataBean.getDescription());
    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public class AlreadyEffectiveHodler extends RecyclerView.ViewHolder {
        private TextView mTvItemAlreadyEffectiveTitle;
        private TextView mTvItemAlreadyEffectiveLabel;
        private TextView mTvItemAlreadyEffectiveMoney;

        public AlreadyEffectiveHodler(@NonNull View itemView) {
            super(itemView);
            mTvItemAlreadyEffectiveTitle = (TextView) itemView.findViewById(R.id.tv_item_already_effective_title);
            mTvItemAlreadyEffectiveLabel = (TextView) itemView.findViewById(R.id.tv_item_already_effective_label);
            mTvItemAlreadyEffectiveMoney = (TextView) itemView.findViewById(R.id.tv_item_already_effective_money);
        }
    }
}
