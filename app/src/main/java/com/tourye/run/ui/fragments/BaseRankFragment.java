package com.tourye.run.ui.fragments;

import com.tourye.run.base.BaseFragment;

/**
 *
 * @ClassName:   BaseRankFragment
 *
 * @Author:   along
 *
 * @Description:    战队详情排行榜父页面
 *
 * @CreateDate:   2019/4/16 5:58 PM
 *
 */
public abstract class BaseRankFragment extends BaseFragment {

    public abstract void changeOrder();

    public abstract void updateThumbStatus(int user_id,boolean isThumb,int thumbCount);


}
