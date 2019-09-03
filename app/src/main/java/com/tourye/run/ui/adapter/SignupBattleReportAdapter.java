package com.tourye.run.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.BattleReportBean;
import com.tourye.run.ui.activities.home.BattleReportActivity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName:   SignupBattleReportAdapter
 *
 * @Author:   along
 *
 * @Description:战报列表适配器---首页
 *
 * @CreateDate:   2019/3/15 2:35 PM
 *
 */
public class SignupBattleReportAdapter extends RecyclerView.Adapter<SignupBattleReportAdapter.BattleReportHolder> {


    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<BattleReportBean.DataBean> mDataBeans=new ArrayList<>();

    public SignupBattleReportAdapter(Context context, List<BattleReportBean.DataBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public BattleReportHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BattleReportHolder(mLayoutInflater.inflate(R.layout.item_battle_report,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BattleReportHolder battleReportHolder, int i) {
        BattleReportBean.DataBean dataBean = mDataBeans.get(i % mDataBeans.size());
        battleReportHolder.mTvItemBattleReport.setText(dataBean.getTitle());
        battleReportHolder.mTvItemBattleReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,BattleReportActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public class BattleReportHolder extends RecyclerView.ViewHolder {
        private TextView mTvItemBattleReport;

        public BattleReportHolder(@NonNull View itemView) {
            super(itemView);
            mTvItemBattleReport = (TextView) itemView.findViewById(R.id.tv_item_battle_report);
        }
    }
}
