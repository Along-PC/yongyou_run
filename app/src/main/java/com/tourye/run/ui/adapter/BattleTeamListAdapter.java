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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.bean.BattleTeamListBean;
import com.tourye.run.bean.TeamBasicInfoBean;
import com.tourye.run.ui.activities.home.SignupActivity;
import com.tourye.run.ui.activities.home.TeamDetailActivity;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @ClassName:   BattleTeamListAdapter
 *
 * @Author:   along
 *
 * @Description:战队列表适配器
 *
 * @CreateDate:   2019/3/15 4:26 PM
 *
 */
public class BattleTeamListAdapter extends RecyclerView.Adapter<BattleTeamListAdapter.BattleTeamListHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<TeamBasicInfoBean> mBattleTeamListBeans=new ArrayList<>();

    public BattleTeamListAdapter(Context context, List<TeamBasicInfoBean> battleTeamListBeans) {
        mContext = context;
        mBattleTeamListBeans = battleTeamListBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setBattleTeamListBeans(List<TeamBasicInfoBean> battleTeamListBeans) {
        mBattleTeamListBeans = battleTeamListBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BattleTeamListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BattleTeamListHolder(mLayoutInflater.inflate(R.layout.item_signup_battle_team_list,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BattleTeamListHolder battleTeamListHolder, int i) {
        final TeamBasicInfoBean dataBean = mBattleTeamListBeans.get(i);
        GlideUtils.getInstance().loadRoundImage(dataBean.getLogo(),battleTeamListHolder.mImgItemBattleTeamList);
        battleTeamListHolder.mTvItemBattleTeamListName.setText(dataBean.getName());
        battleTeamListHolder.mTvItemBattleTeamListCount.setText(dataBean.getMember_count()+"人");
        battleTeamListHolder.mTvItemBattleTeamListDistance.setText(dataBean.getDistance()+"km");
        battleTeamListHolder.mTvItemBattleTeamListIntro.setText("队长: "+dataBean.getMonitor());
        battleTeamListHolder.mTvItemBattleTeamListPosition.setText(dataBean.getCity()==null?"全国":dataBean.getCity());
        battleTeamListHolder.mImgItemBattleTeamListVerify.setVisibility(dataBean.isVerified()?View.VISIBLE:View.GONE);
        boolean is_sign_up_finish=false;//报名是否截止
        String sign_up_finish_date = SaveUtil.getString(SaveConstants.SIGN_UP_FINISH_DATE, "");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (!TextUtils.isEmpty(sign_up_finish_date)) {
            try {
                Date parse = simpleDateFormat.parse(sign_up_finish_date);
                Calendar instance = Calendar.getInstance();
                Date current = instance.getTime();
                instance.setTime(parse);
                instance.add(Calendar.DAY_OF_MONTH,1);
                parse=instance.getTime();
                if (parse.before(current)) {
                    is_sign_up_finish=true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (SaveUtil.getBoolean("is_joined",false)) {
            if (dataBean.getMember_count()>=100) {
                battleTeamListHolder.mTvItemBattleTeamListSignup.setText("战队已满");
                battleTeamListHolder.mTvItemBattleTeamListSignup.setSelected(true);
                battleTeamListHolder.mTvItemBattleTeamListSignup.setOnClickListener(null);
            }else{
                if (is_sign_up_finish) {
                    battleTeamListHolder.mTvItemBattleTeamListSignup.setText("报名截止");
                    battleTeamListHolder.mTvItemBattleTeamListSignup.setSelected(true);
                    battleTeamListHolder.mTvItemBattleTeamListSignup.setOnClickListener(null);
                }else{
                    battleTeamListHolder.mTvItemBattleTeamListSignup.setText("战队详情");
                    battleTeamListHolder.mTvItemBattleTeamListSignup.setSelected(false);
                    battleTeamListHolder.mTvItemBattleTeamListSignup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, TeamDetailActivity.class);
                            intent.putExtra("team_id",dataBean.getId()+"");
                            mContext.startActivity(intent);
                        }
                    });
                }

            }
        }else{
            if (dataBean.getMember_count()>=100) {
                battleTeamListHolder.mTvItemBattleTeamListSignup.setText("战队已满");
                battleTeamListHolder.mTvItemBattleTeamListSignup.setSelected(true);
                battleTeamListHolder.mTvItemBattleTeamListSignup.setOnClickListener(null);
            }else{
                if (is_sign_up_finish) {
                    battleTeamListHolder.mTvItemBattleTeamListSignup.setText("报名截止");
                    battleTeamListHolder.mTvItemBattleTeamListSignup.setSelected(true);
                    battleTeamListHolder.mTvItemBattleTeamListSignup.setOnClickListener(null);
                }else{
                    battleTeamListHolder.mTvItemBattleTeamListSignup.setText("报名");
                    battleTeamListHolder.mTvItemBattleTeamListSignup.setSelected(false);
                    battleTeamListHolder.mTvItemBattleTeamListSignup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, SignupActivity.class);
                            intent.putExtra("team_id",dataBean.getId()+"");
                            mContext.startActivity(intent);
                        }
                    });
                }
            }

        }
        battleTeamListHolder.mRlItemBattleTeamList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TeamDetailActivity.class);
                intent.putExtra("team_id",dataBean.getId()+"");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBattleTeamListBeans.size();
    }

    public class BattleTeamListHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemBattleTeamList;
        private TextView mTvItemBattleTeamListName;
        private LinearLayout mLlItemBattleTeamList;
        private TextView mTvItemBattleTeamListPosition;
        private TextView mTvItemBattleTeamListDistance;
        private TextView mTvItemBattleTeamListCount;
        private TextView mTvItemBattleTeamListIntro;
        private TextView mTvItemBattleTeamListSignup;
        private RelativeLayout mRlItemBattleTeamList;
        private ImageView mImgItemBattleTeamListVerify;

        public BattleTeamListHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemBattleTeamList = (ImageView) itemView.findViewById(R.id.img_item_battle_team_list);
            mTvItemBattleTeamListName = (TextView) itemView.findViewById(R.id.tv_item_battle_team_list_name);
            mLlItemBattleTeamList = (LinearLayout) itemView.findViewById(R.id.ll_item_battle_team_list);
            mTvItemBattleTeamListPosition = (TextView) itemView.findViewById(R.id.tv_item_battle_team_list_position);
            mTvItemBattleTeamListDistance = (TextView) itemView.findViewById(R.id.tv_item_battle_team_list_distance);
            mTvItemBattleTeamListCount = (TextView) itemView.findViewById(R.id.tv_item_battle_team_list_count);
            mTvItemBattleTeamListIntro = (TextView) itemView.findViewById(R.id.tv_item_battle_team_list_intro);
            mTvItemBattleTeamListSignup = (TextView) itemView.findViewById(R.id.tv_item_battle_team_list_signup);
            mRlItemBattleTeamList = (RelativeLayout) itemView.findViewById(R.id.rl_item_battle_team_list);
            mImgItemBattleTeamListVerify = (ImageView) itemView.findViewById(R.id.img_item_battle_team_list_verify);

        }
    }
}
