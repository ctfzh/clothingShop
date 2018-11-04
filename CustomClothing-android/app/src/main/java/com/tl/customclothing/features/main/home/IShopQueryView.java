package com.tl.customclothing.features.main.home;

import com.tl.customclothing.features.base.IBaseView;

import java.util.List;

/**
 * Created by tianlin on 2017/5/24.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public interface IShopQueryView extends IBaseView
{
    void setHomeAdapter(List<HomeVo> homeVoList, boolean reloadData);

    void stopRefresh();
}
