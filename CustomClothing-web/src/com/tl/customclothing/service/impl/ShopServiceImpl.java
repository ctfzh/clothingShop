package com.tl.customclothing.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.tl.customclothing.dao.ICommentDao;
import com.tl.customclothing.dao.IShopDao;
import com.tl.customclothing.dao.IUserDao;
import com.tl.customclothing.factory.ObjectFactory;
import com.tl.customclothing.param.response.CommentItem;
import com.tl.customclothing.param.response.QueryShopResponseItem;
import com.tl.customclothing.param.response.ShopDetailResponse;
import com.tl.customclothing.service.IShopService;
import com.tl.customclothing.util.Constant;
import com.tl.customclothing.util.NullUtils;
import com.tl.customclothing.util.database.TransactionImpl;
import com.tl.customclothing.vo.CommentVo;
import com.tl.customclothing.vo.ShopVo;
import com.tl.customclothing.vo.UserVo;

public class ShopServiceImpl implements IShopService
{
	IShopDao shopDao;
	IUserDao userDao;
	ICommentDao commentDao;

	public ShopServiceImpl()
	{
		shopDao = (IShopDao) ObjectFactory.get("ShopDaoImpl");
		userDao = (IUserDao) ObjectFactory.get("UserDaoImpl");
		commentDao = (ICommentDao) ObjectFactory.get("CommentDaoImpl");
	}

	@Override
	public ShopDetailResponse getShopDetail(int shopId)
	{
		
		ShopDetailResponse shopDetailResponses = new ShopDetailResponse();
		
		TransactionImpl transactionImpl = (TransactionImpl) ObjectFactory.get("TransactionImpl");
		
		transactionImpl.begin();
		
		List<Object> shops = shopDao.queryById(shopId);
		
		if(!NullUtils.isEmpty(shops))
		{
			ShopVo shopVo = (ShopVo) shops.get(0);
			
			shopDetailResponses.setShopId(shopVo.getShopId());
			shopDetailResponses.setShopNo(shopVo.getShopNo());
			shopDetailResponses.setUserIdSale(shopVo.getUserIdSale());
			shopDetailResponses.setShopType(shopVo.getShopType());
			shopDetailResponses.setShopGender(shopVo.getShopGender());
			
			shopDetailResponses.setShopTag(shopVo.getShopTag());
			shopDetailResponses.setShopImg1(shopVo.getShopImg1());
			shopDetailResponses.setShopImg2(shopVo.getShopImg2());
			shopDetailResponses.setShopImg3(shopVo.getShopImg3());
			shopDetailResponses.setShopMainImg(shopVo.getShopMainImg());
			
			shopDetailResponses.setShopImgOnModel(shopVo.getShopImgOnModel());
			shopDetailResponses.setShopPrice(shopVo.getShopPrice());
			shopDetailResponses.setShopSalesCount(shopVo.getShopSalesCount());
			shopDetailResponses.setLastUpdateTime(shopVo.getLastUpdateTime());
			
			String userSaleId = shopVo.getUserIdSale();
			UserVo user = userDao.queryUserByLoginId(userSaleId);
			
			shopDetailResponses.setShopSalesNickName(user.getUserNickName());
			shopDetailResponses.setShopSalesAddr(user.getUserAddrMain());
			
			List<CommentItem> commentVos = new ArrayList<>();
			List<Object> objects = commentDao.queryByShopId(shopId);

			if(!NullUtils.isEmpty(objects))
			{
				for(int i = 0; i < objects.size(); i++)
				{
					CommentVo commentVo = (CommentVo) objects.get(i);
					CommentItem commentItem = new CommentItem(commentVo);
					
					String userId = commentVo.getUserId();

					UserVo commentUser = userDao.queryUserByLoginId(userId);
					
					if(commentUser != null)
						commentItem.setCommentUserNickName(commentUser.getUserNickName());
						
					commentVos.add(commentItem);
				}
			}
			
			shopDetailResponses.setComments(commentVos);
		}
		
		transactionImpl.commit();
		
		return shopDetailResponses;
	}
	
	
	@Override
	public int getQueryCount(String key)
	{
		int result = 0;
		TransactionImpl transactionImpl = (TransactionImpl) ObjectFactory.get("TransactionImpl");

		transactionImpl.begin();

		List<Object> list = shopDao.queryByKey(key);
		if (!NullUtils.isEmpty(list))
		{
			result = (int) list.get(0);
		}

		transactionImpl.commit();
		return result % Constant.PAGE_SIZE == 0 ? result / Constant.PAGE_SIZE : result / Constant.PAGE_SIZE + 1;
	}

	@Override
	public List<QueryShopResponseItem> queryShopByKey(String key, int page)
	{
		TransactionImpl transactionImpl = (TransactionImpl) ObjectFactory.get("TransactionImpl");

		transactionImpl.begin();

		List<Object> list = shopDao.queryByKey(key, page);

		if (NullUtils.isEmpty(list))
			return null;

		List<QueryShopResponseItem> shopResponseItems = new ArrayList<>();

		for (int i = 0; i < list.size(); i++)
		{
			ShopVo shopVo = (ShopVo) list.get(i);
			QueryShopResponseItem queryShopResponse = new QueryShopResponseItem();

			queryShopResponse.setShopId(shopVo.getShopId());
			queryShopResponse.setUserIdSale(shopVo.getUserIdSale());
			queryShopResponse.setShopType(shopVo.getShopType());
			queryShopResponse.setShopGender(shopVo.getShopGender());
			queryShopResponse.setShopTag(shopVo.getShopTag());
			queryShopResponse.setShopMainImg(shopVo.getShopMainImg());
			queryShopResponse.setShopPrice(shopVo.getShopPrice());
			queryShopResponse.setShopSalesCount(shopVo.getShopSalesCount());
			queryShopResponse.setLastUpdateTime(shopVo.getLastUpdateTime());

			String userIdSale = shopVo.getUserIdSale();
			if (!NullUtils.isEmpty(userIdSale))
			{
				UserVo user = userDao.queryUserByLoginId(userIdSale);

				if (user != null)
				{
					queryShopResponse.setShopSalesNickName(user.getUserNickName());
					queryShopResponse.setShopSalesAddr(user.getUserAddrMain());
				}
			}

			shopResponseItems.add(queryShopResponse);

		}

		transactionImpl.commit();

		return shopResponseItems;
	}

}
