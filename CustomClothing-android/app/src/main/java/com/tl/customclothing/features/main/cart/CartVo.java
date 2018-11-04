package com.tl.customclothing.features.main.cart;

/**
 * Created by tianlin on 2017/4/24.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class CartVo
{
    private int viewType;
    private int shopId = 0;
    private String shopType;
    private String shopGender;
    private String shopMainImg;
    private float shopPrice;
    private int shopSalesCount;

    private String noDataDesc;

    // 删除时，是否被选中，默认为false
    private boolean isSelect = false;

    @Override
    public boolean equals(Object obj)
    {
        CartVo cartVo = (CartVo) obj;
        return this.shopId == cartVo.shopId;
    }

    public boolean isSelect()
    {
        return isSelect;
    }

    public void setSelect(boolean select)
    {
        isSelect = select;
    }

    public int getViewType()
    {
        return viewType;
    }

    public void setViewType(int viewType)
    {
        this.viewType = viewType;
    }

    public int getShopId()
    {
        return shopId;
    }

    public void setShopId(int shopId)
    {
        this.shopId = shopId;
    }

    public String getShopType()
    {
        return shopType;
    }

    public void setShopType(String shopType)
    {
        this.shopType = shopType;
    }

    public String getShopGender()
    {
        return shopGender;
    }

    public void setShopGender(String shopGender)
    {
        this.shopGender = shopGender;
    }

    public String getShopMainImg()
    {
        return shopMainImg;
    }

    public void setShopMainImg(String shopMainImg)
    {
        this.shopMainImg = shopMainImg;
    }

    public float getShopPrice()
    {
        return shopPrice;
    }

    public void setShopPrice(float shopPrice)
    {
        this.shopPrice = shopPrice;
    }

    public int getShopSalesCount()
    {
        return shopSalesCount;
    }

    public void setShopSalesCount(int shopSalesCount)
    {
        this.shopSalesCount = shopSalesCount;
    }

    public String getNoDataDesc()
    {
        return noDataDesc;
    }

    public void setNoDataDesc(String noDataDesc)
    {
        this.noDataDesc = noDataDesc;
    }

    @Override
    public String toString()
    {
        return "CartVo{" +
                "viewType=" + viewType +
                ", shopId=" + shopId +
                ", shopType='" + shopType + '\'' +
                ", shopGender='" + shopGender + '\'' +
                ", shopMainImg='" + shopMainImg + '\'' +
                ", shopPrice=" + shopPrice +
                ", shopSalesCount=" + shopSalesCount +
                ", noDataDesc='" + noDataDesc + '\'' +
                '}';
    }
}
