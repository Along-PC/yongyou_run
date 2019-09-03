package com.tourye.run.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.PackageBean;
import com.tourye.run.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

public class SignupPackageAdapter extends RecyclerView.Adapter<SignupPackageAdapter.SignupPackageHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<PackageBean.DataBean> mDataBeans=new ArrayList<>();
    private OnChooseCallback mOnChooseCallback;

    public SignupPackageAdapter(Context context, List<PackageBean.DataBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOnChooseCallback(OnChooseCallback onChooseCallback) {
        mOnChooseCallback = onChooseCallback;
    }

    @NonNull
    @Override
    public SignupPackageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SignupPackageHolder(mLayoutInflater.inflate(R.layout.item_activity_signup_package,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final SignupPackageHolder signupPackageHolder, final int i) {
        final PackageBean.DataBean dataBean = mDataBeans.get(i);
        signupPackageHolder.mRbItemSignupPackage.setChecked(dataBean.isSelected());
        signupPackageHolder.mRbItemSignupPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dataBean.isSelected()) {
                    for (int i1 = 0; i1 < mDataBeans.size(); i1++) {
                        mDataBeans.get(i1).setSelected(false);
                    }
                    dataBean.setSelected(true);
                    notifyDataSetChanged();
                    mOnChooseCallback.onChoose(i);
                }
            }
        });
        GlideUtils.getInstance().loadRoundImage(dataBean.getImage(),signupPackageHolder.mImgItemSignupPackage);
        GlideUtils.getInstance().loadRoundImage(dataBean.getDetail_image(),signupPackageHolder.mImgItemSignupPackageExtends);
        signupPackageHolder.mImgItemSignupPackageExtends.setVisibility(dataBean.isShowDetailImage()?View.VISIBLE:View.GONE);
        signupPackageHolder.mTvItemSignupPackageName.setText(dataBean.getName());
        signupPackageHolder.mTvItemSignupPackageExtends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v.isSelected()) {
                    v.setSelected(true);
                    signupPackageHolder.mTvItemSignupPackageExtends.setText(mContext.getResources().getText(R.string.pay_package_detail_extends));
                    signupPackageHolder.mImgItemSignupPackageExtends.setVisibility(View.VISIBLE);
                    dataBean.setShowDetailImage(true);
                }else{
                    v.setSelected(false);
                    signupPackageHolder.mTvItemSignupPackageExtends.setText(mContext.getResources().getText(R.string.pay_package_detail_collapse));
                    signupPackageHolder.mImgItemSignupPackageExtends.setVisibility(View.GONE);
                    dataBean.setShowDetailImage(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public class SignupPackageHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItemSignupPackage;
        private TextView mTvItemSignupPackageName;
        private TextView mTvItemSignupPackageExtends;
        private RadioButton mRbItemSignupPackage;
        private ImageView mImgItemSignupPackageExtends;

        public SignupPackageHolder(@NonNull View itemView) {
            super(itemView);
            mImgItemSignupPackage = (ImageView) itemView.findViewById(R.id.img_item_signup_package);
            mTvItemSignupPackageName = (TextView) itemView.findViewById(R.id.tv_item_signup_package_name);
            mTvItemSignupPackageExtends = (TextView) itemView.findViewById(R.id.tv_item_signup_package_extends);
            mRbItemSignupPackage = (RadioButton) itemView.findViewById(R.id.rb_item_signup_package);
            mImgItemSignupPackageExtends = (ImageView) itemView.findViewById(R.id.img_item_signup_package_extends);
        }
    }

    public interface OnChooseCallback{
        public void onChoose(int index);
    }
}
