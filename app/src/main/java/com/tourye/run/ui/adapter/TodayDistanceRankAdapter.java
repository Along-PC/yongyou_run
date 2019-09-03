package com.tourye.run.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.bean.CommonStatusBean;
import com.tourye.run.bean.TodayDistanceRankBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.home.TeamRankingActivity;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TodayDistanceRankAdapter
 * @Author: along
 * @Description: 今日距离排行列表适配器
 * @CreateDate: 2019/4/10 6:22 PM
 */
public class TodayDistanceRankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<TodayDistanceRankBean.DataBean.CurrentBean> mRankBeans;

    public static final int FLAG_MYSELF = 1;
    public static final int FLAG_NO_MYSELF = 2;
    private boolean isCompleteRank;

    public TodayDistanceRankAdapter(Context context, List<TodayDistanceRankBean.DataBean.CurrentBean> rankBeans) {
        mContext = context;
        mRankBeans = rankBeans;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setCompleteRank(boolean completeRank) {
        isCompleteRank = completeRank;
    }

    public void setRankBeans(List<TodayDistanceRankBean.DataBean.CurrentBean> rankBeans) {
        mRankBeans = rankBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        TodayDistanceRankBean.DataBean.CurrentBean currentBean = mRankBeans.get(position);
        if (currentBean.isMyself()) {
            return FLAG_MYSELF;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == FLAG_MYSELF) {
            return new TodayDistanceRankCurrentHolder(mLayoutInflater.inflate(R.layout.item_today_distance_rank_myself, viewGroup, false));
        } else {
            return new TodayDistanceRankHolder(mLayoutInflater.inflate(R.layout.item_today_distance_rank, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int i) {
        final TodayDistanceRankBean.DataBean.CurrentBean rankBean = mRankBeans.get(i);
        if (holder instanceof TodayDistanceRankCurrentHolder) {
            TodayDistanceRankCurrentHolder todayDistanceRankCurrentHolder = (TodayDistanceRankCurrentHolder) holder;
            bindMyselfData(todayDistanceRankCurrentHolder, rankBean);
        } else if (holder instanceof TodayDistanceRankHolder) {
            TodayDistanceRankHolder todayDistanceRankHolder = (TodayDistanceRankHolder) holder;
            bindRankData(todayDistanceRankHolder, rankBean);
        }
    }

    private void bindRankData(final TodayDistanceRankHolder todayDistanceRankHolder, final TodayDistanceRankBean.DataBean.CurrentBean rankBean) {
        int rank = rankBean.getRank();
        switch (rank) {
            case 1:
                todayDistanceRankHolder.mTvItemTodayDistanceRankingPosition.setVisibility(View.GONE);
                todayDistanceRankHolder.mImgItemTodayDistanceRankingPosition.setVisibility(View.VISIBLE);
                todayDistanceRankHolder.mImgItemTodayDistanceRankingPosition.setBackgroundResource(R.drawable.icon_gold_medal);
                break;
            case 2:
                todayDistanceRankHolder.mTvItemTodayDistanceRankingPosition.setVisibility(View.GONE);
                todayDistanceRankHolder.mImgItemTodayDistanceRankingPosition.setVisibility(View.VISIBLE);
                todayDistanceRankHolder.mImgItemTodayDistanceRankingPosition.setBackgroundResource(R.drawable.icon_silver_medal);
                break;
            case 3:
                todayDistanceRankHolder.mTvItemTodayDistanceRankingPosition.setVisibility(View.GONE);
                todayDistanceRankHolder.mImgItemTodayDistanceRankingPosition.setVisibility(View.VISIBLE);
                todayDistanceRankHolder.mImgItemTodayDistanceRankingPosition.setBackgroundResource(R.drawable.icon_bronze_medal);
                break;
            default:
                todayDistanceRankHolder.mTvItemTodayDistanceRankingPosition.setVisibility(View.VISIBLE);
                todayDistanceRankHolder.mImgItemTodayDistanceRankingPosition.setVisibility(View.GONE);
                todayDistanceRankHolder.mTvItemTodayDistanceRankingPosition.setText(rank + "");
                break;
        }
        todayDistanceRankHolder.mTvItemTodayDistanceRankingName.setText(rankBean.getNickname());
        GlideUtils.getInstance().loadRoundImage(rankBean.getAvatar(), todayDistanceRankHolder.mImgItemTodayDistanceRankingHead);
        todayDistanceRankHolder.mTvItemTodayDistanceRankingDistance.setText(rankBean.getDistance() + "km");
        todayDistanceRankHolder.mTvItemTodayDistanceRankingThumbCount.setText(rankBean.getThumb_up_count() + "");
        if (rankBean.isHas_thumb_up()) {
            todayDistanceRankHolder.mTvItemTodayDistanceRankingThumbCount.setTextColor(mContext.getResources().getColor(R.color.color_red));
        }else{
            todayDistanceRankHolder.mTvItemTodayDistanceRankingThumbCount.setTextColor(mContext.getResources().getColor(R.color.color_font_gray_lite));
        }
        todayDistanceRankHolder.mImgItemTodayDistanceRankingThumb.setSelected(rankBean.isHas_thumb_up());
        todayDistanceRankHolder.mImgItemTodayDistanceRankingThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    thumbCancel(rankBean.getId() + "", rankBean, todayDistanceRankHolder.mImgItemTodayDistanceRankingThumb, todayDistanceRankHolder.mTvItemTodayDistanceRankingThumbCount);
                } else {
                    thumb(rankBean.getId() + "", rankBean, todayDistanceRankHolder.mImgItemTodayDistanceRankingThumb, todayDistanceRankHolder.mTvItemTodayDistanceRankingThumbCount);
                }
            }
        });
    }

    /**
     * 绑定自己的数据
     *
     * @param todayDistanceRankCurrentHolder
     * @param rankBean
     */
    private void bindMyselfData(final TodayDistanceRankCurrentHolder todayDistanceRankCurrentHolder, final TodayDistanceRankBean.DataBean.CurrentBean rankBean) {
        if (isCompleteRank) {
            todayDistanceRankCurrentHolder.mViewItemTodayDistanceRankingDivider.setVisibility(View.VISIBLE);
            todayDistanceRankCurrentHolder.mViewItemTodayDistanceRankingLine.setVisibility(View.GONE);
        } else {
            todayDistanceRankCurrentHolder.mViewItemTodayDistanceRankingDivider.setVisibility(View.GONE);
            todayDistanceRankCurrentHolder.mViewItemTodayDistanceRankingLine.setVisibility(View.VISIBLE);
        }
        todayDistanceRankCurrentHolder.mTvItemTodayDistanceRankingIntro.setText("第" + rankBean.getRank() + "名");
        todayDistanceRankCurrentHolder.mTvItemTodayDistanceRankingName.setText(rankBean.getNickname());
        GlideUtils.getInstance().loadRoundImage(rankBean.getAvatar(), todayDistanceRankCurrentHolder.mImgItemTodayDistanceRankingHead);
        todayDistanceRankCurrentHolder.mTvItemTodayDistanceRankingDistance.setText(rankBean.getDistance() + "km");
        todayDistanceRankCurrentHolder.mTvItemTodayDistanceRankingThumbCount.setText(rankBean.getThumb_up_count() + "");
        if (rankBean.isHas_thumb_up()) {
            todayDistanceRankCurrentHolder.mTvItemTodayDistanceRankingThumbCount.setTextColor(mContext.getResources().getColor(R.color.color_red));
        }else{
            todayDistanceRankCurrentHolder.mTvItemTodayDistanceRankingThumbCount.setTextColor(mContext.getResources().getColor(R.color.color_font_gray_lite));
        }
        todayDistanceRankCurrentHolder.mImgItemTodayDistanceRankingThumb.setSelected(rankBean.isHas_thumb_up());
        todayDistanceRankCurrentHolder.mImgItemTodayDistanceRankingThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    thumbCancel(rankBean.getId() + "", rankBean, todayDistanceRankCurrentHolder.mImgItemTodayDistanceRankingThumb, todayDistanceRankCurrentHolder.mTvItemTodayDistanceRankingThumbCount);
                } else {
                    thumb(rankBean.getId() + "", rankBean, todayDistanceRankCurrentHolder.mImgItemTodayDistanceRankingThumb, todayDistanceRankCurrentHolder.mTvItemTodayDistanceRankingThumbCount);
                }
            }
        });
    }

    public void thumb(String user_id, final TodayDistanceRankBean.DataBean.CurrentBean currentBean, final ImageView imageView, final TextView textView) {
        Map<String, String> map = new HashMap<>();
        map.put("activity_id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        map.put("user_id", user_id);
        HttpUtils.getInstance().post(Constants.USER_THUMB_UP, map, new HttpCallback<CommonStatusBean>() {
            @Override
            public void onSuccessExecute(CommonStatusBean commonStatusBean) {
                if (commonStatusBean.isData()) {
                    currentBean.setThumb_up_count(currentBean.getThumb_up_count() + 1);
                    currentBean.setHas_thumb_up(true);
                    imageView.setSelected(true);
                    textView.setText(currentBean.getThumb_up_count() + "");
                    textView.setTextColor(mContext.getResources().getColor(R.color.color_red));

                    //刷新排行榜数据
                    Intent intent = new Intent();
                    intent.setAction(TeamRankingActivity.THUMB_ACTION);
                    intent.putExtra("type",1);
                    intent.putExtra("isThumb",currentBean.isHas_thumb_up());
                    intent.putExtra("thumbCount",currentBean.getThumb_up_count());
                    intent.putExtra("userId",currentBean.getId());
                    mContext.sendBroadcast(intent);
                }
            }
        });
    }

    public void thumbCancel(String user_id, final TodayDistanceRankBean.DataBean.CurrentBean currentBean, final ImageView imageView, final TextView textView) {
        Map<String, String> map = new HashMap<>();
        map.put("activity_id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        map.put("user_id", user_id);
        HttpUtils.getInstance().post(Constants.USER_THUMB_UP_CANCEL, map, new HttpCallback<CommonStatusBean>() {
            @Override
            public void onSuccessExecute(CommonStatusBean commonStatusBean) {
                if (commonStatusBean.isData()) {
                    currentBean.setHas_thumb_up(false);
                    currentBean.setThumb_up_count(currentBean.getThumb_up_count() - 1);
                    imageView.setSelected(false);
                    textView.setText(currentBean.getThumb_up_count() + "");
                    textView.setTextColor(mContext.getResources().getColor(R.color.color_font_gray_lite));

                    //刷新排行榜数据
                    Intent intent = new Intent();
                    intent.setAction(TeamRankingActivity.THUMB_ACTION);
                    intent.putExtra("type",1);
                    intent.putExtra("isThumb",currentBean.isHas_thumb_up());
                    intent.putExtra("thumbCount",currentBean.getThumb_up_count());
                    intent.putExtra("userId",currentBean.getId());
                    mContext.sendBroadcast(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRankBeans.size();
    }

    public class TodayDistanceRankHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemTodayDistanceRankingPosition;
        private TextView mTvItemTodayDistanceRankingPosition;
        private ImageView mImgItemTodayDistanceRankingHead;
        private TextView mTvItemTodayDistanceRankingName;
        private TextView mTvItemTodayDistanceRankingDistance;
        private ImageView mImgItemTodayDistanceRankingThumb;
        private TextView mTvItemTodayDistanceRankingThumbCount;
        private View mViewItemTodayDistanceRankingLine;
        private View mViewItemTodayDistanceRankingDivider;

        public TodayDistanceRankHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemTodayDistanceRankingPosition = (ImageView) itemView.findViewById(R.id.img_item_today_distance_ranking_position);
            mTvItemTodayDistanceRankingPosition = (TextView) itemView.findViewById(R.id.tv_item_today_distance_ranking_position);
            mImgItemTodayDistanceRankingHead = (ImageView) itemView.findViewById(R.id.img_item_today_distance_ranking_head);
            mTvItemTodayDistanceRankingName = (TextView) itemView.findViewById(R.id.tv_item_today_distance_ranking_name);
            mTvItemTodayDistanceRankingDistance = (TextView) itemView.findViewById(R.id.tv_item_today_distance_ranking_distance);
            mImgItemTodayDistanceRankingThumb = (ImageView) itemView.findViewById(R.id.img_item_today_distance_ranking_thumb);
            mTvItemTodayDistanceRankingThumbCount = (TextView) itemView.findViewById(R.id.tv_item_today_distance_ranking_thumbCount);
            mViewItemTodayDistanceRankingLine = (View) itemView.findViewById(R.id.view_item_today_distance_ranking_line);

        }
    }

    public class TodayDistanceRankCurrentHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemTodayDistanceRankingHead;
        private TextView mTvItemTodayDistanceRankingName;
        private TextView mTvItemTodayDistanceRankingIntro;
        private TextView mTvItemTodayDistanceRankingDistance;
        private ImageView mImgItemTodayDistanceRankingThumb;
        private TextView mTvItemTodayDistanceRankingThumbCount;
        private View mViewItemTodayDistanceRankingDivider;
        private View mViewItemTodayDistanceRankingLine;

        public TodayDistanceRankCurrentHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemTodayDistanceRankingHead = (ImageView) itemView.findViewById(R.id.img_item_today_distance_ranking_head);
            mTvItemTodayDistanceRankingName = (TextView) itemView.findViewById(R.id.tv_item_today_distance_ranking_name);
            mTvItemTodayDistanceRankingIntro = (TextView) itemView.findViewById(R.id.tv_item_today_distance_ranking_intro);
            mTvItemTodayDistanceRankingDistance = (TextView) itemView.findViewById(R.id.tv_item_today_distance_ranking_distance);
            mImgItemTodayDistanceRankingThumb = (ImageView) itemView.findViewById(R.id.img_item_today_distance_ranking_thumb);
            mTvItemTodayDistanceRankingThumbCount = (TextView) itemView.findViewById(R.id.tv_item_today_distance_ranking_thumbCount);
            mViewItemTodayDistanceRankingDivider = (View) itemView.findViewById(R.id.view_item_today_distance_ranking_divider);
            mViewItemTodayDistanceRankingLine = (View) itemView.findViewById(R.id.view_item_today_distance_ranking_line);

        }
    }
}
