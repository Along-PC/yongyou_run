package com.tourye.run.base;

import android.app.Application;
import android.content.Context;
import android.os.Debug;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.mob.MobSDK;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.bugly.crashreport.CrashReport;
import com.tourye.run.BuildConfig;
import com.tourye.run.utils.LogcatHelper;

/**
 * Created by meridian on 2018/2/2.
 */

public class BaseApplication extends Application {


    public static Context mApplicationContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //解决方法数超标
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //日志工具
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(L) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("run")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        mApplicationContext = getApplicationContext();
        //设置内存缓存级别
//        Glide.get(BaseApplication.mApplicationContext).setMemoryCategory(MemoryCategory.HIGH);

        //mob三方分享初始化
        MobSDK.init(this);

        //bugly初始化
        CrashReport.initCrashReport(getApplicationContext(), "88225e6785", true);

        if (BuildConfig.DEBUG) {
            //todo
            //本地日志初始化
            LogcatHelper logcatHelper = LogcatHelper.getInstance(this);
            logcatHelper.init(this);
            logcatHelper.start();
        }

    }

}
