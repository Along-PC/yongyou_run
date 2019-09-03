package com.tourye.run.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tourye.run.BuildConfig;
import com.tourye.run.R;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.bean.AdvertiseBean;
import com.tourye.run.ui.activities.common.CommonWebActivity;
import com.tourye.run.utils.SaveUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName:   SignupAdvertiseAdapter
 *
 * @Author:   along
 *
 * @Description:  报名底部广告适配器
 *
 * @CreateDate:   2019/3/15 5:42 PM
 *
 */
public class SignupAdvertiseAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<AdvertiseBean.DataBean> mAdvertiseBeans=new ArrayList<>();

    public SignupAdvertiseAdapter(Context context, List<AdvertiseBean.DataBean> advertiseBeans) {
        mContext = context;
        mAdvertiseBeans = advertiseBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final AdvertiseBean.DataBean dataBean = mAdvertiseBeans.get(position%mAdvertiseBeans.size());
        final View inflate = mLayoutInflater.inflate(R.layout.item_common_image, container, false);
        ImageView imageView=inflate.findViewById(R.id.img_item_common_image);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(BaseApplication.mApplicationContext).load(dataBean.getImage()).into(imageView);
        container.addView(inflate);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(dataBean.getUrl())) {
                    Intent intent = new Intent(mContext, CommonWebActivity.class);
                    String link = dataBean.getUrl();
                    URL url = null;
                    try {
                        url = new URL(link);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    String query = url.getQuery();
                    boolean contains = url.getHost().equalsIgnoreCase(BuildConfig.HOST_URL);
                    String token="env=gio&jwt="+ SaveUtil.getString("Authorization", "");
                    if (contains) {
                        if (TextUtils.isEmpty(query)) {
                            String protocol = url.getProtocol();
                            //替换url中的query来标识身份
                            String host = url.getHost();
                            int port = url.getDefaultPort();
                            String path = url.getPath();
                            String ref = url.getRef();
                            link=protocol+"://"+host+":"+port+path+"?"+query+token+"#"+ref;
                        }else{
                            link = link.replace(query, query + "&" + token);
                        }
                    }
                    intent.putExtra("url",link);
                    mContext.startActivity(intent);
                }
            }
        });
        return inflate;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

}
