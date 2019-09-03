package com.tourye.run.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.MessageCommentBean;
import com.tourye.run.utils.GlideUtils;

import java.util.List;

/**
 *
 * @ClassName:   MessageCommentAdapter
 *
 * @Author:   along
 *
 * @Description:    评论消息通知适配器
 *
 * @CreateDate:   2019/4/3 2:34 PM
 *
 */
public class MessageCommentAdapter extends RecyclerView.Adapter<MessageCommentAdapter.MessageCommentHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<MessageCommentBean.DataBean> mDataBeans;

    public MessageCommentAdapter(Context context, List<MessageCommentBean.DataBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDataBeans(List<MessageCommentBean.DataBean> dataBeans) {
        mDataBeans = dataBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageCommentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MessageCommentHolder(mLayoutInflater.inflate(R.layout.item_fragment_message_comment,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageCommentHolder messageCommentHolder, int i) {
        MessageCommentBean.DataBean dataBean = mDataBeans.get(i);
        messageCommentHolder.mTvItemMessageCommentName.setText(dataBean.getNickname());
        messageCommentHolder.mTvItemMessageCommentTime.setText(dataBean.getCreate_time());
        List<String> images = dataBean.getImage();
        if (images!=null && images.size()>0) {
            messageCommentHolder.mImgItemMessageCommentRefer.setVisibility(View.VISIBLE);
            messageCommentHolder.mTvItemMessageCommentRefer.setVisibility(View.GONE);
            GlideUtils.getInstance().loadImage(images.get(0),messageCommentHolder.mImgItemMessageCommentRefer);
        }else{
            messageCommentHolder.mImgItemMessageCommentRefer.setVisibility(View.GONE);
            messageCommentHolder.mTvItemMessageCommentRefer.setVisibility(View.VISIBLE);
            messageCommentHolder.mTvItemMessageCommentRefer.setText(dataBean.getMoment_content());
        }
        GlideUtils.getInstance().loadCircleImage(dataBean.getAvatar(),messageCommentHolder.mImgItemMessageCommentHead);
        if (dataBean.getType().equals("reply")) {
            String temp="回复<font color='#4A90E2'>@"+dataBean.getReply_nickname()+":</font>"+dataBean.getReply_content();
            messageCommentHolder.mTvItemMessageCommentContent.setText(Html.fromHtml(temp));
        }else{
            messageCommentHolder.mTvItemMessageCommentContent.setText(dataBean.getComment_content());
        }
    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public class MessageCommentHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemMessageCommentHead;
        private TextView mTvItemMessageCommentName;
        private TextView mTvItemMessageCommentTime;
        private TextView mTvItemMessageCommentContent;
        private TextView mTvItemMessageCommentRefer;
        private ImageView mImgItemMessageCommentRefer;

        public MessageCommentHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemMessageCommentHead = (ImageView) itemView.findViewById(R.id.img_item_message_comment_head);
            mTvItemMessageCommentName = (TextView) itemView.findViewById(R.id.tv_item_message_comment_name);
            mTvItemMessageCommentTime = (TextView) itemView.findViewById(R.id.tv_item_message_comment_time);
            mTvItemMessageCommentContent = (TextView) itemView.findViewById(R.id.tv_item_message_comment_content);
            mTvItemMessageCommentRefer = (TextView) itemView.findViewById(R.id.tv_item_message_comment_refer);
            mImgItemMessageCommentRefer = (ImageView) itemView.findViewById(R.id.img_item_message_comment_refer);

        }
    }
}
