package com.tl.customclothing.param.response;

import java.util.List;

public class OrderSearchResponse 
{
	private List<OrderSearchResponseItem> orders;

	public List<OrderSearchResponseItem> getOrders()
	{
		return orders;
	}

	public void setOrders(List<OrderSearchResponseItem> orders)
	{
		this.orders = orders;
	}
	
	
	
}
