package com.tl.customclothing.param.request;

/**
 * Created by tianlin on 2017/4/25.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class OrderSearchRequest
{
    private String userIdBuy;
    private String orderStatus;

    public String getUserIdBuy()
    {
        return userIdBuy;
    }

    public void setUserIdBuy(String userIdBuy)
    {
        this.userIdBuy = userIdBuy;
    }

    public String getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus)
    {
        this.orderStatus = orderStatus;
    }
}
