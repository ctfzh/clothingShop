package com.tl.customclothing.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.tl.customclothing.util.NullUtils;
import com.tl.customclothing.util.database.ObjectMapper;
import com.tl.customclothing.vo.UserVo;

public class UserMapper implements ObjectMapper
{
	@Override
	public Object rowMapper(ResultSet rs) throws SQLException
	{
		UserVo userVo = new UserVo();
		
		userVo.setId(rs.getInt("id"));
		userVo.setUserLoginId(rs.getString("userLoginId"));
		userVo.setUserNickName(rs.getString("userNickName"));
		userVo.setUserRelName(rs.getString("userRelName"));
		userVo.setUserExp(rs.getInt("userExp"));
		userVo.setUserIcon(rs.getString("userIcon"));
		userVo.setUserGender(rs.getString("userGender"));
		userVo.setUserPwd(rs.getString("userPwd"));
		userVo.setUserAddrMain(rs.getString("userAddrMain"));
		userVo.setUserAddr1(rs.getString("userAddr1"));
		userVo.setUserAddr2(rs.getString("userAddr2"));
		
		String time = rs.getString("registerTime");
		if(!NullUtils.isEmpty(time))
			userVo.setRegisterTime(time.replace(".0", ""));
		
		time = rs.getString("lastUpdateTime");
		if(!NullUtils.isEmpty(time))
			userVo.setLastUpdataTime(time.replace(".0", ""));
		
		return userVo;
	}
	

}
