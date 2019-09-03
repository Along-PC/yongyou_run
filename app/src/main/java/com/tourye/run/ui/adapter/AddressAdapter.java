package com.tourye.run.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.DistrictsBean;

import java.util.List;

/**
 *
 * @ClassName:   AddressAdapter
 *
 * @Author:   along
 *
 * @Description: 地址列表适配器
 *
 * @CreateDate:   2019/4/9 2:48 PM
 *
 */
public class AddressAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<DistrictsBean.DataBean> mDataBeans;

    public AddressAdapter(Context context, List<DistrictsBean.DataBean> dataBeans) {
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
        AddressHolder addressHolder;
        if (convertView==null) {
            convertView=mLayoutInflater.inflate(R.layout.item_dialog_address,parent,false);
            addressHolder=new AddressHolder(convertView);
            convertView.setTag(addressHolder);
        }else{
            addressHolder= (AddressHolder) convertView.getTag();
        }
        DistrictsBean.DataBean dataBean = mDataBeans.get(position);
        addressHolder.mTvItemDialogAddress.setText(dataBean.getName());
        return convertView;
    }

    public class AddressHolder{
        private TextView mTvItemDialogAddress;

        public AddressHolder(View view){
            mTvItemDialogAddress = (TextView) view.findViewById(R.id.tv_item_dialog_address);
        }
    }
}
