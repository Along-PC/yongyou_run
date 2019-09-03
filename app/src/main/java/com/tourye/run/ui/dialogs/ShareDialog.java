package com.tourye.run.ui.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.utils.ShareUtils;

import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * @ClassName: ShareDialog
 * @Author: along
 * @Description: 分享弹窗
 * @CreateDate: 2019/5/22 3:22 PM
 */
public class ShareDialog extends BaseDialog implements View.OnClickListener {
    private final Context mContext;
    private LinearLayout mLlDialogShareWechat;
    private LinearLayout mLlDialogShareWechatMoment;
    private LinearLayout mLlDialogShareQq;
    private LinearLayout mLlDialogShareQqZone;
    private LinearLayout mLlDialogShareSina;
    private LinearLayout mLlDialogShare;
    private LinearLayout mLlDialogShareDownload;
    private TextView mTvDialogShareCancel;

    private String mTitle = "";
    private String mDesc = "";
    private String mLink = "";
    private String mImgUrl = "";

    public ShareDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void initData() {

    }

    public void setData(String title, String desc, String link, String imgUrl) {
        mTitle = title;
        mDesc = desc;
        mLink = link;
        mImgUrl = imgUrl;
    }

    @Override
    protected void initView() {
        mLlDialogShareWechat = (LinearLayout) findViewById(R.id.ll_dialog_share_wechat);
        mLlDialogShareWechatMoment = (LinearLayout) findViewById(R.id.ll_dialog_share_wechat_moment);
        mLlDialogShareQq = (LinearLayout) findViewById(R.id.ll_dialog_share_qq);
        mLlDialogShareQqZone = (LinearLayout) findViewById(R.id.ll_dialog_share_qq_zone);
        mLlDialogShareSina = (LinearLayout) findViewById(R.id.ll_dialog_share_sina);
        mLlDialogShare = (LinearLayout) findViewById(R.id.ll_dialog_share);
        mLlDialogShareDownload = (LinearLayout) findViewById(R.id.ll_dialog_share_download);
        mTvDialogShareCancel = (TextView) findViewById(R.id.tv_dialog_share_cancel);

        mTvDialogShareCancel.setOnClickListener(this);
        mLlDialogShareWechat.setOnClickListener(this);
        mLlDialogShareWechatMoment.setOnClickListener(this);

    }

    @Override
    protected int getRootView() {
        return R.layout.dialog_share;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dialog_share_cancel:
                dismiss();
                break;
            case R.id.ll_dialog_share_wechat:
                ShareUtils.getInstance().share(Wechat.NAME, mTitle, mLink, mDesc, mImgUrl, mContext);
                dismiss();
                break;
            case R.id.ll_dialog_share_wechat_moment:
                ShareUtils.getInstance().share(WechatMoments.NAME, mTitle, mLink, mDesc, mImgUrl, mContext);
                dismiss();
                break;
        }
    }
}
