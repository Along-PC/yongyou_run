package com.tourye.run.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.bean.DistrictsBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.views.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by longlongren on 2018/9/1.
 * <p>
 * introduce:打卡时间弹框
 */

public class PunchTimeDialog extends Dialog {

    private TextView mTvDialogPunchTimeCancel;
    private TextView mTvDialogPunchTimeCertain;
    private WheelView mWheelDialogPunchTimeHour;
    private WheelView mWheelDialogPunchTimeMinute;

    private OnChooseResultListener mOnChooseResultListener;

    public PunchTimeDialog(@NonNull Context context) {
        super(context);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_punch_time);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);

        getWindow().setWindowAnimations(R.style.HeadDialogStyle);

        mTvDialogPunchTimeCancel = (TextView) findViewById(R.id.tv_dialog_punch_time_cancel);
        mTvDialogPunchTimeCertain = (TextView) findViewById(R.id.tv_dialog_punch_time_certain);
        mWheelDialogPunchTimeHour = (WheelView) findViewById(R.id.wheel_dialog_punch_time_hour);
        mWheelDialogPunchTimeMinute = (WheelView) findViewById(R.id.wheel_dialog_punch_time_minute);

        mTvDialogPunchTimeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mTvDialogPunchTimeCertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnChooseResultListener!=null) {
                    int hour = mWheelDialogPunchTimeHour.getSelected();
                    int minute = mWheelDialogPunchTimeMinute.getSelected();
                    mOnChooseResultListener.onChoose(hour,minute);
                }
                dismiss();
            }
        });

        initData();

    }

    private void initData() {
        ArrayList<String> minuteList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            minuteList.add((i)+"分钟");
        }
        mWheelDialogPunchTimeMinute.setData(minuteList);

        ArrayList<String> hourList = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            hourList.add((i)+"小时");
        }
        mWheelDialogPunchTimeHour.setData(hourList);

        mWheelDialogPunchTimeHour.setDefault(0);
        mWheelDialogPunchTimeMinute.setDefault(0);

    }

    public void setOnChooseResultListener(OnChooseResultListener onChooseResultListener) {
        mOnChooseResultListener = onChooseResultListener;
    }

    public interface OnChooseResultListener{
        public void onChoose(int hour,int minute);
    }


}
