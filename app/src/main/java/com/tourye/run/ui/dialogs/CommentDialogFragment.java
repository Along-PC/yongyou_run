package com.tourye.run.ui.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.tourye.run.BuildConfig;
import com.tourye.run.R;


/**
 * CommentDialogFragment
 * author:along
 * 2018/10/9 上午10:57
 *
 * 描述:社区评论软键盘顶起输入框
 */


public class CommentDialogFragment extends DialogFragment implements View.OnClickListener{

    private EditText mEdtDialogComment;
    private TextView mTvDialogComment;


    private InputMethodManager inputMethodManager;
    private DialogFragmentDataCallback dataCallback;

    @Override
    public void onAttach(Context context) {
        if (!(getActivity() instanceof DialogFragmentDataCallback)) {
            throw new IllegalStateException("DialogFragment 所在的 activity 必须实现 DialogFragmentDataCallback 接口");
        }
        super.onAttach(context);
    }

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

        fillEditText();
        setSoftKeyboard();

        mEdtDialogComment.addTextChangedListener(mTextWatcher);
        mTvDialogComment.setOnClickListener(this);

        return mDialog;
    }

    private void fillEditText() {
        dataCallback = (DialogFragmentDataCallback) getActivity();
        String commentText = dataCallback.getCommentText();
        if (BuildConfig.DEBUG) Log.d("CommentDialogFragment", "再次进入"+commentText);
        mEdtDialogComment.setText(dataCallback.getCommentText());
        mEdtDialogComment.setSelection(dataCallback.getCommentText().length());
        if (dataCallback.getCommentText().length() == 0) {
            mTvDialogComment.setEnabled(false);
//            mTvDialogComment.setColorFilter(ContextCompat.getColor(getActivity(), R.color.iconCover));
        }
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
                        if (Build.VERSION.SDK_INT < 16) {
                            mEdtDialogComment.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            mEdtDialogComment.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
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
                String s = mEdtDialogComment.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    return;
                }
                dataCallback.setCommentText(s);
                mEdtDialogComment.setText("");
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface DialogFragmentDataCallback {

        String getCommentText();

        void setCommentText(String commentTextTemp);

    }

}
