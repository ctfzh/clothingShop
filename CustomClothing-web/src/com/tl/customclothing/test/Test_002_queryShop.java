package com.tl.customclothing.test;

import java.util.List;

import com.tl.customclothing.dao.impl.ShopDaoImpl;

public class Test_002_queryShop
{
	public static void main(String[] args)
	{
		ShopDaoImpl shopDaoImpl = new ShopDaoImpl();
		List list = shopDaoImpl.queryByKey("上衣", 3);
		System.out.println(list.size());
		
		System.out.println(shopDaoImpl.queryByKey(""));
	}

}
