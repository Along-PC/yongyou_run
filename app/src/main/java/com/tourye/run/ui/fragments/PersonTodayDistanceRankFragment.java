package com.tourye.run.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.bean.TodayDistanceRankBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.TodayDistanceRankAdapter;
import com.tourye.run.utils.SaveUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:   PersonTodayDistanceRankFragment
 *
 * @Author:   along
 *
 * @Description:    个人今日距离排行榜页面
 *
 * @CreateDate:   2019/4/16 5:43 PM
 *
 */
public class PersonTodayDistanceRankFragment extends BaseRankFragment {

    private RecyclerView mRecyclerFragmentPersonTodayDistanceRank;
    private List<TodayDistanceRankBean.DataBean.CurrentBean> mRankList;
    private String mTeam_id;
    private TodayDistanceRankAdapter mTodayDistanceRankAdapter;

    @Override
    public void initView(View view) {
        mRecyclerFragmentPersonTodayDistanceRank = (RecyclerView) view.findViewById(R.id.recycler_fragment_person_today_distance_rank);

    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        mTeam_id = arguments.getString("team_id");
        getTodayDistanceRank(mTeam_id);
    }

    /**
     * 获取今日距离排行榜
     */
    public void getTodayDistanceRank(final String team_id){
        Map<String,String> map=new HashMap<>();
        map.put("id",SaveUtil.getString(SaveConstants.ACTION_ID,""));
        map.put("team_id",team_id);
        map.put("count","100");
        map.put("offset","0");
        HttpUtils.getInstance().get(Constants.DISTANCE_TODAY_RANKING, map, new HttpCallback<TodayDistanceRankBean>() {
            @Override
            public void onSuccessExecute(TodayDistanceRankBean todayDistanceRankingBean) {
                TodayDistanceRankBean.DataBean data = todayDistanceRankingBean.getData();
                if (data==null) {
                    return;
                }
                TodayDistanceRankBean.DataBean.CurrentBean current = data.getCurrent();
                List<TodayDistanceRankBean.DataBean.CurrentBean> list = data.getList();
                if (list==null) {
                    list=new ArrayList<>();
                }
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setRank(i+1);
                }
                if (team_id.equals(SaveUtil.getString(SaveConstants.TEAM_ID,""))) {
                    current.setMyself(true);
                    list.add(0,current);
                }
                mRankList=list;
                mTodayDistanceRankAdapter = new TodayDistanceRankAdapter(mActivity, list);
                mTodayDistanceRankAdapter.setCompleteRank(true);
                mRecyclerFragmentPersonTodayDistanceRank.setLayoutManager(new LinearLayoutManager(mActivity));
                mRecyclerFragmentPersonTodayDistanceRank.setAdapter(mTodayDistanceRankAdapter);

            }
        });

    }

    @Override
    public int getRootView() {
        return R.layout.fragment_person_today_distance_rank;
    }

    @Override
    public void changeOrder() {
        if (mRankList==null || mRankList.size()<=0) {
            return;
        }
        if (mTeam_id.equals(SaveUtil.getString(SaveConstants.TEAM_ID,""))) {
            List<TodayDistanceRankBean.DataBean.CurrentBean> currentBeans=new ArrayList<>();
            currentBeans.add(mRankList.get(0));
            mRankList.remove(0);
            Collections.reverse(mRankList);
            currentBeans.addAll(mRankList);
            mRankList=currentBeans;
            mTodayDistanceRankAdapter.setRankBeans(mRankList);
        }else{
            Collections.reverse(mRankList);
            mTodayDistanceRankAdapter.setRankBeans(mRankList);
        }
    }

    /**
     * 更新点赞状态
     */
    @Override
    public void updateThumbStatus(int user_id,boolean isThumb,int thumbCount) {
        if (mRankList==null || mRankList.size()<=0) {
            return;
        }
        for (int i = 0; i < mRankList.size(); i++) {
            TodayDistanceRankBean.DataBean.CurrentBean currentBean = mRankList.get(i);
            if (currentBean.getId()==user_id) {
                currentBean.setHas_thumb_up(isThumb);
                currentBean.setThumb_up_count(thumbCount);
            }
        }
        mTodayDistanceRankAdapter.setRankBeans(mRankList);
        mTodayDistanceRankAdapter.setRankBeans(mRankList);
    }

}
