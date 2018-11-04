package com.tl.customclothing.param.response;

import com.tl.customclothing.vo.CommentVo;

public class CommentItem extends CommentVo
{

	private String commentUserNickName;
	
	public CommentItem()
	{
		
	}
	
	public CommentItem(CommentVo commentVo)
	{
		if(commentVo == null)
			return;
		
		this.setId(commentVo.getId());
		this.setCommentTime(commentVo.getCommentTime());
		this.setContent(commentVo.getContent());
		this.setLastUpdateTime(commentVo.getLastUpdateTime());
		this.setShopId(commentVo.getShopId());
		this.setUserId(commentVo.getUserId());
	}

	
	public String getCommentUserNickName()
	{
		return commentUserNickName;
	}

	public void setCommentUserNickName(String commentUserNickName)
	{
		this.commentUserNickName = commentUserNickName;
	}

	@Override
	public String toString()
	{
		return super.toString() + "CommentItem [commentUserNickName=" + commentUserNickName + "]";
	}
	
	
}
