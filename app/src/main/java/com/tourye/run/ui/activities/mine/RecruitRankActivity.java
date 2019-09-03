package com.tourye.run.ui.activities.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.BattleRecruitBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.RecruitRankAdapter;
import com.tourye.run.utils.SaveUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: RecruitRankActivity
 * @Author: along
 * @Description: 招募排行榜页面
 * @CreateDate: 2019/4/18 2:39 PM
 */

public class RecruitRankActivity extends BaseActivity {
    private RecyclerView mRecyclerActivityRecruitRank;
    private SmartRefreshLayout mRefreshLayoutActivityRecruitRank;
    private int mOffset;
    private List<BattleRecruitBean.DataBean.CurrentBean> mRankBeans = new ArrayList<>();
    private RecruitRankAdapter mRecruitRankAdapter;

    @Override
    public void initView() {
        mRecyclerActivityRecruitRank = (RecyclerView) findViewById(R.id.recycler_activity_recruit_rank);
        mRefreshLayoutActivityRecruitRank = (SmartRefreshLayout) findViewById(R.id.refreshLayout_activity_recruit_rank);
        mTvTitle.setText("招募榜单");
    }

    @Override
    public void initData() {
        mRecruitRankAdapter = new RecruitRankAdapter(mActivity);
        mRecruitRankAdapter.setCurrentBeans(mRankBeans);
        mRecyclerActivityRecruitRank.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerActivityRecruitRank.setAdapter(mRecruitRankAdapter);

        getRankData(false);

        mRefreshLayoutActivityRecruitRank.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getRankData(true);
            }
        });
        mRefreshLayoutActivityRecruitRank.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mOffset = 0;
                getRankData(false);
            }
        });
    }

    public void getRankData(final boolean isLoadMore) {
        Map<String, String> map = new HashMap<>();
        map.put("id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        map.put("count", 10 + "");
        map.put("offset", mOffset + "");
        HttpUtils.getInstance().get(Constants.team_recruit_rank, map, new HttpCallback<BattleRecruitBean>() {

            @Override
            public void onPreExcute() {
                super.onPreExcute();
                if (isLoadMore) {
                    mRefreshLayoutActivityRecruitRank.finishLoadMore();
                } else {
                    mRefreshLayoutActivityRecruitRank.finishRefresh();
                }
            }

            @Override
            public void onSuccessExecute(BattleRecruitBean battleRecruitBean) {
                BattleRecruitBean.DataBean data = battleRecruitBean.getData();
                if (data == null) {
                    return;
                }
                BattleRecruitBean.DataBean.CurrentBean current = data.getCurrent();
                List<BattleRecruitBean.DataBean.CurrentBean> list = data.getList();
                if (!isLoadMore) {
                    mRankBeans.clear();
                }
                if (current != null) {
                    mRankBeans.add(current);
                }
                if (list != null) {
                    mRankBeans.addAll(list);
                    mOffset += list.size();
                }
                mRecruitRankAdapter.setCurrentBeans(mRankBeans);
            }
        });

    }

    @Override
    public int getRootView() {
        return R.layout.activity_recruit_rank;
    }

}
