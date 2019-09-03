package com.tourye.run.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.tourye.run.base.BaseApplication;

/**
 * Created by longlongren on 2018/8/24.
 * <p>
 * introduce:存储工具类
 */
public class SaveUtil {

    public static String FILE_NAME="run";
    /**
     * 存入的字符串的数据，Context.MODE_PRIVATE一般是私有的也可是可读可写的模式
     * @param key           键
     * @param value         值
     */
    public static void putString(String key, String value){

        SharedPreferences sharedPreferences = BaseApplication.mApplicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        edit.commit();
    }
    /**
     * 获取的字符串的数据，Context.MODE_PRIVATE一般是私有的也可是可读可写的模式
     * @param key           键
     * @param defValue      默认值
     */
    public static String getString(String key, String defValue){
        SharedPreferences sharedPreferences = BaseApplication.mApplicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defValue);
    }

    //存入布尔值数据
    public static void putBoolean(String key, boolean value){
        SharedPreferences sharedPreferences = BaseApplication.mApplicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }
    //获取布尔值数据
    public static boolean getBoolean(String key, boolean defValue){
        SharedPreferences sharedPreferences = BaseApplication.mApplicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defValue);
    }

    public static void putFloat(String key, float value){
        SharedPreferences sharedPreferences = BaseApplication.mApplicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putFloat(key, value);
        edit.commit();
    }

    public static float getFloat(String key, float defValue){
        SharedPreferences sharedPreferences = BaseApplication.mApplicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, defValue);
    }

    public static void putInt(String key, int value){
        SharedPreferences sharedPreferences = BaseApplication.mApplicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static int getInt(String key, int defValue){
        SharedPreferences sharedPreferences = BaseApplication.mApplicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defValue);
    }

    public static void putLong(String key, long value){
        SharedPreferences sharedPreferences = BaseApplication.mApplicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    public static long getLong(String key, long defValue){
        SharedPreferences sharedPreferences = BaseApplication.mApplicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, defValue);
    }

}
