package com.tourye.run.ui.activities.common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.luck.picture.lib.photoview.PhotoView;
import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.utils.GlideUtils;

/**
 *
 * @ClassName:   SingleImageActivity
 *
 * @Author:   along
 *
 * @Description:    单张图片查看页面
 *
 * @CreateDate:   2019/6/5 9:59 AM
 *
 */

public class SingleImageActivity extends BaseActivity {

    private PhotoView mPhotoActivitySingleImage;

    @Override
    public void initView() {
        mPhotoActivitySingleImage = (PhotoView) findViewById(R.id.photo_activity_single_image);
    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        if (!TextUtils.isEmpty(url)) {
            GlideUtils.getInstance().loadImage(url,mPhotoActivitySingleImage);
            mPhotoActivitySingleImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @Override
    public boolean isNeedTitle() {
        return false;
    }

    @Override
    public int getRootView() {
        return R.layout.activity_single_image;
    }
}
