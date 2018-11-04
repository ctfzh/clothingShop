package com.tl.customclothing.dao;

import com.tl.customclothing.param.request.RegisterRequest;
import com.tl.customclothing.param.request.UpdateUserAddrRequest;
import com.tl.customclothing.vo.UserVo;

public interface IUserDao
{

	/**
	 * 向user表新增用户
	 * @param request 请求参数
	 */
	boolean insertUser(RegisterRequest request);
	
	/**
	 * 根据用户id查询用户信息
	 * @param userLoginId
	 * @return
	 */
	UserVo queryUserByLoginId(String userLoginId);
	
	boolean updateAddr(UpdateUserAddrRequest request);
}
