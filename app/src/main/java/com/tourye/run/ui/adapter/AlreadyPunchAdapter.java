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
import com.tourye.run.bean.TeamMemberPunchBean;
import com.tourye.run.utils.GlideUtils;

import java.util.List;

/**
 *
 * @ClassName:   AlreadyPunchAdapter
 *
 * @Author:   along
 *
 * @Description:
 *
 * @CreateDate:   2019/4/23 10:16 AM
 *
 */
public class AlreadyPunchAdapter extends RecyclerView.Adapter<AlreadyPunchAdapter.AlreadyPunchHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<TeamMemberPunchBean.DataBean> mDataBeans;

    public AlreadyPunchAdapter(Context context, List<TeamMemberPunchBean.DataBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setDataBeans(List<TeamMemberPunchBean.DataBean> dataBeans) {
        mDataBeans = dataBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlreadyPunchHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AlreadyPunchHolder(mLayoutInflater.inflate(R.layout.item_already_punch,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlreadyPunchHolder alreadyPunchHolder, int i) {
        TeamMemberPunchBean.DataBean dataBean = mDataBeans.get(i);
        GlideUtils.getInstance().loadCircleImage(dataBean.getAvatar(),alreadyPunchHolder.mImgItemAlreadyPunchHead);
        alreadyPunchHolder.mTvItemAlreadyPunchName.setText(dataBean.getNickname());
        alreadyPunchHolder.mTvItemAlreadyPunchInsist.setText("已坚持"+dataBean.getTotal_days()+"天");
        if ("normal".equalsIgnoreCase(dataBean.getStatus())) {
            alreadyPunchHolder.mTvItemAlreadyPunchDistance.setText("待审核");
        }else if ("accepted".equalsIgnoreCase(dataBean.getStatus())){
            alreadyPunchHolder.mTvItemAlreadyPunchDistance.setText(dataBean.getDistance()+"km");
        }
    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public class AlreadyPunchHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemAlreadyPunchHead;
        private TextView mTvItemAlreadyPunchName;
        private TextView mTvItemAlreadyPunchInsist;
        private TextView mTvItemAlreadyPunchDistance;

        public AlreadyPunchHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemAlreadyPunchHead = (ImageView) itemView.findViewById(R.id.img_item_already_punch_head);
            mTvItemAlreadyPunchName = (TextView) itemView.findViewById(R.id.tv_item_already_punch_name);
            mTvItemAlreadyPunchInsist = (TextView) itemView.findViewById(R.id.tv_item_already_punch_insist);
            mTvItemAlreadyPunchDistance = (TextView) itemView.findViewById(R.id.tv_item_already_punch_distance);
        }
    }
}
