package com.tourye.run.ui.activities.home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.CommonBean;
import com.tourye.run.bean.GoodsSizeBean;
import com.tourye.run.bean.UserGiftInfoBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.ShippingAddressAdapter;
import com.tourye.run.ui.adapter.ShippingAddressSizeAdapter;
import com.tourye.run.ui.dialogs.AddressDialog;
import com.tourye.run.ui.dialogs.ShippingAddressDialog;
import com.tourye.run.utils.SaveUtil;
import com.tourye.run.views.MeasureGridView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ShippingAddressActivity
 * @Author: along
 * @Description: 收货地址页面
 * @CreateDate: 2019/3/19 2:25 PM
 */
public class ShippingAddressActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mLlShippingAddressArea;
    private RecyclerView mRecyclerActivityShippingAddress;
    private EditText mEdtActivityShippingAddressName;
    private EditText mEdtActivityShippingAddressPhone;
    private EditText mEdtActivityShippingAddressAge;
    private EditText mEdtActivityShippingAddressAddressDetail;
    private TextView mTvActivityShippingAddressSave;
    private TextView mTvShippingAddressArea;

    private UserGiftInfoBean.DataBean mUserGiftInfoBeanData;

    @Override
    public void initView() {
        mLlShippingAddressArea = (LinearLayout) findViewById(R.id.ll_shipping_address_area);
        mRecyclerActivityShippingAddress = (RecyclerView) findViewById(R.id.recycler_activity_shipping_address);
        mEdtActivityShippingAddressName = (EditText) findViewById(R.id.edt_activity_shipping_address_name);
        mEdtActivityShippingAddressPhone = (EditText) findViewById(R.id.edt_activity_shipping_address_phone);
        mEdtActivityShippingAddressAge = (EditText) findViewById(R.id.edt_activity_shipping_address_age);
        mEdtActivityShippingAddressAddressDetail = (EditText) findViewById(R.id.edt_activity_shipping_address_address_detail);
        mTvActivityShippingAddressSave = (TextView) findViewById(R.id.tv_activity_shipping_address_save);
        mTvShippingAddressArea = (TextView) findViewById(R.id.tv_shipping_address_area);

        mTvTitle.setText("收货地址");

        mLlShippingAddressArea.setOnClickListener(this);
        mTvActivityShippingAddressSave.setOnClickListener(this);
    }

    @Override
    public void initData() {

        Map<String, String> map = new HashMap<>();
        map.put("activity_id", SaveUtil.getString("action_id", ""));
        HttpUtils.getInstance().get(Constants.USER_GIFT_INFO, map, new HttpCallback<UserGiftInfoBean>() {
            @Override
            public void onSuccessExecute(UserGiftInfoBean userGiftInfoBean) {
                mUserGiftInfoBeanData = userGiftInfoBean.getData();
                if (mUserGiftInfoBeanData == null) {
                    return;
                }

                List<UserGiftInfoBean.DataBean.GiftsBean> gifts = mUserGiftInfoBeanData.getGifts();
                if (gifts == null) {
                    return;
                }

                mEdtActivityShippingAddressName.setText(mUserGiftInfoBeanData.getName());
                mEdtActivityShippingAddressPhone.setText(mUserGiftInfoBeanData.getMobile());
                mEdtActivityShippingAddressAge.setText(mUserGiftInfoBeanData.getAge());
                mEdtActivityShippingAddressAddressDetail.setText(mUserGiftInfoBeanData.getAddress());

                ShippingAddressAdapter shippingAddressAdapter = new ShippingAddressAdapter(mActivity, gifts);
                mRecyclerActivityShippingAddress.setNestedScrollingEnabled(false);
                mRecyclerActivityShippingAddress.setHasFixedSize(false);
                mRecyclerActivityShippingAddress.setLayoutManager(new LinearLayoutManager(mActivity));
                mRecyclerActivityShippingAddress.setAdapter(shippingAddressAdapter);

            }
        });
    }

    public void updateData(){
        String name = mEdtActivityShippingAddressName.getText().toString();
        String age = mEdtActivityShippingAddressAge.getText().toString();
        String mobile = mEdtActivityShippingAddressPhone.getText().toString();
        String address = mEdtActivityShippingAddressAddressDetail.getText().toString();

        List<String> titles=Arrays.asList("姓名","年龄","手机","详细地址");
        List<EditText> editTexts=new ArrayList<>();
        editTexts.add(mEdtActivityShippingAddressName);
        editTexts.add(mEdtActivityShippingAddressAge);
        editTexts.add(mEdtActivityShippingAddressPhone);
        editTexts.add(mEdtActivityShippingAddressAddressDetail);

        for (int i = 0; i < editTexts.size(); i++) {
            String temp = editTexts.get(i).getText().toString();
            if (TextUtils.isEmpty(temp)) {
                Toast.makeText(mActivity, "请填写" + titles.get(i) + "信息", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        JsonObject object = new JsonObject();
        List<UserGiftInfoBean.DataBean.GiftsBean> gifts = mUserGiftInfoBeanData.getGifts();
        for (int i = 0; i < gifts.size(); i++) {
            UserGiftInfoBean.DataBean.GiftsBean giftsBean = gifts.get(i);
            List<UserGiftInfoBean.DataBean.GiftsBean.ChoicesBean> choices = giftsBean.getChoices();
            for (int i1 = 0; i1 < choices.size(); i1++) {
                if (choices.get(i1).isSelected()) {
                    object.addProperty(giftsBean.getId()+"",choices.get(i1).getId());
                }
            }
        }

        if (TextUtils.isEmpty(mUserGiftInfoBeanData.getDistrict_3_id())) {
            Toast.makeText(mActivity, "请选择地区", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("activity_id",SaveUtil.getString("action_id", ""));
        jsonObject.addProperty("name",name);
        jsonObject.addProperty("age",age);
        jsonObject.addProperty("mobile",mobile);
        jsonObject.addProperty("district_1_id",mUserGiftInfoBeanData.getDistrict_1_id());
        jsonObject.addProperty("district_2_id",mUserGiftInfoBeanData.getDistrict_2_id());
        jsonObject.addProperty("district_3_id",mUserGiftInfoBeanData.getDistrict_3_id());
        jsonObject.addProperty("address",address);
        jsonObject.add("specifications",object);

        HttpUtils.getInstance().post_json(Constants.USER_GIFT_INFO, jsonObject.toString(), new HttpCallback<CommonBean>() {
            @Override
            public void onSuccessExecute(CommonBean commonBean) {
                if (commonBean.getStatus()==0) {

                }
            }
        });

    }

    @Override
    public int getRootView() {
        return R.layout.activity_shipping_address;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_shipping_address_area:
                ShippingAddressDialog shippingAddressDialog = new ShippingAddressDialog(mActivity);
                shippingAddressDialog.setOnAddressChooseListener(new ShippingAddressDialog.OnAddressChooseListener() {
                    @Override
                    public void onChoose(int province_id, int city_id, int county_id, String address) {
                        mTvShippingAddressArea.setText(address);
                        mUserGiftInfoBeanData.setDistrict_1_id(province_id+"");
                        mUserGiftInfoBeanData.setDistrict_2_id(city_id+"");
                        mUserGiftInfoBeanData.setDistrict_3_id(county_id+"");
                    }
                });
                shippingAddressDialog.show();
                break;
            case R.id.tv_activity_shipping_address_save:
                updateData();
                break;
        }
    }
}
