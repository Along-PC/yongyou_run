package com.tourye.run.ui.activities.mine;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.BattleMemberBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.TeamMemberAdapter;
import com.tourye.run.utils.SaveUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TeamMemberActivity
 * @Author: along
 * @Description:战队成员列表页面-----战队成员打卡情况页面
 * @CreateDate: 2019/3/18 4:48 PM
 */
public class TeamMemberActivity extends BaseActivity {

    private RecyclerView mRecyclerActivityTeamMember;


    @Override
    public void initView() {
        mRecyclerActivityTeamMember = (RecyclerView) findViewById(R.id.recycler_activity_team_member);

        mTvTitle.setText("战队成员");
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        final String team_id = intent.getStringExtra("team_id");

        if (!TextUtils.isEmpty(team_id)) {
            final String teamId = SaveUtil.getString("team_id", "");

            Map<String, String> map = new HashMap<>();
            map.put("id", team_id);
            HttpUtils.getInstance().get(Constants.TEAM_MEMBER_INFO, map, new HttpCallback<BattleMemberBean>() {
                @Override
                public void onSuccessExecute(BattleMemberBean battleMemberBean) {
                    List<BattleMemberBean.DataBean> data = battleMemberBean.getData();
                    if (data != null && data.size() > 0) {
                        TeamMemberAdapter teamMemberAdapter = new TeamMemberAdapter(mActivity,data);
                        //如果是同队
                        if (team_id.equals(teamId)) {
                            teamMemberAdapter.setSameTeam(true);
                        }
                        mRecyclerActivityTeamMember.setLayoutManager(new LinearLayoutManager(mActivity));
                        mRecyclerActivityTeamMember.setAdapter(teamMemberAdapter);
                    }
                }
            });
        }


    }


    @Override
    public int getRootView() {
        return R.layout.activity_team_member;
    }
}
