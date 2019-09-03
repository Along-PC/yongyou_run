package com.tourye.run.ui.activities.punch;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.ScreenPunchBean;
import com.tourye.run.bean.UploadFileBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.common.CommonWebActivity;
import com.tourye.run.ui.dialogs.ModifyHeadDialog;
import com.tourye.run.ui.dialogs.PunchTimeDialog;
import com.tourye.run.utils.CameraHelper;
import com.tourye.run.utils.PermissionDialogUtil;
import com.tourye.run.utils.SaveUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 *
 * @ClassName:   ScreenPunchActivity
 *
 * @Author:   along
 *
 * @Description:    截图打卡页面
 *
 * @CreateDate:   2019/4/4 5:43 PM
 *
 */
public class ScreenPunchActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImgActivityScreenPunch;
    private EditText mEdtActivitySedtcreenPunchDistance;
    private TextView mTvActivityScreenPunchTime;
    private TextView mTvActivityScreenPunchCrossCountry;
    private TextView mTvActivityScreenPunchRule;
    private TextView mTvActivityScreenPunchSubmit;
    private ImageView mImgActivityScreenPunchReset;

    private List<LocalMedia> selectList = new ArrayList<>();
    private int mPunch_hour;//打卡小时数
    private int mPunch_minute;//打卡分钟数
    private ProgressDialog mAlertDialog;

    @Override
    public void initView() {
        mImgActivityScreenPunch = (ImageView) findViewById(R.id.img_activity_screen_punch);
        mEdtActivitySedtcreenPunchDistance = (EditText) findViewById(R.id.edt_activity_sedtcreen_punch_distance);
        mTvActivityScreenPunchTime = (TextView) findViewById(R.id.tv_activity_screen_punch_time);
        mTvActivityScreenPunchCrossCountry = (TextView) findViewById(R.id.tv_activity_screen_punch_crossCountry);
        mTvActivityScreenPunchRule = (TextView) findViewById(R.id.tv_activity_screen_punch_rule);
        mTvActivityScreenPunchSubmit = (TextView) findViewById(R.id.tv_activity_screen_punch_submit);
        mImgActivityScreenPunchReset = (ImageView) findViewById(R.id.img_activity_screen_punch_reset);

        mTvActivityScreenPunchTime.setOnClickListener(this);
        mImgActivityScreenPunch.setOnClickListener(this);
        mTvActivityScreenPunchCrossCountry.setOnClickListener(this);
        mTvActivityScreenPunchSubmit.setOnClickListener(this);
        mImgActivityScreenPunchReset.setOnClickListener(this);
        mTvActivityScreenPunchRule.setOnClickListener(this);

        mEdtActivitySedtcreenPunchDistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content = s.toString();
                if (TextUtils.isEmpty(content)) {
                    mTvActivityScreenPunchSubmit.setSelected(true);
                    mTvActivityScreenPunchSubmit.setOnClickListener(null);
                    return;
                }
                if (content.startsWith(".")) {
                    mTvActivityScreenPunchSubmit.setSelected(true);
                    mTvActivityScreenPunchSubmit.setOnClickListener(null);
                    return;
                }
                String temp=content;
                int counts = temp.length() - temp.replace(".", "").length();
                if (counts>1) {
                    mTvActivityScreenPunchSubmit.setSelected(true);
                    mTvActivityScreenPunchSubmit.setOnClickListener(null);
                    return;
                }
                mTvActivityScreenPunchSubmit.setSelected(false);
                mTvActivityScreenPunchSubmit.setOnClickListener(ScreenPunchActivity.this);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTvTitle.setText("截图打卡");
    }

    @Override
    public void initData() {

    }

    /**
     * 请求权限
     */
    public void requestPermissionRx() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            showCameraDialog();
                        } else {
                            PermissionDialogUtil.showPermissionDialog(mActivity, "缺少拍照或存储相关权限，请前往手机设置开启");
                        }
                    }
                });
    }

    /**
     * 拍照对话框
     */
    public void showCameraDialog() {
        ModifyHeadDialog modifyHeadDialog = new ModifyHeadDialog(this);
        modifyHeadDialog.setCameraCallback(new ModifyHeadDialog.CameraCallback() {
            @Override
            public void openCamera() {
                goCamera();
            }

            @Override
            public void openGallery() {
                goAlbum();
            }
        });
        modifyHeadDialog.show();
    }

    // 单独拍照
    public void goCamera(){
        CameraHelper.goCamera(mActivity,false);
    }

    //跳转相册
    public void goAlbum() {
        CameraHelper.goAlbumNoCache(mActivity,PictureConfig.CHOOSE_REQUEST);
    }

    /**
     * 上传图片
     * @param file_address
     */
    public void uploadImage(String file_address) {
        mAlertDialog = new ProgressDialog(mActivity);
        mAlertDialog.setMessage("正在上传，请稍后...");
        mAlertDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("type", "1");
        map.put("square", "0");
        File file = new File(file_address);
        HttpUtils.getInstance().upload(Constants.UPLOAD_IMAGE, map, "file", file, new HttpCallback<UploadFileBean>() {
            @Override
            public void onSuccessExecute(UploadFileBean uploadFileBean) {
                if (uploadFileBean.getStatus() == 0) {
                    UploadFileBean.DataBean data = uploadFileBean.getData();
                    if (data!=null) {
                        punch(data.getKey());
                    }
                }
            }
        });
    }

    /**
     * 提交打卡
     * @param image_key
     */
    private void punch(String image_key) {
        Map<String,String> map=new HashMap<>();
        map.put("image",image_key);
        map.put("time",mPunch_hour*60+mPunch_minute+"");
        map.put("distance",mEdtActivitySedtcreenPunchDistance.getText().toString());
        map.put("cross_country_run",mTvActivityScreenPunchCrossCountry.isSelected()?"1":"0");
        HttpUtils.getInstance().post(Constants.SCREEN_PUNCH, map, new HttpCallback<ScreenPunchBean>() {
            @Override
            public void onSuccessExecute(ScreenPunchBean screenPunchBean) {
                mAlertDialog.dismiss();
                if (screenPunchBean.getStatus()!=0) {
                    Toast.makeText(mActivity, screenPunchBean.getMessage(), Toast.LENGTH_SHORT).show();
                }
                ScreenPunchBean.DataBean data = screenPunchBean.getData();
                if (data!=null) {
                    Intent intent = new Intent(mActivity, ScreenPunchResultActivity.class);
                    intent.putExtra("punch_id",data.getId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList.clear();
                    selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList!=null && selectList.get(0)!=null) {
                        String path = "";
                        LocalMedia media = selectList.get(0);
                        if (media.isCut() && !media.isCompressed()) {
                            // 裁剪过
                            path = media.getCutPath();
                        } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                            // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                            path = media.getCompressPath();
                        } else {
                            // 原图
                            path = media.getPath();
                        }
                        RequestOptions requestOptions=new RequestOptions();
                        requestOptions.centerCrop()
                                .placeholder(R.drawable.shape_holder)
                                .diskCacheStrategy(DiskCacheStrategy.ALL);
                        Glide.with(BaseApplication.mApplicationContext)
                                .load(path)
                                .apply(requestOptions)
                                .into(mImgActivityScreenPunch);
                        mImgActivityScreenPunchReset.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    }

    @Override
    public int getRootView() {
        return R.layout.activity_screen_punch;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_activity_screen_punch_reset:
                mImgActivityScreenPunch.setImageResource(0);
                mImgActivityScreenPunchReset.setVisibility(View.GONE);
                selectList.clear();
                break;
            case R.id.tv_activity_screen_punch_time:
                PunchTimeDialog punchTimeDialog = new PunchTimeDialog(mActivity);
                punchTimeDialog.setOnChooseResultListener(new PunchTimeDialog.OnChooseResultListener() {
                    @Override
                    public void onChoose(int hour, int minute) {
                        mPunch_hour=hour;
                        mPunch_minute=minute;
                        mTvActivityScreenPunchTime.setText(mPunch_hour+"小时"+mPunch_minute+"分钟");
                    }
                });
                punchTimeDialog.show();
                break;
            case R.id.img_activity_screen_punch:
                requestPermissionRx();
                break;
            case R.id.tv_activity_screen_punch_crossCountry:
                if (!mTvActivityScreenPunchCrossCountry.isSelected()) {
                    mTvActivityScreenPunchCrossCountry.setSelected(true);
                    mTvActivityScreenPunchCrossCountry.setText("开");
                }else{
                    mTvActivityScreenPunchCrossCountry.setSelected(false);
                    mTvActivityScreenPunchCrossCountry.setText("关");
                }
                break;
            case R.id.tv_activity_screen_punch_submit:
                if (!(selectList.size()>0)) {
                    Toast.makeText(mActivity, "请上传打卡截图", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mEdtActivitySedtcreenPunchDistance.getText().toString())) {
                    Toast.makeText(mActivity, "请输入目标距离", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(mPunch_hour*60+mPunch_minute>0)) {
                    Toast.makeText(mActivity, "请选择打卡时长", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean is_joined = SaveUtil.getBoolean(SaveConstants.IS_JOINED, false);
                if (is_joined) {
                    String level_group = SaveUtil.getString(SaveConstants.LEVEL_GROUP, "");
                    int distance_goal = Integer.parseInt(level_group);
                    String currentDistanceStr = mEdtActivitySedtcreenPunchDistance.getText().toString();
                    float currentDistance = Float.parseFloat(currentDistanceStr);
                    if(currentDistance<distance_goal){
                        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                        builder.setTitle("上传打卡")
                                .setMessage("距离未满足百日跑要求，是否重新打卡？")
                                .setPositiveButton("继续提交", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        uploadImage(selectList.get(0).getPath());
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("重新打卡", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }else{
                        uploadImage(selectList.get(0).getPath());
                    }
                }else{
                    uploadImage(selectList.get(0).getPath());
                }
                break;
            case R.id.tv_activity_screen_punch_rule:
                Intent intent = new Intent(mActivity, CommonWebActivity.class);
                intent.putExtra("url","https://mp.weixin.qq.com/s/-GNLfykLIPM2GqX7iLfgrQ");
                intent.putExtra("title","详细规则");
                startActivity(intent);
                break;
        }
    }
}
