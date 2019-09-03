package com.tourye.run.ui.activities.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.CommonBean;
import com.tourye.run.bean.RunnerInfoBean;
import com.tourye.run.bean.UploadFileBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.dialogs.AddressDialog;
import com.tourye.run.ui.dialogs.ModifyHeadDialog;
import com.tourye.run.utils.CameraHelper;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.PermissionDialogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 *  
 * @ClassName:   PersonInfoActivity
 *
 * @Author:   along
 * 
 * @Description: 编辑个人信息页面
 * 
 * @CreateDate:   2019/3/25 3:05 PM
 * 
 */
public class PersonInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImgPersonInfoHead;
    private RadioGroup mRgPersonInfoSex;
    private RadioButton mRbPersonInfoMan;
    private RadioButton mRbPersonInfoWoman;
    private EditText mEdtPersonInfoNickName;
    private EditText mEdtPersonInfoAge;
    private TextView mTvPersonInfoSubmit;

    private List<LocalMedia> selectList = new ArrayList<>();
    private String mImagePath;

    private Bitmap mHeadBitmap;

    @Override
    public void initView() {
        mImgPersonInfoHead = (ImageView) findViewById(R.id.img_person_info_head);
        mRgPersonInfoSex = (RadioGroup) findViewById(R.id.rg_person_info_sex);
        mRbPersonInfoMan = (RadioButton) findViewById(R.id.rb_person_info_man);
        mRbPersonInfoWoman = (RadioButton) findViewById(R.id.rb_person_info_woman);
        mEdtPersonInfoNickName = (EditText) findViewById(R.id.edt_person_info_nickName);
        mEdtPersonInfoAge = (EditText) findViewById(R.id.edt_person_info_age);
        mTvPersonInfoSubmit = (TextView) findViewById(R.id.tv_person_info_submit);

        mTvTitle.setText("填写信息");
        mImgPersonInfoHead.setOnClickListener(this);
        mTvPersonInfoSubmit.setOnClickListener(this);

    }

    @Override
    public void initData() {

        Map<String,String> map=new HashMap<>();
        HttpUtils.getInstance().get(Constants.UPDATE_USER_INFO, map, new HttpCallback<RunnerInfoBean>() {
            @Override
            public void onSuccessExecute(RunnerInfoBean runnerInfoBean) {
                RunnerInfoBean.DataBean data = runnerInfoBean.getData();
                if (data!=null) {
                    mEdtPersonInfoAge.setText(data.getAge());
                    mEdtPersonInfoNickName.setText(data.getNickname());
                    Glide.with(BaseApplication.mApplicationContext).asBitmap().load(data.getAvatar_url()).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            mImgPersonInfoHead.setImageBitmap(resource);
                            mHeadBitmap = resource;
                        }
                    });
                    if ("male".equalsIgnoreCase(data.getGender())) {
                        mRbPersonInfoMan.setChecked(true);
                    }else if ("female".equalsIgnoreCase(data.getGender())){
                        mRbPersonInfoWoman.setChecked(true);
                    }
                }
            }
        });

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
                CameraHelper.goCamera(mActivity,selectList,true);
            }

            @Override
            public void openGallery() {
                CameraHelper.goAlbum(mActivity,selectList,true);
            }

        });
        modifyHeadDialog.show();
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
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                    }
                    if (selectList!=null && selectList.get(0)!=null) {
                        mImagePath = "";
                        LocalMedia media = selectList.get(0);
                        if (media.isCut() && !media.isCompressed()) {
                            // 裁剪过
                            mImagePath = media.getCutPath();
                        } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                            // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                            mImagePath = media.getCompressPath();
                        } else {
                            // 原图
                            mImagePath = media.getPath();
                        }
                        RequestOptions requestOptions=new RequestOptions();
                        requestOptions.centerCrop()
                                .placeholder(R.drawable.shape_holder)
                                .diskCacheStrategy(DiskCacheStrategy.ALL);
                        Glide.with(BaseApplication.mApplicationContext)
                                .load(mImagePath)
                                .apply(requestOptions)
                                .into(mImgPersonInfoHead);
                        mHeadBitmap=null;
                    }
                    break;
            }
        }
    }

    /**
     * 上传头像bitmap
     * @param headBitmap
     */
    private void uploadBitmap(final Bitmap headBitmap) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            String absolutePath = getExternalCacheDir().getAbsolutePath();
                            File file = new File(absolutePath,System.currentTimeMillis()+"jpg");
                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                headBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                                uploadImage(file.getAbsolutePath());
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else {
                            PermissionDialogUtil.showPermissionDialog(mActivity, "缺少拍照或存储相关权限，请前往手机设置开启");
                        }
                    }
                });

    }

    /**
     * 上传头像
     * @param file_address
     */
    public void uploadImage(String file_address) {
        Map<String, String> map = new HashMap<>();
        map.put("type", "1");
        map.put("square", "1");
        File file = new File(file_address);
        HttpUtils.getInstance().upload(Constants.UPLOAD_IMAGE, map, "file", file, new HttpCallback<UploadFileBean>() {
            @Override
            public void onSuccessExecute(UploadFileBean uploadFileBean) {
                if (uploadFileBean.getStatus() == 0) {
                    UploadFileBean.DataBean data = uploadFileBean.getData();
                    submitPersonInfo(data.getKey());
                }
            }
        });
    }

    /**
     * 上传信息
     */
    public void submitPersonInfo(String imageKey){
        Map<String,String> map=new HashMap<>();
        map.put("nickname",mEdtPersonInfoNickName.getText().toString());
        map.put("avatar",imageKey);
        if (mRbPersonInfoMan.isChecked()) {
            map.put("gender","male");
        }else if(mRbPersonInfoWoman.isChecked()){
            map.put("gender","female");
        }else{
            map.put("gender","");
        }
        map.put("age",mEdtPersonInfoAge.getText().toString());
        HttpUtils.getInstance().post(Constants.UPDATE_USER_INFO, map, new HttpCallback<CommonBean>() {
            @Override
            public void onSuccessExecute(CommonBean commonBean) {
                if (commonBean.getStatus()==0) {
                    finish();
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.activity_person_info;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_person_info_submit:
                String name = mEdtPersonInfoNickName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(mActivity, "请输入昵称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mImagePath) && mHeadBitmap==null) {
                    Toast.makeText(mActivity, "请选择头像", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mImagePath)) {
                    uploadBitmap(mHeadBitmap);
                }else{
                    uploadImage(mImagePath);
                }
                break;
            case R.id.img_person_info_head:
                requestPermissionRx();
                break;
        }
    }


}
