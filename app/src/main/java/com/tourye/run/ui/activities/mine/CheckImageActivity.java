package com.tourye.run.ui.activities.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.luck.picture.lib.photoview.PhotoView;
import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.utils.ImageUtils;

/**
 *
 * @ClassName:   CheckImageActivity
 *
 * @Author:   along
 *
 * @Description:    审核打卡的图片的详情
 *
 * @CreateDate:   2019/4/30 11:24 AM
 *
 */
public class CheckImageActivity extends BaseActivity {
    private PhotoView mPhotoActivityCheckImage;


    @Override
    public void initView() {
        mPhotoActivityCheckImage = (PhotoView) findViewById(R.id.photo_activity_check_image);

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        final String name = intent.getStringExtra("name");
        Glide.with(this).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Bitmap along = ImageUtils.addTextWatermark(resource, name,  false);
                mPhotoActivityCheckImage.setImageBitmap(along);
            }

        });
        mPhotoActivityCheckImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean isNeedTitle() {
        return false;
    }

    @Override
    public int getRootView() {
        return R.layout.activity_check_image;
    }
}
