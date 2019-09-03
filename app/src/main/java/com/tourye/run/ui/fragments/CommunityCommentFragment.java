package com.tourye.run.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.bean.CommonBean;
import com.tourye.run.bean.CreateCommentBean;
import com.tourye.run.bean.CreateReplyBean;
import com.tourye.run.bean.ReplyBean;
import com.tourye.run.bean.ReplyEntity;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.CommunityCommentAdapter;
import com.tourye.run.ui.dialogs.DeleteCommentDialog;
import com.tourye.run.utils.SaveUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by longlongren on 2018/10/15.
 * <p>
 * introduce:社区详情评论页面
 */

public class CommunityCommentFragment extends BaseCommunityFragment {

    private RecyclerView mListFragmentCommunityComment;
    private SmartRefreshLayout mRefreshLayoutFragmentCommunityComment;

    private CommunityCommentAdapter mCommunityCommentAdapter;

    private int last_id=0;//上一条评论的id
    private int count=10;//每次请求评论的数量
    private int mDynamic_id;//动态的id

    private List<ReplyBean.DataBean> mDataBeans=new ArrayList<>();//评论数据

    @Override
    public void initView(View view) {
        mListFragmentCommunityComment = (RecyclerView) view.findViewById(R.id.list_fragment_community_comment);
        mRefreshLayoutFragmentCommunityComment = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout_fragment_community_comment);

        //禁用下拉刷新
        mRefreshLayoutFragmentCommunityComment.setEnableRefresh(false);
//        mRefreshLayoutFragmentCommunityComment.setEnableAutoLoadMore(false);
        mRefreshLayoutFragmentCommunityComment.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getCommentList(false);
            }
        });
    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        mDynamic_id =arguments.getInt("data");

        mCommunityCommentAdapter = new CommunityCommentAdapter(this);
        mListFragmentCommunityComment.setLayoutManager(new LinearLayoutManager(mActivity));
        mListFragmentCommunityComment.setAdapter(mCommunityCommentAdapter);
        mCommunityCommentAdapter.setOnItemclickListener(new CommunityCommentAdapter.OnItemclickListener() {
            @Override
            public void onItemclick(final int pos) {
                int userId = SaveUtil.getInt(SaveConstants.USER_ID, -1);
                ReplyBean.DataBean dataBean = mDataBeans.get(pos);
                if (userId!=dataBean.getUser_id()) {
                    return;
                }
                DeleteCommentDialog deleteCommentDialog = new DeleteCommentDialog(mActivity);
                deleteCommentDialog.setDeleteCommentCallback(new DeleteCommentDialog.DeleteCommentCallback() {
                    @Override
                    public void deleteComment() {
                        delComment(pos,mCommunityCommentAdapter);
                    }
                });
                deleteCommentDialog.show();
            }
        });
        getCommentList(true);
    }

    /**
     * 获取评论列表
     * @param isRefresh 是否是刷新操作
     */
    public void getCommentList(final boolean isRefresh){
        Map<String,String> map=new HashMap<>();
        map.put("id", mDynamic_id +"");
        map.put("count",count+"");
        if (!isRefresh) {
            map.put("last_id",last_id+"");
        }
        HttpUtils.getInstance().get(Constants.COMMENT_LIST, map, new HttpCallback<ReplyBean>() {
            @Override
            public void onFailureExecute() {
                super.onFailureExecute();
                if (isRefresh) {

                }else{
                    mRefreshLayoutFragmentCommunityComment.finishLoadMore();
                }
            }

            @Override
            public void onPreExcute() {
                super.onPreExcute();
                if (isRefresh) {

                }else{
                    mRefreshLayoutFragmentCommunityComment.finishLoadMore();
                }
            }

            @Override
            public void onSuccessExecute(ReplyBean replyBean) {
                if (replyBean.getStatus()!=0) {
                    return;
                }
                List<ReplyBean.DataBean> data = replyBean.getData();
                if (data!=null && data.size()>0) {
                    ReplyBean.DataBean dataBean = data.get(data.size() - 1);
                    last_id=dataBean.getId();
                    if (isRefresh) {
                        mDataBeans=data;
                    }else{
                        mDataBeans.addAll(data);
                    }
                    mCommunityCommentAdapter.setData(mDataBeans);

                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10086 && resultCode==110) {
            //添加评论的回复
            final String comment = data.getStringExtra("comment");
            final String current_id = data.getStringExtra("current_id");
            final int pos = data.getIntExtra("pos", -1);
            Map<String, String> map = new HashMap<>();
            map.put("id", current_id);
            map.put("content", comment);
            HttpUtils.getInstance().post(Constants.CREATE_REPLY, map, new HttpCallback<CreateReplyBean>() {
                @Override
                public void onSuccessExecute(CreateReplyBean createReplyBean) {
                    if (createReplyBean.getStatus() == 0) {
                        int id = createReplyBean.getData();
                        int user_id = SaveUtil.getInt("user_id", -998);
                        ReplyEntity reply = new ReplyEntity();
                        reply.setId(id);
                        reply.setComment_id(Integer.parseInt(current_id));
                        reply.setUser_id(user_id);
                        reply.setContent(comment);
                        reply.setNickname(SaveUtil.getString("user_name", ""));
                        mDataBeans.get(pos).getReplies().add(0,reply);
                        mDataBeans.get(pos).setReply_count(mDataBeans.get(pos).getReply_count()+1);
                        mCommunityCommentAdapter.notifyItemChanged(pos);
                    }
                }
            });
        }else if(requestCode==10010 && resultCode==110){
            //添加评论回复的回复
            final String comment = data.getStringExtra("comment");
            final String current_id = data.getStringExtra("current_id");
            int reply_id = data.getIntExtra("reply_id",-1);
            int reply_to = data.getIntExtra("reply_to",-1);
            final int pos = data.getIntExtra("pos", -1);
            final int item_pos = data.getIntExtra("item_pos", -1);
            Map<String, String> map = new HashMap<>();
            map.put("id", current_id);
            map.put("content", comment);
            map.put("reply_id", reply_id+"");
            map.put("reply_to", reply_to+"");
            HttpUtils.getInstance().post(Constants.CREATE_REPLY, map, new HttpCallback<CreateReplyBean>() {
                @Override
                public void onSuccessExecute(CreateReplyBean createReplyBean) {
                    if (createReplyBean.getStatus() == 0) {
                        int id = createReplyBean.getData();
                        int user_id = SaveUtil.getInt("user_id", -998);
                        ReplyEntity reply = new ReplyEntity();
                        reply.setId(id);
                        reply.setComment_id(Integer.parseInt(current_id));
                        reply.setUser_id(user_id);
                        reply.setContent(comment);
                        reply.setNickname(SaveUtil.getString("user_name", ""));
                        reply.setReply_to(mDataBeans.get(pos).getReplies().get(item_pos).getNickname());
                        mDataBeans.get(pos).getReplies().add(0,reply);
                        mDataBeans.get(pos).setReply_count(mDataBeans.get(pos).getReply_count()+1);
                        mCommunityCommentAdapter.notifyItemChanged(pos);
                    }
                }
            });
        }
    }

    @Override
    public boolean isNeedLazyLoad() {
        return false;
    }

    /**
     * 新增评论
     */
    @Override
    public void addComment(final String text) {
        super.addComment(text);
        Map<String,String> map=new HashMap<>();
        map.put("id", mDynamic_id +"");
        map.put("content",text);
        HttpUtils.getInstance().post(Constants.CREATE_COMMENT, map, new HttpCallback<CreateCommentBean>() {
            @Override
            public void onSuccessExecute(CreateCommentBean createCommentBean) {
                if (createCommentBean.getStatus()==0) {
                    //生成的评论id
                    int data = createCommentBean.getData();
//                    last_id=data;
                    String user_name = SaveUtil.getString("user_name", "");
                    ReplyBean.DataBean dataBean = new ReplyBean.DataBean();
                    dataBean.setNickname(user_name);
                    dataBean.setContent(text);
                    dataBean.setId(data);
                    dataBean.setUser_id(SaveUtil.getInt(SaveConstants.USER_ID,-1));
                    dataBean.setAvatar(SaveUtil.getString("user_head",""));
                    dataBean.setReplies(new ArrayList<ReplyEntity>());
                    dataBean.setCreate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    mDataBeans.add(0,dataBean);
                    mCommunityCommentAdapter.setData(mDataBeans);
                    mListFragmentCommunityComment.smoothScrollToPosition(0);
                    //发送事件，更新动态列表中评论的数量
                    AllDynamicFragment.CommentCountBean commentCountBean = new AllDynamicFragment.CommentCountBean();
                    commentCountBean.setDynamic_id(mDynamic_id);
                    commentCountBean.setUpdate_comment_count(1);
                    EventBus.getDefault().post(commentCountBean);
                }
            }
        });

    }

    /**
     * 删除评论
     * @param pos
     * @param communityCommentAdapter
     */
    private void delComment(final int pos, CommunityCommentAdapter communityCommentAdapter) {
        ReplyBean.DataBean dataBean = mDataBeans.get(pos);
        int id = dataBean.getId();
        Map<String,String> map=new HashMap<>();
        map.put("id",id+"");
        HttpUtils.getInstance().post(Constants.DELETE_COMMENT, map, new HttpCallback<CommonBean>() {
            @Override
            public void onSuccessExecute(CommonBean commonBean) {
                if (commonBean.getStatus()==0) {
                    mDataBeans.remove(pos);
                    mCommunityCommentAdapter.setData(mDataBeans);
                    if (mDataBeans!=null && mDataBeans.size()>0) {
                        last_id=mDataBeans.get(mDataBeans.size()-1).getId();
                    }
                    //发送事件，更新动态列表中评论的数量
                    AllDynamicFragment.CommentCountBean commentCountBean = new AllDynamicFragment.CommentCountBean();
                    commentCountBean.setDynamic_id(mDynamic_id);
                    commentCountBean.setUpdate_comment_count(-1);
                    EventBus.getDefault().post(commentCountBean);
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_community_comment;
    }

}
