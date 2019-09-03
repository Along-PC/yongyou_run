package com.tourye.run.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.base.BaseFragment;
import com.tourye.run.bean.DistanceRankingBean;
import com.tourye.run.bean.GameRankingBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.GameRankingAdapter;
import com.tourye.run.utils.GlideRoundTransform;
import com.tourye.run.utils.SaveUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: PersonCompleteRankingFragment
 * @Author: along
 * @Description: 个人完赛排行榜
 * @CreateDate: 2019/3/19 3:44 PM
 */
public class PersonCompleteRankingFragment extends BaseRankFragment {

    private RecyclerView mRecyclerFragmentGameRanking;

    private String mTeam_id;//队伍id
    private int mOffset = 0;
    private List<GameRankingBean.DataBean.ListBean> mListBeans = new ArrayList<>();
    private GameRankingAdapter mGameRankingAdapter;

    @Override
    public void initView(View view) {
        mRecyclerFragmentGameRanking = (RecyclerView) view.findViewById(R.id.recycler_fragment_game_ranking);

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
    public void getRankData() {
        Map<String, String> map = new HashMap<>();
        map.put("id", SaveUtil.getString("action_id", ""));
        map.put("team_id", mTeam_id);
        map.put("count", "100");
        map.put("offset", mOffset + "");
        HttpUtils.getInstance().get(Constants.GAME_COMPLETE_RANKING, map, new HttpCallback<GameRankingBean>() {
            @Override
            public void onSuccessExecute(GameRankingBean gameRankingBean) {
                GameRankingBean.DataBean data = gameRankingBean.getData();
                if (data == null) {
                    return;
                }
                GameRankingBean.DataBean.ListBean current = data.getCurrent();
                List<GameRankingBean.DataBean.ListBean> list = data.getList();
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        GameRankingBean.DataBean.ListBean listBean = list.get(i);
                        listBean.setRank(i + 1);
                    }
                }else{
                    list=new ArrayList<>();
                }
                if (mTeam_id.equals(SaveUtil.getString(SaveConstants.TEAM_ID, ""))) {
                    if (current!=null) {
                        list.add(0, current);
                    }
                }
                mListBeans.clear();
                mListBeans.addAll(list);
                mRecyclerFragmentGameRanking.setLayoutManager(new LinearLayoutManager(mActivity));
                mGameRankingAdapter = new GameRankingAdapter(mActivity, mListBeans);
                mGameRankingAdapter.setSameTeam(mTeam_id.equals(SaveUtil.getString(SaveConstants.TEAM_ID, "")));
                mGameRankingAdapter.setCompleteRank(true);
                mRecyclerFragmentGameRanking.setAdapter(mGameRankingAdapter);
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_game_ranking;
    }

    @Override
    public void changeOrder() {
        if (mListBeans == null || mListBeans.size() <= 0) {
            return;
        }
        if (mTeam_id.equals(SaveUtil.getString(SaveConstants.TEAM_ID, ""))) {
            List<GameRankingBean.DataBean.ListBean> listBeans = new ArrayList<>();
            listBeans.add(mListBeans.get(0));
            mListBeans.remove(0);
            Collections.reverse(mListBeans);
            listBeans.addAll(mListBeans);
            mListBeans=listBeans;
            mGameRankingAdapter.setListBeans(mListBeans);
        } else {
            Collections.reverse(mListBeans);
            mGameRankingAdapter.setListBeans(mListBeans);
        }
    }

    @Override
    public void updateThumbStatus(int user_id, boolean isThumb, int thumbCount) {
        if (mListBeans == null || mListBeans.size() <= 0) {
            return;
        }
        for (int i = 0; i < mListBeans.size(); i++) {
            GameRankingBean.DataBean.ListBean listBean = mListBeans.get(i);
            if (listBean.getId() == user_id) {
                listBean.setHas_thumb_up(isThumb);
                listBean.setThumb_up_count(thumbCount);
            }
        }
        mGameRankingAdapter.setListBeans(mListBeans);
    }
}
