package com.tourye.run.ui.activities.mine;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.CommonJsonBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.utils.SaveUtil;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @ClassName:   PrizeSigningActivity
 *
 * @Author:   along
 *
 * @Description:    奖金签约页面---上上签
 *
 * @CreateDate:   2019/4/25 1:14 PM
 *
 */
public class PrizeSigningActivity extends BaseActivity {

    private WebView mWebActivityPrizeSigning;

    @Override
    public void initView() {
        mWebActivityPrizeSigning = (WebView) findViewById(R.id.web_activity_prize_signing);

        mTvTitle.setText("");

        mImgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWebActivityPrizeSigning.canGoBack()) {
                    mWebActivityPrizeSigning.goBack();
                    return;
                }
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mWebActivityPrizeSigning.canGoBack()) {
            mWebActivityPrizeSigning.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void initData() {

        Map<String,String> map=new HashMap<>();
        map.put("id",SaveUtil.getString(SaveConstants.ACTION_ID,""));
        HttpUtils.getInstance().get(Constants.CONTRACT_SIGN_URL, map, new HttpCallback<CommonJsonBean>() {
            @Override
            public void onSuccessExecute(CommonJsonBean commonJsonBean) {
                if (commonJsonBean.getStatus()==0) {
                    mWebActivityPrizeSigning.loadUrl(commonJsonBean.getData());
                    mWebActivityPrizeSigning.getSettings().setJavaScriptEnabled(true);
                    mWebActivityPrizeSigning.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                    mWebActivityPrizeSigning.getSettings().setDomStorageEnabled(true);
                    mWebActivityPrizeSigning.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                            //7。0之上需要更改设置，要不webview不显示
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                view.loadUrl(request.getUrl().toString());
                            } else {
                                view.loadUrl(request.toString());
                            }
                            return true;
                        }

                        @Override
                        public void onLoadResource(WebView view, String url) {
                            super.onLoadResource(view, url);
                            if (url.contains("#/user/withdraw/1")) {
                                Intent intent = new Intent(mActivity,BonusWithdrawalActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                return;
                            }
                        }
                    });
                }
            }
        });

    }

    @Override
    public int getRootView() {
        return R.layout.activity_prize_signing;
    }

}
