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
import com.tourye.run.utils.SaveUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:   PunchAllFragment
 *
 * @Author:   along
 *
 * @Description:    战队内所有人的打卡情况
 *
 * @CreateDate:   2019/4/22 6:26 PM
 *
 */
public class PunchAllFragment extends BaseFragment {
    private RecyclerView mRecyclerFragmentAllPunch;

    @Override
    public void initView(View view) {
        mRecyclerFragmentAllPunch = (RecyclerView) view.findViewById(R.id.recycler_fragment_all_punch);

    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        final String date = arguments.getString("date");
        final String team_id = arguments.getString("team_id");

        Map<String,String> map=new HashMap<>();
        map.put("team_id",team_id);
        map.put("date",date);
        map.put("type","all");
        HttpUtils.getInstance().get(Constants.TEAM_SIGN_IN_DETAIL, map, new HttpCallback<TeamMemberPunchBean>() {
            @Override
            public void onSuccessExecute(TeamMemberPunchBean teamMemberPunchBean) {
                List<TeamMemberPunchBean.DataBean> data = teamMemberPunchBean.getData();
                if (data!=null && data.size()>0) {
                    AllPunchAdapter allPunchAdapter = new AllPunchAdapter(mActivity, data);
                    allPunchAdapter.setData(team_id,date);
                    mRecyclerFragmentAllPunch.setLayoutManager(new LinearLayoutManager(mActivity));
                    mRecyclerFragmentAllPunch.setAdapter(allPunchAdapter);
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_all_punch;
    }

}
