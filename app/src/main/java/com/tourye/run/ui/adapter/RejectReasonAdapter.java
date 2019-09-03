package com.tourye.run.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.RejectReasonBean;

import java.util.List;

/**
 *
 * @ClassName:   RejectReasonAdapter
 *
 * @Author:   along
 *
 * @Description:    审核打卡不通过列表适配器
 *
 * @CreateDate:   2019/4/29 3:04 PM
 *
 */
public class RejectReasonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<RejectReasonBean> mRejectReasonBeans;

    private int mSelected=998;//单选选中条目

    private OnSelectedListener mOnSelectedListener;

    public RejectReasonAdapter(Context context, List<RejectReasonBean> rejectReasonBeans) {
        mContext = context;
        mRejectReasonBeans = rejectReasonBeans;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        mOnSelectedListener = onSelectedListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==mRejectReasonBeans.size()-1) {
            return 2;
        }else{
            return 1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i==1) {
            return new RejectReasonHolder(mLayoutInflater.inflate(R.layout.item_dialog_reject_reason,viewGroup,false));
        }else{
            return new RejectReasonInputHolder(mLayoutInflater.inflate(R.layout.item_dialog_reject_reason_input,viewGroup,false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        final RejectReasonBean rejectReasonBean = mRejectReasonBeans.get(i);
        int itemViewType = getItemViewType(i);
        if (itemViewType==1) {
            RejectReasonHolder rejectReasonHolder= (RejectReasonHolder) holder;
            rejectReasonHolder.mTvItemRejectReason.setText(rejectReasonBean.getReason());
            rejectReasonHolder.mRbItemRejectReason.setChecked(rejectReasonBean.isSelected());
            rejectReasonHolder.mRbItemRejectReason.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i1 = 0; i1 < mRejectReasonBeans.size(); i1++) {
                        mRejectReasonBeans.get(i1).setSelected(false);
                    }
                    rejectReasonBean.setSelected(true);
                    notifyDataSetChanged();
                    mSelected=i;
                    mOnSelectedListener.onSelected(rejectReasonBean.getReason());
                }
            });
        }else{
            final RejectReasonInputHolder rejectReasonInputHolder= (RejectReasonInputHolder) holder;
            rejectReasonInputHolder.mLlItemRejectReasonInput.setFocusable(true);
            rejectReasonInputHolder.mLlItemRejectReasonInput.setFocusableInTouchMode(true);
            rejectReasonInputHolder.mRbItemRejectReasonInput.setChecked(rejectReasonBean.isSelected());
            rejectReasonInputHolder.mEdtItemRejectReasonInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        if (!rejectReasonBean.isSelected()) {
                            if (mSelected!=998) {
                                mRejectReasonBeans.get(mSelected).setSelected(false);
                                notifyItemChanged(mSelected);
                            }
                            rejectReasonBean.setSelected(true);
                            rejectReasonInputHolder.mRbItemRejectReasonInput.setChecked(true);
                            mSelected=i;
                        }
                    }
                }
            });
            rejectReasonInputHolder.mEdtItemRejectReasonInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String content = s.toString();
                    mOnSelectedListener.onSelected(content);

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            rejectReasonInputHolder.mRbItemRejectReasonInput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i1 = 0; i1 < mRejectReasonBeans.size(); i1++) {
                        mRejectReasonBeans.get(i1).setSelected(false);
                    }
                    rejectReasonBean.setSelected(true);
                    notifyDataSetChanged();
                    mSelected=i;
                    mOnSelectedListener.onSelected(rejectReasonInputHolder.mEdtItemRejectReasonInput.getText().toString());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mRejectReasonBeans.size();
    }

    public class RejectReasonHolder extends RecyclerView.ViewHolder {
        private RadioButton mRbItemRejectReason;
        private TextView mTvItemRejectReason;

        public RejectReasonHolder(@NonNull View itemView) {
            super(itemView);
            mRbItemRejectReason = (RadioButton) itemView.findViewById(R.id.rb_item_reject_reason);
            mTvItemRejectReason = (TextView) itemView.findViewById(R.id.tv_item_reject_reason);

        }
    }

    public class RejectReasonInputHolder extends RecyclerView.ViewHolder {
        private RadioButton mRbItemRejectReasonInput;
        private EditText mEdtItemRejectReasonInput;
        private LinearLayout mLlItemRejectReasonInput;


        public RejectReasonInputHolder(@NonNull View itemView) {
            super(itemView);
            mRbItemRejectReasonInput = (RadioButton) itemView.findViewById(R.id.rb_item_reject_reason_input);
            mEdtItemRejectReasonInput = (EditText) itemView.findViewById(R.id.edt_item_reject_reason_input);
            mLlItemRejectReasonInput = (LinearLayout) itemView.findViewById(R.id.ll_item_reject_reason_input);

        }
    }

    public interface OnSelectedListener{
        public void onSelected(String reason);
    }
}
