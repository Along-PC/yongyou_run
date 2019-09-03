package com.tourye.run.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.CommonStatusBean;
import com.tourye.run.bean.DistanceRankingBean;
import com.tourye.run.bean.TodayDistanceRankBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.home.TeamRankingActivity;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DistanceRankingAdapter
 * @Author: along
 * @Description: 战队距离排行榜适配器
 * @CreateDate: 2019/3/20 2:29 PM
 */
public class DistanceRankingAdapter extends RecyclerView.Adapter<DistanceRankingAdapter.DistanceRankingHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<DistanceRankingBean.DataBean.ListBean> mListBeans = new ArrayList<>();
    private boolean isCompleteRank;//是否是完整排行榜
    private boolean isSameTeam;//是否是同战队

    public DistanceRankingAdapter(Context context, List<DistanceRankingBean.DataBean.ListBean> listBeans) {
        mContext = context;
        mListBeans = listBeans;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setCompleteRank(boolean completeRank) {
        isCompleteRank = completeRank;
    }

    public void setSameTeam(boolean sameTeam) {
        isSameTeam = sameTeam;
    }

    public void setListBeans(List<DistanceRankingBean.DataBean.ListBean> listBeans) {
        mListBeans = listBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DistanceRankingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DistanceRankingHolder(mLayoutInflater.inflate(R.layout.item_team_distance_rank, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final DistanceRankingHolder distanceRankingHolder, int i) {
        final DistanceRankingBean.DataBean.ListBean listBean = mListBeans.get(i);
        if (!isCompleteRank) {
            changeToUnCompleteRank(distanceRankingHolder, i, listBean);
        } else {
            changeToCompleteRank(distanceRankingHolder, i, listBean);
        }

        GlideUtils.getInstance().loadRoundImage(listBean.getAvatar(), distanceRankingHolder.mImgItemDistanceRankingHead);
        distanceRankingHolder.mTvItemDistanceRankingName.setText(listBean.getNickname());
        distanceRankingHolder.mTvItemDistanceRankingDistance.setText(listBean.getTotal_distance() + "KM");
        distanceRankingHolder.mTvItemDistanceRankingThumbCount.setText(listBean.getThumb_up_count() + "");
        if (listBean.isHas_thumb_up()) {
            distanceRankingHolder.mTvItemDistanceRankingThumbCount.setTextColor(mContext.getResources().getColor(R.color.color_red));
        }else{
            distanceRankingHolder.mTvItemDistanceRankingThumbCount.setTextColor(mContext.getResources().getColor(R.color.color_font_gray_lite));
        }
        distanceRankingHolder.mImgItemDistanceRankingThumb.setSelected(listBean.isHas_thumb_up());
        distanceRankingHolder.mImgItemDistanceRankingThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    thumbCancel(distanceRankingHolder, listBean);
                } else {
                    thumb(distanceRankingHolder, listBean);
                }
            }
        });

    }

    /**
     * 切换至非完整榜单
     *
     * @param distanceRankingHolder
     * @param i
     * @param listBean
     */
    private void changeToUnCompleteRank(DistanceRankingHolder distanceRankingHolder, int i, DistanceRankingBean.DataBean.ListBean listBean) {
        distanceRankingHolder.mViewItemDistanceRankingDivider.setVisibility(View.GONE);
        distanceRankingHolder.mViewItemDistanceRankingLine.setVisibility(View.VISIBLE);
        int rank = listBean.getRank();
        //如果是同战队
        if (isSameTeam) {
            if (i == 0) {
                distanceRankingHolder.mTvItemDistanceRankingPos.setVisibility(View.VISIBLE);
                distanceRankingHolder.mTvItemDistanceRankingPos.setText("第" + listBean.getRank() + "名");
                distanceRankingHolder.mTvItemDistanceRankingPosition.setVisibility(View.GONE);
                distanceRankingHolder.mImgItemDistanceRankingPosition.setVisibility(View.GONE);
            } else{
                if (rank == 1) {
                    distanceRankingHolder.mTvItemDistanceRankingPos.setVisibility(View.GONE);
                    distanceRankingHolder.mTvItemDistanceRankingPosition.setVisibility(View.GONE);
                    distanceRankingHolder.mImgItemDistanceRankingPosition.setVisibility(View.VISIBLE);
                    distanceRankingHolder.mImgItemDistanceRankingPosition.setBackgroundResource(R.drawable.icon_gold_medal);
                } else if (rank == 2) {
                    distanceRankingHolder.mTvItemDistanceRankingPos.setVisibility(View.GONE);
                    distanceRankingHolder.mTvItemDistanceRankingPosition.setVisibility(View.GONE);
                    distanceRankingHolder.mImgItemDistanceRankingPosition.setVisibility(View.VISIBLE);
                    distanceRankingHolder.mImgItemDistanceRankingPosition.setBackgroundResource(R.drawable.icon_silver_medal);
                } else if (rank == 3) {
                    distanceRankingHolder.mTvItemDistanceRankingPos.setVisibility(View.GONE);
                    distanceRankingHolder.mTvItemDistanceRankingPosition.setVisibility(View.GONE);
                    distanceRankingHolder.mImgItemDistanceRankingPosition.setVisibility(View.VISIBLE);
                    distanceRankingHolder.mImgItemDistanceRankingPosition.setBackgroundResource(R.drawable.icon_bronze_medal);
                } else {
                    distanceRankingHolder.mTvItemDistanceRankingPos.setVisibility(View.GONE);
                    distanceRankingHolder.mTvItemDistanceRankingPosition.setVisibility(View.VISIBLE);
                    distanceRankingHolder.mImgItemDistanceRankingPosition.setVisibility(View.GONE);
                    distanceRankingHolder.mTvItemDistanceRankingPosition.setText(rank + "");
                }
            }
        } else {
            if (rank == 1) {
                distanceRankingHolder.mTvItemDistanceRankingPos.setVisibility(View.GONE);
                distanceRankingHolder.mTvItemDistanceRankingPosition.setVisibility(View.GONE);
                distanceRankingHolder.mImgItemDistanceRankingPosition.setVisibility(View.VISIBLE);
                distanceRankingHolder.mImgItemDistanceRankingPosition.setBackgroundResource(R.drawable.icon_gold_medal);
            } else if (rank == 2) {
                distanceRankingHolder.mTvItemDistanceRankingPos.setVisibility(View.GONE);
                distanceRankingHolder.mTvItemDistanceRankingPosition.setVisibility(View.GONE);
                distanceRankingHolder.mImgItemDistanceRankingPosition.setVisibility(View.VISIBLE);
                distanceRankingHolder.mImgItemDistanceRankingPosition.setBackgroundResource(R.drawable.icon_silver_medal);
            } else if (rank == 3) {
                distanceRankingHolder.mTvItemDistanceRankingPos.setVisibility(View.GONE);
                distanceRankingHolder.mTvItemDistanceRankingPosition.setVisibility(View.GONE);
                distanceRankingHolder.mImgItemDistanceRankingPosition.setVisibility(View.VISIBLE);
                distanceRankingHolder.mImgItemDistanceRankingPosition.setBackgroundResource(R.drawable.icon_bronze_medal);
            } else {
                distanceRankingHolder.mTvItemDistanceRankingPos.setVisibility(View.GONE);
                distanceRankingHolder.mTvItemDistanceRankingPosition.setVisibility(View.VISIBLE);
                distanceRankingHolder.mImgItemDistanceRankingPosition.setVisibility(View.GONE);
                distanceRankingHolder.mTvItemDistanceRankingPosition.setText(rank + "");
            }

        }
    }

    /**
     * 切换完整榜单
     *
     * @param distanceRankingHolder
     * @param i
     * @param listBean
     */
    private void changeToCompleteRank(DistanceRankingHolder distanceRankingHolder, int i, DistanceRankingBean.DataBean.ListBean listBean) {
        int rank = listBean.getRank();
        //如果是同战队
        if (isSameTeam) {
            if (i == 0) {
                distanceRankingHolder.mViewItemDistanceRankingDivider.setVisibility(View.VISIBLE);
                distanceRankingHolder.mViewItemDistanceRankingLine.setVisibility(View.GONE);
                distanceRankingHolder.mTvItemDistanceRankingPos.setVisibility(View.VISIBLE);
                distanceRankingHolder.mTvItemDistanceRankingPos.setText("第" + listBean.getRank() + "名");
                distanceRankingHolder.mTvItemDistanceRankingPosition.setVisibility(View.GONE);
                distanceRankingHolder.mImgItemDistanceRankingPosition.setVisibility(View.GONE);
            } else {
                if (rank == 1) {
                    distanceRankingHolder.mViewItemDistanceRankingDivider.setVisibility(View.GONE);
                    distanceRankingHolder.mViewItemDistanceRankingLine.setVisibility(View.VISIBLE);
                    distanceRankingHolder.mTvItemDistanceRankingPos.setVisibility(View.GONE);
                    distanceRankingHolder.mTvItemDistanceRankingPosition.setVisibility(View.GONE);
                    distanceRankingHolder.mImgItemDistanceRankingPosition.setVisibility(View.VISIBLE);
                    distanceRankingHolder.mImgItemDistanceRankingPosition.setBackgroundResource(R.drawable.icon_gold_medal);
                } else if (rank == 2) {
                    distanceRankingHolder.mViewItemDistanceRankingDivider.setVisibility(View.GONE);
                    distanceRankingHolder.mViewItemDistanceRankingLine.setVisibility(View.VISIBLE);
                    distanceRankingHolder.mTvItemDistanceRankingPos.setVisibility(View.GONE);
                    distanceRankingHolder.mTvItemDistanceRankingPosition.setVisibility(View.GONE);
                    distanceRankingHolder.mImgItemDistanceRankingPosition.setVisibility(View.VISIBLE);
                    distanceRankingHolder.mImgItemDistanceRankingPosition.setBackgroundResource(R.drawable.icon_silver_medal);
                } else if (rank == 3) {
                    distanceRankingHolder.mViewItemDistanceRankingDivider.setVisibility(View.GONE);
                    distanceRankingHolder.mViewItemDistanceRankingLine.setVisibility(View.VISIBLE);
                    distanceRankingHolder.mTvItemDistanceRankingPos.setVisibility(View.GONE);
                    distanceRankingHolder.mTvItemDistanceRankingPosition.setVisibility(View.GONE);
                    distanceRankingHolder.mImgItemDistanceRankingPosition.setVisibility(View.VISIBLE);
                    distanceRankingHolder.mImgItemDistanceRankingPosition.setBackgroundResource(R.drawable.icon_bronze_medal);
                } else {
                    distanceRankingHolder.mViewItemDistanceRankingDivider.setVisibility(View.GONE);
                    distanceRankingHolder.mViewItemDistanceRankingLine.setVisibility(View.VISIBLE);
                    distanceRankingHolder.mTvItemDistanceRankingPos.setVisibility(View.GONE);
                    distanceRankingHolder.mTvItemDistanceRankingPosition.setVisibility(View.VISIBLE);
                    distanceRankingHolder.mImgItemDistanceRankingPosition.setVisibility(View.GONE);
                    distanceRankingHolder.mTvItemDistanceRankingPosition.setText(rank + "");
                }
            }

        } else {
            distanceRankingHolder.mViewItemDistanceRankingDivider.setVisibility(View.GONE);
            distanceRankingHolder.mViewItemDistanceRankingLine.setVisibility(View.VISIBLE);
            if (rank == 1) {
                distanceRankingHolder.mTvItemDistanceRankingPos.setVisibility(View.GONE);
                distanceRankingHolder.mTvItemDistanceRankingPosition.setVisibility(View.GONE);
                distanceRankingHolder.mImgItemDistanceRankingPosition.setVisibility(View.VISIBLE);
                distanceRankingHolder.mImgItemDistanceRankingPosition.setBackgroundResource(R.drawable.icon_gold_medal);
            } else if (rank == 2) {
                distanceRankingHolder.mTvItemDistanceRankingPos.setVisibility(View.GONE);
                distanceRankingHolder.mTvItemDistanceRankingPosition.setVisibility(View.GONE);
                distanceRankingHolder.mImgItemDistanceRankingPosition.setVisibility(View.VISIBLE);
                distanceRankingHolder.mImgItemDistanceRankingPosition.setBackgroundResource(R.drawable.icon_silver_medal);
            } else if (rank == 3) {
                distanceRankingHolder.mTvItemDistanceRankingPos.setVisibility(View.GONE);
                distanceRankingHolder.mTvItemDistanceRankingPosition.setVisibility(View.GONE);
                distanceRankingHolder.mImgItemDistanceRankingPosition.setVisibility(View.VISIBLE);
                distanceRankingHolder.mImgItemDistanceRankingPosition.setBackgroundResource(R.drawable.icon_bronze_medal);
            } else {
                distanceRankingHolder.mTvItemDistanceRankingPos.setVisibility(View.GONE);
                distanceRankingHolder.mTvItemDistanceRankingPosition.setVisibility(View.VISIBLE);
                distanceRankingHolder.mImgItemDistanceRankingPosition.setVisibility(View.GONE);
                distanceRankingHolder.mTvItemDistanceRankingPosition.setText(rank + "");
            }

        }
    }

    public void thumb(final DistanceRankingHolder distanceRankingHolder, final DistanceRankingBean.DataBean.ListBean listBean) {
        Map<String, String> map = new HashMap<>();
        map.put("activity_id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        map.put("user_id", listBean.getId() + "");
        HttpUtils.getInstance().post(Constants.USER_THUMB_UP, map, new HttpCallback<CommonStatusBean>() {
            @Override
            public void onSuccessExecute(CommonStatusBean commonStatusBean) {
                if (commonStatusBean.isData()) {
                    listBean.setThumb_up_count(listBean.getThumb_up_count() + 1);
                    listBean.setHas_thumb_up(true);
                    distanceRankingHolder.mImgItemDistanceRankingThumb.setSelected(true);
                    distanceRankingHolder.mTvItemDistanceRankingThumbCount.setText(listBean.getThumb_up_count() + "");
                    distanceRankingHolder.mTvItemDistanceRankingThumbCount.setTextColor(mContext.getResources().getColor(R.color.color_red));

                    //刷新排行榜数据
                    Intent intent = new Intent();
                    intent.setAction(TeamRankingActivity.THUMB_ACTION);
                    intent.putExtra("type",1);
                    intent.putExtra("isThumb",listBean.isHas_thumb_up());
                    intent.putExtra("thumbCount",listBean.getThumb_up_count());
                    intent.putExtra("userId",listBean.getId());
                    mContext.sendBroadcast(intent);
                }
            }
        });
    }

    public void thumbCancel(final DistanceRankingHolder distanceRankingHolder, final DistanceRankingBean.DataBean.ListBean listBean) {
        Map<String, String> map = new HashMap<>();
        map.put("activity_id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        map.put("user_id", listBean.getId() + "");
        HttpUtils.getInstance().post(Constants.USER_THUMB_UP_CANCEL, map, new HttpCallback<CommonStatusBean>() {
            @Override
            public void onSuccessExecute(CommonStatusBean commonStatusBean) {
                if (commonStatusBean.isData()) {
                    listBean.setHas_thumb_up(false);
                    listBean.setThumb_up_count(listBean.getThumb_up_count() - 1);
                    distanceRankingHolder.mImgItemDistanceRankingThumb.setSelected(false);
                    distanceRankingHolder.mTvItemDistanceRankingThumbCount.setText(listBean.getThumb_up_count() + "");
                    distanceRankingHolder.mTvItemDistanceRankingThumbCount.setTextColor(mContext.getResources().getColor(R.color.color_font_gray_lite));
                    
                    //刷新排行榜数据
                    Intent intent = new Intent();
                    intent.setAction(TeamRankingActivity.THUMB_ACTION);
                    intent.putExtra("type",1);
                    intent.putExtra("isThumb",listBean.isHas_thumb_up());
                    intent.putExtra("thumbCount",listBean.getThumb_up_count());
                    intent.putExtra("userId",listBean.getId());
                    mContext.sendBroadcast(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListBeans.size();
    }

    public class DistanceRankingHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemDistanceRankingPosition;
        private TextView mTvItemDistanceRankingPosition;
        private ImageView mImgItemDistanceRankingHead;
        private TextView mTvItemDistanceRankingName;
        private ImageView mImgItemDistanceRankingThumb;
        private TextView mTvItemDistanceRankingThumbCount;
        private TextView mTvItemDistanceRankingDistance;
        private TextView mTvItemDistanceRankingPos;
        private View mViewItemDistanceRankingLine;
        private View mViewItemDistanceRankingDivider;

        public DistanceRankingHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemDistanceRankingPosition = (ImageView) itemView.findViewById(R.id.img_item_distance_ranking_position);
            mTvItemDistanceRankingPosition = (TextView) itemView.findViewById(R.id.tv_item_distance_ranking_position);
            mImgItemDistanceRankingHead = (ImageView) itemView.findViewById(R.id.img_item_distance_ranking_head);
            mTvItemDistanceRankingName = (TextView) itemView.findViewById(R.id.tv_item_distance_ranking_name);
            mImgItemDistanceRankingThumb = (ImageView) itemView.findViewById(R.id.img_item_distance_ranking_thumb);
            mTvItemDistanceRankingThumbCount = (TextView) itemView.findViewById(R.id.tv_item_distance_ranking_thumbCount);
            mTvItemDistanceRankingDistance = (TextView) itemView.findViewById(R.id.tv_item_distance_ranking_distance);
            mTvItemDistanceRankingPos = (TextView) itemView.findViewById(R.id.tv_item_distance_ranking_pos);
            mViewItemDistanceRankingLine = (View) itemView.findViewById(R.id.view_item_distance_ranking_line);
            mViewItemDistanceRankingDivider = (View) itemView.findViewById(R.id.view_item_distance_ranking_divider);
        }
    }
}
