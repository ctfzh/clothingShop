package com.tl.customclothing.dao;

import java.util.List;

public interface IShopDao
{

	/**
	 * 根据关键字进行信息搜索商品
	 * @param key
	 * @return
	 */
	List<Object> queryByKey(String key, int page);
	
	/**
	 * 查询当前条件的总页数
	 * @param key
	 * @return
	 */
	List<Object> queryByKey(String key);
	
	/**
	 * 根据商品id查询商品信息
	 * @param shopId
	 * @return
	 */
	List<Object> queryById(int shopId);
}
