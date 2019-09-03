package com.tourye.run.ui.dialogs;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.TeamManageCalendarBean;
import com.tourye.run.ui.adapter.TeamManageCalendarAdapter;
import com.tourye.run.utils.DateFormatUtils;
import com.tourye.run.utils.DensityUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: TeamManageCalendarDialog
 * @Author: along
 * @Description: 队伍管理日历弹窗
 * @CreateDate: 2019/5/27 9:50 AM
 */
public class TeamManageCalendarDialog extends BaseDialog implements View.OnClickListener {
    private ImageView mImgDialogTeamManageCalendarLast;
    private TextView mTvDialogTeamManageCalendarCurrent;
    private ImageView mImgDialogTeamManageCalendarNext;
    private RecyclerView mRecyclerDialogTeamManageCalendarMonth;
    private RelativeLayout mRlDialogTeamManageCalendarLast;
    private RelativeLayout mRlDialogTeamManageCalendarNext;

    private int mOffset;
    private List<TeamManageCalendarBean> mTeamManageCalendarBeans;
    private TeamManageCalendarAdapter mTeamManageCalendarAdapter;

    private OnChooseListener mOnChooseListener;

    public TeamManageCalendarDialog(Context context) {
        super(context);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.y = DensityUtils.dp2px(44);
        window.setAttributes(params);
        layoutParams.gravity = Gravity.TOP;
        getWindow().setAttributes(layoutParams);
    }

    public void setOnChooseListener(OnChooseListener onChooseListener) {
        mOnChooseListener = onChooseListener;
        if (mTeamManageCalendarAdapter != null) {
            mTeamManageCalendarAdapter.setOnItemClickListener(new TeamManageCalendarAdapter.OnItemClickListener() {
                @Override
                public void onClick(String date) {
                    mOnChooseListener.onChoose(date);
                    hide();
                }
            });
        }
    }

    @Override
    protected void initData() {
        mTeamManageCalendarBeans = new ArrayList<>();
        initMonth(0);
        mRlDialogTeamManageCalendarLast.setOnClickListener(this);
        mRlDialogTeamManageCalendarNext.setOnClickListener(this);
    }

    public void initMonth(int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date currentDate = calendar.getTime();
        mOffset += offset;
        calendar.add(Calendar.MONTH, mOffset);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
        int first = calendar.get(Calendar.DAY_OF_WEEK);

        mTvDialogTeamManageCalendarCurrent.setText(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月");

        mTeamManageCalendarBeans.clear();
        for (int i = 0; i < first - 1; i++) {
            mTeamManageCalendarBeans.add(new TeamManageCalendarBean());
        }
        for (int i = 0; i < day; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, 1 + i);
            Date time = calendar.getTime();
            String date = DateFormatUtils.formatDate(time);
            TeamManageCalendarBean teamManageCalendarBean = new TeamManageCalendarBean();
            if (time.compareTo(currentDate) == 0) {
                teamManageCalendarBean.setIs_selected(true);
            }
            teamManageCalendarBean.setDate(time);
            teamManageCalendarBean.setDateOfMonth(date);
            teamManageCalendarBean.setDate_index(1 + i + "");
            mTeamManageCalendarBeans.add(teamManageCalendarBean);
        }
        if (mTeamManageCalendarAdapter == null) {
            mTeamManageCalendarAdapter = new TeamManageCalendarAdapter(mContext, mTeamManageCalendarBeans);
            mRecyclerDialogTeamManageCalendarMonth.setLayoutManager(new GridLayoutManager(mContext, 7));
            mRecyclerDialogTeamManageCalendarMonth.setAdapter(mTeamManageCalendarAdapter);
        } else {
            mTeamManageCalendarAdapter.setTeamManageCalendarBeans(mTeamManageCalendarBeans);
        }
    }

    @Override
    protected void initView() {
        mImgDialogTeamManageCalendarLast = (ImageView) findViewById(R.id.img_dialog_team_manage_calendar_last);
        mTvDialogTeamManageCalendarCurrent = (TextView) findViewById(R.id.tv_dialog_team_manage_calendar_current);
        mImgDialogTeamManageCalendarNext = (ImageView) findViewById(R.id.img_dialog_team_manage_calendar_next);
        mRecyclerDialogTeamManageCalendarMonth = (RecyclerView) findViewById(R.id.recycler_dialog_team_manage_calendar_month);
        mRlDialogTeamManageCalendarLast = (RelativeLayout) findViewById(R.id.rl_dialog_team_manage_calendar_last);
        mRlDialogTeamManageCalendarNext = (RelativeLayout) findViewById(R.id.rl_dialog_team_manage_calendar_next);

    }

    @Override
    protected int getRootView() {
        return R.layout.dialog_team_manage_calendar;
    }

    @Override
    public boolean isLocationBottom() {
        return false;
    }

    @Override
    protected boolean isNeedAnimation() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_dialog_team_manage_calendar_last:
                initMonth(-1);
                break;
            case R.id.rl_dialog_team_manage_calendar_next:
                initMonth(1);
                break;
        }
    }

    public interface OnChooseListener {
        public void onChoose(String date);
    }
}
