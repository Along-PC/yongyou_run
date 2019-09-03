package com.tourye.run.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tourye.run.R;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.utils.DensityUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * NineGridlayout
 * author:along
 * 2018/11/30 下午1:49
 *
 * 描述:九宫格控件
 */

public class NineGridlayout extends ViewGroup {

    /**
     * 图片之间的间隔
     */
    private int gap = DensityUtils.dp2px(5);
    private int columns;//列数
    private int rows;//行数
    private List<String> listData=new ArrayList<>();//数据
    private int totalWidth;//总共宽度
    private OnItemClickListener mOnItemClickListener;

    public NineGridlayout(Context context) {
        super(context);
    }

    public NineGridlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        totalWidth = context.getResources().getDisplayMetrics().widthPixels- DensityUtils.dp2px(BaseApplication.mApplicationContext,30);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 排列子条目
     */
    private void layoutChildrenView() {
        totalWidth=BaseApplication.mApplicationContext.getResources().getDisplayMetrics().widthPixels-DensityUtils.dp2px(86);
        int childrenCount = listData.size();
        if (childrenCount==1) {
            Glide.with(BaseApplication.mApplicationContext).load(listData.get(0)).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    ImageView childrenView = (ImageView) getChildAt(0);
                    int intrinsicWidth = resource.getIntrinsicWidth();
                    int intrinsicHeight = resource.getIntrinsicHeight();
                    int maxWidth = DensityUtils.dp2px(190);
                    int final_width=0;
                    int final_height=0;
                    if (intrinsicWidth*1.0/intrinsicHeight>1) {
                        final_width=maxWidth;
                        if (intrinsicWidth*1.0/intrinsicHeight>5) {
                            final_height= (int) (maxWidth/5);
                        }else{
                            final_height= (int) (intrinsicHeight*1.0/intrinsicWidth*final_width);
                        }
                    }else{
                        final_height=maxWidth;
                        if (intrinsicHeight*1.0/intrinsicWidth>5){
                            final_width= maxWidth/5;
                        }else{
                            final_width= (int) (intrinsicWidth*1.0/intrinsicHeight*final_height);
                        }
                    }
                    LayoutParams params = getLayoutParams();
                    params.height = final_height;
                    setLayoutParams(params);
                    LayoutParams layoutParams = childrenView.getLayoutParams();
                    layoutParams.height=final_height;
                    layoutParams.width=final_width;
                    childrenView.setLayoutParams(layoutParams);
                    childrenView.setImageDrawable(resource);
                    Glide.with(BaseApplication.mApplicationContext).load(listData.get(0)).into(childrenView);
                    childrenView.layout(0, 0, final_width, final_height);
                    childrenView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnItemClickListener!=null) {
                                mOnItemClickListener.onItemClick(0);
                            }
                        }
                    });
                    invalidate();
                }
            });
        }else{
            int singleWidth = (totalWidth - gap * 2) / 3;
            int singleHeight = singleWidth;

            //根据子view数量确定高度
            LayoutParams params = getLayoutParams();
            params.height = singleHeight * rows + gap * (rows - 1);
            setLayoutParams(params);

            for (int i = 0; i < childrenCount; i++) {
                ImageView childrenView = (ImageView) getChildAt(i);
                Glide.with(BaseApplication.mApplicationContext).load(listData.get(i)).into(childrenView);

                int[] position = findPosition(i);
                int left = (singleWidth + gap) * position[1];
                int top = (singleHeight + gap) * position[0];
                int right = left + singleWidth;
                int bottom = top + singleHeight;
                final int finalI = i;
                childrenView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null) {
                            mOnItemClickListener.onItemClick(finalI);
                        }
                    }
                });
                childrenView.layout(left, top, right, bottom);
            }

        }


    }


    /**
     * 定位子条目的位置
     * @param childNum
     * @return
     */
    private int[] findPosition(int childNum) {
        int[] position = new int[2];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i * columns + j) == childNum) {
                    position[0] = i;//行
                    position[1] = j;//列
                    break;
                }
            }
        }
        return position;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    /**
     * 设置数据
     * @param lists
     */
    public void setImagesData(List<String> lists) {
        if (lists == null || lists.isEmpty()) {
            return;
        }
        //初始化布局
        generateChildrenLayout(lists.size());
        int i = 0;
        removeAllViews();
        while (i < lists.size()) {
            ImageView iv = generageImageView();
            addView(iv, generateDefaultLayoutParams());
            i++;
        }
        //    这里做一个重用view的处理
//        if (listData == null) {
//            int i = 0;
//            while (i < lists.size()) {
//                ImageView iv = generageImageView();
//                addView(iv, generateDefaultLayoutParams());
//                i++;
//            }
//        } else {
//            int oldViewCount = listData.size();
//            int newViewCount = lists.size();
//            if (oldViewCount > newViewCount) {
//                removeViews(newViewCount - 1, oldViewCount - newViewCount);
//            } else if (oldViewCount < newViewCount) {
//                for (int i = 0; i < newViewCount - oldViewCount; i++) {
//                    ImageView iv = generageImageView();
//                    addView(iv, generateDefaultLayoutParams());
//                }
//            }
//        }
        listData = lists;
        layoutChildrenView();
    }


    /**
     * 根据图片个数确定行列数量
     * 对应关系如下
     * num	row	column
     * 1	   1	1
     * 2	   1	2
     * 3	   1	3
     * 4	   2	2
     * 5	   2	3
     * 6	   2	3
     * 7	   3	3
     * 8	   3	3
     * 9	   3	3
     *
     * @param length
     */
    private void generateChildrenLayout(int length) {
        switch (length) {
            case 1:
                rows = 1;
                columns = 1;
                break;
            case 2:
                rows = 1;
                columns = 2;
                break;
            case 3:
                rows = 1;
                columns = 3;
                break;
            case 4:
                rows = 2;
                columns = 2;
                break;
            case 5:
            case 6:
                rows = 2;
                columns = 3;
                break;
            default:
                rows = 3;
                columns = 3;
                break;
        }
    }

    public ImageView generageImageView(){
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setBackgroundColor(Color.parseColor("#f5f5f5"));
        return imageView;
    }

    public interface OnItemClickListener{
        public void onItemClick(int i);
    }

}
