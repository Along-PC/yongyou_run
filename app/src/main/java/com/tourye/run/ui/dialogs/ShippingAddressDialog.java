package com.tourye.run.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.bean.DistrictsBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.AddressAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:   ShippingAddressDialog
 *
 * @Author:   along
 *
 * @Description:    收获地址地区选择弹框
 *
 * @CreateDate:   2019/4/26 1:39 PM
 *
 */
public class ShippingAddressDialog extends BaseDialog implements View.OnClickListener{

    private Context mContext;
    private TextView mTvDialogAddressCancel;
    private TextView mTvDialogAddressCertain;
    private TextView mTvDialogAddressProvince;
    private TextView mTvDialogAddressCity;
    private TextView mTvDialogAddressCounty;
    private ListView mListDialogAddressProvince;
    private ListView mListDialogAddressCity;
    private ListView mListDialogAddressCounty;
    private RelativeLayout mRlDialogAddressBar;

    private DistrictsBean.DataBean provinceBean;
    private DistrictsBean.DataBean cityBean;
    private DistrictsBean.DataBean countyBean;

    private int province_id;
    private int city_id;
    private int county_id;
    private String address;

    private OnAddressChooseListener mOnAddressChooseListener;

    public ShippingAddressDialog(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    protected void initData() {
        getTopDistricts();
    }

    public void setOnAddressChooseListener(OnAddressChooseListener onAddressChooseListener) {
        mOnAddressChooseListener = onAddressChooseListener;
    }

    @Override
    protected void initView() {
        mTvDialogAddressCancel = (TextView) findViewById(R.id.tv_dialog_address_cancel);
        mTvDialogAddressCertain = (TextView) findViewById(R.id.tv_dialog_address_certain);
        mTvDialogAddressProvince = (TextView) findViewById(R.id.tv_dialog_address_province);
        mTvDialogAddressCity = (TextView) findViewById(R.id.tv_dialog_address_city);
        mTvDialogAddressCounty = (TextView) findViewById(R.id.tv_dialog_address_county);
        mListDialogAddressProvince = (ListView) findViewById(R.id.list__dialog_address_province);
        mListDialogAddressCity = (ListView) findViewById(R.id.list__dialog_address_city);
        mListDialogAddressCounty = (ListView) findViewById(R.id.list__dialog_address_county);
        mRlDialogAddressBar = (RelativeLayout) findViewById(R.id.rl_dialog_address_bar);

        mRlDialogAddressBar.setVisibility(View.VISIBLE);
        mTvDialogAddressCancel.setOnClickListener(this);
        mTvDialogAddressCertain.setOnClickListener(this);

    }

    public void getTopDistricts(){
        Map<String,String> map=new HashMap<>();
        HttpUtils.getInstance().get(Constants.TOP_DISTRICTS, map, new HttpCallback<DistrictsBean>() {

            @Override
            public void onSuccessExecute(DistrictsBean districtsBean) {
                final List<DistrictsBean.DataBean> data = districtsBean.getData();
                if (data!=null && data.size()>0) {
                    mTvDialogAddressProvince.setText(data.get(0).getName());
                    mTvDialogAddressProvince.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTvDialogAddressCity.setVisibility(View.GONE);
                            mTvDialogAddressCounty.setVisibility(View.GONE);
                            mListDialogAddressProvince.setVisibility(View.VISIBLE);
                            mListDialogAddressCity.setVisibility(View.GONE);
                            mListDialogAddressCounty.setVisibility(View.GONE);
                            province_id=0;
                            city_id=0;
                            county_id=0;
                        }
                    });
                    AddressAdapter addressAdapter = new AddressAdapter(mContext, data);
                    mListDialogAddressProvince.setAdapter(addressAdapter);
                    mListDialogAddressProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            provinceBean=data.get(position);
                            province_id=provinceBean.getId();
                            mTvDialogAddressProvince.setText(provinceBean.getName());
                            getChildDistricts(data.get(position).getId(),mListDialogAddressCity);
                        }
                    });
                }
            }
        });


    }

    public void getChildDistricts(int id, final ListView listView){
        Map<String,String> child_map=new HashMap<>();
        child_map.put("id",id+"");
        HttpUtils.getInstance().get(Constants.CHILD_DISTRICTS, child_map, new HttpCallback<DistrictsBean>() {
            @Override
            public void onSuccessExecute(DistrictsBean districtsBean) {
                final List<DistrictsBean.DataBean> data = districtsBean.getData();
                if (data!=null && data.size()>0) {
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
                            city_id=0;
                            county_id=0;
                        }
                    });
                    AddressAdapter addressAdapter = new AddressAdapter(mContext, data);
                    listView.setAdapter(addressAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            cityBean=data.get(position);
                            if (cityBean.getLevel()==2) {
                                city_id=cityBean.getId();
                            }else{
                                county_id=cityBean.getId();
                            }
                            mTvDialogAddressCity.setText(cityBean.getName());
                            getCountyDistricts(data.get(position).getId(),mListDialogAddressCounty);
                        }
                    });
                }
            }
        });
    }

    public void getCountyDistricts(int id, final ListView listView){
        Map<String,String> child_map=new HashMap<>();
        child_map.put("id",id+"");
        HttpUtils.getInstance().get(Constants.CHILD_DISTRICTS, child_map, new HttpCallback<DistrictsBean>() {
            @Override
            public void onSuccessExecute(DistrictsBean districtsBean) {
                final List<DistrictsBean.DataBean> data = districtsBean.getData();
                if (data!=null && data.size()>0) {
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
                            countyBean=data.get(position);
                            county_id=countyBean.getId();
                            mTvDialogAddressCounty.setText(countyBean.getName());
                        }
                    });
                }
            }
        });
    }

    @Override
    protected int getRootView() {
        return R.layout.dialog_address;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dialog_address_cancel:
                dismiss();
                break;
            case R.id.tv_dialog_address_certain:
                if (mOnAddressChooseListener!=null) {
                    address=mTvDialogAddressProvince.getText().toString()+mTvDialogAddressCity.getText().toString()+mTvDialogAddressCounty.getText().toString();
                    mOnAddressChooseListener.onChoose(province_id,city_id,county_id,address);
                }
                dismiss();
                break;
        }
    }

    public interface OnAddressChooseListener{
        public void onChoose(int province_id,int city_id,int county_id,String address);
    }

}
