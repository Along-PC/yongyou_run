package com.tourye.run.ui.activities.home;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.BattleGroupBean;
import com.tourye.run.bean.CreateBattleBean;
import com.tourye.run.bean.CreateBattleCacheBean;
import com.tourye.run.bean.CreateBattleSubmitBean;
import com.tourye.run.bean.MonitorTeamListbean;
import com.tourye.run.bean.TeamSubmitBean;
import com.tourye.run.bean.UploadFileBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.mine.TeamManageActivity;
import com.tourye.run.ui.adapter.CreateBattleAlbumAdapter;
import com.tourye.run.ui.adapter.CreateBattleDistanceAdapter;
import com.tourye.run.ui.dialogs.AddressDialog;
import com.tourye.run.ui.dialogs.CheckBattleDialog;
import com.tourye.run.ui.dialogs.ModifyHeadDialog;
import com.tourye.run.utils.AntiShakeClickUtils;
import com.tourye.run.utils.CacheUtils;
import com.tourye.run.utils.CameraHelper;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.FullyGridLayoutManager;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.GridDividerItemDecoration;
import com.tourye.run.utils.PermissionDialogUtil;
import com.tourye.run.utils.SaveUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ExecutionException;

import io.reactivex.Flowable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName: CreateBattleActivity
 * @Author: along
 * @Description: 创建战队页面
 * @CreateDate: 2019/4/9 10:03 AM
 */
public class CreateBattleActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImgActivityCreateBattleHead;
    private EditText mEdtActivityCreateBattleName;
    private TextView mTvActivityCreateBattleRegion;
    private RecyclerView mRecyclerActivityCreateBattleGroup;
    private EditText mEdtActivityCreateBattleContent;
    private TextView mTvActivityCreateBattleContentNum;
    private ImageView mImgActivityCreateBattleBattleQrcode;
    private ImageView mImgActivityCreateBattleLeaderQrcode;
    private RecyclerView mRecyclerActivityCreateBattleAlbum;
    private TextView mTvActivityCreateBattleSubmit;
    private RelativeLayout mRlActivityCreateBattleBattleQrcode;
    private RelativeLayout mRlActivityCreateBattleLeaderQrcode;

    private List<LocalMedia> selectLogoList = new ArrayList<>();
    private List<LocalMedia> selectBattleList = new ArrayList<>();
    private List<LocalMedia> selectLeaderList = new ArrayList<>();
    private List<LocalMedia> selectAlbumList = new ArrayList<>();

    private CreateBattleAlbumAdapter mCreateBattleAlbumAdapter;

    private String head_key;//头像上传key
    private String head_path;//头像本地路径
    private String battle_key;//战队二维码上传key
    private String battle_path;//战队二维码本地路径
    private String leader_key;//队长二维码上传key
    private String leader_path;//队长二维码本地路径
    private List<String> camera_keys = new ArrayList<>();//相册上传key集合
    private List<String> mPhotos = new ArrayList<>();//相册本地路径
    private Map<Integer, String> camera_map = new HashMap<>();
    private int photos_index = 0;
    private List<BattleGroupBean.DataBean> mBattleGroupBeanData;//组别列表数据
    private int mCity_id=-998;
    private String mCity_name;
    private int level_id = -1;
    private CreateBattleDistanceAdapter mCreateBattleDistanceAdapter;//战队组别适配器
    private int mType;//1--从战队管理过来更新战队信息   其他---创建战队
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10086:
                    if (!TextUtils.isEmpty(head_key) &&
                            !TextUtils.isEmpty(battle_key) &&
                            !TextUtils.isEmpty(leader_key) &&
                            camera_map.size() == mPhotos.size()) {
                        submit();
                    }
                    break;
            }
        }
    };
    private String mTeam_id;
    private ProgressDialog mProgressDialog;


    @Override
    public void initView() {
        mImgActivityCreateBattleHead = (ImageView) findViewById(R.id.img_activity_create_battle_head);
        mEdtActivityCreateBattleName = (EditText) findViewById(R.id.edt_activity_create_battle_name);
        mTvActivityCreateBattleRegion = (TextView) findViewById(R.id.tv_activity_create_battle_region);
        mRecyclerActivityCreateBattleGroup = (RecyclerView) findViewById(R.id.recycler_activity_create_battle_group);
        mEdtActivityCreateBattleContent = (EditText) findViewById(R.id.edt_activity_create_battle_content);
        mTvActivityCreateBattleContentNum = (TextView) findViewById(R.id.tv_activity_create_battle_contentNum);
        mImgActivityCreateBattleBattleQrcode = (ImageView) findViewById(R.id.img_activity_create_battle_battle_qrcode);
        mImgActivityCreateBattleLeaderQrcode = (ImageView) findViewById(R.id.img_activity_create_battle_leader_qrcode);
        mRecyclerActivityCreateBattleAlbum = (RecyclerView) findViewById(R.id.recycler_activity_create_battle_album);
        mTvActivityCreateBattleSubmit = (TextView) findViewById(R.id.tv_activity_create_battle_submit);
        mRlActivityCreateBattleBattleQrcode = (RelativeLayout) findViewById(R.id.rl_activity_create_battle_battle_qrcode);
        mRlActivityCreateBattleLeaderQrcode = (RelativeLayout) findViewById(R.id.rl_activity_create_battle_leader_qrcode);

        mTvTitle.setText("编辑战队信息");
        mImgReturn.setOnClickListener(this);
        mImgActivityCreateBattleHead.setOnClickListener(this);
        mImgActivityCreateBattleBattleQrcode.setOnClickListener(this);
        mImgActivityCreateBattleLeaderQrcode.setOnClickListener(this);
        mTvActivityCreateBattleRegion.setOnClickListener(this);
        mTvActivityCreateBattleSubmit.setOnClickListener(this);
        mEdtActivityCreateBattleContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = s.toString();
                mTvActivityCreateBattleContentNum.setText(string.length() + "/140");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        resetQrcodeSize();

    }

    @Override
    public void initData() {

        initDiatanceGroup();
        initAlbum();

    }

    private void resetQrcodeSize() {
        int screenWidth = DensityUtils.getScreenWidth();
        int unit_width=(screenWidth-DensityUtils.dp2px(14)*2-DensityUtils.dp2px(20))/2;
        ViewGroup.LayoutParams layoutParamsBattle = mRlActivityCreateBattleBattleQrcode.getLayoutParams();
        layoutParamsBattle.height=unit_width;
        layoutParamsBattle.width=unit_width;
        mRlActivityCreateBattleBattleQrcode.setLayoutParams(layoutParamsBattle);
        ViewGroup.LayoutParams layoutParamsLeader = mRlActivityCreateBattleLeaderQrcode.getLayoutParams();
        layoutParamsLeader.height=unit_width;
        layoutParamsLeader.width=unit_width;
        mRlActivityCreateBattleLeaderQrcode.setLayoutParams(layoutParamsLeader);
    }

    /**
     * 获取已经提交了的战队信息
     *
     * @param team_id
     */
    public void getTeamInfo(String team_id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", team_id);
        HttpUtils.getInstance().get(Constants.UPDATE_TEAM_INFO, map, new HttpCallback<TeamSubmitBean>() {
            @Override
            public void onSuccessExecute(TeamSubmitBean teamSubmitBean) {
                TeamSubmitBean.DataBean data = teamSubmitBean.getData();
                if (data != null) {
                    GlideUtils.getInstance().loadImage(data.getLogo_url(), mImgActivityCreateBattleHead);
                    mEdtActivityCreateBattleName.setText(data.getName());
                    mEdtActivityCreateBattleContent.setText(data.getDescription());
                    GlideUtils.getInstance().loadImage(data.getMonitor_qr_code_url(), mImgActivityCreateBattleLeaderQrcode);
                    GlideUtils.getInstance().loadImage(data.getGroup_qr_code_url(), mImgActivityCreateBattleBattleQrcode);
                    List<TeamSubmitBean.DataBean.PhotoBean> photoBeans = data.getPhotos();
                    if (photoBeans != null && photoBeans.size() > 0) {
                        List<String> photoKeys = new ArrayList<>();
                        for (int i = 0; i < photoBeans.size(); i++) {
                            mPhotos.add(photoBeans.get(i).getUrl());
                            photoKeys.add(photoBeans.get(i).getKey());
                        }
                        mCreateBattleAlbumAdapter.setList(mPhotos);
                        mCreateBattleAlbumAdapter.notifyDataSetChanged();
                    }
                    String city_name = data.getCity_name();
                    mCity_id = data.getCity_id();
                    if (TextUtils.isEmpty(city_name)) {
                        city_name="全国";
                        mCity_id=0;
                    }
                    mTvActivityCreateBattleRegion.setText(city_name);
                    level_id = data.getLevel_id();
                    head_path=data.getLogo_url();
                    leader_path=data.getMonitor_qr_code_url();
                    battle_path=data.getGroup_qr_code_url();
                    //选中组别
                    for (int i = 0; i < mBattleGroupBeanData.size(); i++) {
                        if (mBattleGroupBeanData.get(i).getId() == level_id) {
                            mBattleGroupBeanData.get(i).setSelected(true);
                        }
                    }
                    mCreateBattleDistanceAdapter.setDataBeans(mBattleGroupBeanData);
                }
            }
        });
    }

    /**
     * 获取已经创建的战队列表
     */
    public void getTeamList() {
        Map<String, String> map = new HashMap<>();
        map.put("activity_id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        HttpUtils.getInstance().get(Constants.MONITOR_TEAM_LIST, map, new HttpCallback<MonitorTeamListbean>() {
            @Override
            public void onSuccessExecute(MonitorTeamListbean monitorTeamListbean) {
                List<MonitorTeamListbean.DataBean> data = monitorTeamListbean.getData();
                if (data != null && data.size() > 0) {
                    CheckBattleDialog checkBattleDialog = new CheckBattleDialog(mActivity);
                    checkBattleDialog.setData(data);
                    checkBattleDialog.show();
                }
            }
        });
    }

    /**
     * 战队相册添加监听
     */
    private CreateBattleAlbumAdapter.onAddPicClickListener onAddPicClickListener = new CreateBattleAlbumAdapter.onAddPicClickListener() {
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
                                CameraHelper.goAlbum(mActivity, 9 - mPhotos.size(), 998, false);
                            } else {
                                PermissionDialogUtil.showPermissionDialog(mActivity, "缺少存储权限，请前往手机设置开启");
                            }
                        }
                    });
        }

    };

    public void initAlbum() {
        GridLayoutManager manager = new GridLayoutManager(mActivity, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerActivityCreateBattleAlbum.setLayoutManager(manager);
        mCreateBattleAlbumAdapter = new CreateBattleAlbumAdapter(mActivity, onAddPicClickListener);
        mCreateBattleAlbumAdapter.setList(mPhotos);
        mCreateBattleAlbumAdapter.setSelectMax(9);
        mCreateBattleAlbumAdapter.setDividerData(mActivity.getResources().getDisplayMetrics().widthPixels-DensityUtils.dp2px(28),DensityUtils.dp2px(5));
        GridDividerItemDecoration gridDividerItemDecoration = new GridDividerItemDecoration(mActivity);
        gridDividerItemDecoration.setDividerSize(DensityUtils.dp2px(5),DensityUtils.dp2px(5));
        mRecyclerActivityCreateBattleAlbum.addItemDecoration(gridDividerItemDecoration);
        mRecyclerActivityCreateBattleAlbum.setAdapter(mCreateBattleAlbumAdapter);
        //条目删除监听
        mCreateBattleAlbumAdapter.setOnItemDeleteListener(new CreateBattleAlbumAdapter.OnItemDeleteListener() {
            @Override
            public void OnItemDelete(List<String> list, int pos) {
                mPhotos = list;
            }
        });
    }

    /**
     * 初始化战队组别列表
     */
    public void initDiatanceGroup() {
        Map<String, String> map = new HashMap<>();
        map.put("id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        map.put("city_id", "");
        HttpUtils.getInstance().get(Constants.BATTLE_GROUP_LIST, map, new HttpCallback<BattleGroupBean>() {
            @Override
            public void onSuccessExecute(BattleGroupBean battleGroupBean) {
                mBattleGroupBeanData = battleGroupBean.getData();
                if (mBattleGroupBeanData == null) {
                    return;
                }
                mCreateBattleDistanceAdapter = new CreateBattleDistanceAdapter(mBattleGroupBeanData, mActivity);
                mRecyclerActivityCreateBattleGroup.setLayoutManager(new GridLayoutManager(mActivity, 5));
                mRecyclerActivityCreateBattleGroup.setAdapter(mCreateBattleDistanceAdapter);
                Intent intent = getIntent();
                mType = intent.getIntExtra("type", 0);
                if (mType == 1) {
                    //从战队管理过来进行更新数据
                    mTeam_id = intent.getStringExtra("team_id");
                    getTeamInfo(mTeam_id);
                    mCreateBattleDistanceAdapter.setCanChange(false);
                } else {
                    restoreData();
                    getTeamList();
                    mCreateBattleDistanceAdapter.setCanChange(true);
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
                CameraHelper.goCamera(mActivity, selectLogoList);
            }

            @Override
            public void openGallery() {
                CameraHelper.goAlbum(mActivity, selectLogoList);
            }
        });
        modifyHeadDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // 图片选择结果回调
            List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    selectLogoList = images;
                    String logo_path = loadImage(data, mImgActivityCreateBattleHead, selectLogoList);
                    head_path = logo_path;
                    break;
                case 10086:
                    selectBattleList = images;
                    String battlePath = loadImage(data, mImgActivityCreateBattleBattleQrcode, selectBattleList);
                    battle_path = battlePath;
                    break;
                case 10010:
                    selectLeaderList = images;
                    String leaderPath = loadImage(data, mImgActivityCreateBattleLeaderQrcode, selectLeaderList);
                    leader_path = leaderPath;
                    break;
                case 998:
                    selectAlbumList = PictureSelector.obtainMultipleResult(data);
                    for (int i = 0; i < selectAlbumList.size(); i++) {
                        LocalMedia localMedia = selectAlbumList.get(i);
                        mPhotos.add(getPath(localMedia));
                    }
                    mCreateBattleAlbumAdapter.setList(mPhotos);
                    mCreateBattleAlbumAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    /**
     * 加载图片
     *
     * @param data
     * @param imageView
     * @param images
     * @return
     */
    public String loadImage(Intent data, ImageView imageView, List<LocalMedia> images) {
        if (images != null && images.get(0) != null) {
            LocalMedia media = images.get(0);
            String path = getPath(media);
            GlideUtils.getInstance().loadLocalImage(path, imageView);
            return path;
        }
        return null;
    }

    /**
     * 获取图片本地路径
     *
     * @param media
     * @return
     */
    public String getPath(LocalMedia media) {
        String path;
//        if (media.isCut() && !media.isCompressed()) {
//            // 裁剪过
//            path = media.getCutPath();
//        } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
//            // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
//            path = media.getCompressPath();
//        } else {
//            // 原图
//            path = media.getPath();
//        }
        return media.getPath();
    }

    /**
     * 上传图片
     *
     * @param file_address 图片地址
     * @param type         上传的什么图片  1--头像  2--战队二维码  3--队长二维码  4--相册
     */
    public void uploadImage(final String file_address, final int type) {
        io.reactivex.Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        e.onNext(file_address);
                    }
                })
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        //获取图片缓存路径
                        FutureTarget<File> fileFutureTarget = Glide.with(BaseApplication.mApplicationContext)
                                .load(s)
                                .downloadOnly(500, 500);
                        String path="";
                        try {
                            File cacheFile = fileFutureTarget.get();
                            path = cacheFile.getAbsolutePath();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        return path;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String final_path) throws Exception {
                        Map<String, String> map = new HashMap<>();
                        map.put("type", "1");
                        map.put("square", "1");
                        File file = new File(final_path);
                        HttpUtils.getInstance().upload(Constants.UPLOAD_IMAGE, map, "file", file, new HttpCallback<UploadFileBean>() {
                            @Override
                            public void onSuccessExecute(UploadFileBean uploadFileBean) {
                                if (uploadFileBean.getStatus() == 0) {
                                    UploadFileBean.DataBean data = uploadFileBean.getData();
                                    switch (type) {
                                        case 1:
                                            head_key = data.getKey();
                                            break;
                                        case 2:
                                            battle_key = data.getKey();
                                            break;
                                        case 3:
                                            leader_key = data.getKey();
                                            break;
                                        case 4:
                                            camera_map.put(photos_index, data.getKey());
                                            photos_index++;
                                            break;
                                    }
                                    mHandler.sendEmptyMessage(10086);
                                }
                            }
                        });
                    }
                });

    }

    /**
     * 提交战队申请
     */
    public void submit() {
        int level_id = 0;
        for (int i = 0; i < mBattleGroupBeanData.size(); i++) {
            BattleGroupBean.DataBean dataBean = mBattleGroupBeanData.get(i);
            if (dataBean.isSelected()) {
                level_id = dataBean.getId();
            }
        }
        CreateBattleSubmitBean createBattleSubmitBean = new CreateBattleSubmitBean();
        createBattleSubmitBean.setActivity_id(SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        createBattleSubmitBean.setLogo(head_key);
        createBattleSubmitBean.setName(mEdtActivityCreateBattleName.getText().toString());
        createBattleSubmitBean.setDescription(mEdtActivityCreateBattleContent.getText().toString());
        if (mCity_id != -998 && mCity_id!=0) {
            createBattleSubmitBean.setCity_id(mCity_id + "");
        }
        createBattleSubmitBean.setLevel_id(level_id);
        createBattleSubmitBean.setGroup_qr_code(battle_key);
        createBattleSubmitBean.setMonitor_qr_code(leader_key);
        for (int i = 0; i < photos_index; i++) {
            camera_keys.add(camera_map.get(i));
        }
        createBattleSubmitBean.setPhotos(camera_keys);
        String submit_url;
        if (mType==1) {
            submit_url=Constants.UPDATE_TEAM_INFO;
            createBattleSubmitBean.setId(mTeam_id);
        }else{
            submit_url=Constants.CREATE_TEAM;
        }
        Gson gson = new Gson();
        String json = gson.toJson(createBattleSubmitBean);
        HttpUtils.getInstance().post_json(submit_url, json, new HttpCallback<CreateBattleBean>() {
            @Override
            public void onSuccessExecute(CreateBattleBean createBattleBean) {
                mProgressDialog.dismiss();
                if (createBattleBean.getStatus()==0) {
                    if (mType==1) {
                        Toast.makeText(CreateBattleActivity.this, "更新信息成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(CreateBattleActivity.this, "创建成功", Toast.LENGTH_SHORT).show();
                        int team_id = createBattleBean.getData();
                        Intent intent = new Intent(mActivity, TeamManageActivity.class);
                        intent.putExtra("team_id", team_id + "");
                        startActivity(intent);
                    }
                }else {
                    Toast.makeText(CreateBattleActivity.this, createBattleBean.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * 缓存数据
     */
    public void cacheData() {
        //从战队管理页过来更新数据不缓存数据
        if (mType == 1) {
            return;
        }
        CreateBattleCacheBean createBattleCacheBean = new CreateBattleCacheBean();
        createBattleCacheBean.setBattle_name(mEdtActivityCreateBattleName.getText().toString());
        createBattleCacheBean.setBattle_description(mEdtActivityCreateBattleContent.getText().toString());
        createBattleCacheBean.setHead_path(head_path);
        createBattleCacheBean.setLeader_path(leader_path);
        createBattleCacheBean.setBattle_path(battle_path);
        createBattleCacheBean.setCity_id(mCity_id);
        createBattleCacheBean.setCity_name(mCity_name);
        int level_id = 0;
        for (int i = 0; i < mBattleGroupBeanData.size(); i++) {
            BattleGroupBean.DataBean dataBean = mBattleGroupBeanData.get(i);
            if (dataBean.isSelected()) {
                level_id = dataBean.getId();
            }
        }
        createBattleCacheBean.setLevel_id(level_id);
        createBattleCacheBean.setPhotos(mPhotos);
        CacheUtils.getInstance().cachedata("createBattle.txt", createBattleCacheBean);
    }

    /**
     * 加载缓存数据
     */
    public void restoreData() {
        //从战队管理页过来更新数据不缓存数据
        if (mType == 1) {
            return;
        }
        CreateBattleCacheBean battleData = CacheUtils.getInstance().restoreData("createBattle.txt", new CreateBattleCacheBean());
        if (battleData == null) {
            return;
        }
        mEdtActivityCreateBattleName.setText(battleData.getBattle_name());
        mEdtActivityCreateBattleContent.setText(battleData.getBattle_description());
        if (!TextUtils.isEmpty(battleData.getHead_path())) {
            head_path = battleData.getHead_path();
            GlideUtils.getInstance().loadLocalImage(battleData.getHead_path(), mImgActivityCreateBattleHead);
        }
        if (!TextUtils.isEmpty(battleData.getBattle_path())) {
            battle_path = battleData.getBattle_path();
            GlideUtils.getInstance().loadLocalImage(battle_path, mImgActivityCreateBattleBattleQrcode);
        }
        if (!TextUtils.isEmpty(battleData.getLeader_path())) {
            leader_path = battleData.getLeader_path();
            GlideUtils.getInstance().loadLocalImage(battleData.getLeader_path(), mImgActivityCreateBattleLeaderQrcode);
        }
        List<String> photos = battleData.getPhotos();
        if (photos != null && photos.size() > 0) {
            mPhotos = photos;
            mCreateBattleAlbumAdapter.setList(photos);
            mCreateBattleAlbumAdapter.notifyDataSetChanged();
        }
        level_id = battleData.getLevel_id();
        //选中组别
        for (int i = 0; i < mBattleGroupBeanData.size(); i++) {
            if (mBattleGroupBeanData.get(i).getId() == level_id) {
                mBattleGroupBeanData.get(i).setSelected(true);
            }
        }
        mCreateBattleDistanceAdapter.setDataBeans(mBattleGroupBeanData);
        mCity_id = battleData.getCity_id();
        String city_name = battleData.getCity_name();
        if (mCity_id==0) {
            city_name="全国";
        }
        mCity_name = city_name;
        mTvActivityCreateBattleRegion.setText(city_name);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cacheData();
        finish();
    }

    @Override
    public int getRootView() {
        return R.layout.activity_create_battle;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_return:
                cacheData();
                finish();
                break;
            case R.id.img_activity_create_battle_head:
                requestPermissionRx();
                break;
            case R.id.img_activity_create_battle_battle_qrcode:
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions
                        .request(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    CameraHelper.goAlbum(mActivity, selectBattleList, 10086, false);
                                } else {
                                    PermissionDialogUtil.showPermissionDialog(mActivity, "缺少拍照或存储相关权限，请前往手机设置开启");
                                }
                            }
                        });
                break;
            case R.id.img_activity_create_battle_leader_qrcode:
                RxPermissions permissions = new RxPermissions(this);
                permissions
                        .request(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    CameraHelper.goAlbum(mActivity, selectLeaderList, 10010, false);
                                } else {
                                    PermissionDialogUtil.showPermissionDialog(mActivity, "缺少拍照或存储相关权限，请前往手机设置开启");
                                }
                            }
                        });
                break;
            case R.id.tv_activity_create_battle_region:
                AddressDialog addressDialog = new AddressDialog(mActivity);
                addressDialog.setOnAddressChooseListener(new AddressDialog.OnAddressChooseListener() {
                    @Override
                    public void onChoose(int city_id, String city_name) {
                        mCity_id = city_id;
                        mCity_name = city_name;
                        mTvActivityCreateBattleRegion.setText(city_name);
                    }
                });
                addressDialog.show();
                break;
            case R.id.tv_activity_create_battle_submit:
                //多次点击拦截
                if (AntiShakeClickUtils.isFastClick()) {
                    return;
                }

                head_key = null;
                battle_key = null;
                leader_key = null;
                camera_map.clear();

                String name = mEdtActivityCreateBattleName.getText().toString();
                if (TextUtils.isEmpty(name.trim())) {
                    Toast.makeText(mActivity, "请输入战队名称", Toast.LENGTH_SHORT).show();
                    mEdtActivityCreateBattleName.setFocusable(true);
                    return;
                }

                if (mCity_id==-998) {
                    Toast.makeText(mActivity, "请选择队员招募区域", Toast.LENGTH_SHORT).show();
                    mTvActivityCreateBattleRegion.setFocusable(true);
                    return;
                }

                boolean isChooseBattleGroup = false;
                for (BattleGroupBean.DataBean battleGroupBeanDatum : mBattleGroupBeanData) {
                    if (battleGroupBeanDatum.isSelected()) {
                        isChooseBattleGroup = true;
                    }
                }
                if (!isChooseBattleGroup) {
                    Toast.makeText(mActivity, "请选择日打卡目标", Toast.LENGTH_SHORT).show();
                    return;
                }
                String descript = mEdtActivityCreateBattleContent.getText().toString();
                if (TextUtils.isEmpty(descript.trim())) {
                    Toast.makeText(mActivity, "请输入战队描述", Toast.LENGTH_SHORT).show();
                    mEdtActivityCreateBattleContent.setFocusable(true);
                    return;
                }
                if (TextUtils.isEmpty(head_path)) {
                    Toast.makeText(mActivity, "请选择战队logo", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(battle_path)) {
                    Toast.makeText(mActivity, "请选择战队二维码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(leader_path)) {
                    Toast.makeText(mActivity, "请选择队长二维码", Toast.LENGTH_SHORT).show();
                    return;
                }
                //上传弹窗
                mProgressDialog = new ProgressDialog(mActivity);
                mProgressDialog.setMessage("正在上传，请稍后...");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();
                uploadImage(head_path, 1);
                uploadImage(battle_path, 2);
                uploadImage(leader_path, 3);
                //上传相册图片
                photos_index = 0;
                for (int i = 0; i < mPhotos.size(); i++) {
                    uploadImage(mPhotos.get(i), 4);
                }
                break;
        }
    }

}
