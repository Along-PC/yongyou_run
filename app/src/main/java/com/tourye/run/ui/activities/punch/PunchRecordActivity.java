package com.tourye.run.ui.activities.punch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.CalendarPunchBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.PunchRecordAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:   PunchRecordActivity
 *
 * @Author:   along
 *
 * @Description:    打卡记录页面
 *
 * @CreateDate:   2019/4/10 3:43 PM
 *
 */
public class PunchRecordActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mLlFragmentPunchDate;
    private ImageView mImgActivityPunchRecordLast;
    private TextView mTvActivityPunchRecordCurrent;
    private ImageView mImgActivityPunchRecordNext;
    private RecyclerView mRecyclerActivityPunchRecord;
    private RelativeLayout mRlActivityPunchRecordNoContent;
    private RelativeLayout mRlActivityPunchRecordLast;
    private RelativeLayout mRlActivityPunchRecordNext;

    private int mOffset;//距离当前月份的偏移量

    private List<CalendarPunchBean.DataBeanX.DataBean> mDataBeans=new ArrayList<>();
    private PunchRecordAdapter mPunchRecordAdapter;

    @Override
    public void initView() {
        mLlFragmentPunchDate = (LinearLayout) findViewById(R.id.ll_fragment_punch_date);
        mImgActivityPunchRecordLast = (ImageView) findViewById(R.id.img_activity_punch_record_last);
        mTvActivityPunchRecordCurrent = (TextView) findViewById(R.id.tv_activity_punch_record_current);
        mImgActivityPunchRecordNext = (ImageView) findViewById(R.id.img_activity_punch_record_next);
        mRecyclerActivityPunchRecord = (RecyclerView) findViewById(R.id.recycler_activity_punch_record);
        mRlActivityPunchRecordNoContent = (RelativeLayout) findViewById(R.id.rl_activity_punch_record_noContent);
        mRlActivityPunchRecordLast = (RelativeLayout) findViewById(R.id.rl_activity_punch_record_last);
        mRlActivityPunchRecordNext = (RelativeLayout) findViewById(R.id.rl_activity_punch_record_next);

        mRlActivityPunchRecordLast.setOnClickListener(this);
        mRlActivityPunchRecordNext.setOnClickListener(this);

        mTvTitle.setText("打卡记录");
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 998);
        if (type==1) {
            mOffset=intent.getIntExtra("offset",998);
        }

        mPunchRecordAdapter = new PunchRecordAdapter(mActivity);
        mRecyclerActivityPunchRecord.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerActivityPunchRecord.setAdapter(mPunchRecordAdapter);

        getCalendarData();

    }

    public void getYearAndMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,mOffset);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
        String yearAndMonth = simpleDateFormat.format(calendar.getTime());
        mTvActivityPunchRecordCurrent.setText(yearAndMonth);
    }

    /**
     * 获取日历打卡记录
     */
    public void getCalendarData(){

        getYearAndMonth();

        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MONTH, mOffset);
        int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH)+1;
        Map<String,String> map=new HashMap<>();
        map.put("month",year+"-"+month);
        HttpUtils.getInstance().get(Constants.CALENDAR_PUNCH_RECORD, map, new HttpCallback<CalendarPunchBean>() {
            @Override
            public void onSuccessExecute(CalendarPunchBean calendarPunchBean) {
                List<CalendarPunchBean.DataBeanX> data = calendarPunchBean.getData();
                if (data==null) {
                    mRlActivityPunchRecordNoContent.setVisibility(View.VISIBLE);
                    mRecyclerActivityPunchRecord.setVisibility(View.GONE);
                    return;
                }
                mDataBeans.clear();
                for (int i = 0; i < data.size(); i++) {
                    CalendarPunchBean.DataBeanX dataBeanX = data.get(i);
                    List<CalendarPunchBean.DataBeanX.DataBean> datas = dataBeanX.getData();
                    if (datas!=null) {
                        mDataBeans.addAll(datas);
                    }
                }
                if (mDataBeans.size()>0) {
                    mRlActivityPunchRecordNoContent.setVisibility(View.GONE);
                    mRecyclerActivityPunchRecord.setVisibility(View.VISIBLE);
                    Collections.reverse(mDataBeans);
                    mPunchRecordAdapter.setDataBeans(mDataBeans);
                }else{
                    mRlActivityPunchRecordNoContent.setVisibility(View.VISIBLE);
                    mRecyclerActivityPunchRecord.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.activity_punch_record;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_activity_punch_record_last:
                mOffset--;
                getCalendarData();
                break;
            case R.id.rl_activity_punch_record_next:
                mOffset++;
                getCalendarData();
                break;
        }
    }
}
