package com.tl.customclothing.features.shopdetail;

import java.util.List;

/**
 * Created by tianlin on 2017/4/18.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class ShopDetailVo
{
    private int viewType;

    // 商品基本信息
    private int shopId;
    private String shopNo;
    private String userIdSale;
    private String shopType;
    private String shopGender;
    private String shopTag;
    private String shopMainImg;
    private List<String> shopImgs;
    private String shopImgOnModel;
    private float shopPrice;
    private int shopSalesCount;

    // 卖方昵称
    private String shopSalesNickName;
    // 卖方地址
    private String shopSalesAddr;

    // 评论的基本信息
    private int id;
    private String userId;
    private String content;
    private String commentTime;
    private String lastUpdateTime;
    private String commentUserNickName;

    @Override
    public String toString()
    {
        return "ShopDetailVo{" +
                "viewType=" + viewType +
                ", shopId=" + shopId +
                ", shopNo='" + shopNo + '\'' +
                ", userIdSale='" + userIdSale + '\'' +
                ", shopType='" + shopType + '\'' +
                ", shopGender='" + shopGender + '\'' +
                ", shopTag='" + shopTag + '\'' +
                ", shopMainImg='" + shopMainImg + '\'' +
                ", shopImgs=" + shopImgs +
                ", shopImgOnModel='" + shopImgOnModel + '\'' +
                ", shopPrice=" + shopPrice +
                ", shopSalesCount=" + shopSalesCount +
                ", shopSalesNickName='" + shopSalesNickName + '\'' +
                ", shopSalesAddr='" + shopSalesAddr + '\'' +
                ", id=" + id +
                ", userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                ", commentTime='" + commentTime + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                ", commentUserNickName='" + commentUserNickName + '\'' +
                '}';
    }

    public List<String> getShopImgs()
    {
        return shopImgs;
    }

    public void setShopImgs(List<String> shopImgs)
    {
        this.shopImgs = shopImgs;
    }

    public int getViewType()
    {
        return viewType;
    }

    public void setViewType(int viewType)
    {
        this.viewType = viewType;
    }

    public String getCommentUserNickName()
    {
        return commentUserNickName;
    }

    public void setCommentUserNickName(String commentUserNickName)
    {
        this.commentUserNickName = commentUserNickName;
    }

    public int getShopId()
    {
        return shopId;
    }

    public void setShopId(int shopId)
    {
        this.shopId = shopId;
    }

    public String getShopNo()
    {
        return shopNo;
    }

    public void setShopNo(String shopNo)
    {
        this.shopNo = shopNo;
    }

    public String getUserIdSale()
    {
        return userIdSale;
    }

    public void setUserIdSale(String userIdSale)
    {
        this.userIdSale = userIdSale;
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

    public String getShopTag()
    {
        return shopTag;
    }

    public void setShopTag(String shopTag)
    {
        this.shopTag = shopTag;
    }

    public String getShopMainImg()
    {
        return shopMainImg;
    }

    public void setShopMainImg(String shopMainImg)
    {
        this.shopMainImg = shopMainImg;
    }

    public String getShopImgOnModel()
    {
        return shopImgOnModel;
    }

    public void setShopImgOnModel(String shopImgOnModel)
    {
        this.shopImgOnModel = shopImgOnModel;
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

    public String getShopSalesNickName()
    {
        return shopSalesNickName;
    }

    public void setShopSalesNickName(String shopSalesNickName)
    {
        this.shopSalesNickName = shopSalesNickName;
    }

    public String getShopSalesAddr()
    {
        return shopSalesAddr;
    }

    public void setShopSalesAddr(String shopSalesAddr)
    {
        this.shopSalesAddr = shopSalesAddr;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getCommentTime()
    {
        return commentTime;
    }

    public void setCommentTime(String commentTime)
    {
        this.commentTime = commentTime;
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
