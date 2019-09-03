package com.tourye.run.ui.activities.home;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.LeaderApplyStatusBean;
import com.tourye.run.bean.SearchBattleBean;
import com.tourye.run.bean.TeamBasicInfoBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.SearchBattleAdapter;
import com.tourye.run.utils.SaveUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SearchBattleActivity
 * @Author: along
 * @Description: 搜索战队页面
 * @CreateDate: 2019/4/3 5:03 PM
 */
public class SearchBattleActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEditActivitySearchBattle;
    private TextView mTvActivitySearchBattleFind;
    private TextView mTvActivitySearchBattleResult;
    private TextView mTvActivitySearchBattleCreate;
    private SmartRefreshLayout mRefreshLayoutActivitySearchBattle;
    private RecyclerView mRecyclerActivitySearchBattle;
    private ConstraintLayout mConstraintActivitySearchBattleNoResult;
    private ImageView mImgActivitySearchBattleClear;

    private String keyword;//搜索关键字
    private boolean isFirstLoad=true;//是否首次加载
    private List<TeamBasicInfoBean> mDataBeans=new ArrayList<>();
    private SearchBattleAdapter mSearchBattleAdapter;
    private int mOffset=0;

    @Override
    public void initView() {

        mEditActivitySearchBattle = (EditText) findViewById(R.id.edit_activity_search_battle);
        mTvActivitySearchBattleFind = (TextView) findViewById(R.id.tv_activity_search_battle_find);
        mTvActivitySearchBattleResult = (TextView) findViewById(R.id.tv_activity_search_battle_result);
        mTvActivitySearchBattleCreate = (TextView) findViewById(R.id.tv_activity_search_battle_create);
        mRefreshLayoutActivitySearchBattle = (SmartRefreshLayout) findViewById(R.id.refreshLayout_activity_search_battle);
        mRecyclerActivitySearchBattle = (RecyclerView) findViewById(R.id.recycler_activity_search_battle);
        mConstraintActivitySearchBattleNoResult = (ConstraintLayout) findViewById(R.id.constraint_activity_search_battle_noResult);
        mImgActivitySearchBattleClear = (ImageView) findViewById(R.id.img_activity_search_battle_clear);

        mTvTitle.setText("搜索战队");
        mTvActivitySearchBattleFind.setOnClickListener(this);
        mTvActivitySearchBattleCreate.setOnClickListener(this);
        mImgActivitySearchBattleClear.setOnClickListener(this);

        mEditActivitySearchBattle.setFocusable(true);
        mEditActivitySearchBattle.setFocusableInTouchMode(true);
        mEditActivitySearchBattle.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    @Override
    public void initData() {
        mRefreshLayoutActivitySearchBattle.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mOffset=0;
                getBattleList(false);
            }
        });
        mRefreshLayoutActivitySearchBattle.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getBattleList(true);
            }
        });

        mEditActivitySearchBattle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = s.toString();
                if (TextUtils.isEmpty(temp)) {
                    mImgActivitySearchBattleClear.setVisibility(View.INVISIBLE);
                }else{
                    mImgActivitySearchBattleClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 获取搜索战队列表
     *
     * @param isLoadmore
     */
    public void getBattleList(final boolean isLoadmore) {
        final Map<String, String> map = new HashMap<>();
        map.put("id", SaveUtil.getString(SaveConstants.ACTION_ID, ""));
        map.put("count", "10");
        map.put("offset", mOffset+"");
        map.put("name", keyword);
        HttpUtils.getInstance().get(Constants.SEARCH_BATTLE, map, new HttpCallback<SearchBattleBean>() {
            @Override
            public void onPreExcute() {
                super.onPreExcute();
                if (isLoadmore) {
                    mRefreshLayoutActivitySearchBattle.finishLoadMore();
                } else {
                    mRefreshLayoutActivitySearchBattle.finishRefresh();
                }
            }

            @Override
            public void onSuccessExecute(SearchBattleBean searchBattleBean) {
                List<TeamBasicInfoBean> data = searchBattleBean.getData();
                if (data == null || data.size() <= 0) {
                    mRefreshLayoutActivitySearchBattle.setVisibility(View.GONE);
                    mConstraintActivitySearchBattleNoResult.setVisibility(View.VISIBLE);
                    return;
                }
                mRefreshLayoutActivitySearchBattle.setVisibility(View.VISIBLE);
                mConstraintActivitySearchBattleNoResult.setVisibility(View.GONE);
                if (!isLoadmore) {
                    mDataBeans.clear();
                }
                mDataBeans.addAll(data);
                if (isFirstLoad) {
                    mSearchBattleAdapter = new SearchBattleAdapter(mActivity, mDataBeans);
                    mRecyclerActivitySearchBattle.setLayoutManager(new LinearLayoutManager(mActivity));
                    mRecyclerActivitySearchBattle.setAdapter(mSearchBattleAdapter);
                    isFirstLoad=false;
                }else{
                    mSearchBattleAdapter.setDataBeans(mDataBeans);
                }
                mOffset+=data.size();
            }
        });
    }

    /**
     * 检查队长申请状态
     */
    private void checkLeaderApplyStatus() {
        Map<String,String> map=new HashMap<>();
        map.put("activity_id",SaveUtil.getString(SaveConstants.ACTION_ID,""));
        HttpUtils.getInstance().get(Constants.LEADER_APPLY_STATUS, map, new HttpCallback<LeaderApplyStatusBean>() {
            @Override
            public void onSuccessExecute(LeaderApplyStatusBean leaderApplyStatusBean) {
                LeaderApplyStatusBean.DataBean data = leaderApplyStatusBean.getData();
                if (data!=null) {
                    //队长申请成功
                    if (data.isApplied()) {
                        if ("accepted".equalsIgnoreCase(data.getStatus())) {
                            startActivity(new Intent(mActivity, CreateBattleActivity.class));
                        }else{
                            startActivity(new Intent(mActivity, LeaderApplyActivity.class));
                        }
                    }else{
                        startActivity(new Intent(mActivity,LeaderApplyActivity.class));
                    }
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.activity_search_battle;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_activity_search_battle_find:
                keyword= mEditActivitySearchBattle.getText().toString().trim();
                if (keyword==null) {
                    Toast.makeText(mActivity, "请输入关键字", Toast.LENGTH_SHORT).show();
                    return;
                }
                mConstraintActivitySearchBattleNoResult.setVisibility(View.GONE);
                mRefreshLayoutActivitySearchBattle.setVisibility(View.VISIBLE);
                getBattleList(false);
                break;
            case R.id.tv_activity_search_battle_create:
                checkLeaderApplyStatus();
                break;
            case R.id.img_activity_search_battle_clear:
                mEditActivitySearchBattle.setText("");
                break;
        }
    }

}
