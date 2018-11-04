package com.tl.customclothing.param.response;

import java.util.List;

public class QueryShopResponse
{
	
	private int pageCount = 0;
	
	private List<QueryShopResponseItem> shops;
	
	public int getPageCount()
	{
		return pageCount;
	}

	public void setPageCount(int pageCount)
	{
		this.pageCount = pageCount;
	}

	public List<QueryShopResponseItem> getShops()
	{
		return shops;
	}

	public void setShops(List<QueryShopResponseItem> shops)
	{
		this.shops = shops;
	}
	
	
}
