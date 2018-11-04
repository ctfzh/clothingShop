package com.tl.customclothing.http.response;


import com.tl.customclothing.model.vo.ShopVo;

import java.util.List;

public class ShopDetailResponse extends ShopVo
{
    // 卖方昵称
    private String shopSalesNickName;
    // 卖方地址
    private String shopSalesAddr;
    // 该条商品的热门评论
    private List<CommentItem> comments;

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

    public List<CommentItem> getComments()
    {
        return comments;
    }

    public void setComments(List<CommentItem> comments)
    {
        this.comments = comments;
    }


}
