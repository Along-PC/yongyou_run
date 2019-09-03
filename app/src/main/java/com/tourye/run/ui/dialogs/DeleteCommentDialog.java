package com.tourye.run.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.tourye.run.R;


/**
 * Created by longlongren on 2018/10/9.
 * <p>
 * introduce:删除评论弹框
 */

public class DeleteCommentDialog extends Dialog implements View.OnClickListener {
    private TextView mTvDialogDeleteComment;
    private TextView mTvDialogDeleteCommentCancel;
    private DeleteCommentCallback mDeleteCommentCallback;


    public DeleteCommentDialog(@NonNull Context context) {
        super(context);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_delete_comment);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);

        getWindow().setWindowAnimations(R.style.HeadDialogStyle);

        mTvDialogDeleteComment = (TextView) findViewById(R.id.tv_dialog_delete_comment);
        mTvDialogDeleteCommentCancel = (TextView) findViewById(R.id.tv_dialog_delete_comment_cancel);

        mTvDialogDeleteComment.setOnClickListener(this);
        mTvDialogDeleteCommentCancel.setOnClickListener(this);
    }

    public void setDeleteCommentCallback(DeleteCommentCallback deleteCommentCallback) {
        mDeleteCommentCallback = deleteCommentCallback;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dialog_delete_comment:
                mDeleteCommentCallback.deleteComment();
                dismiss();
                break;
            case R.id.tv_dialog_delete_comment_cancel:
                dismiss();
                break;
        }
    }

    public interface DeleteCommentCallback{
        public void deleteComment();
    }

}
