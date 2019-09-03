package com.tourye.run.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tourye.run.R;


/**
 * Created by longlongren on 2018/8/10.
 * <p>
 * introduce:
 */

public abstract class BaseFragment extends Fragment {

    //标题栏控件
    protected ImageView mImgReturn;
    protected TextView mTvTitle;
    protected TextView mTvCertain;
    protected ImageView mImgCertain;
    private RelativeLayout mRlRootTop;

    public Activity mActivity;

    protected boolean mFirstLoad=true;//是否首次加载
    protected boolean mIsCreated=false;//界面是否渲染完毕

    public static final String TAG = "BaseFragment";
    protected LayoutInflater mInflater;

    private View contentView;//渲染出来的页面

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = this.getActivity();
        mInflater = inflater;

        mIsCreated=true;

        //避免oncreateview多次执行
        if (contentView!=null) {
            ViewGroup parent = (ViewGroup) contentView.getParent();
            if (parent!=null) {
                parent.removeView(contentView);
            }
            return contentView;
        }

        //模板模式完成初始化
        if (isNeedTitle()) {
            LinearLayout linearLayout = new LinearLayout(mActivity);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(layoutParams);
            View inflateTitle = inflater.inflate(R.layout.title_top, linearLayout, false);
            mImgReturn = (ImageView) inflateTitle.findViewById(R.id.img_return);
            mTvTitle = (TextView) inflateTitle.findViewById(R.id.tv_title);
            mTvCertain = (TextView) inflateTitle.findViewById(R.id.tv_certain);
            mImgCertain = (ImageView) inflateTitle.findViewById(R.id.img_certain);
            mRlRootTop = (RelativeLayout)inflateTitle.findViewById(R.id.rl_root_top);
            //获取状态栏高度设置padding
            int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                int result = this.getResources().getDimensionPixelSize(resourceId);
                mRlRootTop.setPadding(0,result,0,0);
            }

            View inflateContent = inflater.inflate(getRootView(), linearLayout, false);

            linearLayout.addView(inflateTitle);
            linearLayout.addView(inflateContent);

            initView(linearLayout);

            if (!isNeedLazyLoad()) {
                mFirstLoad=false;
                initData();
            }

            contentView=linearLayout;

            return linearLayout;
        } else {
            View inflateContent = inflater.inflate(getRootView(), container, false);


            initView(inflateContent);

            if (!isNeedLazyLoad()) {
                mFirstLoad=false;
                initData();
            }

            contentView=inflateContent;

            return inflateContent;
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        //界面首次可见、需要懒加载、
        if (isVisibleToUser && isNeedLazyLoad() && mFirstLoad && mIsCreated) {
            initData();
            mFirstLoad=false;
        }

        //界面再次可见，执行刷新操作
        if (isVisibleToUser && mIsCreated && !mFirstLoad) {
            refreshData();
        }

        //当界面创建完成，从可见变为不可见时
        if (!isVisibleToUser && !mFirstLoad) {
            onInvisible();
        }
    }

    /**
     * 懒加载之后的刷新
     */
    public void refreshData(){

    }

    /**
     *  当用户进入过界面，然后界面不可见之后
     */
    public void onInvisible(){

    }

    /**
     * 是否需要懒加载数据
     * @return
     */
    public boolean isNeedLazyLoad(){
        return false;
    }

    //初始化控件
    public abstract void initView(View view);

    //初始化数据
    public abstract void initData();

    //获取页面布局
    public abstract int getRootView();

    //是否需要头部标题
    public boolean isNeedTitle() {
        return false;
    }

}
