package com.tourye.run.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.bean.DistrictsBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.AddressAdapter;
import com.tourye.run.views.WheelView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by longlongren on 2018/9/1.
 * <p>
 * introduce:地址管理选项弹窗
 */

public class AddressDialog extends Dialog {

    private final Context mContext;
    private TextView mTvDialogAddressProvince;
    private TextView mTvDialogAddressCity;
    private TextView mTvDialogAddressCounty;
    private ListView mListDialogAddressProvince;
    private ListView mListDialogAddressCity;
    private ListView mListDialogAddressCounty;

    private DistrictsBean.DataBean provinceBean;
    private DistrictsBean.DataBean cityBean;
    private DistrictsBean.DataBean countyBean;

    private OnAddressChooseListener mOnAddressChooseListener;

    public AddressDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_address);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);

        getWindow().setWindowAnimations(R.style.HeadDialogStyle);

        mTvDialogAddressProvince = (TextView) findViewById(R.id.tv_dialog_address_province);
        mTvDialogAddressCity = (TextView) findViewById(R.id.tv_dialog_address_city);
        mTvDialogAddressCounty = (TextView) findViewById(R.id.tv_dialog_address_county);
        mListDialogAddressProvince = (ListView) findViewById(R.id.list__dialog_address_province);
        mListDialogAddressCity = (ListView) findViewById(R.id.list__dialog_address_city);
        mListDialogAddressCounty = (ListView) findViewById(R.id.list__dialog_address_county);

        initListener();

        getTopDistricts();

    }

    public void setOnAddressChooseListener(OnAddressChooseListener onAddressChooseListener) {
        mOnAddressChooseListener = onAddressChooseListener;
    }

    private void initListener() {

    }

    public void getTopDistricts() {
        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().get(Constants.TOP_DISTRICTS, map, new HttpCallback<DistrictsBean>() {

            @Override
            public void onSuccessExecute(DistrictsBean districtsBean) {
                final List<DistrictsBean.DataBean> data = districtsBean.getData();
                if (data != null && data.size() > 0) {
                    mTvDialogAddressProvince.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTvDialogAddressCity.setVisibility(View.GONE);
                            mTvDialogAddressCounty.setVisibility(View.GONE);
                            mListDialogAddressProvince.setVisibility(View.VISIBLE);
                            mListDialogAddressCity.setVisibility(View.GONE);
                            mListDialogAddressCounty.setVisibility(View.GONE);
                        }
                    });
                    DistrictsBean.DataBean dataBean = new DistrictsBean.DataBean();
                    dataBean.setName("全国");
                    dataBean.setId(0);
                    data.add(0,dataBean);
                    mTvDialogAddressProvince.setText(data.get(0).getName());
                    AddressAdapter addressAdapter = new AddressAdapter(mContext, data);
                    mListDialogAddressProvince.setAdapter(addressAdapter);
                    mListDialogAddressProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position==0) {
                                if (mOnAddressChooseListener!=null) {
                                    mOnAddressChooseListener.onChoose(data.get(0).getId(), data.get(0).getName());
                                }
                                dismiss();
                                return;
                            }else{
                                provinceBean = data.get(position);
                                mTvDialogAddressProvince.setText(provinceBean.getName());
                                int city_id = provinceBean.getId();
                                String city_name = provinceBean.getName();
                                getChildDistricts(data.get(position).getId(), mListDialogAddressCity, city_id, city_name);
                            }

                        }
                    });
                }
            }
        });


    }

    public void getChildDistricts(int id, final ListView listView, final int city_id, final String city_name) {
        Map<String, String> child_map = new HashMap<>();
        child_map.put("id", id + "");
        HttpUtils.getInstance().get(Constants.CHILD_DISTRICTS, child_map, new HttpCallback<DistrictsBean>() {
            @Override
            public void onSuccessExecute(DistrictsBean districtsBean) {
                final List<DistrictsBean.DataBean> data = districtsBean.getData();
                if (data != null && data.size() > 0) {
                    if (data.get(0).getLevel() == 3) {
                        if (mOnAddressChooseListener!=null) {
                            mOnAddressChooseListener.onChoose(city_id, city_name);
                        }
                        dismiss();
                        return;
                    }
                    mTvDialogAddressCity.setVisibility(View.VISIBLE);
                    mListDialogAddressProvince.setVisibility(View.GONE);
                    mListDialogAddressCity.setVisibility(View.VISIBLE);
                    mListDialogAddressCounty.setVisibility(View.GONE);
                    mTvDialogAddressCity.setVisibility(View.VISIBLE);
                    mTvDialogAddressCity.setText(data.get(0).getName());
                    mTvDialogAddressCity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTvDialogAddressCounty.setVisibility(View.GONE);
                            mListDialogAddressProvince.setVisibility(View.GONE);
                            mListDialogAddressCity.setVisibility(View.VISIBLE);
                            mListDialogAddressCounty.setVisibility(View.GONE);
                        }
                    });
                    AddressAdapter addressAdapter = new AddressAdapter(mContext, data);
                    listView.setAdapter(addressAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            cityBean = data.get(position);
                            if (mOnAddressChooseListener!=null) {
                                mOnAddressChooseListener.onChoose(cityBean.getId(), cityBean.getName());
                            }
                            dismiss();
                        }
                    });
                }
            }
        });
    }

    public void getCountyDistricts(int id, final ListView listView) {
        Map<String, String> child_map = new HashMap<>();
        child_map.put("id", id + "");
        HttpUtils.getInstance().get(Constants.CHILD_DISTRICTS, child_map, new HttpCallback<DistrictsBean>() {
            @Override
            public void onSuccessExecute(DistrictsBean districtsBean) {
                final List<DistrictsBean.DataBean> data = districtsBean.getData();
                if (data != null && data.size() > 0) {
                    mTvDialogAddressCounty.setVisibility(View.VISIBLE);
                    mListDialogAddressProvince.setVisibility(View.GONE);
                    mListDialogAddressCity.setVisibility(View.GONE);
                    mListDialogAddressCounty.setVisibility(View.VISIBLE);
                    mTvDialogAddressCounty.setVisibility(View.VISIBLE);
                    mTvDialogAddressCounty.setText(data.get(0).getName());
                    mTvDialogAddressCounty.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListDialogAddressProvince.setVisibility(View.GONE);
                            mListDialogAddressCity.setVisibility(View.GONE);
                            mListDialogAddressCounty.setVisibility(View.VISIBLE);
                        }
                    });
                    AddressAdapter addressAdapter = new AddressAdapter(mContext, data);
                    listView.setAdapter(addressAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            countyBean = data.get(position);
                            mTvDialogAddressCounty.setText(countyBean.getName());
                        }
                    });
                }
            }
        });
    }

    public interface OnAddressChooseListener {
        public void onChoose(int city_id, String cityName);
    }

}
