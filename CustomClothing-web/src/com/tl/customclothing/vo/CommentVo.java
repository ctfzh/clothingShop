package com.tl.customclothing.vo;

public class CommentVo
{
	private int id;
	
	private int shopId;
	
	private String userId;
	
	private String content;
	
	private String commentTime;
	
	private String lastUpdateTime;
	
	private int orderId;
	

	public int getOrderId()
	{
		return orderId;
	}

	public void setOrderId(int orderId)
	{
		this.orderId = orderId;
	}

	@Override
	public String toString()
	{
		return "CommentVo [id=" + id + ", shopId=" + shopId + ", userId=" + userId + ", content=" + content
				+ ", commentTime=" + commentTime + ", lastUpdateTime=" + lastUpdateTime + "]";
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getShopId()
	{
		return shopId;
	}

	public void setShopId(int shopId)
	{
		this.shopId = shopId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getCommentTime()
	{
		return commentTime;
	}

	public void setCommentTime(String commentTime)
	{
		this.commentTime = commentTime;
	}

	public String getLastUpdateTime()
	{
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}
	
	
}
