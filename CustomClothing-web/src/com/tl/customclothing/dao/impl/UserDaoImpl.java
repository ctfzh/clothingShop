package com.tl.customclothing.dao.impl;

import java.util.List;

import com.tl.customclothing.dao.IUserDao;
import com.tl.customclothing.mapper.UserMapper;
import com.tl.customclothing.param.request.RegisterRequest;
import com.tl.customclothing.param.request.UpdateUserAddrRequest;
import com.tl.customclothing.util.NullUtils;
import com.tl.customclothing.util.database.JDBCTemplate;
import com.tl.customclothing.vo.UserVo;

public class UserDaoImpl implements IUserDao
{
	
	@Override
	public boolean updateAddr(UpdateUserAddrRequest request)
	{
		JDBCTemplate jdbcTemplate = new JDBCTemplate();
		
		StringBuilder sb = new StringBuilder("update user set ");
		
		if(!NullUtils.isEmpty(request.getUserAddrMain()))
		{
			sb.append("userAddrMain = '" + request.getUserAddrMain() + "' ");
		}
		
		if(!NullUtils.isEmpty(request.getUserAddr1()))
		{
			sb.append(", userAddr1 = '" + request.getUserAddr1() + "' ");
		}
		
		if(!NullUtils.isEmpty(request.getUserAddr2()))
		{
			sb.append(", userAddr2 = '" + request.getUserAddr2() + "' ");
		}
		
		sb.append("where userLoginId = ?");
		
		return jdbcTemplate.update(sb.toString(), new Object[]{request.getUserId()});
	}
	
	@Override
	public boolean insertUser(RegisterRequest request)
	{
		boolean result = false;
		
		String sql = "insert into "
				+ "user(userLoginId, userNickName, userRelName, "
				+ "userExp, userGender, userPwd, "
				+ "userAddrMain, registerTime, "
				+ "lastUpdateTime) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		JDBCTemplate jdbcTemplate = new JDBCTemplate();
		
		result = jdbcTemplate.update(sql, new Object[]{
				request.getUserLoginId(), request.getUserNickName(), request.getUserRelName(), 
				request.getUserExp(), request.getUserGender(), request.getUserPwd(), 
				request.getUserAddrMain(), request.getRegisterTime(), 
				request.getLastUpdateTime()
				
		});
		
		return result;
	}
	
	@Override
	public UserVo queryUserByLoginId(String userLoginId)
	{
		JDBCTemplate jdbcTemplate = new JDBCTemplate();
		
		String sql = "select * from user where userLoginId = ?";
		
		List<Object> list = jdbcTemplate.query(sql, new UserMapper(), new Object[]{userLoginId});
		
		UserVo userVo = null;
		
		if(!NullUtils.isEmpty(list))
		{
			userVo = (UserVo) list.get(0);
		}
		
		return userVo;
	}

}