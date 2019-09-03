package com.tourye.run.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.ui.adapter.AreaAdapter;
import com.tourye.run.utils.AreaUtils;

/**
 *  
 * @ClassName:   AreaActivity
 *
 * @Author:   along
 * 
 * @Description:     地区选择页面
 * 
 * @CreateDate:   2019/5/21 3:06 PM
 * 
 */
public class AreaActivity extends BaseActivity {
    private RecyclerView mRecyclerActivityArea;
    private AreaAdapter mAreaAdapter;

    public static final int REQUEST_CODE=10086;
    public static final int RESULT_CODE=10010;

    @Override
    public void initView() {
        mRecyclerActivityArea = (RecyclerView) findViewById(R.id.recycler_activity_area);

        mTvTitle.setText("区域选择");
        mAreaAdapter = new AreaAdapter(mActivity, AreaUtils.getArea());
        mRecyclerActivityArea.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerActivityArea.setAdapter(mAreaAdapter);
    }

    @Override
    public void initData() {

        mAreaAdapter.setOnItemClickListener(new AreaAdapter.OnItemClickListener() {
            @Override
            public void onClick(String code) {
                Intent intent = getIntent();
                intent.putExtra("code",code);
                setResult(RESULT_CODE,intent);
                finish();
            }
        });


    }

    @Override
    public int getRootView() {
        return R.layout.activity_area;
    }
}
