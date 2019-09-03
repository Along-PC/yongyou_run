package com.tourye.run.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tourye.run.R;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.utils.GlideCircleTransform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @ClassName:   FlowHeadLayout
 *
 * @Author:   along
 *
 * @Description:    头像层叠布局
 *
 * @CreateDate:   2019/6/4 4:56 PM
 *
 */

public class FlowHeadLayout extends ViewGroup {

    private Context context;

    private List<String> allUrls = new ArrayList<>();

    private boolean flag = false;//false表示右边增加，true表示左边增加

    private int spWidth = 20;
    //在new的时候会调用此方法
    public FlowHeadLayout(Context context) {
        super(context);
        this.context = context;
    }

    public FlowHeadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public FlowHeadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        //wrap_content
        int width = 0;
        int height = 0;

        //每一行的，宽，高
        int lineWidth = 0;
        int lineHeight = 0;
        //获取所有的子view
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            //测量子view
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //得到layoutparams
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                //换行判断
                height += lineHeight;
                //新的行高
                lineHeight = childHeight;
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;

            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }

            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }

        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
    }


    /**
     * 存储所有的View
     */
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    /**
     * 每一行的高度
     */
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        mAllViews.clear();
        mLineHeight.clear();

        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        List<View> lineViews = new ArrayList<View>();
        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - getPaddingRight() - getPaddingLeft()) {
                mLineHeight.add(lineHeight);
                mAllViews.add(lineViews);
                lineViews = new ArrayList<>();
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin - spWidth;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            lineViews.add(child);
        }

        // 处理最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        // 设置子View的位置

        int left = getPaddingLeft();
        int top = getPaddingTop();
        // 行数
        int lineNum = mAllViews.size();

        for (int i = 0; i < lineNum; i++) {
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            //反序显示
            if(flag){
                Collections.reverse(lineViews);
            }
//            Collections.reverse(lineViews);

            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin - spWidth;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    public void setSpWidth(int spWidth) {
        this.spWidth = spWidth;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setUrls(List<String> listVals) {
        allUrls.addAll(listVals);
        //开始绘制view
        setViews();
    }

    public void setOneUrls(String urlVal) {
        allUrls.add(urlVal);
        setViews();
    }

    public void cancels(String urlVal) {
        allUrls.remove(urlVal);
        setViews();
    }


    private void setViews() {
        //清空，重新绘制
        removeAllViews();
        for (int i = 0; i < allUrls.size(); i++) {
            //清空重新绘制
            ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.item_praise, this, false);
            RequestOptions options = new RequestOptions().transform(new GlideCircleTransform(BaseApplication.mApplicationContext));
            Glide.with(BaseApplication.mApplicationContext)
                    .load(allUrls.get(i))
                    .apply(options)
                    .into(imageView);
            this.addView(imageView);
        }
    }


    public void addDefaultViews() {
        //清空，重新绘制
        removeAllViews();
        for (int i = 0; i < allUrls.size(); i++) {
            //清空重新绘制
            ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.item_praise, this, false);
            RequestOptions options = new RequestOptions().transform(new GlideCircleTransform(BaseApplication.mApplicationContext));
            Glide.with(context)
                    .load(allUrls.get(i))
                    .apply(options)
                    .into(imageView);
            this.addView(imageView);
        }
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.item_praise, this, false);
        RequestOptions options = new RequestOptions().transform(new GlideCircleTransform(BaseApplication.mApplicationContext));
        Glide.with(context)
                .load(R.drawable.icon_head_default)
                .apply(options)
                .into(imageView);
        this.addView(imageView);
    }

    public void addHeadViews(List<CascadingBean> cascadingBeans) {
        //清空，重新绘制
        removeAllViews();
        for (int i = 0; i < cascadingBeans.size(); i++) {
            CascadingBean cascadingBean = cascadingBeans.get(i);
            if (cascadingBean.type==1) {
                ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.item_praise, this, false);
                RequestOptions options = new RequestOptions().transform(new GlideCircleTransform(BaseApplication.mApplicationContext));
                Glide.with(context)
                        .load(cascadingBean.getUrl())
                        .apply(options)
                        .into(imageView);
                this.addView(imageView);
            }else{
                ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.item_praise, this, false);
                RequestOptions options = new RequestOptions().transform(new GlideCircleTransform(BaseApplication.mApplicationContext));
                Glide.with(context)
                        .load(cascadingBean.getResourceId())
                        .apply(options)
                        .into(imageView);
                this.addView(imageView);
            }
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public static class CascadingBean{
        private int type;
        private String url;
        private int resourceId;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getResourceId() {
            return resourceId;
        }

        public void setResourceId(int resourceId) {
            this.resourceId = resourceId;
        }
    }
}
