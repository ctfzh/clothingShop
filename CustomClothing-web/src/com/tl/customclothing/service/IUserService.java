package com.tl.customclothing.service;

import com.tl.customclothing.param.request.QueryUserAddrRequest;
import com.tl.customclothing.param.request.RegisterRequest;
import com.tl.customclothing.param.request.UpdateUserAddrRequest;
import com.tl.customclothing.param.response.QueryUserAddrResponse;
import com.tl.customclothing.param.response.UpdateUserAddrResponse;
import com.tl.customclothing.vo.UserVo;

public interface IUserService
{
	/**
	 * 用户注册
	 * @param request 用户信息
	 */
	boolean register(RegisterRequest request);

	/**
	 * 判断用户是否已经存在
	 * @param userLoginId
	 * @return
	 */
	boolean checkUserExist(String userLoginId);
	
	/**
	 * 查询用户的基本信息
	 * @param userLoginId
	 * @return
	 */
	UserVo queryUserByLoginId(String userLoginId);
	
	/**
	 * 获取用户的地址
	 * @param request
	 * @return
	 */
	QueryUserAddrResponse getUserAddr(QueryUserAddrRequest request);
	
	/**
	 * 更新用户的地址
	 * @param request
	 * @return
	 */
	UpdateUserAddrResponse updateUserAddr(UpdateUserAddrRequest request);
	
	
}
