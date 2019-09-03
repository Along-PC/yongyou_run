package com.tourye.run.ui.activities.mine;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.necer.calendar.BaseCalendar;
import com.necer.calendar.MonthCalendar;
import com.necer.listener.OnCalendarChangedListener;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.PunchCalendarBean;
import com.tourye.run.bean.PunchCalendarRawBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.CalendarMonthAdapter;
import com.tourye.run.ui.adapter.PunchCalendarAdapter;
import com.tourye.run.utils.DateFormatUtils;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.PunchCalendarPainter;
import com.tourye.run.utils.SaveUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tourye.run.ui.adapter.CalendarMonthAdapter.INIT_POSITION;

/**
 * @ClassName: PunchCalendarActivity
 * @Author: along
 * @Description: 打卡日历
 * @CreateDate: 2019/4/22 4:39 PM
 */
public class PunchCalendarActivity extends BaseActivity {

    private ImageView mImgActivityPunchCalendarHead;
    private TextView mTvActivityPunchCalendarName;
    private TextView mTvActivityPunchCalendarInsist;
    private TextView mTvActivityPunchCalendarDays;
    private TextView mTvActivityPunchCalendarTotal;
    private LinearLayout mLlActivityPunchCalendarStatus;
    private LinearLayout mLlActivityPunchCalendarCall;
    private TextView mTvActivityPunchCalendarNopass;
    private TextView mTvActivityPunchCalendarToday;
    private TextView mTvActivityPunchCalendarDate;
    private MonthCalendar mDatepickerActivityPunchCalendar;

    private String mTeam_id;//队伍id
    private String mUser_id;//用户id
    private String mDate;//所选记录日期
    private PunchCalendarPainter mPunchCalendarPainter;

    private List<LocalDate> mPassDays = new ArrayList<>();//通过的天数集合
    private List<LocalDate> mNoPassdays = new ArrayList<>();//不通过的天数集合

    @Override
    public void initView() {
        mImgActivityPunchCalendarHead = (ImageView) findViewById(R.id.img_activity_punch_calendar_head);
        mTvActivityPunchCalendarName = (TextView) findViewById(R.id.tv_activity_punch_calendar_name);
        mTvActivityPunchCalendarInsist = (TextView) findViewById(R.id.tv_activity_punch_calendar_insist);
        mTvActivityPunchCalendarDays = (TextView) findViewById(R.id.tv_activity_punch_calendar_days);
        mTvActivityPunchCalendarTotal = (TextView) findViewById(R.id.tv_activity_punch_calendar_total);
        mLlActivityPunchCalendarStatus = (LinearLayout) findViewById(R.id.ll_activity_punch_calendar_status);
        mLlActivityPunchCalendarCall = (LinearLayout) findViewById(R.id.ll_activity_punch_calendar_call);
        mTvActivityPunchCalendarNopass = (TextView) findViewById(R.id.tv_activity_punch_calendar_nopass);
        mTvActivityPunchCalendarToday = (TextView) findViewById(R.id.tv_activity_punch_calendar_today);
        mTvActivityPunchCalendarDate = (TextView) findViewById(R.id.tv_activity_punch_calendar_date);
        mDatepickerActivityPunchCalendar = (MonthCalendar) findViewById(R.id.datepicker_activity_punch_calendar);

        mTvTitle.setText("打卡日历");

        initCalendar();

    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        mTeam_id = intent.getStringExtra("team_id");
        mUser_id = intent.getStringExtra("user_id");
        mDate = intent.getStringExtra("month");

        Calendar calendar = Calendar.getInstance();
        Date todayDate = calendar.getTime();
        mTvActivityPunchCalendarDate.setText(DateFormatUtils.formatDate(todayDate));

    }

    @Override
    public boolean isNeedUpdateTypefaceSize() {
        return false;
    }

    /**
     * 判断选择日期是否在活动期间
     * @param date
     * @param yearAndMonth
     * @param localDate
     */
    public void judgeDate(String date,String yearAndMonth,LocalDate localDate){
        String finish_date = SaveUtil.getString(SaveConstants.SIGN_IN_FINISH_DATE, "");
        String start_date = SaveUtil.getString(SaveConstants.SIGN_IN_START_DATE, "");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        Date finishDate;
        Date chooseDate;
        try {
            startDate=simpleDateFormat.parse(start_date);
            finishDate=simpleDateFormat.parse(finish_date);
            chooseDate=simpleDateFormat.parse(date);
            int i = chooseDate.compareTo(startDate);
            int i1 = chooseDate.compareTo(finishDate);
            if (i>=0 && i1<=0) {
                mLlActivityPunchCalendarStatus.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(mActivity, "超出队长管理时限，无法查看", Toast.LENGTH_SHORT).show();
                mLlActivityPunchCalendarStatus.setVisibility(View.INVISIBLE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化日历控件
     */
    private void initCalendar() {
        mPunchCalendarPainter = new PunchCalendarPainter(mActivity, mDatepickerActivityPunchCalendar);
        mDatepickerActivityPunchCalendar.setCalendarPainter(mPunchCalendarPainter);
        mDatepickerActivityPunchCalendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, LocalDate localDate) {
//                tv_result.setText(year + "年" + month + "月" + "   当前页面选中 " + localDate);
                mTvActivityPunchCalendarDate.setText(localDate + "");
                Date date = localDate.toDate();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String yearAndMonth = simpleDateFormat.format(date);
                String currentDate = sdf.format(date);

                judgeDate(currentDate,yearAndMonth,localDate);
                getCheckResultData(yearAndMonth, localDate);

            }
        });

    }

    public void getCheckResultData(String month, final LocalDate localDate) {
        mPassDays.clear();
        mNoPassdays.clear();
        Map<String, String> map = new HashMap<>();
        map.put("activity_id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        map.put("team_id", mTeam_id);
        map.put("user_id", mUser_id);
        map.put("month", month);
        HttpUtils.getInstance().get(Constants.TEAM_SIGN_IN_CALENDAR, map, new HttpCallback<PunchCalendarRawBean>() {
            @Override
            public void onSuccessExecute(PunchCalendarRawBean punchCalendarRawBean) {
                PunchCalendarRawBean.DataBeanX data = punchCalendarRawBean.getData();
                if (data != null) {
                    List<PunchCalendarRawBean.DataBeanX.DataBean> punchList = data.getData();
                    mTvActivityPunchCalendarName.setText(data.getNickname());
                    mTvActivityPunchCalendarInsist.setText("已坚持" + data.getTotal_days() + "天");
                    mTvActivityPunchCalendarDays.setText("未来" + data.getFuture_day() + "天需打卡" + data.getNeed_day() + "天");
                    mTvActivityPunchCalendarTotal.setText("累计打卡" + data.getTotal_distance() + "km");
                    GlideUtils.getInstance().loadCircleImage(data.getAvatar(),mImgActivityPunchCalendarHead);
                    if (punchList != null) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date chooseDate = localDate.toDate();
                        boolean canContinue = true;
                        for (int i = 0; i < punchList.size(); i++) {
                            final PunchCalendarRawBean.DataBeanX.DataBean dataBean = punchList.get(i);
                            String date_str = dataBean.getDate();
                            final String status = dataBean.getStatus();
                            try {
                                Date parse = simpleDateFormat.parse(date_str);
                                if ("accepted".equalsIgnoreCase(status)) {
                                    mPassDays.add(LocalDate.fromDateFields(parse));
                                } else if ("rejected".equalsIgnoreCase(status)) {
                                    mNoPassdays.add(LocalDate.fromDateFields(parse));
                                }
                                if (canContinue) {
                                    if (chooseDate.compareTo(parse) == 0) {
                                        canContinue = false;
                                        switch (status) {
                                            case "rejected":
                                                mLlActivityPunchCalendarCall.setVisibility(View.VISIBLE);
                                                mTvActivityPunchCalendarToday.setText("打卡被拒绝!");
                                                mTvActivityPunchCalendarNopass.setVisibility(View.VISIBLE);
                                                break;
                                            case "accepted":
                                                mLlActivityPunchCalendarCall.setVisibility(View.GONE);
                                                mTvActivityPunchCalendarToday.setText("今日已打卡，打卡" + dataBean.getDistance() + "km");
                                                mTvActivityPunchCalendarNopass.setVisibility(View.INVISIBLE);
                                                break;
                                            case "no_sign_in":
                                                mLlActivityPunchCalendarCall.setVisibility(View.VISIBLE);
                                                mTvActivityPunchCalendarToday.setText("今日未打卡");
                                                mTvActivityPunchCalendarNopass.setVisibility(View.INVISIBLE);
                                                break;
                                            default:
                                                break;
                                        }
                                        mLlActivityPunchCalendarCall.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dataBean.getMobile()));//跳转到拨号界面，同时传递电话号码
                                                startActivity(dialIntent);
                                            }
                                        });
                                    } else {
                                        mLlActivityPunchCalendarCall.setVisibility(View.GONE);
                                        mTvActivityPunchCalendarToday.setText("今日未打卡");
                                        mTvActivityPunchCalendarNopass.setVisibility(View.INVISIBLE);
                                        mLlActivityPunchCalendarCall.setOnClickListener(null);
                                    }
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        mPunchCalendarPainter.setPunchResult(mPassDays, mNoPassdays);
                    }
                }
            }
        });

    }

    @Override
    public int getRootView() {
        return R.layout.activity_punch_calendar;
    }

}
