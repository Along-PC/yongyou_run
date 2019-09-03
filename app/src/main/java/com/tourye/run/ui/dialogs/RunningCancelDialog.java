package com.tourye.run.ui.dialogs;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.utils.DensityUtils;

/**
 *
 * @ClassName:   RunningCancelDialog
 *
 * @Author:   along
 *
 * @Description:    跑步长按提示弹窗
 *
 * @CreateDate:   2019/7/15 8:11 PM
 *
 */
public class RunningCancelDialog extends PopupWindow {
    private Context mContext;

    public RunningCancelDialog(Context context) {
        super(context);
        mContext = context;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_running_cancel, null);
        setContentView(view);

        setWidth(DensityUtils.dp2px(88));
        setHeight(DensityUtils.dp2px(40));

        setBackgroundDrawable(new BitmapDrawable());

    }

}
