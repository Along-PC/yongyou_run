package com.tourye.run.ui.activities.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName:   DigitalProtocolActivity
 *
 * @Author:   along
 *
 * @Description:    数字协议页面
 *
 * @CreateDate:   2019/4/25 9:41 AM
 *
 */
public class DigitalProtocolActivity extends BaseActivity {
    private LinearLayout mLlActivityDigitalProtocol;


    @Override
    public void initView() {
        mLlActivityDigitalProtocol = (LinearLayout) findViewById(R.id.ll_activity_digital_protocol);

        mTvTitle.setText("数字证书使用协议");
    }

    @Override
    public void initData() {
        List<String> protocols=new ArrayList<>();
        protocols.add("https://static.run100.runorout.cn/meta/digi_cert_usage_proto/1.jpg");
        protocols.add("https://static.run100.runorout.cn/meta/digi_cert_usage_proto/2.jpg");
        protocols.add("https://static.run100.runorout.cn/meta/digi_cert_usage_proto/3.jpg");
        protocols.add("https://static.run100.runorout.cn/meta/digi_cert_usage_proto/4.jpg");
        protocols.add("https://static.run100.runorout.cn/meta/digi_cert_usage_proto/5.jpg");

        for (int i = 0; i < protocols.size(); i++) {
            String protocol = protocols.get(i);
            ImageView imageView = new ImageView(mActivity);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(layoutParams);
            GlideUtils.getInstance().loadImage(protocol,imageView);
            mLlActivityDigitalProtocol.addView(imageView);
        }

    }

    @Override
    public int getRootView() {
        return R.layout.activity_digital_protocol;
    }

}
