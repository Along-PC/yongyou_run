package com.tourye.run.ui.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.base.BaseFragment;
import com.tourye.run.bean.DynamicListBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.MainActivity;
import com.tourye.run.ui.activities.community.CommunityDetailActivity;
import com.tourye.run.ui.adapter.FindCommunityAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by longlongren on 2018/9/25.
 * <p>
 * introduce:社区---所有动态
 */
public class AllDynamicFragment extends BaseFragment {
    private ListView mListFragmentFindCommunity;
    private FindCommunityAdapter mFindCommunityAdapter;
    private SmartRefreshLayout mRefreshLayoutFragmentFindCommunity;

    private int last_id = 0;//上一次请求的最后一条数据的id
    private int count = 10;//一次请求多少数据
    private List<DynamicListBean.DataBean> mDataBeans = new ArrayList<>();//列表数据

    @Override
    public void initView(View view) {
        mListFragmentFindCommunity = (ListView) view.findViewById(R.id.list_fragment_find_community);
        mRefreshLayoutFragmentFindCommunity = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout_fragment_find_community);

        //注册event-bus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void initData() {

        mFindCommunityAdapter = new FindCommunityAdapter(mActivity, mDataBeans);
        mListFragmentFindCommunity.setAdapter(mFindCommunityAdapter);

        getDynamicList(true, true);

        mRefreshLayoutFragmentFindCommunity.setEnableAutoLoadMore(false);
        mRefreshLayoutFragmentFindCommunity.setEnableFooterFollowWhenLoadFinished(true);
        mRefreshLayoutFragmentFindCommunity.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mRefreshLayoutFragmentFindCommunity.setNoMoreData(false);
                last_id = 0;
                getDynamicList(true, false);
            }
        });
        mRefreshLayoutFragmentFindCommunity.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getDynamicList(false, false);
            }
        });

        mListFragmentFindCommunity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DynamicListBean.DataBean dataBean = mDataBeans.get(i);
                Intent intent = new Intent(mActivity, CommunityDetailActivity.class);
                intent.putExtra("dynamic_id", dataBean.getId());
                mActivity.startActivity(intent);
            }
        });
    }

    //动态更新动态列表
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateDynamicList(UpdateDynamicBean updateDynamicBean) {
        if (updateDynamicBean.isUpdate_list()) {
            last_id = 0;
            getDynamicList(true, false);
        } else {
            int dynamicId = updateDynamicBean.getDynamicId();
            Iterator<DynamicListBean.DataBean> iterator = mDataBeans.iterator();
            while (iterator.hasNext()) {
                DynamicListBean.DataBean next = iterator.next();
                if (next.getId()==dynamicId) {
                    iterator.remove();
                }
            }
            mFindCommunityAdapter.setDatas(mDataBeans);
        }
    }

    //更新点赞状态
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Msg msg) {
        int dynamic_id = msg.getDynamic_id();
        for (int i = 0; i < mDataBeans.size(); i++) {
            DynamicListBean.DataBean dataBean = mDataBeans.get(i);
            if (dataBean.getId() == dynamic_id) {
                dataBean.setAlready_like(msg.isIs_already_thumb());
                int count = msg.is_already_thumb == true ? 1 : -1;
                dataBean.setLike_count(dataBean.getLike_count() + count);
                mFindCommunityAdapter.notifyDataSetChanged();
            }
        }

    }

    //更新评论数量
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateCommentCount(CommentCountBean commentCountBean) {
        int dynamic_id = commentCountBean.getDynamic_id();
        for (int i = 0; i < mDataBeans.size(); i++) {
            DynamicListBean.DataBean dataBean = mDataBeans.get(i);
            if (dataBean.getId() == dynamic_id) {
                dataBean.setComment_count(dataBean.getComment_count() + commentCountBean.getUpdate_comment_count());
                mFindCommunityAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean isNeedLazyLoad() {
        return false;
    }

    @Override
    public void onDestroy() {
        //取消event-bus监听
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 获取动态列表的数据
     *
     * @param isRefresh
     * @param isFirst
     */
    public void getDynamicList(final boolean isRefresh, final boolean isFirst) {
        Map<String, String> map = new HashMap<>();
        if (!isRefresh) {
            map.put("last_id", last_id + "");
        }
        map.put("count", count + "");
        map.put("mine", "all");
        HttpUtils.getInstance().get(Constants.MOMENT_LIST, map, new HttpCallback<DynamicListBean>() {
            @Override
            public void onFailureExecute() {
                super.onFailureExecute();
                if (isFirst) {
                    return;
                }
                if (isRefresh) {
                    mRefreshLayoutFragmentFindCommunity.finishRefresh();
                } else {
                    mRefreshLayoutFragmentFindCommunity.finishLoadMore();
                }
            }

            @Override
            public void onSuccessExecute(DynamicListBean dynamicListBean) {
                if (dynamicListBean.getStatus() == 0) {
                    List<DynamicListBean.DataBean> data = dynamicListBean.getData();
                    if (data != null) {
                        if (isRefresh) {
                            mDataBeans = data;
                            mFindCommunityAdapter.setDatas(mDataBeans);
                        } else {
                            mDataBeans.addAll(data);
                            mFindCommunityAdapter.setDatas(mDataBeans);
                        }
                        if (data.size() > 0) {
                            DynamicListBean.DataBean dataBean = data.get(data.size() - 1);
                            last_id = dataBean.getId();
                        }
                    }
                    if (isRefresh) {
                        mRefreshLayoutFragmentFindCommunity.finishRefresh();
                    } else {
                        if (data==null || data.size()<10) {
                            mRefreshLayoutFragmentFindCommunity.finishLoadMoreWithNoMoreData();
                        }else{
                            mRefreshLayoutFragmentFindCommunity.finishLoadMore();
                        }
                    }
                }
            }
        });

    }

    @Override
    public int getRootView() {
        return R.layout.fragment_all_dynamic;
    }

    /**
     * 更新点赞状态实体
     */
    public static class Msg {
        private boolean is_already_thumb;
        private int update_comment_count;
        private int dynamic_id;

        public int getDynamic_id() {
            return dynamic_id;
        }

        public void setDynamic_id(int dynamic_id) {
            this.dynamic_id = dynamic_id;
        }

        public boolean isIs_already_thumb() {
            return is_already_thumb;
        }

        public void setIs_already_thumb(boolean is_already_thumb) {
            this.is_already_thumb = is_already_thumb;
        }

        public int getUpdate_comment_count() {
            return update_comment_count;
        }

        public void setUpdate_comment_count(int update_comment_count) {
            this.update_comment_count = update_comment_count;
        }
    }

    /**
     * 更新动态实体---删除或者新增动态后刷新列表
     */
    public static class UpdateDynamicBean {
        private boolean update_list;//是否请求网络数据刷新列表
        private int dynamicId;//要删除的动态id

        public boolean isUpdate_list() {
            return update_list;
        }

        public void setUpdate_list(boolean update_list) {
            this.update_list = update_list;
        }

        public int getDynamicId() {
            return dynamicId;
        }

        public void setDynamicId(int dynamicId) {
            this.dynamicId = dynamicId;
        }
    }

    /**
     * 更新评论数量实体
     */
    public static class CommentCountBean {
        private int update_comment_count;
        private int dynamic_id;

        public int getDynamic_id() {
            return dynamic_id;
        }

        public void setDynamic_id(int dynamic_id) {
            this.dynamic_id = dynamic_id;
        }

        public int getUpdate_comment_count() {
            return update_comment_count;
        }

        public void setUpdate_comment_count(int update_comment_count) {
            this.update_comment_count = update_comment_count;
        }
    }
}
