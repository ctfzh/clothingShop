package com.tl.customclothing.dao.impl;

import java.util.List;

import com.tl.customclothing.dao.IOrderDao;
import com.tl.customclothing.mapper.OrderMapper;
import com.tl.customclothing.param.request.OrderSearchRequest;
import com.tl.customclothing.util.NullUtils;
import com.tl.customclothing.util.database.JDBCTemplate;
import com.tl.customclothing.vo.OrderVo;

public class OrderDaoImpl implements IOrderDao
{

	@Override
	public boolean insert(OrderVo orderVo)
	{
		boolean result = false;
		JDBCTemplate jdbcTemplate = new JDBCTemplate();

		String sql = "insert into orders(orderNo, shopId, userIdSale, "
				+ "userIdBuy, orderStatus, money, "
				+ "bust, waist, hip, "
				+ "handLength, legLength, uploadTime, "
				+ "lastUpdateTime) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		result = jdbcTemplate.update(sql, new Object[]{
				orderVo.getOrderNo(), orderVo.getShopId(), orderVo.getUserIdSale(), 
				orderVo.getUserIdBuy(), orderVo.getOrderStatus(), orderVo.getMoney(), 
				orderVo.getBust(), orderVo.getWaist(), orderVo.getHip(), 
				orderVo.getHandLength(), orderVo.getLegLength(), orderVo.getUploadTime(), 
				orderVo.getLastUpdateTime()
				
		});
		return result;
	}
	
	@Override
	public List<Object> query(OrderSearchRequest request)
	{
		JDBCTemplate jdbcTemplate = new JDBCTemplate();
		StringBuilder sb = new StringBuilder("select * from orders where userIdBuy = " + request.getUserIdBuy());
		
		if(!NullUtils.isEmpty(request.getOrderStatus()))
		{
			sb.append(" and orderStatus = '" + request.getOrderStatus() + "'");
		}
		
		sb.append(" order by uploadTime desc");
		
		return jdbcTemplate.query(sb.toString(), new OrderMapper(), null);
	}

}
