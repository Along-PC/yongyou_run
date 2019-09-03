package com.tourye.run.ui.activities.home;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseActivity;
import com.tourye.run.bean.KitBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.activities.common.CommonWebActivity;
import com.tourye.run.ui.adapter.KitAdapter;
import com.tourye.run.utils.GlideUtils;
import com.tourye.run.utils.SaveUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName:   KitActivity
 *
 * @Author:   along
 *
 * @Description:    锦囊页面
 *
 * @CreateDate:   2019/4/2 2:40 PM
 *
 */
public class KitActivity extends BaseActivity {
    private ImageView mImgActivityKit;
    private ListView mListActivityKit;

    @Override
    public void initView() {
        mImgActivityKit = (ImageView) findViewById(R.id.img_activity_kit);
        mListActivityKit = (ListView) findViewById(R.id.list_activity_kit);

        mTvTitle.setText("锦囊");

        GlideUtils.getInstance().loadImage(SaveUtil.getString(SaveConstants.TIPS_COVER,""),mImgActivityKit);
        LayoutInflater layoutInflater = getLayoutInflater();
        View inflate = layoutInflater.inflate(R.layout.item_footer, mListActivityKit, false);
        mListActivityKit.addFooterView(inflate);

    }

    @Override
    public void initData() {
        Map<String,String> map=new HashMap<>();
        map.put("activity_id",SaveUtil.getString("action_id",""));
        HttpUtils.getInstance().get(Constants.ACTION_TIPS, map, new HttpCallback<KitBean>() {

            @Override
            public void onSuccessExecute(KitBean kitBean) {
                final List<KitBean.DataBean> data = kitBean.getData();
                if (data==null) {
                    return;
                }
                KitAdapter kitAdapter = new KitAdapter(mActivity, data);
                mListActivityKit.setAdapter(kitAdapter);
                mListActivityKit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        KitBean.DataBean dataBean = data.get(position);
                        if (!TextUtils.isEmpty(dataBean.getUrl())) {
                            Intent intent = new Intent(mActivity, CommonWebActivity.class);
                            intent.putExtra("url",dataBean.getUrl());
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.activity_kit;
    }
}
