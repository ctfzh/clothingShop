package com.tl.customclothing.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.tl.customclothing.util.NullUtils;
import com.tl.customclothing.util.database.ObjectMapper;
import com.tl.customclothing.vo.ShopVo;

public class ShopMapper implements ObjectMapper
{

	@Override
	public Object rowMapper(ResultSet rs) throws SQLException
	{
		ShopVo shopVo = new ShopVo();
		
		shopVo.setShopId(rs.getInt("shopId"));
		shopVo.setShopNo(rs.getString("shopNo"));
		shopVo.setUserIdSale(rs.getString("userIdSale"));
		shopVo.setShopType(rs.getString("shopType"));
		shopVo.setShopGender(rs.getString("shopGender"));
		
		shopVo.setShopTag(rs.getString("shopTag"));
		shopVo.setShopMainImg(rs.getString("shopMainImg"));
		shopVo.setShopImg1(rs.getString("shopImg1"));
		shopVo.setShopImg2(rs.getString("shopImg2"));
		shopVo.setShopImg3(rs.getString("shopImg3"));
		
		shopVo.setShopImgOnModel(rs.getString("shopImgOnModel"));
		shopVo.setShopPrice(rs.getFloat("shopPrice"));
		shopVo.setShopSalesCount(rs.getInt("shopSalesCount"));
		
		String time = rs.getString("uploadTime");
		if(!NullUtils.isEmpty(time))
			shopVo.setUploadTime(time.replace(".0", ""));
		
		time = rs.getString("lastUpdateTime");
		if(!NullUtils.isEmpty(time))
			shopVo.setLastUpdateTime(time.replace(".0", ""));
		
		return shopVo;
	}
}
