package com.tourye.run.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tourye.run.R;
import com.tourye.run.bean.PunchCalendarBean;
import com.tourye.run.utils.DensityUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.tourye.run.ui.adapter.CalendarMonthAdapter.CURRENT_DATE_STR;

public class PunchCalendarAdapter extends RecyclerView.Adapter<PunchCalendarAdapter.PunchCalendarHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<PunchCalendarBean> mPunchCalendarBeans;
    private OnItemClickListener mOnItemClickListener;

    public PunchCalendarAdapter(Context context,List<PunchCalendarBean> punchCalendarBeans) {
        mContext = context;
        mPunchCalendarBeans=punchCalendarBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setPunchCalendarBeans(List<PunchCalendarBean> punchCalendarBeans) {
        mPunchCalendarBeans = punchCalendarBeans;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PunchCalendarHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PunchCalendarHolder(mLayoutInflater.inflate(R.layout.item_activity_punch_calendar,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PunchCalendarHolder punchCalendarHolder, final int i) {
        int lineCount = mPunchCalendarBeans.size() / 7;
        if (mPunchCalendarBeans.size() % 7>0) {
            lineCount++;
        }
        //重新设置高度
        ViewGroup.LayoutParams layoutParams = punchCalendarHolder.mRlItemPunchCalendar.getLayoutParams();
        layoutParams.height=DensityUtils.dp2px(300)/lineCount;
        punchCalendarHolder.mRlItemPunchCalendar.setLayoutParams(layoutParams);

        final PunchCalendarBean punchCalendarBean = mPunchCalendarBeans.get(i);
        if (CURRENT_DATE_STR.equalsIgnoreCase(punchCalendarBean.getDateStr())) {
            punchCalendarHolder.mImgItemPunchCalendarPoint.setVisibility(View.VISIBLE);
        }else{
            punchCalendarHolder.mImgItemPunchCalendarPoint.setVisibility(View.GONE);
        }

        String status = punchCalendarBean.getStatus();
        String lastStatus = punchCalendarBean.getLastStatus();
        String nextStatus = punchCalendarBean.getNextStatus();

        punchCalendarHolder.mImgItemPunchCalendarRight.setBackgroundResource(0);
        punchCalendarHolder.mImgItemPunchCalendarLeft.setBackgroundResource(0);
        punchCalendarHolder.mTvItemPunchCalendar.setBackgroundResource(0);
        punchCalendarHolder.mImgItemPunchCalendar.setBackgroundResource(0);

        if (!TextUtils.isEmpty(status)) {

            //和前后日期状态不同
            if (status.equalsIgnoreCase("accepted") && !status.equalsIgnoreCase(lastStatus) && !status.equalsIgnoreCase(nextStatus)) {
                punchCalendarHolder.mTvItemPunchCalendar.setBackgroundResource(R.drawable.shape_calendar_pass);
            }else if(status.equalsIgnoreCase("rejected") && !status.equalsIgnoreCase(lastStatus) && !status.equalsIgnoreCase(nextStatus)){
                punchCalendarHolder.mTvItemPunchCalendar.setBackgroundResource(R.drawable.shape_calendar_nopass);
            }

            //和前后日期状态相同
            if (status.equalsIgnoreCase(lastStatus) && status.equalsIgnoreCase("accepted") && status.equalsIgnoreCase(nextStatus)) {
                if (punchCalendarBean.getTag()==1) {
                    punchCalendarHolder.mImgItemPunchCalendarRight.setBackgroundResource(R.drawable.shape_calendar_other_left_pass);
                }else if (punchCalendarBean.getTag()==7){
                    punchCalendarHolder.mImgItemPunchCalendarLeft.setBackgroundResource(R.drawable.shape_calendar_other_right_pass);
                }else{
                    punchCalendarHolder.mImgItemPunchCalendar.setBackgroundResource(R.drawable.shape_calendar_other_pass);
                }
            }else if(status.equalsIgnoreCase(lastStatus) && status.equalsIgnoreCase("rejected") && status.equalsIgnoreCase(nextStatus)){
                if (punchCalendarBean.getTag()==1) {
                    punchCalendarHolder.mImgItemPunchCalendarRight.setBackgroundResource(R.drawable.shape_calendar_other_left_nopass);
                }else if (punchCalendarBean.getTag()==7){
                    punchCalendarHolder.mImgItemPunchCalendarLeft.setBackgroundResource(R.drawable.shape_calendar_other_right_nopass);
                }else{
                    punchCalendarHolder.mImgItemPunchCalendar.setBackgroundResource(R.drawable.shape_calendar_other_nopass);
                }
            }

            //和前后日期状态部分相同
            if (status.equalsIgnoreCase(lastStatus) && status.equalsIgnoreCase("accepted") && !status.equalsIgnoreCase(nextStatus)) {
                if (punchCalendarBean.getTag()==1) {
                    punchCalendarHolder.mTvItemPunchCalendar.setBackgroundResource(R.drawable.shape_calendar_pass);
                }else if (punchCalendarBean.getTag()==7){
                    punchCalendarHolder.mImgItemPunchCalendarLeft.setBackgroundResource(R.drawable.shape_calendar_other_right_pass);
                }else{
                    punchCalendarHolder.mImgItemPunchCalendarLeft.setBackgroundResource(R.drawable.shape_calendar_other_right_pass);
                }
            }else if (status.equalsIgnoreCase(lastStatus) && status.equalsIgnoreCase("rejected") && !status.equalsIgnoreCase(nextStatus)) {
                if (punchCalendarBean.getTag()==1) {
                    punchCalendarHolder.mTvItemPunchCalendar.setBackgroundResource(R.drawable.shape_calendar_nopass);
                }else if (punchCalendarBean.getTag()==7){
                    punchCalendarHolder.mImgItemPunchCalendarLeft.setBackgroundResource(R.drawable.shape_calendar_other_right_nopass);
                }else{
                    punchCalendarHolder.mImgItemPunchCalendarLeft.setBackgroundResource(R.drawable.shape_calendar_other_right_nopass);
                }
            }

            if (!status.equalsIgnoreCase(lastStatus) && status.equalsIgnoreCase("accepted") && status.equalsIgnoreCase(nextStatus)) {
                if (punchCalendarBean.getTag()==1) {
                    punchCalendarHolder.mImgItemPunchCalendarRight.setBackgroundResource(R.drawable.shape_calendar_other_left_pass);
                }else if (punchCalendarBean.getTag()==7){
                    punchCalendarHolder.mTvItemPunchCalendar.setBackgroundResource(R.drawable.shape_calendar_pass);
                }else{
                    punchCalendarHolder.mImgItemPunchCalendarRight.setBackgroundResource(R.drawable.shape_calendar_other_left_pass);
                }
            }else if (!status.equalsIgnoreCase(lastStatus) && status.equalsIgnoreCase("rejected") && status.equalsIgnoreCase(nextStatus)) {
                if (punchCalendarBean.getTag()==1) {
                    punchCalendarHolder.mImgItemPunchCalendarRight.setBackgroundResource(R.drawable.shape_calendar_other_left_nopass);
                }else if (punchCalendarBean.getTag()==7){
                    punchCalendarHolder.mTvItemPunchCalendar.setBackgroundResource(R.drawable.shape_calendar_nopass);
                }else{
                    punchCalendarHolder.mImgItemPunchCalendarRight.setBackgroundResource(R.drawable.shape_calendar_other_left_nopass);
                }
            }

        }
        if (punchCalendarBean.isAfterDay()) {
            punchCalendarHolder.mTvItemPunchCalendar.setTextColor(Color.parseColor("#FFCCCCCC"));
        }else{
            punchCalendarHolder.mTvItemPunchCalendar.setTextColor(Color.parseColor("#FF333333"));
        }
        punchCalendarHolder.mTvItemPunchCalendar.setText(punchCalendarBean.getDay_index());
        if (!punchCalendarBean.isEmptyBean() && !punchCalendarBean.isAfterDay()) {
            punchCalendarHolder.mRlItemPunchCalendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CURRENT_DATE_STR=punchCalendarBean.getDateStr();
                    EventBus.getDefault().post(punchCalendarBean);
                    if (mOnItemClickListener!=null) {
                        mOnItemClickListener.onClick();
                    }
                }
            });
        }else{
            punchCalendarHolder.mRlItemPunchCalendar.setOnClickListener(null);
        }

    }

    @Override
    public int getItemCount() {
        return mPunchCalendarBeans.size();
    }

    public class PunchCalendarHolder extends RecyclerView.ViewHolder {
        private TextView mTvItemPunchCalendar;
        private ImageView mImgItemPunchCalendarLeft;
        private ImageView mImgItemPunchCalendarRight;
        private ImageView mImgItemPunchCalendarPoint;
        private RelativeLayout mRlItemPunchCalendar;
        private ImageView mImgItemPunchCalendar;

        public PunchCalendarHolder(@NonNull View itemView) {
            super(itemView);
            mTvItemPunchCalendar = (TextView) itemView.findViewById(R.id.tv_item_punch_calendar);
            mImgItemPunchCalendarLeft = (ImageView) itemView.findViewById(R.id.img_item_punch_calendar_left);
            mImgItemPunchCalendarRight = (ImageView) itemView.findViewById(R.id.img_item_punch_calendar_right);
            mImgItemPunchCalendarPoint = (ImageView) itemView.findViewById(R.id.img_item_punch_calendar_point);
            mRlItemPunchCalendar = (RelativeLayout) itemView.findViewById(R.id.rl_item_punch_calendar);
            mImgItemPunchCalendar = (ImageView) itemView.findViewById(R.id.img_item_punch_calendar);
        }

    }

    public interface OnItemClickListener{
        public void onClick();
    }

}
