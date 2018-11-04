package com.tl.customclothing.test;

import com.tl.customclothing.dao.impl.ShopDaoImpl;

public class Test_004_queryShop
{

	public static void main(String[] args)
	{

		ShopDaoImpl shopDaoImpl = new ShopDaoImpl();
		
		System.out.println(shopDaoImpl.queryById(1));
	}

}
