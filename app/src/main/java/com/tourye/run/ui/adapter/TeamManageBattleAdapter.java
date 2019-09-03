package com.tourye.run.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.bean.MonitorTeamListbean;
import com.tourye.run.ui.activities.common.CommonWebActivity;
import com.tourye.run.ui.activities.home.TeamDetailActivity;
import com.tourye.run.ui.activities.mine.MultiTeamManageActivity;
import com.tourye.run.ui.activities.mine.TeamManageActivity;
import com.tourye.run.ui.dialogs.ShareDialog;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;

import java.util.List;

/**
 *
 * @ClassName:   TeamManageBattleAdapter
 *
 * @Author:   along
 *
 * @Description:    多只战队管理，战队列表适配器
 *
 * @CreateDate:   2019/4/16 4:27 PM
 *
 */
public class TeamManageBattleAdapter extends RecyclerView.Adapter<TeamManageBattleAdapter.TeamManageBattleHolder> {

    private List<MonitorTeamListbean.DataBean> mDataBeans;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public TeamManageBattleAdapter(List<MonitorTeamListbean.DataBean> dataBeans, Context context) {
        mDataBeans = dataBeans;
        mContext = context;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public TeamManageBattleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TeamManageBattleHolder(mLayoutInflater.inflate(R.layout.item_multi_team_manage_battle,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeamManageBattleHolder teamManageBattleHolder, int i) {
        final MonitorTeamListbean.DataBean dataBean = mDataBeans.get(i);
        GlideUtils.getInstance().loadRoundImage(dataBean.getLogo(),teamManageBattleHolder.mImgItemMultiTeamLogo);
        teamManageBattleHolder.mTvItemMultiTeamName.setText(dataBean.getName());
        String city = dataBean.getCity();
        if (TextUtils.isEmpty(city) || "null".equalsIgnoreCase(city)) {
            city="全国";
        }
        teamManageBattleHolder.mTvItemMultiTeamCity.setText(city);
        teamManageBattleHolder.mTvItemMultiTeamDistance.setText(dataBean.getDistance()+"km");
        teamManageBattleHolder.mTvItemMultiTeamCounts.setText(dataBean.getMember_count()+"人");
        teamManageBattleHolder.mTvItemMultiTeamHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,TeamDetailActivity.class);
                intent.putExtra("team_id",dataBean.getId()+"");
                mContext.startActivity(intent);
            }
        });
        teamManageBattleHolder.mTvItemMultiTeamInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inviteIntent = new Intent(mContext, CommonWebActivity.class);
                String inviteUrl = Constants.DOMAIN + "?env=gio"
                        + "#/team/invite_card/" + SaveUtil.getString(SaveConstants.ACTION_ID, "");
                inviteUrl = inviteUrl + "/" + dataBean.getId();
                inviteIntent.putExtra("url", inviteUrl);
                inviteIntent.putExtra("title", "邀请卡");
                mContext.startActivity(inviteIntent);
            }
        });
        teamManageBattleHolder.mRlItemMultiTeamContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teamManageIntent = new Intent(mContext, TeamManageActivity.class);
                teamManageIntent.putExtra("team_id",dataBean.getId()+"");
                mContext.startActivity(teamManageIntent);
            }
        });
        teamManageBattleHolder.mTvItemMultiTeamShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String share_first_title = SaveUtil.getString(SaveConstants.SHARE_FIRST_TITLE, "");
                String share_second_title = SaveUtil.getString(SaveConstants.SHARE_SECOND_TITLE, "");
                String user_name = SaveUtil.getString(SaveConstants.USER_NAME, "");
                if (!TextUtils.isEmpty(share_first_title)) {
                    share_first_title=share_first_title.replace("${user_name}",user_name);
                    if (dataBean!=null) {
                        share_first_title=share_first_title.replace("${team_name}",dataBean.getName());
                    }
                }
                String link = Constants.DOMAIN + "?env=gio"
                        +  "#/activity/submit/order/"+SaveUtil.getString(SaveConstants.ACTION_ID,"")+"/" + dataBean.getId();
                ShareDialog shareDialog = new ShareDialog(mContext);
                shareDialog.setData(share_first_title, share_second_title,
                        link, "https://static.run100.runorout.cn/meta/logo2.png");
                shareDialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public class TeamManageBattleHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemMultiTeamLogo;
        private TextView mTvItemMultiTeamName;
        private TextView mTvItemMultiTeamIntro;
        private TextView mTvItemMultiTeamHomepage;
        private TextView mTvItemMultiTeamShare;
        private TextView mTvItemMultiTeamInvite;
        private RelativeLayout mRlItemMultiTeamContent;
        private TextView mTvItemMultiTeamCity;
        private TextView mTvItemMultiTeamDistance;
        private TextView mTvItemMultiTeamCounts;


        public TeamManageBattleHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemMultiTeamLogo = (ImageView) itemView.findViewById(R.id.img_item_multi_team_logo);
            mTvItemMultiTeamName = (TextView) itemView.findViewById(R.id.tv_item_multi_team_name);
            mTvItemMultiTeamIntro = (TextView) itemView.findViewById(R.id.tv_item_multi_team_intro);
            mTvItemMultiTeamHomepage = (TextView) itemView.findViewById(R.id.tv_item_multi_team_homepage);
            mTvItemMultiTeamShare = (TextView) itemView.findViewById(R.id.tv_item_multi_team_share);
            mTvItemMultiTeamInvite = (TextView) itemView.findViewById(R.id.tv_item_multi_team_invite);
            mRlItemMultiTeamContent = (RelativeLayout) itemView.findViewById(R.id.rl_item_multi_team_content);
            mTvItemMultiTeamCity = (TextView) itemView.findViewById(R.id.tv_item_multi_team_city);
            mTvItemMultiTeamDistance = (TextView) itemView.findViewById(R.id.tv_item_multi_team_distance);
            mTvItemMultiTeamCounts = (TextView) itemView.findViewById(R.id.tv_item_multi_team_counts);

        }
    }
}
