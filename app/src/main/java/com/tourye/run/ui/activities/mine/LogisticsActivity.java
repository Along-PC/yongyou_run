package com.tourye.run.ui.activities.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.LogisticsBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.LogisticsAdapter;
import com.tourye.run.utils.SaveUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:   LogisticsActivity
 *
 * @Author:   along
 *
 * @Description:    物流页面
 *
 * @CreateDate:   2019/4/29 9:25 AM
 *
 */
public class LogisticsActivity extends BaseActivity {
    private RecyclerView mRecyclerActivityLogistics;
    private TextView mTvActivityLogisticsNodata;


    @Override
    public void initView() {
        mRecyclerActivityLogistics = (RecyclerView) findViewById(R.id.recycler_activity_logistics);
        mTvActivityLogisticsNodata = (TextView) findViewById(R.id.tv_activity_logistics_nodata);

        mTvTitle.setText("物流列表");
    }

    @Override
    public void initData() {
        Map<String,String> map=new HashMap<>();
//        map.put("activity_id",SaveUtil.getString(SaveConstants.ACTION_ID,""));
        map.put("count","100");
        map.put("offset","0");
        HttpUtils.getInstance().get(Constants.LOGISTICS_LIST, map, new HttpCallback<LogisticsBean>() {
            @Override
            public void onSuccessExecute(LogisticsBean logisticsBean) {
                List<LogisticsBean.DataBean> data = logisticsBean.getData();
                if (data!=null && data.size()>0) {
                    LogisticsAdapter logisticsAdapter = new LogisticsAdapter(mActivity, data);
                    mRecyclerActivityLogistics.setLayoutManager(new LinearLayoutManager(mActivity));
                    mRecyclerActivityLogistics.setAdapter(logisticsAdapter);
                }else {
                    mRecyclerActivityLogistics.setVisibility(View.GONE);
                    mTvActivityLogisticsNodata.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.activity_logistics;
    }

}
