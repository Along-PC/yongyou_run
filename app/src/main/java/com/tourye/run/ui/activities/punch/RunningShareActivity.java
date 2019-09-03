package com.tourye.run.ui.activities.punch;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.UploadFileBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.LocationUtils;
import com.tourye.run.utils.ShareUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @ClassName: RunningShareActivity
 * @Author: along
 * @Description: 跑步结束分享页面
 * @CreateDate: 2019/5/9 2:13 PM
 */
public class RunningShareActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImgActivityRunningShareCapture;
    private RelativeLayout mRlActivityRunningShareDownload;
    private RelativeLayout mRlActivityRunningShareWx;
    private RelativeLayout mRlActivityRunningShareMoment;
    private RelativeLayout mRlActivityRunningShareWb;
    private RelativeLayout mRlActivityRunningShareQq;
    private RelativeLayout mRlActivityRunningShareKj;
    private String mScreenShotUrl;//屏幕截图地址

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(mActivity, "图片下载成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(mActivity, "图片下载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void initView() {
        mImgActivityRunningShareCapture = (ImageView) findViewById(R.id.img_activity_running_share_capture);
        mRlActivityRunningShareDownload = (RelativeLayout) findViewById(R.id.rl_activity_running_share_download);
        mRlActivityRunningShareWx = (RelativeLayout) findViewById(R.id.rl_activity_running_share_wx);
        mRlActivityRunningShareMoment = (RelativeLayout) findViewById(R.id.rl_activity_running_share_moment);
        mRlActivityRunningShareWb = (RelativeLayout) findViewById(R.id.rl_activity_running_share_wb);
        mRlActivityRunningShareQq = (RelativeLayout) findViewById(R.id.rl_activity_running_share_qq);
        mRlActivityRunningShareKj = (RelativeLayout) findViewById(R.id.rl_activity_running_share_kj);

        mRlActivityRunningShareDownload.setOnClickListener(this);
        mRlActivityRunningShareWx.setOnClickListener(this);
        mRlActivityRunningShareMoment.setOnClickListener(this);

        mTvTitle.setText("轨迹分享");
    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        mScreenShotUrl = intent.getStringExtra("url");
        GlideUtils.getInstance().loadImage(mScreenShotUrl, mImgActivityRunningShareCapture);

    }

    /**
     * 下载图片
     */
    private void downloadImage() {
        Glide.with(BaseApplication.mApplicationContext)
                .asBitmap()
                .load(mScreenShotUrl)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        FileOutputStream fileOutputStream = null;
                        try {
                            //系统相册路径
                            String cameraPath = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM + File.separator + "Camera";
                            File file = new File(cameraPath, System.currentTimeMillis() + ".jpg");
                            fileOutputStream = new FileOutputStream(file);
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                            //保存图片后发送广播通知更新数据库
                            Uri uri = Uri.fromFile(file);
                            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                            mHandler.sendEmptyMessage(1);
                        } catch (Exception e) {
                            e.printStackTrace();
                            mHandler.sendEmptyMessage(2);
                        } finally {
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
    }

    public void share(String plat_name) {
        if (TextUtils.isEmpty(mScreenShotUrl)) {
            return;
        }
        ShareUtils.getInstance().shareImage(plat_name, "", mScreenShotUrl, "", mScreenShotUrl, mActivity);
    }

    @Override
    public int getRootView() {
        return R.layout.activity_running_share;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_activity_running_share_download:
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) {
                                if (aBoolean) {
                                    downloadImage();
                                } else {
                                    Toast.makeText(mActivity, "缺少存储授权", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                break;
            case R.id.rl_activity_running_share_wx:
                share(Wechat.NAME);
                break;
            case R.id.rl_activity_running_share_moment:
                share(WechatMoments.NAME);
                break;
        }
    }

}
