package com.tourye.run.ui.activities.punch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.CheckPunchBean;
import com.tourye.run.bean.CommonBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.common.CommonWebActivity;
import com.tourye.run.ui.activities.mine.CheckImageActivity;
import com.tourye.run.ui.dialogs.RejectReasonDialog;
import com.tourye.run.utils.CacheUtils;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.ImageUtils;
import com.tourye.run.utils.SaveUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:   CheckPunchActivity
 *
 * @Author:   along
 *
 * @Description:    审核打卡页面
 *
 * @CreateDate:   2019/4/8 11:47 AM
 *
 */
public class CheckPunchActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTvActivityCheckPunchTitle;
    private TextView mTvActivityCheckPunchRule;
    private ImageView mImgActivityCheckPunchCreate;
    private ImageView mImgActivityCheckPunchLast;
    private ImageView mImgActivityCheckPunchNext;
    private ImageView mImgActivityCheckPunchHead;
    private TextView mTvActivityCheckPunchName;
    private TextView mTvActivityCheckPunchIntro;
    private TextView mTvActivityCheckPunchDiatance;
    private TextView mTvActivityCheckPunchTime;
    private TextView mTvActivityCheckPunchNopass;
    private TextView mTvActivityCheckPunchPass;
    private LinearLayout mLlActivityCheckPunchRecord;//有记录时页面
    private ConstraintLayout mConstraintActivityCheckPunchNorecord;//无记录时页面
    private ImageView mImgActivityCheckPunchResult;//审核结果
    private LinearLayout mLlActivityCheckPunchLabel;//审核结果的标签---比如审核拒绝几次。。。

    private CheckPunchBean.DataBean mDataBean;//当前展示审核卡片信息
    private List<CheckPunchBean.DataBean> mBeanList=new ArrayList<>();//批阅过的卡片信息

    private int mOffset=0;//当前打卡记录偏移量，向左为正

    @Override
    public void initView() {

        mTvActivityCheckPunchTitle = (TextView) findViewById(R.id.tv_activity_check_punch_title);
        mTvActivityCheckPunchRule = (TextView) findViewById(R.id.tv_activity_check_punch_rule);
        mImgActivityCheckPunchCreate = (ImageView) findViewById(R.id.img_activity_check_punch_create);
        mImgActivityCheckPunchLast = (ImageView) findViewById(R.id.img_activity_check_punch_last);
        mImgActivityCheckPunchNext = (ImageView) findViewById(R.id.img_activity_check_punch_next);
        mImgActivityCheckPunchHead = (ImageView) findViewById(R.id.img_activity_check_punch_head);
        mTvActivityCheckPunchName = (TextView) findViewById(R.id.tv_activity_check_punch_name);
        mTvActivityCheckPunchIntro = (TextView) findViewById(R.id.tv_activity_check_punch_intro);
        mTvActivityCheckPunchDiatance = (TextView) findViewById(R.id.tv_activity_check_punch_diatance);
        mTvActivityCheckPunchTime = (TextView) findViewById(R.id.tv_activity_check_punch_time);
        mTvActivityCheckPunchNopass = (TextView) findViewById(R.id.tv_activity_check_punch_nopass);
        mTvActivityCheckPunchPass = (TextView) findViewById(R.id.tv_activity_check_punch_pass);
        mLlActivityCheckPunchRecord = (LinearLayout) findViewById(R.id.ll_activity_check_punch_record);
        mConstraintActivityCheckPunchNorecord = (ConstraintLayout) findViewById(R.id.constraint_activity_check_punch_norecord);
        mImgActivityCheckPunchResult = (ImageView) findViewById(R.id.img_activity_check_punch_result);
        mLlActivityCheckPunchLabel = (LinearLayout) findViewById(R.id.ll_activity_check_punch_label);

        mTvActivityCheckPunchNopass.setOnClickListener(this);
        mTvActivityCheckPunchPass.setOnClickListener(this);
        mImgActivityCheckPunchLast.setOnClickListener(this);
        mImgActivityCheckPunchNext.setOnClickListener(this);
        mTvActivityCheckPunchRule.setOnClickListener(this);

        mTvTitle.setText("审核打卡");
        mTvActivityCheckPunchTitle.setText("审核打卡("+SaveUtil.getString(SaveConstants.LEVEL_GROUP,"-")+"km组别)");

    }

    @Override
    public void initData() {
        long current_time = System.currentTimeMillis();
        long save_time = SaveUtil.getLong("save_time", 0);
        //复查三分钟内的审核
        if (current_time-save_time<3*1000*60) {
            ArrayList<CheckPunchBean.DataBean> dataBeans = CacheUtils.getInstance().restoreData("check_punch.txt", new ArrayList<CheckPunchBean.DataBean>());
            if (dataBeans!=null && dataBeans.size()>0) {
                mBeanList.addAll(dataBeans);
                mImgActivityCheckPunchLast.setVisibility(View.VISIBLE);
            }
        }
        getCheckPunchInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveUtil.putLong("save_time",System.currentTimeMillis());
        List<CheckPunchBean.DataBean> beans=new ArrayList<>();//批阅过的卡片信息
        for (int i = 0; i < mBeanList.size(); i++) {
            CheckPunchBean.DataBean dataBean = mBeanList.get(i);
            if (dataBean.isHas_operate()) {
                beans.add(dataBean);
            }
        }
        CacheUtils.getInstance().cachedata("check_punch.txt",beans);
    }

    /**
     * 审核打卡
     * @param result
     */
    public void checkPunch(final String result,String reason){
        Map<String,String> map=new HashMap<>();
        map.put("id",mDataBean.getId()+"");
        map.put("action",result);
        map.put("reason",reason);
        HttpUtils.getInstance().post(Constants.GET_CHECK_PUNCH, map, new HttpCallback<CommonBean>() {
            @Override
            public void onSuccessExecute(CommonBean commonBean) {
                if (commonBean.getStatus()==0) {
                    if ("accepted".equalsIgnoreCase(result)) {
                        mDataBean.setHas_operate(true);
                        mDataBean.setIs_success(true);
                    }else if("rejected".equalsIgnoreCase(result)){
                        mDataBean.setHas_operate(true);
                        mDataBean.setIs_success(false);
                    }
                    if (mBeanList.size()>0) {
                        mImgActivityCheckPunchLast.setVisibility(View.VISIBLE);
                    }
                    getCheckPunchInfo();
                }
            }
        });
    }

    /**
     * 获取可以审核的卡片
     */
    public void getCheckPunchInfo(){
        Map<String,String> map=new HashMap<>();
        map.put("id",SaveUtil.getString(SaveConstants.ACTION_ID,""));
        HttpUtils.getInstance().get(Constants.GET_CHECK_PUNCH, map, new HttpCallback<CheckPunchBean>() {
            @Override
            public void onSuccessExecute(CheckPunchBean checkPunchBean) {
                CheckPunchBean.DataBean data = checkPunchBean.getData();
                if (data==null) {
                    mLlActivityCheckPunchRecord.setVisibility(View.GONE);
                    mConstraintActivityCheckPunchNorecord.setVisibility(View.VISIBLE);
                }else{
                    mLlActivityCheckPunchRecord.setVisibility(View.VISIBLE);
                    mConstraintActivityCheckPunchNorecord.setVisibility(View.GONE);
                    mBeanList.add(data);
                    loadData(data,false);
                }
            }
        });
    }

    /**
     *
     * @param data  打卡数据
     * @param isShowResult  是否显示审核结果
     */
    public void loadData(final CheckPunchBean.DataBean data, boolean isShowResult){
        mDataBean=data;
        Glide.with(this).asBitmap().load(data.getImage()).into(new SimpleTarget<Bitmap>() {

            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Bitmap along = ImageUtils.addTextWatermark(resource, data.getNickname(),  false);
                mImgActivityCheckPunchCreate.setImageBitmap(along);
            }

        });
        mImgActivityCheckPunchCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, CheckImageActivity.class);
                intent.putExtra("url",data.getImage());
                intent.putExtra("name",data.getNickname());
                startActivity(intent);
            }
        });
        GlideUtils.getInstance().loadCircleImage(data.getAvatar(),mImgActivityCheckPunchHead);
        mTvActivityCheckPunchName.setText(data.getNickname());
        mTvActivityCheckPunchDiatance.setText(data.getDistance()+"km");
        mTvActivityCheckPunchTime.setText(data.getTime()+"分钟");
        mTvActivityCheckPunchIntro.setText(data.getDate());

        //审核拒绝次数
        int sign_in_rejected_count = data.getSign_in_rejected_count();
        mLlActivityCheckPunchLabel.removeAllViews();
        mImgActivityCheckPunchResult.setVisibility(View.GONE);
        if (data.getCross_country_run()==1) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(mActivity);
            textView.setPadding(DensityUtils.dp2px(8),DensityUtils.dp2px(5),DensityUtils.dp2px(8),DensityUtils.dp2px(5));
            textView.setText("越野跑");
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.shape_check_punch_label);
            layoutParams.setMargins(0,0,0,DensityUtils.dp2px(10));
            layoutParams.gravity=Gravity.CENTER_HORIZONTAL;
            textView.setLayoutParams(layoutParams);
            mLlActivityCheckPunchLabel.addView(textView);
        }
        if (sign_in_rejected_count==1) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(mActivity);
            textView.setPadding(DensityUtils.dp2px(8),DensityUtils.dp2px(5),DensityUtils.dp2px(8),DensityUtils.dp2px(5));
            textView.setText("该打卡被判不通过1次，请仔细审核");
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.shape_check_punch_label);
            layoutParams.setMargins(0,0,0,DensityUtils.dp2px(10));
            layoutParams.gravity=Gravity.CENTER_HORIZONTAL;
            textView.setLayoutParams(layoutParams);
            mLlActivityCheckPunchLabel.addView(textView);
        }
        if (isShowResult) {
            if (data.isIs_success()) {
                mImgActivityCheckPunchResult.setVisibility(View.VISIBLE);
            }else{
                if (data.isHas_operate()) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    TextView textView = new TextView(mActivity);
                    textView.setPadding(DensityUtils.dp2px(8),DensityUtils.dp2px(5),DensityUtils.dp2px(8),DensityUtils.dp2px(5));
                    textView.setText("该打卡被判不通过");
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                    textView.setTextColor(Color.WHITE);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundResource(R.drawable.shape_check_punch_label);
                    layoutParams.gravity=Gravity.CENTER_HORIZONTAL;
                    textView.setLayoutParams(layoutParams);
                    mLlActivityCheckPunchLabel.addView(textView);
                }
            }
        }else{
            mImgActivityCheckPunchResult.setVisibility(View.GONE);
        }
    }



    @Override
    public int getRootView() {
        return R.layout.activity_check_punch;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_activity_check_punch_nopass:
                if (mDataBean!=null) {
                    RejectReasonDialog rejectReasonDialog = new RejectReasonDialog(mActivity);
                    rejectReasonDialog.setOnReasonSelectedListener(new RejectReasonDialog.OnReasonSelectedListener() {
                        @Override
                        public void onSelected(String reason) {
                            checkPunch("rejected",reason);
                        }
                    });
                    rejectReasonDialog.show();
                }
                break;
            case R.id.tv_activity_check_punch_pass:
                if (mDataBean!=null) {
                    checkPunch("accepted","");
                }
                break;
            case R.id.img_activity_check_punch_last:
                mOffset++;
                if (mOffset==mBeanList.size()-1) {
//                    mOffset--;
                    mImgActivityCheckPunchLast.setVisibility(View.GONE);
                }

                mDataBean = mBeanList.get(mBeanList.size() - 1 - mOffset);
                loadData(mDataBean,true);
                break;
            case R.id.img_activity_check_punch_next:
                if (mOffset>0) {
                    mImgActivityCheckPunchLast.setVisibility(View.VISIBLE);
                    mOffset--;
                    mDataBean = mBeanList.get(mBeanList.size() - 1 - mOffset );
                    loadData(mDataBean,true);
                }else{
                    if (mDataBean!=null) {
                        checkPunch("pass","");
                    }
                }
                break;
            case R.id.tv_activity_check_punch_rule:
                Intent intent = new Intent(mActivity, CommonWebActivity.class);
                intent.putExtra("url","http://mp.weixin.qq.com/s?__biz=MzU5ODY4NjM3OQ==&mid=100000364&idx=1&sn=0d8e60edd8e57d7e7283a12960ca2b07&chksm=7e412da84936a4beae4e0dac2252ba99ec9fe2b4ec80f776c5d06287d1dc120cfee0c29d5713#rd");
                intent.putExtra("title","审核规则");
                startActivity(intent);
                break;
        }
    }
}
