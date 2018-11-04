package com.tl.customclothing.service;

import com.tl.customclothing.param.request.PostCommentRequest;
import com.tl.customclothing.param.response.PostCommentResponse;

public interface ICommentService
{
	
	/**
	 * 提交评论
	 * @param request
	 * @return
	 */
	PostCommentResponse postComment(PostCommentRequest request);
	
}
