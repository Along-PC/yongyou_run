package com.tourye.run.ui.activities.community;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.CreateDynamicBean;
import com.tourye.run.bean.ParseLocationBean;
import com.tourye.run.bean.PunchDetailBean;
import com.tourye.run.bean.SubmitDynamicBean;
import com.tourye.run.bean.UploadFileBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.MainActivity;
import com.tourye.run.ui.adapter.GridImageAdapter;
import com.tourye.run.ui.fragments.AllDynamicFragment;
import com.tourye.run.utils.AntiShakeClickUtils;
import com.tourye.run.utils.CameraHelper;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.FullyGridLayoutManager;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.GridDividerItemDecoration;
import com.tourye.run.utils.LocationUtils;
import com.tourye.run.utils.PermissionDialogUtil;
import com.tourye.run.utils.SaveUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.functions.Consumer;
import okhttp3.Response;

/**
 * @ClassName: CreateDynamicActivity
 * @Author: along
 * @Description: 发布动态页面
 * @CreateDate: 2019/4/12 2:37 PM
 */
public class CreateDynamicActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEdtActivityCreateDynamic;
    private TextView mTvActivityCreateDynamicCount;
    private RecyclerView mRecyclerActivityCreateDynamicPhotos;
    private TextView mTvActivityCreateDynamicLocation;
    private Switch mSwitchActivityCreateDynamicLocation;
    private TextView mTvActivityCreateDynamicSubmit;
    private RelativeLayout mRlActivityCreateDynamicRunRecord;
    private ImageView mImgActivityCreateDynamicRunCard;
    private TextView mTvActivityCreateDynamicRunIntro;
    private TextView mTvActivityCreateDynamicRunTime;

    private String location_city;//定位城市

    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter adapter;
    private int mPunch_id;//截图打卡id
    private List<String> mImageKeys = new ArrayList<>();//图片上传key集合
    private int mImageUploadIndex = 0;//图片上传索引
    private double mLongitude;//定位精度
    private double mLatitude;//定位纬度
    private String mType;//1--从打卡完成后跳转而来
    private ProgressDialog mProgressDialog;

    @Override
    public void initView() {
        mEdtActivityCreateDynamic = (EditText) findViewById(R.id.edt_activity_create_dynamic);
        mTvActivityCreateDynamicCount = (TextView) findViewById(R.id.tv_activity_create_dynamic_count);
        mRecyclerActivityCreateDynamicPhotos = (RecyclerView) findViewById(R.id.recycler_activity_create_dynamic_photos);
        mTvActivityCreateDynamicLocation = (TextView) findViewById(R.id.tv_activity_create_dynamic_location);
        mSwitchActivityCreateDynamicLocation = (Switch) findViewById(R.id.switch_activity_create_dynamic_location);
        mTvActivityCreateDynamicSubmit = (TextView) findViewById(R.id.tv_activity_create_dynamic_submit);
        mRlActivityCreateDynamicRunRecord = (RelativeLayout) findViewById(R.id.rl_activity_create_dynamic_run_record);
        mImgActivityCreateDynamicRunCard = (ImageView) findViewById(R.id.img_activity_create_dynamic_run_card);
        mTvActivityCreateDynamicRunIntro = (TextView) findViewById(R.id.tv_activity_create_dynamic_run_intro);
        mTvActivityCreateDynamicRunTime = (TextView) findViewById(R.id.tv_activity_create_dynamic_run_time);

        mTvTitle.setText("发表动态");
        //焦点转移
        mTvTitle.setFocusable(true);
        mTvTitle.setFocusableInTouchMode(true);
        mTvTitle.requestFocus();
        mEdtActivityCreateDynamic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = s.toString();
                mTvActivityCreateDynamicCount.setText(temp.length() + "/140");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTvActivityCreateDynamicSubmit.setOnClickListener(this);
        mSwitchActivityCreateDynamicLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    RxPermissions rxPermissions = new RxPermissions(mActivity);
                    rxPermissions
                            .request(Manifest.permission.ACCESS_FINE_LOCATION)
                            .subscribe(new Consumer<Boolean>() {
                                @Override
                                public void accept(Boolean aBoolean) {
                                    if (aBoolean) {
                                        LocationUtils.getInstance().startLocation(new AMapLocationListener() {
                                            @Override
                                            public void onLocationChanged(AMapLocation aMapLocation) {
                                                if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                                                    //定位成功
                                                    location_city = aMapLocation.getCity();
                                                    mTvActivityCreateDynamicLocation.setText(location_city);
                                                    mLongitude = aMapLocation.getLongitude();
                                                    mLatitude = aMapLocation.getLatitude();
                                                }else{
                                                    Toast.makeText(mActivity, "定位失败", Toast.LENGTH_SHORT).show();
                                                    mSwitchActivityCreateDynamicLocation.setChecked(false);
                                                }
                                            }
                                        });
                                    } else {
                                        mSwitchActivityCreateDynamicLocation.setChecked(false);
                                        Toast.makeText(mActivity, "请前往设置开启定位权限", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    mLatitude = 0;
                    mLongitude = 0;
                    location_city = "";
                    mTvActivityCreateDynamicLocation.setText("定位");
                }
            }
        });

    }

    /**
     * 根据经纬度解析地理位置
     *
     * @param lot
     * @param lat
     */
    public void parseLocation(String lot, String lat, final String content, final List<String> ids) {
        Map<String, String> map = new HashMap<>();
        map.put("longitude", lot);
        map.put("latitude", lat);
        HttpUtils.getInstance().get(Constants.PARSE_LOCATION, map, new HttpCallback<ParseLocationBean>() {
            @Override
            public void onSuccessExecute(ParseLocationBean parseLocationBean) {
                ParseLocationBean.DataBean data = parseLocationBean.getData();
                if (data == null) {
                    return;
                }
                int cityId = data.getId();
                submitDynamicByCityid(content, ids, cityId);

            }
        });
    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        mType = intent.getStringExtra("type");
        //从截图打卡来
        if (!TextUtils.isEmpty(mType) && mType.equalsIgnoreCase("1")) {

            mPunch_id = intent.getIntExtra("punch_id", 0);
            getPunchData(mPunch_id + "");
        } else {
            mRlActivityCreateDynamicRunRecord.setVisibility(View.GONE);
        }

        initRecycler();
    }

    /**
     * 获取打卡数据
     *
     * @param punch_id
     */
    private void getPunchData(String punch_id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", punch_id);
        HttpUtils.getInstance().get(Constants.PUNCH_DETAIL, map, new HttpCallback<PunchDetailBean>() {
            @Override
            public void onSuccessExecute(PunchDetailBean punchDetailBean) {
                PunchDetailBean.DataBean data = punchDetailBean.getData();
                if (data != null) {
                    GlideUtils.getInstance().loadRoundImage(data.getImage_url(), mImgActivityCreateDynamicRunCard);
                    if (SaveUtil.getBoolean(SaveConstants.IS_JOINED,false)) {
                        mTvActivityCreateDynamicRunIntro.setText("百日跑坚持打卡已" + data.getTotal_days() + "天");
                        mTvActivityCreateDynamicRunIntro.setVisibility(View.VISIBLE);
                    }else{
                        mTvActivityCreateDynamicRunIntro.setVisibility(View.GONE);
                    }
                    mTvActivityCreateDynamicRunTime.setText("完成" + data.getDistance() + "公里，用时" + data.getTime() + "分钟");
                }
            }
        });
    }

    public void initRecycler() {
//        FullyGridLayoutManager manager = new FullyGridLayoutManager(mActivity, 3, GridLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 3);
        mRecyclerActivityCreateDynamicPhotos.setLayoutManager(gridLayoutManager);
        adapter = new GridImageAdapter(mActivity, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(9);
        adapter.setDividerData(mActivity.getResources().getDisplayMetrics().widthPixels - DensityUtils.dp2px(28), DensityUtils.dp2px(5));
        GridDividerItemDecoration gridDividerItemDecoration = new GridDividerItemDecoration(mActivity);
        gridDividerItemDecoration.setDividerSize(DensityUtils.dp2px(5), DensityUtils.dp2px(5));
        mRecyclerActivityCreateDynamicPhotos.addItemDecoration(gridDividerItemDecoration);
        mRecyclerActivityCreateDynamicPhotos.setAdapter(adapter);
        //条目删除监听
        adapter.setOnItemDeleteListener(new GridImageAdapter.OnItemDeleteListener() {
            @Override
            public void OnItemDelete(List<LocalMedia> list) {
                selectList = list;
                String content = mEdtActivityCreateDynamic.getText().toString();
                if (selectList.size() <= 0 && TextUtils.isEmpty(content)) {
                    mTvCertain.setTextColor(Color.parseColor("#FF999999"));
                    mTvCertain.setOnClickListener(null);
                }
            }
        });
        //条目点击监听
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(mActivity).themeStyle(R.style.picture_default_style).openExternalPreview(position, selectList);
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
                                CameraHelper.goAlbum(mActivity, 9, selectList);
                            } else {
                                PermissionDialogUtil.showPermissionDialog(mActivity, "缺少手机权限，请前往手机设置开启");
                            }
                        }
                    });

        }

    };

    //上传动态
    public void submitDynamic(String content, List<String> ids) {
        if (mSwitchActivityCreateDynamicLocation.isChecked()) {
            parseLocation(mLongitude + "", mLatitude + "", content, ids);
            return;
        }
        SubmitDynamicBean submitDynamicBean = new SubmitDynamicBean();
        submitDynamicBean.setContent(content);
        submitDynamicBean.setImages(ids);
        if (mPunch_id != 0) {
            submitDynamicBean.setSign_in_id(mPunch_id + "");
        }
        Gson gson = new Gson();
        String json = gson.toJson(submitDynamicBean);
        HttpUtils.getInstance().post_json(Constants.CREATE_DYNAMIC, json, new HttpCallback<CreateDynamicBean>() {
            @Override
            public void onSuccessExecute(CreateDynamicBean createDynamicBean) {
                if (createDynamicBean.getStatus() == 0) {
//                    //清除压缩和裁剪的缓存
//                    RxPermissions rxPermissions = new RxPermissions(mActivity);
//                    rxPermissions
//                            .request(Manifest.permission.READ_EXTERNAL_STORAGE,
//                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                            .subscribe(new Consumer<Boolean>() {
//                                @Override
//                                public void accept(Boolean aBoolean) throws Exception {
//                                    if (aBoolean) {
//                                        PictureFileUtils.deleteCacheDirFile(mActivity);
//                                    }
//                                }
//                            });
                    mProgressDialog.dismiss();
                    Toast.makeText(CreateDynamicActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                    //刷新动态的列表
                    AllDynamicFragment.UpdateDynamicBean updateDynamicBean = new AllDynamicFragment.UpdateDynamicBean();
                    updateDynamicBean.setUpdate_list(true);
                    EventBus.getDefault().post(updateDynamicBean);
                    if (!TextUtils.isEmpty(mType) && mType.equalsIgnoreCase("1")) {
                        Intent intent = new Intent(mActivity, MainActivity.class);
                        intent.putExtra("type", 1);
                        startActivity(intent);
                    } else {
                        finish();
                    }
                }
            }
        });

    }

    /**
     * 上传动态
     *
     * @param content 文本内容
     * @param ids     图片id
     * @param cityId  城市id
     */
    public void submitDynamicByCityid(String content, List<String> ids, int cityId) {
        SubmitDynamicBean submitDynamicBean = new SubmitDynamicBean();
        submitDynamicBean.setContent(content);
        submitDynamicBean.setImages(ids);
        if (mPunch_id != 0) {
            submitDynamicBean.setSign_in_id(mPunch_id + "");
        }
        submitDynamicBean.setPosition(cityId + "");
        Gson gson = new Gson();
        String json = gson.toJson(submitDynamicBean);
        HttpUtils.getInstance().post_json(Constants.CREATE_DYNAMIC, json, new HttpCallback<CreateDynamicBean>() {
            @Override
            public void onSuccessExecute(CreateDynamicBean createDynamicBean) {
                if (createDynamicBean.getStatus() == 0) {
                    Toast.makeText(CreateDynamicActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                    //刷新动态的列表
                    AllDynamicFragment.UpdateDynamicBean updateDynamicBean = new AllDynamicFragment.UpdateDynamicBean();
                    updateDynamicBean.setUpdate_list(true);
                    EventBus.getDefault().post(updateDynamicBean);

                    if (!TextUtils.isEmpty(mType) && mType.equalsIgnoreCase("1")) {
                        Intent intent = new Intent(mActivity, MainActivity.class);
                        intent.putExtra("type", 1);
                        startActivity(intent);
                    } else {
                        finish();
                    }
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivity=null;
    }

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
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                    }
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    /**
     * 上传图片
     */
    public void uploadImage() {
        LocalMedia localMedia = selectList.get(mImageUploadIndex);
        String path = localMedia.getCompressPath();
        Map<String, String> map = new HashMap<>();
        map.put("type", "1");
        File file = new File(path);
        HttpUtils.getInstance().upload(Constants.UPLOAD_IMAGE, map, "file", file, new HttpCallback<UploadFileBean>() {
            @Override
            public void onSuccessExecute(UploadFileBean uploadFileBean) {
                UploadFileBean.DataBean data = uploadFileBean.getData();
                if (data != null) {
                    mImageUploadIndex++;
                    mImageKeys.add(data.getKey());
                    if(mImageUploadIndex < selectList.size()){
                        uploadImage();
                        return;
                    }
                    if (mImageUploadIndex == selectList.size()) {
                        String content = mEdtActivityCreateDynamic.getText().toString();
                        submitDynamic(content, mImageKeys);
                    }
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.activity_create_dynamic;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_activity_create_dynamic_submit:
                if (mActivity==null) {
                    return;
                }
                if (AntiShakeClickUtils.isFastClick()) {
                    return;
                }
                String content = mEdtActivityCreateDynamic.getText().toString();
                if (content.length() <= 0 && selectList.size() <= 0) {
                    Toast.makeText(mActivity, "文本或照片请至少选择一样", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mSwitchActivityCreateDynamicLocation.isChecked()) {
                    if (mLongitude <= 0 || mLongitude <= 0) {
                        Toast.makeText(mActivity, "请等待定位结果", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                mProgressDialog = new ProgressDialog(mActivity);
                mProgressDialog.setMessage("正在上传，请稍后...");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();
                if (selectList.size() > 0) {
                    mImageUploadIndex = 0;
                    uploadImage();
                    return;
                }
                submitDynamic(content, mImageKeys);
                break;
        }
    }
}
