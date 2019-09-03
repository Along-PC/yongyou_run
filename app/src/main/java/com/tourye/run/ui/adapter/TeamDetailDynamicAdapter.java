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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tourye.run.R;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.BattleDetailDynamicBean;
import com.tourye.run.ui.activities.common.ImageDetailActivity;
import com.tourye.run.utils.GlideCircleTransform;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.views.NineImageView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName:   TeamDetailDynamicAdapter
 *
 * @Author:   along
 *
 * @Description: 战队详情动态列表适配器
 *
 * @CreateDate:   2019/3/19 9:50 AM
 *
 */
public class TeamDetailDynamicAdapter extends RecyclerView.Adapter<TeamDetailDynamicAdapter.TeamDetailDynamicHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<BattleDetailDynamicBean.DataBean> mDataBeans=new ArrayList<>();

    public TeamDetailDynamicAdapter(Context context, List<BattleDetailDynamicBean.DataBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDataBeans(List<BattleDetailDynamicBean.DataBean> dataBeans) {
        mDataBeans = dataBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TeamDetailDynamicHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TeamDetailDynamicHolder(mLayoutInflater.inflate(R.layout.item_team_detail_dynamic,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeamDetailDynamicHolder teamDetailDynamicHolder, int i) {
        BattleDetailDynamicBean.DataBean dataBean = mDataBeans.get(i);
        GlideUtils.getInstance().loadCircleImage(dataBean.getAvatar(),teamDetailDynamicHolder.mImgTeamDetailDynamicHead);
        teamDetailDynamicHolder.mTvTeamDetailDynamicName.setText(dataBean.getNickname());
        if (!TextUtils.isEmpty(dataBean.getContent())) {
            teamDetailDynamicHolder.mTvTeamDetailDynamicContent.setVisibility(View.VISIBLE);
            teamDetailDynamicHolder.mTvTeamDetailDynamicContent.setText(dataBean.getContent());
        }else{
            teamDetailDynamicHolder.mTvTeamDetailDynamicContent.setVisibility(View.GONE);
        }
        List<BattleDetailDynamicBean.DataBean.ImagesBean> images = dataBean.getImages();
        final ArrayList<String> list=new ArrayList<>();
        for (int i1 = 0; i1 < images.size(); i1++) {
            BattleDetailDynamicBean.DataBean.ImagesBean imagesBean = images.get(i1);
            list.add(imagesBean.getUrl());
        }
        if (list.size()==0) {
            teamDetailDynamicHolder.mNineTeamDetailDynamicImages.setVisibility(View.GONE);
        }else{
            teamDetailDynamicHolder.mNineTeamDetailDynamicImages.setVisibility(View.VISIBLE);
            teamDetailDynamicHolder.mNineTeamDetailDynamicImages.setImagesData(list);
        }
        teamDetailDynamicHolder.mNineTeamDetailDynamicImages.setOnItemClickListener(new NineImageView.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                Intent intent = new Intent(mContext, ImageDetailActivity.class);
                intent.putExtra("data", list);
                intent.putExtra("pos",i);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public class TeamDetailDynamicHolder extends RecyclerView.ViewHolder {
        private ImageView mImgTeamDetailDynamicHead;
        private TextView mTvTeamDetailDynamicName;
        private TextView mTvTeamDetailDynamicContent;
        private NineImageView mNineTeamDetailDynamicImages;


        public TeamDetailDynamicHolder(@NonNull View itemView) {
            super(itemView);
            mImgTeamDetailDynamicHead = (ImageView) itemView.findViewById(R.id.img_team_detail_dynamic_head);
            mTvTeamDetailDynamicName = (TextView) itemView.findViewById(R.id.tv_team_detail_dynamic_name);
            mTvTeamDetailDynamicContent = (TextView) itemView.findViewById(R.id.tv_team_detail_dynamic_content);
            mNineTeamDetailDynamicImages = (NineImageView) itemView.findViewById(R.id.nine_team_detail_dynamic_images);

        }
    }
}
