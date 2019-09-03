package com.tourye.run.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.TeamInfluenceBean;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName:   TeamManageInfluenceAdapter
 *
 * @Author:   along
 *
 * @Description:  队伍管理影响人数适配器
 *
 * @CreateDate:   2019/3/26 1:33 PM
 *
 */
public class TeamManageInfluenceAdapter extends RecyclerView.Adapter<TeamManageInfluenceAdapter.TeamManageInfluenceHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<TeamInfluenceBean.DataBean.ListBean> mListBeans=new ArrayList<>();

    public TeamManageInfluenceAdapter(Context context, List<TeamInfluenceBean.DataBean.ListBean> listBeans) {
        mContext = context;
        mListBeans = listBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public TeamManageInfluenceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TeamManageInfluenceHolder(mLayoutInflater.inflate(R.layout.item_team_manage_influence,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeamManageInfluenceHolder teamManageInfluenceHolder, int i) {
        TeamInfluenceBean.DataBean.ListBean listBean = mListBeans.get(i);
        GlideUtils.getInstance().loadCircleImage(listBean.getAvatar(),teamManageInfluenceHolder.mImgItemTeamManageInfluenceHead);
        teamManageInfluenceHolder.mTvItemTeamManageInfluenceName.setText(listBean.getNickname());
        if (listBean.isJoined()) {
            teamManageInfluenceHolder.mTvItemTeamManageInfluenceSignup.setVisibility(View.VISIBLE);
        }else{
            teamManageInfluenceHolder.mTvItemTeamManageInfluenceSignup.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mListBeans.size();
    }

    public class TeamManageInfluenceHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemTeamManageInfluenceHead;
        private TextView mTvItemTeamManageInfluenceName;
        private TextView mTvItemTeamManageInfluenceSignup;


        public TeamManageInfluenceHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemTeamManageInfluenceHead = (ImageView) itemView.findViewById(R.id.img_item_team_manage_influence_head);
            mTvItemTeamManageInfluenceName = (TextView) itemView.findViewById(R.id.tv_item_team_manage_influence_name);
            mTvItemTeamManageInfluenceSignup = (TextView) itemView.findViewById(R.id.tv_item_team_manage_influence_signup);
        }
    }
}
