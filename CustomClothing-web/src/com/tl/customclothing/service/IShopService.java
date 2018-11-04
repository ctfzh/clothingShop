package com.tl.customclothing.service;

import java.util.List;

import com.tl.customclothing.param.response.QueryShopResponseItem;
import com.tl.customclothing.param.response.ShopDetailResponse;

public interface IShopService
{
	/**
	 * 查看指定页的内容
	 * @param key
	 * @param page
	 * @return
	 */
	List<QueryShopResponseItem> queryShopByKey(String key, int page);
	
	/**
	 * 返回指定条件的总页数
	 * @param key
	 * @return
	 */
	int getQueryCount(String key);
	
	/**
	 * 查询商品的详细信息
	 * @param shopId
	 * @return
	 */
	ShopDetailResponse getShopDetail(int shopId);
}
