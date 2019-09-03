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
import com.tourye.run.bean.AlreadyWithdrawalBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.AlreadyWithdrawalAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:   AlreadyWithdrawalFragment
 *
 * @Author:   along
 *
 * @Description:    已经提现的账目页面
 *
 * @CreateDate:   2019/4/23 3:10 PM
 *
 */
public class AlreadyWithdrawalFragment extends BaseFragment {
    private SmartRefreshLayout mRefreshLayoutFragmentAlreadyWithdrawal;
    private RecyclerView mRecyclerFragmentAlreadyWithdrawal;

    private int mOffset=0;//默认请求数据的索引号
    private AlreadyWithdrawalAdapter mAlreadyWithdrawalAdapter;
    private List<AlreadyWithdrawalBean.DataBean> mDataBeans=new ArrayList<>();

    @Override
    public void initView(View view) {
        mRefreshLayoutFragmentAlreadyWithdrawal = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout_fragment_already_withdrawal);
        mRecyclerFragmentAlreadyWithdrawal = (RecyclerView) view.findViewById(R.id.recycler_fragment_already_withdrawal);

        mRefreshLayoutFragmentAlreadyWithdrawal.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mOffset=0;
                getWithdrawalData(false);
            }
        });
        mRefreshLayoutFragmentAlreadyWithdrawal.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getWithdrawalData(true);
            }
        });
    }

    @Override
    public void initData() {

        mAlreadyWithdrawalAdapter = new AlreadyWithdrawalAdapter(mActivity,mDataBeans);
        mRecyclerFragmentAlreadyWithdrawal.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerFragmentAlreadyWithdrawal.setAdapter(mAlreadyWithdrawalAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        getWithdrawalData(false);

    }

    public void getWithdrawalData(final boolean isLoadmore){
        Map<String,String> map=new HashMap<>();
        map.put("offset",mOffset+"");
        map.put("count","20");
        HttpUtils.getInstance().get(Constants.WITHDRAW_DETAIL, map, new HttpCallback<AlreadyWithdrawalBean>() {
            @Override
            public void onPreExcute() {
                super.onPreExcute();
                if (isLoadmore) {
                    mRefreshLayoutFragmentAlreadyWithdrawal.finishLoadMore();
                }else{
                    mRefreshLayoutFragmentAlreadyWithdrawal.finishRefresh();
                }
            }

            @Override
            public void onSuccessExecute(AlreadyWithdrawalBean alreadyWithdrawalBean) {
                List<AlreadyWithdrawalBean.DataBean> data = alreadyWithdrawalBean.getData();
                if (data!=null && data.size()>0) {
                    if (!isLoadmore) {
                        mDataBeans.clear();
                    }
                    mDataBeans.addAll(data);
                    mAlreadyWithdrawalAdapter.setDataBeans(mDataBeans);
                    mOffset+=data.size();
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_already_withdrawal;
    }
}
