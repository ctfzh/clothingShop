package com.tl.customclothing.vo;

public class ShopVo
{
	private int shopId;

	private String shopNo;

	private String userIdSale;

	private String shopType;

	private String shopGender;

	private String shopTag;

	private String shopMainImg;

	private String shopImg1;

	private String shopImg2;

	private String shopImg3;

	private String shopImgOnModel;

	private float shopPrice;

	private int shopSalesCount;

	private String uploadTime;

	private String lastUpdateTime;

	
	public ShopVo()
	{
		super();
	}

	public ShopVo(int shopId, String shopNo, String userIdSale, String shopType, String shopGender, String shopTag,
			String shopMainImg, String shopImg1, String shopImg2, String shopImg3, String shopImgOnModel,
			float shopPrice, int shopSalesCount, String uploadTime, String lastUpdateTime)
	{
		super();
		this.shopId = shopId;
		this.shopNo = shopNo;
		this.userIdSale = userIdSale;
		this.shopType = shopType;
		this.shopGender = shopGender;
		this.shopTag = shopTag;
		this.shopMainImg = shopMainImg;
		this.shopImg1 = shopImg1;
		this.shopImg2 = shopImg2;
		this.shopImg3 = shopImg3;
		this.shopImgOnModel = shopImgOnModel;
		this.shopPrice = shopPrice;
		this.shopSalesCount = shopSalesCount;
		this.uploadTime = uploadTime;
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getUserIdSale()
	{
		return userIdSale;
	}

	public void setUserIdSale(String userIdSale)
	{
		this.userIdSale = userIdSale;
	}

	public int getShopId()
	{
		return shopId;
	}

	public void setShopId(int shopId)
	{
		this.shopId = shopId;
	}

	public String getShopNo()
	{
		return shopNo;
	}

	public void setShopNo(String shopNo)
	{
		this.shopNo = shopNo;
	}

	public String getShopType()
	{
		return shopType;
	}

	public void setShopType(String shopType)
	{
		this.shopType = shopType;
	}

	public String getShopGender()
	{
		return shopGender;
	}

	public void setShopGender(String shopGender)
	{
		this.shopGender = shopGender;
	}

	public String getShopTag()
	{
		return shopTag;
	}

	public void setShopTag(String shopTag)
	{
		this.shopTag = shopTag;
	}

	public String getShopMainImg()
	{
		return shopMainImg;
	}

	public void setShopMainImg(String shopMainImg)
	{
		this.shopMainImg = shopMainImg;
	}

	public String getShopImg1()
	{
		return shopImg1;
	}

	public void setShopImg1(String shopImg1)
	{
		this.shopImg1 = shopImg1;
	}

	public String getShopImg2()
	{
		return shopImg2;
	}

	public void setShopImg2(String shopImg2)
	{
		this.shopImg2 = shopImg2;
	}

	public String getShopImg3()
	{
		return shopImg3;
	}

	public void setShopImg3(String shopImg3)
	{
		this.shopImg3 = shopImg3;
	}

	public String getShopImgOnModel()
	{
		return shopImgOnModel;
	}

	public void setShopImgOnModel(String shopImgOnModel)
	{
		this.shopImgOnModel = shopImgOnModel;
	}

	public float getShopPrice()
	{
		return shopPrice;
	}

	public void setShopPrice(float shopPrice)
	{
		this.shopPrice = shopPrice;
	}

	public int getShopSalesCount()
	{
		return shopSalesCount;
	}

	public void setShopSalesCount(int shopSalesCount)
	{
		this.shopSalesCount = shopSalesCount;
	}

	public String getUploadTime()
	{
		return uploadTime;
	}

	public void setUploadTime(String uploadTime)
	{
		this.uploadTime = uploadTime;
	}

	public String getLastUpdateTime()
	{
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}

	@Override
	public String toString()
	{
		return "ShopVo [shopId=" + shopId + ", shopNo=" + shopNo + ", userIdSale=" + userIdSale + ", shopType="
				+ shopType + ", shopGender=" + shopGender + ", shopTag=" + shopTag + ", shopMainImg=" + shopMainImg
				+ ", shopImg1=" + shopImg1 + ", shopImg2=" + shopImg2 + ", shopImg3=" + shopImg3 + ", shopImgOnModel="
				+ shopImgOnModel + ", shopPrice=" + shopPrice + ", shopSalesCount=" + shopSalesCount + ", uploadTime="
				+ uploadTime + ", lastUpdateTime=" + lastUpdateTime + "]";
	}

}
