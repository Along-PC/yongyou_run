package com.tourye.run.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tourye.run.BuildConfig;
import com.tourye.run.R;
import com.tourye.run.bean.MessageSystemBean;
import com.tourye.run.ui.activities.common.CommonWebActivity;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 *
 * @ClassName:   MessageSystemAdapter
 *
 * @Author:   along
 *
 * @Description:    系统消息适配器
 *
 * @CreateDate:   2019/4/3 1:33 PM
 *
 */
public class MessageSystemAdapter extends RecyclerView.Adapter<MessageSystemAdapter.MessageSystemHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<MessageSystemBean.DataBean> mDataBeans;

    public MessageSystemAdapter(Context context, List<MessageSystemBean.DataBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDataBeans(List<MessageSystemBean.DataBean> dataBeans) {
        mDataBeans = dataBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageSystemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MessageSystemHolder(mLayoutInflater.inflate(R.layout.item_fragment_message_system,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageSystemHolder messageSystemHolder, int i) {
        final MessageSystemBean.DataBean dataBean = mDataBeans.get(i);
        messageSystemHolder.mTvItemMessageSystemTitle.setText(Html.fromHtml(dataBean.getTitle()));
        messageSystemHolder.mTvItemMessageSystemTime.setText(dataBean.getCreated_at());
        if (TextUtils.isEmpty(dataBean.getBg())) {
            messageSystemHolder.mImgItemMessageSystemBanner.setVisibility(View.GONE);
        }else{
            messageSystemHolder.mImgItemMessageSystemBanner.setVisibility(View.GONE);
            GlideUtils.getInstance().loadImage(dataBean.getBg(),messageSystemHolder.mImgItemMessageSystemBanner);
        }
        if (TextUtils.isEmpty(dataBean.getLink())) {
            messageSystemHolder.mLlItemMessageSystemContent.setOnClickListener(null);
        }else{
            messageSystemHolder.mLlItemMessageSystemContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CommonWebActivity.class);
                    String link = dataBean.getLink();
                    URL url = null;
                    try {
                        url = new URL(link);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    //替换url中的query来标识身份
                    boolean contains = url.getHost().equalsIgnoreCase(BuildConfig.HOST_URL);
                    String token="env=gio&jwt="+ SaveUtil.getString("Authorization", "");
                    if (contains) {
                        String query = url.getQuery();
                        if (TextUtils.isEmpty(query)) {
                            String protocol = url.getProtocol();
                            //替换url中的query来标识身份
                            String host = url.getHost();
                            int port = url.getDefaultPort();
                            String path = url.getPath();
                            String ref = url.getRef();
                            link=protocol+"://"+host+":"+port+path+"?"+query+token+"#"+ref;
                        }else{
                            link = link.replace(query, query + "&" + token);
                        }
                    }
                    intent.putExtra("url",link);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public class MessageSystemHolder extends RecyclerView.ViewHolder {
        private TextView mTvItemMessageSystemTitle;
        private TextView mTvItemMessageSystemTime;
        private ImageView mImgItemMessageSystemBanner;
        private LinearLayout mLlItemMessageSystemContent;

        public MessageSystemHolder(@NonNull View itemView) {
            super(itemView);
            mTvItemMessageSystemTitle = (TextView) itemView.findViewById(R.id.tv_item_message_system_title);
            mTvItemMessageSystemTime = (TextView) itemView.findViewById(R.id.tv_item_message_system_time);
            mImgItemMessageSystemBanner = (ImageView) itemView.findViewById(R.id.img_item_message_system_banner);
            mLlItemMessageSystemContent = (LinearLayout) itemView.findViewById(R.id.ll_item_message_system_content);
        }
    }
}
