package com.tourye.run.ui.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.tourye.run.R;
import com.tourye.run.utils.GlideUtils;

/**
 *
 * @ClassName:   CloudHelperDialog
 *
 * @Author:   along
 *
 * @Description:    云助手认证弹窗
 *
 * @CreateDate:   2019/7/17 6:39 PM
 *
 */
public class CloudHelperDialog extends BaseDialog {
    private ImageView mImgDialogCloudHelper;
    private ImageView mImgDialogCloudHelperClose;

    public CloudHelperDialog(Context context) {
        super(context);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mImgDialogCloudHelper = (ImageView) findViewById(R.id.img_dialog_cloud_helper);
        mImgDialogCloudHelperClose = (ImageView) findViewById(R.id.img_dialog_cloud_helper_close);

        GlideUtils.getInstance().loadImage("https://static.run100.runorout.cn/meta/withdraw/yun_zhang_hu_mp.png",mImgDialogCloudHelper);
        mImgDialogCloudHelperClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    protected boolean isNeedMatchParent() {
        return false;
    }

    @Override
    protected boolean isNeedAnimation() {
        return false;
    }

    @Override
    public boolean isLocationBottom() {
        return false;
    }

    @Override
    protected int getRootView() {
        return R.layout.dialog_cloud_helper;
    }
}
