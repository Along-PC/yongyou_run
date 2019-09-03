package com.tourye.run.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.base.BaseFragment;
import com.tourye.run.bean.TeamMemberPunchBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.AllPunchAdapter;
import com.tourye.run.ui.adapter.AlreadyPunchAdapter;
import com.tourye.run.utils.SaveUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:   PunchAllFragment
 *
 * @Author:   along
 *
 * @Description:    战队内所有打完卡的情况
 *
 * @CreateDate:   2019/4/22 6:26 PM
 *
 */
public class PunchAlreadyFragment extends BaseFragment {
    private RecyclerView mRecyclerFragmentAlreadyPunch;
    private List<TeamMemberPunchBean.DataBean> mPunchList;
    private AlreadyPunchAdapter mAlreadyPunchAdapter;

    @Override
    public void initView(View view) {
        mRecyclerFragmentAlreadyPunch = (RecyclerView) view.findViewById(R.id.recycler_fragment_already_punch);

    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        String date = arguments.getString("date");
        String team_id = arguments.getString("team_id");

        Map<String,String> map=new HashMap<>();
        map.put("team_id",team_id);
        map.put("date",date);
        map.put("type","yes");
        HttpUtils.getInstance().get(Constants.TEAM_SIGN_IN_DETAIL, map, new HttpCallback<TeamMemberPunchBean>() {
            @Override
            public void onSuccessExecute(TeamMemberPunchBean teamMemberPunchBean) {
                mPunchList = teamMemberPunchBean.getData();
                if (mPunchList !=null && mPunchList.size()>0) {
                    mAlreadyPunchAdapter = new AlreadyPunchAdapter(mActivity, mPunchList);
                    mRecyclerFragmentAlreadyPunch.setLayoutManager(new LinearLayoutManager(mActivity));
                    mRecyclerFragmentAlreadyPunch.setAdapter(mAlreadyPunchAdapter);
                }
            }
        });
    }

    /**
     * 更改列表排序
     */
    public void changeOrder(){
        if (mPunchList !=null && mPunchList.size()>0) {
            Collections.reverse(mPunchList);
            mAlreadyPunchAdapter.setDataBeans(mPunchList);
        }
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_already_punch;
    }
}
