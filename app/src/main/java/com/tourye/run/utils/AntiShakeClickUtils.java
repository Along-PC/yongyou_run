package com.tourye.run.utils;

import android.view.View;

/**
 *
 * @ClassName:   AntiShakeClickUtils
 *
 * @Author:   along
 *
 * @Description:防抖动点击工具类
 *
 * @CreateDate:   2019/7/9 9:08 AM
 *
 */
public class AntiShakeClickUtils {
    // 两次点击间隔不能少于1000ms
    private static final int FAST_CLICK_DELAY_TIME = 1000;
    private static long LASTCLICKTIME;

    public static boolean isFastClick() {
        boolean flag = false;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - LASTCLICKTIME) < FAST_CLICK_DELAY_TIME ) {
            flag = true;
        }else{
            LASTCLICKTIME = currentClickTime;
        }
        return flag;
    }
}
