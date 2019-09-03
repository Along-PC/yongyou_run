package com.tourye.run.ui.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.base.BaseFragment;
import com.tourye.run.bean.MessageCommentBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.MessageCommentAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:   MessageCommentFragment
 *
 * @Author:   along
 *
 * @Description:    评论消息页面
 *
 * @CreateDate:   2019/4/3 11:31 AM
 *
 */
public class MessageCommentFragment extends BaseFragment {
    private SmartRefreshLayout mRefreshLayoutFragmentMessageComment;
    private RecyclerView mRecyclerFragmentMessageComment;

    private boolean isFirstLoad=true;
    private String last_id="";
    private List<MessageCommentBean.DataBean> mDataBeans=new ArrayList<>();
    private MessageCommentAdapter mMessageCommentAdapter;

    @Override
    public void initView(View view) {
        mRefreshLayoutFragmentMessageComment = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout_fragment_message_comment);
        mRecyclerFragmentMessageComment = (RecyclerView) view.findViewById(R.id.recycler_fragment_message_comment);

    }

    @Override
    public void initData() {
        getMessageData(false);
        mRefreshLayoutFragmentMessageComment.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                last_id="";
                getMessageData(false);
            }
        });
        mRefreshLayoutFragmentMessageComment.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getMessageData(true);
            }
        });
    }

    /**
     * 获取消息数据
     * @param isLoadmore
     */
    public void getMessageData(final boolean isLoadmore){
        Map<String, String> map = new HashMap<>();
        map.put("count", "10");
        if (isLoadmore) {
            map.put("last_id", last_id);
        }
        HttpUtils.getInstance().get(Constants.MESSAGE_COMMENT, map, new HttpCallback<MessageCommentBean>() {
            @Override
            public void onPreExcute() {
                super.onPreExcute();
                if (isLoadmore) {
                    mRefreshLayoutFragmentMessageComment.finishLoadMore();
                }else{
                    mRefreshLayoutFragmentMessageComment.finishRefresh();
                }
            }

            @Override
            public void onSuccessExecute(MessageCommentBean messageCommentBean) {
                List<MessageCommentBean.DataBean> data = messageCommentBean.getData();
                if (data==null || data.size()<=0) {
                    return;
                }
                if (!isLoadmore) {
                    mDataBeans.clear();
                }
                mDataBeans.addAll(data);
                if (isFirstLoad) {
                    mMessageCommentAdapter = new MessageCommentAdapter(mActivity, mDataBeans);
                    mRecyclerFragmentMessageComment.setLayoutManager(new LinearLayoutManager(mActivity));
                    mRecyclerFragmentMessageComment.setAdapter(mMessageCommentAdapter);
                    isFirstLoad=false;
                }else{
                    mMessageCommentAdapter.setDataBeans(mDataBeans);
                }
                last_id=data.get(data.size()-1).getId()+"";
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_message_comment;
    }
}
