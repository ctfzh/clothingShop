package com.tl.customclothing.dao;

import java.util.List;

import com.tl.customclothing.param.request.PostCommentRequest;

public interface ICommentDao
{
	/**
	 * 根据商品id查询商品的评论
	 * @param shopId
	 * @return
	 */
	List<Object> queryByShopId(int shopId);
	
	/**
	 * 提交评论
	 * @param request
	 * @return
	 */
	boolean insert(PostCommentRequest request);
}
