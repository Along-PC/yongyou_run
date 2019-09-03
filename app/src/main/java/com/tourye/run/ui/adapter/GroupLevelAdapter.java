package com.tourye.run.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tourye.run.R;
import com.tourye.run.bean.BattleGroupBean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName:   GroupLevelAdapter
 *
 * @Author:   along
 *
 * @Description:    战队组别列表适配器
 *
 * @CreateDate:   2019/4/1 1:48 PM
 *
 */
public class GroupLevelAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<BattleGroupBean.DataBean> mdatas=new ArrayList<>();

    public GroupLevelAdapter(Context context, List<BattleGroupBean.DataBean> mdatas) {
        mContext = context;
        this.mdatas = mdatas;
        mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mdatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mdatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GroupLevelHolder groupLevelHolder;
        if (convertView==null) {
            convertView=mLayoutInflater.inflate(R.layout.item_dialog_group_level,parent,false);
            groupLevelHolder=new GroupLevelHolder(convertView);
            convertView.setTag(groupLevelHolder);
        }else{
            groupLevelHolder= (GroupLevelHolder) convertView.getTag();
        }
        BattleGroupBean.DataBean dataBean = mdatas.get(position);
        groupLevelHolder.mTvItemDialogGroupLevel.setText(dataBean.getName());
        return convertView;
    }

    public class GroupLevelHolder{
        private TextView mTvItemDialogGroupLevel;

        public GroupLevelHolder(View view){
            mTvItemDialogGroupLevel = (TextView) view.findViewById(R.id.tv_item_dialog_group_level);

        }
    }
}
