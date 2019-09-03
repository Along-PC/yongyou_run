package com.tourye.run.ui.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.base.BaseFragment;
import com.tourye.run.bean.PersonThumbRankBean;
import com.tourye.run.bean.TeamThumbRankBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.PersonThumbRankAdapter;
import com.tourye.run.ui.adapter.TeamThumbRankAdapter;
import com.tourye.run.utils.SaveUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @ClassName:   BattlePopularRankingFragment
 *
 * @Author:   along
 *
 * @Description:    战队和个人点赞排行表
 *
 * @CreateDate:   2019/4/1 3:05 PM
 *
 */
public class BattlePopularRankingFragment extends BaseFragment {
    private TextView mTvFragmentBattlePopularRanking;
    private TextView mTvBattlePopularRankingSwitch;
    private SmartRefreshLayout mRefreshLayoutBattlePopularRanking;
    private RecyclerView mRecyclerFragmentBattlePopularRanking;//战队排行
    private SmartRefreshLayout mRefreshLayoutPersonPopularRanking;
    private RecyclerView mRecyclerPersonPopularRanking;

    private int mTeamOffset=0;//战队排行数据索引
    private int mPersonOffset=0;//个人排行数据索引

    private boolean isFirstLoadTeam=true;//是否首次加载战队数据
    private boolean isFirstLoadPerson=true;//是否首次加载个人数据

    private List<TeamThumbRankBean.DataBean.ListBean> mTeamRankData=new ArrayList<>();
    private List<PersonThumbRankBean.DataBean.ListBean> mPersonRankData=new ArrayList<>();
    private TeamThumbRankAdapter mTeamThumbRankAdapter;
    private PersonThumbRankAdapter mPersonThumbRankAdapter;

    private boolean isTeamData=true;//当前排行榜数据是否是战队的

    @Override
    public void initView(View view) {
        mTvFragmentBattlePopularRanking = (TextView) view.findViewById(R.id.tv_fragment_battle_popular_ranking);
        mTvBattlePopularRankingSwitch = (TextView) view.findViewById(R.id.tv_battle_popular_ranking_switch);
        mRefreshLayoutBattlePopularRanking = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout_battle_popular_ranking);
        mRecyclerFragmentBattlePopularRanking = (RecyclerView) view.findViewById(R.id.recycler_fragment_battle_popular_ranking);
        mRefreshLayoutPersonPopularRanking = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout_person_popular_ranking);
        mRecyclerPersonPopularRanking = (RecyclerView) view.findViewById(R.id.recycler_person_popular_ranking);

        mTvBattlePopularRankingSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTeamData) {
                    if (isFirstLoadPerson) {
                        getPersonPopularRank(false);
                    }
                    mTvBattlePopularRankingSwitch.setText("切换到战队");
                    mTvFragmentBattlePopularRanking.setText("个人获赞榜单");
                    mRefreshLayoutBattlePopularRanking.setVisibility(View.GONE);
                    mRefreshLayoutPersonPopularRanking.setVisibility(View.VISIBLE);
                    isTeamData=false;
                }else{
                    mTvBattlePopularRankingSwitch.setText("切换到个人");
                    mTvFragmentBattlePopularRanking.setText("战队获赞榜单");
                    mRefreshLayoutBattlePopularRanking.setVisibility(View.VISIBLE);
                    mRefreshLayoutPersonPopularRanking.setVisibility(View.GONE);
                    isTeamData=true;
                }
            }
        });
    }

    @Override
    public void initData() {

        getBattlePopularRank(false);
        mRefreshLayoutBattlePopularRanking.setEnableAutoLoadMore(false);
        mRefreshLayoutPersonPopularRanking.setEnableAutoLoadMore(false);

        mRefreshLayoutBattlePopularRanking.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getBattlePopularRank(true);
            }
        });
        mRefreshLayoutBattlePopularRanking.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mRefreshLayoutBattlePopularRanking.setEnableLoadMore(true);
                mTeamOffset=0;
                getBattlePopularRank(false);
            }
        });
        mRefreshLayoutPersonPopularRanking.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getPersonPopularRank(true);
            }
        });
        mRefreshLayoutPersonPopularRanking.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mRefreshLayoutPersonPopularRanking.setEnableLoadMore(true);
                mPersonOffset=0;
                getPersonPopularRank(false);
            }
        });

    }

    /**
     * 获取战队人气排行
     * @param isloadmore
     */
    private void getBattlePopularRank(final boolean isloadmore) {
        Map<String,String> map=new HashMap<>();
        map.put("id",SaveUtil.getString("action_id",""));
        map.put("count","10");
        map.put("offset",mTeamOffset+"");
        HttpUtils.getInstance().get(Constants.TEAM_THUMB_UP_RANK, map, new HttpCallback<TeamThumbRankBean>() {
            @Override
            public void onPreExcute() {
                super.onPreExcute();
                if (isloadmore) {
                    mRefreshLayoutBattlePopularRanking.finishLoadMore();
                }else{
                    mRefreshLayoutBattlePopularRanking.finishRefresh();
                }
            }

            @Override
            public void onSuccessExecute(TeamThumbRankBean teamThumbRankBean) {
                TeamThumbRankBean.DataBean data = teamThumbRankBean.getData();
                if (data!=null) {
                    TeamThumbRankBean.DataBean.ListBean current = data.getCurrent();
                    List<TeamThumbRankBean.DataBean.ListBean> list = data.getList();
                    if (!isloadmore) {
                        mTeamRankData.clear();
                        if (current!=null) {
                            mTeamRankData.add(current);
                        }
                    }

                    if (list!=null) {
                        mTeamOffset+=list.size();
                        mTeamRankData.addAll(list);
                    }

                    if (list.size()<10) {
                        mRefreshLayoutBattlePopularRanking.setEnableLoadMore(false);
                    }
                    if (isFirstLoadTeam) {
                        mTeamThumbRankAdapter = new TeamThumbRankAdapter(mActivity, mTeamRankData);
                        mRecyclerFragmentBattlePopularRanking.setLayoutManager(new LinearLayoutManager(mActivity));
                        mRecyclerFragmentBattlePopularRanking.setAdapter(mTeamThumbRankAdapter);
                        isFirstLoadTeam=false;
                    }else{
                        mTeamThumbRankAdapter.setdatas(mTeamRankData);
                    }
                }
            }
        });
    }

    public void getPersonPopularRank(final boolean isLoadmore){
        Map<String,String> map=new HashMap<>();
        map.put("id",SaveUtil.getString("action_id",""));
        map.put("team_id",SaveUtil.getString("team_id",""));
        map.put("count","10");
        map.put("offset",mPersonOffset+"");
        HttpUtils.getInstance().get(Constants.PERSON_THUMB_UP_RANK, map, new HttpCallback<PersonThumbRankBean>() {
            @Override
            public void onPreExcute() {
                super.onPreExcute();
                if (isLoadmore) {
                    mRefreshLayoutPersonPopularRanking.finishLoadMore();
                }else{
                    mRefreshLayoutPersonPopularRanking.finishRefresh();
                }
            }

            @Override
            public void onSuccessExecute(PersonThumbRankBean personThumbRankBean) {
                PersonThumbRankBean.DataBean data = personThumbRankBean.getData();
                PersonThumbRankBean.DataBean.ListBean current = data.getCurrent();
                List<PersonThumbRankBean.DataBean.ListBean> list = data.getList();
                if (!isLoadmore) {
                    mPersonRankData.clear();
                    if (current!=null) {
                        mPersonRankData.add(current);
                    }
                }
                if (list!=null) {
                    mPersonOffset+=list.size();
                    mPersonRankData.addAll(list);
                }
                if (list.size()<10) {
                    mRefreshLayoutPersonPopularRanking.setEnableLoadMore(false);
                }
                if (isFirstLoadPerson) {
                    mPersonThumbRankAdapter = new PersonThumbRankAdapter(mActivity, mPersonRankData);
                    mRecyclerPersonPopularRanking.setLayoutManager(new LinearLayoutManager(mActivity));
                    mRecyclerPersonPopularRanking.setAdapter(mPersonThumbRankAdapter);
                    isFirstLoadPerson=false;
                }else{
                    mPersonThumbRankAdapter.setdatas(mPersonRankData);
                }

            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_battle_popular_ranking;
    }
}
