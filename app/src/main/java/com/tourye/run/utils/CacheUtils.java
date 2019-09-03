package com.tourye.run.utils;

import android.os.Environment;

import com.tourye.run.base.BaseApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 *
 * @ClassName:   CacheUtils
 *
 * @Author:   along
 *
 * @Description:    缓存工具类
 *
 * @CreateDate:   2019/4/16 2:09 PM
 *
 */
public class CacheUtils {

    private static CacheUtils mCacheUtils;

    private CacheUtils(){

    }

    public static CacheUtils getInstance(){
        if (mCacheUtils==null) {
            mCacheUtils=new CacheUtils();
        }
        return mCacheUtils;
    }

    /**
     * 获取app缓存路径
     * @return
     */
    private String getCachePath(){
        String cachePath ;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            //外部存储可用
            cachePath = BaseApplication.mApplicationContext.getExternalCacheDir().getPath() ;
        }else {
            //外部存储不可用
            cachePath = BaseApplication.mApplicationContext.getCacheDir().getPath() ;
        }
        return cachePath ;
    }

    //缓存数据
    public void cachedata(String fileName,Object o){
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(getCachePath(), fileName)));
            objectOutputStream.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (objectOutputStream!=null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取缓存数据
     * @param fileName
     * @param <T>
     * @return
     */
    public <T> T restoreData(String fileName,T t){
        ObjectInputStream objectInputStream=null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(new File(getCachePath(), fileName)));
            t = (T) objectInputStream.readObject();
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream!=null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
