package com.tourye.run.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.CalendarPunchBean;
import com.tourye.run.ui.activities.punch.PunchRecordActivity;

import java.util.ArrayList;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<CalendarPunchBean.DataBeanX> mCalendarBeans=new ArrayList<>();
    private int offset;//离当前月份的偏移量

    public CalendarAdapter(Context context, List<CalendarPunchBean.DataBeanX> calendarBeans) {
        mContext = context;
        mCalendarBeans = calendarBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public CalendarAdapter(Context context) {
        mContext = context;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setCalendarBeans(List<CalendarPunchBean.DataBeanX> calendarBeans) {
        mCalendarBeans = calendarBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CalendarHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CalendarHolder(mLayoutInflater.inflate(R.layout.item_calendar,viewGroup,false));
    }

    @Override
    public int getItemCount() {
        return mCalendarBeans.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarHolder calendarHolder, int i) {
        CalendarPunchBean.DataBeanX calendarBean = mCalendarBeans.get(i);
        calendarHolder.mTvItemCalendarDate.setText(calendarBean.getDay_index());
        if (calendarBean.isSignin()) {
            calendarHolder.mTvItemCalendarDistance.setText(calendarBean.getTotal_distance()+"k");
            calendarHolder.mImgItemCalendarBack.setVisibility(View.VISIBLE);
        }else{
            calendarHolder.mTvItemCalendarDistance.setText("");
            calendarHolder.mImgItemCalendarBack.setVisibility(View.GONE);
        }
        if (calendarBean.isSignin()) {
            calendarHolder.mRlItemCalendarContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,PunchRecordActivity.class);
                    intent.putExtra("offset",offset);
                    intent.putExtra("type",1);
                    mContext.startActivity(intent);
                }
            });
        }else{
            calendarHolder.mRlItemCalendarContent.setOnClickListener(null);
        }

    }

    public class CalendarHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemCalendarBack;
        private TextView mTvItemCalendarDate;
        private TextView mTvItemCalendarDistance;
        private RelativeLayout mRlItemCalendarContent;

        public CalendarHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemCalendarBack = (ImageView) itemView.findViewById(R.id.img_item_calendar_back);
            mTvItemCalendarDate = (TextView) itemView.findViewById(R.id.tv_item_calendar_date);
            mTvItemCalendarDistance = (TextView) itemView.findViewById(R.id.tv_item_calendar_distance);
            mRlItemCalendarContent = (RelativeLayout) itemView.findViewById(R.id.rl_item_calendar_content);

        }
    }
}
