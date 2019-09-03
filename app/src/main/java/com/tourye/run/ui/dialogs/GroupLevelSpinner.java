package com.tourye.run.ui.dialogs;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.tourye.run.Constants;
import com.tourye.run.R;
import com.tourye.run.bean.BattleGroupBean;
import com.tourye.run.net.HttpCallback;
import com.tourye.run.net.HttpUtils;
import com.tourye.run.ui.adapter.GroupLevelAdapter;
import com.tourye.run.utils.DensityUtils;
import com.tourye.run.utils.SaveUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupLevelSpinner extends PopupWindow {
    private final Context mContext;
    private ListView mListDialogGroupLevelSpinner;
    private OnItemClickListener mOnItemClickListener;

    public GroupLevelSpinner(Context context) {
        super(context);
        mContext = context;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_group_level_spinner, null);
        mListDialogGroupLevelSpinner = (ListView) view.findViewById(R.id.list_dialog_group_level_spinner);
        setContentView(view);

        setWidth(DensityUtils.getScreenWidth());
        setHeight(DensityUtils.dp2px(200));

        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setData(final List<BattleGroupBean.DataBean> data) {

        GroupLevelAdapter groupLevelAdapter = new GroupLevelAdapter(mContext, data);
        mListDialogGroupLevelSpinner.setAdapter(groupLevelAdapter);
        mListDialogGroupLevelSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnItemClickListener != null) {
                    BattleGroupBean.DataBean dataBean = data.get(position);
                    mOnItemClickListener.onItemClick(dataBean.getId(), dataBean.getName());
                }
                dismiss();
            }
        });
    }

    public interface OnItemClickListener {
        public void onItemClick(int level_id, String levelName);
    }

}
