package com.tourye.run.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.InfluencePeopleBean;
import com.tourye.run.utils.GlideUtils;

import java.util.List;

/**
 *
 * @ClassName:   InfluencePeopleAdapter
 *
 * @Author:   along
 *
 * @Description:    影响人数适配器
 *
 * @CreateDate:   2019/4/19 5:51 PM
 *
 */
public class InfluencePeopleAdapter extends RecyclerView.Adapter<InfluencePeopleAdapter.InfluencePeopleHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<InfluencePeopleBean.DataBean> mDataBeans;

    public InfluencePeopleAdapter(Context context) {
        mContext = context;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDataBeans(List<InfluencePeopleBean.DataBean> dataBeans) {
        mDataBeans = dataBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InfluencePeopleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new InfluencePeopleHolder(mLayoutInflater.inflate(R.layout.item_fragment_influence_people,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InfluencePeopleHolder influencePeopleHolder, int i) {
        final InfluencePeopleBean.DataBean dataBean = mDataBeans.get(i);
        GlideUtils.getInstance().loadCircleImage(dataBean.getAvatar(),influencePeopleHolder.mImgItemFragmentInfluencePeopleHead);
        influencePeopleHolder.mTvItemFragmentInfluencePeopleName.setText(dataBean.getNickname());
        if (TextUtils.isEmpty(dataBean.getMobile())) {
            influencePeopleHolder.mImgItemFragmentInfluencePeoplePhone.setVisibility(View.GONE);
            influencePeopleHolder.mImgItemFragmentInfluencePeoplePhone.setOnClickListener(null);
        }else{
            influencePeopleHolder.mImgItemFragmentInfluencePeoplePhone.setVisibility(View.VISIBLE);
            influencePeopleHolder.mImgItemFragmentInfluencePeoplePhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent dialIntent =  new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + dataBean.getMobile()));//跳转到拨号界面，同时传递电话号码
                    mContext.startActivity(dialIntent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public class InfluencePeopleHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemFragmentInfluencePeopleHead;
        private TextView mTvItemFragmentInfluencePeopleName;
        private ImageView mImgItemFragmentInfluencePeoplePhone;

        public InfluencePeopleHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemFragmentInfluencePeopleHead = (ImageView) itemView.findViewById(R.id.img_item_fragment_influence_people_head);
            mTvItemFragmentInfluencePeopleName = (TextView) itemView.findViewById(R.id.tv_item_fragment_influence_people_name);
            mImgItemFragmentInfluencePeoplePhone = (ImageView) itemView.findViewById(R.id.img_item_fragment_influence_people_phone);

        }
    }
}
