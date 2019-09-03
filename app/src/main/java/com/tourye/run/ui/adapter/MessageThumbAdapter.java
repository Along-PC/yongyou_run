package com.tourye.run.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.MessageThumbBean;
import com.tourye.run.utils.GlideUtils;

import java.util.List;

/**
 *
 * @ClassName:   MessageThumbAdapter
 *
 * @Author:   along
 *
 * @Description:    消息通知点赞列表适配器
 *
 * @CreateDate:   2019/4/3 4:03 PM
 *
 */
public class MessageThumbAdapter extends RecyclerView.Adapter<MessageThumbAdapter.MessageThumbHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<MessageThumbBean.DataBean> mDataBeans;

    public MessageThumbAdapter(Context context, List<MessageThumbBean.DataBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDataBeans(List<MessageThumbBean.DataBean> dataBeans) {
        mDataBeans = dataBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageThumbHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MessageThumbHolder(mLayoutInflater.inflate(R.layout.item_fragment_message_thumb,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageThumbHolder messageThumbHolder, int i) {
        MessageThumbBean.DataBean dataBean = mDataBeans.get(i);
        messageThumbHolder.mTvItemMessageThumbName.setText(dataBean.getNickname());
        messageThumbHolder.mTvItemMessageThumbContent.setText("点赞了你的动态");
        messageThumbHolder.mTvItemMessageThumbTime.setText(dataBean.getCreated_at());
        GlideUtils.getInstance().loadCircleImage(dataBean.getAvatar(),messageThumbHolder.mImgItemMessageThumbHead);
        List<String> images = dataBean.getImage();
        if (images != null && images.size()>0) {
            messageThumbHolder.mImgItemMessageThumbRefer.setVisibility(View.VISIBLE);
            messageThumbHolder.mTvItemMessageThumbRefer.setVisibility(View.GONE);
            GlideUtils.getInstance().loadImage(images.get(0),messageThumbHolder.mImgItemMessageThumbRefer);
        }else{
            messageThumbHolder.mImgItemMessageThumbRefer.setVisibility(View.GONE);
            messageThumbHolder.mTvItemMessageThumbRefer.setVisibility(View.VISIBLE);
            messageThumbHolder.mTvItemMessageThumbRefer.setText(dataBean.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public class MessageThumbHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemMessageThumbHead;
        private TextView mTvItemMessageThumbName;
        private TextView mTvItemMessageThumbTime;
        private TextView mTvItemMessageThumbContent;
        private TextView mTvItemMessageThumbRefer;
        private ImageView mImgItemMessageThumbRefer;

        public MessageThumbHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemMessageThumbHead = (ImageView) itemView.findViewById(R.id.img_item_message_thumb_head);
            mTvItemMessageThumbName = (TextView) itemView.findViewById(R.id.tv_item_message_thumb_name);
            mTvItemMessageThumbTime = (TextView) itemView.findViewById(R.id.tv_item_message_thumb_time);
            mTvItemMessageThumbContent = (TextView) itemView.findViewById(R.id.tv_item_message_thumb_content);
            mTvItemMessageThumbRefer = (TextView) itemView.findViewById(R.id.tv_item_message_thumb_refer);
            mImgItemMessageThumbRefer = (ImageView) itemView.findViewById(R.id.img_item_message_thumb_refer);

        }
    }
}
