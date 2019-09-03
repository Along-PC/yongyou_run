package com.tourye.run.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tourye.run.R;


/**
 * Created by longlongren on 2018/10/10.
 * <p>
 * introduce:删除动态弹窗
 */

public class DeleteDynamicDialog extends Dialog{
    private TextView mTvDialogDeleteDynamicTitle;
    private LinearLayout mLlDialogDeleteDynamic;
    private TextView mTvDialogDeleteDynamicCancel;
    private TextView mTvDialogDeleteDynamicCertain;

    private OnDeleteInterface mOnDeleteInterface;

    public DeleteDynamicDialog(@NonNull Context context) {
        super(context);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_delete_dynamic);

        mTvDialogDeleteDynamicTitle = (TextView) findViewById(R.id.tv_dialog_delete_dynamic_title);
        mLlDialogDeleteDynamic = (LinearLayout) findViewById(R.id.ll_dialog_delete_dynamic);
        mTvDialogDeleteDynamicCancel = (TextView) findViewById(R.id.tv_dialog_delete_dynamic_cancel);
        mTvDialogDeleteDynamicCertain = (TextView) findViewById(R.id.tv_dialog_delete_dynamic_certain);

        mTvDialogDeleteDynamicCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mTvDialogDeleteDynamicCertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                mOnDeleteInterface.delete();
            }
        });

    }

    public void setOnDeleteInterface(OnDeleteInterface onDeleteInterface) {
        mOnDeleteInterface = onDeleteInterface;
    }

    public interface OnDeleteInterface{
        public void delete();
    }

}
