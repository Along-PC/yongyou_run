package com.tourye.run.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import com.tourye.run.R;

/**
 *
 * @ClassName:   BaseDialog
 *
 * @Author:   along
 *
 * @Description:    弹窗基类
 *
 * @CreateDate:   2019/4/23 1:39 PM
 *
 */
public abstract class BaseDialog extends Dialog {

    protected Context mContext;

    public BaseDialog(Context context) {
        super(context);
        mContext = context;
        //设置背景透明
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        setContentView(getRootView());

        //设置宽度沾满屏幕----需要在setContentView之后
        if (isNeedMatchParent()) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().getDecorView().setPadding(0, 0, 0, 0);
            getWindow().setAttributes(layoutParams);
        }
        //设置弹窗在屏幕底部
        if (isLocationBottom()) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            getWindow().setAttributes(layoutParams);
        }

        initView();

        initData();
        //设置动画
        if (isNeedAnimation()) {
            getWindow().setWindowAnimations(R.style.HeadDialogStyle);
        }

    }

    protected boolean isNeedAnimation() {
        return true;
    }

    protected boolean isNeedMatchParent(){
        return true;
    }

    public boolean isLocationBottom(){
        return true;
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getRootView();

}
