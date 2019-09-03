package com.tourye.run.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @ClassName:   NineImageView
 *
 * @Author:   along
 *
 * @Description:    九宫格图片
 *
 * @CreateDate:   2019/5/20 3:25 PM
 *
 */
public class NineImageView extends ViewGroup {

    /**
     * 图片之间的间隔
     */
    private int gap = 5;
    private int columns;//列数
    private int rows;//行数
    private List<String> listData=new ArrayList<>();//数据
    private int totalWidth;//总共宽度
    private OnItemClickListener mOnItemClickListener;

    public NineImageView(Context context) {
        super(context);
    }

    public NineImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        totalWidth = context.getResources().getDisplayMetrics().widthPixels- DensityUtils.dp2px(BaseApplication.mApplicationContext,30);
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
        int childrenCount = listData.size();

        int singleWidth = (totalWidth - gap * (columns- 1)) / columns;
        int singleHeight = singleWidth;

        //根据子view数量确定高度
        ViewGroup.LayoutParams params = getLayoutParams();
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
        //这里做一个重用view的处理
        if (listData == null) {
            int i = 0;
            while (i < lists.size()) {
                ImageView iv = generageImageView();
                addView(iv, generateDefaultLayoutParams());
                i++;
            }
        } else {
            int oldViewCount = listData.size();
            int newViewCount = lists.size();
            if (oldViewCount > newViewCount) {
                removeViews(newViewCount - 1, oldViewCount - newViewCount);
            } else if (oldViewCount < newViewCount) {
                for (int i = 0; i < newViewCount - oldViewCount; i++) {
                    ImageView iv = generageImageView();
                    addView(iv, generateDefaultLayoutParams());
                }
            }
        }
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

