package com.tourye.run.ui.activities.community;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.CommonBean;
import com.tourye.run.bean.CommonJsonBean;
import com.tourye.run.bean.DynamicDetailBean;
import com.tourye.run.bean.DynamicListBean;
import com.tourye.run.bean.ThumbUpBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.common.SingleImageActivity;
import com.tourye.run.ui.adapter.CommunityDetailImageAdapter;
import com.tourye.run.ui.dialogs.DeleteDynamicDialog;
import com.tourye.run.ui.dialogs.DynamicOperateDialog;
import com.tourye.run.ui.dialogs.ShareDialog;
import com.tourye.run.ui.fragments.AllDynamicFragment;
import com.tourye.run.ui.fragments.BaseCommunityFragment;
import com.tourye.run.ui.fragments.CommunityCommentFragment;
import com.tourye.run.ui.adapter.CommunityDetailVpAdapter;
import com.tourye.run.utils.DateFormatUtils;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.GlideCircleTransform;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;
import com.tourye.run.views.MeasureGridView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CommunityDetailActivity
 * author:along
 * 2018/10/8 上午10:24
 * <p>
 * 描述:社区详情页面
 */

public class CommunityDetailActivity extends BaseActivity implements View.OnClickListener {
    private AppBarLayout mAppbarActivityCommunityDetail;//顶部toolbar父控件
    private ImageView mImgCommunityDetailHead;//头像
    private TextView mTvCommunityDetailName;//昵称
    private TextView mTvCommunityDetailContent;//动态的文字
    private MeasureGridView mGridCommunityDetail;//动态的图片列表
    private RelativeLayout mRlCommunityDetailShare;//分享模块
    private TextView mTvItemFindCommunityShare;
    private RelativeLayout mRlCommunityDetailComment;//评论模块
    private TextView mTvItemFindCommunityComment;
    private RelativeLayout mRlCommunityDetailThumb;//点赞模块
    private TextView mTvFindCommunityThumb;
    private TabLayout mTabCommunityDetail;//导航栏
    private ViewPager mVpCommunityDetail;
    private TextView mTvCommunityDetailTime;
    private ImageView mImgCommunityDetailThumb;
    private TextView mTvCommunityDetailDelete;//删除动态
    private RelativeLayout mRlCommunityDetailRunRecord;
    private ImageView mImgCommunityDetailRunCard;
    private TextView mTvCommunityDetailRunIntro;
    private TextView mTvCommunityDetailRunTime;

    private static final String TAG = "CommunityDetailActivity";
    private CommunityDetailImageAdapter mCommunityDetailImageAdapter;

    private BaseCommunityFragment mCommunityThumbFragment;//点赞页面
    private BaseCommunityFragment mCommunityCommentFragment;//评论页面
    private List<BaseCommunityFragment> mBaseCommunityFragments = new ArrayList<>();
    private FragmentManager mSupportFragmentManager;
    private CommunityDetailVpAdapter mCommunityDetailVpAdapter;

    private int mPunchId;//动态的id
    private TabLayout.Tab mTabComment;
    private int mComment_count;//评论的数量
    private DynamicDetailBean.DataBean mDataBean;//数据实体
    private int mDynamic_id;
    private int mUser_id;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView() {

        mAppbarActivityCommunityDetail = (AppBarLayout) findViewById(R.id.appbar_activity_community_detail);
        mImgCommunityDetailHead = (ImageView) findViewById(R.id.img_community_detail_head);
        mTvCommunityDetailName = (TextView) findViewById(R.id.tv_community_detail_name);
        mTvCommunityDetailContent = (TextView) findViewById(R.id.tv_community_detail_content);
        mGridCommunityDetail = (MeasureGridView) findViewById(R.id.grid_community_detail);
        mRlCommunityDetailShare = (RelativeLayout) findViewById(R.id.rl_community_detail_share);
        mTvItemFindCommunityShare = (TextView) findViewById(R.id.tv_item_find_community_share);
        mRlCommunityDetailComment = (RelativeLayout) findViewById(R.id.rl_community_detail_comment);
        mTvItemFindCommunityComment = (TextView) findViewById(R.id.tv_item_find_community_comment);
        mRlCommunityDetailThumb = (RelativeLayout) findViewById(R.id.rl_community_detail_thumb);
        mTvFindCommunityThumb = (TextView) findViewById(R.id.tv_item_find_community_thumb);
        mTabCommunityDetail = (TabLayout) findViewById(R.id.tab_community_detail);
        mVpCommunityDetail = (ViewPager) findViewById(R.id.vp_community_detail);
        mTvCommunityDetailTime = (TextView) findViewById(R.id.tv_community_detail_time);
        mImgCommunityDetailThumb = (ImageView) findViewById(R.id.img_community_detail_thumb);
        mTvCommunityDetailDelete = (TextView) findViewById(R.id.tv_community_detail_delete);
        mRlCommunityDetailRunRecord = (RelativeLayout) findViewById(R.id.rl_community_detail_run_record);
        mImgCommunityDetailRunCard = (ImageView) findViewById(R.id.img_community_detail_run_card);
        mTvCommunityDetailRunIntro = (TextView) findViewById(R.id.tv_community_detail_run_intro);
        mTvCommunityDetailRunTime = (TextView) findViewById(R.id.tv_community_detail_run_time);

        mRlCommunityDetailComment.setOnClickListener(this);
        mTvCommunityDetailDelete.setOnClickListener(this);
        mRlCommunityDetailShare.setOnClickListener(this);

        mTvTitle.setText("动态详情");
        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mImgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    /**
     * 初始化消息图片的数据
     */
    public void initGridData(ArrayList<String> images) {
        mCommunityDetailImageAdapter = new CommunityDetailImageAdapter(mActivity, images);
        int size = images.size();
        switch (size) {
            case 1:
                mGridCommunityDetail.setNumColumns(1);
                break;
            case 2:
            case 3:
            case 4:
                mGridCommunityDetail.setNumColumns(2);
                break;
            default:
                mGridCommunityDetail.setNumColumns(3);
                break;
        }
        mGridCommunityDetail.setAdapter(mCommunityDetailImageAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //注册event-bus
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //解除注册event-bus
        EventBus.getDefault().unregister(this);
    }

    //更新评论数量
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateCommentCount(AllDynamicFragment.CommentCountBean commentCountBean) {
        mComment_count = mComment_count + commentCountBean.getUpdate_comment_count();
        mTabComment.setText("评论(" + mComment_count + ")");
        if (mComment_count == 0) {
            mTvItemFindCommunityComment.setText("评论");
        } else {
            mTvItemFindCommunityComment.setText("" + mComment_count);
        }

    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        mDynamic_id = intent.getIntExtra("dynamic_id", -1);
        Map<String, String> map = new HashMap<>();
        map.put("id", mDynamic_id + "");
        HttpUtils.getInstance().get(Constants.DYNAMIC_DETAIL, map, new HttpCallback<DynamicDetailBean>() {
            @Override
            public void onSuccessExecute(DynamicDetailBean dynamicDetailBean) {
                DynamicDetailBean.DataBean data = dynamicDetailBean.getData();
                if (data != null) {
                    mDataBean = data;
                    //头像
                    RequestOptions requestOptions = new RequestOptions().transform(new GlideCircleTransform(BaseApplication.mApplicationContext));
                    Glide.with(BaseApplication.mApplicationContext).load(mDataBean.getAvatar()).apply(requestOptions).into(mImgCommunityDetailHead);
                    //姓名
                    mTvCommunityDetailName.setText(mDataBean.getNickname());
                    //文字内容
                    String content = mDataBean.getContent();
                    if (TextUtils.isEmpty(content)) {
                        mTvCommunityDetailContent.setVisibility(View.GONE);
                    } else {
                        mTvCommunityDetailContent.setText(content);
                    }
                    mPunchId = mDataBean.getId();
                    //发表时间
                    String create_time = mDataBean.getCreate_time();
                    if (!TextUtils.isEmpty(create_time)) {
                        formatTime(mTvCommunityDetailTime, create_time);
                    } else {
                        mTvCommunityDetailTime.setVisibility(View.GONE);
                    }
                    //点赞状态
                    mImgCommunityDetailThumb.setSelected(mDataBean.isAlready_like());
                    int like_count = mDataBean.getLike_count();
                    if (like_count == 0) {
                        mTvFindCommunityThumb.setText("赞");
                    } else {
                        mTvFindCommunityThumb.setText(mDataBean.getLike_count() + "");
                    }
                    //顶部图片数据
                    ArrayList<String> images = mDataBean.getImages();
                    if (images != null && images.size() > 0) {
                        initGridData(mDataBean.getImages());
                    }
                    //是否是自己的动态，是的话可以删除,不是的话可以举报
                    mUser_id = mDataBean.getUser_id();
                    //底部跑步状态
                    if (mDataBean.getSign_in_data() != null) {
                        mRlCommunityDetailRunRecord.setVisibility(View.VISIBLE);
                        final DynamicDetailBean.DataBean.SignInDataBean sign_in_data = mDataBean.getSign_in_data();
                        GlideUtils.getInstance().loadRoundImage(sign_in_data.getImage(), mImgCommunityDetailRunCard);
                        mImgCommunityDetailRunCard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mActivity, SingleImageActivity.class);
                                intent.putExtra("url", sign_in_data.getImage());
                                startActivity(intent);
                            }
                        });
                        mTvCommunityDetailRunTime.setText("完成" + sign_in_data.getDistance() + "公里，用时" + sign_in_data.getTime() + "分");
                        if (mDataBean.getTotal_days() > 0) {
                            mTvCommunityDetailRunIntro.setVisibility(View.VISIBLE);
                            mTvCommunityDetailRunIntro.setText("百日跑坚持打卡已" + mDataBean.getTotal_days() + "天");
                        } else {
                            mTvCommunityDetailRunIntro.setVisibility(View.GONE);
                        }
                    } else {
                        mRlCommunityDetailRunRecord.setVisibility(View.GONE);
                    }

                    mComment_count = mDataBean.getComment_count();//评论数
                    if (mComment_count == 0) {
                        mTvItemFindCommunityComment.setText("评论");
                    } else {
                        mTvItemFindCommunityComment.setText("" + mComment_count);
                    }

                    initTabData(mDataBean.getComment_count());
                    initVp();

                    mImgCommunityDetailThumb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mImgCommunityDetailThumb.setOnClickListener(null);
                            if (mImgCommunityDetailThumb.isSelected()) {
                                cancel_thumb_up(mDataBean);
                            } else {
                                thumb_up(mDataBean);
                            }
                        }
                    });
                }
            }
        });

    }

    //取消点赞
    public void cancel_thumb_up(final DynamicDetailBean.DataBean dataBean) {
        Map<String, String> map = new HashMap<>();
        map.put("id", dataBean.getId() + "");
        HttpUtils.getInstance().post(Constants.THUMB_UP_CANCEL, map, new HttpCallback<ThumbUpBean>() {
            @Override
            public void onSuccessExecute(ThumbUpBean thumbUpBean) {
                if (thumbUpBean.getStatus() == 0) {
                    if (thumbUpBean.isData()) {
                        mImgCommunityDetailThumb.setSelected(false);
                        dataBean.setLike_count(dataBean.getLike_count() - 1);
                        dataBean.setAlready_like(false);
                        //发送状态更改事件，刷新列表
                        AllDynamicFragment.Msg msg = new AllDynamicFragment.Msg();
                        msg.setDynamic_id(mDynamic_id);
                        msg.setIs_already_thumb(false);
                        EventBus.getDefault().post(msg);
                        //拿到网络请求结果，重新添加监听
                        if (dataBean.getLike_count() == 0) {
                            mTvFindCommunityThumb.setText("赞");
                        } else {
                            mTvFindCommunityThumb.setText(dataBean.getLike_count() + "");
                        }
                        mImgCommunityDetailThumb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mImgCommunityDetailThumb.setOnClickListener(null);
                                if (mImgCommunityDetailThumb.isSelected()) {
                                    cancel_thumb_up(dataBean);
                                } else {
                                    thumb_up(dataBean);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * 格式化时间
     *
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
            int secondUnit = 1000;
            int minuteUnit = secondUnit * 60;
            int hourUnit = minuteUnit * 60;
            int dayUnit = hourUnit * 24;
            if (timeDiscrepancy < minuteUnit) {
                tvItemFindCommunityTime.setText("刚刚");
            } else if (timeDiscrepancy >= minuteUnit && timeDiscrepancy < hourUnit) {
                int minutes = (int) Math.floor(timeDiscrepancy / minuteUnit);
                tvItemFindCommunityTime.setText(minutes + "分钟前");
            } else if (timeDiscrepancy >= hourUnit && timeDiscrepancy < dayUnit) {
                int hours = (int) Math.floor(timeDiscrepancy / hourUnit);
                tvItemFindCommunityTime.setText(hours + "小时前");
            } else if (timeDiscrepancy >= dayUnit && timeDiscrepancy < dayUnit * 3) {
                int days = (int) Math.floor(timeDiscrepancy / dayUnit);
                tvItemFindCommunityTime.setText(days + "天前");
            } else {
                tvItemFindCommunityTime.setText(DateFormatUtils.format(create_time));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //点赞
    public void thumb_up(final DynamicDetailBean.DataBean dataBean) {
        Map<String, String> map = new HashMap<>();
        map.put("id", dataBean.getId() + "");
        HttpUtils.getInstance().post(Constants.THUMB_UP, map, new HttpCallback<ThumbUpBean>() {
            @Override
            public void onSuccessExecute(ThumbUpBean thumbUpBean) {
                if (thumbUpBean.getStatus() == 0) {
                    if (thumbUpBean.isData()) {
                        mImgCommunityDetailThumb.setSelected(true);
                        dataBean.setAlready_like(true);
                        int thumb_up_count = dataBean.getLike_count();
                        dataBean.setLike_count(thumb_up_count + 1);
                        //发送状态更改事件，刷新列表
                        AllDynamicFragment.Msg msg = new AllDynamicFragment.Msg();
                        msg.setDynamic_id(mDynamic_id);
                        msg.setIs_already_thumb(true);
                        EventBus.getDefault().post(msg);
                        //拿到网络请求结果，重新添加监听
                        if (dataBean.getLike_count() == 0) {
                            mTvFindCommunityThumb.setText("赞");
                        } else {
                            mTvFindCommunityThumb.setText(dataBean.getLike_count() + "");
                        }
                        mImgCommunityDetailThumb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mImgCommunityDetailThumb.setOnClickListener(null);
                                if (mImgCommunityDetailThumb.isSelected()) {
                                    cancel_thumb_up(dataBean);
                                } else {
                                    thumb_up(dataBean);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * 初始化viewpager
     */
    private void initVp() {
        mCommunityCommentFragment = new CommunityCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("data", mPunchId);
        mCommunityCommentFragment.setArguments(bundle);
        mBaseCommunityFragments.add(mCommunityCommentFragment);
        mSupportFragmentManager = getSupportFragmentManager();
        mCommunityDetailVpAdapter = new CommunityDetailVpAdapter(mSupportFragmentManager);
        mCommunityDetailVpAdapter.setFragments(mBaseCommunityFragments);
        mVpCommunityDetail.setAdapter(mCommunityDetailVpAdapter);
        mVpCommunityDetail.setCurrentItem(0);
        mVpCommunityDetail.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabCommunityDetail.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化导航栏
     *
     * @param num 评论数
     */
    private void initTabData(int num) {
//        tabLayout.addTab(tabLayout.newTab().setText("赞(300)"));
        mTabComment = mTabCommunityDetail.newTab();
        mTabComment.setText("评论(" + num + ")");
        mTabCommunityDetail.addTab(mTabComment);
        mTabCommunityDetail.getTabAt(0).select();
        mTabCommunityDetail.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    mVpCommunityDetail.setCurrentItem(0);
                } else {
                    mVpCommunityDetail.setCurrentItem(1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086 && resultCode == 110) {
            String comment = data.getStringExtra("comment");
            mCommunityCommentFragment.addComment(comment);
        }
    }

    /**
     * 举报动态
     *
     * @param dynamicId 动态id
     */
    private void report(int dynamicId) {
        Map<String, String> map = new HashMap<>();
        map.put("id", dynamicId + "");
        HttpUtils.getInstance().post(Constants.REPORT_DYNAMIC, map, new HttpCallback<CommonJsonBean>() {
            @Override
            public void onSuccessExecute(CommonJsonBean commonJsonBean) {
                if (commonJsonBean.getStatus() == 0) {
                    Toast.makeText(mActivity, "举报成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.activity_community_detail;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_community_detail_comment:
                Intent intent = new Intent(mActivity, CommentActivity.class);
                intent.putExtra("dynamic_id", mPunchId);
                intent.putExtra("type", 2);
                intent.putExtra("reply_name", mDataBean.getNickname());
                startActivityForResult(intent, 10086);
                break;
            case R.id.tv_community_detail_delete:
                DynamicOperateDialog dynamicOperateDialog = new DynamicOperateDialog(mActivity);
                int userId = SaveUtil.getInt("user_id", -998);
                if (mUser_id == userId) {
                    dynamicOperateDialog.setOnClickListener(new DynamicOperateDialog.OnClickListener() {
                        @Override
                        public void onClick() {
                            DeleteDynamicDialog deleteDynamicDialog = new DeleteDynamicDialog(mActivity);
                            deleteDynamicDialog.setOnDeleteInterface(new DeleteDynamicDialog.OnDeleteInterface() {
                                @Override
                                public void delete() {
                                    deleteDynamic();
                                }
                            });
                            deleteDynamicDialog.show();
                        }
                    }, "删除");
                } else {
                    dynamicOperateDialog.setOnClickListener(new DynamicOperateDialog.OnClickListener() {
                        @Override
                        public void onClick() {
                            report(mPunchId);
                        }
                    }, "举报");
                }
                dynamicOperateDialog.showAsDropDown(mTvCommunityDetailDelete, -DensityUtils.dp2px(67), 0);
                break;
            case R.id.rl_community_detail_share:
                String link = Constants.DOMAIN + "?"
                        + "#/community/" + mDynamic_id;
                ShareDialog shareDialog = new ShareDialog(mActivity);
                shareDialog.setData("这是["+mDataBean.getNickname()+"]发布的百日跑社区动态，快来瞧瞧", "万人坚持跑步打卡100天，看到坚持的力量",
                        link, "https://static.run100.runorout.cn/meta/logo2.png");
                shareDialog.show();
                break;
        }
    }

    /**
     * 删除动态
     */
    private void deleteDynamic() {
        Map<String, String> map = new HashMap<>();
        map.put("id", mDataBean.getId() + "");
        HttpUtils.getInstance().post(Constants.DELETE_DYNAMIC, map, new HttpCallback<CommonBean>() {
            @Override
            public void onSuccessExecute(CommonBean commonBean) {
                if (commonBean.getStatus() == 0) {
                    //刷新列表
                    AllDynamicFragment.UpdateDynamicBean updateDynamicBean = new AllDynamicFragment.UpdateDynamicBean();
                    updateDynamicBean.setUpdate_list(false);
                    updateDynamicBean.setDynamicId(mPunchId);
                    EventBus.getDefault().post(updateDynamicBean);

                    Toast.makeText(mActivity, "删除成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(mActivity, "删除失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
