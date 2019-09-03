package com.tourye.run.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by longlongren on 2018/9/1.
 * <p>
 * introduce:没有网络时，弹窗显示，当界面上有弹框时不要重复创建
 */

public class NoneNetUtils {

    private static AlertDialog.Builder mBuilder;
    private static AlertDialog mDialog;

    public static void showDialog(final Context context) {
        if (mDialog == null) {
            mBuilder = new AlertDialog.Builder(context);
            mBuilder.setMessage("请开启网络");
            mBuilder.setTitle("网络异常");
            mBuilder.setPositiveButton("前往", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mDialog = null;//置空弹款对象，方便下次吊起
                    //跳转网络设置界面
                    Intent intent = null;
                    if(android.os.Build.VERSION.SDK_INT > 10){  // 3.0以上
                        intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    }else{
                        intent = new Intent();
                        intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
                    }
                    context.startActivity(intent);
                }
            });
            mBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mDialog = null;//置空弹款对象，方便下次吊起
                    dialogInterface.dismiss();
                }
            });
            mDialog = mBuilder.create();

            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        }
    }
}
