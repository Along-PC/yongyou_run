package com.tourye.run.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tourye.run.R;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.BattleInfoBean;
import com.tourye.run.ui.activities.common.ImageDetailActivity;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.GlideRoundTransform;
import com.tourye.run.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName:   TeamDetailAlbumAdapter
 *
 * @Author:   along
 *
 * @Description:  战队详情相册适配器
 *
 * @CreateDate:   2019/3/18 5:36 PM
 *
 */
public class TeamDetailAlbumAdapter extends RecyclerView.Adapter<TeamDetailAlbumAdapter.TeamDetailAlbumHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<BattleInfoBean.DataBean.PhotosBean> mPhotosBeans=new ArrayList<>();

    public TeamDetailAlbumAdapter(Context context, List<BattleInfoBean.DataBean.PhotosBean> photosBeans) {
        mContext = context;
        mPhotosBeans = photosBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public TeamDetailAlbumHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TeamDetailAlbumHolder(mLayoutInflater.inflate(R.layout.item_team_detail_album,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeamDetailAlbumHolder teamDetailAlbumHolder, final int i) {
        BattleInfoBean.DataBean.PhotosBean photosBean = mPhotosBeans.get(i);
        RequestOptions requestOptions = new RequestOptions().transform(new GlideRoundTransform(BaseApplication.mApplicationContext,DensityUtils.dp2px(BaseApplication.mApplicationContext,4)));
        GlideUtils.getInstance().loadRoundImage(photosBean.getUrl(),teamDetailAlbumHolder.mImgItemTeamDetailAlbum,DensityUtils.dp2px(4));
//        Glide.with(BaseApplication.mApplicationContext).load(photosBean.getUrl()).apply(requestOptions).into(teamDetailAlbumHolder.mImgItemTeamDetailAlbum);
        teamDetailAlbumHolder.mImgItemTeamDetailAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> strings = new ArrayList<>();
                for (int i1 = 0; i1 < mPhotosBeans.size(); i1++) {
                    strings.add(mPhotosBeans.get(i1).getUrl());
                }
                Intent intent = new Intent(mContext, ImageDetailActivity.class);
                intent.putStringArrayListExtra("data",strings);
                intent.putExtra("pos",i);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPhotosBeans.size();
    }

    public class TeamDetailAlbumHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemTeamDetailAlbum;

        public TeamDetailAlbumHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemTeamDetailAlbum = (ImageView) itemView.findViewById(R.id.img_item_team_detail_album);

        }
    }

}
