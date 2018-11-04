package com.tl.customclothing.service.impl;

import com.tl.customclothing.dao.ICommentDao;
import com.tl.customclothing.factory.ObjectFactory;
import com.tl.customclothing.param.request.PostCommentRequest;
import com.tl.customclothing.param.response.PostCommentResponse;
import com.tl.customclothing.service.ICommentService;
import com.tl.customclothing.util.Constant;
import com.tl.customclothing.util.database.TransactionImpl;

public class CommentServiceImpl implements ICommentService
{

	ICommentDao commentDao;
	
	public CommentServiceImpl()
	{
		commentDao = (ICommentDao) ObjectFactory.get("CommentDaoImpl");
	}
	@Override
	public PostCommentResponse postComment(PostCommentRequest request)
	{
		PostCommentResponse postCommentResponse = new PostCommentResponse();
		
		TransactionImpl transactionImpl = (TransactionImpl) ObjectFactory.get("TransactionImpl");
		
		transactionImpl.begin();
		
		boolean result = commentDao.insert(request);
		
		if(result)
		{
			transactionImpl.commit();
			postCommentResponse.setResponse(Constant.POST_COMMENT_SUCCESS);
		}
		else
		{
			transactionImpl.rollback();
			postCommentResponse.setResponse(Constant.POST_COMMENT_FAILED);
		}
		
		return postCommentResponse;
	}
}
