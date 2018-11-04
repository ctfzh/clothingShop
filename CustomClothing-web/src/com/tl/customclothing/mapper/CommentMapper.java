package com.tl.customclothing.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.tl.customclothing.util.NullUtils;
import com.tl.customclothing.util.database.ObjectMapper;
import com.tl.customclothing.vo.CommentVo;

public class CommentMapper implements ObjectMapper
{
	@Override
	public Object rowMapper(ResultSet rs) throws SQLException
	{
		CommentVo commentVo = new CommentVo();
		
		commentVo.setId(rs.getInt("id"));
		commentVo.setShopId(rs.getInt("shopId"));
		commentVo.setUserId(rs.getString("userId"));
		commentVo.setContent(rs.getString("content"));
		
		commentVo.setOrderId(rs.getInt("orderId"));
		
		String time = rs.getString("commentTime");
		if(!NullUtils.isEmpty(time))
			commentVo.setCommentTime(time.replace(".0", ""));
		
		time = rs.getString("lastUpdateTime");
		if(!NullUtils.isEmpty(time))
			commentVo.setLastUpdateTime(time.replace(".0", ""));
		
		return commentVo;
	}

}
