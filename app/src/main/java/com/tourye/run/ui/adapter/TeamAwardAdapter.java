package com.tourye.run.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.TeamAwardBean;
import com.tourye.run.ui.activities.common.CommonWebActivity;
import com.tourye.run.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

public class TeamAwardAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<TeamAwardBean.DataBean> mDataBeans=new ArrayList<>();

    public TeamAwardAdapter(Context context, List<TeamAwardBean.DataBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TeamAwardHolder teamAwardHolder;
        if (convertView==null) {
            convertView=mLayoutInflater.inflate(R.layout.item_team_award,parent,false);
            teamAwardHolder=new TeamAwardHolder(convertView);
            convertView.setTag(teamAwardHolder);
        }else{
            teamAwardHolder= (TeamAwardHolder) convertView.getTag();
        }
        final TeamAwardBean.DataBean dataBean = mDataBeans.get(position);
        GlideUtils.getInstance().loadRoundImage(dataBean.getImage(),teamAwardHolder.mImgItemTeamAwardHead,8);
        teamAwardHolder.mTvItemTeamAwardName.setText(Html.fromHtml(dataBean.getName()));
        teamAwardHolder.mLlItemTeamAward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = dataBean.getLink();
                if (TextUtils.isEmpty(link)) {
                    return;
                }
                Intent intent = new Intent(mContext, CommonWebActivity.class);
                intent.putExtra("url",link);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    public class TeamAwardHolder{
        private ImageView mImgItemTeamAwardHead;
        private TextView mTvItemTeamAwardName;
        private LinearLayout mLlItemTeamAward;

        public TeamAwardHolder(View view){
            mImgItemTeamAwardHead = (ImageView) view.findViewById(R.id.img_item_team_award_head);
            mTvItemTeamAwardName = (TextView) view.findViewById(R.id.tv_item_team_award_name);
            mLlItemTeamAward = (LinearLayout) view.findViewById(R.id.ll_item_team_award);
        }
    }
}
