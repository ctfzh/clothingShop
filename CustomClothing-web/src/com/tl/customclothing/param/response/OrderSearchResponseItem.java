package com.tl.customclothing.param.response;

import com.tl.customclothing.vo.OrderVo;

public class OrderSearchResponseItem extends OrderVo
{
	private String userSaleAddr;
	private String userNickName;
	
	private String shopMainImg;
	private String shopTag;
	
	
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
	public String getUserSaleAddr()
	{
		return userSaleAddr;
	}
	public void setUserSaleAddr(String userSaleAddr)
	{
		this.userSaleAddr = userSaleAddr;
	}
	public String getUserNickName()
	{
		return userNickName;
	}
	public void setUserNickName(String userNickName)
	{
		this.userNickName = userNickName;
	}
	
}
