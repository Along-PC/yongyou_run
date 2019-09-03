package com.tourye.run.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.utils.NetStateUtils;
import com.tourye.run.utils.NoneNetUtils;


public abstract class BaseActivity extends AppCompatActivity {

    protected LayoutInflater mLayoutInflater;

    //标题栏控件
    protected ImageView mImgReturn;
    protected TextView mTvTitle;
    protected TextView mTvCertain;
    protected ImageView mImgCertain;
    private RelativeLayout mRlRootTop;

    protected Activity mActivity;
    protected Bundle mSavedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //沉浸式---状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (isNeedDarkState()) {
            //android6.0以后可以对状态栏文字颜色和图标进行修改
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        //强制竖屏
        if (isNeedPortrait()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        mLayoutInflater = this.getLayoutInflater();

        //模板模式完成初始化
        if (isNeedTitle()) {
            LinearLayout linearLayout=new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(layoutParams);
            View inflateTitle = mLayoutInflater.inflate(R.layout.title_top, linearLayout, false);
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
            mImgReturn.setBackgroundResource(R.drawable.icon_return);
            mImgReturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            View inflateContent = mLayoutInflater.inflate(getRootView(), linearLayout, false);

            linearLayout.addView(inflateTitle);
            linearLayout.addView(inflateContent);
            setContentView(linearLayout);
        }else{
            setContentView(getRootView());
        }
        mActivity=this;
        mSavedInstanceState = savedInstanceState;

        initView();
        initData();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isNeedCheckNet()) {
            checkNet();
        }
    }

    public void checkNet(){
        int netState = NetStateUtils.getNetState(mActivity);
        if (netState==NetStateUtils.NETWORK_STATE_NONE) {
            NoneNetUtils.showDialog(mActivity);
        }
    }

    /**
     * 禁止修改字体大小
     * @return
     */
    @Override
    public Resources getResources() {
        if (isNeedUpdateTypefaceSize()) {
            Resources res = super.getResources();
            Configuration config=new Configuration();
            config.setToDefaults();
            res.updateConfiguration(config,res.getDisplayMetrics() );
            return res;
        }else{
            return super.getResources();
        }
    }

    /**
     * 点击输入框以外的地方，收起键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 收起键盘
     *
     * @param v
     * @param event
     * @return
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否需要暗系状态栏
     * @return
     */
    public boolean isNeedDarkState(){
        return true;
    }

    //初始化控件
    public abstract void initView();

    //初始化数据
    public abstract void initData();

    //获取页面布局
    public abstract int getRootView();

    //是否需要头部标题
    public boolean isNeedTitle(){
        return true;
    }

    /**
     * 是否强制竖屏
     * @return
     */
    public boolean isNeedPortrait(){
        return true;
    }

    /**
     * 是否需要页面启动时检查网络
     * @return
     */
    protected boolean isNeedCheckNet(){
        return true;
    }

    /**
     * 是否需要禁止修改字体大小
     * @return
     */
    public boolean isNeedUpdateTypefaceSize(){
        return true;
    }

}
