package com.tourye.run.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.TeamMemberPunchBean;
import com.tourye.run.ui.activities.mine.PunchCalendarActivity;
import com.tourye.run.utils.GlideUtils;

import java.util.List;

/**
 *
 * @ClassName:   AllPunchAdapter
 *
 * @Author:   along
 *
 * @Description:    队员所有打卡情况适配器
 *
 * @CreateDate:   2019/4/23 9:28 AM
 *
 */
public class AllPunchAdapter extends RecyclerView.Adapter<AllPunchAdapter.AllPunchHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<TeamMemberPunchBean.DataBean> mDataBeans;

    private String mTeam_id;
    private String mDate;

    public AllPunchAdapter(Context context, List<TeamMemberPunchBean.DataBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(String team_id,String date) {
        mTeam_id = team_id;
        mDate=date;
    }

    @NonNull
    @Override
    public AllPunchHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AllPunchHolder(mLayoutInflater.inflate(R.layout.item_all_punch,viewGroup,false));
    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    @Override
    public void onBindViewHolder(@NonNull AllPunchHolder allPunchHolder, int i) {
        final TeamMemberPunchBean.DataBean dataBean = mDataBeans.get(i);
        GlideUtils.getInstance().loadCircleImage(dataBean.getAvatar(),allPunchHolder.mImgItemAllPunchHead);
        allPunchHolder.mTvItemAllPunchName.setText(dataBean.getNickname());
        allPunchHolder.mTvItemAllPunchInsist.setText("已坚持"+dataBean.getTotal_days()+"天");
        String status = dataBean.getStatus();
        if ("no_sign_in".equalsIgnoreCase(status)) {
            allPunchHolder.mTvItemAllPunchDistance.setText("未开始打卡");
        }else if("rejected".equalsIgnoreCase(status)){
            allPunchHolder.mTvItemAllPunchDistance.setText("审核不通过");
        }else{
            allPunchHolder.mTvItemAllPunchDistance.setText(dataBean.getDistance()+"km");
        }
        allPunchHolder.mRlItemAllPunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PunchCalendarActivity.class);
                intent.putExtra("team_id",mTeam_id);
                intent.putExtra("user_id",dataBean.getUser_id()+"");
                intent.putExtra("month",mDate);
                mContext.startActivity(intent);
            }
        });
    }

    public class AllPunchHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemAllPunchHead;
        private TextView mTvItemAllPunchName;
        private TextView mTvItemAllPunchInsist;
        private TextView mTvItemAllPunchDistance;
        private ImageView mImgItemAllPunchArrow;
        private RelativeLayout mRlItemAllPunch;


        public AllPunchHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemAllPunchHead = (ImageView) itemView.findViewById(R.id.img_item_all_punch_head);
            mTvItemAllPunchName = (TextView) itemView.findViewById(R.id.tv_item_all_punch_name);
            mTvItemAllPunchInsist = (TextView) itemView.findViewById(R.id.tv_item_all_punch_insist);
            mTvItemAllPunchDistance = (TextView) itemView.findViewById(R.id.tv_item_all_punch_distance);
            mImgItemAllPunchArrow = (ImageView) itemView.findViewById(R.id.img_item_all_punch_arrow);
            mRlItemAllPunch = (RelativeLayout) itemView.findViewById(R.id.rl_item_all_punch);

        }
    }
}
