package com.tourye.run.ui.dialogs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import com.tourye.run.R;
import com.tourye.run.ui.activities.common.CommonWebActivity;

/**
 * @ClassName: UserProtocolDialog
 * @Author: along
 * @Description: 用户协议弹窗
 * @CreateDate: 2019/8/5 4:38 PM
 */
public class UserProtocolDialog extends BaseDialog {

    private TextView mTvDialogUserProtocolContent;
    private TextView mTvDialogUserProtocolDisagree;
    private TextView mTvDialogUserProtocolAgree;

    private OnOperateListener mOnOperateListener;

    public UserProtocolDialog(Context context) {
        super(context);
    }

    public void setOnOperateListener(OnOperateListener onOperateListener) {
        mOnOperateListener = onOperateListener;
    }

    @Override
    protected void initData() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append("欢迎使用百日跑，百日跑非常重视您的隐私和信息保护，请认真阅读《百日跑用户协议》，同意后才能使用。");
        //设置部分文字点击事件
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                String url="https://mp.weixin.qq.com/s/T4AutF1H8eCaBeifHk3pnQ";
                Intent intent = new Intent(mContext, CommonWebActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("title","用户协议");
                mContext.startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.parseColor("#FFFF1D1D"));
                //取消下划线
                ds.setUnderlineText(false);
            }
        };
        spannableStringBuilder.setSpan(clickableSpan, 30, 39, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置部分文字颜色
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#FFFF1D1D"));
        spannableStringBuilder.setSpan(foregroundColorSpan, 30, 39, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //文字背景颜色
        BackgroundColorSpan bgColorSpan = new BackgroundColorSpan(Color.parseColor("#00000000"));
        spannableStringBuilder.setSpan(bgColorSpan, 30, 39, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //配置给TextView
        mTvDialogUserProtocolContent.setText(spannableStringBuilder);
        //设置这个点击事件才生效
        mTvDialogUserProtocolContent.setMovementMethod(LinkMovementMethod.getInstance());
        //设置局部点击后背景更换的问题
        mTvDialogUserProtocolContent.setHighlightColor(Color.TRANSPARENT);
    }

    @Override
    protected void initView() {
        mTvDialogUserProtocolContent = (TextView) findViewById(R.id.tv_dialog_user_protocol_content);
        mTvDialogUserProtocolDisagree = (TextView) findViewById(R.id.tv_dialog_user_protocol_disagree);
        mTvDialogUserProtocolAgree = (TextView) findViewById(R.id.tv_dialog_user_protocol_agree);

        mTvDialogUserProtocolDisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnOperateListener!=null) {
                    mOnOperateListener.onDisagree();
                }
            }
        });

        mTvDialogUserProtocolAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnOperateListener!=null) {
                    mOnOperateListener.onAgraee();
                }
            }
        });

        setCancelable(false);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected boolean isNeedAnimation() {
        return false;
    }

    @Override
    public boolean isLocationBottom() {
        return false;
    }

    @Override
    protected boolean isNeedMatchParent() {
        return false;
    }

    @Override
    protected int getRootView() {
        return R.layout.dialog_user_protocol;
    }

    public interface OnOperateListener{

        public void onDisagree();

        public void onAgraee();

    }
}
