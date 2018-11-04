package com.tl.customclothing.service;

import com.tl.customclothing.param.request.OrderSearchRequest;
import com.tl.customclothing.param.response.OrderSearchResponse;
import com.tl.customclothing.param.response.SubmitOrderResponse;
import com.tl.customclothing.vo.OrderVo;

public interface IOrderService
{
	/**
	 * 提交订单
	 * @param orderVo
	 * @return
	 */
	SubmitOrderResponse submitOrder(OrderVo orderVo);
	
	/**
	 * 订单查询
	 * @param request
	 * @return
	 */
	OrderSearchResponse queryOrder(OrderSearchRequest request);
}
