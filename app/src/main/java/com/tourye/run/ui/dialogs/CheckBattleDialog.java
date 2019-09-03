package com.tourye.run.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.bean.MonitorTeamListbean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.mine.MultiTeamManageActivity;
import com.tourye.run.ui.activities.mine.TeamManageActivity;
import com.tourye.run.utils.SaveUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:   CheckBattleDialog
 *
 * @Author:   along
 *
 * @Description:    查看已经创建的战队对话框
 *
 * @CreateDate:   2019/4/17 2:50 PM
 *
 */
public class CheckBattleDialog extends Dialog {
    private final Context mContext;
    private TextView mTvDialogCheckBattleTitle;
    private LinearLayout mLlDialogCheckBattleDynamic;
    private TextView mTvDialogCheckBattleCurrent;
    private TextView mTvDialogCheckBattleGoOn;
    private TextView mTvDialogCheckBattleContent;


    public CheckBattleDialog( Context context) {
        super(context);
        mContext = context;
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_check_battle);

        mTvDialogCheckBattleTitle = (TextView) findViewById(R.id.tv_dialog_check_battle_title);
        mLlDialogCheckBattleDynamic = (LinearLayout) findViewById(R.id.ll_dialog_check_battle_dynamic);
        mTvDialogCheckBattleCurrent = (TextView) findViewById(R.id.tv_dialog_check_battle_current);
        mTvDialogCheckBattleGoOn = (TextView) findViewById(R.id.tv_dialog_check_battle_goOn);
        mTvDialogCheckBattleContent = (TextView) findViewById(R.id.tv_dialog_check_battle_content);

        mTvDialogCheckBattleGoOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public void setData(final List<MonitorTeamListbean.DataBean> data){
        mTvDialogCheckBattleTitle.setText("当前您已创建"+data.size()+"支战队");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < data.size(); i++) {
            stringBuffer.append(data.get(i).getName()+"\n");
        }
        String temp = stringBuffer.toString();
        mTvDialogCheckBattleContent.setText(temp);
        if (data.size()>1) {
            mTvDialogCheckBattleCurrent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext,MultiTeamManageActivity.class));
                    dismiss();
                }
            });
        }else{
            mTvDialogCheckBattleCurrent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MonitorTeamListbean.DataBean dataBean = data.get(0);
                    Intent intent = new Intent(mContext, TeamManageActivity.class);
                    intent.putExtra("team_id",dataBean.getId()+"");
                    mContext.startActivity(intent);
                    dismiss();
                }
            });
        }


    }

}
