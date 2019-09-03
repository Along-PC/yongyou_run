package com.tourye.run.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import com.tourye.run.utils.GlideUtils;

import java.util.List;

/**
 *
 * @ClassName:   NotPunchAdapter
 *
 * @Author:   along
 *
 * @Description:    队员没有打卡的列表适配器
 *
 * @CreateDate:   2019/4/23 9:56 AM
 *
 */
public class NotPunchAdapter extends RecyclerView.Adapter<NotPunchAdapter.NotPunchHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<TeamMemberPunchBean.DataBean> mDataBeans;

    public NotPunchAdapter(Context context, List<TeamMemberPunchBean.DataBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
        mContext = context;
        mDataBeans = dataBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public NotPunchHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NotPunchHolder(mLayoutInflater.inflate(R.layout.item_not_punch,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotPunchHolder notPunchHolder, int i) {
        final TeamMemberPunchBean.DataBean dataBean = mDataBeans.get(i);
        GlideUtils.getInstance().loadCircleImage(dataBean.getAvatar(),notPunchHolder.mImgItemNotPunchHead);
        notPunchHolder.mTvItemNotPunchName.setText(dataBean.getNickname());
        if ("no_sign_in".equalsIgnoreCase(dataBean.getStatus())) {
            notPunchHolder.mTvItemNotPunchStatus.setText("未开始打卡");
            notPunchHolder.mTvItemNotPunchStatus.setTextColor(Color.parseColor("#FF666666"));
        }else{
            notPunchHolder.mTvItemNotPunchStatus.setText("审核未通过");
            notPunchHolder.mTvItemNotPunchStatus.setTextColor(Color.parseColor("#FFFF1D1D"));
        }

        notPunchHolder.mRlItemNotPunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent =  new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + dataBean.getMobile()));//跳转到拨号界面，同时传递电话号码
                mContext.startActivity(dialIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public class NotPunchHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemNotPunchHead;
        private TextView mTvItemNotPunchName;
        private TextView mTvItemNotPunchCall;
        private ImageView mImgItemNotPunchArrow;
        private TextView mTvItemNotPunchStatus;
        private RelativeLayout mRlItemNotPunch;



        public NotPunchHolder(@NonNull View itemView) {
            super(itemView);
            mRlItemNotPunch = (RelativeLayout) itemView.findViewById(R.id.rl_item_not_punch);
            mImgItemNotPunchHead = (ImageView) itemView.findViewById(R.id.img_item_not_punch_head);
            mTvItemNotPunchName = (TextView) itemView.findViewById(R.id.tv_item_not_punch_name);
            mTvItemNotPunchCall = (TextView) itemView.findViewById(R.id.tv_item_not_punch_call);
            mImgItemNotPunchArrow = (ImageView) itemView.findViewById(R.id.img_item_not_punch_arrow);
            mTvItemNotPunchStatus = (TextView) itemView.findViewById(R.id.tv_item_not_punch_status);

        }
    }
}
