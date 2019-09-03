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
 * introduce:下载图片弹窗
 */

public class DownloadImageDialog extends Dialog implements View.OnClickListener {
    private TextView mTvDialogDownloadImage;
    private TextView mTvDialogDownloadImageCancel;


    private DownloadCallback mDownloadCallback;


    public DownloadImageDialog(@NonNull Context context) {
        super(context);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        setContentView(R.layout.dialog_download_image);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);

        getWindow().setWindowAnimations(R.style.HeadDialogStyle);

        mTvDialogDownloadImage = (TextView) findViewById(R.id.tv_dialog_download_image);
        mTvDialogDownloadImageCancel = (TextView) findViewById(R.id.tv_dialog_download_image_cancel);

        mTvDialogDownloadImage.setOnClickListener(this);
        mTvDialogDownloadImageCancel.setOnClickListener(this);

    }

    public void setDownloadCallback(DownloadCallback downloadCallback) {
        mDownloadCallback = downloadCallback;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dialog_download_image:
                if (mDownloadCallback!=null) {
                    mDownloadCallback.download();
                    dismiss();
                }
                break;
            case R.id.tv_dialog_download_image_cancel:
                dismiss();
                break;
        }
    }

    public interface DownloadCallback{

        public void download();
    }
}
