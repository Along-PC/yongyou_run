package com.tourye.run.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tourye.run.R;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.BattleMemberBean;
import com.tourye.run.utils.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName:   TeamMemberAdapter
 *
 * @Author:   along
 *
 * @Description: 战队成员列表适配器
 *
 * @CreateDate:   2019/3/18 5:04 PM
 *
 */
public class TeamMemberAdapter extends RecyclerView.Adapter<TeamMemberAdapter.TeamMemberHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<BattleMemberBean.DataBean> mDataBeans=new ArrayList<>();
    private boolean isSameTeam =false;

    public boolean isSameTeam() {
        return isSameTeam;
    }

    public void setSameTeam(boolean sameTeam) {
        isSameTeam = sameTeam;
    }

    public TeamMemberAdapter(Context context) {
        mContext = context;
    }

    public TeamMemberAdapter(Context context, List<BattleMemberBean.DataBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public TeamMemberHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TeamMemberHolder(mLayoutInflater.inflate(R.layout.item_activity_team_member,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeamMemberHolder teamMemberHolder, int i) {
        BattleMemberBean.DataBean dataBean = mDataBeans.get(i);
        teamMemberHolder.mTvItemTeamMemberName.setText(dataBean.getNickname());
        RequestOptions requestOptions = new RequestOptions().transform(new GlideCircleTransform(BaseApplication.mApplicationContext));
        Glide.with(BaseApplication.mApplicationContext).load(dataBean.getAvatar()).apply(requestOptions).into(teamMemberHolder.mImgItemTeamMemberHead);
        if (isSameTeam) {
            teamMemberHolder.mTvItemTeamMemberState.setVisibility(View.GONE);
            teamMemberHolder.mTvItemTeamMemberState.setText(dataBean.isAlready_sign_in()?"已打卡":"未打卡");
        }else{
            teamMemberHolder.mTvItemTeamMemberState.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public class TeamMemberHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemTeamMemberHead;
        private TextView mTvItemTeamMemberName;
        private TextView mTvItemTeamMemberState;

        public TeamMemberHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemTeamMemberHead = (ImageView) itemView.findViewById(R.id.img_item_team_member_head);
            mTvItemTeamMemberName = (TextView) itemView.findViewById(R.id.tv_item_team_member_name);
            mTvItemTeamMemberState = (TextView) itemView.findViewById(R.id.tv_item_team_member_state);

        }
    }
}
