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
import com.tourye.run.bean.CheckNewsBean;
import com.tourye.run.bean.MessageSystemBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.MessageSystemAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: MessageSystemFragment
 * @Author: along
 * @Description: 系统消息页面
 * @CreateDate: 2019/4/3 11:30 AM
 */
public class MessageSystemFragment extends BaseFragment {
    private SmartRefreshLayout mRefreshLayoutFragmentMessageSystem;
    private RecyclerView mRecyclerFragmentMessageSystem;

    private boolean isFirstLoad = true;//是否首次加载
    private List<MessageSystemBean.DataBean> mDataBeans = new ArrayList<>();
    private MessageSystemAdapter mMessageSystemAdapter;
    private String last_id = "";//最后一条数据的id

    @Override
    public void initView(View view) {
        mRefreshLayoutFragmentMessageSystem = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout_fragment_message_system);
        mRecyclerFragmentMessageSystem = (RecyclerView) view.findViewById(R.id.recycler_fragment_message_system);

    }

    @Override
    public void initData() {
        getMessage(false);

        mRefreshLayoutFragmentMessageSystem.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                last_id="";
                getMessage(false);
            }
        });

        mRefreshLayoutFragmentMessageSystem.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getMessage(true);
            }
        });

    }

    /**
     * 获取消息数据
     *
     * @param isLoadmore
     */
    public void getMessage(final boolean isLoadmore) {
        Map<String, String> map = new HashMap<>();
        map.put("count", "10");
        if (isLoadmore) {
            map.put("last_id", last_id);
        }
        HttpUtils.getInstance().get(Constants.MESSAGE_SYSTEM, map, new HttpCallback<MessageSystemBean>() {

            @Override
            public void onPreExcute() {
                super.onPreExcute();
                if (isLoadmore) {
                    mRefreshLayoutFragmentMessageSystem.finishLoadMore();
                } else {
                    mRefreshLayoutFragmentMessageSystem.finishRefresh();
                }
            }

            @Override
            public void onSuccessExecute(MessageSystemBean messageSystemBean) {
                List<MessageSystemBean.DataBean> data = messageSystemBean.getData();
                if (data == null || data.size() <= 0) {
                    return;
                }
                if (!isLoadmore) {
                    mDataBeans.clear();
                }
                mDataBeans.addAll(data);
                if (isFirstLoad) {
                    mMessageSystemAdapter = new MessageSystemAdapter(mActivity, mDataBeans);
                    mRecyclerFragmentMessageSystem.setLayoutManager(new LinearLayoutManager(mActivity));
                    mRecyclerFragmentMessageSystem.setAdapter(mMessageSystemAdapter);
                    isFirstLoad = false;
                } else {
                    mMessageSystemAdapter.setDataBeans(mDataBeans);
                }
                last_id = data.get(data.size() - 1).getId() + "";

            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_message_system;
    }
}
