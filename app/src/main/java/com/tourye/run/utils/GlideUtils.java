package com.tourye.run.utils;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.tourye.run.R;
import com.tourye.run.base.BaseApplication;

import java.io.File;
import java.math.BigDecimal;

public class GlideUtils {

    private static GlideUtils mGlideUtils;

    private GlideUtils(){

    }

    public static GlideUtils getInstance(){
        if (mGlideUtils==null) {
            mGlideUtils=new GlideUtils();
        }
        return mGlideUtils;
    }

    /**
     * 加载图片
     * @param url
     * @param imageView
     */
    public void loadImage(String url, ImageView imageView){
        Glide.with(BaseApplication.mApplicationContext).load(url).into(imageView);
    }

    /**
     * 加载圆角图片
     * @param url
     * @param imageView
     */
    public void loadRoundImage(String url, ImageView imageView){
        RequestOptions requestOptions = new RequestOptions().transform(new RoundedCorners(DensityUtils.dp2px(4)));
        Glide.with(BaseApplication.mApplicationContext).load(url).apply(requestOptions).into(imageView);
    }

    /**
     * 加载圆角图片
     * @param url
     * @param imageView
     * @param roundCorner  圆角大小
     */
    public void loadRoundImage(String url, ImageView imageView,int roundCorner){
        RequestOptions requestOptions = new RequestOptions().transform(new RoundedCorners(DensityUtils.dp2px(roundCorner)));
        Glide.with(BaseApplication.mApplicationContext).load(url).apply(requestOptions).into(imageView);
    }

    /**
     * 加载圆形图片
     * @param url
     * @param imageView
     */
    public void loadCircleImage(String url, ImageView imageView){
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(BaseApplication.mApplicationContext).load(url).apply(requestOptions).into(imageView);
    }


    /**
     * 加载本地图片
     * @param file_address
     * @param imageView
     */
    public void loadLocalImage(String file_address,ImageView imageView){
        if (TextUtils.isEmpty(file_address)) {
            return;
        }
        RequestOptions requestOptions = new RequestOptions().transform(new RoundedCorners(DensityUtils.dp2px(4)));
        File file = new File(file_address);
        Glide.with(BaseApplication.mApplicationContext).load(file).apply(requestOptions).into(imageView);
    }


    // 获取指定文件夹内所有文件大小的和
    public long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    // 格式化单位
    public String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

}
