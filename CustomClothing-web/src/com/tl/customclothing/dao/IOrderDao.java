package com.tl.customclothing.dao;

import java.util.List;
import com.tl.customclothing.param.request.OrderSearchRequest;
import com.tl.customclothing.vo.OrderVo;

public interface IOrderDao
{

	/**
	 * 王订单表插入数据
	 * @param orderVo
	 * @return
	 */
	boolean insert(OrderVo orderVo);

	/**
	 * 根据用户id查询不同状态的的订单
	 * @param request
	 * @return
	 */
	List<Object> query(OrderSearchRequest request);
}
