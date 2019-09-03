package com.tourye.run.ui.adapter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.ChildReplyBean;
import com.tourye.run.bean.CommonBean;
import com.tourye.run.bean.ReplyBean;
import com.tourye.run.bean.ReplyEntity;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.community.CommentActivity;
import com.tourye.run.ui.dialogs.DeleteCommentDialog;
import com.tourye.run.utils.DateFormatUtils;
import com.tourye.run.utils.GlideCircleTransform;
import com.tourye.run.utils.SaveUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by longlongren on 2018/10/11.
 * <p>
 * introduce:社区详情评论适配器
 */

public class CommunityCommentAdapter extends RecyclerView.Adapter<CommunityCommentAdapter.CommunityCommentHolder> {
    private Fragment mContext;
    private LayoutInflater mLayoutInflater;
    private List<ReplyBean.DataBean> mDataBeans = new ArrayList<>();
    private OnItemclickListener mOnItemclickListener;

    public CommunityCommentAdapter(Fragment context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext.getContext());
    }

    /**
     * 列表条目点击事件
     *
     * @param onItemclickListener
     */
    public void setOnItemclickListener(OnItemclickListener onItemclickListener) {
        mOnItemclickListener = onItemclickListener;
    }

    public void setData(List<ReplyBean.DataBean> dataBeans) {
        mDataBeans = dataBeans;
        notifyDataSetChanged();
    }

    @Override
    public CommunityCommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommunityCommentHolder(mLayoutInflater.inflate(R.layout.item_community_detail_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(final CommunityCommentHolder holder, final int position) {
        final ReplyBean.DataBean dataBean = mDataBeans.get(position);
        List<ReplyEntity> replies = dataBean.getReplies();
        if (replies!=null && replies.size()>0) {
            dataBean.setLast_reply_id(replies.get(replies.size()-1).getId());
        }

        processReply(dataBean,holder,position);

        //加载头像
        RequestOptions requestOptions = new RequestOptions().transform(new GlideCircleTransform(BaseApplication.mApplicationContext));
        Glide.with(BaseApplication.mApplicationContext).load(dataBean.getAvatar()).apply(requestOptions).into(holder.mImgCommunityDetailCommentHead);
        //姓名
        holder.mTvCommunityDetailCommentName.setText(dataBean.getNickname());
        //发表时间
        formatTime(holder.mTvCommunityDetailCommentTime,dataBean.getCreate_time());
        //点击回复
        holder.mImgCommunityDetailCommentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext.getContext(), CommentActivity.class);
                intent.putExtra("current_id",dataBean.getId()+"");
                intent.putExtra("type",3);
                intent.putExtra("pos",position);
                intent.putExtra("reply_name",dataBean.getNickname());
                mContext.startActivityForResult(intent,10086);
            }
        });
        holder.mTvCommunityDetailCommentMessage.setText(dataBean.getContent());

        //设置条目点击事件
        holder.mLlItemCommunityDetailComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemclickListener.onItemclick(position);
            }
        });

    }

    /**
     * 处理回复列表
     * @param dataBean
     * @param holder
     * @param position
     */
    private void processReply(final ReplyBean.DataBean dataBean, final CommunityCommentHolder holder, final int position) {
        //设置二级评论
        final List<ReplyEntity> replies = dataBean.getReplies();
        if (replies != null && replies.size() > 0) {
            holder.mLlItemCommunityDetailReply.setVisibility(View.VISIBLE);
            final CommentReplyAdapter commentReplyAdapter = new CommentReplyAdapter(mContext.getContext());
            holder.mListCommunityDetailComment.setLayoutManager(new LinearLayoutManager(mContext.getContext()));
            holder.mListCommunityDetailComment.setAdapter(commentReplyAdapter);
            for (int i = 0; i < replies.size(); i++) {
                replies.get(i).setComment_id(dataBean.getId());
            }
            commentReplyAdapter.setData(replies);
            //二级评论条目点击事件--点击
            commentReplyAdapter.setOnItemClickListener(new CommentReplyAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(final int i,String nickName) {
                    ReplyEntity replyEntity = replies.get(i);
                    int user_id_temp = replyEntity.getUser_id();
                    int user_id = SaveUtil.getInt("user_id", -998);
                    if (user_id == user_id_temp) {
                        //自己的回复
                        DeleteCommentDialog deleteCommentDialog = new DeleteCommentDialog(mContext.getContext());
                        deleteCommentDialog.setDeleteCommentCallback(new DeleteCommentDialog.DeleteCommentCallback() {
                            @Override
                            public void deleteComment() {
                                deleteReply(replies, i,commentReplyAdapter,dataBean,holder);
                            }
                        });
                        deleteCommentDialog.show();
                    }else{
                        //别人的回复
                        Intent intent = new Intent(mContext.getContext(), CommentActivity.class);
                        intent.putExtra("current_id",dataBean.getId()+"");
                        intent.putExtra("type",4);
                        intent.putExtra("pos",position);
                        intent.putExtra("item_pos",i);
                        intent.putExtra("reply_id",dataBean.getId());
                        intent.putExtra("reply_to",dataBean.getUser_id());
                        intent.putExtra("reply_name",nickName);
                        mContext.startActivityForResult(intent,10010);
                    }

                }
            });

            //加载更多二级评论
            if (dataBean.getReply_count() > 2) {
                int remainder_count = dataBean.getReply_count() - replies.size();//剩余回复数
                dataBean.setRemainder_count(remainder_count);
                if (remainder_count<=0) {
                    holder.mTvCommunityDetailCommentExpand.setVisibility(View.GONE);
                }else{
                    holder.mTvCommunityDetailCommentExpand.setVisibility(View.VISIBLE);
                    holder.mTvCommunityDetailCommentExpand.setText("还有" + remainder_count + "条回复>");
                }
            } else {
                holder.mTvCommunityDetailCommentExpand.setVisibility(View.GONE);
            }
            holder.mTvCommunityDetailCommentExpand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getMoreReply(dataBean, replies, commentReplyAdapter, holder);
                }
            });

        }else{
            holder.mLlItemCommunityDetailReply.setVisibility(View.GONE);
        }

    }

    /**
     * 格式化时间
     * @param tvItemFindCommunityTime
     * @param create_time
     */
    private void formatTime(TextView tvItemFindCommunityTime, String create_time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date createTime = simpleDateFormat.parse(create_time);
            Calendar instance = Calendar.getInstance();
            instance.setTime(createTime);
            long timeInMillis = instance.getTimeInMillis();
            long timeDiscrepancy = System.currentTimeMillis() - timeInMillis;
            int secondUnit=1000;
            int minuteUnit=secondUnit*60;
            int hourUnit=minuteUnit*60;
            int dayUnit=hourUnit*24;
            if (timeDiscrepancy<minuteUnit) {
                tvItemFindCommunityTime.setText("刚刚");
            }else if(timeDiscrepancy>=minuteUnit && timeDiscrepancy<hourUnit){
                int minutes = (int) Math.floor(timeDiscrepancy / minuteUnit);
                tvItemFindCommunityTime.setText(minutes+"分钟前");
            }else if(timeDiscrepancy>=hourUnit && timeDiscrepancy<dayUnit){
                int hours = (int) Math.floor(timeDiscrepancy / hourUnit);
                tvItemFindCommunityTime.setText(hours+"小时前");
            }else if(timeDiscrepancy>=dayUnit && timeDiscrepancy<dayUnit*3){
                int days = (int) Math.floor(timeDiscrepancy / dayUnit);
                tvItemFindCommunityTime.setText(days+"天前");
            }else{
                tvItemFindCommunityTime.setText(DateFormatUtils.format(create_time));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除回复
     *
     * @param replies
     * @param position
     */
    private void deleteReply(final List<ReplyEntity> replies, final int position, final CommentReplyAdapter commentReplyAdapter, final ReplyBean.DataBean dataBean, final CommunityCommentHolder holder) {
        ReplyEntity replyEntity = replies.get(position);
        Map<String, String> map = new HashMap<>();
        map.put("id", replyEntity.getId() + "");
        HttpUtils.getInstance().post(Constants.DELETE_REPLY, map, new HttpCallback<CommonBean>() {
            @Override
            public void onSuccessExecute(CommonBean commonBean) {
                if (commonBean.getStatus()==0) {
                    replies.remove(position);
                    dataBean.setReply_count(dataBean.getReply_count()-1);
                    commentReplyAdapter.notifyDataSetChanged();
                    if (dataBean.getReply_count()<=0) {
                        holder.mLlItemCommunityDetailReply.setVisibility(View.GONE);
                    }
                }
            }
        });

    }

    /**
     * 获取更多回复
     *
     * @param dataBean            当前评论数据
     * @param replies             当前回复数据
     * @param commentReplyAdapter 回复列表适配器
     */
    private void getMoreReply(final ReplyBean.DataBean dataBean, final List<ReplyEntity> replies, final CommentReplyAdapter commentReplyAdapter, final CommunityCommentHolder holder) {

        Map<String, String> map = new HashMap<>();
        map.put("id", dataBean.getId() + "");
        map.put("count", 10 + "");
        map.put("last_id", dataBean.getLast_reply_id() + "");

        HttpUtils.getInstance().get(Constants.REPLY_LIST, map, new HttpCallback<ChildReplyBean>() {
            @Override
            public void onSuccessExecute(ChildReplyBean childReplyBean) {
                if (childReplyBean.getStatus() != 0) {
                    return;
                }
                List<ReplyEntity> data = childReplyBean.getData();
                if (data != null && data.size() > 0) {
//                    commentReplyAdapter.addData(data);
                    if (data.size() < 10) {
                        holder.mTvCommunityDetailCommentExpand.setVisibility(View.GONE);
                    } else {
                        int remainder_count = dataBean.getRemainder_count();
                        remainder_count = remainder_count - data.size();
                        dataBean.setRemainder_count(remainder_count);
                        holder.mTvCommunityDetailCommentExpand.setText("还有" + remainder_count + "条回复>");
                    }
                    replies.addAll(data);
                    commentReplyAdapter.setData(replies);
                    dataBean.setLast_reply_id(data.get(data.size()-1).getId());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    /**
     * 条目点击事件
     */
    public interface OnItemclickListener {
        public void onItemclick(int pos);
    }

    public class CommunityCommentHolder extends RecyclerView.ViewHolder {

        private ImageView mImgCommunityDetailCommentHead;
        private TextView mTvCommunityDetailCommentName;
        private TextView mTvCommunityDetailCommentTime;
        private ImageView mImgCommunityDetailCommentSubmit;
        private TextView mTvCommunityDetailCommentMessage;
        private RecyclerView mListCommunityDetailComment;
        private TextView mTvCommunityDetailCommentExpand;
        private LinearLayout mLlItemCommunityDetailComment;
        private LinearLayout mLlItemCommunityDetailReply;

        public CommunityCommentHolder(View itemView) {
            super(itemView);
            mLlItemCommunityDetailComment = (LinearLayout) itemView.findViewById(R.id.ll_item_community_detail_comment);
            mImgCommunityDetailCommentHead = (ImageView) itemView.findViewById(R.id.img_community_detail_comment_head);
            mTvCommunityDetailCommentName = (TextView) itemView.findViewById(R.id.tv_community_detail_comment_name);
            mTvCommunityDetailCommentTime = (TextView) itemView.findViewById(R.id.tv_community_detail_comment_time);
            mImgCommunityDetailCommentSubmit = (ImageView) itemView.findViewById(R.id.img_community_detail_comment_submit);
            mTvCommunityDetailCommentMessage = (TextView) itemView.findViewById(R.id.tv_community_detail_comment_message);
            mListCommunityDetailComment = (RecyclerView) itemView.findViewById(R.id.list_community_detail_comment);
            mTvCommunityDetailCommentExpand = (TextView) itemView.findViewById(R.id.tv_community_detail_comment_expand);
            mLlItemCommunityDetailReply = (LinearLayout) itemView.findViewById(R.id.ll_item_community_detail_reply);
        }
    }

}
