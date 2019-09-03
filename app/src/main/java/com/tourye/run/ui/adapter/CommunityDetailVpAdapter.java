package com.tourye.run.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tourye.run.ui.fragments.BaseCommunityFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longlongren on 2018/10/15.
 * <p>
 * introduce:社区详情viewpager适配器
 */

public class CommunityDetailVpAdapter extends FragmentPagerAdapter {

    private List<BaseCommunityFragment> mFragments=new ArrayList<>();

    public CommunityDetailVpAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<BaseCommunityFragment> fragments) {
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
