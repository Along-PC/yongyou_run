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
 * Created by longlongren on 2018/8/22.
 * <p>
 * introduce:修改头像弹框
 */

public class ModifyHeadDialog extends Dialog implements View.OnClickListener {
    private TextView mTvDialogModifyHeadCamera;
    private TextView mTvDialogModifyHeadPhoto;
    private TextView mTvDialogModifyHeadCancel;
    private CameraCallback mCameraCallback;


    public ModifyHeadDialog(@NonNull Context context) {
        super(context);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        setContentView(R.layout.dialog_modify_head);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);

        getWindow().setWindowAnimations(R.style.HeadDialogStyle);

        mTvDialogModifyHeadCamera = (TextView) findViewById(R.id.tv_dialog_modify_head_camera);
        mTvDialogModifyHeadPhoto = (TextView) findViewById(R.id.tv_dialog_modify_head_photo);
        mTvDialogModifyHeadCancel = (TextView) findViewById(R.id.tv_dialog_modify_head_cancel);

        mTvDialogModifyHeadCancel.setOnClickListener(this);
        mTvDialogModifyHeadCamera.setOnClickListener(this);
        mTvDialogModifyHeadPhoto.setOnClickListener(this);

    }

    public void setCameraCallback(CameraCallback cameraCallback) {
        mCameraCallback = cameraCallback;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dialog_modify_head_camera:
                if (mCameraCallback!=null) {
                    mCameraCallback.openCamera();
                    dismiss();
                }
                break;
            case R.id.tv_dialog_modify_head_photo:
                if (mCameraCallback!=null) {
                    mCameraCallback.openGallery();
                    dismiss();
                }
                break;
            case R.id.tv_dialog_modify_head_cancel:
                dismiss();
                break;
        }
    }

    public interface CameraCallback{

        public void openCamera();

        public void openGallery();
    }
}
