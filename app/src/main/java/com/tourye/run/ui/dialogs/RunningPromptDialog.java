package com.tourye.run.ui.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.tourye.run.R;

/**
 *
 * @ClassName:   RunningPromptDialog
 *
 * @Author:   along
 *
 * @Description:    跑步定位操作提示弹窗
 *
 * @CreateDate:   2019/7/15 10:43 AM
 *
 */
public class RunningPromptDialog extends BaseDialog{
    private Button mBtDialogRunnningPrompt;


    public RunningPromptDialog(Context context) {
        super(context);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mBtDialogRunnningPrompt = (Button) findViewById(R.id.bt_dialog_runnning_prompt);
        mBtDialogRunnningPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    protected boolean isNeedAnimation() {
        return false;
    }

    @Override
    public boolean isLocationBottom() {
        return false;
    }

    @Override
    protected boolean isNeedMatchParent() {
        return false;
    }

    @Override
    protected int getRootView() {
        return R.layout.dialog_running_prompt;
    }
}
