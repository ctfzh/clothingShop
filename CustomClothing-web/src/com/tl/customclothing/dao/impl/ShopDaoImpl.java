package com.tl.customclothing.dao.impl;

import java.util.List;

import com.tl.customclothing.dao.IShopDao;
import com.tl.customclothing.mapper.CountMapper;
import com.tl.customclothing.mapper.ShopMapper;
import com.tl.customclothing.util.Constant;
import com.tl.customclothing.util.database.JDBCTemplate;

public class ShopDaoImpl implements IShopDao
{
	
	@Override
	public List<Object> queryById(int shopId)
	{
		JDBCTemplate jdbcTemplate = new JDBCTemplate();
		String sql = "select * from shop where shopId = ?";
		return jdbcTemplate.query(sql, new ShopMapper(), new Object[]{shopId});
	}
	
	@Override
	public List<Object> queryByKey(String key, int page)
	{
		JDBCTemplate jdbcTemplate = new JDBCTemplate();

		StringBuilder sb = new StringBuilder(100).append("select * from shop where ").append("shopType like ? or ")
				.append("shopTag like ? ")
				.append("limit " + (page - 1) * Constant.PAGE_SIZE + "," + Constant.PAGE_SIZE);

		List<Object> queryList = jdbcTemplate.query(sb.toString(), new ShopMapper(), new Object[]
		{ "%" + key + "%", "%" + key + "%" });

		return queryList;
	}

	@Override
	public List<Object> queryByKey(String key)
	{
		JDBCTemplate jdbcTemplate = new JDBCTemplate();

		StringBuilder sb = new StringBuilder(100)
				.append("select count(*) from shop where ")
				.append("shopType like ? or ")
				.append("shopTag like ? ");

		List<Object> countList = jdbcTemplate.query(sb.toString(), new CountMapper(), new Object[]
		{ "%" + key + "%", "%" + key + "%" });
		
		return countList;
	}

}
