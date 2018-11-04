package com.tl.customclothing.http.response;


import com.tl.customclothing.model.vo.JustOneTextVo;

/**
 * Created by tianlin on 2017/4/14.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class LoginResponse extends JustOneTextVo
{
    private String userNickName;

    private String userIcon;

    private int jifen;

    private String userGender;

    private String userId;

    private String password;


    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUserNickName()
    {
        return userNickName;
    }

    public void setUserNickName(String userNickName)
    {
        this.userNickName = userNickName;
    }

    public String getUserIcon()
    {
        return userIcon;
    }

    public void setUserIcon(String userIcon)
    {
        this.userIcon = userIcon;
    }

    public int getJifen()
    {
        return jifen;
    }

    public void setJifen(int jifen)
    {
        this.jifen = jifen;
    }

    public String getUserGender()
    {
        return userGender;
    }

    public void setUserGender(String userGender)
    {
        this.userGender = userGender;
    }
}
