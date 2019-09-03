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
import com.tourye.run.bean.BattleRankingCompleteBean;
import com.tourye.run.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: BattleRankingCompleteAdapter
 * @Author: along
 * @Description: 战队完赛率排行榜适配器
 * @CreateDate: 2019/4/1 10:43 AM
 */
public class BattleRankingCompleteAdapter extends RecyclerView.Adapter<BattleRankingCompleteAdapter.BattleRankingCompleteHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<BattleRankingCompleteBean.DataBean.ListBean> mListBeans = new ArrayList<>();
    private boolean isShowCurrent = false;

    public BattleRankingCompleteAdapter(Context context, List<BattleRankingCompleteBean.DataBean.ListBean> listBeans) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListBeans = listBeans;
    }

    /**
     * 是否显示自己所在战队数据
     *
     * @param showCurrent
     */
    public void setShowCurrent(boolean showCurrent) {
        isShowCurrent = showCurrent;
    }

    public void setListBeans(List<BattleRankingCompleteBean.DataBean.ListBean> listBeans) {
        mListBeans = listBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BattleRankingCompleteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BattleRankingCompleteHolder(mLayoutInflater.inflate(R.layout.item_fragment_battle_ranking_complete, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BattleRankingCompleteHolder battleRankingCompleteHolder, int i) {
        BattleRankingCompleteBean.DataBean.ListBean listBean = mListBeans.get(i);
        battleRankingCompleteHolder.mTvBattleRankingCompleteName.setText(listBean.getName());
        battleRankingCompleteHolder.mTvBattleRankingCompleteGroup.setText(listBean.getMember_count() + "人 · " + listBean.getTotal_distance() + "公里");
        GlideUtils.getInstance().loadImage(listBean.getLogo(), battleRankingCompleteHolder.mImgBattleRankingCompleteHead);
        if (isShowCurrent) {
            bindAllData(battleRankingCompleteHolder, listBean, i);
        } else {
            bindListData(battleRankingCompleteHolder, listBean, i);
        }

    }

    /**
     * 绑定战队列表数据----不包括自己的
     *
     * @param battleRankingCompleteHolder
     * @param listBean
     * @param i
     */
    private void bindListData(BattleRankingCompleteHolder battleRankingCompleteHolder, BattleRankingCompleteBean.DataBean.ListBean listBean, int i) {
        if (i < 3) {
            battleRankingCompleteHolder.mTvBattleRankingCompletePosition.setVisibility(View.GONE);
            battleRankingCompleteHolder.mImgBattleRankingCompletePosition.setVisibility(View.VISIBLE);
        } else {
            battleRankingCompleteHolder.mTvBattleRankingCompletePosition.setVisibility(View.VISIBLE);
            battleRankingCompleteHolder.mImgBattleRankingCompletePosition.setVisibility(View.GONE);
        }
        battleRankingCompleteHolder.mViewBattleRankingCompleteLine.setVisibility(View.VISIBLE);
        battleRankingCompleteHolder.mViewBattleRankingCompleteSeparation.setVisibility(View.GONE);
        if (i == 0) {
            battleRankingCompleteHolder.mImgBattleRankingCompletePosition.setBackgroundResource(R.drawable.icon_gold_medal);
        } else if (i == 1) {
            battleRankingCompleteHolder.mImgBattleRankingCompletePosition.setBackgroundResource(R.drawable.icon_silver_medal);
        } else if (i == 2) {
            battleRankingCompleteHolder.mImgBattleRankingCompletePosition.setBackgroundResource(R.drawable.icon_bronze_medal);
        } else {
            battleRankingCompleteHolder.mTvBattleRankingCompletePosition.setText((i + 1) + "");
        }
        battleRankingCompleteHolder.mTvBattleRankingCompleteRate.setText(listBean.getRate()+"%");
    }

    /**
     * 绑定所有数据包括自己的
     *
     * @param battleRankingCompleteHolder
     * @param listBean
     * @param i
     */
    private void bindAllData(BattleRankingCompleteHolder battleRankingCompleteHolder, BattleRankingCompleteBean.DataBean.ListBean listBean, int i) {
        if (i == 0) {
            battleRankingCompleteHolder.mViewBattleRankingCompleteLine.setVisibility(View. GONE);
            battleRankingCompleteHolder.mViewBattleRankingCompleteSeparation.setVisibility(View.VISIBLE);
            battleRankingCompleteHolder.mTvBattleRankingCompletePosition.setVisibility(View.VISIBLE);
            battleRankingCompleteHolder.mImgBattleRankingCompletePosition.setVisibility(View.GONE);
        } else if (i < 4) {
            battleRankingCompleteHolder.mViewBattleRankingCompleteLine.setVisibility(View.VISIBLE);
            battleRankingCompleteHolder.mViewBattleRankingCompleteSeparation.setVisibility(View.GONE);
            battleRankingCompleteHolder.mTvBattleRankingCompletePosition.setVisibility(View.GONE);
            battleRankingCompleteHolder.mImgBattleRankingCompletePosition.setVisibility(View.VISIBLE);
        } else {
            battleRankingCompleteHolder.mViewBattleRankingCompleteLine.setVisibility(View.VISIBLE);
            battleRankingCompleteHolder.mViewBattleRankingCompleteSeparation.setVisibility(View.GONE);
            battleRankingCompleteHolder.mTvBattleRankingCompletePosition.setVisibility(View.VISIBLE);
            battleRankingCompleteHolder.mImgBattleRankingCompletePosition.setVisibility(View.GONE);
        }
        if (i == 0) {
            battleRankingCompleteHolder.mTvBattleRankingCompletePosition.setText(listBean.getRank() + "");
        } else if (i == 1) {
            battleRankingCompleteHolder.mImgBattleRankingCompletePosition.setBackgroundResource(R.drawable.icon_gold_medal);
        } else if (i == 2) {
            battleRankingCompleteHolder.mImgBattleRankingCompletePosition.setBackgroundResource(R.drawable.icon_silver_medal);
        } else if (i == 3) {
            battleRankingCompleteHolder.mImgBattleRankingCompletePosition.setBackgroundResource(R.drawable.icon_bronze_medal);
        } else {
            battleRankingCompleteHolder.mTvBattleRankingCompletePosition.setText(i + "");
        }
        battleRankingCompleteHolder.mTvBattleRankingCompleteRate.setText(listBean.getRate()+"%");
    }

    @Override
    public int getItemCount() {
        return mListBeans.size();
    }

    public class BattleRankingCompleteHolder extends RecyclerView.ViewHolder {
        private ImageView mImgBattleRankingCompletePosition;
        private TextView mTvBattleRankingCompletePosition;
        private ImageView mImgBattleRankingCompleteHead;
        private TextView mTvBattleRankingCompleteName;
        private TextView mTvBattleRankingCompleteGroup;
        private TextView mTvBattleRankingCompleteRate;
        private View mViewBattleRankingCompleteLine;
        private View mViewBattleRankingCompleteSeparation;

        public BattleRankingCompleteHolder(@NonNull View itemView) {
            super(itemView);
            mImgBattleRankingCompletePosition = (ImageView) itemView.findViewById(R.id.img_battle_ranking_complete_position);
            mTvBattleRankingCompletePosition = (TextView) itemView.findViewById(R.id.tv_battle_ranking_complete_position);
            mImgBattleRankingCompleteHead = (ImageView) itemView.findViewById(R.id.img_battle_ranking_complete_head);
            mTvBattleRankingCompleteName = (TextView) itemView.findViewById(R.id.tv_battle_ranking_complete_name);
            mTvBattleRankingCompleteGroup = (TextView) itemView.findViewById(R.id.tv_battle_ranking_complete_group);
            mTvBattleRankingCompleteRate = (TextView) itemView.findViewById(R.id.tv_battle_ranking_complete_rate);
            mViewBattleRankingCompleteLine = (View) itemView.findViewById(R.id.view_battle_ranking_complete_line);
            mViewBattleRankingCompleteSeparation = (View) itemView.findViewById(R.id.view_battle_ranking_complete_separation);
        }
    }
}
