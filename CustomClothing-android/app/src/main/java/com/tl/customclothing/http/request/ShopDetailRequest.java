package com.tl.customclothing.http.request;

public class ShopDetailRequest implements BaseRequest
{
    private int shopId;

    public int getShopId()
    {
        return shopId;
    }

    public void setShopId(int shopId)
    {
        this.shopId = shopId;
    }

    @Override
    public String toString()
    {
        return "ShopDetailRequest [shopId=" + shopId + "]";
    }

}
