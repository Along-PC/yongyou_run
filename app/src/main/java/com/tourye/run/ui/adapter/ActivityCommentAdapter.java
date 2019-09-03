package com.tourye.run.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tourye.run.R;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.ActivityCommentBean;
import com.tourye.run.utils.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @ClassName:   ActivityCommentAdapter
 *
 * @Author:   along
 *
 * @Description:    跑友说适配器
 *
 * @CreateDate:   2019/4/2 2:03 PM
 *
 */
public class ActivityCommentAdapter extends RecyclerView.Adapter<ActivityCommentAdapter.ActivityCommentHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<ActivityCommentBean.DataBean> mDataBeans=new ArrayList<>();

    public ActivityCommentAdapter(Context context, List<ActivityCommentBean.DataBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ActivityCommentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ActivityCommentHolder(mLayoutInflater.inflate(R.layout.item_signup_running_friend,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityCommentHolder activityCommentHolder, int i) {
        ActivityCommentBean.DataBean dataBean = mDataBeans.get(i);
        activityCommentHolder.mTvItemSignupRunningFriendName.setText(dataBean.getNickname());
        activityCommentHolder.mTvItemSignupRunningFriendGrade.setText(dataBean.getTag());
        activityCommentHolder.mTvItemSignupRunningFriendContent.setText(dataBean.getContent());
        RequestOptions requestOptions = new RequestOptions().transform(new GlideCircleTransform(BaseApplication.mApplicationContext));
        Glide.with(BaseApplication.mApplicationContext).load(dataBean.getAvatar()).apply(requestOptions).into(activityCommentHolder.mImgItemSignupRunningFriendHead);

    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public class ActivityCommentHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemSignupRunningFriendHead;
        private TextView mTvItemSignupRunningFriendName;
        private TextView mTvItemSignupRunningFriendGrade;
        private TextView mTvItemSignupRunningFriendContent;



        public ActivityCommentHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemSignupRunningFriendHead = (ImageView) itemView.findViewById(R.id.img_item_signup_running_friend_head);
            mTvItemSignupRunningFriendName = (TextView) itemView.findViewById(R.id.tv_item_signup_running_friend_name);
            mTvItemSignupRunningFriendGrade = (TextView) itemView.findViewById(R.id.tv_item_signup_running_friend_grade);
            mTvItemSignupRunningFriendContent = (TextView) itemView.findViewById(R.id.tv_item_signup_running_friend_content);

        }
    }
}
