package com.tl.customclothing.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tl.customclothing.factory.ObjectFactory;
import com.tl.customclothing.param.request.LoginRequest;
import com.tl.customclothing.param.request.QueryUserAddrRequest;
import com.tl.customclothing.param.request.RegisterRequest;
import com.tl.customclothing.param.request.RequestTemplate;
import com.tl.customclothing.param.request.UpdateUserAddrRequest;
import com.tl.customclothing.param.response.LoginResponse;
import com.tl.customclothing.param.response.QueryUserAddrResponse;
import com.tl.customclothing.param.response.RegisterResponse;
import com.tl.customclothing.param.response.ResponseTemplate;
import com.tl.customclothing.param.response.UpdateUserAddrResponse;
import com.tl.customclothing.service.IUserService;
import com.tl.customclothing.util.BodyIOUtils;
import com.tl.customclothing.util.Constant;
import com.tl.customclothing.util.NullUtils;
import com.tl.customclothing.vo.UserVo;

public class UserController
{
	IUserService userService;

	public UserController()
	{
		userService = (IUserService) ObjectFactory.get("UserServiceImpl");
	}
	public void updateAddr(HttpServletRequest request, HttpServletResponse response)
	{
		ResponseTemplate<UpdateUserAddrResponse> responseTemplate = new ResponseTemplate<>();
		
		String json = BodyIOUtils.readBody(request); 
		
		
		RequestTemplate<UpdateUserAddrRequest> requestTemplate = 
				JSON.parseObject(json, new TypeReference<RequestTemplate<UpdateUserAddrRequest>>(){}.getType());
		
		if (requestTemplate == null)
		{
			responseTemplate.setServlet("");
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("请求参数为空");
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}
		
		UpdateUserAddrRequest updateUserAddrRequest = requestTemplate.getContent();
		if(updateUserAddrRequest == null)
		{
			responseTemplate.setServlet(requestTemplate.getServlet());
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("请求参数为空");
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}
		
		UpdateUserAddrResponse updateUserAddrResponse = userService.updateUserAddr(updateUserAddrRequest);
		
		responseTemplate.setServlet(requestTemplate.getServlet());
		responseTemplate.setStatus(Constant.STATUS_SUCCESS);
		responseTemplate.setMsg("更新成功");
		
		responseTemplate.setContent(updateUserAddrResponse);
		BodyIOUtils.writeBody(response, responseTemplate);
		
		
	}

	public void queryAddr(HttpServletRequest request, HttpServletResponse response)
	{
		ResponseTemplate<QueryUserAddrResponse> responseTemplate = new ResponseTemplate<>();
		
		
		String json = BodyIOUtils.readBody(request);
		
		RequestTemplate<QueryUserAddrRequest> requestTemplate = 
				JSON.parseObject(json, new TypeReference<RequestTemplate<QueryUserAddrRequest>>(){}.getType());
		
		if (requestTemplate == null)
		{
			responseTemplate.setServlet("");
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("请求参数为空");
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}
		
		QueryUserAddrRequest queryUserAddrRequest = requestTemplate.getContent();
		if(queryUserAddrRequest == null)
		{
			responseTemplate.setServlet(requestTemplate.getServlet());
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("请求参数为空");
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}
		
		QueryUserAddrResponse queryUserAddrResponse = userService.getUserAddr(queryUserAddrRequest);
		
		responseTemplate.setServlet(requestTemplate.getServlet());
		responseTemplate.setStatus(Constant.STATUS_SUCCESS);
		responseTemplate.setMsg("获取地址信息成功");
		
		responseTemplate.setContent(queryUserAddrResponse);
		BodyIOUtils.writeBody(response, responseTemplate);
		
	}
	public void login(HttpServletRequest request, HttpServletResponse response)
	{
		ResponseTemplate<LoginResponse> responseTemplate = new ResponseTemplate<>();
		LoginResponse loginResponse = new LoginResponse();
		responseTemplate.setContent(loginResponse);

		String json = BodyIOUtils.readBody(request);

		RequestTemplate<LoginRequest> requestTemplate = JSON.parseObject(json,
				new TypeReference<RequestTemplate<LoginRequest>>()
				{
				}.getType());

		if (requestTemplate == null)
		{
			responseTemplate.setServlet("");
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("请求参数为空");
			loginResponse.setResponse(Constant.LOGIN_ERROR);
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}

		LoginRequest loginRequest = requestTemplate.getContent();

		if (loginRequest == null)
		{
			responseTemplate.setServlet(requestTemplate.getServlet());
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("请求参数为空");
			loginResponse.setResponse(Constant.LOGIN_ERROR);
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}

		// 获取用户数据，并非登录
		if (NullUtils.isEmpty(loginRequest.getPassword()))
		{
			UserVo userVo = userService.queryUserByLoginId(loginRequest.getUsername());
			
			if(userVo != null)
			{
				responseTemplate.setServlet(requestTemplate.getServlet());
				responseTemplate.setStatus(Constant.STATUS_SUCCESS);
				responseTemplate.setMsg("获取数据成功");

				loginResponse.setUserNickName(userVo.getUserNickName());
				loginResponse.setUserGender(userVo.getUserGender());
				loginResponse.setUserIcon(userVo.getUserIcon());
				loginResponse.setJifen(userVo.getUserExp());
				loginResponse.setUserId(userVo.getUserLoginId());
				loginResponse.setPassword(userVo.getUserPwd());

				loginResponse.setResponse(Constant.ASYNC_SUCCESS);
			}
			else
			{
				responseTemplate.setServlet(requestTemplate.getServlet());
				responseTemplate.setStatus(Constant.STATUS_ERROR);
				responseTemplate.setMsg("获取数据失败");

				loginResponse.setResponse(Constant.ASYNC_FAILED);
			}
			

			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}

		UserVo userVo = userService.queryUserByLoginId(loginRequest.getUsername());

		if (userVo == null)
		{
			responseTemplate.setServlet(requestTemplate.getServlet());
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("用户名不存在");
			loginResponse.setResponse(Constant.LOGIN_ERROR);
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}

		if (userVo.getUserPwd().equals(loginRequest.getPassword()))
		{
			responseTemplate.setServlet(requestTemplate.getServlet());
			responseTemplate.setStatus(Constant.STATUS_SUCCESS);
			responseTemplate.setMsg("登录成功");

			loginResponse.setResponse(Constant.LOGIN_SUCCESS);
			loginResponse.setUserId(userVo.getUserLoginId());
			loginResponse.setPassword(userVo.getUserPwd());
			loginResponse.setUserNickName(userVo.getUserNickName());
			loginResponse.setUserGender(userVo.getUserGender());
			loginResponse.setUserIcon(userVo.getUserIcon());
			loginResponse.setJifen(userVo.getUserExp());

			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		} else
		{
			responseTemplate.setServlet(requestTemplate.getServlet());
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("密码错误");
			loginResponse.setResponse(Constant.LOGIN_ERROR);

			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}

	}

	public void register(HttpServletRequest request, HttpServletResponse response)
	{

		ResponseTemplate<RegisterResponse> responseTemplate = new ResponseTemplate<>();
		RegisterResponse registerResponse = new RegisterResponse();
		responseTemplate.setContent(registerResponse);

		String json = BodyIOUtils.readBody(request);

		RequestTemplate<RegisterRequest> requestTemplate = JSON.parseObject(json,
				new TypeReference<RequestTemplate<RegisterRequest>>()
				{
				}.getType());

		System.out.println(requestTemplate);

		if (requestTemplate == null)
		{
			responseTemplate.setServlet("");
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("请求参数为空");

			registerResponse.setResponse(Constant.REGISTER_ERROR);
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}

		RegisterRequest registerRequest = requestTemplate.getContent();

		if (registerRequest == null)
		{
			responseTemplate.setServlet(requestTemplate.getServlet());
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("请求参数为空");

			registerResponse.setResponse(Constant.REGISTER_ERROR);
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}

		boolean bool = userService.checkUserExist(registerRequest.getUserLoginId());

		if (bool)
		{
			responseTemplate.setServlet(requestTemplate.getServlet());
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("用户名已存在");

			registerResponse.setResponse(Constant.REGISTER_ERROR);
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}

		boolean result = userService.register(registerRequest);

		if (result)
		{
			// 1，测试发现对象的为null的属性是不会发到json字符串中的
			// 2，如果传入"",能放到json中，对应的值为""
			responseTemplate.setServlet(requestTemplate.getServlet());
			responseTemplate.setStatus(Constant.STATUS_SUCCESS);
			responseTemplate.setMsg("注册成功");

			registerResponse.setResponse(Constant.REGISTER_SUCCESS);
			BodyIOUtils.writeBody(response, responseTemplate);
		} else
		{
			responseTemplate.setServlet(requestTemplate.getServlet());
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("注册失败");

			registerResponse.setResponse(Constant.REGISTER_ERROR);
			BodyIOUtils.writeBody(response, responseTemplate);
		}

	}
}
