package com.tourye.run.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longlongren on 2019/3/11.
 * <p>
 * introduce:主页vp适配器
 */
public class CommonFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments=new ArrayList<>();
    public CommonFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<Fragment> fragments) {
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
