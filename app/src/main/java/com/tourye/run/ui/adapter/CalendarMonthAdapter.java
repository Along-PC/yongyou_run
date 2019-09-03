package com.tourye.run.ui.adapter;

import android.content.Context;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.bean.PunchCalendarBean;
import com.tourye.run.bean.PunchCalendarRawBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.utils.DateFormatUtils;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: CalendarMonthAdapter
 * @Author: along
 * @Description: 打卡日历viewpager适配器
 * @CreateDate: 2019/5/27 11:44 AM
 */
public class CalendarMonthAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private String mDate="";
    private String mTeam_id="";
    private String mUser_id="";

    public static int INIT_POSITION = 500;

    public static String CURRENT_DATE_STR = "";

    public CalendarMonthAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDate(String date, String team_id, String user_id, String current_date) {
        mDate = date;
        mTeam_id = team_id;
        mUser_id = user_id;
        CURRENT_DATE_STR = current_date;
    }

    @Override
    public int getCount() {
        return INIT_POSITION*2;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View inflate = mLayoutInflater.inflate(R.layout.item_activity_create_vp, container, false);
        RecyclerView recyclerView = inflate.findViewById(R.id.recycler_item_activity_create_vp);
        List<PunchCalendarBean> monthCalendarBeans = new ArrayList<>();
        final PunchCalendarAdapter punchCalendarAdapter = new PunchCalendarAdapter(mContext,monthCalendarBeans);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 7));
        recyclerView.setAdapter(punchCalendarAdapter);
        punchCalendarAdapter.setOnItemClickListener(new PunchCalendarAdapter.OnItemClickListener() {
            @Override
            public void onClick() {
                notifyDataSetChanged();
            }
        });
        initMonth(position - INIT_POSITION, recyclerView, monthCalendarBeans, punchCalendarAdapter);
        container.addView(inflate);
        return inflate;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public void initMonth(final int offset, RecyclerView recyclerView, final List<PunchCalendarBean> monthCalendarBeans, final PunchCalendarAdapter punchCalendarAdapter) {
//        io.reactivex.Observable
//                .create(new ObservableOnSubscribe<List<PunchCalendarBean>>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<List<PunchCalendarBean>> e) throws Exception {
//                        e.onNext(monthCalendarBeans);
//                    }
//                })
//                .map(new Function<List<PunchCalendarBean>, List<PunchCalendarBean>>() {
//                    @Override
//                    public List<PunchCalendarBean> apply(List<PunchCalendarBean> punchCalendarBeans) throws Exception {
//                        Calendar calendar = Calendar.getInstance();
//                        Date todayDate = calendar.getTime();
//                        String todayStr = DateFormatUtils.formatDate(todayDate);
//                        Date currentDate = DateFormatUtils.getDate(todayStr);
//
//                        calendar.add(Calendar.MONTH, offset);
//                        calendar.set(Calendar.DAY_OF_MONTH, 1);
//                        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
//                        int first = calendar.get(Calendar.DAY_OF_WEEK);//本月第一天是周几
//
//                        monthCalendarBeans.clear();
//                        for (int i = 1; i < first; i++) {
//                            PunchCalendarBean punchCalendarBean = new PunchCalendarBean();
//                            punchCalendarBean.setEmptyBean(true);
//                            monthCalendarBeans.add(punchCalendarBean);
//                        }
//                        for (int i = 0; i < day; i++) {
//                            calendar.set(Calendar.DAY_OF_MONTH, 1 + i);
//                            Date time = calendar.getTime();
//                            String dateStr = DateFormatUtils.formatDate(time);
//                            Date date = DateFormatUtils.getDate(dateStr);
//                            PunchCalendarBean calendarBean = new PunchCalendarBean();
//                            if (dateStr.equalsIgnoreCase(mDate)) {
//                                calendarBean.setShowPoint(true);
//                            }
//                            if (date.after(currentDate)) {
//                                calendarBean.setAfterDay(true);
//                            }
//                            calendarBean.setDateStr(dateStr);
//                            calendarBean.setDate(date);
//                            calendarBean.setTag(calendar.get(Calendar.DAY_OF_WEEK));
//                            calendarBean.setDay_index(1 + i + "");
//                            monthCalendarBeans.add(calendarBean);
//                        }
//
//                        for (int i = 0; i < monthCalendarBeans.size(); i++) {
//                            PunchCalendarBean punchCalendarCurrent = monthCalendarBeans.get(i);
//                            if (punchCalendarCurrent.getDate() == null) {
//                                continue;
//                            }
//                            if (i > 0) {
//                                PunchCalendarBean punchCalendarLast = monthCalendarBeans.get(i - 1);
//                                punchCalendarLast.setNextStatus(punchCalendarCurrent.getStatus());
//                            }
//                            if (i < monthCalendarBeans.size() - 1) {
//                                PunchCalendarBean punchCalendarNext = monthCalendarBeans.get(i + 1);
//                                punchCalendarNext.setLastStatus(punchCalendarCurrent.getStatus());
//                            }
//                        }
//                        return monthCalendarBeans;
//                    }
//                })
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<PunchCalendarBean>>() {
//                    @Override
//                    public void accept(List<PunchCalendarBean> punchCalendarBeans) throws Exception {
//
//                        punchCalendarAdapter.setPunchCalendarBeans(punchCalendarBeans);
//                        getPunchCalendarData(toYearAndMonth(mDate, offset), monthCalendarBeans, punchCalendarAdapter);
//                    }
//                });
        Calendar calendar = Calendar.getInstance();
        Date todayDate = calendar.getTime();
        String todayStr = DateFormatUtils.formatDate(todayDate);
        Date currentDate = DateFormatUtils.getDate(todayStr);

        calendar.add(Calendar.MONTH, offset);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
        int first = calendar.get(Calendar.DAY_OF_WEEK);//本月第一天是周几

        monthCalendarBeans.clear();
        for (int i = 1; i < first; i++) {
            PunchCalendarBean punchCalendarBean = new PunchCalendarBean();
            punchCalendarBean.setEmptyBean(true);
            monthCalendarBeans.add(punchCalendarBean);
        }
        for (int i = 0; i < day; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, 1 + i);
            Date time = calendar.getTime();
            String dateStr = DateFormatUtils.formatDate(time);
            Date date = DateFormatUtils.getDate(dateStr);
            PunchCalendarBean calendarBean = new PunchCalendarBean();
            if (dateStr.equalsIgnoreCase(mDate)) {
                calendarBean.setShowPoint(true);
            }
            if (date.after(currentDate)) {
                calendarBean.setAfterDay(true);
            }
            calendarBean.setDateStr(dateStr);
            calendarBean.setDate(date);
            calendarBean.setTag(calendar.get(Calendar.DAY_OF_WEEK));
            calendarBean.setDay_index(1 + i + "");
            monthCalendarBeans.add(calendarBean);
        }

        for (int i = 0; i < monthCalendarBeans.size(); i++) {
            PunchCalendarBean punchCalendarCurrent = monthCalendarBeans.get(i);
            if (punchCalendarCurrent.getDate() == null) {
                continue;
            }
            if (i > 0) {
                PunchCalendarBean punchCalendarLast = monthCalendarBeans.get(i - 1);
                punchCalendarLast.setNextStatus(punchCalendarCurrent.getStatus());
            }
            if (i < monthCalendarBeans.size() - 1) {
                PunchCalendarBean punchCalendarNext = monthCalendarBeans.get(i + 1);
                punchCalendarNext.setLastStatus(punchCalendarCurrent.getStatus());
            }
        }

        punchCalendarAdapter.setPunchCalendarBeans(monthCalendarBeans);

        getPunchCalendarData(toYearAndMonth(mDate, offset), monthCalendarBeans, punchCalendarAdapter);
    }

    /**
     * 获取上线月份
     *
     * @param date
     * @return
     */
    public String toYearAndMonth(String date, int offset) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");
        if (date == null) {
            return "";
        }
        try {
            Date parse = sdf.parse(date);
            Calendar instance = Calendar.getInstance();
            instance.setTime(parse);
            instance.add(Calendar.MONTH, offset);
            parse = instance.getTime();
            String format = sdfMonth.format(parse);
            return format;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void getPunchCalendarData(String month, final List<PunchCalendarBean> monthCalendarBeans, final PunchCalendarAdapter punchCalendarAdapter) {
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
                    if (punchList != null) {
                        for (int i = 0; i < punchList.size(); i++) {
                            PunchCalendarRawBean.DataBeanX.DataBean dataBean = punchList.get(i);
                            String dateStr = dataBean.getDate();
                            Date date = DateFormatUtils.getDate(dateStr);
                            if (date == null) {
                                continue;
                            }
                            for (int i1 = 0; i1 < monthCalendarBeans.size(); i1++) {
                                PunchCalendarBean punchCalendarBean = monthCalendarBeans.get(i1);
                                Date dateLast = punchCalendarBean.getDate();
                                if (dateLast == null) {
                                    continue;
                                }
                                if (date.compareTo(dateLast) == 0) {
                                    punchCalendarBean.setStatus(dataBean.getStatus());
                                    punchCalendarBean.setDistance(dataBean.getDistance());
                                }
                            }
                        }

                        for (int i = 0; i < monthCalendarBeans.size(); i++) {
                            PunchCalendarBean punchCalendarCurrent = monthCalendarBeans.get(i);
                            if (punchCalendarCurrent.getDate() == null) {
                                continue;
                            }
                            if (i > 0) {
                                PunchCalendarBean punchCalendarLast = monthCalendarBeans.get(i - 1);
                                punchCalendarLast.setNextStatus(punchCalendarCurrent.getStatus());
                            }
                            if (i < monthCalendarBeans.size() - 1) {
                                PunchCalendarBean punchCalendarNext = monthCalendarBeans.get(i + 1);
                                punchCalendarNext.setLastStatus(punchCalendarCurrent.getStatus());
                            }
                        }
                        punchCalendarAdapter.setPunchCalendarBeans(monthCalendarBeans);
                    }
                }
            }
        });

    }

}
