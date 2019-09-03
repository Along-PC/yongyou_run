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
import com.tourye.run.bean.AlreadyEffectiveBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.AlreadyEffectiveAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:   AlreadyEffectiveFragment
 *
 * @Author:   along
 *
 * @Description:    已经生效的账目列表页面
 *
 * @CreateDate:   2019/4/23 3:11 PM
 *
 */
public class AlreadyEffectiveFragment extends BaseFragment {
    private RecyclerView mRecyclerFragmentAlreadyEffective;
    private SmartRefreshLayout mRefreshLayoutFragmentAlreadyEffective;

    private int mOffset=0;
    private List<AlreadyEffectiveBean.DataBean> mDataBeans=new ArrayList<>();
    private AlreadyEffectiveAdapter mAlreadyEffectiveAdapter;

    @Override
    public void initView(View view) {
        mRecyclerFragmentAlreadyEffective = (RecyclerView) view.findViewById(R.id.recycler_fragment_already_effective);
        mRefreshLayoutFragmentAlreadyEffective = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout_fragment_already_effective);

        mRefreshLayoutFragmentAlreadyEffective.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getEffectiveData(true);
            }
        });
        mRefreshLayoutFragmentAlreadyEffective.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mOffset=0;
                getEffectiveData(false);
            }
        });

    }

    @Override
    public void initData() {

        mAlreadyEffectiveAdapter = new AlreadyEffectiveAdapter(mActivity, mDataBeans);
        mRecyclerFragmentAlreadyEffective.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerFragmentAlreadyEffective.setAdapter(mAlreadyEffectiveAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        getEffectiveData(false);

    }

    public void getEffectiveData(final boolean isLoadmore){
        Map<String,String> map=new HashMap<>();
        map.put("offset",mOffset+"");
        map.put("count","20");
        HttpUtils.getInstance().get(Constants.PRIZE_DETAIL, map, new HttpCallback<AlreadyEffectiveBean>() {

            @Override
            public void onPreExcute() {
                super.onPreExcute();
                if (isLoadmore) {
                    mRefreshLayoutFragmentAlreadyEffective.finishLoadMore();
                }else{
                    mRefreshLayoutFragmentAlreadyEffective.finishRefresh();
                }
            }

            @Override
            public void onSuccessExecute(AlreadyEffectiveBean alreadyEffectiveBean) {
                List<AlreadyEffectiveBean.DataBean> data = alreadyEffectiveBean.getData();
                if (data!=null && data.size()>0) {
                    if (!isLoadmore) {
                        mDataBeans.clear();
                    }
                    mOffset+=data.size();
                    mDataBeans.addAll(data);
                    mAlreadyEffectiveAdapter.setDataBeans(mDataBeans);
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_already_effective;
    }
}
