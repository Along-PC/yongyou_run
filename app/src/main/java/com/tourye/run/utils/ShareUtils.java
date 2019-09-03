package com.tourye.run.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tourye.run.base.BaseApplication;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qzone.QZone;

public class ShareUtils {
    private static ShareUtils mShareUtils;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private ShareCallback mShareCallback;

    private static final String TAG = "ShareUtils";
    private ShareUtils(){

    }

    public static ShareUtils getInstance(){
        if (mShareUtils==null) {
            mShareUtils=new ShareUtils();
        }
        return mShareUtils;
    }

    public void share(String platform, String title, String link, String desc, String imgurl, final Context context) {

        if (TextUtils.isEmpty(title)) {
            title="看到坚持的力量";
        }
        if (TextUtils.isEmpty(desc)) {
            desc="想和你过好这一生";
        }

        final OnekeyShare oks = new OnekeyShare();
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        oks.setPlatform(platform);
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(link);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(desc);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博----这个参数不填写效果很诡异
        oks.setImageUrl(imgurl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(link);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment(desc);
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(link);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl(link);

        //分享成功失败的回调
        oks.setCallback(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                mHandler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
                    }
                });
                if (mShareCallback!=null) {
                    mShareCallback.onFailure();
                }
            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                mHandler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
                    }
                });
                if (mShareCallback!=null) {
                    mShareCallback.onSuccess();
                }

            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub
                mHandler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(context, "取消分享", Toast.LENGTH_SHORT).show();
                    }
                });
                if (mShareCallback!=null) {
                    mShareCallback.onCancel();
                }
            }
        });
        //启动分享
        oks.show(context);
    }

    public void shareImage(String platform, String title, String link, String desc, String imgurl, final Context context) {
        if (TextUtils.isEmpty(title)) {
            title="看到坚持的力量";
        }
        if (TextUtils.isEmpty(desc)) {
            desc="想和你过好这一生";
        }
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(title);
        sp.setTitleUrl(link); // 标题的超链接
        sp.setText(desc);
        sp.setImageUrl(imgurl);
        sp.setShareType(Platform.SHARE_IMAGE);
        Platform share = ShareSDK.getPlatform (platform);
        // 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        share.setPlatformActionListener (new PlatformActionListener() {
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                Log.d(TAG, "onError() called with: arg0 = [" + arg0 + "], arg1 = [" + arg1 + "], arg2 = [" + arg2 + "]");
                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                mHandler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
                    }
                });
                if (mShareCallback!=null) {
                    mShareCallback.onFailure();
                }
            }
            public void onComplete(Platform arg0, int arg1, HashMap arg2) {
                //分享成功的回调
                mHandler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
                    }
                });
                if (mShareCallback!=null) {
                    mShareCallback.onSuccess();
                }
            }
            public void onCancel(Platform arg0, int arg1) {
                //取消分享的回调
                mHandler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(context, "取消分享", Toast.LENGTH_SHORT).show();
                    }
                });
                if (mShareCallback!=null) {
                    mShareCallback.onCancel();
                }
            }
        });
        // 执行图文分享
        share.share(sp);

    }


    public ShareUtils setShareCallBack(ShareCallback shareCallback){
        mShareCallback=shareCallback;
        return this;
    }

    public interface ShareCallback {

        public void onSuccess();

        public void onFailure();

        public void onCancel();

    }

}
