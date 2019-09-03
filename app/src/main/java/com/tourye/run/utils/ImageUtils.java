package com.tourye.run.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;

import java.util.List;

/**
 *
 * @ClassName:   ImageUtils
 *
 * @Author:   along
 *
 * @Description:    添加水印工具类
 *
 * @CreateDate:   2019/4/30 10:53 AM
 *
 */
public class ImageUtils {

    /**
     * 给一张Bitmap添加水印文字。
     *
     * @param src      源图片
     * @param content  水印文本
     * @param recycle  是否回收
     * @return 已经添加水印后的Bitmap。
     */
    public static Bitmap addTextWatermark(Bitmap src, String content, boolean recycle) {
        if (src==null || TextUtils.isEmpty(content)) {
            return null;
        }
        Bitmap ret = src.copy(src.getConfig(), true);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas(ret);
        canvas.save();
        canvas.rotate(-15);
        paint.setColor(Color.GRAY);
        paint.setTextSize(DensityUtils.sp2px(18));
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        Rect bounds = new Rect();
        int width = ret.getWidth();
        int height = ret.getHeight();
        paint.getTextBounds(content, 0, content.length(), bounds);
        float[] textSize = getTextSize(content, paint);
        for (int j = 0; j < 9; j++) {
            float startX=(DensityUtils.dp2px(20)+textSize[0])*(j-3);
            for (int i = 0; i < 5; i++) {
                canvas.drawText(content, startX, (bounds.height()+height/5)*i, paint);
            }
        }
        canvas.restore();
        if (recycle && !src.isRecycled())
            src.recycle();
        return ret;
    }

    /**
     * 获取字符串宽高
     *
     * @param string
     * @param paint
     * @return
     */
    public static float[] getTextSize(String string, Paint paint) {
        float[] floats = new float[2];
        Rect rect = new Rect();
        paint.getTextBounds(string, 0, string.length(), rect);
        floats[0] = Math.max(rect.width(), floats[0]);
        floats[1] = Math.max(rect.height(), floats[1]);
        return floats;
    }



}
