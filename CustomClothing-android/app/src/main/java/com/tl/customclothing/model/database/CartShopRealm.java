package com.tl.customclothing.model.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tianlin on 2017/4/24.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class CartShopRealm extends RealmObject
{
    @PrimaryKey
    private int shopId;

    private String shopType;
    private String shopGender;

    private String shopMainImg;
    private float shopPrice;
    private int shopSalesCount;

    private String imgOnModel;
    private String userIdSale;

    public String getUserIdSale()
    {
        return userIdSale;
    }

    public void setUserIdSale(String userIdSale)
    {
        this.userIdSale = userIdSale;
    }

    public String getImgOnModel()
    {
        return imgOnModel;
    }

    public void setImgOnModel(String imgOnModel)
    {
        this.imgOnModel = imgOnModel;
    }

    private long time;

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
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
}
