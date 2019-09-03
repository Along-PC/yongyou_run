package com.tourye.run.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.utils.AreaUtils;

import java.util.List;

/**
 *
 * @ClassName:   AreaAdapter
 *
 * @Author:   along
 *
 * @Description:    国家手机号前缀适配器
 *
 * @CreateDate:   2019/5/21 3:10 PM
 *
 */
public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.AreaHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<AreaUtils.AreaBean> mAreaBeans;

    private OnItemClickListener mOnItemClickListener;
    public AreaAdapter(Context context, List<AreaUtils.AreaBean> areaBeans) {
        mContext = context;
        mAreaBeans = areaBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AreaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AreaHolder(mLayoutInflater.inflate(R.layout.item_activity_area,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AreaHolder areaHolder, int i) {
        final AreaUtils.AreaBean areaBean = mAreaBeans.get(i);
        areaHolder.mTvItemActivityAreaCountry.setText(areaBean.getText());
        areaHolder.mTvItemActivityAreaCode.setText("+"+areaBean.getValue());
        areaHolder.mLlItemActivityAreaRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener!=null) {
                    mOnItemClickListener.onClick(areaBean.getValue());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAreaBeans.size();
    }

    public class AreaHolder extends RecyclerView.ViewHolder {
        private LinearLayout mLlItemActivityAreaRoot;
        private TextView mTvItemActivityAreaCountry;
        private TextView mTvItemActivityAreaCode;

        public AreaHolder(@NonNull View itemView) {
            super(itemView);
            mLlItemActivityAreaRoot = (LinearLayout) itemView.findViewById(R.id.ll_item_activity_area_root);
            mTvItemActivityAreaCountry = (TextView) itemView.findViewById(R.id.tv_item_activity_area_country);
            mTvItemActivityAreaCode = (TextView) itemView.findViewById(R.id.tv_item_activity_area_code);

        }
    }

    public interface OnItemClickListener{
        public void onClick(String code);
    }
}
