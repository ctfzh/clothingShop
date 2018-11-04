package com.tl.customclothing.service.impl;

import com.tl.customclothing.dao.IUserDao;
import com.tl.customclothing.factory.ObjectFactory;
import com.tl.customclothing.param.request.QueryUserAddrRequest;
import com.tl.customclothing.param.request.RegisterRequest;
import com.tl.customclothing.param.request.UpdateUserAddrRequest;
import com.tl.customclothing.param.response.QueryUserAddrResponse;
import com.tl.customclothing.param.response.UpdateUserAddrResponse;
import com.tl.customclothing.service.IUserService;
import com.tl.customclothing.util.database.TransactionImpl;
import com.tl.customclothing.vo.UserVo;

public class UserServiceImpl implements IUserService
{
	private IUserDao userDao;

	public UserServiceImpl()
	{
		userDao = (IUserDao) ObjectFactory.get("UserDaoImpl");
	}

	@Override
	public UpdateUserAddrResponse updateUserAddr(UpdateUserAddrRequest request)
	{
		UpdateUserAddrResponse updateUserAddrResponse = new UpdateUserAddrResponse();
		
		TransactionImpl transactionImpl = (TransactionImpl) ObjectFactory.get("TransactionImpl");
		transactionImpl.begin();
		
		boolean result = userDao.updateAddr(request);
		
		if(result)
		{
			transactionImpl.commit();
			updateUserAddrResponse.setResponse("修改成功");
		}
		else
		{
			transactionImpl.rollback();
			updateUserAddrResponse.setResponse("修改失败");
		}
		
		return updateUserAddrResponse;
	}
	@Override
	public QueryUserAddrResponse getUserAddr(QueryUserAddrRequest request)
	{
		TransactionImpl transactionImpl = (TransactionImpl) ObjectFactory.get("TransactionImpl");
		transactionImpl.begin();
		QueryUserAddrResponse queryUserAddrResponse = new QueryUserAddrResponse();
		UserVo userVo = userDao.queryUserByLoginId(request.getUserId());
		transactionImpl.commit();

		if (userVo != null)
		{
			queryUserAddrResponse.setUserAddrMain(userVo.getUserAddrMain());
			queryUserAddrResponse.setUserAddr1(userVo.getUserAddr1());
			queryUserAddrResponse.setUserAddr2(userVo.getUserAddr2());
		}
		return queryUserAddrResponse;
	}

	@Override
	public UserVo queryUserByLoginId(String userLoginId)
	{
		TransactionImpl transactionImpl = (TransactionImpl) ObjectFactory.get("TransactionImpl");

		transactionImpl.begin();

		UserVo userVo = userDao.queryUserByLoginId(userLoginId);
		transactionImpl.commit();
		return userVo;
	}

	@Override
	public boolean checkUserExist(String userLoginId)
	{
		boolean result = false;
		TransactionImpl transactionImpl = (TransactionImpl) ObjectFactory.get("TransactionImpl");
		transactionImpl.begin();

		UserVo userVo = userDao.queryUserByLoginId(userLoginId);

		if (userVo != null)
			result = true;

		transactionImpl.commit();

		return result;
	}

	@Override
	public boolean register(RegisterRequest request)
	{
		boolean result = false;
		TransactionImpl transactionImpl = (TransactionImpl) ObjectFactory.get("TransactionImpl");
		transactionImpl.begin();

		result = userDao.insertUser(request);

		if (!result)
			transactionImpl.rollback();
		else
			transactionImpl.commit();

		return result;
	}

}
