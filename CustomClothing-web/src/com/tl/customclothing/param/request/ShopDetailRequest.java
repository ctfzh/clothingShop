package com.tl.customclothing.param.request;

public class ShopDetailRequest
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
