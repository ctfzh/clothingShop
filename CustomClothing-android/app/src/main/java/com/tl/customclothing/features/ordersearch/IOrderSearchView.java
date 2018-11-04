package com.tl.customclothing.features.ordersearch;

import com.tl.customclothing.features.base.IBaseView;
import com.tl.customclothing.features.ordersearch.pager.OrderPageVo;

import java.util.List;

/**
 * Created by tianlin on 2017/4/8.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public interface IOrderSearchView extends IBaseView
{
    void setData(int position, List<OrderPageVo> orderPageVos);
}
