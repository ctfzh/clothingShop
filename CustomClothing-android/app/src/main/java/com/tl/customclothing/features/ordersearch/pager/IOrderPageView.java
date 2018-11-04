package com.tl.customclothing.features.ordersearch.pager;

import com.tl.customclothing.features.base.IBaseView;

import java.util.List;

/**
 * Created by tianlin on 2017/4/15.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public interface IOrderPageView extends IBaseView
{
    void setAdapter(List<OrderPageVo> orderPageVos);

    void stopRefresh();
}
