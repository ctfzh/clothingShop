package com.tl.customclothing.test;

import com.tl.customclothing.dao.impl.CommentDaoImpl;

public class Test_005_queryComment
{

	public static void main(String[] args)
	{
		CommentDaoImpl commentDaoImpl = new CommentDaoImpl();
		System.out.println(commentDaoImpl.queryByShopId(1));
	}
}
