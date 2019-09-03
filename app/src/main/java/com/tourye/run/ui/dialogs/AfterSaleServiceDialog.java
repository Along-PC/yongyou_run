package com.tourye.run.ui.dialogs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.LogisticsBean;
import com.tourye.run.utils.GlideUtils;

/**
 *
 * @ClassName:   AfterSaleServiceDialog
 *
 * @Author:   along
 *
 * @Description:    售后服务弹窗
 *
 * @CreateDate:   2019/4/29 10:29 AM
 *
 */
public class AfterSaleServiceDialog extends BaseDialog {
    private TextView mTvSaleServiceContent;
    private TextView mTvSaleServicePhone;
    private ImageView mImgSaleServiceQrcode;

    private LogisticsBean.DataBean dataBean;

    public AfterSaleServiceDialog(Context context) {
        super(context);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTvSaleServiceContent = (TextView) findViewById(R.id.tv_sale_service_content);
        mTvSaleServicePhone = (TextView) findViewById(R.id.tv_sale_service_phone);
        mImgSaleServiceQrcode = (ImageView) findViewById(R.id.img_sale_service_qrcode);

    }

    public void setDataBean(LogisticsBean.DataBean dataBean) {
        this.dataBean = dataBean;
        if (dataBean==null) {
            return;
        }
        final String after_sale_text = dataBean.getAfter_sale_text();
        final String after_sale_phone = dataBean.getAfter_sale_phone();
        String after_sale_image = dataBean.getAfter_sale_image();
        if (!TextUtils.isEmpty(after_sale_text)) {
            mTvSaleServiceContent.setText(Html.fromHtml(after_sale_text));
        }else{
            mTvSaleServiceContent.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(after_sale_phone)) {
            mTvSaleServicePhone.setText(after_sale_phone);
            mTvSaleServicePhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent dialIntent =  new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+after_sale_phone ));//跳转到拨号界面，同时传递电话号码
                    mContext.startActivity(dialIntent);
                }
            });
        }else{
            mTvSaleServicePhone.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(after_sale_image)) {
            GlideUtils.getInstance().loadImage(after_sale_image,mImgSaleServiceQrcode);
        }else{
            mImgSaleServiceQrcode.setVisibility(View.GONE);
        }

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
        return R.layout.dialog_after_sale_service;
    }
}
