package com.tourye.run.ui.activities.common;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tourye.run.BuildConfig;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.CommonJsonBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.dialogs.DownloadImageDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * CommonWebActivity
 * author:along
 * 2018/10/30 上午10:40
 * <p>
 * 描述:常规webview展示页面
 */

public class CommonWebActivity extends BaseActivity {

    private static final String TAG = "CommonWebActivity";

    private WebView mWebActivityCommonWeb;

    //webview访问相册需要的参数
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 10086;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(mActivity, "图片下载成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(mActivity, "图片下载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void initView() {

        mWebActivityCommonWeb = (WebView) findViewById(R.id.web_activity_common_web);

        mImgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (mWebActivityCommonWeb.canGoBack()) {
            WebBackForwardList webBackForwardList = mWebActivityCommonWeb.copyBackForwardList();
            for (int i = 0; i < webBackForwardList.getSize(); i++) {
                WebHistoryItem itemAtIndex = webBackForwardList.getItemAtIndex(i);
            }
            mWebActivityCommonWeb.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        mTvTitle.setText(title);
        if (!TextUtils.isEmpty(url)) {
            mWebActivityCommonWeb.loadUrl(url);
            mWebActivityCommonWeb.getSettings().setJavaScriptEnabled(true);
            mWebActivityCommonWeb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            mWebActivityCommonWeb.getSettings().setDomStorageEnabled(true);
            //添加代理方便h5作识别
            String userAgentString = mWebActivityCommonWeb.getSettings().getUserAgentString();
            userAgentString = userAgentString + " Bairipao/" + BuildConfig.VERSION_NAME;
            mWebActivityCommonWeb.getSettings().setUserAgentString(userAgentString);
            // android 5.0以上默认不支持Mixed Content 加载https有问题
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mWebActivityCommonWeb.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
            }
            mWebActivityCommonWeb.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    //7。0之上需要更改设置，要不webview不显示
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        String url = request.getUrl().toString();
                        try {
                            if (url.startsWith("http:") || url.startsWith("https:")) {
                                view.loadUrl(url);
                                return true;
                            } else {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(intent);
                                return true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        String url = request.toString();
                        Log.e("along","加载地址："+url);
                        try {
                            if (url.startsWith("http:") || url.startsWith("https:")) {
                                view.loadUrl(url);
                                return true;
                            } else {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(intent);
                                return true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed();
                }
            });
            mWebActivityCommonWeb.setWebChromeClient(new WebChromeClient() {

                //WebChromeClient的几个方法：
                public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                    Log.e(TAG, "openFileChoose(ValueCallback<Uri> uploadMsg)");
                    mUploadMessage = uploadMsg;
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("*/*");
                    mActivity.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
                }

                public void openFileChooser(ValueCallback uploadMsg, String acceptType) {

                    this.openFileChooser(uploadMsg);
                }

                public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                    this.openFileChooser(uploadMsg);
                }

                // For Android 5.0+
                @Override
                public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                    Log.e(TAG, "openFileChoose(ValueCallback<Uri> uploadMsg)");
                    mUploadCallbackAboveL = filePathCallback;
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("*/*");
                    mActivity.startActivityForResult(
                            Intent.createChooser(i, "File Browser"),
                            10086);
                    return true;
                }

            });

            saveImage();
        }
    }

    public void saveImage() {
        mWebActivityCommonWeb.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final WebView.HitTestResult hitTestResult = mWebActivityCommonWeb.getHitTestResult();
                if (hitTestResult.getType() != WebView.HitTestResult.IMAGE_TYPE) {
                    return false;
                }
                DownloadImageDialog downloadImageDialog = new DownloadImageDialog(mActivity);
                downloadImageDialog.setDownloadCallback(new DownloadImageDialog.DownloadCallback() {
                    @Override
                    public void download() {
                        RxPermissions rxPermissions = new RxPermissions(mActivity);
                        rxPermissions
                                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .subscribe(new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean aBoolean) {
                                        if (aBoolean) {
                                            downloadImage(hitTestResult.getExtra());
                                        } else {
                                            Toast.makeText(mActivity, "缺少存储授权", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
                downloadImageDialog.show();
                return false;
            }
        });
    }

    /**
     * 下载图片
     */
    private void downloadImage(String image_url) {
        Glide.with(BaseApplication.mApplicationContext)
                .asBitmap()
                .load(image_url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                        FileOutputStream fileOutputStream = null;
                        try {
                            //系统相册路径
                            String cameraPath = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM + File.separator + "Camera";
                            File file = new File(cameraPath, System.currentTimeMillis() + ".jpg");
                            fileOutputStream = new FileOutputStream(file);
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                            //保存图片后发送广播通知更新数据库
                            Uri uri = Uri.fromFile(file);
                            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                            mHandler.sendEmptyMessage(1);
                        } catch (Exception e) {
                            e.printStackTrace();
                            mHandler.sendEmptyMessage(2);
                        } finally {
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
    }

    /**
     * 5。0weview访问相册回调专用
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                        Log.e(TAG, "onActivityResultAboveL: " + results[i].getPath());
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
                Log.e(TAG, "onActivityResultAboveL: " + results.length);
            }
        }
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
    }

    @Override
    public int getRootView() {
        return R.layout.activity_common_web;
    }

}
