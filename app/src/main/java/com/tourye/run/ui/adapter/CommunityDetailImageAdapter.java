package com.tourye.run.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.tourye.run.R;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.ui.activities.common.ImageDetailActivity;
import com.tourye.run.utils.DensityUtils;

import java.util.ArrayList;

/**
 * Created by longlongren on 2018/10/9.
 * <p>
 * introduce: 社区详情图片适配器
 */

public class CommunityDetailImageAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> mUrls=new ArrayList<>();

    public CommunityDetailImageAdapter(Context context, ArrayList<String> urls) {
        mContext = context;
        mLayoutInflater=LayoutInflater.from(mContext);
        mUrls = urls;
    }

    @Override
    public int getCount() {
        return mUrls.size();
    }

    @Override
    public Object getItem(int i) {
        return mUrls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        CommunityDetailImageHolder communityDetailImageHolder;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.item_community_image, viewGroup, false);
            communityDetailImageHolder = new CommunityDetailImageHolder(view);
            view.setTag(communityDetailImageHolder);
        } else {
            communityDetailImageHolder = (CommunityDetailImageHolder) view.getTag();
        }
        int size = mUrls.size();
        //屏幕宽度
        int widthPixels = mContext.getResources().getDisplayMetrics().widthPixels;
        switch (size) {
            case 1:
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(widthPixels, widthPixels);
                communityDetailImageHolder.mImgItemCommunityImage.setLayoutParams(layoutParams);
                break;
            case 2:
            case 3:
            case 4:
                int widthPixelTwo = widthPixels - DensityUtils.dp2px(mContext, 10);
                widthPixelTwo=widthPixelTwo/2;
                RelativeLayout.LayoutParams layoutParamsTwo = new RelativeLayout.LayoutParams(widthPixelTwo, widthPixelTwo);
                communityDetailImageHolder.mImgItemCommunityImage.setLayoutParams(layoutParamsTwo);
                break;
            default:
                int widthPixelThree = widthPixels - DensityUtils.dp2px(mContext, 10)*2;
                widthPixelThree=widthPixelThree/3;
                RelativeLayout.LayoutParams layoutParamsThree = new RelativeLayout.LayoutParams(widthPixelThree, widthPixelThree);
                communityDetailImageHolder.mImgItemCommunityImage.setLayoutParams(layoutParamsThree);
                break;
        }
        final String url = mUrls.get(i);
        communityDetailImageHolder.mImgItemCommunityImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ImageDetailActivity.class);
                intent.putExtra("data", mUrls);
                intent.putExtra("pos",i);
                mContext.startActivity(intent);
            }
        });
        Glide.with(BaseApplication.mApplicationContext).load(mUrls.get(i)).into(communityDetailImageHolder.mImgItemCommunityImage);
        return view;
    }

    public class CommunityDetailImageHolder {
        private ImageView mImgItemCommunityImage;

        public CommunityDetailImageHolder(View view) {
            mImgItemCommunityImage = (ImageView) view.findViewById(R.id.img_item_community_image);
        }
    }
}
