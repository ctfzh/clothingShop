package com.tl.customclothing.dao.impl;

import java.util.List;

import com.tl.customclothing.dao.ICommentDao;
import com.tl.customclothing.mapper.CommentMapper;
import com.tl.customclothing.param.request.PostCommentRequest;
import com.tl.customclothing.util.database.JDBCTemplate;

public class CommentDaoImpl implements ICommentDao
{

	@Override
	public List<Object> queryByShopId(int shopId)
	{
		JDBCTemplate jdbcTemplate = new JDBCTemplate();

		String sql = "select * from comment where shopId = ? order by commentTime desc";
		
		List<Object> list = jdbcTemplate.query(sql, new CommentMapper(), new Object[]{shopId});
		
		return list;
	}
	
	@Override
	public boolean insert(PostCommentRequest request)
	{
		
		JDBCTemplate jdbcTemplate = new JDBCTemplate();
		
		String sql = "insert into comment(shopId, userId, content, "
				+ "commentTime, lastUpdateTime, orderId) values(?, ?, ?, ?, ?, ?)";
		
		return jdbcTemplate.update(sql, new Object[]{
				request.getShopId(), request.getUserId(), request.getContent(), 
				request.getCommentTime(), request.getLastUpdateTime(), request.getOrderId()
		});
	}

}
