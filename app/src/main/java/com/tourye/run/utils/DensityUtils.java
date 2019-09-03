package com.tourye.run.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.tourye.run.base.BaseApplication;

/**
 * Created by longlongren on 2018/8/14.
 * <p>
 * introduce:像素单位转换工具
 */

public class DensityUtils {

    /**
     * dp值转化成px
     * @param dp
     * @return
     */
    public static int dp2px(Context context, int dp){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp*density+0.5f);
    }

    /**
     * dp值转化成px
     * @param dp
     * @return
     */
    public static int dp2px( int dp){
        float density = BaseApplication.mApplicationContext.getResources().getDisplayMetrics().density;
        return (int) (dp*density+0.5f);
    }

    /**
     * sp值转化成px
     * @param sp
     * @return
     */
    public static int sp2px(Context context, int sp){
        float density = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp*density+0.5f);
    }

    /**
     * sp值转化成px
     * @param sp
     * @return
     */
    public static int sp2px( int sp){
        float density = BaseApplication.mApplicationContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp*density+0.5f);
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    public static int getScreenWidth(){
        Resources resources = BaseApplication.mApplicationContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        return width;
    }

    /**
     * 获取屏幕高度
     * @return
     */
    public static int getScreenheight(){
        Resources resources = BaseApplication.mApplicationContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int height = dm.heightPixels;
        return height;
    }

}
