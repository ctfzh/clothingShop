package com.tl.customclothing.param.request;

/**
 * 
 * @author tianlin
 */
public class RegisterRequest
{
	// 用户登录id
	private String userLoginId;
	// 用户昵称
	private String userNickName;
	// 用户的真实姓名
	private String userRelName;
	// 用户的性别
	private String userGender;
	// 用户的主要收获地址
	private String userAddrMain;
	// 账号注册时间
	private String registerTime;
	// 用户信息更新时间
	private String lastUpdateTime;
	// 用户的积分
	private String userExp;
	// 用户密码
	public String userPwd;
	
	public String getUserPwd()
	{
		return userPwd;
	}
	public void setUserPwd(String userPwd)
	{
		this.userPwd = userPwd;
	}
	public String getUserExp()
	{
		return userExp;
	}
	public void setUserExp(String userExp)
	{
		this.userExp = userExp;
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
	public String getUserGender()
	{
		return userGender;
	}
	public void setUserGender(String userGender)
	{
		this.userGender = userGender;
	}
	public String getUserAddrMain()
	{
		return userAddrMain;
	}
	public void setUserAddrMain(String userAddrMain)
	{
		this.userAddrMain = userAddrMain;
	}
	public String getRegisterTime()
	{
		return registerTime;
	}
	public void setRegisterTime(String registerTime)
	{
		this.registerTime = registerTime;
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
		return "RegisterRequest [userLoginId=" + userLoginId + ", userNickName=" + userNickName + ", userRelName="
				+ userRelName + ", userGender=" + userGender + ", userAddrMain=" + userAddrMain + ", registerTime="
				+ registerTime + ", lastUpdateTime=" + lastUpdateTime + ", userExp=" + userExp + ", userPwd=" + userPwd
				+ "]";
	}
	
}
