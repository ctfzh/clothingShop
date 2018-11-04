package com.tl.customclothing.vo;

public class UserVo
{

	private int id;
	
	private String userLoginId;
	
	private String userNickName;
	
	private String userRelName;
	
	private int userExp;
	
	private String userIcon;
	
	private String userGender;
	
	private String userPwd;
	
	private String userAddrMain;
	
	private String userAddr1;
	
	private String userAddr2;
	
	private String registerTime;
	
	private String lastUpdataTime;


	@Override
	public String toString()
	{
		return "UserVo [id=" + id + ", userLoginId=" + userLoginId + ", userNickName=" + userNickName + ", userRelName="
				+ userRelName + ", userExp=" + userExp + ", userIcon=" + userIcon + ", userGender=" + userGender
				+ ", userPwd=" + userPwd + ", userAddrMain=" + userAddrMain + ", userAddr1=" + userAddr1
				+ ", userAddr2=" + userAddr2 + ", registerTime=" + registerTime + ", lastUpdataTime=" + lastUpdataTime
				+ "]";
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getUserLoginId()
	{
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId)
	{
		this.userLoginId = userLoginId;
	}

	public String getUserNickName()
	{
		return userNickName;
	}

	public void setUserNickName(String userNickName)
	{
		this.userNickName = userNickName;
	}

	public String getUserRelName()
	{
		return userRelName;
	}

	public void setUserRelName(String userRelName)
	{
		this.userRelName = userRelName;
	}

	public int getUserExp()
	{
		return userExp;
	}

	public void setUserExp(int userExp)
	{
		this.userExp = userExp;
	}

	public String getUserIcon()
	{
		return userIcon;
	}

	public void setUserIcon(String userIcon)
	{
		this.userIcon = userIcon;
	}

	public String getUserGender()
	{
		return userGender;
	}

	public void setUserGender(String userGender)
	{
		this.userGender = userGender;
	}

	public String getUserPwd()
	{
		return userPwd;
	}

	public void setUserPwd(String userPwd)
	{
		this.userPwd = userPwd;
	}

	public String getUserAddrMain()
	{
		return userAddrMain;
	}

	public void setUserAddrMain(String userAddrMain)
	{
		this.userAddrMain = userAddrMain;
	}

	public String getUserAddr1()
	{
		return userAddr1;
	}

	public void setUserAddr1(String userAddr1)
	{
		this.userAddr1 = userAddr1;
	}

	public String getUserAddr2()
	{
		return userAddr2;
	}

	public void setUserAddr2(String userAddr2)
	{
		this.userAddr2 = userAddr2;
	}

	public String getRegisterTime()
	{
		return registerTime;
	}

	public void setRegisterTime(String registerTime)
	{
		this.registerTime = registerTime;
	}

	public String getLastUpdataTime()
	{
		return lastUpdataTime;
	}

	public void setLastUpdataTime(String lastUpdataTime)
	{
		this.lastUpdataTime = lastUpdataTime;
	}
	
	
}
