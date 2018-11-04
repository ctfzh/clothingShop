package com.tl.customclothing.param.request;

public class QueryShopRequest
{
	private String key;
	
	private String page;

	public String getPage()
	{
		return page;
	}

	public void setPage(String page)
	{
		this.page = page;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	@Override
	public String toString()
	{
		return "QueryShopRequest [key=" + key + ", page=" + page + "]";
	}
	

}
