package com.tourye.run.ui.activities.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.BattleReportBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.BattleReportAdapter;
import com.tourye.run.ui.adapter.SignupBattleReportAdapter;
import com.tourye.run.utils.SaveUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: BattleReportActivity
 *
 * @Author: along
 *
 * @Description: 最新战报页面
 *
 * @CreateDate: 2019/3/18 2:06 PM
 */

public class BattleReportActivity extends BaseActivity {
    private SmartRefreshLayout mRefreshLayoutActivityBattleReport;
    private RecyclerView mRecyclerActivityBattleReport;
    private BattleReportAdapter mBattleReportAdapter;
    private List<BattleReportBean.DataBean> mDataBeans = new ArrayList<>();
    private String last_id="";//最后一条数据的id
    private ClassicsFooter mFooterActivityBattleReport;

    @Override
    public void initView() {
        mRefreshLayoutActivityBattleReport = (SmartRefreshLayout) findViewById(R.id.refreshLayout_activity_battle_report);
        mRecyclerActivityBattleReport = (RecyclerView) findViewById(R.id.recycler_activity_battle_report);
        mFooterActivityBattleReport = (ClassicsFooter) findViewById(R.id.footer_activity_battle_report);

        mTvTitle.setText("最新战报");
    }

    public void getBattleReportData(final boolean isLoadMore) {
        Map<String, String> map = new HashMap<>();
        map.put("id", SaveUtil.getString("action_id", ""));
        map.put("count", "10");
        if (isLoadMore) {
            map.put("last_id", last_id);
        }
        HttpUtils.getInstance().get(Constants.BATTLE_REPORT_LIST, map, new HttpCallback<BattleReportBean>() {
            @Override
            public void onPreExcute() {
                super.onPreExcute();
                if (isLoadMore) {
                    mRefreshLayoutActivityBattleReport.finishLoadMore();
                }else{
                    mRefreshLayoutActivityBattleReport.finishRefresh();
                }
            }

            @Override
            public void onSuccessExecute(BattleReportBean battleReportBean) {
                List<BattleReportBean.DataBean> data = battleReportBean.getData();
                if (data != null && data.size() > 0) {
                    if (!isLoadMore) {
                        mDataBeans.clear();
                    }
                    mDataBeans.addAll(data);
                    mBattleReportAdapter.setDataBeans(mDataBeans);
                    BattleReportBean.DataBean dataBean = data.get(data.size() - 1);
                    last_id=dataBean.getId()+"";
                }
            }
        });
    }

    @Override
    public void initData() {
        mBattleReportAdapter = new BattleReportAdapter(mActivity);
        mRecyclerActivityBattleReport.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerActivityBattleReport.setAdapter(mBattleReportAdapter);

        getBattleReportData(false);

        mRefreshLayoutActivityBattleReport.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getBattleReportData(true);
            }
        });
        mRefreshLayoutActivityBattleReport.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                last_id="";
                getBattleReportData(false);
            }
        });

    }

    @Override
    public int getRootView() {
        return R.layout.activity_battle_report;
    }
}
