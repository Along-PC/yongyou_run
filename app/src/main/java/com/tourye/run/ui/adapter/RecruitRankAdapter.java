package com.tourye.run.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.BattleRecruitBean;
import com.tourye.run.utils.GlideUtils;

import java.util.List;

/**
 *
 * @ClassName:   RecruitRankAdapter
 *
 * @Author:   along
 *
 * @Description:    招募排行榜适配器
 *
 * @CreateDate:   2019/4/18 2:57 PM
 *
 */
public class RecruitRankAdapter extends RecyclerView.Adapter<RecruitRankAdapter.RecruitRankHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<BattleRecruitBean.DataBean.CurrentBean> mCurrentBeans;

    public RecruitRankAdapter(Context context, List<BattleRecruitBean.DataBean.CurrentBean> currentBeans) {
        mContext = context;
        mCurrentBeans = currentBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public RecruitRankAdapter(Context context) {
        mContext = context;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setCurrentBeans(List<BattleRecruitBean.DataBean.CurrentBean> currentBeans) {
        mCurrentBeans = currentBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecruitRankHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecruitRankHolder(mLayoutInflater.inflate(R.layout.item_activity_recruit_rank,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecruitRankHolder recruitRankHolder, int i) {
        BattleRecruitBean.DataBean.CurrentBean currentBean = mCurrentBeans.get(i);
        if (i==0) {
            recruitRankHolder.mImgItemActivityRecruitRankPos.setVisibility(View.GONE);
            recruitRankHolder.mTvItemActivityRecruitRankPos.setVisibility(View.GONE);
            recruitRankHolder.mTvItemActivityRecruitRankStatus.setVisibility(View.VISIBLE);
            if ("未上榜".equalsIgnoreCase(currentBean.getRank())) {
                recruitRankHolder.mTvItemActivityRecruitRankStatus.setText("未上榜");
            }else{
                recruitRankHolder.mTvItemActivityRecruitRankStatus.setText("第"+currentBean.getRank()+"名");
            }
        }else if(i==1){
            recruitRankHolder.mImgItemActivityRecruitRankPos.setVisibility(View.VISIBLE);
            recruitRankHolder.mTvItemActivityRecruitRankPos.setVisibility(View.GONE);
            recruitRankHolder.mTvItemActivityRecruitRankStatus.setVisibility(View.GONE);
            recruitRankHolder.mImgItemActivityRecruitRankPos.setBackgroundResource(R.drawable.icon_gold_medal);
        }else if(i==2){
            recruitRankHolder.mImgItemActivityRecruitRankPos.setVisibility(View.VISIBLE);
            recruitRankHolder.mTvItemActivityRecruitRankPos.setVisibility(View.GONE);
            recruitRankHolder.mTvItemActivityRecruitRankStatus.setVisibility(View.GONE);
            recruitRankHolder.mImgItemActivityRecruitRankPos.setBackgroundResource(R.drawable.icon_silver_medal);
        }else if(i==3){
            recruitRankHolder.mImgItemActivityRecruitRankPos.setVisibility(View.VISIBLE);
            recruitRankHolder.mTvItemActivityRecruitRankPos.setVisibility(View.GONE);
            recruitRankHolder.mTvItemActivityRecruitRankStatus.setVisibility(View.GONE);
            recruitRankHolder.mImgItemActivityRecruitRankPos.setBackgroundResource(R.drawable.icon_bronze_medal);
        }else{
            recruitRankHolder.mImgItemActivityRecruitRankPos.setVisibility(View.GONE);
            recruitRankHolder.mTvItemActivityRecruitRankPos.setVisibility(View.VISIBLE);
            recruitRankHolder.mTvItemActivityRecruitRankStatus.setVisibility(View.GONE);
            recruitRankHolder.mTvItemActivityRecruitRankPos.setText(i+"");
        }
        GlideUtils.getInstance().loadCircleImage(currentBean.getAvatar(),recruitRankHolder.mImgItemActivityRecruitRankLogo);
        recruitRankHolder.mTvItemActivityRecruitRankName.setText(currentBean.getNickname());
        recruitRankHolder.mTvItemActivityRecruitRankCount.setText(currentBean.getScore()+"人");
    }

    @Override
    public int getItemCount() {
        return mCurrentBeans.size();
    }

    public class RecruitRankHolder extends RecyclerView.ViewHolder {
        private TextView mTvItemActivityRecruitRankPos;
        private ImageView mImgItemActivityRecruitRankPos;
        private ImageView mImgItemActivityRecruitRankLogo;
        private TextView mTvItemActivityRecruitRankName;
        private TextView mTvItemActivityRecruitRankStatus;
        private TextView mTvItemActivityRecruitRankCount;

        public RecruitRankHolder(@NonNull View itemView) {
            super(itemView);
            mTvItemActivityRecruitRankPos = (TextView) itemView.findViewById(R.id.tv_item_activity_recruit_rank_pos);
            mImgItemActivityRecruitRankPos = (ImageView) itemView.findViewById(R.id.img_item_activity_recruit_rank_pos);
            mImgItemActivityRecruitRankLogo = (ImageView) itemView.findViewById(R.id.img_item_activity_recruit_rank_logo);
            mTvItemActivityRecruitRankName = (TextView) itemView.findViewById(R.id.tv_item_activity_recruit_rank_name);
            mTvItemActivityRecruitRankStatus = (TextView) itemView.findViewById(R.id.tv_item_activity_recruit_rank_status);
            mTvItemActivityRecruitRankCount = (TextView) itemView.findViewById(R.id.tv_item_activity_recruit_rank_count);

        }
    }
}
