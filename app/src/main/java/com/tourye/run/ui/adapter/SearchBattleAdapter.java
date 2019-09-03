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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.SearchBattleBean;
import com.tourye.run.bean.TeamBasicInfoBean;
import com.tourye.run.ui.activities.home.TeamDetailActivity;
import com.tourye.run.utils.GlideUtils;

import java.util.List;

/**
 *
 * @ClassName:   SearchBattleAdapter
 *
 * @Author:   along
 *
 * @Description:    搜索战队列表适配器
 *
 * @CreateDate:   2019/4/4 9:43 AM
 *
 */
public class SearchBattleAdapter extends RecyclerView.Adapter<SearchBattleAdapter.SearchBattleHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<TeamBasicInfoBean> mDataBeans;

    public SearchBattleAdapter(Context context, List<TeamBasicInfoBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDataBeans(List<TeamBasicInfoBean> dataBeans) {
        mDataBeans = dataBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchBattleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SearchBattleHolder(mLayoutInflater.inflate(R.layout.item_activity_search_battle,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchBattleHolder searchBattleHolder, int i) {
        final TeamBasicInfoBean dataBean = mDataBeans.get(i);
        GlideUtils.getInstance().loadRoundImage(dataBean.getLogo(),searchBattleHolder.mImgItemSearchBattleHead);
        searchBattleHolder.mTvItemSearchBattleName.setText(dataBean.getName());
        searchBattleHolder.mTvItemSearchBattleDistance.setText(dataBean.getDistance()+"km");
        String city = dataBean.getCity();
        if (TextUtils.isEmpty(city)) {
            city="全国";
        }
        searchBattleHolder.mTvItemSearchBattlePosition.setText(city);
        searchBattleHolder.mTvItemSearchBattleCount.setText(dataBean.getMember_count()+"人");
        searchBattleHolder.mTvItemSearchBattleIntro.setText("队长："+dataBean.getMonitor());
        searchBattleHolder.mRlItemSearchBattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TeamDetailActivity.class);
                intent.putExtra("team_id",dataBean.getId()+"");
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public class SearchBattleHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mRlItemSearchBattle;
        private ImageView mImgItemSearchBattleHead;
        private TextView mTvItemSearchBattleName;
        private LinearLayout mLlItemSearchBattle;
        private TextView mTvItemSearchBattlePosition;
        private TextView mTvItemSearchBattleDistance;
        private TextView mTvItemSearchBattleCount;
        private TextView mTvItemSearchBattleIntro;

        public SearchBattleHolder(@NonNull View itemView) {
            super(itemView);mRlItemSearchBattle = (RelativeLayout) itemView.findViewById(R.id.rl_item_search_battle);
            mImgItemSearchBattleHead = (ImageView) itemView.findViewById(R.id.img_item_search_battle_head);
            mTvItemSearchBattleName = (TextView) itemView.findViewById(R.id.tv_item_search_battle_name);
            mLlItemSearchBattle = (LinearLayout) itemView.findViewById(R.id.ll_item_search_battle);
            mTvItemSearchBattlePosition = (TextView) itemView.findViewById(R.id.tv_item_search_battle_position);
            mTvItemSearchBattleDistance = (TextView) itemView.findViewById(R.id.tv_item_search_battle_distance);
            mTvItemSearchBattleCount = (TextView) itemView.findViewById(R.id.tv_item_search_battle_count);
            mTvItemSearchBattleIntro = (TextView) itemView.findViewById(R.id.tv_item_search_battle_intro);
        }

    }
}
