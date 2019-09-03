package com.tourye.run.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseFragment;
import com.tourye.run.bean.InfluencePeopleBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.InfluencePeopleAdapter;
import com.tourye.run.utils.SaveUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:   InfluencePeoplefragment
 *
 * @Author:   along
 *
 * @Description:    影响人数报名---未报名列表页面
 *
 * @CreateDate:   2019/4/19 5:29 PM
 *
 */
public class InfluencePeoplefragment extends BaseFragment {
    private SmartRefreshLayout mRefreshLayoutFragmentInfluencePeople;
    private RecyclerView mRecyclerFragmentInfluencePeople;
    private List<InfluencePeopleBean.DataBean> mInfluencePeoples=new ArrayList<>();
    private int mOffset=0;
    private int mIsJoined=0;//0---未报名  1---已经报名
    private String mTeamId;
    private InfluencePeopleAdapter mInfluencePeopleAdapter;

    @Override
    public void initView(View view) {
        mRefreshLayoutFragmentInfluencePeople = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout_fragment_influence_people);
        mRecyclerFragmentInfluencePeople = (RecyclerView) view.findViewById(R.id.recycler_fragment_influence_people);

    }

    @Override
    public void initData() {

        Bundle arguments = getArguments();
        mTeamId=arguments.getString("team_id");
        mIsJoined=arguments.getInt("is_joined");

        mRefreshLayoutFragmentInfluencePeople.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mOffset=0;
                getInfluencePeople(false);
            }
        });

        mRefreshLayoutFragmentInfluencePeople.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getInfluencePeople(true);
            }
        });

        getInfluencePeople(false);
        mRecyclerFragmentInfluencePeople.setLayoutManager(new LinearLayoutManager(mActivity));
        mInfluencePeopleAdapter = new InfluencePeopleAdapter(mActivity);
        mInfluencePeopleAdapter.setDataBeans(mInfluencePeoples);
        mRecyclerFragmentInfluencePeople.setAdapter(mInfluencePeopleAdapter);
    }

    public void getInfluencePeople(final boolean isLoadMore){
        Map<String,String> map=new HashMap<>();
        map.put("activity_id",SaveUtil.getString(SaveConstants.ACTION_ID,""));
        map.put("team_id",mTeamId);
        map.put("joined",mIsJoined+"");
        map.put("count","10");
        map.put("offset",mOffset+"");
        HttpUtils.getInstance().get(Constants.INFLUENCE_PEOPLE_LIST, map, new HttpCallback<InfluencePeopleBean>() {
            @Override
            public void onPreExcute() {
                super.onPreExcute();
                if (isLoadMore) {
                    mRefreshLayoutFragmentInfluencePeople.finishLoadMore();

                }else{
                    mRefreshLayoutFragmentInfluencePeople.finishRefresh();
                }
            }

            @Override
            public void onSuccessExecute(InfluencePeopleBean influencePeopleBean) {
                List<InfluencePeopleBean.DataBean> data = influencePeopleBean.getData();
                if (data!=null && data.size()>0) {
                    if (!isLoadMore) {
                        mInfluencePeoples.clear();
                    }
                    mInfluencePeoples.addAll(data);
                    mInfluencePeopleAdapter.setDataBeans(mInfluencePeoples);
                    mOffset+=data.size();
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_influence_people;
    }
}
