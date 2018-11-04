package com.tl.customclothing.model.vo;

public class OrderVo
{

    private int orderId;

    private String orderNo;

    private int shopId;

    private String userIdSale;

    private String userIdBuy;

    private String orderStatus;

    private float money;

    private float bust;

    private float waist;

    private float hip;

    private float handLength;

    private float legLength;

    private String uploadTime;

    private String lastUpdateTime;

    public float getMoney()
    {
        return money;
    }


    public int getOrderId()
    {
        return orderId;
    }


    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }


    public void setMoney(float money)
    {
        this.money = money;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public int getShopId()
    {
        return shopId;
    }

    public void setShopId(int shopId)
    {
        this.shopId = shopId;
    }

    public String getUserIdSale()
    {
        return userIdSale;
    }

    public void setUserIdSale(String userIdSale)
    {
        this.userIdSale = userIdSale;
    }

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

    public float getBust()
    {
        return bust;
    }

    public void setBust(float bust)
    {
        this.bust = bust;
    }

    public float getWaist()
    {
        return waist;
    }

    public void setWaist(float waist)
    {
        this.waist = waist;
    }

    public float getHip()
    {
        return hip;
    }

    public void setHip(float hip)
    {
        this.hip = hip;
    }

    public float getHandLength()
    {
        return handLength;
    }

    public void setHandLength(float handLength)
    {
        this.handLength = handLength;
    }

    public float getLegLength()
    {
        return legLength;
    }

    public void setLegLength(float legLength)
    {
        this.legLength = legLength;
    }

    public String getUploadTime()
    {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime)
    {
        this.uploadTime = uploadTime;
    }

    public String getLastUpdateTime()
    {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }

}
