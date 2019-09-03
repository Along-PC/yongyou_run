package com.tourye.run.ui.activities.punch;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.ScreenPunchCardBean;
import com.tourye.run.bean.WaitCheckCountBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;
import com.tourye.run.utils.ShareUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import io.reactivex.functions.Consumer;

/**
 *
 * @ClassName:   AchievementCardActivity
 *
 * @Author:   along
 *
 * @Description:    成就卡页面
 *
 * @CreateDate:   2019/5/14 9:27 AM
 *
 */
public class AchievementCardActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImgActivityAchievementCard;
    private RelativeLayout mRlActivityAchievementCardDownload;
    private RelativeLayout mRlActivityAchievementCardCheck;
    private RelativeLayout mRlActivityAchievementCardWx;
    private RelativeLayout mRlActivityAchievementCardMoment;
    private RelativeLayout mRlActivityAchievementCardWb;
    private RelativeLayout mRlActivityAchievementCardQq;
    private RelativeLayout mRlActivityAchievementCardKj;
    private ImageView mImgActivityAchievementCardCheck;
    private TextView mTvActivityAchievementCardCheck;
    private String mAchievementCardUrl;

    @Override
    public void initView() {
        mImgActivityAchievementCard = (ImageView) findViewById(R.id.img_activity_achievement_card);
        mRlActivityAchievementCardDownload = (RelativeLayout) findViewById(R.id.rl_activity_achievement_card_download);
        mRlActivityAchievementCardCheck = (RelativeLayout) findViewById(R.id.rl_activity_achievement_card_check);
        mRlActivityAchievementCardWx = (RelativeLayout) findViewById(R.id.rl_activity_achievement_card_wx);
        mRlActivityAchievementCardMoment = (RelativeLayout) findViewById(R.id.rl_activity_achievement_card_moment);
        mRlActivityAchievementCardWb = (RelativeLayout) findViewById(R.id.rl_activity_achievement_card_wb);
        mRlActivityAchievementCardQq = (RelativeLayout) findViewById(R.id.rl_activity_achievement_card_qq);
        mRlActivityAchievementCardKj = (RelativeLayout) findViewById(R.id.rl_activity_achievement_card_kj);
        mImgActivityAchievementCardCheck = (ImageView) findViewById(R.id.img_activity_achievement_card_check);
        mTvActivityAchievementCardCheck = (TextView) findViewById(R.id.tv_activity_achievement_card_check);

        mTvTitle.setText("成就卡");
        mRlActivityAchievementCardDownload.setOnClickListener(this);
        mRlActivityAchievementCardWx.setOnClickListener(this);
        mRlActivityAchievementCardMoment.setOnClickListener(this);
        mRlActivityAchievementCardCheck.setOnClickListener(this);
    }

    @Override
    public void initData() {

        getCheckNum();

        getCard();

    }

    /**
     * 获取可审核打卡数
     */
    private void getCheckNum() {
        //可审核打卡数
        if (SaveUtil.getBoolean(SaveConstants.IS_JOINED,false)) {
            Map<String,String> map=new HashMap<>();
            map.put("id",SaveUtil.getString(SaveConstants.ACTION_ID,""));
            HttpUtils.getInstance().get(Constants.CHECK_SIGN_IN_COUNT, map, new HttpCallback<WaitCheckCountBean>() {
                @Override
                public void onSuccessExecute(WaitCheckCountBean waitCheckCountBean) {
                    String data = waitCheckCountBean.getData();
                    int number = Integer.parseInt(data);
                    if (number<=0) {
                        mRlActivityAchievementCardCheck.setVisibility(View.GONE);
                    }else{
                        mTvActivityAchievementCardCheck.setText(data);
                    }
                }
            });
        }else{
            mRlActivityAchievementCardCheck.setVisibility(View.GONE);
        }
    }

    /**
     * 获取成就卡
     */
    private void getCard() {
        Intent intent = getIntent();
        int punch_id = intent.getIntExtra("punch_id",0);
        Map<String,String> map=new HashMap<>();
        map.put("id", punch_id+"");
        HttpUtils.getInstance().get(Constants.SCREEN_PUNCH_CARD, map, new HttpCallback<ScreenPunchCardBean>() {
            @Override
            public void onSuccessExecute(ScreenPunchCardBean screenPunchCardBean) {
                mAchievementCardUrl = screenPunchCardBean.getData();
                if (!TextUtils.isEmpty(mAchievementCardUrl)) {
                    GlideUtils.getInstance().loadRoundImage(mAchievementCardUrl,mImgActivityAchievementCard);
                }
            }
        });
    }

    /**
     * 下载图片
     */
    private void downloadImage() {
        if (TextUtils.isEmpty(mAchievementCardUrl)) {
            return;
        }
        Glide.with(BaseApplication.mApplicationContext)
                .asBitmap()
                .load(mAchievementCardUrl)
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
                            Toast.makeText(mActivity, "下载成功", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(mActivity, "下载失败", Toast.LENGTH_SHORT).show();
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

    /**
     * 分享
     * @param plat_name
     */
    public void share(String plat_name) {
        if (TextUtils.isEmpty(mAchievementCardUrl)) {
            return;
        }
        ShareUtils.getInstance().shareImage(plat_name, "", mAchievementCardUrl, "", mAchievementCardUrl, mActivity);
    }

    @Override
    public int getRootView() {
        return R.layout.activity_achievement_card;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_activity_achievement_card_download:
                if (TextUtils.isEmpty(mAchievementCardUrl)) {

                    return;
                }
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
            case R.id.rl_activity_achievement_card_wx:
                share(Wechat.NAME);
                break;
            case R.id.rl_activity_achievement_card_moment:
                share(WechatMoments.NAME);
                break;
            case R.id.rl_activity_achievement_card_check:
                startActivity(new Intent(mActivity,CheckPunchActivity.class));
                break;
        }
    }
}
