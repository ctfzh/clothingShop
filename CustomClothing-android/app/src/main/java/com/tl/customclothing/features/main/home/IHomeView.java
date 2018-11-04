package com.tl.customclothing.features.main.home;

import com.tl.customclothing.features.base.IBaseView;

import java.util.List;

/**
 * Created by tianlin on 2017/4/10.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public interface IHomeView extends IBaseView
{
    void setHomeAdapter(List<HomeVo> homeVoList, boolean reloadData);

    void stopRefresh();

}
