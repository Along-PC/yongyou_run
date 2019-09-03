package com.tourye.run.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.BattleGroupBean;

import java.util.List;

/**
 *
 * @ClassName:   CreateBattleDistanceAdapter
 *
 * @Author:   along
 *
 * @Description:    创建战队距离组别适配器
 *
 * @CreateDate:   2019/4/9 1:14 PM
 *
 */
public class CreateBattleDistanceAdapter extends RecyclerView.Adapter<CreateBattleDistanceAdapter.CreateBattleDistanceHolder> {

    private List<BattleGroupBean.DataBean> mDataBeans;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private boolean isCanChange;

    public CreateBattleDistanceAdapter(List<BattleGroupBean.DataBean> dataBeans, Context context) {
        mDataBeans = dataBeans;
        mContext = context;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setCanChange(boolean canChange) {
        isCanChange = canChange;
    }

    public void setDataBeans(List<BattleGroupBean.DataBean> dataBeans) {
        mDataBeans = dataBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CreateBattleDistanceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CreateBattleDistanceHolder(mLayoutInflater.inflate(R.layout.item_activity_create_battle_distance,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CreateBattleDistanceHolder createBattleDistanceHolder, int i) {
        final BattleGroupBean.DataBean dataBean = mDataBeans.get(i);
        createBattleDistanceHolder.mTvItemCreateBattleDistance.setText(dataBean.getName());
        createBattleDistanceHolder.mTvItemCreateBattleDistance.setSelected(dataBean.isSelected());
        if (isCanChange) {
            createBattleDistanceHolder.mTvItemCreateBattleDistance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i1 = 0; i1 < mDataBeans.size(); i1++) {
                        mDataBeans.get(i1).setSelected(false);
                    }
                    if (v.isSelected()) {
                        dataBean.setSelected(false);
                    }else{
                        dataBean.setSelected(true);
                    }
                    notifyDataSetChanged();
                }
            });
        }else{
            createBattleDistanceHolder.mTvItemCreateBattleDistance.setOnClickListener(null);
        }


    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public class CreateBattleDistanceHolder extends RecyclerView.ViewHolder {
        private TextView mTvItemCreateBattleDistance;

        public CreateBattleDistanceHolder(@NonNull View itemView) {
            super(itemView);
            mTvItemCreateBattleDistance = (TextView) itemView.findViewById(R.id.tv_item_create_battle_distance);
        }
    }
}
