package com.tourye.run.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.LogisticsBean;
import com.tourye.run.ui.activities.common.CommonWebActivity;
import com.tourye.run.ui.dialogs.AfterSaleServiceDialog;
import com.tourye.run.utils.GlideUtils;

import java.util.List;

/**
 * @ClassName: LogisticsAdapter
 * @Author: along
 * @Description: 物流列表适配器
 * @CreateDate: 2019/4/29 10:00 AM
 */
public class LogisticsAdapter extends RecyclerView.Adapter<LogisticsAdapter.LogisticsHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<LogisticsBean.DataBean> mDataBeans;

    public LogisticsAdapter(Context context, List<LogisticsBean.DataBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public LogisticsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LogisticsHolder(mLayoutInflater.inflate(R.layout.item_activity_logistics, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LogisticsHolder logisticsHolder, int i) {
        final LogisticsBean.DataBean dataBean = mDataBeans.get(i);

        GlideUtils.getInstance().loadImage(dataBean.getImage(), logisticsHolder.mImgItemActivityLogisticsPhoto);
        logisticsHolder.mTvItemActivityLogisticsGoodsName.setText(dataBean.getName());
        logisticsHolder.mTvItemActivityLogisticsGoodsCount.setText("x1");
        String spec = dataBean.getSpec();
        if (TextUtils.isEmpty(spec)) {
            logisticsHolder.mTvItemActivityLogisticsGoodsSize.setVisibility(View.GONE);
        }else{
            logisticsHolder.mTvItemActivityLogisticsGoodsSize.setVisibility(View.VISIBLE);
            logisticsHolder.mTvItemActivityLogisticsGoodsSize.setText(dataBean.getSpec());
        }
        if ("pending".equalsIgnoreCase(dataBean.getStatus())) {
            logisticsHolder.mTvItemActivityLogisticsStatus.setText("待发货");
            logisticsHolder.mTvItemActivityLogisticsViewLogistics.setVisibility(View.GONE);
        } else if ("sent".equalsIgnoreCase(dataBean.getStatus())) {
            logisticsHolder.mTvItemActivityLogisticsStatus.setText("已发货");
            logisticsHolder.mTvItemActivityLogisticsViewLogistics.setVisibility(View.VISIBLE);
        }

        String after_sale_image = dataBean.getAfter_sale_image();
        String after_sale_phone = dataBean.getAfter_sale_phone();
        String after_sale_text = dataBean.getAfter_sale_text();
        if (!TextUtils.isEmpty(after_sale_text) || !TextUtils.isEmpty(after_sale_phone) || !TextUtils.isEmpty(after_sale_image)) {
            logisticsHolder.mTvItemActivityLogisticsSaleService.setVisibility(View.VISIBLE);
        } else {
            logisticsHolder.mTvItemActivityLogisticsSaleService.setVisibility(View.GONE);
        }
        logisticsHolder.mTvItemActivityLogisticsSaleService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AfterSaleServiceDialog afterSaleServiceDialog = new AfterSaleServiceDialog(mContext);
                afterSaleServiceDialog.setDataBean(dataBean);
                afterSaleServiceDialog.show();
            }
        });
        logisticsHolder.mTvItemActivityLogisticsViewLogistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommonWebActivity.class);
                intent.putExtra("url", "https://m.kuaidi100.com/index_all.html?type=" + dataBean.getExpress_company() + "&postid=" + dataBean.getExpress_number());
                intent.putExtra("title", "查看物流");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public class LogisticsHolder extends RecyclerView.ViewHolder {
        private TextView mTvItemActivityLogisticsStatus;
        private TextView mTvItemActivityLogisticsGoodsName;
        private TextView mTvItemActivityLogisticsGoodsSize;
        private TextView mTvItemActivityLogisticsGoodsCount;
        private TextView mTvItemActivityLogisticsSaleService;
        private TextView mTvItemActivityLogisticsViewLogistics;
        private ImageView mImgItemActivityLogisticsPhoto;

        public LogisticsHolder(@NonNull View itemView) {
            super(itemView);
            mTvItemActivityLogisticsStatus = (TextView) itemView.findViewById(R.id.tv_item_activity_logistics_status);
            mTvItemActivityLogisticsGoodsName = (TextView) itemView.findViewById(R.id.tv_item_activity_logistics_goods_name);
            mTvItemActivityLogisticsGoodsSize = (TextView) itemView.findViewById(R.id.tv_item_activity_logistics_goods_size);
            mTvItemActivityLogisticsGoodsCount = (TextView) itemView.findViewById(R.id.tv_item_activity_logistics_goods_count);
            mTvItemActivityLogisticsSaleService = (TextView) itemView.findViewById(R.id.tv_item_activity_logistics_sale_service);
            mTvItemActivityLogisticsViewLogistics = (TextView) itemView.findViewById(R.id.tv_item_activity_logistics_view_logistics);
            mImgItemActivityLogisticsPhoto = (ImageView) itemView.findViewById(R.id.img_item_activity_logistics_photo);

        }
    }
}
