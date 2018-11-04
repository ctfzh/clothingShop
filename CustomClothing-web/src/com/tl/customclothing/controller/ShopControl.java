package com.tl.customclothing.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tl.customclothing.factory.ObjectFactory;
import com.tl.customclothing.param.request.QueryShopRequest;
import com.tl.customclothing.param.request.RequestTemplate;
import com.tl.customclothing.param.request.ShopDetailRequest;
import com.tl.customclothing.param.response.QueryShopResponse;
import com.tl.customclothing.param.response.QueryShopResponseItem;
import com.tl.customclothing.param.response.ResponseTemplate;
import com.tl.customclothing.param.response.ShopDetailResponse;
import com.tl.customclothing.service.IShopService;
import com.tl.customclothing.util.BodyIOUtils;
import com.tl.customclothing.util.Constant;

public class ShopControl
{
	private IShopService shopService;
	
	public ShopControl()
	{
		shopService = (IShopService) ObjectFactory.get("ShopServiceImpl");
	}
	public void detail(HttpServletRequest request,HttpServletResponse response)
	{
		ResponseTemplate<ShopDetailResponse> responseTemplate = new ResponseTemplate<>();
		String json = BodyIOUtils.readBody(request);

		RequestTemplate<ShopDetailRequest> requestTemplate = 
				JSON.parseObject(json, new TypeReference<RequestTemplate<ShopDetailRequest>>(){}.getType());
		if(requestTemplate == null)
		{
			responseTemplate.setServlet("");
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("请求参数为空");
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}
		
		ShopDetailRequest shopDetailRequest = requestTemplate.getContent();
		if(shopDetailRequest == null)
		{
			responseTemplate.setServlet(requestTemplate.getServlet());
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("请求参数为空");
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}
		
		int shopId = shopDetailRequest.getShopId();
		
		ShopDetailResponse shopDetailResponse = shopService.getShopDetail(shopId);
		
		responseTemplate.setContent(shopDetailResponse);
		responseTemplate.setServlet(requestTemplate.getServlet());
		responseTemplate.setStatus(Constant.STATUS_SUCCESS);
		responseTemplate.setMsg("请求成功");
		BodyIOUtils.writeBody(response, responseTemplate);
		
		
	}
	public void query(HttpServletRequest request,HttpServletResponse response)
	{
		ResponseTemplate<QueryShopResponse> responseTemplate = new ResponseTemplate<>();
		QueryShopResponse queryShopResponse = new QueryShopResponse();
		responseTemplate.setContent(queryShopResponse);
		
		String json = BodyIOUtils.readBody(request);
		
		RequestTemplate<QueryShopRequest> requestTemplate = 
				JSON.parseObject(json, new TypeReference<RequestTemplate<QueryShopRequest>>(){}.getType());
		
		if(requestTemplate == null)
		{
			responseTemplate.setServlet("");
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("请求参数为空");
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}
		
		QueryShopRequest queryShopRequest = requestTemplate.getContent();
		if(queryShopRequest == null)
		{
			responseTemplate.setServlet(requestTemplate.getServlet());
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("请求参数为空");
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}
		
		String key = queryShopRequest.getKey();
		String page_str = queryShopRequest.getPage();
		
		int page = 0;
		
		try
		{
			page = Integer.parseInt(page_str);
		}
		catch (Exception e)
		{
			page = 0;
		}
		
		List<QueryShopResponseItem> items = shopService.queryShopByKey(key, page);
		
		int count = shopService.getQueryCount(key);
		
		responseTemplate.setServlet(requestTemplate.getServlet());
		responseTemplate.setStatus(Constant.STATUS_SUCCESS);
		responseTemplate.setMsg("请求成功");
		
		queryShopResponse.setShops(items);
		queryShopResponse.setPageCount(count);
		
		BodyIOUtils.writeBody(response, responseTemplate);
		
	}
}
