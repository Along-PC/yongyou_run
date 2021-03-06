package com.tourye.run.ui.activities.mine;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
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
import com.tourye.run.bean.CommonBean;
import com.tourye.run.bean.UploadFileBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.MainActivity;
import com.tourye.run.ui.dialogs.ModifyHeadDialog;
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
 * EditInformationActivity
 * author:along
 * 2018/12/19 1:36 PM
 *
 * 描述:设置默认头像、昵称页面
 */

public class EditInformationActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImgActivityEditInforHead;
    private TextView mTvActivityEditInforHead;
    private EditText mEdtActivityEditInforName;
    private TextView mTvActivityEditInforName;
    private TextView mTvActivityEditInformationSkip;

    private List<LocalMedia> selectList = new ArrayList<>();
    private String mImagePath;

    @Override
    public void initView() {

        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mImgActivityEditInforHead = (ImageView) findViewById(R.id.img_activity_edit_infor_head);
        mTvActivityEditInforHead = (TextView) findViewById(R.id.tv_activity_edit_infor_head);
        mEdtActivityEditInforName = (EditText) findViewById(R.id.edt_activity_edit_infor_name);
        mTvActivityEditInforName = (TextView) findViewById(R.id.tv_activity_edit_infor_name);
        mTvActivityEditInformationSkip = (TextView) findViewById(R.id.tv_activity_edit_information_skip);

        mTvTitle.setText("信息填写");
        mTvCertain.setVisibility(View.VISIBLE);
        mTvCertain.setTextColor(Color.parseColor("#FFCC1C24"));
        mTvCertain.setText("保存");

        mImgReturn.setOnClickListener(this);
        mImgReturn.setVisibility(View.GONE);
        mTvCertain.setOnClickListener(this);
        mTvActivityEditInformationSkip.setOnClickListener(this);
        mImgActivityEditInforHead.setOnClickListener(this);

    }

    @Override
    public void initData() {

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
                                .into(mImgActivityEditInforHead);
                    }
                    break;
            }
        }
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
        map.put("nickname",mEdtActivityEditInforName.getText().toString());
        map.put("avatar",imageKey);
        HttpUtils.getInstance().post(Constants.UPDATE_USER_INFO, map, new HttpCallback<CommonBean>() {
            @Override
            public void onSuccessExecute(CommonBean commonBean) {
                if (commonBean.getStatus()==0) {
                    startActivity(new Intent(mActivity,MainActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.activity_edit_information;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                finish();
                break;
            case R.id.tv_certain:
                if (TextUtils.isEmpty(mImagePath)) {
                    Toast.makeText(mActivity, "请选择头像", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = mEdtActivityEditInforName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(mActivity, "请输入昵称", Toast.LENGTH_SHORT).show();
                    return;
                }
                uploadImage(mImagePath);
                break;
            case R.id.img_activity_edit_infor_head:
                requestPermissionRx();
                break;
            case R.id.tv_activity_edit_information_skip:
                startActivity(new Intent(mActivity,MainActivity.class));
                finish();
                break;
        }
    }
}
