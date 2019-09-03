package com.tourye.run.ui.dialogs;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tourye.run.R;
import com.tourye.run.base.BaseApplication;

/**
 *
 * @ClassName:   LoadingDialog
 *
 * @Author:   along
 *
 * @Description:    加载弹窗
 *
 * @CreateDate:   2019/7/17 10:49 AM
 *
 */
public class LoadingDialog extends BaseDialog{
    private ImageView mImgDialogLoadinng;

    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mImgDialogLoadinng = (ImageView) findViewById(R.id.img_dialog_loadinng);
        Glide.with(BaseApplication.mApplicationContext).load(R.drawable.icon_loading).into(mImgDialogLoadinng);
        setCancelable(false);

        //去除蒙层
        getWindow().setDimAmount(0f);

    }

    private boolean isNeedCancel() {
        return false;
    }

    protected boolean isNeedAnimation() {
        return false;
    }

    protected boolean isNeedMatchParent(){
        return false;
    }

    public boolean isLocationBottom(){
        return false;
    }

    @Override
    protected int getRootView() {
        return R.layout.dialog_loading;
    }
}
