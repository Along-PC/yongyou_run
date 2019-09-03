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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.CalendarPunchBean;
import com.tourye.run.bean.TraceSubmitBean;
import com.tourye.run.ui.activities.common.SingleImageActivity;
import com.tourye.run.ui.activities.punch.PunchRecordActivity;
import com.tourye.run.ui.activities.punch.RunningResultActivity;
import com.tourye.run.ui.activities.punch.ScreenPunchResultActivity;
import com.tourye.run.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName:   PunchRecordAdapter
 *
 * @Author:   along
 *
 * @Description:    打卡记录适配器
 *
 * @CreateDate:   2019/4/12 1:36 PM
 *
 */
public class PunchRecordAdapter extends RecyclerView.Adapter<PunchRecordAdapter.PunchRecordHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<CalendarPunchBean.DataBeanX.DataBean> mDataBeans=new ArrayList<>();

    public PunchRecordAdapter(Context context) {
        mContext = context;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDataBeans(List<CalendarPunchBean.DataBeanX.DataBean> dataBeans) {
        mDataBeans = dataBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PunchRecordHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PunchRecordHolder(mLayoutInflater.inflate(R.layout.item_activity_punch_record,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PunchRecordHolder punchRecordHolder, int i) {
        final CalendarPunchBean.DataBeanX.DataBean dataBean = mDataBeans.get(i);
        GlideUtils.getInstance().loadRoundImage(dataBean.getImage(),punchRecordHolder.mImgItemActivityPunchRecordHead);
        punchRecordHolder.mImgItemActivityPunchRecordHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SingleImageActivity.class);
                intent.putExtra("url",dataBean.getImage());
                mContext.startActivity(intent);
            }
        });
        punchRecordHolder.mTvItemActivityPunchRecordDistance.setText(dataBean.getDistance()+"km");
        //打卡时间
        int time = dataBean.getTime();
        int hour = time / 60;
        int minute = time % 60;
        //格式化时间
        String hour_str = String.format("%0"+2+"d", hour);
        String minute_str = String.format("%0"+2+"d", minute);
        //打卡距离
        String distance = dataBean.getDistance();
        double parseInt = Double.parseDouble(distance);
        //配速
        int speed_minute = (int) Math.floor(time / parseInt);
        int speed_second = (int) (time*1.0f % parseInt / parseInt * 60);
        punchRecordHolder.mTvItemActivityPunchRecordTime.setText(hour_str+":"+minute_str+":00   "+speed_minute+"'"+speed_second+"''");
        punchRecordHolder.mTvItemActivityPunchRecordDate.setText(dataBean.getSign_in_time());
        punchRecordHolder.mRlItemActivityPunchRecordContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trace_id = dataBean.getTrace_id();
                if (TextUtils.isEmpty(trace_id)) {
                    Intent intent = new Intent(mContext, ScreenPunchResultActivity.class);
                    intent.putExtra("punch_id",dataBean.getSign_in_id());
                    mContext.startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext, RunningResultActivity.class);
                    intent.putExtra("trace_data",trace_id);
                    intent.putExtra("punch_time",dataBean.getSign_in_time());
                    intent.putExtra("punch_id",dataBean.getSign_in_id());
                    intent.putExtra("punch_image",dataBean.getImage());
                    intent.putExtra("punch_distance",dataBean.getDistance());
                    intent.putExtra("type",2);
                    mContext.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public class PunchRecordHolder extends RecyclerView.ViewHolder {

        private ImageView mImgItemActivityPunchRecordHead;
        private TextView mTvItemActivityPunchRecordDescription;
        private TextView mTvItemActivityPunchRecordDistance;
        private TextView mTvItemActivityPunchRecordTime;
        private TextView mTvItemActivityPunchRecordDate;
        private ImageView mImgItemActivityPunchRecordArrow;
        private RelativeLayout mRlItemActivityPunchRecordContent;

        public PunchRecordHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemActivityPunchRecordHead = (ImageView) itemView.findViewById(R.id.img_item_activity_punch_record_head);
            mTvItemActivityPunchRecordDescription = (TextView) itemView.findViewById(R.id.tv_item_activity_punch_record_description);
            mTvItemActivityPunchRecordDistance = (TextView) itemView.findViewById(R.id.tv_item_activity_punch_record_distance);
            mTvItemActivityPunchRecordTime = (TextView) itemView.findViewById(R.id.tv_item_activity_punch_record_time);
            mTvItemActivityPunchRecordDate = (TextView) itemView.findViewById(R.id.tv_item_activity_punch_record_date);
            mImgItemActivityPunchRecordArrow = (ImageView) itemView.findViewById(R.id.img_item_activity_punch_record_arrow);
            mRlItemActivityPunchRecordContent = (RelativeLayout) itemView.findViewById(R.id.rl_item_activity_punch_record_content);
        }

    }
}
