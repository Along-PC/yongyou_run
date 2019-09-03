package com.tourye.run.ui.activities.mine;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.ui.adapter.GridImageAdapter;
import com.tourye.run.utils.CameraHelper;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.GridDividerItemDecoration;
import com.tourye.run.utils.PermissionDialogUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 *
 * @ClassName:   RunningGroupApplyActivity
 *
 * @Author:   along
 *
 * @Description:    跑团申请页面
 *
 * @CreateDate:   2019/5/6 2:18 PM
 *
 */
public class RunningGroupApplyActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEdtActivityRunningGroupApplyName;
    private RelativeLayout mRlActivityRunningGroupProvince;
    private EditText mEdtActivityRunningGroupApplyTime;
    private EditText mEdtActivityRunningGroupApplyPlace;
    private EditText mEdtActivityRunningGroupApplyLeadername;
    private EditText mEdtActivityRunningGroupApplyPhone;
    private RelativeLayout mEdtActivityRunningGroupApplyShippingAddress;
    private EditText mEdtActivityRunningGroupApplyAddress;
    private EditText mEdtActivityRunningGroupApplyIntro;
    private TextView mTvActivityRunningGroupApplyIntroCount;
    private RecyclerView mRecyclerActivityRunningGroupApplyAlbum;
    private TextView mTvActivityRunningGroupApplySubmit;

    private List<LocalMedia> mPhotoList = new ArrayList<>();
    private GridImageAdapter mGridImageAdapter;

    @Override
    public void initView() {
        mEdtActivityRunningGroupApplyName = (EditText) findViewById(R.id.edt_activity_running_group_apply_name);
        mRlActivityRunningGroupProvince = (RelativeLayout) findViewById(R.id.rl_activity_running_group_province);
        mEdtActivityRunningGroupApplyTime = (EditText) findViewById(R.id.edt_activity_running_group_apply_time);
        mEdtActivityRunningGroupApplyPlace = (EditText) findViewById(R.id.edt_activity_running_group_apply_place);
        mEdtActivityRunningGroupApplyLeadername = (EditText) findViewById(R.id.edt_activity_running_group_apply_leadername);
        mEdtActivityRunningGroupApplyPhone = (EditText) findViewById(R.id.edt_activity_running_group_apply_phone);
        mEdtActivityRunningGroupApplyShippingAddress = (RelativeLayout) findViewById(R.id.edt_activity_running_group_apply_shipping_address);
        mEdtActivityRunningGroupApplyAddress = (EditText) findViewById(R.id.edt_activity_running_group_apply_address);
        mEdtActivityRunningGroupApplyIntro = (EditText) findViewById(R.id.edt_activity_running_group_apply_intro);
        mTvActivityRunningGroupApplyIntroCount = (TextView) findViewById(R.id.tv_activity_running_group_apply_introCount);
        mRecyclerActivityRunningGroupApplyAlbum = (RecyclerView) findViewById(R.id.recycler_activity_running_group_apply_album);
        mTvActivityRunningGroupApplySubmit = (TextView) findViewById(R.id.tv_activity_running_group_apply_submit);

        mTvTitle.setText("跑团申请");

        mTvActivityRunningGroupApplySubmit.setOnClickListener(this);
        mEdtActivityRunningGroupApplyIntro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content = s.toString();
                mTvActivityRunningGroupApplyIntroCount.setText(content.length()+"/140");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
    }

    @Override
    public void initData() {
        initRecycler();
    }

    public void initRecycler() {
//        FullyGridLayoutManager manager = new FullyGridLayoutManager(mActivity, 3, GridLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 3);
        mRecyclerActivityRunningGroupApplyAlbum.setLayoutManager(gridLayoutManager);
        mGridImageAdapter = new GridImageAdapter(mActivity, onAddPicClickListener);
        mGridImageAdapter.setList(mPhotoList);
        mGridImageAdapter.setSelectMax(9);
        mGridImageAdapter.setDividerData(mActivity.getResources().getDisplayMetrics().widthPixels-DensityUtils.dp2px(28),DensityUtils.dp2px(5));
        GridDividerItemDecoration gridDividerItemDecoration = new GridDividerItemDecoration(mActivity);
        gridDividerItemDecoration.setDividerSize(DensityUtils.dp2px(5),DensityUtils.dp2px(5));
        mRecyclerActivityRunningGroupApplyAlbum.addItemDecoration(gridDividerItemDecoration);
        mRecyclerActivityRunningGroupApplyAlbum.setAdapter(mGridImageAdapter);
        //条目删除监听
        mGridImageAdapter.setOnItemDeleteListener(new GridImageAdapter.OnItemDeleteListener() {
            @Override
            public void OnItemDelete(List<LocalMedia> list) {
                mPhotoList = list;
            }
        });
        //条目点击监听
        mGridImageAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (mPhotoList.size() > 0) {
                    LocalMedia media = mPhotoList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(mActivity).themeStyle(R.style.picture_default_style).openExternalPreview(position, mPhotoList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(mActivity).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(mActivity).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {

            RxPermissions rxPermissions = new RxPermissions(mActivity);
            rxPermissions
                    .request(Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                CameraHelper.goAlbum(mActivity,9,mPhotoList);
                            } else {
                                PermissionDialogUtil.showPermissionDialog(mActivity, "缺少存储权限，请前往手机设置开启");
                            }
                        }
                    });

        }

    };

    /**
     * 获取图片选择结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    mPhotoList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : mPhotoList) {
                        Log.i("图片-----》", media.getPath());
                    }
                    mGridImageAdapter.setList(mPhotoList);
                    mGridImageAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public int getRootView() {
        return R.layout.activity_running_group_apply;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_activity_running_group_apply_submit:

                break;
        }
    }
}
