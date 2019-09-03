package com.tourye.run.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.KitBean;
import com.tourye.run.ui.activities.common.CommonWebActivity;

import java.util.List;

/**
 *
 * @ClassName:   KitAdapter
 *
 * @Author:   along
 *
 * @Description:    锦囊适配器
 *
 * @CreateDate:   2019/4/2 3:18 PM
 *
 */
public class KitAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<KitBean.DataBean> mDataBeans;

    public KitAdapter(Context context, List<KitBean.DataBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        KitHolder kitHolder;
        if (convertView==null) {
            convertView=mLayoutInflater.inflate(R.layout.item_activity_kit,parent,false);
            kitHolder=new KitHolder(convertView);
            convertView.setTag(kitHolder);
        }else{
            kitHolder= (KitHolder) convertView.getTag();
        }
        final KitBean.DataBean dataBean = mDataBeans.get(position);
        kitHolder.mTvItemActivityKitSport.setText(dataBean.getTitle());
        if (TextUtils.isEmpty(dataBean.getUrl())) {
            kitHolder.mTvItemActivityKitState.setTextColor(Color.parseColor("#FF999999"));
            kitHolder.mTvItemActivityKitState.setText("未解锁");
            kitHolder.mImgItemActivityKitLock.setVisibility(View.VISIBLE);
            kitHolder.mLlItemActivityKitContent.setOnClickListener(null);
        }else{
            kitHolder.mTvItemActivityKitState.setTextColor(Color.parseColor("#FFFD4C4F"));
            kitHolder.mTvItemActivityKitState.setText("解锁成功");
            kitHolder.mImgItemActivityKitLock.setVisibility(View.GONE);
            kitHolder.mLlItemActivityKitContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CommonWebActivity.class);
                    intent.putExtra("url",dataBean.getUrl());
                    mContext.startActivity(intent);
                }
            });
        }

        return convertView;
    }

    public class KitHolder{
        private TextView mTvItemActivityKitSport;
        private TextView mTvItemActivityKitState;
        private ImageView mImgItemActivityKitLock;
        private LinearLayout mLlItemActivityKitContent;


        public KitHolder(View view){
            mTvItemActivityKitSport = (TextView) view.findViewById(R.id.tv_item_activity_kit_sport);
            mTvItemActivityKitState = (TextView) view.findViewById(R.id.tv_item_activity_kit_state);
            mImgItemActivityKitLock = (ImageView) view.findViewById(R.id.img_item_activity_kit_lock);
            mLlItemActivityKitContent = (LinearLayout) view.findViewById(R.id.ll_item_activity_kit_content);

        }
    }
}
