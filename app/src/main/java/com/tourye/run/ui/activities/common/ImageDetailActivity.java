package com.tourye.run.ui.activities.common;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.ui.adapter.ImageDetailAdapter;

import java.util.ArrayList;

/**
 * ImageDetailActivity
 * author:along
 * 2018/9/27 下午4:35
 *
 * 描述:发现社区图片放大页面
 */

public class ImageDetailActivity extends BaseActivity {

    private ViewPager mVpActivityImageDetail;
    private TextView mTvActivityImageDetail;
    private ImageView mImgActivityImageDetail;

    @Override
    public void initView() {
        mVpActivityImageDetail = (ViewPager) findViewById(R.id.vp_activity_image_detail);
        mTvActivityImageDetail = (TextView) findViewById(R.id.tv_activity_image_detail);
        mImgActivityImageDetail = (ImageView) findViewById(R.id.img_activity_image_detail);
        mImgActivityImageDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        final ArrayList<String> data = intent.getStringArrayListExtra("data");
        final int pos = intent.getIntExtra("pos", 998);
        if (data==null || pos==998) {
            finish();
        }
        mTvActivityImageDetail.setText((pos+1)+"/"+data.size());
        ImageDetailAdapter imageDetailAdapter = new ImageDetailAdapter(mActivity, data);
        imageDetailAdapter.setOnImageClickListener(new ImageDetailAdapter.OnImageClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        mVpActivityImageDetail.setAdapter(imageDetailAdapter);
        mVpActivityImageDetail.setCurrentItem(pos);
        mVpActivityImageDetail.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvActivityImageDetail.setText((position+1)+"/"+data.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean isNeedTitle() {
        return false;
    }

    @Override
    public int getRootView() {
        return R.layout.activity_image_detail;
    }
}
