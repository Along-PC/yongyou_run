package com.tourye.run.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.BattleReportBean;
import com.tourye.run.ui.activities.common.CommonWebActivity;

import java.util.ArrayList;
import java.util.List;

public class BattleReportAdapter extends RecyclerView.Adapter<BattleReportAdapter.BattleReportHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<BattleReportBean.DataBean> mDataBeans=new ArrayList<>();

    public BattleReportAdapter(Context context, List<BattleReportBean.DataBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public BattleReportAdapter(Context context) {
        mContext = context;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDataBeans(List<BattleReportBean.DataBean> dataBeans) {
        mDataBeans = dataBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BattleReportHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BattleReportHolder(mLayoutInflater.inflate(R.layout.item_activity_battle_report,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BattleReportHolder battleReportHolder, int i) {
        final BattleReportBean.DataBean dataBean = mDataBeans.get(i);
        battleReportHolder.mTvItemBattleReportIntro.setText(dataBean.getTitle());
        battleReportHolder.mTvItemBattleReportTime.setText(dataBean.getTime());
        battleReportHolder.mLlItemBattleReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(dataBean.getLink())) {
                    Intent intent = new Intent(mContext, CommonWebActivity.class);
                    intent.putExtra("url",dataBean.getLink());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public class BattleReportHolder extends RecyclerView.ViewHolder{
        private TextView mTvItemBattleReportIntro;
        private TextView mTvItemBattleReportTime;
        private LinearLayout mLlItemBattleReport;

        public BattleReportHolder(@NonNull View itemView) {
            super(itemView);
            mTvItemBattleReportIntro = (TextView) itemView.findViewById(R.id.tv_item_battle_report_intro);
            mTvItemBattleReportTime = (TextView) itemView.findViewById(R.id.tv_item_battle_report_time);
            mLlItemBattleReport = (LinearLayout) itemView.findViewById(R.id.ll_item_battle_report);

        }
    }
}
