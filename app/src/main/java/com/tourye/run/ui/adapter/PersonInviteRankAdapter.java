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
import com.tourye.run.bean.PersonInviteRankBean;
import com.tourye.run.utils.GlideUtils;

import java.util.List;

/**
 *
 * @ClassName:   PersonInviteRankAdapter
 *
 * @Author:   along
 *
 * @Description:    个人邀请排行榜适配器
 *
 * @CreateDate:   2019/4/2 10:06 AM
 *
 */
public class PersonInviteRankAdapter extends RecyclerView.Adapter<PersonInviteRankAdapter.PersonInviteRankHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<PersonInviteRankBean.DataBean.ListBean> mInviteBeans;

    public PersonInviteRankAdapter(Context context, List<PersonInviteRankBean.DataBean.ListBean> inviteBeans) {
        mContext = context;
        mInviteBeans = inviteBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setInviteBeans(List<PersonInviteRankBean.DataBean.ListBean> inviteBeans) {
        mInviteBeans = inviteBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PersonInviteRankHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PersonInviteRankHolder(mLayoutInflater.inflate(R.layout.item_person_invite_rank,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PersonInviteRankHolder personInviteRankHolder, int i) {
        PersonInviteRankBean.DataBean.ListBean listBean = mInviteBeans.get(i);
        if (i==0) {
            personInviteRankHolder.mTvItemPersonInviteRankPosition.setVisibility(View.GONE);
            personInviteRankHolder.mImgItemPersonInviteRankPosition.setVisibility(View.GONE);
            personInviteRankHolder.mTvItemPersonInviteRankDescribe.setVisibility(View.VISIBLE);
            personInviteRankHolder.mTvItemPersonInviteRankDescribe.setText(listBean.getRank());
            personInviteRankHolder.mViewItemPersonInviteRankSeparation.setVisibility(View.VISIBLE);
            personInviteRankHolder.mViewItemPersonInviteRankLine.setVisibility(View.GONE);
        }else if(i==1){
            personInviteRankHolder.mTvItemPersonInviteRankPosition.setVisibility(View.GONE);
            personInviteRankHolder.mImgItemPersonInviteRankPosition.setVisibility(View.VISIBLE);
            personInviteRankHolder.mTvItemPersonInviteRankDescribe.setVisibility(View.GONE);
            personInviteRankHolder.mViewItemPersonInviteRankSeparation.setVisibility(View.GONE);
            personInviteRankHolder.mViewItemPersonInviteRankLine.setVisibility(View.VISIBLE);
            personInviteRankHolder.mImgItemPersonInviteRankPosition.setBackgroundResource(R.drawable.icon_gold_medal);
        }else if(i==2){
            personInviteRankHolder.mTvItemPersonInviteRankPosition.setVisibility(View.GONE);
            personInviteRankHolder.mImgItemPersonInviteRankPosition.setVisibility(View.VISIBLE);
            personInviteRankHolder.mTvItemPersonInviteRankDescribe.setVisibility(View.GONE);
            personInviteRankHolder.mViewItemPersonInviteRankSeparation.setVisibility(View.GONE);
            personInviteRankHolder.mViewItemPersonInviteRankLine.setVisibility(View.VISIBLE);
            personInviteRankHolder.mImgItemPersonInviteRankPosition.setBackgroundResource(R.drawable.icon_silver_medal);
        }else if(i==3){
            personInviteRankHolder.mTvItemPersonInviteRankPosition.setVisibility(View.GONE);
            personInviteRankHolder.mImgItemPersonInviteRankPosition.setVisibility(View.VISIBLE);
            personInviteRankHolder.mTvItemPersonInviteRankDescribe.setVisibility(View.GONE);
            personInviteRankHolder.mViewItemPersonInviteRankSeparation.setVisibility(View.GONE);
            personInviteRankHolder.mViewItemPersonInviteRankLine.setVisibility(View.VISIBLE);
            personInviteRankHolder.mImgItemPersonInviteRankPosition.setBackgroundResource(R.drawable.icon_bronze_medal);
        }else{
            personInviteRankHolder.mTvItemPersonInviteRankPosition.setVisibility(View.VISIBLE);
            personInviteRankHolder.mImgItemPersonInviteRankPosition.setVisibility(View.GONE);
            personInviteRankHolder.mTvItemPersonInviteRankDescribe.setVisibility(View.GONE);
            personInviteRankHolder.mViewItemPersonInviteRankSeparation.setVisibility(View.GONE);
            personInviteRankHolder.mViewItemPersonInviteRankLine.setVisibility(View.VISIBLE);
            personInviteRankHolder.mTvItemPersonInviteRankPosition.setText(i+"");
        }
        personInviteRankHolder.mTvItemPersonInviteRankName.setText(listBean.getNickname());
        GlideUtils.getInstance().loadRoundImage(listBean.getAvatar(),personInviteRankHolder.mImgItemPersonInviteRankHead);
        personInviteRankHolder.mTvItemPersonInviteRankCount.setText(listBean.getReferrer_count()+"人");
    }

    @Override
    public int getItemCount() {
        return mInviteBeans.size();
    }

    public class PersonInviteRankHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemPersonInviteRankPosition;
        private TextView mTvItemPersonInviteRankPosition;
        private ImageView mImgItemPersonInviteRankHead;
        private TextView mTvItemPersonInviteRankName;
        private TextView mTvItemPersonInviteRankDescribe;
        private TextView mTvItemPersonInviteRankCount;
        private View mViewItemPersonInviteRankLine;
        private View mViewItemPersonInviteRankSeparation;


        public PersonInviteRankHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemPersonInviteRankPosition = (ImageView) itemView.findViewById(R.id.img_item_person_invite_rank_position);
            mTvItemPersonInviteRankPosition = (TextView) itemView.findViewById(R.id.tv_item_person_invite_rank_position);
            mImgItemPersonInviteRankHead = (ImageView) itemView.findViewById(R.id.img_item_person_invite_rank_head);
            mTvItemPersonInviteRankName = (TextView) itemView.findViewById(R.id.tv_item_person_invite_rank_name);
            mTvItemPersonInviteRankDescribe = (TextView) itemView.findViewById(R.id.tv_item_person_invite_rank_describe);
            mTvItemPersonInviteRankCount = (TextView) itemView.findViewById(R.id.tv_item_person_invite_rank_count);
            mViewItemPersonInviteRankLine = (View) itemView.findViewById(R.id.view_item_person_invite_rank_line);
            mViewItemPersonInviteRankSeparation = (View) itemView.findViewById(R.id.view_item_person_invite_rank_separation);
        }

    }
}
