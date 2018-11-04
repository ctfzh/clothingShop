package com.tl.customclothing.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tl.customclothing.factory.ObjectFactory;
import com.tl.customclothing.param.request.OrderSearchRequest;
import com.tl.customclothing.param.request.RequestTemplate;
import com.tl.customclothing.param.request.SubmitOrderRequest;
import com.tl.customclothing.param.response.OrderSearchResponse;
import com.tl.customclothing.param.response.ResponseTemplate;
import com.tl.customclothing.param.response.SubmitOrderResponse;
import com.tl.customclothing.service.IOrderService;
import com.tl.customclothing.util.BodyIOUtils;
import com.tl.customclothing.util.Constant;

public class OrderControl
{
	IOrderService orderService;
	
	public OrderControl()
	{
		orderService = (IOrderService) ObjectFactory.get("OrderServiceImpl");
	}
	
	public void search(HttpServletRequest request,HttpServletResponse response)
	{
		ResponseTemplate<OrderSearchResponse> responseTemplate = new ResponseTemplate<>();
		String json = BodyIOUtils.readBody(request);
	
		RequestTemplate<OrderSearchRequest> requestTemplate = 
				JSON.parseObject(json, new TypeReference<RequestTemplate<OrderSearchRequest>>(){}.getType());
		
		if(requestTemplate == null)
		{
			responseTemplate.setServlet("");
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("请求参数为空");
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}
		
		OrderSearchRequest orderSearchRequest = requestTemplate.getContent();
		
		if(orderSearchRequest == null)
		{
			responseTemplate.setServlet(requestTemplate.getServlet());
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("请求参数为空");
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}
		
		OrderSearchResponse orderSearchResponse = orderService.queryOrder(orderSearchRequest);
		
		responseTemplate.setServlet(requestTemplate.getServlet());
		responseTemplate.setStatus(Constant.STATUS_SUCCESS);
		responseTemplate.setMsg("请求成功");
		responseTemplate.setContent(orderSearchResponse);
		
		BodyIOUtils.writeBody(response, responseTemplate);
		
	}
	public void submit(HttpServletRequest request,HttpServletResponse response)
	{
		ResponseTemplate<SubmitOrderResponse> responseTemplate = new ResponseTemplate<>();
		
		String json = BodyIOUtils.readBody(request);
		
		RequestTemplate<SubmitOrderRequest> requestTemplate = 
				JSON.parseObject(json, new TypeReference<RequestTemplate<SubmitOrderRequest>>(){}.getType());
		
		if(requestTemplate == null)
		{
			responseTemplate.setServlet("");
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("请求参数为空");
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}
		
		SubmitOrderRequest submitOrderRequest = requestTemplate.getContent();
		if(submitOrderRequest == null)
		{
			responseTemplate.setServlet(requestTemplate.getServlet());
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("请求参数为空");
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}
		
		SubmitOrderResponse submitOrderResponse = orderService.submitOrder(submitOrderRequest);
		
		responseTemplate.setServlet(requestTemplate.getServlet());
		responseTemplate.setStatus(Constant.STATUS_ERROR);
		responseTemplate.setMsg("请求成功");
		responseTemplate.setContent(submitOrderResponse);
		
		BodyIOUtils.writeBody(response, responseTemplate);
	}
}
