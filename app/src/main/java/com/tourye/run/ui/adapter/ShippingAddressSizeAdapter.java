package com.tourye.run.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.GoodsSizeBean;
import com.tourye.run.bean.UserGiftInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName:   ShippingAddressSizeAdapter
 *
 * @Author:   along
 *
 * @Description:  地址管理尺寸适配器
 *
 * @CreateDate:   2019/3/27 3:16 PM
 *
 */
public class ShippingAddressSizeAdapter extends BaseAdapter {

    private Context mContext;
    private List<UserGiftInfoBean.DataBean.GiftsBean.ChoicesBean> mChoicesBeans=new ArrayList<>();
    private LayoutInflater mLayoutInflater;

    public ShippingAddressSizeAdapter(Context context, List<UserGiftInfoBean.DataBean.GiftsBean.ChoicesBean> choicesBeans) {
        mContext = context;
        mChoicesBeans = choicesBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setChoicesBeans(List<UserGiftInfoBean.DataBean.GiftsBean.ChoicesBean> choicesBeans) {
        mChoicesBeans = choicesBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mChoicesBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mChoicesBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShippingAddressHolder shippingAddressHolder;
        if (convertView==null) {
            convertView=mLayoutInflater.inflate(R.layout.item_shipping_address_size,parent,false);
            shippingAddressHolder=new ShippingAddressHolder(convertView);
            convertView.setTag(shippingAddressHolder);
        }else{
            shippingAddressHolder= (ShippingAddressHolder) convertView.getTag();
        }
        final UserGiftInfoBean.DataBean.GiftsBean.ChoicesBean choicesBean = mChoicesBeans.get(position);
        shippingAddressHolder.mTvItemActivityShippingAddress.setText(choicesBean.getText());
        shippingAddressHolder.mTvItemActivityShippingAddress.setTextColor(ContextCompat.getColorStateList(mContext, R.color.selector_shipping_address));
        shippingAddressHolder.mTvItemActivityShippingAddress.setSelected(choicesBean.isSelected());
        //剩余数量
        int left_count = choicesBean.getLeft_count();
        if (left_count>0) {
            shippingAddressHolder.mTvItemActivityShippingAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < mChoicesBeans.size(); i++) {
                        UserGiftInfoBean.DataBean.GiftsBean.ChoicesBean bean = mChoicesBeans.get(i);
                        bean.setSelected(false);
                    }
                    if (v.isSelected()) {
                        choicesBean.setSelected(false);
                    }else{
                        choicesBean.setSelected(true);
                    }
                    notifyDataSetChanged();
                }
            });
        }else{
            shippingAddressHolder.mTvItemActivityShippingAddress.setTextColor(Color.parseColor("#FFCCCCCC"));
            shippingAddressHolder.mTvItemActivityShippingAddress.setOnClickListener(null);
        }

        return convertView;
    }

    public class ShippingAddressHolder{
        private TextView mTvItemActivityShippingAddress;

        public ShippingAddressHolder(View view){
            mTvItemActivityShippingAddress = (TextView) view.findViewById(R.id.tv_item_activity_shipping_address);
        }
    }

}
