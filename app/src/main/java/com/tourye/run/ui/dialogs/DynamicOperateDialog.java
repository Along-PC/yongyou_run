package com.tourye.run.ui.dialogs;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.utils.DensityUtils;

/**
 *
 * @ClassName:   DynamicOperateDialog
 *
 * @Author:   along
 *
 * @Description:    动态操作弹窗
 *
 * @CreateDate:   2019/6/5 2:10 PM
 *
 */
public class DynamicOperateDialog extends PopupWindow {

    private Context mContext;
    private TextView mTvDialogDynamicOperate;
    private OnClickListener mOnClickListener;

    public DynamicOperateDialog(Context context) {
        super(context);
        mContext = context;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_dynamic_operate, null);
        setContentView(view);

        mTvDialogDynamicOperate = (TextView) view.findViewById(R.id.tv_dialog_dynamic_operate);

        setWidth(DensityUtils.dp2px(100));
        setHeight(DensityUtils.dp2px(40));

        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
    }

    public void setOnClickListener(OnClickListener onClickListener,String text) {
        mOnClickListener = onClickListener;
        mTvDialogDynamicOperate.setText(text);
        mTvDialogDynamicOperate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mOnClickListener.onClick();
            }
        });
    }

    public interface OnClickListener{
        public void onClick();
    }
}
