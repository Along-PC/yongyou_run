package com.tourye.run.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseFragment;
import com.tourye.run.bean.ActionInfoBean;
import com.tourye.run.bean.BattleGroupBean;
import com.tourye.run.bean.BattleRankingCompleteBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.BattleRankingCompleteAdapter;
import com.tourye.run.ui.dialogs.GroupLevelSpinner;
import com.tourye.run.utils.SaveUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:   BattleCompleteRankingFragment
 *
 * @Author:   along
 *
 * @Description:    战队排行榜内容页面
 *
 * @CreateDate:   2019/4/1 9:50 AM
 *
 */
public class BattleCompleteRankingFragment extends BaseFragment {
    private TextView mTvFragmentBattleRankingGroup;
    private TextView mTvFragmentBattleRankingChoose;
    private RecyclerView mRecyclerFragmentBattleRanking;
    private SmartRefreshLayout mRefreshLayoutBattleRankingComplete;
    private LinearLayout mLlFragmentBattleRankingGroup;

    private int mOffset=0;
    private BattleRankingCompleteAdapter mBattleRankingCompleteAdapter;

    //列表数据
    private List<BattleRankingCompleteBean.DataBean.ListBean> mRankingData=new ArrayList<>();
    private boolean isFirstLoad=true;//是否首次加载
    private String mLevel_id;

    private List<BattleGroupBean.DataBean> mGroupData;//战队组别数据

    @Override
    public void initView(View view) {
        mTvFragmentBattleRankingGroup = (TextView) view.findViewById(R.id.tv_fragment_battle_ranking_group);
        mTvFragmentBattleRankingChoose = (TextView) view.findViewById(R.id.tv_fragment_battle_ranking_choose);
        mRecyclerFragmentBattleRanking = (RecyclerView) view.findViewById(R.id.recycler_fragment_battle_ranking);
        mRefreshLayoutBattleRankingComplete = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout_battle_ranking_complete);
        mLlFragmentBattleRankingGroup = (LinearLayout) view.findViewById(R.id.ll_fragment_battle_ranking_group);

        mLlFragmentBattleRankingGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGroupData==null) {
                    return;
                }
                GroupLevelSpinner groupLevelSpinner = new GroupLevelSpinner(mActivity);
                groupLevelSpinner.setData(mGroupData);
                groupLevelSpinner.showAsDropDown(mTvFragmentBattleRankingGroup);
                groupLevelSpinner.setOnItemClickListener(new GroupLevelSpinner.OnItemClickListener() {
                    @Override
                    public void onItemClick(int index, String levelName) {
                        mRefreshLayoutBattleRankingComplete.setEnableLoadMore(true);
                        mOffset=0;
                        mLevel_id=index+"";
                        getRankList(false);
                        mTvFragmentBattleRankingGroup.setText(levelName+"组别榜单");
                    }
                });
            }
        });

    }

    @Override
    public void initData() {

        getGroupData();

        mRefreshLayoutBattleRankingComplete.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mRefreshLayoutBattleRankingComplete.setEnableLoadMore(true);
                mOffset=0;
                getRankList(false);
            }
        });
        mRefreshLayoutBattleRankingComplete.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getRankList(true);
            }
        });
    }

    /**
     * 获取战队组别列表数据
     */
    public void getGroupData(){
        Map<String,String> map=new HashMap<>();
        map.put("id",SaveUtil.getString("action_id",""));
        map.put("city_id","");
        HttpUtils.getInstance().get(Constants.BATTLE_GROUP_LIST, map, new HttpCallback<BattleGroupBean>() {
            @Override
            public void onSuccessExecute(BattleGroupBean battleGroupBean) {
                mGroupData = battleGroupBean.getData();
                if (SaveUtil.getBoolean(SaveConstants.IS_JOINED,false)) {
                    mLevel_id = SaveUtil.getString(SaveConstants.LEVEL_ID, "");
                }else{
                    BattleGroupBean.DataBean dataBean = mGroupData.get(0);
                    mLevel_id=dataBean.getId()+"";
                }
                for (int i = 0; i < mGroupData.size(); i++) {
                    BattleGroupBean.DataBean dataBean = mGroupData.get(i);
                    if ((dataBean.getId()+"").equalsIgnoreCase(mLevel_id)) {
                        mTvFragmentBattleRankingGroup.setText(dataBean.getName()+"组别榜单");
                    }
                }

                getRankList(false);
            }
        });
    }

    /**
     * 获取列表数据
     * @param isLoadMore 是否加载更多
     */
    public void getRankList(final boolean isLoadMore){
        Map<String,String> map=new HashMap<>();
        map.put("id",SaveUtil.getString("action_id",""));
        map.put("level_id",mLevel_id);
        map.put("count","10");
        map.put("offset",mOffset+"");
        HttpUtils.getInstance().get(Constants.TEAM_FINISH_RATE_RANK, map, new HttpCallback<BattleRankingCompleteBean>() {
            @Override
            public void onPreExcute() {
                super.onPreExcute();
                if (isLoadMore) {
                    mRefreshLayoutBattleRankingComplete.finishLoadMore();
                }else{
                    mRefreshLayoutBattleRankingComplete.finishRefresh();
                }
            }

            @Override
            public void onSuccessExecute(BattleRankingCompleteBean battleRankingCompleteBean) {
                BattleRankingCompleteBean.DataBean data = battleRankingCompleteBean.getData();
                if (data!=null) {
                    BattleRankingCompleteBean.DataBean.ListBean current = data.getCurrent();
                    List<BattleRankingCompleteBean.DataBean.ListBean> list = data.getList();
                    if (!isLoadMore) {
                        mRankingData.clear();
                        if (current!=null && mLevel_id.equalsIgnoreCase(SaveUtil.getString("level_id",""))) {
                            mRankingData.add(current);
                        }
                    }
                    if (list!=null ) {
                        mOffset+=list.size();
                        mRankingData.addAll(list);
                    }
                    if (list.size()<10) {
                        mRefreshLayoutBattleRankingComplete.setEnableLoadMore(false);
                    }
                    if (isFirstLoad) {
                        mBattleRankingCompleteAdapter = new BattleRankingCompleteAdapter(mActivity,mRankingData);
                        mRecyclerFragmentBattleRanking.setLayoutManager(new LinearLayoutManager(mActivity));
                        if (SaveUtil.getBoolean(SaveConstants.IS_JOINED,false) && mLevel_id.equalsIgnoreCase(SaveUtil.getString(SaveConstants.LEVEL_ID,""))){
                            mBattleRankingCompleteAdapter.setShowCurrent(true);
                        }else{
                            mBattleRankingCompleteAdapter.setShowCurrent(false);
                        }
                        mRecyclerFragmentBattleRanking.setAdapter(mBattleRankingCompleteAdapter);
                        isFirstLoad=false;
                    }else{
                        if (SaveUtil.getBoolean(SaveConstants.IS_JOINED,false) && mLevel_id.equalsIgnoreCase(SaveUtil.getString(SaveConstants.LEVEL_ID,""))){
                            mBattleRankingCompleteAdapter.setShowCurrent(true);
                        }else{
                            mBattleRankingCompleteAdapter.setShowCurrent(false);
                        }
                        mBattleRankingCompleteAdapter.setListBeans(mRankingData);
                    }
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_battle_ranking_complete;
    }
}
