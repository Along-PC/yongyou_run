package com.tourye.run.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.TeamManageCalendarBean;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @ClassName:   TeamManageCalendarAdapter
 *
 * @Author:   along
 *
 * @Description:    队伍管理日历适配器
 *
 * @CreateDate:   2019/5/27 10:06 AM
 *
 */
public class TeamManageCalendarAdapter extends RecyclerView.Adapter<TeamManageCalendarAdapter.TeamManageCalendarHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<TeamManageCalendarBean> mTeamManageCalendarBeans;
private OnItemClickListener mOnItemClickListener;
    public TeamManageCalendarAdapter(Context context, List<TeamManageCalendarBean> teamManageCalendarBeans) {
        mContext = context;
        mTeamManageCalendarBeans = teamManageCalendarBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setTeamManageCalendarBeans(List<TeamManageCalendarBean> teamManageCalendarBeans) {
        mTeamManageCalendarBeans = teamManageCalendarBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TeamManageCalendarHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TeamManageCalendarHolder(mLayoutInflater.inflate(R.layout.item_team_manage_calendar,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TeamManageCalendarHolder teamManageCalendarHolder, final int i) {
        final TeamManageCalendarBean teamManageCalendarBean = mTeamManageCalendarBeans.get(i);
        final Date date = mTeamManageCalendarBeans.get(i).getDate();
        Date current = Calendar.getInstance().getTime();
        if (date!=null) {
            if (current.after(date)) {
                teamManageCalendarHolder.mTvItemTeamManageCalendar.setTextColor(mContext.getResources().getColor(R.color.color_font_black));
            }else{
                teamManageCalendarHolder.mTvItemTeamManageCalendar.setTextColor(Color.parseColor("#999999"));
            }
        }
        teamManageCalendarHolder.mTvItemTeamManageCalendar.setSelected(mTeamManageCalendarBeans.get(i).isIs_selected());
        teamManageCalendarHolder.mTvItemTeamManageCalendar.setText(mTeamManageCalendarBeans.get(i).getDate_index());
        teamManageCalendarHolder.mTvItemTeamManageCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date==null) {
                    return;
                }
                if (mOnItemClickListener==null) {
                    return;
                }
                for (TeamManageCalendarBean manageCalendarBean : mTeamManageCalendarBeans) {
                    if (manageCalendarBean.getDate()==null) {
                        continue;
                    }
                    manageCalendarBean.setIs_selected(false);
                }
                teamManageCalendarBean.setIs_selected(true);
                notifyDataSetChanged();
                mOnItemClickListener.onClick(mTeamManageCalendarBeans.get(i).getDateOfMonth());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTeamManageCalendarBeans.size();
    }

    public class TeamManageCalendarHolder extends RecyclerView.ViewHolder {
        private TextView mTvItemTeamManageCalendar;

        public TeamManageCalendarHolder(@NonNull View itemView) {
            super(itemView);
            mTvItemTeamManageCalendar = (TextView) itemView.findViewById(R.id.tv_item_team_manage_calendar);
        }
    }

    public interface OnItemClickListener{
        public void onClick(String date);
    }
}
