package com.tourye.run.ui.activities.punch;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
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
import com.tourye.run.ui.activities.community.CreateDynamicActivity;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 *
 * @ClassName:   ScreenPunchResultActivity
 *
 * @Author:   along
 *
 * @Description:    截图打卡结果页面
 *
 * @CreateDate:   2019/4/8 10:00 AM
 *
 */
public class ScreenPunchResultActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImgActivityScreenPunchResultReturn;
    private TextView mTvActivityScreenPunchResultCheck;
    private TextView mTvActivityScreenPunchResultCheckNum;
    private TextView mTvActivityScreenPunchResultDynamic;
    private RelativeLayout mRlActivityScreenPunchResultCheck;
    private TextView mTvActivityScreenPunchResultReturn;

    private int mPunch_id;//打卡id

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
        mImgActivityScreenPunchResultReturn = (ImageView) findViewById(R.id.img_activity_screen_punch_result_return);
        mTvActivityScreenPunchResultCheck = (TextView) findViewById(R.id.tv_activity_screen_punch_result_check);
        mTvActivityScreenPunchResultCheckNum = (TextView) findViewById(R.id.tv_activity_screen_punch_result_checkNum);
        mTvActivityScreenPunchResultDynamic = (TextView) findViewById(R.id.tv_activity_screen_punch_result_dynamic);
        mRlActivityScreenPunchResultCheck = (RelativeLayout) findViewById(R.id.rl_activity_screen_punch_result_check);
        mTvActivityScreenPunchResultReturn = (TextView) findViewById(R.id.tv_activity_screen_punch_result_return);

        mTvActivityScreenPunchResultCheck.setOnClickListener(this);
        mTvActivityScreenPunchResultDynamic.setOnClickListener(this);

        mTvTitle.setText("打卡结果");
    }

    @Override
    public void initData() {

        getCard();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCheckNum();
    }

    /**
     * 下载图片
     */
    private void downloadImage(String url) {
        Glide.with(BaseApplication.mApplicationContext)
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
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
                        mRlActivityScreenPunchResultCheck.setVisibility(View.GONE);
                    }else{
                        mTvActivityScreenPunchResultCheckNum.setText(data);
                    }
                }
            });
        }else{
            mRlActivityScreenPunchResultCheck.setVisibility(View.GONE);
        }
    }

    /**
     * 获取成就卡
     */
    private void getCard() {
        Intent intent = getIntent();
        mPunch_id = intent.getIntExtra("punch_id",0);
        Map<String,String> map=new HashMap<>();
        map.put("id", mPunch_id+"");
        HttpUtils.getInstance().get(Constants.SCREEN_PUNCH_CARD, map, new HttpCallback<ScreenPunchCardBean>() {
            @Override
            public void onSuccessExecute(ScreenPunchCardBean screenPunchCardBean) {
                final String data = screenPunchCardBean.getData();
                if (TextUtils.isEmpty(data)) {
                    mImgActivityScreenPunchResultReturn.setVisibility(View.GONE);
                    mTvActivityScreenPunchResultReturn.setVisibility(View.GONE);
                }else{
                    GlideUtils.getInstance().loadRoundImage(data,mImgActivityScreenPunchResultReturn);
                    mImgActivityScreenPunchResultReturn.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            RxPermissions rxPermissions = new RxPermissions(mActivity);
                            rxPermissions
                                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    .subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean aBoolean) {
                                            if (aBoolean) {
                                                downloadImage(data);
                                            } else {
                                                Toast.makeText(mActivity, "缺少存储授权", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            return true;
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.activity_screen_punch_result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_activity_screen_punch_result_check:
                startActivity(new Intent(mActivity,CheckPunchActivity.class));
                break;
            case R.id.tv_activity_screen_punch_result_dynamic:
                Intent dynamicIntent = new Intent(mActivity, CreateDynamicActivity.class);
                dynamicIntent.putExtra("type","1");
                dynamicIntent.putExtra("punch_id",mPunch_id);
                startActivity(dynamicIntent);
                break;
        }
    }
}
