package com.tourye.run.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.CommonStatusBean;
import com.tourye.run.bean.DistanceRankingBean;
import com.tourye.run.bean.GameRankingBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.home.TeamRankingActivity;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.GlideRoundTransform;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:   GameRankingAdapter
 *
 * @Author:   along
 *
 * @Description:  个人完赛率排行榜适配器
 *
 * @CreateDate:   2019/3/19 5:04 PM
 *
 */
public class GameRankingAdapter extends RecyclerView.Adapter<GameRankingAdapter.GameRankingHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<GameRankingBean.DataBean.ListBean> mListBeans=new ArrayList<>();

    private boolean isCompleteRank;//是否是完整榜单
    private boolean isSameTeam;//是否是相同队伍

    public GameRankingAdapter(Context context, List<GameRankingBean.DataBean.ListBean> listBeans) {
        mContext = context;
        mListBeans = listBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setListBeans(List<GameRankingBean.DataBean.ListBean> listBeans) {
        mListBeans = listBeans;
        notifyDataSetChanged();
    }

    public void setCompleteRank(boolean completeRank) {
        isCompleteRank = completeRank;
    }

    public void setSameTeam(boolean sameTeam) {
        isSameTeam = sameTeam;
    }

    @NonNull
    @Override
    public GameRankingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new GameRankingHolder(mLayoutInflater.inflate(R.layout.item_team_game_ranking,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final GameRankingHolder gameRankingHolder, int i) {
        final GameRankingBean.DataBean.ListBean listBean = mListBeans.get(i);

        if (isCompleteRank) {
            changeToCompleteRank(gameRankingHolder,listBean,i);
        }else{
            changeToUnCompleteRank(gameRankingHolder,listBean,i);
        }

        if (listBean.isHas_sign_in()) {
            gameRankingHolder.mTvItemGameRankingInsist.setTextColor(Color.parseColor("#5AA856"));
            gameRankingHolder.mTvItemGameRankingRate.setTextColor(Color.parseColor("#5AA856"));
        }else{
            gameRankingHolder.mTvItemGameRankingInsist.setTextColor(mContext.getResources().getColor(R.color.color_font_gray));
            gameRankingHolder.mTvItemGameRankingRate.setTextColor(mContext.getResources().getColor(R.color.color_font_black));
        }
        gameRankingHolder.mTvItemGameRankingName.setText(listBean.getNickname());
        gameRankingHolder.mTvItemGameRankingRate.setText(listBean.getRate()+"%");
        GlideUtils.getInstance().loadRoundImage(listBean.getAvatar(),gameRankingHolder.mImgItemGameRankingHead);
        gameRankingHolder.mImgItemGameRankingThumb.setSelected(listBean.isHas_thumb_up());
        gameRankingHolder.mTvItemGameRankingThumbCount.setText(listBean.getThumb_up_count()+"");
        if (listBean.isHas_thumb_up()) {
            gameRankingHolder.mTvItemGameRankingThumbCount.setTextColor(mContext.getResources().getColor(R.color.color_red));
        }else{
            gameRankingHolder.mTvItemGameRankingThumbCount.setTextColor(mContext.getResources().getColor(R.color.color_font_gray_lite));
        }
        gameRankingHolder.mImgItemGameRankingThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    thumbCancel(gameRankingHolder,listBean);
                }else{
                    thumb(gameRankingHolder,listBean);
                }
            }
        });

    }

    /**
     * 切换至非完整排行榜
     * @param gameRankingHolder
     * @param listBean
     * @param i
     */
    private void changeToUnCompleteRank(GameRankingHolder gameRankingHolder, GameRankingBean.DataBean.ListBean listBean, int i) {
        gameRankingHolder.mViewItemGameRankingDivider.setVisibility(View.GONE);
        gameRankingHolder.mViewItemGameRankingLine.setVisibility(View.VISIBLE);
        int rank = listBean.getRank();
        if (isSameTeam) {
            if (i==0) {
                gameRankingHolder.mTvItemGameRankingPosition.setVisibility(View.GONE);
                gameRankingHolder.mImgItemGameRankingPosition.setVisibility(View.GONE);
                gameRankingHolder.mTvItemGameRankingInsist.setText("第"+listBean.getRank()+"名");
            }else{
                switch (rank) {
                    case 1:
                        gameRankingHolder.mTvItemGameRankingPosition.setVisibility(View.GONE);
                        gameRankingHolder.mImgItemGameRankingPosition.setVisibility(View.VISIBLE);
                        gameRankingHolder.mImgItemGameRankingPosition.setBackgroundResource(R.drawable.icon_gold_medal);
                        break;
                    case 2:
                        gameRankingHolder.mTvItemGameRankingPosition.setVisibility(View.GONE);
                        gameRankingHolder.mImgItemGameRankingPosition.setVisibility(View.VISIBLE);
                        gameRankingHolder.mImgItemGameRankingPosition.setBackgroundResource(R.drawable.icon_silver_medal);
                        break;
                    case 3:
                        gameRankingHolder.mTvItemGameRankingPosition.setVisibility(View.GONE);
                        gameRankingHolder.mImgItemGameRankingPosition.setVisibility(View.VISIBLE);
                        gameRankingHolder.mImgItemGameRankingPosition.setBackgroundResource(R.drawable.icon_bronze_medal);
                        break;
                    default:
                        gameRankingHolder.mTvItemGameRankingPosition.setVisibility(View.VISIBLE);
                        gameRankingHolder.mImgItemGameRankingPosition.setVisibility(View.GONE);
                        gameRankingHolder.mTvItemGameRankingPosition.setText(rank+"");
                        break;
                }
                gameRankingHolder.mTvItemGameRankingInsist.setText("已坚持"+listBean.getTotal_days()+"天");
            }
        }else{
            switch (rank) {
                case 1:
                    gameRankingHolder.mTvItemGameRankingPosition.setVisibility(View.GONE);
                    gameRankingHolder.mImgItemGameRankingPosition.setVisibility(View.VISIBLE);
                    gameRankingHolder.mImgItemGameRankingPosition.setBackgroundResource(R.drawable.icon_gold_medal);
                    break;
                case 2:
                    gameRankingHolder.mTvItemGameRankingPosition.setVisibility(View.GONE);
                    gameRankingHolder.mImgItemGameRankingPosition.setVisibility(View.VISIBLE);
                    gameRankingHolder.mImgItemGameRankingPosition.setBackgroundResource(R.drawable.icon_silver_medal);
                    break;
                case 3:
                    gameRankingHolder.mTvItemGameRankingPosition.setVisibility(View.GONE);
                    gameRankingHolder.mImgItemGameRankingPosition.setVisibility(View.VISIBLE);
                    gameRankingHolder.mImgItemGameRankingPosition.setBackgroundResource(R.drawable.icon_bronze_medal);
                    break;
                default:
                    gameRankingHolder.mTvItemGameRankingPosition.setVisibility(View.VISIBLE);
                    gameRankingHolder.mImgItemGameRankingPosition.setVisibility(View.GONE);
                    gameRankingHolder.mTvItemGameRankingPosition.setText(rank+"");
                    break;
            }
            gameRankingHolder.mTvItemGameRankingInsist.setText("已坚持"+listBean.getTotal_days()+"天");
        }
    }

    /**
     * 切换至完整排行榜
     * @param gameRankingHolder
     * @param listBean
     * @param i
     */
    private void changeToCompleteRank(GameRankingHolder gameRankingHolder, GameRankingBean.DataBean.ListBean listBean, int i) {
        int rank = listBean.getRank();
        if (isSameTeam) {
            if (i==0) {
                gameRankingHolder.mViewItemGameRankingDivider.setVisibility(View.VISIBLE);
                gameRankingHolder.mViewItemGameRankingLine.setVisibility(View.GONE);
                gameRankingHolder.mTvItemGameRankingPosition.setVisibility(View.GONE);
                gameRankingHolder.mImgItemGameRankingPosition.setVisibility(View.GONE);
                gameRankingHolder.mTvItemGameRankingInsist.setText("第"+listBean.getRank()+"名");
            }else{
                switch (rank) {
                    case 1:
                        gameRankingHolder.mViewItemGameRankingDivider.setVisibility(View.GONE);
                        gameRankingHolder.mViewItemGameRankingLine.setVisibility(View.VISIBLE);
                        gameRankingHolder.mTvItemGameRankingPosition.setVisibility(View.GONE);
                        gameRankingHolder.mImgItemGameRankingPosition.setVisibility(View.VISIBLE);
                        gameRankingHolder.mImgItemGameRankingPosition.setBackgroundResource(R.drawable.icon_gold_medal);
                        break;
                    case 2:
                        gameRankingHolder.mViewItemGameRankingDivider.setVisibility(View.GONE);
                        gameRankingHolder.mViewItemGameRankingLine.setVisibility(View.VISIBLE);
                        gameRankingHolder.mTvItemGameRankingPosition.setVisibility(View.GONE);
                        gameRankingHolder.mImgItemGameRankingPosition.setVisibility(View.VISIBLE);
                        gameRankingHolder.mImgItemGameRankingPosition.setBackgroundResource(R.drawable.icon_silver_medal);
                        break;
                    case 3:
                        gameRankingHolder.mViewItemGameRankingDivider.setVisibility(View.GONE);
                        gameRankingHolder.mViewItemGameRankingLine.setVisibility(View.VISIBLE);
                        gameRankingHolder.mTvItemGameRankingPosition.setVisibility(View.GONE);
                        gameRankingHolder.mImgItemGameRankingPosition.setVisibility(View.VISIBLE);
                        gameRankingHolder.mImgItemGameRankingPosition.setBackgroundResource(R.drawable.icon_bronze_medal);
                        break;
                    default:
                        gameRankingHolder.mViewItemGameRankingDivider.setVisibility(View.GONE);
                        gameRankingHolder.mViewItemGameRankingLine.setVisibility(View.VISIBLE);
                        gameRankingHolder.mTvItemGameRankingPosition.setVisibility(View.VISIBLE);
                        gameRankingHolder.mImgItemGameRankingPosition.setVisibility(View.GONE);
                        gameRankingHolder.mTvItemGameRankingPosition.setText(rank+"");
                        break;
                }
                gameRankingHolder.mTvItemGameRankingInsist.setText("已坚持"+listBean.getTotal_days()+"天");
            }
        }else{
            gameRankingHolder.mViewItemGameRankingDivider.setVisibility(View.GONE);
            gameRankingHolder.mViewItemGameRankingLine.setVisibility(View.VISIBLE);
            gameRankingHolder.mTvItemGameRankingInsist.setText("已坚持"+listBean.getTotal_days()+"天");
            switch (rank) {
                case 1:
                    gameRankingHolder.mTvItemGameRankingPosition.setVisibility(View.GONE);
                    gameRankingHolder.mImgItemGameRankingPosition.setVisibility(View.VISIBLE);
                    gameRankingHolder.mImgItemGameRankingPosition.setBackgroundResource(R.drawable.icon_gold_medal);
                    break;
                case 2:
                    gameRankingHolder.mTvItemGameRankingPosition.setVisibility(View.GONE);
                    gameRankingHolder.mImgItemGameRankingPosition.setVisibility(View.VISIBLE);
                    gameRankingHolder.mImgItemGameRankingPosition.setBackgroundResource(R.drawable.icon_silver_medal);
                    break;
                case 3:
                    gameRankingHolder.mTvItemGameRankingPosition.setVisibility(View.GONE);
                    gameRankingHolder.mImgItemGameRankingPosition.setVisibility(View.VISIBLE);
                    gameRankingHolder.mImgItemGameRankingPosition.setBackgroundResource(R.drawable.icon_bronze_medal);
                    break;
                default:
                    gameRankingHolder.mTvItemGameRankingPosition.setVisibility(View.VISIBLE);
                    gameRankingHolder.mImgItemGameRankingPosition.setVisibility(View.GONE);
                    gameRankingHolder.mTvItemGameRankingPosition.setText(rank+"");
                    break;
            }
        }
    }

    public void thumb(final GameRankingHolder gameRankingHolder, final GameRankingBean.DataBean.ListBean listBean){
        Map<String,String> map=new HashMap<>();
        map.put("activity_id",SaveUtil.getString(SaveConstants.ACTION_ID,""));
        map.put("user_id",listBean.getId()+"");
        HttpUtils.getInstance().post(Constants.USER_THUMB_UP, map, new HttpCallback<CommonStatusBean>() {
            @Override
            public void onSuccessExecute(CommonStatusBean commonStatusBean) {
                if (commonStatusBean.isData()) {
                    listBean.setThumb_up_count(listBean.getThumb_up_count()+1);
                    listBean.setHas_thumb_up(true);
                    gameRankingHolder.mImgItemGameRankingThumb.setSelected(true);
                    gameRankingHolder.mTvItemGameRankingThumbCount.setText(listBean.getThumb_up_count()+"");
                    gameRankingHolder.mTvItemGameRankingThumbCount.setTextColor(mContext.getResources().getColor(R.color.color_red));
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

    public void thumbCancel(final GameRankingHolder gameRankingHolder, final GameRankingBean.DataBean.ListBean listBean){
        Map<String,String> map=new HashMap<>();
        map.put("activity_id",SaveUtil.getString(SaveConstants.ACTION_ID,""));
        map.put("user_id",listBean.getId()+"");
        HttpUtils.getInstance().post(Constants.USER_THUMB_UP_CANCEL, map, new HttpCallback<CommonStatusBean>() {
            @Override
            public void onSuccessExecute(CommonStatusBean commonStatusBean) {
                if (commonStatusBean.isData()) {
                    listBean.setHas_thumb_up(false);
                    listBean.setThumb_up_count(listBean.getThumb_up_count()-1);
                    gameRankingHolder.mImgItemGameRankingThumb.setSelected(false);
                    gameRankingHolder.mTvItemGameRankingThumbCount.setText(listBean.getThumb_up_count()+"");
                    gameRankingHolder.mTvItemGameRankingThumbCount.setTextColor(mContext.getResources().getColor(R.color.color_font_gray_lite));
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

    public class GameRankingHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemGameRankingPosition;
        private TextView mTvItemGameRankingPosition;
        private ImageView mImgItemGameRankingHead;
        private TextView mTvItemGameRankingName;
        private TextView mTvItemGameRankingInsist;
        private TextView mTvItemGameRankingRate;
        private ImageView mImgItemGameRankingThumb;
        private TextView mTvItemGameRankingThumbCount;
        private View mViewItemGameRankingLine;
        private View mViewItemGameRankingDivider;

        public GameRankingHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemGameRankingPosition = (ImageView) itemView.findViewById(R.id.img_item_game_ranking_position);
            mTvItemGameRankingPosition = (TextView) itemView.findViewById(R.id.tv_item_game_ranking_position);
            mImgItemGameRankingHead = (ImageView) itemView.findViewById(R.id.img_item_game_ranking_head);
            mTvItemGameRankingName = (TextView) itemView.findViewById(R.id.tv_item_game_ranking_name);
            mTvItemGameRankingInsist = (TextView) itemView.findViewById(R.id.tv_item_game_ranking_insist);
            mTvItemGameRankingRate = (TextView) itemView.findViewById(R.id.tv_item_game_ranking_rate);
            mImgItemGameRankingThumb = (ImageView) itemView.findViewById(R.id.img_item_game_ranking_thumb);
            mTvItemGameRankingThumbCount = (TextView) itemView.findViewById(R.id.tv_item_game_ranking_thumbCount);
            mViewItemGameRankingLine = (View) itemView.findViewById(R.id.view_item_game_ranking_line);
            mViewItemGameRankingDivider = (View) itemView.findViewById(R.id.view_item_game_ranking_divider);

        }
    }
}
