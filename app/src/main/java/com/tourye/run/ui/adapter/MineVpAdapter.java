package com.tourye.run.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tourye.run.BuildConfig;
import com.tourye.run.R;
import com.tourye.run.bean.UserCenterAdBean;
import com.tourye.run.ui.activities.common.CommonWebActivity;
import com.tourye.run.utils.SaveUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by longlongren on 2019/3/11.
 * <p>
 * introduce:个人中心viewpager适配器
 */
public class MineVpAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<UserCenterAdBean.DataBean> mAdavertiseBeans;

    public MineVpAdapter(Context context, List<UserCenterAdBean.DataBean> dataBeans) {
        mContext = context;
        this.mAdavertiseBeans = dataBeans;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View inflate = mLayoutInflater.inflate(R.layout.item_fragment_mine_vp, container, false);
        ImageView mImgItemFragmentMineVp = inflate.findViewById(R.id.img_item_fragment_mine_vp);
        UserCenterAdBean.DataBean dataBean = mAdavertiseBeans.get(position % mAdavertiseBeans.size());
        Glide.with(mContext).load(dataBean.getImage()).into(mImgItemFragmentMineVp);
        container.addView(inflate);
        final String link = dataBean.getUrl();
        if (TextUtils.isEmpty(link)) {
            inflate.setOnClickListener(null);
        }else{
            inflate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    URL url = null;
                    try {
                        url = new URL(link);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    String query = url.getQuery();
                    boolean contains = url.getHost().equalsIgnoreCase(BuildConfig.HOST_URL);
                    String token="env=gio&jwt="+ SaveUtil.getString("Authorization", "");
                    String finalUrl=null;
                    if (contains) {
                        if (TextUtils.isEmpty(query)) {
                            String protocol = url.getProtocol();
                            //替换url中的query来标识身份
                            String host = url.getHost();
                            int port = url.getDefaultPort();
                            String path = url.getPath();
                            String ref = url.getRef();
                            finalUrl=protocol+"://"+host+":"+port+path+"?"+query+token+"#"+ref;
                        }else{
                            finalUrl = link.replace(query, query + "&" + token);
                        }
                    }
                    Intent intent = new Intent(mContext, CommonWebActivity.class);
                    intent.putExtra("url",finalUrl);
                    mContext.startActivity(intent);
                }
            });
        }

        return inflate;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

}
