package com.tourye.run.ui.activities.community;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.CreateCommentBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.fragments.AllDynamicFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @ClassName:   CommentActivity
 *
 * @Author:   along
 *
 * @Description:    回复评论页面
 *
 * @CreateDate:   2019/4/11 3:37 PM
 *
 */
public class CommentActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEdtActivityCommentReply;
    private TextView mTvActivityCommentCount;
    private TextView mTvActivityCommentSubmit;

    private int type;//1--动态列表进入  2--动态详情进入 3--回复评论   4---回复评论的回复
    private int dynamic_id;//动态的id
    private String reply_name;//回复人的名字

    private  Intent intent;
    private String mCurrent_id;
    private int mPos;
    private int mReply_id;
    private int mReply_to;
    private int mItem_pos;

    @Override
    public void initView() {
        mEdtActivityCommentReply = (EditText) findViewById(R.id.edt_activity_comment_reply);
        mTvActivityCommentCount = (TextView) findViewById(R.id.tv_activity_comment_count);
        mTvActivityCommentSubmit = (TextView) findViewById(R.id.tv_activity_comment_submit);

        mTvActivityCommentSubmit.setOnClickListener(this);
        mEdtActivityCommentReply.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = s.toString();
                mTvActivityCommentCount.setText(temp.length()+"/140");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void initData() {
        intent = getIntent();
        type = intent.getIntExtra("type", 998);
        switch (type) {
            case 1:
                mTvTitle.setText("评论");
                reply_name = intent.getStringExtra("reply_name");
                mEdtActivityCommentReply.setHint("写评论...");
                dynamic_id = intent.getIntExtra("dynamic_id", 998);
                break;
            case 2:
                mTvTitle.setText("评论");
                reply_name = intent.getStringExtra("reply_name");
                mEdtActivityCommentReply.setHint("写评论...");

                break;
            case 3:
                mCurrent_id = intent.getStringExtra("current_id");
                mPos = intent.getIntExtra("pos",-1);

                mTvTitle.setText("回复");
                reply_name = intent.getStringExtra("reply_name");
                mEdtActivityCommentReply.setHint("回复@"+reply_name);

                break;
            case 4:
                mCurrent_id = intent.getStringExtra("current_id");
                mPos = intent.getIntExtra("pos",-1);
                mItem_pos = intent.getIntExtra("item_pos", -1);
                mReply_id = intent.getIntExtra("reply_id", -1);
                mReply_to = intent.getIntExtra("reply_to", -1);
                mTvTitle.setText("回复");
                reply_name = intent.getStringExtra("reply_name");
                mEdtActivityCommentReply.setHint("回复@"+reply_name);
                break;
        }


    }

    /**
     * 新增评论
     */
    public void addComment(final String text) {

        Map<String,String> map=new HashMap<>();
        map.put("id",dynamic_id+"");
        map.put("content",text);
        HttpUtils.getInstance().post(Constants.CREATE_COMMENT, map, new HttpCallback<CreateCommentBean>() {
            @Override
            public void onSuccessExecute(CreateCommentBean createCommentBean) {
                if (createCommentBean.getStatus()==0) {
                    //发送事件，更新动态列表中评论的数量
                    AllDynamicFragment.CommentCountBean commentCountBean = new AllDynamicFragment.CommentCountBean();
                    commentCountBean.setDynamic_id(dynamic_id);
                    commentCountBean.setUpdate_comment_count(1);
                    EventBus.getDefault().post(commentCountBean);
                    //跳转详情页
                    Intent intent = new Intent(mActivity, CommunityDetailActivity.class);
                    intent.putExtra("dynamic_id",dynamic_id);
                    mActivity.startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public int getRootView() {
        return R.layout.activity_comment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_activity_comment_submit:
                String temp= mEdtActivityCommentReply.getText().toString();
                if (TextUtils.isEmpty(temp.trim())) {
                    Toast.makeText(mActivity, "请输入回复内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                switch (type) {
                    case 1:
                        addComment(temp);
                        break;
                    case 2:
                        intent.putExtra("comment",temp);
                        setResult(110,intent);
                        finish();
                        break;
                    case 3:
                        intent.putExtra("current_id",mCurrent_id);
                        intent.putExtra("comment",temp);
                        intent.putExtra("pos",mPos);
                        setResult(110,intent);
                        finish();
                        break;
                    case 4:
                        intent.putExtra("current_id",mCurrent_id);
                        intent.putExtra("comment",temp);
                        intent.putExtra("pos",mPos);
                        intent.putExtra("item_pos",mItem_pos);
                        setResult(110,intent);
                        finish();
                        break;
                }
                break;
        }
    }
}
