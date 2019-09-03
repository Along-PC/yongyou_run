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
import com.tourye.run.SaveConstants;
import com.tourye.run.bean.PersonThumbRankBean;
import com.tourye.run.bean.TeamThumbRankBean;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName:   TeamThumbRankAdapter
 *
 * @Author:   along
 *
 * @Description:    个人点赞排行适配器
 *
 * @CreateDate:   2019/4/1 3:28 PM
 *
 */
public class PersonThumbRankAdapter extends RecyclerView.Adapter<PersonThumbRankAdapter.PersonThumbRankHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<PersonThumbRankBean.DataBean.ListBean> mdatas=new ArrayList<>();

    public PersonThumbRankAdapter(Context context, List<PersonThumbRankBean.DataBean.ListBean> mdatas) {
        mContext = context;
        this.mdatas = mdatas;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setdatas(List<PersonThumbRankBean.DataBean.ListBean> mdatas) {
        this.mdatas = mdatas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PersonThumbRankHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PersonThumbRankHolder(mLayoutInflater.inflate(R.layout.item_person_thumb_rank,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PersonThumbRankHolder personThumbRankHolder, int i) {
        PersonThumbRankBean.DataBean.ListBean listBean = mdatas.get(i);

        boolean IS_JOINED = SaveUtil.getBoolean(SaveConstants.IS_JOINED, false);
        if (IS_JOINED) {
            bindToAll(personThumbRankHolder,listBean,i);
        }else{
            bindToNoOwn(personThumbRankHolder,listBean,i);
        }

        GlideUtils.getInstance().loadRoundImage(listBean.getAvatar(),personThumbRankHolder.mImgItemPersonThumbRankHead);
        personThumbRankHolder.mTvItemPersonThumbRankName.setText(listBean.getNickname());
        personThumbRankHolder.mTvItemPersonThumbRankCount.setText(listBean.getThumb_up_count()+"");
    }

    /**
     * 绑定包含自己的列表数据
     * @param personThumbRankHolder
     * @param listBean
     * @param i
     */
    private void bindToAll(PersonThumbRankHolder personThumbRankHolder, PersonThumbRankBean.DataBean.ListBean listBean, int i) {
        if (i==0) {
            personThumbRankHolder.mTvItemPersonThumbRankDescribe.setText("第"+listBean.getRank()+"名");
            personThumbRankHolder.mImgItemPersonThumbRankPosition.setVisibility(View.GONE);
            personThumbRankHolder.mTvItemPersonThumbRankPosition.setVisibility(View.GONE);
            personThumbRankHolder.mTvItemPersonThumbRankDescribe.setVisibility(View.VISIBLE);
            personThumbRankHolder.mViewItemPersonThumbRankSeparation.setVisibility(View.VISIBLE);
            personThumbRankHolder.mViewItemPersonThumbRankLine.setVisibility(View.GONE);
        }else if (i==1) {
            personThumbRankHolder.mImgItemPersonThumbRankPosition.setVisibility(View.VISIBLE);
            personThumbRankHolder.mTvItemPersonThumbRankPosition.setVisibility(View.GONE);
            personThumbRankHolder.mTvItemPersonThumbRankDescribe.setVisibility(View.GONE);
            personThumbRankHolder.mViewItemPersonThumbRankSeparation.setVisibility(View.GONE);
            personThumbRankHolder.mViewItemPersonThumbRankLine.setVisibility(View.VISIBLE);
            personThumbRankHolder.mImgItemPersonThumbRankPosition.setBackgroundResource(R.drawable.icon_gold_medal);
        }else if (i==2) {
            personThumbRankHolder.mImgItemPersonThumbRankPosition.setVisibility(View.VISIBLE);
            personThumbRankHolder.mTvItemPersonThumbRankPosition.setVisibility(View.GONE);
            personThumbRankHolder.mTvItemPersonThumbRankDescribe.setVisibility(View.GONE);
            personThumbRankHolder.mViewItemPersonThumbRankSeparation.setVisibility(View.GONE);
            personThumbRankHolder.mViewItemPersonThumbRankLine.setVisibility(View.VISIBLE);
            personThumbRankHolder.mImgItemPersonThumbRankPosition.setBackgroundResource(R.drawable.icon_silver_medal);
        }else if (i==3) {
            personThumbRankHolder.mImgItemPersonThumbRankPosition.setVisibility(View.VISIBLE);
            personThumbRankHolder.mTvItemPersonThumbRankPosition.setVisibility(View.GONE);
            personThumbRankHolder.mTvItemPersonThumbRankDescribe.setVisibility(View.GONE);
            personThumbRankHolder.mViewItemPersonThumbRankSeparation.setVisibility(View.GONE);
            personThumbRankHolder.mViewItemPersonThumbRankLine.setVisibility(View.VISIBLE);
            personThumbRankHolder.mImgItemPersonThumbRankPosition.setBackgroundResource(R.drawable.icon_bronze_medal);
        }else{
            personThumbRankHolder.mTvItemPersonThumbRankPosition.setText(i+"");
            personThumbRankHolder.mImgItemPersonThumbRankPosition.setVisibility(View.GONE);
            personThumbRankHolder.mTvItemPersonThumbRankPosition.setVisibility(View.VISIBLE);
            personThumbRankHolder.mTvItemPersonThumbRankDescribe.setVisibility(View.GONE);
            personThumbRankHolder.mViewItemPersonThumbRankSeparation.setVisibility(View.GONE);
            personThumbRankHolder.mViewItemPersonThumbRankLine.setVisibility(View.VISIBLE);
            personThumbRankHolder.mTvItemPersonThumbRankPosition.setText(i+"");
        }
    }

    /**
     * 绑定不包含自己的列表数据
     * @param personThumbRankHolder
     * @param listBean
     * @param i
     */
    private void bindToNoOwn(PersonThumbRankHolder personThumbRankHolder, PersonThumbRankBean.DataBean.ListBean listBean, int i) {
        if (i==0) {
            personThumbRankHolder.mImgItemPersonThumbRankPosition.setVisibility(View.VISIBLE);
            personThumbRankHolder.mTvItemPersonThumbRankPosition.setVisibility(View.GONE);
            personThumbRankHolder.mTvItemPersonThumbRankDescribe.setVisibility(View.GONE);
            personThumbRankHolder.mViewItemPersonThumbRankSeparation.setVisibility(View.GONE);
            personThumbRankHolder.mViewItemPersonThumbRankLine.setVisibility(View.VISIBLE);
            personThumbRankHolder.mImgItemPersonThumbRankPosition.setBackgroundResource(R.drawable.icon_gold_medal);
        }else if (i==1) {
            personThumbRankHolder.mImgItemPersonThumbRankPosition.setVisibility(View.VISIBLE);
            personThumbRankHolder.mTvItemPersonThumbRankPosition.setVisibility(View.GONE);
            personThumbRankHolder.mTvItemPersonThumbRankDescribe.setVisibility(View.GONE);
            personThumbRankHolder.mViewItemPersonThumbRankSeparation.setVisibility(View.GONE);
            personThumbRankHolder.mViewItemPersonThumbRankLine.setVisibility(View.VISIBLE);
            personThumbRankHolder.mImgItemPersonThumbRankPosition.setBackgroundResource(R.drawable.icon_silver_medal);
        }else if (i==2) {
            personThumbRankHolder.mImgItemPersonThumbRankPosition.setVisibility(View.VISIBLE);
            personThumbRankHolder.mTvItemPersonThumbRankPosition.setVisibility(View.GONE);
            personThumbRankHolder.mTvItemPersonThumbRankDescribe.setVisibility(View.GONE);
            personThumbRankHolder.mViewItemPersonThumbRankSeparation.setVisibility(View.GONE);
            personThumbRankHolder.mViewItemPersonThumbRankLine.setVisibility(View.VISIBLE);
            personThumbRankHolder.mImgItemPersonThumbRankPosition.setBackgroundResource(R.drawable.icon_bronze_medal);
        }else{
            personThumbRankHolder.mTvItemPersonThumbRankPosition.setText(i+"");
            personThumbRankHolder.mImgItemPersonThumbRankPosition.setVisibility(View.GONE);
            personThumbRankHolder.mTvItemPersonThumbRankPosition.setVisibility(View.VISIBLE);
            personThumbRankHolder.mTvItemPersonThumbRankDescribe.setVisibility(View.GONE);
            personThumbRankHolder.mViewItemPersonThumbRankSeparation.setVisibility(View.GONE);
            personThumbRankHolder.mViewItemPersonThumbRankLine.setVisibility(View.VISIBLE);
            personThumbRankHolder.mTvItemPersonThumbRankPosition.setText(i+"");
        }
    }

    @Override
    public int getItemCount() {
        return mdatas.size();
    }

    public class PersonThumbRankHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemPersonThumbRankPosition;
        private TextView mTvItemPersonThumbRankPosition;
        private ImageView mImgItemPersonThumbRankHead;
        private TextView mTvItemPersonThumbRankName;
        private TextView mTvItemPersonThumbRankDescribe;
        private TextView mTvItemPersonThumbRankCount;
        private View mViewItemPersonThumbRankLine;
        private View mViewItemPersonThumbRankSeparation;

        public PersonThumbRankHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemPersonThumbRankPosition = (ImageView) itemView.findViewById(R.id.img_item_person_thumb_rank_position);
            mTvItemPersonThumbRankPosition = (TextView) itemView.findViewById(R.id.tv_item_person_thumb_rank_position);
            mImgItemPersonThumbRankHead = (ImageView) itemView.findViewById(R.id.img_item_person_thumb_rank_head);
            mTvItemPersonThumbRankName = (TextView) itemView.findViewById(R.id.tv_item_person_thumb_rank_name);
            mTvItemPersonThumbRankDescribe = (TextView) itemView.findViewById(R.id.tv_item_person_thumb_rank_describe);
            mTvItemPersonThumbRankCount = (TextView) itemView.findViewById(R.id.tv_item_person_thumb_rank_count);
            mViewItemPersonThumbRankLine = (View) itemView.findViewById(R.id.view_item_person_thumb_rank_line);
            mViewItemPersonThumbRankSeparation = (View) itemView.findViewById(R.id.view_item_person_thumb_rank_separation);
        }
    }
}
