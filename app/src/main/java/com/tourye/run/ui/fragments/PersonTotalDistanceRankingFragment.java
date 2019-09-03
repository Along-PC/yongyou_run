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
import com.tourye.run.bean.DistanceRankingBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.DistanceRankingAdapter;
import com.tourye.run.utils.SaveUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:   PersonTotalDistanceRankingFragment
 *
 * @Author:   along
 *
 * @Description:  战队距离排行榜页面
 *
 * @CreateDate:   2019/3/20 2:14 PM
 *
 */

public class PersonTotalDistanceRankingFragment extends BaseRankFragment {

    private RecyclerView mRecyclerFragmentDistanceRanking;
    private String mTeam_id;

    private DistanceRankingAdapter mDistanceRankingAdapter;
    private List<DistanceRankingBean.DataBean.ListBean> mListBeans=new ArrayList<>();
    private boolean isFirstLoad=true;

    @Override
    public void initView(View view) {
        mRecyclerFragmentDistanceRanking = (RecyclerView) view.findViewById(R.id.recycler_fragment_distance_ranking);

    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        mTeam_id = arguments.getString("team_id");

        getRankData();
    }

    /**
     * 获取排行榜数据
     */
    public void getRankData(){
        Map<String,String> map=new HashMap<>();
        map.put("id",SaveUtil.getString("action_id",""));
        map.put("team_id",mTeam_id);
        map.put("count","100");
        map.put("offset","0");
        HttpUtils.getInstance().get(Constants.DISTANCE_RANKING, map, new HttpCallback<DistanceRankingBean>() {

            @Override
            public void onSuccessExecute(DistanceRankingBean distanceRankingBean) {
                DistanceRankingBean.DataBean data = distanceRankingBean.getData();
                if (data==null) {
                    return;
                }
                List<DistanceRankingBean.DataBean.ListBean> list = data.getList();
                if (list!=null) {
                    for (int i = 0; i < list.size(); i++) {
                        DistanceRankingBean.DataBean.ListBean listBean = list.get(i);
                        listBean.setRank(i+1);
                    }
                }else{
                    list=new ArrayList<>();
                }
                mListBeans.clear();
                DistanceRankingBean.DataBean.ListBean current = data.getCurrent();
                if (mTeam_id.equals(SaveUtil.getString(SaveConstants.TEAM_ID,""))) {
                    if (current!=null) {
                        mListBeans.add(0,current);
                    }
                }
                mListBeans.addAll(list);
                mDistanceRankingAdapter = new DistanceRankingAdapter(mActivity, mListBeans);
                mDistanceRankingAdapter.setCompleteRank(true);
                mDistanceRankingAdapter.setSameTeam(mTeam_id.equals(SaveUtil.getString(SaveConstants.TEAM_ID,""))?true:false);
                mRecyclerFragmentDistanceRanking.setLayoutManager(new LinearLayoutManager(mActivity));
                mRecyclerFragmentDistanceRanking.setAdapter(mDistanceRankingAdapter);

            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_distance_ranking;
    }

    @Override
    public void changeOrder() {
        if (mListBeans==null || mListBeans.size()<=0) {
            return;
        }
        if (mTeam_id.equals(SaveUtil.getString(SaveConstants.TEAM_ID,""))) {
            List<DistanceRankingBean.DataBean.ListBean> listBeans=new ArrayList<>();
            listBeans.add(mListBeans.get(0));
            mListBeans.remove(0);
            Collections.reverse(mListBeans);
            listBeans.addAll(mListBeans);
            mListBeans=listBeans;
            mDistanceRankingAdapter.setListBeans(mListBeans);
        }else{
            Collections.reverse(mListBeans);
            mDistanceRankingAdapter.setListBeans(mListBeans);
        }
    }

    @Override
    public void updateThumbStatus(int user_id, boolean isThumb, int thumbCount) {
        if (mListBeans==null || mListBeans.size()<=0) {
            return;
        }
        for (int i = 0; i < mListBeans.size(); i++) {
            DistanceRankingBean.DataBean.ListBean listBean = mListBeans.get(i);
            if (listBean.getId()==user_id) {
                listBean.setThumb_up_count(thumbCount);
                listBean.setHas_thumb_up(isThumb);
            }
        }
        mDistanceRankingAdapter.setListBeans(mListBeans);
    }
}
