package com.tourye.run.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.CommonBean;
import com.tourye.run.bean.CommonJsonBean;
import com.tourye.run.bean.DynamicListBean;
import com.tourye.run.bean.ThumbUpBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.common.SingleImageActivity;
import com.tourye.run.ui.activities.community.CommentActivity;
import com.tourye.run.ui.activities.common.ImageDetailActivity;
import com.tourye.run.ui.activities.community.CommunityDetailActivity;
import com.tourye.run.ui.dialogs.DynamicOperateDialog;
import com.tourye.run.ui.dialogs.ShareDialog;
import com.tourye.run.ui.fragments.AllDynamicFragment;
import com.tourye.run.utils.DateFormatUtils;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;
import com.tourye.run.views.NineGridlayout;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by longlongren on 2018/9/27.
 * <p>
 * introduce:发现--社区适配器
 */

public class FindCommunityAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<DynamicListBean.DataBean> mDatas = new ArrayList<>();

    private int max_lines=5;//文字最多显示行数

    public FindCommunityAdapter(Context context, List<DynamicListBean.DataBean> datas) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mDatas = datas;
    }

    public void setDatas(List<DynamicListBean.DataBean> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public void addDatas(List<DynamicListBean.DataBean> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final FindCommunityHolder findCommunityHolder;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.item_fragment_find_community, viewGroup, false);
            findCommunityHolder = new FindCommunityHolder(view);
            view.setTag(findCommunityHolder);
        } else {
            findCommunityHolder = (FindCommunityHolder) view.getTag();
        }
        //获取数据，开始赋值
        final DynamicListBean.DataBean dataBean = mDatas.get(i);
        //头像
        RequestOptions requestOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false).dontAnimate();
        Glide.with(BaseApplication.mApplicationContext).load(dataBean.getAvatar()).apply(requestOptions).into(findCommunityHolder.mImgItemFindCommunityHead);
        findCommunityHolder.mTvItemFindCommunityName.setText(dataBean.getNickname());
        //文字内容
        setTextContent(dataBean,findCommunityHolder);
        //发表时间
        String create_time = dataBean.getCreate_time();
        if (TextUtils.isEmpty(create_time)) {
            findCommunityHolder.mTvItemFindCommunityTime.setText("");
        } else {
            formatTime(findCommunityHolder.mTvItemFindCommunityTime,create_time);
        }
        //定位城市
        String position = dataBean.getPosition();
        if (!TextUtils.isEmpty(position)) {
            findCommunityHolder.mTvItemFindCommunityCity.setText(position);
        }else{
            findCommunityHolder.mTvItemFindCommunityCity.setText(null);
        }
        //图片数据
        final ArrayList<String> urls = dataBean.getImage_thumbnails();
        if (urls==null || urls.size()==0) {
            findCommunityHolder.mNineItemFindCommunity.setVisibility(View.GONE);
        }else{
            findCommunityHolder.mNineItemFindCommunity.setVisibility(View.VISIBLE);
            findCommunityHolder.mNineItemFindCommunity.setImagesData(urls);
            findCommunityHolder.mNineItemFindCommunity.setOnItemClickListener(new NineGridlayout.OnItemClickListener() {
                @Override
                public void onItemClick(int i) {
                    Intent intent = new Intent(mContext, ImageDetailActivity.class);
                    intent.putStringArrayListExtra("data", urls);
                    intent.putExtra("pos", i);
                    mContext.startActivity(intent);
                }
            });
        }
        //点赞数
        if (dataBean.getLike_count()==0) {
            findCommunityHolder.mTvIteamAllDynamicThumb.setText("赞");
        }else{
            findCommunityHolder.mTvIteamAllDynamicThumb.setText(dataBean.getLike_count() + "");
        }
        //评论数
        if (dataBean.getComment_count()==0) {
            findCommunityHolder.mTvIteamAllDynamicComment.setText("评论");
        }else{
            findCommunityHolder.mTvIteamAllDynamicComment.setText(dataBean.getComment_count()+"");
        }
        //点赞状态
        findCommunityHolder.mImgIteamAllDynamicThumb.setSelected(dataBean.isAlready_like());
        final FindCommunityHolder finalFindCommunityHolder = findCommunityHolder;
        findCommunityHolder.mConstraintItemAllDynamicThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //当发出网络请求，在没有拿到网络请求结果时，取消监听
                finalFindCommunityHolder.mConstraintItemAllDynamicThumb.setOnClickListener(null);
                if (finalFindCommunityHolder.mImgIteamAllDynamicThumb.isSelected()) {
                    cancel_thumb_up(dataBean, finalFindCommunityHolder);
                } else {
                    thumb_up(dataBean, finalFindCommunityHolder);
                }
            }
        });
        //用户操作
        operate(finalFindCommunityHolder,dataBean,i);
        //评论
        findCommunityHolder.mConstraintItemAllDynamicComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataBean.getComment_count()==0) {
                    Intent intent = new Intent(mContext, CommentActivity.class);
                    intent.putExtra("dynamic_id",dataBean.getId());
                    intent.putExtra("type",1);
                    intent.putExtra("reply_name",dataBean.getNickname());
                    mContext.startActivity(intent);
                }else{
                    DynamicListBean.DataBean dataBean = mDatas.get(i);
                    Intent intent = new Intent(mContext, CommunityDetailActivity.class);
                    intent.putExtra("dynamic_id", dataBean.getId());
                    mContext.startActivity(intent);
                }
            }
        });
        //分享
        findCommunityHolder.mConstraintItemAllDynamicShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link=Constants.DOMAIN+"?"+
                        "#/community/"+dataBean.getId();
                ShareDialog shareDialog = new ShareDialog(mContext);
                shareDialog.setData("这是["+dataBean.getNickname()+"]发布的百日跑社区动态，快来瞧瞧","万人坚持跑步打卡100天，看到坚持的力量",
                        link,"https://static.run100.runorout.cn/meta/logo2.png");
                shareDialog.show();
            }
        });
        //底部跑步状态
        if (dataBean.getSign_in_data()!=null) {
            findCommunityHolder.mRlItemFindCommunityRunRecord.setVisibility(View.VISIBLE);
            final DynamicListBean.DataBean.SignInDataBean sign_in_data = dataBean.getSign_in_data();
            GlideUtils.getInstance().loadRoundImage(sign_in_data.getImage(),findCommunityHolder.mImgItemFindCommunityRunCard);
            findCommunityHolder.mImgItemFindCommunityRunCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, SingleImageActivity.class);
                    intent.putExtra("url",sign_in_data.getImage());
                    mContext.startActivity(intent);
                }
            });
            findCommunityHolder.mTvItemFindCommunityRunTime.setText("完成"+sign_in_data.getDistance()+"公里，用时"+sign_in_data.getTime()+"分");
            if (dataBean.getTotal_days()>0) {
                findCommunityHolder.mTvItemFindCommunityRunIntro.setVisibility(View.VISIBLE);
                findCommunityHolder.mTvItemFindCommunityRunIntro.setText("百日跑坚持打卡已"+dataBean.getTotal_days()+"天");
            }else{
                findCommunityHolder.mTvItemFindCommunityRunIntro.setVisibility(View.GONE);
            }
        }else{
            findCommunityHolder.mRlItemFindCommunityRunRecord.setVisibility(View.GONE);
        }
        return view;
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
     * 删除或者举报
     * @param finalFindCommunityHolder
     * @param dataBean
     * @param i
     */
    private void operate(final FindCommunityHolder finalFindCommunityHolder, final DynamicListBean.DataBean dataBean, int i) {
        finalFindCommunityHolder.mImgItemFindCommunityOperate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int user_id = dataBean.getUser_id();
                final int dynamicId = dataBean.getId();
                final int userId = SaveUtil.getInt(SaveConstants.USER_ID, -1);
                DynamicOperateDialog dynamicOperateDialog = new DynamicOperateDialog(mContext);
                if (user_id==userId) {
                    dynamicOperateDialog.setOnClickListener(new DynamicOperateDialog.OnClickListener() {
                        @Override
                        public void onClick() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("提示");
                            builder.setMessage("确定要删除动态吗?");
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteDynamic(dynamicId);
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    },"删除");
                }else{
                    dynamicOperateDialog.setOnClickListener(new DynamicOperateDialog.OnClickListener() {
                        @Override
                        public void onClick() {
                            report(dynamicId);
                        }
                    },"举报");
                }
                dynamicOperateDialog.showAsDropDown(finalFindCommunityHolder.mImgItemFindCommunityOperate,-DensityUtils.dp2px(67),0);
            }
        });
    }

    /**
     * 举报动态
     * @param dynamicId  动态id
     */
    private void report(int dynamicId) {
        Map<String,String> map=new HashMap<>();
        map.put("id",dynamicId+"");
        HttpUtils.getInstance().post(Constants.REPORT_DYNAMIC, map, new HttpCallback<CommonJsonBean>() {
            @Override
            public void onSuccessExecute(CommonJsonBean commonJsonBean) {
                if (commonJsonBean.getStatus()==0) {
                    Toast.makeText(mContext, "举报成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 删除动态
     */
    private void deleteDynamic(final int id) {
        Map<String,String> map=new HashMap<>();
        map.put("id",id+"");
        HttpUtils.getInstance().post(Constants.DELETE_DYNAMIC, map, new HttpCallback<CommonBean>() {
            @Override
            public void onSuccessExecute(CommonBean commonBean) {
                if (commonBean.getStatus()==0) {
                    //刷新列表
                    AllDynamicFragment.UpdateDynamicBean updateDynamicBean = new AllDynamicFragment.UpdateDynamicBean();
                    updateDynamicBean.setUpdate_list(false);
                    updateDynamicBean.setDynamicId(id);
                    EventBus.getDefault().post(updateDynamicBean);

                    Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //取消点赞
    public void cancel_thumb_up(final DynamicListBean.DataBean dataBean, final FindCommunityHolder holder){
        Map<String,String> map=new HashMap<>();
        map.put("id",dataBean.getId()+"");
        HttpUtils.getInstance().post(Constants.THUMB_UP_CANCEL, map, new HttpCallback<ThumbUpBean>() {
            @Override
            public void onSuccessExecute(ThumbUpBean thumbUpBean) {
                if (thumbUpBean.getStatus()==0) {
                    if (thumbUpBean.isData()) {
                        holder.mImgIteamAllDynamicThumb.setSelected(false);
                        dataBean.setLike_count(dataBean.getLike_count()-1);
                        dataBean.setAlready_like(false);
                        //拿到网络请求结果，重新添加监听
                        if (dataBean.getLike_count()==0) {
                            holder.mTvIteamAllDynamicThumb.setText("赞");
                        }else{
                            holder.mTvIteamAllDynamicThumb.setText(dataBean.getLike_count()+"");
                        }
                        holder.mImgIteamAllDynamicThumb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                holder.mImgIteamAllDynamicThumb.setOnClickListener(null);
                                if (holder.mImgIteamAllDynamicThumb.isSelected()) {
                                    cancel_thumb_up(dataBean,holder);
                                }else{
                                    thumb_up(dataBean,holder);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    //点赞
    public void thumb_up(final DynamicListBean.DataBean dataBean, final FindCommunityHolder holder){
        Map<String,String> map=new HashMap<>();
        map.put("id",dataBean.getId()+"");
        HttpUtils.getInstance().post(Constants.THUMB_UP, map, new HttpCallback<ThumbUpBean>() {
            @Override
            public void onSuccessExecute(ThumbUpBean thumbUpBean) {
                if (thumbUpBean.getStatus()==0) {
                    if (thumbUpBean.isData()) {
                        holder.mImgIteamAllDynamicThumb.setSelected(true);
                        dataBean.setAlready_like(true);
                        int thumb_up_count = dataBean.getLike_count();
                        dataBean.setLike_count(thumb_up_count+1);
                        //拿到网络请求结果，重新添加监听
                        if (dataBean.getLike_count()==0) {
                            holder.mTvIteamAllDynamicThumb.setText("赞");
                        }else{
                            holder.mTvIteamAllDynamicThumb.setText(dataBean.getLike_count()+"");
                        }
                        holder.mImgIteamAllDynamicThumb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                holder.mImgIteamAllDynamicThumb.setOnClickListener(null);
                                if (holder.mImgIteamAllDynamicThumb.isSelected()) {
                                    cancel_thumb_up(dataBean,holder);
                                }else{
                                    thumb_up(dataBean,holder);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * 设置文字内容
     * @param dataBean
     * @param findCommunityHolder
     */
    public void setTextContent(final DynamicListBean.DataBean dataBean, final FindCommunityHolder findCommunityHolder) {
        String content = dataBean.getContent();
        if (dataBean.isStick()) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append("置顶");
            spannableStringBuilder.append(" "+content);
            //设置部分文字颜色
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#ff1d1d"));
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            findCommunityHolder.mTvItemFindCommunityContent.setText(spannableStringBuilder);
        }else{
            findCommunityHolder.mTvItemFindCommunityContent.setText(content);
        }
        if (TextUtils.isEmpty(dataBean.getContent())) {
            findCommunityHolder.mTvItemFindCommunityContent.setVisibility(View.GONE);
            findCommunityHolder.mTvFindCommunityContentState.setVisibility(View.GONE);
        } else {
            findCommunityHolder.mTvItemFindCommunityContent.setVisibility(View.VISIBLE);
            //首次初始化
            if (dataBean.getContent_state() == 0) {
                findCommunityHolder.mTvItemFindCommunityContent.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        //这个回掉会调用多次，获取完行数后记得注销监听
                        findCommunityHolder.mTvItemFindCommunityContent.getViewTreeObserver().removeOnPreDrawListener(this);
                        //如果内容显示的行数大于限定显示行数
                        if (findCommunityHolder.mTvItemFindCommunityContent.getLineCount() > max_lines) {
                            findCommunityHolder.mTvItemFindCommunityContent.setMaxLines(max_lines);//设置最大显示行数
                            findCommunityHolder.mTvFindCommunityContentState.setVisibility(View.VISIBLE);//让其显示全文的文本框状态为显示
                            findCommunityHolder.mTvFindCommunityContentState.setText("全文");//设置其文字为全文
                            dataBean.setContent_state(1);
                        } else {
                            findCommunityHolder.mTvFindCommunityContentState.setVisibility(View.GONE);//显示全文隐藏
                            findCommunityHolder.mTvItemFindCommunityContent.setMaxLines(max_lines);
                            dataBean.setContent_state(3);
                        }
                        return true;
                    }
                });
            } else {
//            如果之前已经初始化过了，则使用保存的状态，无需在获取一次
                switch (dataBean.getContent_state()) {
                    case 3:
                        findCommunityHolder.mTvFindCommunityContentState.setVisibility(View.GONE);
                        break;
                    case 1:
                        findCommunityHolder.mTvItemFindCommunityContent.setMaxLines(max_lines);
                        findCommunityHolder.mTvFindCommunityContentState.setVisibility(View.VISIBLE);
                        findCommunityHolder.mTvFindCommunityContentState.setText("全文");
                        break;
                    case 2:
                        findCommunityHolder.mTvItemFindCommunityContent.setMaxLines(Integer.MAX_VALUE);
                        findCommunityHolder.mTvFindCommunityContentState.setVisibility(View.VISIBLE);
                        findCommunityHolder.mTvFindCommunityContentState.setText("收起");
                        break;
                }
            }

            findCommunityHolder.mTvFindCommunityContentState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int content_state = dataBean.getContent_state();
                    if (content_state == 1) {
                        findCommunityHolder.mTvItemFindCommunityContent.setMaxLines(Integer.MAX_VALUE);
                        findCommunityHolder.mTvFindCommunityContentState.setText("收起");
                        dataBean.setContent_state(2);
                    } else if (content_state == 2) {
                        findCommunityHolder.mTvItemFindCommunityContent.setMaxLines(max_lines);
                        findCommunityHolder.mTvFindCommunityContentState.setText("全文");
                        dataBean.setContent_state(1);
                    }
                }
            });
        }
    }

    public class FindCommunityHolder {
        private ImageView mImgItemFindCommunityHead;
        private TextView mTvItemFindCommunityName;
        private TextView mTvItemFindCommunityTime;
        private TextView mTvItemFindCommunityContent;
        private NineGridlayout mNineItemFindCommunity;//九宫格图片
        private TextView mTvFindCommunityContentState;//展开
        private ConstraintLayout mConstraintItemAllDynamicShare;
        private TextView mTvIteamAllDynamicShare;
        private ImageView mImgIteamAllDynamicShare;
        private ConstraintLayout mConstraintItemAllDynamicComment;
        private TextView mTvIteamAllDynamicComment;
        private ImageView mImgIteamAllDynamicComment;
        private ConstraintLayout mConstraintItemAllDynamicThumb;
        private TextView mTvIteamAllDynamicThumb;
        private ImageView mImgIteamAllDynamicThumb;
        private RelativeLayout mRlItemFindCommunityRunRecord;
        private ImageView mImgItemFindCommunityRunCard;
        private TextView mTvItemFindCommunityRunIntro;
        private TextView mTvItemFindCommunityRunTime;
        private TextView mTvItemFindCommunityCity;
        private ImageView mImgItemFindCommunityOperate;

        public FindCommunityHolder(View view) {
            mImgItemFindCommunityHead = (ImageView) view.findViewById(R.id.img_item_find_community_head);
            mTvItemFindCommunityName = (TextView) view.findViewById(R.id.tv_item_find_community_name);
            mTvItemFindCommunityTime = (TextView) view.findViewById(R.id.tv_item_find_community_time);
            mTvItemFindCommunityContent = (TextView) view.findViewById(R.id.tv_item_find_community_content);
            mNineItemFindCommunity = (NineGridlayout) view.findViewById(R.id.nine_item_find_community);
            mTvFindCommunityContentState = (TextView) view.findViewById(R.id.tv_find_community_content_state);
            mConstraintItemAllDynamicShare = (ConstraintLayout) view.findViewById(R.id.constraint_item_all_dynamic_share);
            mTvIteamAllDynamicShare = (TextView) view.findViewById(R.id.tv_item_dynamic_share);
            mImgIteamAllDynamicShare = (ImageView) view.findViewById(R.id.img_item_dynamic_share);
            mConstraintItemAllDynamicComment = (ConstraintLayout) view.findViewById(R.id.constraint_item_all_dynamic_comment);
            mTvIteamAllDynamicComment = (TextView) view.findViewById(R.id.tv_item_dynamic_comment_count);
            mImgIteamAllDynamicComment = (ImageView) view.findViewById(R.id.img_item_dynamic_comment);
            mConstraintItemAllDynamicThumb = (ConstraintLayout) view.findViewById(R.id.constraint_item_all_dynamic_thumb);
            mTvIteamAllDynamicThumb = (TextView) view.findViewById(R.id.tv_item_dynamic_thumb);
            mImgIteamAllDynamicThumb = (ImageView) view.findViewById(R.id.img_item_dynamic_thumb);
            mRlItemFindCommunityRunRecord = (RelativeLayout) view.findViewById(R.id.rl_item_find_community_run_record);
            mImgItemFindCommunityRunCard = (ImageView) view.findViewById(R.id.img_item_find_community_run_card);
            mTvItemFindCommunityRunIntro = (TextView) view.findViewById(R.id.tv_item_find_community_run_intro);
            mTvItemFindCommunityRunTime = (TextView) view.findViewById(R.id.tv_item_find_community_run_time);
            mTvItemFindCommunityCity = (TextView) view.findViewById(R.id.tv_item_find_community_city);
            mImgItemFindCommunityOperate = (ImageView) view.findViewById(R.id.img_item_find_community_operate);

        }
    }
}
