package com.tourye.run.ui.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.tourye.run.R;


/**
 * CommentDialogFragment
 * author:along
 * 2018/10/9 上午10:57
 *
 * 描述:社区评论软键盘顶起输入框
 */


public class CommentDialogInnerFragment extends DialogFragment implements View.OnClickListener{

    private EditText mEdtDialogComment;
    private TextView mTvDialogComment;

    private InputMethodManager inputMethodManager;
    private CommentCallback mCommentCallback;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog mDialog = new Dialog(getActivity(), R.style.BottomDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_fragment_comment_layout);
        mDialog.setCanceledOnTouchOutside(true);

        Window window = mDialog.getWindow();
        WindowManager.LayoutParams layoutParams;
        if (window != null) {
            layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(layoutParams);
        }

        mEdtDialogComment = (EditText) mDialog.findViewById(R.id.edt_dialog_comment);
        mTvDialogComment = (TextView) mDialog.findViewById(R.id.tv_dialog_comment);

        setSoftKeyboard();

        mEdtDialogComment.addTextChangedListener(mTextWatcher);
        mTvDialogComment.setOnClickListener(this);

        return mDialog;
    }

    public void setCommentCallback(CommentCallback commentCallback) {
        mCommentCallback = commentCallback;
    }

    private void setSoftKeyboard() {
        mEdtDialogComment.setFocusable(true);
        mEdtDialogComment.setFocusableInTouchMode(true);
        mEdtDialogComment.requestFocus();

        //为 commentEditText 设置监听器，在 DialogFragment 绘制完后立即呼出软键盘，呼出成功后即注销
        mEdtDialogComment.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    if (inputMethodManager.showSoftInput(mEdtDialogComment, 1)) {
                        mEdtDialogComment.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }

    private TextWatcher mTextWatcher = new TextWatcher() {

        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (temp.length() > 0) {
                mTvDialogComment.setEnabled(true);
                mTvDialogComment.setClickable(true);
//                mTvDialogComment.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorAccent));
            } else {
                mTvDialogComment.setEnabled(false);
//                mTvDialogComment.setColorFilter(ContextCompat.getColor(getActivity(), R.color.iconCover));
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dialog_comment:
                mCommentCallback.comment(mEdtDialogComment.getText().toString());
                mEdtDialogComment.setText("");
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    public interface CommentCallback {
        public void comment(String text);
    }
}