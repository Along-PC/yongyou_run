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
import com.tourye.run.SaveConstants;
import com.tourye.run.bean.TeamThumbRankBean;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TeamThumbRankAdapter
 * @Author: along
 * @Description: 战队点赞排行适配器
 * @CreateDate: 2019/4/1 3:28 PM
 */
public class TeamThumbRankAdapter extends RecyclerView.Adapter<TeamThumbRankAdapter.TeamThumbRankHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<TeamThumbRankBean.DataBean.ListBean> mdatas = new ArrayList<>();

    public TeamThumbRankAdapter(Context context, List<TeamThumbRankBean.DataBean.ListBean> mdatas) {
        mContext = context;
        this.mdatas = mdatas;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setdatas(List<TeamThumbRankBean.DataBean.ListBean> mdatas) {
        this.mdatas = mdatas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TeamThumbRankHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TeamThumbRankHolder(mLayoutInflater.inflate(R.layout.item_team_thumb_rank, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeamThumbRankHolder teamThumbRankHolder, int i) {
        TeamThumbRankBean.DataBean.ListBean listBean = mdatas.get(i);
        if (SaveUtil.getBoolean(SaveConstants.IS_JOINED,false)) {
            bindAllData(teamThumbRankHolder, i, listBean);
        } else {
            bindTeamData(teamThumbRankHolder, i, listBean);
        }
    }

    /**
     * 绑定未报名的数据
     *
     * @param teamThumbRankHolder
     * @param i
     * @param listBean
     */
    private void bindTeamData(TeamThumbRankHolder teamThumbRankHolder, int i, TeamThumbRankBean.DataBean.ListBean listBean) {
        if (i == 0) {
            teamThumbRankHolder.mTvItemTeamThumbRankPosition.setVisibility(View.GONE);
            teamThumbRankHolder.mImgItemTeamThumbRankPosition.setVisibility(View.VISIBLE);
            teamThumbRankHolder.mImgItemTeamThumbRankPosition.setBackgroundResource(R.drawable.icon_gold_medal);
        } else if (i == 1) {
            teamThumbRankHolder.mTvItemTeamThumbRankPosition.setVisibility(View.GONE);
            teamThumbRankHolder.mImgItemTeamThumbRankPosition.setVisibility(View.VISIBLE);
            teamThumbRankHolder.mImgItemTeamThumbRankPosition.setBackgroundResource(R.drawable.icon_silver_medal);
        } else if (i == 2) {
            teamThumbRankHolder.mTvItemTeamThumbRankPosition.setVisibility(View.GONE);
            teamThumbRankHolder.mImgItemTeamThumbRankPosition.setVisibility(View.VISIBLE);
            teamThumbRankHolder.mImgItemTeamThumbRankPosition.setBackgroundResource(R.drawable.icon_bronze_medal);
        } else {
            teamThumbRankHolder.mTvItemTeamThumbRankPosition.setVisibility(View.VISIBLE);
            teamThumbRankHolder.mImgItemTeamThumbRankPosition.setVisibility(View.GONE);
            teamThumbRankHolder.mTvItemTeamThumbRankPosition.setText(i+1+"");
        }
        teamThumbRankHolder.mTvItemTeamThumbRankDescribe.setVisibility(View.GONE);
        teamThumbRankHolder.mViewItemTeamThumbRankSeparation.setVisibility(View.GONE);
        teamThumbRankHolder.mViewItemTeamThumbRankLine.setVisibility(View.VISIBLE);

        GlideUtils.getInstance().loadRoundImage(listBean.getLogo(), teamThumbRankHolder.mImgItemTeamThumbRankHead);
        teamThumbRankHolder.mTvItemTeamThumbRankName.setText(listBean.getName());
        teamThumbRankHolder.mTvItemTeamThumbRankCount.setText(listBean.getCount() + "");
    }

    /**
     * 绑定已经报名的数据
     *
     * @param teamThumbRankHolder
     * @param i
     * @param listBean
     */
    private void bindAllData(TeamThumbRankHolder teamThumbRankHolder, int i, TeamThumbRankBean.DataBean.ListBean listBean) {

        if(i==0){
            teamThumbRankHolder.mTvItemTeamThumbRankPosition.setVisibility(View.GONE);
            teamThumbRankHolder.mImgItemTeamThumbRankPosition.setVisibility(View.GONE);
            teamThumbRankHolder.mTvItemTeamThumbRankDescribe.setVisibility(View.VISIBLE);
            teamThumbRankHolder.mViewItemTeamThumbRankSeparation.setVisibility(View.VISIBLE);
            teamThumbRankHolder.mViewItemTeamThumbRankLine.setVisibility(View.GONE);
        }else if(i<4){
            teamThumbRankHolder.mTvItemTeamThumbRankPosition.setVisibility(View.GONE);
            teamThumbRankHolder.mImgItemTeamThumbRankPosition.setVisibility(View.VISIBLE);
            teamThumbRankHolder.mTvItemTeamThumbRankDescribe.setVisibility(View.GONE);
            teamThumbRankHolder.mViewItemTeamThumbRankSeparation.setVisibility(View.GONE);
            teamThumbRankHolder.mViewItemTeamThumbRankLine.setVisibility(View.VISIBLE);
        }else{
            teamThumbRankHolder.mTvItemTeamThumbRankPosition.setVisibility(View.VISIBLE);
            teamThumbRankHolder.mImgItemTeamThumbRankPosition.setVisibility(View.GONE);
            teamThumbRankHolder.mTvItemTeamThumbRankDescribe.setVisibility(View.GONE);
            teamThumbRankHolder.mViewItemTeamThumbRankSeparation.setVisibility(View.GONE);
            teamThumbRankHolder.mViewItemTeamThumbRankLine.setVisibility(View.VISIBLE);
        }
        if (i == 0) {
            teamThumbRankHolder.mTvItemTeamThumbRankDescribe.setText("第" + listBean.getRank() + "名");
        } else if (i == 1) {
            teamThumbRankHolder.mImgItemTeamThumbRankPosition.setBackgroundResource(R.drawable.icon_gold_medal);
        } else if (i == 2) {
            teamThumbRankHolder.mImgItemTeamThumbRankPosition.setBackgroundResource(R.drawable.icon_silver_medal);
        } else if(i==3){
            teamThumbRankHolder.mImgItemTeamThumbRankPosition.setBackgroundResource(R.drawable.icon_bronze_medal);
        }else{
            teamThumbRankHolder.mTvItemTeamThumbRankPosition.setText(i+"");
        }

        GlideUtils.getInstance().loadRoundImage(listBean.getLogo(), teamThumbRankHolder.mImgItemTeamThumbRankHead);
        teamThumbRankHolder.mTvItemTeamThumbRankName.setText(listBean.getName());
        teamThumbRankHolder.mTvItemTeamThumbRankCount.setText(listBean.getCount() + "");
    }

    @Override
    public int getItemCount() {
        return mdatas.size();
    }

    public class TeamThumbRankHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemTeamThumbRankPosition;
        private TextView mTvItemTeamThumbRankPosition;
        private ImageView mImgItemTeamThumbRankHead;
        private TextView mTvItemTeamThumbRankName;
        private TextView mTvItemTeamThumbRankDescribe;
        private TextView mTvItemTeamThumbRankCount;
        private View mViewItemTeamThumbRankLine;
        private View mViewItemTeamThumbRankSeparation;


        public TeamThumbRankHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemTeamThumbRankPosition = (ImageView) itemView.findViewById(R.id.img_item_team_thumb_rank_position);
            mTvItemTeamThumbRankPosition = (TextView) itemView.findViewById(R.id.tv_item_team_thumb_rank_position);
            mImgItemTeamThumbRankHead = (ImageView) itemView.findViewById(R.id.img_item_team_thumb_rank_head);
            mTvItemTeamThumbRankName = (TextView) itemView.findViewById(R.id.tv_item_team_thumb_rank_name);
            mTvItemTeamThumbRankDescribe = (TextView) itemView.findViewById(R.id.tv_item_team_thumb_rank_describe);
            mTvItemTeamThumbRankCount = (TextView) itemView.findViewById(R.id.tv_item_team_thumb_rank_count);
            mViewItemTeamThumbRankLine = (View) itemView.findViewById(R.id.view_item_team_thumb_rank_line);
            mViewItemTeamThumbRankSeparation = (View) itemView.findViewById(R.id.view_item_team_thumb_rank_separation);
        }
    }
}
