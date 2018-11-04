package com.tl.customclothing.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;
import com.tl.customclothing.dao.IOrderDao;
import com.tl.customclothing.dao.IShopDao;
import com.tl.customclothing.dao.IUserDao;
import com.tl.customclothing.factory.ObjectFactory;
import com.tl.customclothing.param.request.OrderSearchRequest;
import com.tl.customclothing.param.response.OrderSearchResponse;
import com.tl.customclothing.param.response.OrderSearchResponseItem;
import com.tl.customclothing.param.response.SubmitOrderResponse;
import com.tl.customclothing.service.IOrderService;
import com.tl.customclothing.util.Constant;
import com.tl.customclothing.util.NullUtils;
import com.tl.customclothing.util.database.TransactionImpl;
import com.tl.customclothing.vo.OrderVo;
import com.tl.customclothing.vo.ShopVo;
import com.tl.customclothing.vo.UserVo;

public class OrderServiceImpl implements IOrderService
{

	IOrderDao orderDao;
	
	IUserDao userDao;
	
	IShopDao shopDao;
	
	public OrderServiceImpl()
	{
		orderDao = (IOrderDao) ObjectFactory.get("OrderDaoImpl");
		userDao = (IUserDao) ObjectFactory.get("UserDaoImpl");
		shopDao = (IShopDao) ObjectFactory.get("ShopDaoImpl");
	}
	
	@Override
	public OrderSearchResponse queryOrder(OrderSearchRequest request)
	{
		
		OrderSearchResponse orderSearchResponse = new OrderSearchResponse();
		List<OrderSearchResponseItem> orderSearchResponses = new ArrayList<>();
		orderSearchResponse.setOrders(orderSearchResponses);
		
		TransactionImpl transactionImpl = (TransactionImpl) ObjectFactory.get("TransactionImpl");
		transactionImpl.begin();
		
		List<Object> orders = orderDao.query(request);
		
		if(NullUtils.isEmpty(orders))
		{
			return orderSearchResponse;
		}
		else
		{
			for(int i = 0; i < orders.size(); i++)
			{
				OrderVo orderVo = (OrderVo) orders.get(i);
				OrderSearchResponseItem item = new OrderSearchResponseItem();
				
				item.setOrderId(orderVo.getOrderId());
				item.setOrderNo(orderVo.getOrderNo());
				item.setShopId(orderVo.getShopId());
				item.setUserIdSale(orderVo.getUserIdSale());
				item.setUserIdBuy(orderVo.getUserIdBuy());
				
				item.setOrderStatus(orderVo.getOrderStatus());
				item.setMoney(orderVo.getMoney());
				item.setBust(orderVo.getBust());
				item.setWaist(orderVo.getWaist());
				item.setHip(orderVo.getHip());
				
				item.setHandLength(orderVo.getHandLength());
				item.setLegLength(orderVo.getLegLength());
				item.setUploadTime(orderVo.getUploadTime());
				item.setLastUpdateTime(orderVo.getLastUpdateTime());
				
				String userIdSale = orderVo.getUserIdSale();
				UserVo userSale = userDao.queryUserByLoginId(userIdSale);
				
				item.setUserNickName(userSale.getUserNickName());
				item.setUserSaleAddr(userSale.getUserAddrMain());
				
				int shopId = orderVo.getShopId();
				List<Object> shops = shopDao.queryById(shopId);
				
				if(!NullUtils.isEmpty(shops))
				{
					ShopVo shopVo = (ShopVo) shops.get(0);
					item.setShopMainImg(shopVo.getShopMainImg());
					item.setShopTag(shopVo.getShopTag());
				}
				
				orderSearchResponses.add(item);
			}
		}
		
		transactionImpl.commit();
		
		return orderSearchResponse;
	}
	
	/**
	 * 该方法需要同步
	 */
	@Override
	public synchronized SubmitOrderResponse submitOrder(OrderVo orderVo)
	{
		SubmitOrderResponse submitOrderResponse = new SubmitOrderResponse();
		TransactionImpl transactionImpl = (TransactionImpl) ObjectFactory.get("TransactionImpl");
		
		transactionImpl.begin();
		
		boolean result = orderDao.insert(orderVo);
		
		if(!result)
		{
			transactionImpl.rollback();
			submitOrderResponse.setResponse(Constant.SUBMIT_FAILED);
		}
		else
		{
			
			transactionImpl.commit();
			submitOrderResponse.setResponse(Constant.SUBMIT_SUCCESS);
		}
		return submitOrderResponse;
	}

}
