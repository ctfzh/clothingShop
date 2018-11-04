package com.tl.customclothing.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tl.customclothing.factory.ObjectFactory;
import com.tl.customclothing.param.request.PostCommentRequest;
import com.tl.customclothing.param.request.RequestTemplate;
import com.tl.customclothing.param.response.PostCommentResponse;
import com.tl.customclothing.param.response.ResponseTemplate;
import com.tl.customclothing.service.ICommentService;
import com.tl.customclothing.util.BodyIOUtils;
import com.tl.customclothing.util.Constant;

public class CommentControl
{
	ICommentService commentService;
	
	public CommentControl()
	{
		commentService = (ICommentService) ObjectFactory.get("CommentServiceImpl");
	}
	public void post(HttpServletRequest request,HttpServletResponse response)
	{
		ResponseTemplate<PostCommentResponse> responseTemplate = new ResponseTemplate<>();
		
		String json = BodyIOUtils.readBody(request);
		
		RequestTemplate<PostCommentRequest> requestTemplate = 
				JSON.parseObject(json, new TypeReference<RequestTemplate<PostCommentRequest>>(){}.getType());
		
		if(requestTemplate == null)
		{
			responseTemplate.setServlet("");
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("请求参数为空");
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}
		
		PostCommentRequest postCommentRequest = requestTemplate.getContent();
		
		if(postCommentRequest == null)
		{
			responseTemplate.setServlet(requestTemplate.getServlet());
			responseTemplate.setStatus(Constant.STATUS_ERROR);
			responseTemplate.setMsg("请求参数为空");
			BodyIOUtils.writeBody(response, responseTemplate);
			return;
		}
		
		PostCommentResponse postCommentResponse = commentService.postComment(postCommentRequest);
		responseTemplate.setServlet(requestTemplate.getServlet());
		responseTemplate.setStatus(Constant.STATUS_SUCCESS);
		responseTemplate.setMsg("请求成功");
		
		responseTemplate.setContent(postCommentResponse);
		BodyIOUtils.writeBody(response, responseTemplate);
	}
}
