package com.tourye.run.ui.activities.mine;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.ui.activities.LoginActivity;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;

import java.io.File;

/**
 *
 * @ClassName:   FunctionSettingActivity
 *
 * @Author:   along
 *
 * @Description:功能设置页面
 *
 * @CreateDate:   2019/3/14 1:38 PM
 *
 */
public class FunctionSettingActivity extends BaseActivity implements View.OnClickListener {
    private TextView mActivityFunctionSettingTvQuit;
    private LinearLayout mLlActivityFunctionSettingCache;
    private TextView mTvActivityFunctionSettingCache;
    private TextView mTvActivityFunctionSettingVersion;

    @Override
    public void initView() {
        mActivityFunctionSettingTvQuit = (TextView) findViewById(R.id.activity_function_setting_tv_quit);
        mLlActivityFunctionSettingCache = (LinearLayout) findViewById(R.id.ll_activity_function_setting_cache);
        mTvActivityFunctionSettingCache = (TextView) findViewById(R.id.tv_activity_function_setting_cache);
        mTvActivityFunctionSettingVersion = (TextView) findViewById(R.id.tv_activity_function_setting_version);

        mActivityFunctionSettingTvQuit.setOnClickListener(this);
        mLlActivityFunctionSettingCache.setOnClickListener(this);
        mTvTitle.setText("功能设置");
    }

    @Override
    public void initData() {

        //获取包管理器
        PackageManager pm = mActivity.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(mActivity.getPackageName(), 0);
            //版本号
           mTvActivityFunctionSettingVersion.setText(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //缓存大小
        try {
            long folderSize = GlideUtils.getInstance().getFolderSize(new File(BaseApplication.mApplicationContext.getCacheDir() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR));
            String formatSize = GlideUtils.getInstance().getFormatSize(folderSize);
            mTvActivityFunctionSettingCache.setText(formatSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除缓存
     */
    private void clearCache() {
        AlertDialog dialog = new AlertDialog.Builder(mActivity)
                .setMessage("确定清除缓存？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(FunctionSettingActivity.this, "清除成功", Toast.LENGTH_SHORT).show();
                        new Thread(){
                            @Override
                            public void run() {
                                super.run();
                                Glide.get(BaseApplication.mApplicationContext).clearDiskCache();
                            }
                        }.start();
                        mTvActivityFunctionSettingCache.setText("0KB");
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    /**
     * 退出程序
     */
    private void quit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("退出")
                .setMessage("确定退出吗？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SaveUtil.putString("Authorization","");
                        Intent intent = new Intent(mActivity, LoginActivity.class);
                        //跳转之前清空activity栈中的activity
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public int getRootView() {
        return R.layout.activity_function_setting;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_function_setting_tv_quit:
                quit();
                break;
            case R.id.ll_activity_function_setting_cache:
                clearCache();
                break;
        }
    }

}
