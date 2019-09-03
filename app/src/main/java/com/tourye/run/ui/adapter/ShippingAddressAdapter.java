package com.tourye.run.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.UserGiftInfoBean;
import com.tourye.run.ui.activities.common.ImageDetailActivity;
import com.tourye.run.views.MeasureGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ShippingAddressAdapter
 * @Author: along
 * @Description: 地址管理适配器
 * @CreateDate: 2019/3/27 11:19 AM
 */
public class ShippingAddressAdapter extends RecyclerView.Adapter<ShippingAddressAdapter.ShippingAddressHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<UserGiftInfoBean.DataBean.GiftsBean> gifts = new ArrayList<>();

    public ShippingAddressAdapter(Context context, List<UserGiftInfoBean.DataBean.GiftsBean> gifts) {
        mContext = context;
        this.gifts = gifts;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ShippingAddressHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ShippingAddressHolder(mLayoutInflater.inflate(R.layout.item_shipping_address, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShippingAddressHolder shippingAddressHolder, int i) {
        UserGiftInfoBean.DataBean.GiftsBean giftsBean = gifts.get(i);
        final List<UserGiftInfoBean.DataBean.GiftsBean.ChoicesBean> choices = giftsBean.getChoices();
        if (choices == null) {
            return;
        }
        shippingAddressHolder.mTvItemShippingAddressPackageName.setText(giftsBean.getName());

        final ShippingAddressSizeAdapter shippingAddressSizeAdapter = new ShippingAddressSizeAdapter(mContext, choices);
        shippingAddressHolder.mGridItemShippingAddress.setAdapter(shippingAddressSizeAdapter);

        final String size_table = giftsBean.getSize_table();
        if (TextUtils.isEmpty(size_table)) {
            shippingAddressHolder.mTvItemShippingAddressSize.setVisibility(View.GONE);
        }else{
            shippingAddressHolder.mTvItemShippingAddressSize.setVisibility(View.VISIBLE);
            shippingAddressHolder.mTvItemShippingAddressSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ImageDetailActivity.class);
                    ArrayList<String> strings=new ArrayList<>();
                    strings.add(size_table);
                    intent.putStringArrayListExtra("data",strings);
                    intent.putExtra("pos",0);
                    mContext.startActivity(intent);
                }
            });
        }

        //如果区分性别
        if (giftsBean.isNeed_gender()) {
            shippingAddressHolder.mLlItemShippingAddressSex.setVisibility(View.VISIBLE);
            //拆分男女数据
            final List<UserGiftInfoBean.DataBean.GiftsBean.ChoicesBean> choicesManBeans = new ArrayList<>();
            final List<UserGiftInfoBean.DataBean.GiftsBean.ChoicesBean> choicesWomanBeans = new ArrayList<>();
            for (int index = 0; index < choices.size(); index++) {
                UserGiftInfoBean.DataBean.GiftsBean.ChoicesBean choicesBean = choices.get(index);
                if ("female".equalsIgnoreCase(choicesBean.getGender())) {
                    choicesWomanBeans.add(choicesBean);
                } else if("male".equalsIgnoreCase(choicesBean.getGender())){
                    choicesManBeans.add(choicesBean);
                }
            }
            //遍历获取选中的规格
            int specification = giftsBean.getSpecification();
            for (int i1 = 0; i1 < choices.size(); i1++) {
                if (choices.get(i1).getId()==specification) {
                    choices.get(i1).setSelected(true);
                    if ("male".equalsIgnoreCase(choices.get(i1).getGender())) {
                        shippingAddressHolder.mRbItemShippingAddressMan.setChecked(true);
                        shippingAddressSizeAdapter.setChoicesBeans(choicesManBeans);
                    }else if ("female".equalsIgnoreCase(choices.get(i1).getGender())){
                        shippingAddressHolder.mRbItemShippingAddressWoman.setChecked(true);
                        shippingAddressSizeAdapter.setChoicesBeans(choicesWomanBeans);
                    }
                }
            }
            //点击切换男女规格
            shippingAddressHolder.mRgItemShippingAddressSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    for (int index = 0; index < choices.size(); index++) {
                        UserGiftInfoBean.DataBean.GiftsBean.ChoicesBean choicesBean = choices.get(index);
                        choicesBean.setSelected(false);
                    }
                    switch (checkedId) {
                        case R.id.rb_item_shipping_address_man:
                            shippingAddressSizeAdapter.setChoicesBeans(choicesManBeans);
                            break;
                        case R.id.rb_item_shipping_address_woman:
                            shippingAddressSizeAdapter.setChoicesBeans(choicesWomanBeans);
                            break;
                        default:
                            break;
                    }
                }
            });

        }else{
            shippingAddressHolder.mLlItemShippingAddressSex.setVisibility(View.GONE);
            int specification = giftsBean.getSpecification();
            for (int i1 = 0; i1 < choices.size(); i1++) {
                if (choices.get(i1).getId()==specification) {
                    choices.get(i1).setSelected(true);
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return gifts.size();
    }

    public class ShippingAddressHolder extends RecyclerView.ViewHolder {
        private RadioGroup mRgItemShippingAddressSex;
        private RadioButton mRbItemShippingAddressMan;
        private RadioButton mRbItemShippingAddressWoman;
        private MeasureGridView mGridItemShippingAddress;
        private TextView mTvItemShippingAddressPackageName;
        private LinearLayout mLlItemShippingAddressSex;
        private TextView mTvItemShippingAddressSize;

        public ShippingAddressHolder(@NonNull View itemView) {
            super(itemView);
            mRgItemShippingAddressSex = (RadioGroup) itemView.findViewById(R.id.rg_item_shipping_address_sex);
            mRbItemShippingAddressMan = (RadioButton) itemView.findViewById(R.id.rb_item_shipping_address_man);
            mRbItemShippingAddressWoman = (RadioButton) itemView.findViewById(R.id.rb_item_shipping_address_woman);
            mGridItemShippingAddress = (MeasureGridView) itemView.findViewById(R.id.grid_item_shipping_address);
            mTvItemShippingAddressPackageName = (TextView) itemView.findViewById(R.id.tv_item_shipping_address_packageName);
            mLlItemShippingAddressSex = (LinearLayout) itemView.findViewById(R.id.ll_item_shipping_address_sex);
            mTvItemShippingAddressSize = (TextView) itemView.findViewById(R.id.tv_item_shipping_address_size);

        }
    }
}
