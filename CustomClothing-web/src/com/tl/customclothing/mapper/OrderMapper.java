package com.tl.customclothing.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.tl.customclothing.util.NullUtils;
import com.tl.customclothing.util.database.ObjectMapper;
import com.tl.customclothing.vo.OrderVo;

public class OrderMapper implements ObjectMapper
{

	@Override
	public Object rowMapper(ResultSet rs) throws SQLException
	{
		OrderVo orderVo = new OrderVo();
		
		orderVo.setOrderId(rs.getInt("orderId"));
		orderVo.setOrderNo(rs.getString("orderNo"));
		orderVo.setShopId(rs.getInt("shopId"));
		orderVo.setUserIdSale(rs.getString("userIdSale"));
		orderVo.setUserIdBuy(rs.getString("userIdBuy"));
		orderVo.setOrderStatus(rs.getString("orderStatus"));
		orderVo.setMoney(rs.getFloat("money"));
		orderVo.setBust(rs.getFloat("bust"));
		orderVo.setWaist(rs.getFloat("waist"));
		orderVo.setHip(rs.getFloat("hip"));
		orderVo.setHandLength(rs.getFloat("handLength"));
		orderVo.setLegLength(rs.getFloat("legLength"));
		
		String time = rs.getString("uploadTime");
		if(!NullUtils.isEmpty(time))
		{
			orderVo.setUploadTime(time.replace(".0", ""));
		}
		
		time = rs.getString("lastUpdateTime");
		if(!NullUtils.isEmpty(time))
		{
			orderVo.setLastUpdateTime(time.replace(".0", ""));
		}
		
		return orderVo;
	}

}
