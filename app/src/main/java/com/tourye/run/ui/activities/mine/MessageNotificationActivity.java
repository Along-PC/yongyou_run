package com.tourye.run.ui.activities.mine;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.CheckNewsBean;
import com.tourye.run.bean.UserBasicInfoBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.CommonFragmentAdapter;
import com.tourye.run.ui.fragments.MessageCommentFragment;
import com.tourye.run.ui.fragments.MessageSystemFragment;
import com.tourye.run.ui.fragments.MessageThumbFragment;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: MessageNotificationActivity
 * @Author: along
 * @Description: 消息通知页面
 * @CreateDate: 2019/3/25 5:19 PM
 */
public class MessageNotificationActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTvActivityMessageNotifySystem;
    private TextView mTvActivityMessageNotifyComment;
    private TextView mTvActivityMessageNotifyThumb;
    private View mViewActivityMessageNotifyLine;
    private RelativeLayout mRlActivityMessageNotifySystem;
    private ImageView mImgActivityMessageNotifySystem;
    private RelativeLayout mRlActivityMessageNotifyComment;
    private ImageView mImgActivityMessageNotifyComment;
    private RelativeLayout mRlActivityMessageNotifyThumb;
    private ImageView mImgActivityMessageNotifyThumb;
    private ViewPager mVpActivityMessageNotify;

    private TextView mCurrent;//当前选中导航


    @Override
    public void initView() {
        mTvActivityMessageNotifySystem = (TextView) findViewById(R.id.tv_activity_message_notify_system);
        mTvActivityMessageNotifyComment = (TextView) findViewById(R.id.tv_activity_message_notify_comment);
        mTvActivityMessageNotifyThumb = (TextView) findViewById(R.id.tv_activity_message_notify_thumb);
        mViewActivityMessageNotifyLine = (View) findViewById(R.id.view_activity_message_notify_line);
        mRlActivityMessageNotifySystem = (RelativeLayout) findViewById(R.id.rl_activity_message_notify_system);
        mImgActivityMessageNotifySystem = (ImageView) findViewById(R.id.img_activity_message_notify_system);
        mRlActivityMessageNotifyComment = (RelativeLayout) findViewById(R.id.rl_activity_message_notify_comment);
        mImgActivityMessageNotifyComment = (ImageView) findViewById(R.id.img_activity_message_notify_comment);
        mRlActivityMessageNotifyThumb = (RelativeLayout) findViewById(R.id.rl_activity_message_notify_thumb);
        mImgActivityMessageNotifyThumb = (ImageView) findViewById(R.id.img_activity_message_notify_thumb);
        mVpActivityMessageNotify = (ViewPager) findViewById(R.id.vp_activity_message_notify);

        mTvTitle.setText("消息通知");

        mRlActivityMessageNotifySystem.setOnClickListener(this);
        mRlActivityMessageNotifyComment.setOnClickListener(this);
        mRlActivityMessageNotifyThumb.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mViewActivityMessageNotifyLine.post(new Runnable() {
            @Override
            public void run() {
                mCurrent = mTvActivityMessageNotifySystem;
                mTvActivityMessageNotifySystem.setSelected(true);
                translateLine(1);
            }
        });

        checkNews();

        initVp();

    }

    private void initVp() {
        List<Fragment> fragments = new ArrayList<>();
        MessageSystemFragment messageSystemFragment = new MessageSystemFragment();
        MessageCommentFragment messageCommentFragment = new MessageCommentFragment();
        MessageThumbFragment messageThumbFragment = new MessageThumbFragment();
        fragments.add(messageSystemFragment);
        fragments.add(messageCommentFragment);
        fragments.add(messageThumbFragment);
        CommonFragmentAdapter commonFragmentAdapter = new CommonFragmentAdapter(getSupportFragmentManager());
        commonFragmentAdapter.setFragments(fragments);
        mVpActivityMessageNotify.setAdapter(commonFragmentAdapter);
        mVpActivityMessageNotify.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        translateLine(1);
                        changeNavi(mTvActivityMessageNotifySystem);
                        break;
                    case 1:
                        translateLine(2);
                        changeNavi(mTvActivityMessageNotifyComment);
                        break;
                    case 2:
                        translateLine(3);
                        changeNavi(mTvActivityMessageNotifyThumb);
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /**
     * 是否有最新消息
     */
    private void checkNews() {
        Intent intent = getIntent();
        CheckNewsBean.DataBean data = (CheckNewsBean.DataBean) intent.getSerializableExtra("data");
        if (data==null) {
            return;
        }
        mImgActivityMessageNotifySystem.setVisibility(data.isNotice() == true ? View.VISIBLE : View.GONE);
        mImgActivityMessageNotifyComment.setVisibility(data.isReply() == true ? View.VISIBLE : View.GONE);
        mImgActivityMessageNotifyThumb.setVisibility(data.isLike() == true ? View.VISIBLE : View.GONE);
    }

    public void translateLine(int index) {
        int screenWidth = DensityUtils.getScreenWidth();
        float unitWidth = screenWidth / 3;
        float width = mViewActivityMessageNotifyLine.getWidth();
        float distance = (unitWidth - width) / 2 + unitWidth * (index - 1);
        float current = mViewActivityMessageNotifyLine.getX();
        ObjectAnimator translationX = ObjectAnimator.ofFloat(mViewActivityMessageNotifyLine, "translationX", current, distance);
        translationX.setDuration(200);
        translationX.start();

        switch (index) {
            case 1:
                mImgActivityMessageNotifySystem.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mImgActivityMessageNotifySystem.setVisibility(View.GONE);
                    }
                },1000);
                break;
            case 2:
                mImgActivityMessageNotifyComment.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mImgActivityMessageNotifyComment.setVisibility(View.GONE);
                    }
                },1000);
                break;
            case 3:
                mImgActivityMessageNotifyThumb.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mImgActivityMessageNotifyThumb.setVisibility(View.GONE);
                    }
                },1000);
                break;
        }
    }

    public void changeNavi(TextView textView) {
        mCurrent.setSelected(false);
        mCurrent = textView;
        mCurrent.setSelected(true);
    }

    @Override
    public int getRootView() {
        return R.layout.activity_message_notification;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_activity_message_notify_system:
                translateLine(1);
                changeNavi(mTvActivityMessageNotifySystem);
                mVpActivityMessageNotify.setCurrentItem(0);
                break;
            case R.id.rl_activity_message_notify_comment:
                translateLine(2);
                changeNavi(mTvActivityMessageNotifyComment);
                mVpActivityMessageNotify.setCurrentItem(1);
                break;
            case R.id.rl_activity_message_notify_thumb:
                translateLine(3);
                changeNavi(mTvActivityMessageNotifyThumb);
                mVpActivityMessageNotify.setCurrentItem(2);
                break;
        }
    }
}
