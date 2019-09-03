package com.tourye.run.ui.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.base.BaseFragment;
import com.tourye.run.bean.PersonInviteRankBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.PersonInviteRankAdapter;
import com.tourye.run.utils.SaveUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: PersonInviteRankingFragment
 * @Author: along
 * @Description: 个人邀请排行榜页面
 * @CreateDate: 2019/4/2 10:23 AM
 */
public class PersonInviteRankingFragment extends BaseFragment {
    private SmartRefreshLayout mRefreshLayoutPersonInviteRank;
    private RecyclerView mRecyclerFragmentPersonInviteRank;

    private boolean isFirstLoad = true;//是否首次加载
    private int mOffset = 0;
    private List<PersonInviteRankBean.DataBean.ListBean> mInviteBeans = new ArrayList<>();
    private PersonInviteRankAdapter mPersonInviteRankAdapter;

    @Override
    public void initView(View view) {
        mRefreshLayoutPersonInviteRank = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout_person_invite_rank);
        mRecyclerFragmentPersonInviteRank = (RecyclerView) view.findViewById(R.id.recycler_fragment_person_invite_rank);
        mRecyclerFragmentPersonInviteRank.setFocusableInTouchMode(false);
        mRecyclerFragmentPersonInviteRank.setFocusable(false);
    }

    @Override
    public void initData() {
        getInviteData(false);
        mRefreshLayoutPersonInviteRank.setEnableAutoLoadMore(false);
        mRefreshLayoutPersonInviteRank.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mRefreshLayoutPersonInviteRank.setEnableLoadMore(true);
                mOffset = 0;
                getInviteData(false);
            }
        });
        mRefreshLayoutPersonInviteRank.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getInviteData(true);
            }
        });
    }

    public void getInviteData(final boolean isLoadmore) {
        Map<String, String> map = new HashMap<>();
        map.put("id", SaveUtil.getString("action_id", ""));
        map.put("count", "10");
        map.put("offset", mOffset + "");
        HttpUtils.getInstance().get(Constants.PERSON_INVITE_RANK, map, new HttpCallback<PersonInviteRankBean>() {
            @Override
            public void onPreExcute() {
                super.onPreExcute();
                if (isLoadmore) {
                    mRefreshLayoutPersonInviteRank.finishLoadMore();
                } else {
                    mRefreshLayoutPersonInviteRank.finishRefresh();
                }
            }

            @Override
            public void onSuccessExecute(PersonInviteRankBean personInviteRankBean) {
                PersonInviteRankBean.DataBean data = personInviteRankBean.getData();
                if (data != null) {
                    PersonInviteRankBean.DataBean.ListBean current = data.getCurrent();
                    List<PersonInviteRankBean.DataBean.ListBean> list = data.getList();
                    if (!isLoadmore) {
                        mInviteBeans.clear();
                        if (current != null) {
                            mInviteBeans.add(current);
                        }
                    }
                    if (list != null) {
                        mOffset += list.size();
                        mInviteBeans.addAll(list);
                    }
                    if (isFirstLoad) {
                        mPersonInviteRankAdapter = new PersonInviteRankAdapter(mActivity, mInviteBeans);
                        mRecyclerFragmentPersonInviteRank.setLayoutManager(new LinearLayoutManager(mActivity));
                        mRecyclerFragmentPersonInviteRank.setAdapter(mPersonInviteRankAdapter);
                        isFirstLoad=false;
                    } else {
                        mPersonInviteRankAdapter.setInviteBeans(mInviteBeans);
                    }
                    if (list.size()<10) {
                        mRefreshLayoutPersonInviteRank.setEnableLoadMore(false);
                    }
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_person_invite_rank;
    }
}
