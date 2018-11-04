package com.tl.customclothing.features.ordersearch.pager;

import com.tl.customclothing.http.response.OrderSearchResponseItem;

/**
 * Created by tianlin on 2017/4/25.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class OrderPageVo
{
    private int viewType;

    private String noDataDesc;

    private OrderSearchResponseItem item;

    public OrderSearchResponseItem getItem()
    {
        return item;
    }

    public void setItem(OrderSearchResponseItem item)
    {
        this.item = item;
    }

    public int getViewType()
    {
        return viewType;
    }

    public void setViewType(int viewType)
    {
        this.viewType = viewType;
    }

    public String getNoDataDesc()
    {
        return noDataDesc;
    }

    public void setNoDataDesc(String noDataDesc)
    {
        this.noDataDesc = noDataDesc;
    }
}
